package EntranceControllers;

import java.util.Objects;

import application.Main;
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
		
		if((Objects.equals(userNameField.getText(), "admin")) && (Objects.equals(passwordField.getText(), "admin")))
		{
			//System.out.println("here");
			stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/EntranceView/MainMenu.fxml"));
			scene = new Scene(root);
			stage.setScene(scene);
		}
		
		else
		{
			failPasswordLabel.setText("Password/ Username combination incorrect. Please try again.");
			userNameField.setText("");
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
}
