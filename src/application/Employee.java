package application;

public class Employee 
{
	String UserName, Password, FirstName, LastName;
	int Level;
	
	public Employee(String userName, String password, String firstName, String lastName, int level)
	{
		UserName = userName;
		Password = password;
		FirstName = firstName;
		LastName = lastName;
		Level = level;
	}

	public String getUserName() 
	{
		return UserName;
	}

	public void setUserName(String userName) 
	{
		UserName = userName;
	}

	public String getPassword() 
	{
		return Password;
	}

	public void setPassword(String password) 
	{
		Password = password;
	}

	public String getFirstName() 
	{
		return FirstName;
	}

	public void setFirstName(String firstName) 
	{
		FirstName = firstName;
	}

	public String getLastName() 
	{
		return LastName;
	}

	public void setLastName(String lastName) 
	{
		LastName = lastName;
	}

	public int getLevel() 
	{
		return Level;
	}

	public void setLevel(int level) 
	{
		Level = level;
	}

	public String toString() 
	{
		return "Employee [UserName=" + UserName + ", Password=" + Password + ", FirstName=" + FirstName + ", LastName="
				+ LastName + ", Level=" + Level + "]";
	}
}
