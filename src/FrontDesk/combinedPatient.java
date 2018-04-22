package FrontDesk;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class combinedPatient {
	private StringProperty Proc;
	private StringProperty Time;
	private StringProperty Mod;
	private IntegerProperty Patid;
	private IntegerProperty Procid;
	private IntegerProperty Ordid;
	
	public combinedPatient(String proc,String time,String mod,int id1,int id2,int id3)
	{
		this.Proc = new SimpleStringProperty(proc);
		this.Time = new SimpleStringProperty(time);
		this.Mod = new SimpleStringProperty(mod);
		this.Patid = new SimpleIntegerProperty(id1);
		this.Procid = new SimpleIntegerProperty(id2);
		this.Ordid = new SimpleIntegerProperty(id3);
	}
	
	public String getProc()
	{
		return Proc.get();
	}
	
	public String getTime()
	{
		return Time.get();
	}
	
	public String getMod()
	{
		return Mod.get();
	}
	
	public Integer getPatid()
	{
		return Patid.get();
	}
	
	public Integer getProcid()
	{
		return Procid.get();
	}
	
	public Integer getOrdid()
	{
		return Ordid.get();
	}
	
	public void setProc(String p)
	{
		Proc.set(p);
	}
	
	public void setTime(String t)
	{
		Time.set(t);
	}
	
	public void setMod(String p)
	{
		Mod.set(p);
	}
	
	public void setPatid(int r)
	{
		Patid.set(r);
	}
	
	public void setProcid(int t)
	{
		Procid.set(t);
	}
	
	public void setOrdid(int t)
	{
		Ordid.set(t);
	}
	
	public StringProperty procProperty()
	{
		return Proc;
	}
	
	public StringProperty timeProperty()
	{
		return Time;
	}
	
	public StringProperty modProperty()
	{
		return Mod;
	}
	
	public IntegerProperty patidProperty()
	{
		return Patid;
	}
	
	public IntegerProperty procidProperty()
	{
		return Procid;
	}
	
	public IntegerProperty ordidProperty()
	{
		return Ordid;
	}
}
