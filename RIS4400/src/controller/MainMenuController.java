package controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController 
{
	private Main main;
	Stage stage;
	Scene scene;
	Parent root;
	
	@FXML Button ButtonRegistration;
	@FXML Button ButtonVitals;
	@FXML Button ButtonImaging;
	@FXML Button ButtonFiles;

	//used to connect the main class with the controller
	public void setMain(Main M)
	{
		main=M;
	}
	
	public void registration(ActionEvent event) throws Exception
	{
		System.out.println("registration");
		/*stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

		root = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);*/
	}
	
	public void vitals(ActionEvent event) throws Exception
	{
		System.out.println("vitals");
		/*stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

		root = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);*/
	}
	
	public void imaging(ActionEvent event) throws Exception
	{
		System.out.println("imaging");
		/*stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

		root = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);*/
	}
	
	public void files(ActionEvent event) throws Exception
	{
		System.out.println("files");
		/*stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

		root = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);*/
	}
}
