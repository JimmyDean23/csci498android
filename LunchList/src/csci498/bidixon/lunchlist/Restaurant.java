/**
 * @author Billy Dixon
 * @version 0.1.9
 */

package csci498.bidixon.lunchlist;

/*
 * Restaurant class contains information about a certain
 * restaurant such as name, address, etc.
 */
public class Restaurant {
	
	private String name;
	private String address;
	private String type;
	private String notes;
	
	public String toString() { return getName(); }
	
	public String getName() 	{ return name; }
	public String getAddress() 	{ return address; }
	public String getType() 	{ return type; }
	public String getNotes() 	{ return notes; }
	
	public void setName(String name) 		{ this.name = name; }
	public void setAddress(String address) 	{ this.address = address; }
	public void setType(String type) 		{ this.type = type; }
	public void setNotes(String notes) 		{ this.notes = notes; }
	
}
