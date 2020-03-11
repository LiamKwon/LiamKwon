package stocktaking.master;

import java.sql.Date;

public class Product{
	
	private String bms;
	private String location;
	private Date date;
	private String user;
	public Product() {
		
		this.bms ="";
		this.location ="";
		this.date =null;
		this.user ="";
	}
	
	public Product(String bms, String location, Date date,String user) {
		
		this.bms = bms;
		this.location =location;
		this.date =date;
		this.user =user;
	}
	
	public String getBms() {
		
		return bms;
	}
	
	public void setBms(String bms) {
		
		this.bms=bms;
		
	}
	
	public String getLocation() {
		
		return location;
	}
	
	public void setLocation(String location) {
		
		this.location=location;
		
	}
	public Date getDate() {
		
		return date;
	}
	
	public void setDate(Date date2) {
		
		this.date=date2;
		
	}
	public String getUser() {
		
		return user;
	}
	
	public void setUser(String user) {
		
		this.user=user;
		
	}
	
	
	
}