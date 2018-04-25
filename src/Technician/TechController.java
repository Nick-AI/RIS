package Technician;

import Scheduling.Scheduler;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TechController {
    static java.sql.Connection con;
    static java.sql.Statement stmt, secondStmt;

    @FXML
    private ImageView imgViewer;
    @FXML
    private Button attachButton;
    @FXML
    private Button apprBut;
    @FXML
    private Label procDescrLabel;
    @FXML
    private Label patIDLabel;
    @FXML
    private TableColumn pIDCol;
    @FXML
    private TableColumn modCol;
    @FXML
    private TableColumn descrCol;
    @FXML
    private TableView<WorkListInfo> workList;

    private int techID = 300000000;
    private int maxImgID;
    private File[] imgFiles;
    private int curr_idx = 0;
    Scheduler sched = new Scheduler(createConnection());
    private ObservableList<WorkListInfo> workListItems = FXCollections.observableArrayList();
    private ArrayList<File> images = new ArrayList();


    public void attachImage(ActionEvent e){
        images.add(imgFiles[curr_idx]);
    }

    public void submitImages(ActionEvent e){
        String query = "INSERT INTO images (imageID, description, modality, dateTaken, image, reportID) " +
                "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement upd = null;
        int t_reportID=2;
        ResultSet tempRs;
        String sql = "SELECT * FROM report WHERE patientID = '" + workList.getItems().get(0).patientID +
                "' ORDER BY lastModifiedDate DESC LIMIT 1";
        try {
            tempRs = stmt.executeQuery(sql);
            while(tempRs.next()){
                t_reportID = tempRs.getInt("reportID");
            }
        }
        catch (SQLException e1) {
            e1.printStackTrace();
        }
        for(File item : images) {
            maxImgID ++;
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                Date parsed = format.parse("20182304");
                java.sql.Date sqlD = new java.sql.Date(parsed.getTime());
                upd = con.prepareStatement(query);
                upd.setInt(1, maxImgID);
                upd.setString(2, workList.getItems().get(0).descriptionn.toString());
                upd.setString(3, workList.getItems().get(0).modality.toString());
                upd.setDate(4, sqlD);
                upd.setString(5, item.toString());
                upd.setInt(6, t_reportID);
                upd.executeUpdate();
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void nextImg() {
        try {
            BufferedImage bufferedImage = ImageIO.read(imgFiles[curr_idx]);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imgViewer.setImage(image);
            curr_idx++;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getWorkList() {
        try {
            int t_patId;
            String t_mod = "", t_descr = "";
            ResultSet sec;
            stmt = con.createStatement();
            String sql = "SELECT imageID FROM images ORDER BY imageID DESC LIMIT 1";
            ResultSet entries = stmt.executeQuery(sql);
            if (entries.next())
                maxImgID = entries.getInt("imageID");
            entries.close();
            sql = "SELECT * FROM status WHERE nextStaffID = '" + this.techID + "'";
            entries = stmt.executeQuery(sql);
            while (entries.next()) {
                t_patId = entries.getInt("patientID");
                sql = "SELECT * FROM schedule WHERE patientID = '" + t_patId + "'";
                sec = stmt.executeQuery(sql);
                while (sec.next()) {
                    t_descr = sec.getString("procType");
                    t_mod = sec.getString("modality");
                }
                workListItems.add(new WorkListInfo(new SimpleStringProperty(t_mod), new SimpleStringProperty(t_descr),
                        new SimpleIntegerProperty(t_patId)));
            }
        } catch (SQLException e) {
            System.out.println("There was a problem with getting the newest procedure ID");
            System.out.println(e.getErrorCode());
        }

    }

    private void populateWorkList(){
        pIDCol.setCellValueFactory(new PropertyValueFactory<>("patientID"));
        this.descrCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.modCol.setCellValueFactory(new PropertyValueFactory<>("modality"));
        workList.setItems(null);
        workList.setItems(workListItems);
    }


    private void getImages(File directory) {
        if (directory != null) {
            FilenameFilter filterJpg = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".jpg");
                }
            };

            imgFiles = directory.listFiles(filterJpg);
        }
    }

    private static java.sql.Connection createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/RISystem", "root", "ResidenceLife1873!");
            //CHANGME Variable changes depending on your assigned password for MySQLWorkBench
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    public class WorkListInfo {
        public int getPatientID() {
            return patientID.get();
        }

        public IntegerProperty patientIDProperty() {
            return patientID;
        }

        public void setPatientID(int patientID) {
            this.patientID.set(patientID);
        }

        public WorkListInfo(StringProperty modality, StringProperty description, IntegerProperty patientID) {
            this.modality = modality;
            this.descriptionn = description;
            this.patientID = patientID;
        }

        private final StringProperty modality;

        public String getModality() {
            return modality.get();
        }

        public StringProperty modalityProperty() {
            return modality;
        }

        public void setModality(String modality) {
            this.modality.set(modality);
        }

        public String getDescriptionn() {
            return descriptionn.get();
        }

        public StringProperty descriptionnProperty() {
            return descriptionn;
        }

        public void setDescriptionn(String descriptionn) {
            this.descriptionn.set(descriptionn);
        }


        private final StringProperty descriptionn;
        private final IntegerProperty patientID;
    }
}
