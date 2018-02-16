import java.sql.DriverManager;
import java.sql.SQLException;

/*This method creates the SQL Handshake that needs to be conducted before executing queries
 * 
 * */

public class openSQL 
{
	public java.sql.Connection con;
	public java.sql.Statement stmt;

	public java.sql.Connection createConnection()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");  
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/RISystem","root","changeme");  
		}
		catch (Exception e) 
		{
		e.printStackTrace();
		}
		return con;
	}
	public void createTables(java.sql.Connection con) throws SQLException
	{
		stmt=con.createStatement();
		String sql = "CREATE TABLE IF NOT EXISTS login (\n"
                + "	username varchar(500) PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	level int,\n"
                + " password varChar(500)\n"
                + ");";
		stmt.executeUpdate(sql);
		sql="CREATE TABLE IF NOT EXISTS patientInfo (\n"
				+" fname varchar(200),\n"
				+ "lname varchar(200),\n"
				+ "ID int,\n"
				+ "DOB date,\n"
				+ "gender varChar(3)\n"
				+ ");";
		stmt.executeUpdate(sql);
		sql="CREATE TABLE IF NOT EXISTS PACS (\n"
				+" ID int,\n"
				+ "imageDate date,\n"
				+ "image varBinary(max)\n"
				+ ");";
		stmt.executeUpdate(sql);
		sql="CREATE TABLE IF NOT EXISTS billing (\n"
				+" ID int PRIMARY KEY,\n"
				+ "totalAmount long,\n"
				+ "dueDate date,\n"
				+ "insurance varChar(30),\n"
				+ "insureID int\n"
				+ ");";
		stmt.executeUpdate(sql);
	}
}
