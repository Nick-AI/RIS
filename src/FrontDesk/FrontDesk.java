package FrontDesk;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import Scheduling.Event;
import Scheduling.Scheduler;
import javax.swing.text.MaskFormatter;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import EntranceControllers.LogInController;

//controller
public class FrontDesk {
	
	public static java.sql.Connection con;
	public static java.sql.Statement smt;
	
	java.util.Date date;
	java.sql.Date sqlDate;
	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	Parent root;
	Stage stage;
	Scene scene;
	
	ObservableList<patientData>patientRecord = FXCollections.observableArrayList();
	ObservableList<combinedPatient>cp = FXCollections.observableArrayList();
	Random rand = new Random();
	
	@FXML Button PatientButton;
	@FXML Button ConfirmPatientButton;
	@FXML Button confirmScheduleButton;
	@FXML Button backButton;
	@FXML Button schedulePatientButton;
	@FXML TextField FirstNameField;
	@FXML TextField LastNameField;
	@FXML TextField	IDfield;
	@FXML TextField DOBfield;
	@FXML TextField GenderField;
	@FXML TextField NumberField;
	@FXML TextField CityField;
	@FXML TextField StateField;
	@FXML TextField AddrField1;
	@FXML TextField AddrField2;
	@FXML TextField ZIPfield;
	@FXML TextField fnameSearch;
	@FXML TextField	lnameSearch;
	@FXML TextField bdaySearch;
	@FXML TextField	phoneSearch;
	@FXML TextField citySearch;
	@FXML TextField stateSearch;
	@FXML TextField	zipSearch;
	@FXML TextField searchTextField;
	@FXML TextField editedByUser;
	@FXML TextField dateBox;
	@FXML TextField welcomeUser;
	@FXML TextField modalityBox;
	@FXML TextField priorityBox;
	@FXML TextArea descBox;
	@FXML TableView<patientData> archiveTable;
	@FXML TableColumn<patientData,Integer> idcol;
	@FXML TableColumn<patientData,String> firstNameCol;
	@FXML TableColumn<patientData,String> lastNameCol;
	@FXML TableColumn<patientData,Date> dobcol;
	@FXML TableColumn<patientData,String> phoneCol;
	@FXML TableColumn<patientData,String> addrCol;
	@FXML TableColumn<patientData,String> addrCol2;
	@FXML TableColumn<patientData,String> cityCol;
	@FXML TableColumn<patientData,String> stateCol;
	@FXML TableColumn <patientData,Integer>zipCol;
	@FXML TableColumn <patientData,Integer> refPhysCol;
	@FXML TableView<combinedPatient> ScheduleViewTable;
	@FXML TableColumn<combinedPatient,String>ModCol;
	@FXML TableColumn<combinedPatient,Integer>PatientCol;
	@FXML TableColumn<combinedPatient,String>ProcedureCol;
	@FXML TableColumn<combinedPatient,String>TimeCol;
	@FXML TableColumn<combinedPatient,Integer>ProcIDCol;
	@FXML TableColumn<combinedPatient,Integer>OrderCol;
	@FXML Tab ArchiveTab;
	@FXML Tab HomeTab;
	
	patientData p;
	static int pID;
	static int oID;
	int rID;
	String pfName;
	String plName;
	
