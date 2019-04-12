package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	
	//Declarations
	Label contactList;
	Label message;
	TextField index;
	TextField name;
	TextField address;
	TextField phoneNumber;
	Button add;
	Button delete;
	Button update;
	Button view;
	Button save;
	Button sort;
	BorderPane root;
	SplitPane split;
	VBox controls;
	GridPane buttons;
	Book contacts;
	
	
	
	private void setDimensions() {
		contactList.setPrefSize(250, 400);
		controls.setPrefSize(350, 400);
		buttons.setPrefSize(350, 200);
		message.setPrefSize(350, 60);
		index.setPrefSize(350, 35);
		name.setPrefSize(350, 35);
		address.setPrefSize(350, 35);
		phoneNumber.setPrefSize(350, 35);
		add.setPrefSize(103, 85);
		delete.setPrefSize(103, 85);
		update.setPrefSize(103, 85);
		view.setPrefSize(103, 85);
		save.setPrefSize(103, 85);
		sort.setPrefSize(103, 85);
	}
	
	private void attachCode() {
		add.setOnAction(e-> btncode(e));
		delete.setOnAction(e-> btncode(e));
		update.setOnAction(e-> btncode(e));
		view.setOnAction(e-> btncode(e));
		save.setOnAction(e-> btncode(e));
		sort.setOnAction(e-> btncode(e));
		
	}
	
	private void btncode(ActionEvent e) {
		
		try {
			if (e.getSource() == add) {
				Contact temp = new Contact();
				temp.setName(name.getText());
				temp.setAddress(address.getText());
				temp.setPhone(phoneNumber.getText());
				contacts.insertContact(temp);
				contacts.list.add(temp);
				contacts.sortContacts();
				displayContacts();
			}
			else if (e.getSource() == delete) {
				int num = Integer.parseInt(index.getText());
				contacts.deleteContact(contacts.list.get(num));
				contacts.list.remove(num);
				if(contacts.list.size()>0) {
					contacts.sortContacts();
					displayContacts();
				}
			
			}
			else if (e.getSource() == update) {
				int num = Integer.parseInt(index.getText());
				name.setText(contacts.list.get(num).getName());
				address.setText(contacts.list.get(num).getAddress());
				phoneNumber.setText(contacts.list.get(num).getPhone());
				message.setText("Viewing contact: " + contacts.list.get(num).getName() + "\n" + 
						"Press 'save' when ready to update contact info");
			}
			else if(e.getSource( ) == sort) {
				if(!contacts.isA_z()) {
					contacts.sortContacts();
				}
				else {
					contacts.reverseSortContacts();
				}
				displayContacts();
			
			}
			else if(e.getSource() == view) {
				Contact temp = new Contact();
				int num = Integer.parseInt(index.getText());
				temp = contacts.list.get(num);
				message.setText(temp.getName() + "\n" + temp.getAddress() + "\n" + temp.getPhone());
			}
			else if (e.getSource() == save) {
				int num = Integer.parseInt(index.getText());
				contacts.deleteContact(contacts.list.get(num));
				contacts.list.get(num).setName(name.getText());
				contacts.list.get(num).setAddress(address.getText());
				contacts.list.get(num).setPhone(phoneNumber.getText());
				contacts.insertContact(contacts.list.get(num));
				contacts.sortContacts();
				displayContacts();
			}
			else {
				index.setText("Index");
				name.setText("Name");
				address.setText("Address");
				phoneNumber.setText("Phone");
				if (contacts.list.size()>0) {
					displayContacts();
				}
			}
		}catch(Exception x) {
			x.printStackTrace();
		}
	}
	private void displayContacts() {
		String display = "Your Contacts";
		contactList.setText(display);
		for (int i=0;i<contacts.list.size();i++) {
			 display += "\n" + i + ": " + contacts.list.get(i).getName();
		}
		contactList.setText(display);
	}
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//Instantiations
			contactList = new Label("Your contacts: ");
			message = new Label("Hello");
			index = new TextField("Index");
			name = new TextField("Name");
			address = new TextField("Address");
			phoneNumber = new TextField("Phone");
			add = new Button("Add" + "\n" + "Contact");
			delete = new Button("Delete" + "\n" + "Contact");
			update = new Button("Update" + "\n" + "Contact");
			view = new Button("View" + "\n" + "Contact");
			save = new Button("Save");
			sort = new Button("A<>Z");
			
			
			root = new BorderPane();
			split = new SplitPane();
			controls = new VBox();
			buttons = new GridPane();
			
			
			contacts = new Book();
			contacts.connectToDB();
			
			
			
			root.setCenter(split);
			buttons.setAlignment(Pos.CENTER);
			split.getItems().add(0,contactList);
			split.getItems().add(1,controls);
			contactList.setAlignment(Pos.TOP_LEFT);
			controls.getChildren().add(0, message);
			controls.getChildren().add(1, index);
			controls.getChildren().add(2, name);
			controls.getChildren().add(3, address);
			controls.getChildren().add(4, phoneNumber);
			controls.getChildren().add(5, buttons);
			buttons.setHgap(10);
			buttons.setVgap(10);
			buttons.add(add, 0, 0);
			buttons.add(delete, 0, 1);
			buttons.add(update, 1, 0);
			buttons.add(view, 1, 1);
			buttons.add(sort, 2, 0);
			buttons.add(save, 2, 1);
			
			setDimensions();
			if(contacts.list.size()>0) {
				displayContacts();
			}
			attachCode();
			
			
			
			
			
			
			Scene scene = new Scene(root,620,420);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
