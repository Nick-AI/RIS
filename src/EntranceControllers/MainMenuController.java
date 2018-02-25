package EntranceControllers;

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
	@FXML Button ButtonLogOut;
	@FXML Button ButtonBilling;
	

	//used to connect the main class with the controller
	public void setMain(Main M)
	{
		main=M;
	}
	
	public void setUp()
	{
		int Level = LogInController.getLevel();
		// if not admin or billing can not see billing
		if (Level > 1 && Level != 5)
		{
			ButtonBilling.setVisible(false);
		}
		//if not admin or doctor can not see file review
		if (Level > 2)
		{
			ButtonFiles.setVisible(false);
		}
		//if not admin, doc, or nurse can not see imaging or vitals
		if (Level > 3)
		{
			ButtonImaging.setVisible(false);
			ButtonVitals.setVisible(false);
		}
	}
	
	public void registration(ActionEvent event) throws Exception
	{
		System.out.println("registration");
		/*stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

		root = FXMLLoader.load(getClass().getResource("/EntranceView/Registration.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);*/
	}
	
	public void vitals(ActionEvent event) throws Exception
	{
		System.out.println("vitals");
		/*stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

		root = FXMLLoader.load(getClass().getResource("/EntranceView/Vitals.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);*/
	}
	
	public void imaging(ActionEvent event) throws Exception
	{
		System.out.println("imaging");
		/*stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

		root = FXMLLoader.load(getClass().getResource("/EntranceView/Imaging.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);*/
	}
	
	public void files(ActionEvent event) throws Exception
	{
		System.out.println("files");
		/*stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

		root = FXMLLoader.load(getClass().getResource("/EntranceView/Files.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);*/
	}
	
	public void logOut(ActionEvent event) throws Exception
	{
		//System.out.println("logOut");
		stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

		root = FXMLLoader.load(getClass().getResource("/EntranceView/LogIn.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);
	}
	
	public void billing(ActionEvent event) throws Exception
	{
		System.out.println("billing");
		/*stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

		root = FXMLLoader.load(getClass().getResource("/EntranceView/Registration.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);*/
	}
}
