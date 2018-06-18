package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Record;
import util.DataAccessObject;

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

	private ArrayList<Record> records;
	
	//private  ObservableList<String> recordData = FXCollections.observableArrayList();
	
	private DataAccessObject doa;

	public AdminController() {
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("id"));
		fullNameColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("fullName"));
		
		Record record = new Record();
		record.setMessage("all");
		records = new ArrayList<>();
		records.add(record);
		doa = new DataAccessObject(records);
		records = new ArrayList<>();
		try {
			records = doa.buildQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//recordTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		recordTable.setItems(FXCollections.observableArrayList(records));
		recordTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Record>() {

		    @Override
		    public void changed(ObservableValue<? extends Record> observable,
		    		Record oldValue, Record newValue) {
		      showRecords(newValue);
		    }
		    private void showRecords(Record newValue) {
				userName.setText(newValue.getUserName());
				password.setText(newValue.getPassword());
				fullName.setText(newValue.getFullName());
				email.setText(newValue.getEmail());
				phoneNumber.setText(newValue.getPhoneNumber());
				gender.setText(newValue.getGender());
				type.setText(newValue.getType());	
			}

			
		  });
	}
	
	

}
