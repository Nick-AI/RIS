package EntranceControllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import application.Main;
import application.openSQL;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewEmployeeAccountController 
{
	private Main main;
	Stage stage;
	Scene scene;
	Parent root;
	
	@FXML Button ButtonBack;
	@FXML Button submitButton;
	@FXML TextField userNameField;
	@FXML PasswordField passwordField;
	@FXML Label failLabel;
	@FXML TextField adminUserNameField;
	@FXML PasswordField adminPasswordField;
	@FXML PasswordField confirmPasswordField;
	@FXML ChoiceBox<String> levelChoiceBox;
	@FXML TextField nameField;

	//used to connect the main class with the controller
	public void setMain(Main M)
	{
		main=M;
	}
	
	public void populate()
	{
		levelChoiceBox.setItems(FXCollections.observableArrayList("Admin","Doctor","Nurse","Clerk","Billing"));
	}
	
	public void back(ActionEvent event) throws Exception
	{
		//System.out.println("here");
		stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

		root = FXMLLoader.load(getClass().getResource("/EntranceView/LogIn.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);
	}
	
	public void submit()
	{
		String UserName = userNameField.getText();
		String Name = nameField.getText();
		String Password = passwordField.getText();
		String ConfirmPass = confirmPasswordField.getText();
		String AdminUserName = adminUserNameField.getText();
		String AdminPassword = adminPasswordField.getText();
		int Level = 0;
		int adminLevel = 0;
		boolean check1 = false;
		boolean check2 = false;
		boolean check3 = false;
		boolean check4 = false;
		failLabel.setText("");
		
		if(Objects.equals((String) levelChoiceBox.getValue(), "Admin"))
		{
			Level = 1;
		}
		else if(Objects.equals((String) levelChoiceBox.getValue(), "Doctor"))
		{
			Level = 2;
		}
		else if(Objects.equals((String) levelChoiceBox.getValue(), "Nurse"))
		{
			Level = 3;
		}
		else if(Objects.equals((String) levelChoiceBox.getValue(), "Clerk"))
		{
			Level = 4;
		}
		else if(Objects.equals((String) levelChoiceBox.getValue(), "Billing"))
		{
			Level = 5;
		}
		
		//checks to see if adminUser exist
		try 
		{
			String q1 = "select * from login WHERE username = '"+AdminUserName+"' AND password = '"+AdminPassword+"';";
			ResultSet rs1= openSQL.stmt.executeQuery(q1);
			
			if (rs1.next())
			{
				adminLevel = rs1.getInt(3);
				check1 = true;
			}
			else
			{
				failLabel.setText("admin information incorrect.");
			}
		}
		
		catch (SQLException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//checks if adminUser is actual admin
		if(check1 == true)
		{
			if(adminLevel == 1)
			{
				check2 = true;
			}
			else 
			{
				failLabel.setText(failLabel.getText() + " An admin is required to create a new user.");
			}
		}
		
		//checks to see if user already exist
		if((check1 == true) && (check2 == true))
		{
			try 
			{
				String q1 = "select * from login WHERE username = '"+UserName+"';";
				ResultSet rs2= openSQL.stmt.executeQuery(q1);
				
				if (rs2.next())
				{
					failLabel.setText(failLabel.getText() + " This user already exist.");
				}
				else
				{
					check3 = true;
				}
			} 
			
			catch (SQLException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		//checks to make sure passwords match
		if((check1 == true) && (check2 == true) && (check3 == true))
		{
			if(ConfirmPass.equals(Password))
			{
				check4 = true;
			}
			else
			{
				failLabel.setText("User passwords do not match.");
			}
		}
		
		//creates user if all other parts are true
		if((check1 == true) && (check2 == true) && (check3 == true) && (check4 == true))
		{
			try 
			{
				String q1 = "select * from login WHERE username = '"+ UserName +"' AND password = '"+Password+"';";
				ResultSet rs3= openSQL.stmt.executeQuery(q1);
				
				if (rs3.next())
				{
					System.out.println("User id is already registered");
				}
				else
				{
					// Inserting data in database
		            String q2 = "insert into login values('" +UserName+ "', '" +Password+ 
		                                  "', '" +Level+ "', '" +Name+ "');";
		            int x = openSQL.stmt.executeUpdate(q2);
		            if (x > 0) 
		            {           
		                System.out.println("Successfully Inserted");  
		                failLabel.setText("User Created!");
		                userNameField.setText("");
		        		nameField.setText("");
		        		passwordField.setText("");
		        		confirmPasswordField.setText("");
		        		adminUserNameField.setText("");
		        		adminPasswordField.setText("");
		            }
		            else           
		                System.out.println("Insert Failed");
				}
			} 
			
			catch (SQLException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
