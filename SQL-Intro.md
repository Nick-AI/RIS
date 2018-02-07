# SQL Language
Using a SQL database for the RIS will allow us to retrieve photos, information, and data that is needed for the system to function. 
In Java the use of a JConnector is needed to hook up the code to the SQL Server. For now I recommend that we ignore that process
and focus on how to code java to connect to the server.

## Basic Query
The heart of SQL is based on its query. After establishing tables (which we will do systematically written into the login screen
class), queries can be used to select specific data from the tables. 

Example: If I have a table of patient information and I wanted to just display the row of a specific patient by their lastname the 
query would look like this:

- SELECT * FROM patientTable where lName='Smith"

This query will reply with each row where the last name value is smith.
For more info on queries go to this link : (https://technet.microsoft.com/en-us/library/bb264565%28v=sql.90%29.aspx)

# Result Sets and Statements
SQL queries in java work rather simple. Follow the steps below to do this (Assuming that JConnector is setup):
- create a statement by writing a line of code that says

<public static Statement stmt;> (this is a public variable for the entire class to reuse)
<stmt=con.createStatement();> (the con stands for connection which is done during the JConnector process)

- Formaulate your query by assigning it to a string first (see below for example)

String sql = "CREATE TABLE IF NOT EXISTS login (\n"
			                + "	username varchar(500) PRIMARY KEY,\n"
			                + "	name text NOT NULL,\n"
			                + "	admin boolean,\n"
			                + " password varChar(500)\n"
			                + ");";
                      
- Execute Update (When you want to create a table or update a table's value use the executeUpdate method

stmt.executeUpdate(sql)

- execute Query (When you want to create a query on a table

stmt.executeQuery("Select * from patientTable where lName='Smith');

As long as the query syntax is correct then the query will return correctly

- ResultSets

Result Sets allow you to play around with the returned values by assigning it to one variable. The syntax is pretty simple
and involves a try and catch clause. This is where you put the execute query.
try {
					ResultSet deny=stmt2.executeQuery("select * from denied where ID="+IDnum);
				}
catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

After this you can use the ResultSet to assign dynamic variables to static variables in the code.
while(deny.next())
				{
					firstName=deny.getString("fname");
					lastName= deny.getString("lname");
				}
