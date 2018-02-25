package EntranceControllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import application.Main;
import application.openSQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LogInController 
{
	private Main main;
	Stage stage;
	Scene scene;
	Parent root;
	
	static String UserName, Password, Name;
	static int Level;
	
	@FXML Button ButtonLogIn;
	@FXML TextField userNameField;
	@FXML PasswordField passwordField;
	@FXML Label failPasswordLabel;

	//used to connect the main class with the controller
	public void setMain(Main M)
	{
		main=M;
	}
	
	public void logIn(ActionEvent event) throws Exception
	{
		//used to test login
		//System.out.println("U: " + userNameField.getText() + " " + (userNameField.getText() == "admin"));
		//System.out.println("P: " + passwordField.getText() + " " + (passwordField.getText() == "admin"));
		//if((Objects.equals(userNameField.getText(), "admin")) && (Objects.equals(passwordField.getText(), "admin")))
		//{
			//System.out.println("here");
			//stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
			//root = FXMLLoader.load(getClass().getResource("/EntranceView/MainMenu.fxml"));
			//scene = new Scene(root);
			//stage.setScene(scene);
		//}
		
		//else
		//{
			//failPasswordLabel.setText("Password/ Username combination incorrect. Please try again.");
			//userNameField.setText("");
		//}
		
		//****************************************************************************************************
		//checks login 
		UserName = userNameField.getText();
		Password = passwordField.getText();
		try 
		{
			String q1 = "select * from login WHERE username = '"+UserName+"' AND password = '"+Password+"';";
			ResultSet rs= openSQL.stmt.executeQuery(q1);
			
			if (rs.next())
			{
				Level = rs.getInt(3);
				Name = rs.getString(2);
				stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
				root = FXMLLoader.load(getClass().getResource("/EntranceView/MainMenu.fxml"));
				scene = new Scene(root);
				stage.setScene(scene);
				//MainMenuController.ButtonVitals.setVisible(false);
				System.out.println(Level+Name);
			}
			else
			{
				failPasswordLabel.setText("Password/ Username combination incorrect. Please try again.");
				userNameField.setText("");
			}
		}
		
		catch (SQLException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
		
	public void newAccount(ActionEvent event) throws Exception
	{
					//System.out.println("here");
			stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/EntranceView/NewEmployeeAccount.fxml"));
			scene = new Scene(root);
			stage.setScene(scene);
	}
			
			
			
	public void recover(ActionEvent event) throws Exception
	{
		//System.out.println("here");
		stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("/EntranceView/RecoverEmployeeAccount.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);
		
	}

	//getters and setters
	public static String getUserName() 
	{
		return UserName;
	}

	public static void setUserName(String userName) 
	{
		UserName = userName;
	}

	public static String getPassword() 
	{
		return Password;
	}

	public static void setPassword(String password) 
	{
		Password = password;
	}

	public static String getName() 
	{
		return Name;
	}

	public static void setName(String name) 
	{
		Name = name;
	}

	public static int getLevel() 
	{
		return Level;
	}

	public static void setLevel(int level) 
	{
		Level = level;
	}
	
	
}
