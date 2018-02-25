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

public class RecoverEmployeeAccountController 
{
	private Main main;
	Stage stage;
	Scene scene;
	Parent root;
	
	@FXML Button ButtonBack;
	@FXML Button submitButton;
	@FXML TextField userNameField;
	@FXML Label failLabel;
	@FXML TextField adminUserNameField;
	@FXML PasswordField adminPasswordField;

	//used to connect the main class with the controller
	public void setMain(Main M)
	{
		main=M;
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
		String AdminUserName = adminUserNameField.getText();
		String AdminPassword = adminPasswordField.getText();
		String Password = " ";
		int adminLevel = 0;
		boolean check1 = false;
		boolean check2 = false;
		boolean check3 = false;
		boolean check4 = false;
		failLabel.setText("");
		
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
				failLabel.setText(failLabel.getText() + " An admin is required to recover password.");
			}
		}
		if((check1 == true) && (check2 == true))
		{
			//if the username exist the password will be returned
			try 
			{
				String q1 = "select * from login WHERE username = '"+UserName+"';";
				ResultSet rs= openSQL.stmt.executeQuery(q1);
				
				if (rs.next())
				{
					Password = rs.getString(4);
					
					failLabel.setText("The password is: " + Password);
				}
				else
				{
					failLabel.setText("This username does not exist");
					userNameField.setText("");
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
