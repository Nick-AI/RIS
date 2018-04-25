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
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class RadController {

    static java.sql.Connection con;
    static java.sql.Statement stmt, secondStmt;

    @FXML private TextField fNameField;
    @FXML private TextField lNameField;
    @FXML private TextField sexField;
    @FXML private TextField dOBField;
    @FXML private TextField pulseField;
    @FXML private TextField bloodPressureField;
    @FXML private TextField imageIDField;
    @FXML private TextArea radWelcBox;
    @FXML private TextArea procDesrBox;
    @FXML private TextArea preexCondField;
    @FXML private TextArea noteField;
    @FXML private TextArea imgDescrField;
    @FXML private TextArea reportField;
    @FXML private Label patNameLabel;
    @FXML private Label dateTakenLabel;
    @FXML private Button apprRepButton;
    @FXML private Pagination radImgPrev;
    @FXML private TableView<RadViewInfo> procedureTable;
    @FXML private TableView<RadViewInfo> prevReportsTable;
    @FXML private TableColumn welcTableProcCol;
    @FXML private TableColumn welcTablePatNameCol;
    @FXML private TableColumn welcTablePatIDCol;
    @FXML private TableColumn welcTableProcIDCol;

    private Scheduler sched = new Scheduler(createConnection());
    private int radID = 987; //CHANGE HERE
    private String radFName = "Bob", radLName = "Rad", sql;
    private RadViewInfo repPatient;
    private ObservableList<RadViewInfo> radEvents = FXCollections.observableArrayList();

	public void initRadController(ActionEvent aEv){
	    radEvents.removeAll(radEvents);
	    con = createConnection();
	    try {
            stmt = con.createStatement();
            secondStmt = con.createStatement();
        }
        catch(Exception e){
            System.out.println("Problem establishing connection");
        }
//        this.radID = LogInController.getStaffID(); //CHANGE HERE
//        this.radFName = LogInController.getFName();
//        this.radLName = LogInController.getLName();
	    RadViewInfo temp;
        ResultSet rs, tempRs;
        String t_procedure = "", t_patientfName = "", t_patientlName = "", t_procDescr = "", t_dob = "", t_sex = "";
        int t_patientID = -1, t_procedureID = -1, t_reportID = -1;
        try {
            sql = "SELECT * FROM status WHERE nextStaffID = '" + this.radID +"'";
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                t_patientID = rs.getInt(4);
                HashMap<Appointment, Event> pEvents = sched.getPatientEvents(t_patientID);
                Event[] events = new Event[pEvents.values().size()];
                Event ev = pEvents.values().toArray(events)[0];
                t_procDescr = ev.getDescr();
                t_procedure = ev.getModality();
                t_procedureID = ev.getProcID();
                sql = "SELECT * FROM patient WHERE patientID = '" + t_patientID +"'";
                tempRs = secondStmt.executeQuery(sql);
                while (tempRs.next()) {
                    t_patientfName = tempRs.getString("fName");
                    t_patientlName = tempRs.getString("lName");
                    t_dob = tempRs.getDate("dateOfBirth").toString();
                    t_sex = tempRs.getString("gender");
                }
                tempRs.close();
                sql = "SELECT * FROM report WHERE patientID = '" + t_patientID + "' ORDER BY lastModifiedDate " +
                        "DESC LIMIT 1";
                tempRs = secondStmt.executeQuery(sql);
                while(tempRs.next()){
                    t_reportID = tempRs.getInt("reportID");
                }
                tempRs.close();
                temp = new RadViewInfo(t_procedure, t_patientfName, t_patientlName, t_procDescr, t_sex, t_dob,
                        t_patientID, t_procedureID, t_reportID);
                radEvents.add(temp);
            }
            rs.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("There has been an issue with getting the procedures for the table");
        }
        this.createWelcomeMsg(this.radEvents.size());
        this.popProcList();
    }

    private void popProcList() {
        welcTablePatIDCol.setCellValueFactory(new PropertyValueFactory<>("patientID"));
        welcTablePatNameCol.setCellValueFactory(new PropertyValueFactory<>("patientFullName"));
        welcTableProcCol.setCellValueFactory(new PropertyValueFactory<>("procedure"));
        welcTableProcIDCol.setCellValueFactory(new PropertyValueFactory<>("procedureID"));
        procedureTable.setItems(null);
        procedureTable.setItems(radEvents);
    }

    public void seePatientPreview(MouseEvent e){
	    RadViewInfo selected = procedureTable.getSelectionModel().getSelectedItem();
	    this.patNameLabel.setText(selected.getPatientFullName());
	    this.procDesrBox.setText(selected.getProcDescr());
	    ResultSet rs;
        try {
            sql = "SELECT * FROM images WHERE reportID = '" + selected.getReportID() +"' ORDER BY dateTaken DESC LIMIT 1";
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                selected.setImageDate(rs.getDate("dateTaken").toString());
            }
        }
        catch (SQLException ex) {
            System.out.println("There has been an issue with getting the the preview information for this row");
        }
        this.dateTakenLabel.setText(selected.getImageDate());
        this.populateImgPreview(selected);
    }

    private void populateImgPreview(RadViewInfo selected){
	    ArrayList<File> files = new ArrayList();
	    ResultSet rs;
        try {
            sql = "SELECT * FROM images WHERE reportID = '" + selected.getReportID() +"'";
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                files.add(new File(rs.getString("image")));
            }
        }
        catch (SQLException ex) {
            System.out.println("There has been an issue with getting the procedures for the table");
        }
        radImgPrev.setMaxPageIndicatorCount(files.size());
        radImgPrev.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                return createPage(pageIndex, files);
            }
        });
    }

    private VBox createPage(int index, ArrayList<File> files) {

        ImageView imageView = new ImageView();

        File file = files.get(index);
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imageView.setImage(image);
            imageView.setFitWidth(300);
            imageView.setFitHeight(315);
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

    private void createWelcomeMsg(int numProcedures){
        radWelcBox.setText("Welcome Dr. " + this.radLName + ".\nCurrently, there are " + numProcedures +
                " reports waiting to be created.");

    }
    public void startReport(ActionEvent e){
        this.repPatient = procedureTable.getSelectionModel().getSelectedItem();
        Stage stage = new Stage();
        Scene scene = null;
        Parent repView = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ReportView2.0.fxml"));
            repView = loader.load();
            scene = new Scene(repView,1300,740);

            ReportController rCont = loader.getController();
            rCont.initReport(repPatient);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        catch(IOException ex){
            ex.printStackTrace();
            System.out.println("There has been an issue with loading the report view");
        }
    }


    private static java.sql.Connection createConnection() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/RISystem","root", "ResidenceLife1873!");
            //CHANGME Variable changes depending on your assigned password for MySQLWorkBench
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return con;
    }
    public class RadViewInfo{
	    private final StringProperty procedure;
	    private final StringProperty patientfName;
        private final StringProperty patientlName;
        private final StringProperty procDescr;
        private final StringProperty imageDate = new SimpleStringProperty("2018-04-23");
        private final StringProperty sex;
        private final StringProperty dateOfBirth;
        private final StringProperty patientFullName;
        private final IntegerProperty patientID;
        private final IntegerProperty procedureID;
        private final IntegerProperty reportID;
	    public RadViewInfo(String pMod, String pF, String pL, String pDes, String sex, String doB, int patID,
                           int procID, int repID) {
            this.procedure = new SimpleStringProperty(pMod);
            this.patientfName = new SimpleStringProperty(pF);
            this.patientlName = new SimpleStringProperty(pL);
            this.procDescr = new SimpleStringProperty(pDes);
            this.sex = new SimpleStringProperty(sex);
            this.dateOfBirth = new SimpleStringProperty(doB);
            this.patientID = new SimpleIntegerProperty(patID);
            this.procedureID = new SimpleIntegerProperty(procID);
            this.reportID = new SimpleIntegerProperty(repID);
            this.patientFullName = new SimpleStringProperty(pF + " " + pL);
        }

        public String getProcedure() {
            return procedure.get();
        }

        public StringProperty procedureProperty() {
            return procedure;
        }

        public void setProcedure(String procedure) {
            this.procedure.set(procedure);
        }

        public String getPatientfName() {
            return patientfName.get();
        }

        public StringProperty patientfNameProperty() {
            return patientfName;
        }

        public void setPatientfName(String patientfName) {
            this.patientfName.set(patientfName);
        }

        public String getPatientlName() {
            return patientlName.get();
        }

        public StringProperty patientlNameProperty() {
            return patientlName;
        }

        public void setPatientlName(String patientlName) {
            this.patientlName.set(patientlName);
        }

        public String getProcDescr() {
            return procDescr.get();
        }

        public StringProperty procDescrProperty() {
            return procDescr;
        }

        public void setProcDescr(String procDescr) {
            this.procDescr.set(procDescr);
        }

        public String getImageDate() {
            return imageDate.get();
        }

        public StringProperty imageDateProperty() {
            return imageDate;
        }

        public void setImageDate(String imageDate) {
            this.imageDate.set(imageDate);
        }

        public String getSex() {
            return sex.get();
        }

        public StringProperty sexProperty() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex.set(sex);
        }

        public String getDateOfBirth() {
            return dateOfBirth.get();
        }

        public StringProperty dateOfBirthProperty() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth.set(dateOfBirth);
        }

        public String getPatientFullName() {
            return patientFullName.get();
        }

        public StringProperty patientFullNameProperty() {
            return patientFullName;
        }

        public void setPatientFullName(String patientFullName) {
            this.patientFullName.set(patientFullName);
        }

        public int getPatientID() {
            return patientID.get();
        }

        public IntegerProperty patientIDProperty() {
            return patientID;
        }

        public void setPatientID(int patientID) {
            this.patientID.set(patientID);
        }

        public int getProcedureID() {
            return procedureID.get();
        }

        public IntegerProperty procedureIDProperty() {
            return procedureID;
        }

        public void setProcedureID(int procedureID) {
            this.procedureID.set(procedureID);
        }

        public int getReportID() {
            return reportID.get();
        }

        public IntegerProperty reportIDProperty() {
            return reportID;
        }

        public void setReportID(int reportID) {
            this.reportID.set(reportID);
        }

        @Override
        public String toString() {
            return "RadViewInfo{" +
                    "procedure=" + procedure +
                    ", patientfName=" + patientfName +
                    ", patientlName=" + patientlName +
                    ", procDescr=" + procDescr +
                    ", imageDate=" + imageDate +
                    ", sex=" + sex +
                    ", dateOfBirth=" + dateOfBirth +
                    ", patientFullName=" + patientFullName +
                    ", patientID=" + patientID +
                    ", procedureID=" + procedureID +
                    ", reportID=" + reportID +
                    '}';
        }
    }
}