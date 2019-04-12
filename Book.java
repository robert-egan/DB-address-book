package application;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Book{
	
	//Change these variables to reflect your own database
	String databasePath = "your path";
	String databasePasswd = "your password";
	
	ArrayList<Contact> list = new ArrayList<Contact>();
	
	//Set to true when the list is sorted alphabetically
	private boolean a_z;
	public boolean isA_z() {
		return a_z;
	}
	public void setA_z(boolean a_z) {
		this.a_z = a_z;
	}

	//Using the book class to communicate with the database
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	
	private void close() {
		//Close function for finally clause
		try {
			if (connect != null) {
				connect.close();
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void connectToDB() throws Exception {
		try {
			// Step 1. This will load the MySQL driver. Each DB has its own driver
			//.cj. was added in the latest addition
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//Step 2. Set up the connection with the DB
			connect = DriverManager.getConnection("jdbc:mysql://localhost/contactsdb?"
					+ "useLegacyDatetimeCode=false&serverTimezone=UTC"
					+ " &user=root&password=@ppleFritter27");
			readAllContacts();
			
			
		}catch (Exception e) {
			throw e;
		}finally {
			close();
		}
	}
	
	public void deleteContact(Contact contact) throws Exception {
		/*
		 * When passed a Contact, function will connect to database and send an SQL query which will
		 * delete the Contact from the db
		 */
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/contactsdb?"
					+ "useLegacyDatetimeCode=false&serverTimezone=UTC"
					+ " &user=root&password=@ppleFritter27");
			preparedStatement = connect.prepareStatement("delete from contactsdb.contacts where name=?");
			preparedStatement.setString(1, contact.getName());
			preparedStatement.executeUpdate();
		}catch(Exception e) {
			throw e;
		}finally {
			close();
		}
	}
	
	public void insertContact(Contact contact) throws Exception{
		try {
			//Use PreparedStatement when inserting with variables.
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/contactsdb?"
					+ "useLegacyDatetimeCode=false&serverTimezone=UTC"
					+ " &user=root&password=@ppleFritter27");
			
			preparedStatement = connect.prepareStatement("insert into contactsdb.contacts values "
					+ "(default, ?, ?, ?)");
			preparedStatement.setString(1, contact.getName());
			preparedStatement.setString(2, contact.getAddress());
			preparedStatement.setString(3, contact.getPhone());
			preparedStatement.executeUpdate();
		}catch(Exception e) {
			throw e;
		}finally {
			close();
		}
	}
	
	
	public void readAllContacts() throws Exception{
		try {
			// Step 3. Statements allow us to send SQL queries to the DB.
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from contactsdb.contacts order by name");
			writeResultSet(resultSet);
		}catch(Exception e) {
			throw e;
		}
	}
	public void reverseSortContacts() {
		//Sort the array from z to a
		Object[] array = list.toArray();
		int n = array.length;
		Contact temp = new Contact();
		for(int i=0; i<n;i++) {
			for(int j=i+1;j<n;j++) {
				Contact c1 = (Contact)array[i];
				Contact c2 = (Contact)array[j];
				if(c1.compareTo(c2)<0) {
					temp = c1;
					array[i]=c2;
					array[j]=temp;
				}
			}
		}
		list.clear();
		for(int i=0;i<array.length;i++) {
			list.add(i, (Contact) array[i]);
		}
		this.setA_z(false);
	}
	public void sortContacts() {
		//sorts the array from a to z
		Object[] array = list.toArray();
		int n = array.length;
		Contact temp = new Contact();
		for(int i=0; i<n;i++) {
			for(int j=i+1;j<n;j++) {
				Contact c1 = (Contact)array[i];
				Contact c2 = (Contact)array[j];
				if(c1.compareTo(c2)>0) {
					temp = c1;
					array[i]=c2;
					array[j]=temp;
				}
			}
		}
		list.clear();
		for(int i=0;i<array.length;i++) {
			list.add(i, (Contact) array[i]);
		}
		this.setA_z(true);
	}

	
	private void writeResultSet(ResultSet resultSet) throws SQLException{
		//ResultSet holds our results
		while(resultSet.next()) {
			String name = resultSet.getString("name");
			String address = resultSet.getString("address");
			String phone = resultSet.getString("phone");
			Contact temp = new Contact();
			temp.setName(name);
			temp.setAddress(address);
			temp.setPhone(phone);
			list.add(temp);
		}
	}
	
	
	
}