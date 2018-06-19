package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Record;

public class MemberController implements Initializable{

	@FXML
	private TableView<Record> recordTable;
	@FXML
	private TableColumn<Record, String> idColumn;
	@FXML
	private TableColumn<Record, String> fullNameColumn;
	@FXML
	private TableColumn<Record, String> emailColumn;
	@FXML
	private TableColumn<Record, String> phoneNumberColumn;
	@FXML
	private TableColumn<Record, String> genderColumn;
	@FXML
	private TableColumn<Record, String> typeColumn;
	@FXML
	private TextField filterField = new TextField();
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
	private Button submitButton;
	
	private ObservableList<Record> recordData;
	private ArrayList<Record> records;
	public static ArrayList<Record> currentRecords;


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		idColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("id"));
		fullNameColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("fullName"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("email"));
		phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("phoneNumber"));
		genderColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("gender"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("type"));

		setCurrentInfo();
		loadAllRecords();
		filterSortlogic();
	
	}

	private void filterSortlogic() {
		FilteredList<Record> filteredData = new FilteredList<>(recordData, p -> true);

		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(record -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String lowerCaseFilter = newValue.toLowerCase();

				if (record.getFullName().toLowerCase().contains(lowerCaseFilter)) {
					return true; 
				} 
//				else if (record.getType().toLowerCase().contains(lowerCaseFilter)) {
//					return true; 
//				}
				return false; 
			});
		});
 
        SortedList<Record> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(recordTable.comparatorProperty());
        recordTable.setItems(sortedData);
		
	}

	private void setCurrentInfo() {
		Record rec = currentRecords.get(0);
		userName.setText(rec.getUserName());
		password.setText(rec.getPassword());
		fullName.setText(rec.getFullName());
		email.setText(rec.getEmail());
		phoneNumber.setText(rec.getPhoneNumber());
		gender.setText(rec.getGender());
		type.setText(rec.getType());
	}

	@FXML
	private void submitChanges(ActionEvent event) throws SQLException{
		Record record = new Record();
		record.setId(currentRecords.get(0).getId());
		if(validateFields()){
			record.setMessage("update");
			record.setEmail(email.getText());
			record.setUserName(userName.getText());
			record.setPassword(password.getText());
			record.setGender(gender.getText());
			record.setType(type.getText());
			record.setPhoneNumber(phoneNumber.getText());
			record.setFullName(fullName.getText());
			currentRecords = new ArrayList<>();
			currentRecords.add(record);
			Client client = new Client();
			client.startClient(currentRecords);
			//Refreshing table values
			setCurrentInfo();
			loadAllRecords();
			filterSortlogic();
		}
		else{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Please dont leave any fields empty");
			alert.setTitle("Oops");
			alert.setHeaderText("Something Wrong :)");
			alert.show();
		}
	}
	private void loadAllRecords() {
		Record record = new Record();
		record.setMessage("all");
		records = new ArrayList<>();
		records.add(record);
		Client client = new Client();
		records = client.startClient(records);
		recordData = FXCollections.observableArrayList(records);

		//recordTable.setItems(FXCollections.observableArrayList(recordData));
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

}