	//opens patient form in a new window
	public void patientwindow(ActionEvent event)throws Exception
	{
		
		try {
			System.out.println("New Patient");
			root = FXMLLoader.load(getClass().getResource("/FrontDeskView/FrontDeskNewPatient.fxml"));
			stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//connect to DB and insert values
	public void FDConnection(ActionEvent event) throws Exception
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");  
			con=(Connection) DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/RISystem","root","admin123");  
			smt=(Statement) con.createStatement(); 
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		//getting text entered into textfields
		//if nothing was entered, sets a default value
		String fname = FirstNameField.getText();
		if (fname.trim().equals(""))
			fname = "NONE";
		String lname = LastNameField.getText();
		if (lname.trim().equals(""))
			lname = "NONE";
		String DOB = DOBfield.getText();
		if (DOB.trim().equals("")||DOB.trim().equals(null))
		{
			DOB = "0000-00-00";
			date = formatter.parse(DOB);
			sqlDate = new java.sql.Date(date.getTime());
		}
		else
		{
			date = formatter.parse(DOB);
			sqlDate = new java.sql.Date(date.getTime());

		}
		String gend = GenderField.getText().toUpperCase();
		if (gend.trim().equals(""))
			gend = "M/F";
		String Num = NumberField.getText();
		if (Num.trim().equals(""))
			Num = "0000000000";
		String address1 = AddrField1.getText();
		if (address1.trim().equals(""))
			address1 = "NONE";
		String address2 = AddrField2.getText();
		if (address2.trim().equals(""))
			address2 = "NONE";
		String addresscity = CityField.getText();
		if (addresscity.trim().equals(""))
			addresscity = "NONE";
		String addressstate = StateField.getText();
		if (addressstate.trim().equals(""))
			addressstate = "NA";
		int Zip = -1;
		if(!ZIPfield.getText().isEmpty())
			Zip = Integer.parseInt(ZIPfield.getText());
		
		int refid = getRefNum();
		
		
		//formats phone numbers	
		String phoneMask= "###-###-####";
		MaskFormatter format = new MaskFormatter(phoneMask);
		format.setValueContainsLiteralCharacters(false);
		Num = format.valueToString(Num);
		//	
			
			//inserts data into patient table
			try {
				String sql = "INSERT INTO patient (patientID,fName,lName,dateOfBirth,gender,phoneNum,addressOne,addressTwo,"
						+ "addressCity,addressState,addressZip,refPhysicianID)"
						+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
						
				PreparedStatement smt = con.prepareStatement(sql);
				
				smt.setInt(1, newPatientNum());
				smt.setString(2, fname);
				smt.setString(3, lname);
				smt.setDate(4, sqlDate); 
				smt.setString(5, gend);
				smt.setString(6, Num);
				smt.setString(7, address1);
				smt.setString(8, address2);
				smt.setString(9, addresscity);
				smt.setString(10, addressstate);
				smt.setInt(11, Zip);
				smt.setInt(12, refid);
				
				if(smt.executeUpdate() > 0)
				{
					System.out.println("Row inserted.");
					((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
				}
				else
				{
					System.out.println("Row not inserted.");
				}
				
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	
	//returns a random referring physician id from the database
	public int getRefNum() throws SQLException
	{
		String ref = "Referring Physician";
		String sql = "SELECT * FROM staff WHERE staffType = '"+ref+"';";
		ResultSet rs = con.createStatement().executeQuery(sql);
	    List<Integer> randRefID = new ArrayList<>();
	    while(rs.next())
	    {
	        randRefID.add(rs.getInt("staffID"));
	    }
	    if (randRefID.isEmpty()) {
	        System.out.println("No data for this field");
	        return 1;
	    } else {
	       Random random = new Random();
	       return randRefID.get(random.nextInt(randRefID.size()));
	    }
	}
	
	public void loadFromDB()
	{	   
		archiveTable.setRowFactory(tv -> {
		    TableRow<patientData> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY 
		             && event.getClickCount() == 1) {

		            p = row.getItem();
		            pID = p.getId();
		            rID = p.getRefID();
		            pfName = p.getFirstName();
		            plName = p.getLastName();
		           //System.out.println("loadfromdb "+pID);
		            
		        }
		    });
		    return row ;
		});
		
	
		/*ProcedureCol.setCellValueFactory(new PropertyValueFactory<combinedPatient,String>("Proc"));
		TimeCol.setCellValueFactory(new PropertyValueFactory<combinedPatient,String>("Time"));
		ModCol.setCellValueFactory(new PropertyValueFactory<combinedPatient,String>("Mod"));
		PatientCol.setCellValueFactory(new PropertyValueFactory<combinedPatient,Integer>("Patid"));
		ProcIDCol.setCellValueFactory(new PropertyValueFactory<combinedPatient,Integer>("Procid"));
		OrderCol.setCellValueFactory(new PropertyValueFactory<combinedPatient,Integer>("Ordid"));*/
		
		idcol.setCellValueFactory(new PropertyValueFactory<patientData,Integer>("Id"));
		refPhysCol.setCellValueFactory(new PropertyValueFactory<patientData,Integer>("refID"));
		
		firstNameCol.setCellValueFactory(new PropertyValueFactory<patientData,String>("firstName"));
		firstNameCol.setCellFactory(TextFieldTableCell.<patientData>forTableColumn());
        firstNameCol.setOnEditCommit(event -> {
        	patientData patient = event.getRowValue();
        	patient.setFirstName(event.getNewValue());
        	try {
				updateDB("fName",event.getNewValue(),patient.getId());		//updates first name in database
				userTracker();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
		lastNameCol.setCellValueFactory(new PropertyValueFactory<patientData,String>("lastName"));
		lastNameCol.setCellFactory(TextFieldTableCell.<patientData>forTableColumn());
        lastNameCol.setOnEditCommit(event -> {
        	patientData patient = event.getRowValue();
        	patient.setLastName(event.getNewValue());
        	try {
				updateDB("lName",event.getNewValue(),patient.getId());		//updates last name in database
				userTracker();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
		
        dobcol.setCellValueFactory(new PropertyValueFactory<patientData,Date>("Bdate"));
        dobcol.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Date>() {

            @Override
            public String toString(Date t) {
                if (t==null) {
                    return "" ;
                } else {
                    return formatter.format(t);
                }
            }

            @Override
            public Date fromString(String string) {
                try {
                	date = formatter.parse(string);
        			sqlDate = new java.sql.Date(date.getTime());;
        			return sqlDate;
                } catch (ParseException exc) {
                    return null;
                }
            }

        }));
        dobcol.setOnEditCommit(event -> {
        	patientData patient = event.getRowValue();
        	try {
				patient.setBdate(event.getNewValue().toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	try{
        		updateDBDate("dateOfBirth",event.getNewValue().toString(),patient.getId());
        		userTracker();
        	}catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        });
        
		phoneCol.setCellValueFactory(new PropertyValueFactory<patientData,String>("num"));
		phoneCol.setCellFactory(TextFieldTableCell.<patientData>forTableColumn());
		phoneCol.setOnEditCommit(event -> {
        	patientData patient = event.getRowValue();
        	patient.setNum(event.getNewValue());
        	try {
				updateDB("phoneNum",event.getNewValue(),patient.getId());	//updates phone number in database
				userTracker();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
		
		addrCol.setCellValueFactory(new PropertyValueFactory<patientData,String>("address1"));
		addrCol.setCellFactory(TextFieldTableCell.<patientData>forTableColumn());
		addrCol.setOnEditCommit(event -> {
        	patientData patient = event.getRowValue();
        	patient.setAddress1(event.getNewValue());
        	try {
				updateDB("addressOne",event.getNewValue(),patient.getId());		//updates address one in database
				userTracker();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
		addrCol2.setCellValueFactory(new PropertyValueFactory<patientData,String>("address2"));
		addrCol2.setCellFactory(TextFieldTableCell.<patientData>forTableColumn());
        addrCol2.setOnEditCommit(event -> {
        	patientData patient = event.getRowValue();
        	patient.setAddress2(event.getNewValue());
        	try {
				updateDB("addressTwo",event.getNewValue(),patient.getId());	//updates address two in database
				userTracker();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
        
		cityCol.setCellValueFactory(new PropertyValueFactory<patientData,String>("city"));
		cityCol.setCellFactory(TextFieldTableCell.<patientData>forTableColumn());
		cityCol.setOnEditCommit(event -> {
        	patientData patient = event.getRowValue();
        	patient.setCity(event.getNewValue());
        	try {
				updateDB("addressCity",event.getNewValue(),patient.getId());	//updates city in database
				userTracker();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
		
		stateCol.setCellValueFactory(new PropertyValueFactory<patientData,String>("state"));
		stateCol.setCellFactory(TextFieldTableCell.<patientData>forTableColumn());
	    stateCol.setOnEditCommit(event -> {
        	patientData patient = event.getRowValue();
        	patient.setState(event.getNewValue());
        	try {
				updateDB("addressState",event.getNewValue(),patient.getId());	//updates state in database
				userTracker();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
		
		zipCol.setCellValueFactory(new PropertyValueFactory<patientData,Integer>("zip"));
		zipCol.setCellFactory(TextFieldTableCell.<patientData,Integer>forTableColumn(new IntegerStringConverter()));
        zipCol.setOnEditCommit(event -> {
        	patientData patient = event.getRowValue();
        	patient.setZip(event.getNewValue());
        	try {
				updateDBZip("addressZip",event.getNewValue(),patient.getId());  //updates zip in database
				userTracker();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
                
        try 
		{
			Class.forName("com.mysql.jdbc.Driver");  
			con=(Connection) DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/RISystem","root","admin123"); //change to whatever your username/password is 
			smt=(Statement) con.createStatement(); 
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		//fetches data from patient table
		try
		{
			patientRecord.clear();
			String query = "SELECT * FROM patient";
			ResultSet rs = con.createStatement().executeQuery(query);
			while(rs.next())
			{
				patientRecord.add(new patientData(rs.getInt("patientID"),rs.getString("fName"),rs.getString("lName"),rs.getDate("dateOfBirth"),rs.getString("phoneNum"),
						rs.getString("addressOne"),rs.getString("addressTwo"),rs.getString("addressCity"),rs.getString("addressState"),rs.getInt("addressZip")
						,rs.getInt("refPhysicianID")));
			}
			
		}
		//
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		archiveTable.setItems(patientRecord);
	}
	
	
	//Updates the database to reflect edited values
	public void updateDB(String column, String newValue, int id) throws SQLException
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");  
			con=(Connection) DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/RISystem","root","admin123"); //change to whatever your username/password is 
			smt=(Statement) con.createStatement(); 
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		PreparedStatement stmt = con.prepareStatement("UPDATE patient SET "+column+" = ? WHERE patientID = ? ");
		stmt.setString(1, newValue);
        stmt.setInt(2, id);
        stmt.execute();
        
		try {
			if (smt != null)
			{
				smt.close();
			}
			if (con != null)
			{
				con.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void updateDBZip(String column,int newValue,int id)throws SQLException
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");  
			con=(Connection) DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/RISystem","root","admin123"); //change to whatever your username/password is 
			smt=(Statement) con.createStatement(); 
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		PreparedStatement stmt = con.prepareStatement("UPDATE patient SET "+column+" = ? WHERE patientID = ? ");
		stmt.setInt(1, newValue);
        stmt.setInt(2, id);
        stmt.execute();
        
        try {
			if (smt != null)
			{
				smt.close();
			}
			if (con != null)
			{
				con.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void updateDBDate(String column,String newValue,int id)throws SQLException, ParseException
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");  
			con=(Connection) DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/RISystem","root","admin123"); //change to whatever your username/password is 
			smt=(Statement) con.createStatement(); 
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		date = formatter.parse(newValue);
		sqlDate = new java.sql.Date(date.getTime());
		PreparedStatement stmt = con.prepareStatement("UPDATE patient SET "+column+" = ? WHERE patientID = ? ");
		stmt.setDate(1,sqlDate);
        stmt.setInt(2, id);
        stmt.execute();
        
        try {
			if (smt != null)
			{
				smt.close();
			}
			if (con != null)
			{
				con.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void archiveSearch(KeyEvent key)
	{
		FilteredList<patientData> filterData = new FilteredList<>(patientRecord, p -> true);
		searchTextField.textProperty().addListener((observable,oldvalue,newvalue) -> {
            filterData.setPredicate(pat ->{
            	 if (newvalue == null || newvalue.isEmpty()) {
                     return true;
                 }
                 String typedText = newvalue.toLowerCase();
                 if (pat.getFirstName().toLowerCase().indexOf(typedText) != -1) {

                     return true;
                 }
                 if (pat.getLastName().toLowerCase().indexOf(typedText) != -1){
                	 return true;
                 }
                 if (pat.getBdate().toString().toLowerCase().indexOf(typedText) != -1){
                	 return true;
                 }
                 if (pat.getZipAsString().toLowerCase().indexOf(typedText) != -1){
                	 return true;
                 }
                 if (pat.getAddress1().toLowerCase().indexOf(typedText) != -1){
                	 return true;
                 }
                 if (pat.getAddress2().toLowerCase().indexOf(typedText) != -1){
                	 return true;
                 }
                 if (pat.getNum().toLowerCase().indexOf(typedText) != -1){
                	 return true;
                	 
                 }
                 if (pat.getCity().toLowerCase().indexOf(typedText) != -1){
                	 return true;
                 }
                 if (pat.getState().toLowerCase().indexOf(typedText) != -1){
                	 return true;
                 }
                 else
                	 return false;
            });
            SortedList<patientData> sortedList = new SortedList<>(filterData);
            sortedList.comparatorProperty().bind(archiveTable.comparatorProperty());
            archiveTable.setItems(sortedList);
	});
	
	}
	
	
	public static int newPatientNum()
	{

		ResultSet cur;
		int value = 0;
		try {
			cur=con.createStatement().executeQuery("SELECT patientID FROM patient ORDER BY patientID DESC LIMIT 1;");
			
			while(cur.next())
		{
			
			int val=cur.getInt("patientID");
			val=val+1;
			value=val;
			
		}
			
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return value;
	}

	
	private void userTracker()
	{
		//keeps track of last user to edit the archive table
		editedByUser.setText(LogInController.getUserName());
	}
	
	
	public void returnToMenu(ActionEvent event)throws Exception
	{
		try {
			stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/EntranceView/MainMenu.fxml"));
			scene = new Scene(root);
			stage.setScene(scene);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static int newNumber()
	{

		ResultSet cur;
		int value = 0;
		try {
			cur=con.createStatement().executeQuery("SELECT orderID FROM schedule ORDER BY orderID DESC LIMIT 1;");
			
			while(cur.next())
		{
			
			int val=cur.getInt("orderID");
			val=val+1;
			value = val;
			
		}
			
			
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return value;
	}
	
	
	//shows schedulerView
	public void showScheduler(ActionEvent event)throws Exception
	{
		//System.out.println("showschedule "+pID);
		try {
			System.out.println("Scheduler");
			root = FXMLLoader.load(getClass().getResource("/FrontDeskView/schedulerView.fxml"));
			stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void printRow(patientData p)
	{
		System.out.println(p.getFirstName()+ p.getLastName());
		System.out.println(p.getId());
	}
	
	
	//schedules patient
	public void schedulePatient(ActionEvent event) throws SQLException,NumberFormatException,NullPointerException
	{
		//System.out.println("schedulepatient "+pID);
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");  
			con=(Connection) DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/RISystem","root","admin123"); //change to whatever your username/password is 
			smt=(Statement) con.createStatement(); 
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}

		try {
			final String mod = modalityBox.getText();
			final String desc = descBox.getText();
			final int priority = Integer.parseInt(priorityBox.getText());
			oID = newNumber();
			System.out.println("schedulepatient2 "+pID);
			Event newEvent = new Event(mod,desc,priority,pID,oID);
			Scheduler s = new Scheduler(con);
			s.createEvent(newEvent);
			s.startTreatment(newEvent.getPatientID());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();		//uncomment this if you run into issues
			System.err.println();
		}
		catch (NullPointerException e1){
			System.err.println();
			//e1.printStackTrace();
		}
		System.out.println("Success!");
		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
	}
	
	//updates schedule tableview
	public void loadTable()
	{
		ProcedureCol.setCellValueFactory(new PropertyValueFactory<combinedPatient,String>("Proc"));
		TimeCol.setCellValueFactory(new PropertyValueFactory<combinedPatient,String>("Time"));
		ModCol.setCellValueFactory(new PropertyValueFactory<combinedPatient,String>("Mod"));
		PatientCol.setCellValueFactory(new PropertyValueFactory<combinedPatient,Integer>("Patid"));
		ProcIDCol.setCellValueFactory(new PropertyValueFactory<combinedPatient,Integer>("Procid"));
		OrderCol.setCellValueFactory(new PropertyValueFactory<combinedPatient,Integer>("Ordid"));
		
		 try 
			{
				Class.forName("com.mysql.jdbc.Driver");  
				con=(Connection) DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/RISystem","root","admin123"); //change to whatever your username/password is 
				smt=(Statement) con.createStatement(); 
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			//fetches data from patient table
			try
			{
				cp.clear();
				String query = "SELECT * FROM schedule";
				ResultSet rs = con.createStatement().executeQuery(query);
				while(rs.next())
				{
					cp.add(new combinedPatient(rs.getString("procType"),rs.getString("procTime"),rs.getString("modality"),rs.getInt("patientID"),rs.getInt("procID"),rs.getInt("orderID")));
				}
				
			}
			//
			catch(Exception e)
			{
				e.printStackTrace();
			}
			ScheduleViewTable.setItems(cp);
		
		
	}

}


