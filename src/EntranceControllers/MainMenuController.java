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
	@SuppressWarnings("unused")
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
	@FXML Button ButtonReferring;
	

	//used to connect the main class with the controller
	public void setMain(Main M)
	{
		main=M;
	}
	
	public void setUp()
	{
		
		int Level = LogInController.getLevel();
		ButtonRegistration.setVisible(false);
		ButtonVitals.setVisible(false);
		ButtonImaging.setVisible(false);
		ButtonFiles.setVisible(false);
		ButtonLogOut.setVisible(false);
		ButtonBilling.setVisible(false);
		ButtonReferring.setVisible(false);
		System.out.println(Level);
		
		//admin
		if (Level == 1)
		{
			ButtonRegistration.setVisible(true);
			ButtonVitals.setVisible(true);
			ButtonImaging.setVisible(true);
			ButtonFiles.setVisible(true);
			ButtonLogOut.setVisible(true);
			ButtonBilling.setVisible(true);
			ButtonReferring.setVisible(true);
		}
		
		//Doctor
		else if (Level == 2)
		{
			//ButtonRegistration.setVisible(true);
			//ButtonVitals.setVisible(true);
			//ButtonImaging.setVisible(true);
			ButtonFiles.setVisible(true);
			ButtonLogOut.setVisible(true);
			//ButtonBilling.setVisible(true);
			//ButtonReferring.setVisible(true);
		}
		
		//Nurse
		else if (Level == 3)
		{
			//ButtonRegistration.setVisible(true);
			ButtonVitals.setVisible(true);
			//ButtonImaging.setVisible(true);
			//ButtonFiles.setVisible(true);
			ButtonLogOut.setVisible(true);
			//ButtonBilling.setVisible(true);
			//ButtonReferring.setVisible(true);
		}
		
		//Clerk
		else if (Level == 4)
		{
			ButtonRegistration.setVisible(true);
			//ButtonVitals.setVisible(true);
			//ButtonImaging.setVisible(true);
			//ButtonFiles.setVisible(true);
			ButtonLogOut.setVisible(true);
			ButtonBilling.setVisible(true);
			//ButtonReferring.setVisible(true);
		}
		
		//Referring
		else if (Level == 5)
		{
			//ButtonRegistration.setVisible(true);
			//ButtonVitals.setVisible(true);
			//ButtonImaging.setVisible(true);
			//ButtonFiles.setVisible(true);
			ButtonLogOut.setVisible(true);
			//ButtonBilling.setVisible(true);
			ButtonReferring.setVisible(true);
		}
		
		//Tech
		else if (Level == 6)
		{
			//ButtonRegistration.setVisible(true);
			//ButtonVitals.setVisible(true);
			ButtonImaging.setVisible(true);
			//ButtonFiles.setVisible(true);
			ButtonLogOut.setVisible(true);
			//ButtonBilling.setVisible(true);
			//ButtonReferring.setVisible(true);
		}
		
	}
	
	public void registration(ActionEvent event) throws Exception
	{
		//FrontDesk.FrontDesk.startFD();
		System.out.println("registration");
		stage = (Stage)	((Button)	event.getSource()).getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("/FrontDeskView/FrontDeskHome.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);
	}
	
	public void vitals(ActionEvent event) throws Exception
	{
		System.out.println("vitals");
		stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

		root = FXMLLoader.load(getClass().getResource("/Vitals/VitalsView.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);
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
		//System.out.println("files");
		stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

		root = FXMLLoader.load(getClass().getResource("/Radiologist/RadView2.0.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);
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
		Billing.billing.billingStart();
		/*stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

		root = FXMLLoader.load(getClass().getResource("/EntranceView/Registration.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);*/
	}
	
	public void referring(ActionEvent event) throws Exception
	{
		System.out.println("referring");
		referring.referringPhysician.referringStart();
		/*stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

		root = FXMLLoader.load(getClass().getResource("/EntranceView/Files.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);*/
	}
}
