package application;

public class Contact implements Comparable<Contact>{
	
	private String name;
	private String address;
	private String phone;
	
	//Getters and Setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	//compareTo function to implement Comparable
	@Override public int compareTo(Contact other) {
		return this.getName().compareToIgnoreCase(other.getName());
	}
	
}