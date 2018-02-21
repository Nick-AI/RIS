package application;

import java.sql.ResultSet;
import java.sql.SQLException;

import EntranceControllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application 
{
	
	//public static java.sql.Connection con;
	//public static java.sql.Statement stmt;
	@Override
	public void start(Stage primaryStage) 
	{
		
		//connects to sql and creates tables if they don't already exist
		java.sql.Connection con=openSQL.createConnection();
		try 
		{
			openSQL.createTables(con);
		} 
		catch (SQLException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//created admin account if it does not already exist
		try 
		{
			String q1 = "select * from login WHERE username = 'admin' AND password = 'admin';";
			ResultSet rs= openSQL.stmt.executeQuery(q1);
			
			if (rs.next())
			{
				System.out.println("User id is already registered");
			}
			else
			{
				// Inserting data in database
	            String q2 = "insert into login values('" +"admin"+ "', '" +"admin"+ 
	                                  "', '" +1+ "', '" +"admin"+ "');";
	            int x = openSQL.stmt.executeUpdate(q2);
	            if (x > 0)            
	                System.out.println("Successfully Inserted");            
	            else           
	                System.out.println("Insert Failed");
			}
		} 
		
		catch (SQLException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		//starts first fxml pane
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