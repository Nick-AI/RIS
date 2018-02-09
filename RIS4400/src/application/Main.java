package application;

import EntranceControllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application 
{

	@Override
	public void start(Stage primaryStage) 
	{
		try 
		{
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/EntranceView/Start.fxml"));
			Scene scene = new Scene(root,720,720);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			// we need to give the controller access to the Main app.
			MainController controller=new MainController();
			controller.setMain(this);;
			primaryStage.show();

		} 
		
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}


	public static void main(String[] args) 
	{
		launch(args);
	}
}