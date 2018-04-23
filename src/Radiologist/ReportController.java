package Radiologist;

import EntranceControllers.LogInController;
import Scheduling.Appointment;
import Scheduling.Event;
import Scheduling.Scheduler;
import application.openSQL;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static Scheduling.TestDriver.createConnection;

public class ReportController{

    static java.sql.Connection con;
    static java.sql.Statement stmt, secondStmt;

    @FXML private TextField fNameField;
    @FXML private TextField lNameField;
    @FXML private TextField sexField;
    @FXML private TextField dOBField;
    @FXML private TextField pulseField;
    @FXML private TextField bloodPressureField;
    @FXML private TextField imageIDField;
    @FXML private TextArea procDesrBox;
    @FXML private TextArea preexCondField;
    @FXML private TextArea noteField;
    @FXML private TextArea imgDescrField;
    @FXML private TextArea reportField;
    @FXML private Label patNameLabel;
    @FXML private Label dateTakenLabel;
    @FXML private Button apprRepButton;
    @FXML private Pagination imgSlider;
    @FXML private CheckBox attachImgRadButton;
    @FXML private TableView<RadController.RadViewInfo> procedureTable;
    @FXML private TableView<RadController.RadViewInfo> prevReportsTable;
    @FXML private TableColumn welcTableProcCol;
    @FXML private TableColumn welcTablePatNameCol;
    @FXML private TableColumn welcTablePatIDCol;
    @FXML private TableColumn welcTableProcIDCol;

    private Scheduler sched = new Scheduler(createConnection());
    private RadController.RadViewInfo repPatient;
    private ObservableList<RadController.RadViewInfo> radEvents = FXCollections.observableArrayList();
    private ObservableList<RadImage> pImgs = FXCollections.observableArrayList();

    public void initReport(RadController.RadViewInfo person){
        this.repPatient = person;
        con = createConnection();
        try {
            stmt = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void pageSelected(ActionEvent e){
        if(attachImgRadButton.isSelected()) {
            int idx = imgSlider.getCurrentPageIndex();
            pImgs.get(idx).attached = true;
        }
    }

    @FXML
    public void saveImgChanges(ActionEvent e){
        pImgs.get(imgSlider.getCurrentPageIndex()).imageDescr = imgDescrField.getText();
    }

    @FXML
    public void approveReport(ActionEvent e){
        String sql;
        ResultSet rs;
        Date date = new Date();
        sql = "UPDATE report SET reportBody = ?, lastModifiedID = ?, lastModifiedDate = ? WHERE reportID = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, reportField.getText());
            pstmt.setInt(2, LogInController.getStaffID());
            pstmt.setDate(3, java.sql.Date.valueOf(LocalDate.now(ZoneId.of("America/Montreal"))));
            pstmt.setInt(4, repPatient.getReportID());
            pstmt.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        sql = "SELECT * FROM images WHERE reportID = '" + this.repPatient.getReportID() +"'";
        try {
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                for(RadImage temp : pImgs) {
                    if (rs.getInt("imageID")==temp.imgID) {
                        if(temp.attached){
                            sql = "UPDATE images SET description = ? WHERE imageID = ?";
                            PreparedStatement pstmt = con.prepareStatement(sql);
                            pstmt.setString(1, temp.imageDescr);
                            pstmt.setInt(2, temp.imgID);
                            pstmt.executeUpdate();
                        }
                        else{
                            sql = "DELETE FROM images WHERE imageID = '" + temp.imgID +"'";
                            stmt.executeUpdate(sql);
                        }
                    }
                }
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    public void fillReportPage(ActionEvent e){
        fNameField.setText(this.repPatient.getPatientfName());
        lNameField.setText(this.repPatient.getPatientlName());
        sexField.setText(this.repPatient.getSex());
        dOBField.setText(this.repPatient.getDateOfBirth());

        ArrayList<File> files = new ArrayList();
        ResultSet rs;
        String sql;
        try {
            sql = "SELECT * FROM images WHERE reportID = '" + this.repPatient.getReportID() +"'";
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                files.add(new File(rs.getString(5)));
                pImgs.add(new RadImage(rs.getString("image"), rs.getString(2), rs.getInt(1)));
            }
            rs.close();
        }
        catch (SQLException ex) {
            System.out.println("There has been an issue with getting the procedures for the table");
        }
        this.imgSlider.setPageCount(files.size());
        this.imgSlider.setPageFactory(new Callback<Integer, Node>() { public Node call(Integer pageIndex) {
            return createPage(pageIndex, files); }});

        try {
            sql = "SELECT * FROM vitals WHERE reportID = '" + this.repPatient.getReportID() +"'";
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                pulseField.setText(rs.getString(2));
                bloodPressureField.setText(rs.getString(3));
                preexCondField.setText(rs.getString("preExConditions"));
                noteField.setText("No further remarks.");
            }
            rs.close();
        }
        catch (SQLException ex) {
            System.out.println("There has been an issue with getting the vitals for the table");
        }
    }

    private VBox createPage(int index, ArrayList<File> files) {

        ImageView imageView = new ImageView();

        imageIDField.setText(Integer.toString(pImgs.get(index).imgID));
        imgDescrField.setText(pImgs.get(index).imageDescr);
        attachImgRadButton.setSelected(pImgs.get(index).attached);

        File file = files.get(index);
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imageView.setImage(image);
            imageView.setFitWidth(500);
            imageView.setFitHeight(230);
            imageView.setPreserveRatio(false);
            imageView.setSmooth(true);
            imageView.setCache(true);
        } catch (IOException ex) {
            System.out.println("Problem loading image file");
        }
        VBox pageBox = new VBox();
        pageBox.getChildren().add(imageView);
        return pageBox;
    }

    private class RadImage{
        String fileDir, imageDescr, report;
        boolean attached = false;
        int imgID;

        public RadImage(String fDir, String iDescr, int iID){
            this.fileDir = fDir;
            this.imageDescr = iDescr;
            this.imgID = iID;
        }
    }
}