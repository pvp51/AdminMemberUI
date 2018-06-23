package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.Client;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Record;

public class AdminController implements Initializable{

	@FXML
	private TableView<Record> recordTable;
	@FXML
	private TableColumn<Record, String> idColumn;
	@FXML
	private TableColumn<Record, String> fullNameColumn;
	@FXML
	private TextField userName= new TextField();
	@FXML
	private TextField password= new TextField();
	@FXML
	private TextField fullName= new TextField();
	@FXML
	private TextField email= new TextField();
	@FXML
	private TextField phoneNumber= new TextField();
	@FXML
	private TextField gender= new TextField();
	@FXML
	private TextField type= new TextField();
	@FXML
	private Button deleteButton;
	@FXML
	private Button clearButton;
	@FXML
	private Button newButton;
	@FXML
	private Button editButton;

	private ArrayList<Record> records;


	public AdminController() {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("id"));
		fullNameColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("fullName"));

		loadAllRecords();

		recordTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Record>() {

			@Override
			public void changed(ObservableValue<? extends Record> observable,
					Record oldValue, Record newValue) {
				showRecords(newValue);
			}

		});
	}

	@FXML
	private void newRecord(ActionEvent event) throws SQLException{
		Record record = new Record();

		if(validateFields()){
			record.setMessage("insert");
			record.setEmail(email.getText());
			record.setUserName(userName.getText());
			record.setPassword(password.getText());
			record.setGender(gender.getText());
			record.setType(type.getText());
			record.setPhoneNumber(phoneNumber.getText());
			record.setFullName(fullName.getText());
			records = new ArrayList<>();
			records.add(record);
			Client client = new Client();
			client.startClient(records);
			//Refreshing table values
			loadAllRecords();
		}
		else{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Please fill out all fields");
			alert.setTitle("Oops");
			alert.setHeaderText("Something Wrong :)");
			alert.show();
		}
	}
	
	@FXML
	private void editRecord(ActionEvent event) throws SQLException{
		Record record = new Record();
		record = recordTable.getSelectionModel().getSelectedItem();

		if(validateFields()){
			record.setMessage("update");
			record.setEmail(email.getText());
			record.setUserName(userName.getText());
			record.setPassword(password.getText());
			record.setGender(gender.getText());
			record.setType(type.getText());
			record.setPhoneNumber(phoneNumber.getText());
			record.setFullName(fullName.getText());
			records = new ArrayList<>();
			records.add(record);
			Client client = new Client();
			client.startClient(records);
			//Refreshing table values
			loadAllRecords();
		}
		else{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Please dont leave any fields empty");
			alert.setTitle("Oops");
			alert.setHeaderText("Something Wrong :)");
			alert.show();
		}
	}


	private boolean validateFields() {
		if(userName.getText() != null && !userName.getText().isEmpty() &&
				password.getText() != null && !password.getText().isEmpty() &&
				fullName.getText() != null && !fullName.getText().isEmpty() &&
				email.getText() != null && !email.getText().isEmpty() &&
				phoneNumber.getText() != null && !phoneNumber.getText().isEmpty() &&
				gender.getText() != null && !gender.getText().isEmpty() &&
				type.getText() != null && !type.getText().isEmpty()){
			return true;
		}
		else{
			return false;
		}
	}

	@FXML
	private void deleteRecord(ActionEvent event) throws SQLException{
		Record record = recordTable.getSelectionModel().getSelectedItem();
		record.setMessage("delete");
		records = new ArrayList<>();
		records.add(record);
		Client client = new Client();
		client.startClient(records);
		//Refreshing table values
		loadAllRecords();
	}

	@FXML
	private void clearFields(ActionEvent event) throws SQLException{
		recordTable.getSelectionModel().clearSelection();
		userName.setText("");
		password.setText("");
		fullName.setText("");
		email.setText("");
		phoneNumber.setText("");
		gender.setText("");
		type.setText("");
	}
	
	/**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setContentText("Author: Parth Patel");
    	alert.setTitle("About");
    	alert.setHeaderText("CS602App");
    	alert.show();
    }

	private void showRecords(Record newValue) {
		if(newValue!= null){
			userName.setText(newValue.getUserName());
			password.setText(newValue.getPassword());
			fullName.setText(newValue.getFullName());
			email.setText(newValue.getEmail());
			phoneNumber.setText(newValue.getPhoneNumber());
			gender.setText(newValue.getGender());
			type.setText(newValue.getType());
		}
		else{
			userName.setText("");
			password.setText("");
			fullName.setText("");
			email.setText("");
			phoneNumber.setText("");
			gender.setText("");
			type.setText("");
		}
	}

	private void loadAllRecords() {
		Record record = new Record();
		record.setMessage("all");
		records = new ArrayList<>();
		records.add(record);
		Client client = new Client();
		records = client.startClient(records);

		recordTable.setItems(FXCollections.observableArrayList(records));
	}



}
