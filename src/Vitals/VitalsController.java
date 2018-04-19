package Vitals;

import java.sql.ResultSet;
import java.sql.Time;
import java.util.Date;

import EntranceControllers.LogInController;
import application.Main;
import application.openSQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class VitalsController 
{
	private Main main;
	Stage stage;
	Scene scene;
	Parent root;
	
	@FXML TextField lastNameField;
	@FXML TextField dobField;
	@FXML TextField pulseField;
	@FXML TextField bpField;
	@FXML TextArea preExArea;
	@FXML Button backButton;
	@FXML Button logButton;
	
	Date date = new Date();
    Time time;
    String pulse, bp, preEx, name, dob;
    int staffID, patientID, vitalsID, reportID;
	
	public void back(ActionEvent event) throws Exception
	{
		//System.out.println("here");
		stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

		root = FXMLLoader.load(getClass().getResource("/EntranceView/LogIn.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);
	}
	
	public void log(ActionEvent event) throws Exception
	{
		time = new Time(date.getTime());
		pulse = pulseField.getText();
		bp = bpField.getText();
		preEx = preExArea.getText();
		name = lastNameField.getText();
		dob = dobField.getText();
		
		String q1 = "select * from patient WHERE lName = '"+name+"' AND dateOfBirth = "+dob+";";
		ResultSet rs= openSQL.stmt.executeQuery(q1);
		patientID = rs.getInt(1);
		
		//String q2 = "select * from report WHERE patientID = '"+patientID+"' AND beingModified = 1;";
		String q2 = "select * from report where ("+patientID+") in (select patientID, max(lastModifiedDate) as lastModifiedDate from report group by patientID);";
		ResultSet rs1= openSQL.stmt.executeQuery(q2);
		reportID = rs1.getInt(1);
		
		vitalsID = reportID;
		
		staffID = LogInController.getStaffID();
		
		 String q3 = "insert into vitals values('"+time+"', '"+pulse+"', '"+bp+"', '"+staffID+"',"
         		+ " '"+patientID+"', '"+preEx+"', '"+vitalsID+"', '"+reportID+"');";
         int x = openSQL.stmt.executeUpdate(q3);
         if (x > 0)            
             System.out.println("Successfully Inserted");            
         else           
             System.out.println("Insert Failed");
         
         lastNameField.setText("");
         dobField.setText("");
         pulseField.setText("");
         bpField.setText("");
         preExArea.setText("");
	}
}
