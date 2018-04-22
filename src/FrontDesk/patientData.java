package FrontDesk;

import javafx.beans.property.StringProperty;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.text.MaskFormatter;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

//model
public class patientData {
	private IntegerProperty Id;
	private StringProperty firstName;
	private StringProperty lastName;
	private Date Bdate;
	private StringProperty num;
	private StringProperty address1;
	private StringProperty address2;
	private StringProperty city;
	private StringProperty state;
	private IntegerProperty zip;
	private IntegerProperty refID;
	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	java.util.Date date;
	java.sql.Date sqlDate;
	
	public patientData(int ID,String firstname,String lastname,Date dob,String Num,String address1,String address2,String city,
			String state,int zip,int refID)
	{
		this.Id = new SimpleIntegerProperty(ID);
		this.firstName = new SimpleStringProperty(firstname);
		this.lastName = new SimpleStringProperty(lastname);
		this.Bdate = dob;
		this.num = new SimpleStringProperty(Num);
		this.address1 = new SimpleStringProperty(address1);
		this.address2 = new SimpleStringProperty(address2);
		this.city = new SimpleStringProperty(city);
		this.state = new SimpleStringProperty(state);
		this.zip = new SimpleIntegerProperty(zip);
		this.refID = new SimpleIntegerProperty(refID);
	}
	public int getId()
	{
		return Id.get();
	}
	public void setId(int id)
	{
		Id.set(id);
	}
	public int getRefID()
	{
		return refID.get();
	}
	public void setRefID(int refID)
	{
		this.refID.set(refID);
	}
	public String getFirstName()
	{
		return firstName.get().toString();
	}
	public void setFirstName(String firstname)
	{
		firstName.set(firstname);
	}
	public String getLastName()
	{
		return lastName.get().toString();
	}
	public void setLastName(String lastname)
	{
		lastName.set(lastname);
	}
	public Date getBdate()
	{
		return Bdate;
	}
	public String getAddress1()
	{
		return address1.get();
	}
	public String getAddress2()
	{
		return address2.get();
	}
	public int getZip()
	{
		return zip.get();
	}
	public String getZipAsString()
	{
		return zip.toString();
	}
	public String getNum()
	{
		return num.get();
	}
	public String getState()
	{
		return state.get();
	}
	public void setBdate(String dob) throws ParseException
	{
		
		date = formatter.parse(dob);
		sqlDate = new java.sql.Date(date.getTime());
		Bdate = sqlDate;
		
	}
	public void setNum(String num)
	{
		String phoneMask= "###-###-####";
		MaskFormatter format = null;
		try {
			format = new MaskFormatter(phoneMask);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		format.setValueContainsLiteralCharacters(false);
		try {
			num = format.valueToString(num);
			this.num.set(num);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setAddress1(String addr)
	{
		address1.set(addr);
	}
	public void setAddress2(String addr)
	{
		address2.set(addr);
	}
	public void setCity(String city)
	{
		this.city.set(city);
	}
	public String getCity()
	{
		return city.get();
	}
	public void setState(String state)
	{
		this.state.set(state);
	}
	public void setZip(int zip)
	{
		this.zip.set(zip);
	}
	public IntegerProperty idProperty()
	{
		return Id;
	}
	public IntegerProperty refIdProperty()
	{
		return refID;
	}
	public StringProperty firstNameProperty()
	{
		return firstName;
	}
	public StringProperty lastNameProperty()
	{
		return lastName;
	}
	public StringProperty numProperty()
	{
		return num;
	}
	public StringProperty address1Property()
	{
		return address1;
	}
	public StringProperty address2Property()
	{
		return address2;
	}
	public StringProperty cityProperty()
	{
		return city;
	}
	public StringProperty stateProperty()
	{
		return state;
	}
	public IntegerProperty zipProperty()
	{
		return zip;
	}

}
