package controller;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Record;

public class LoginController implements Initializable{

	@FXML
	private TextField userName;
	@FXML
	private PasswordField password;

	@FXML
	private RadioButton member;
	@FXML
	private RadioButton admin;
	@FXML
	private Button loginButton;

	private ToggleGroup group;
	private Stage primaryStage;
	private BorderPane rootLayout;

	private ArrayList<Record> records;
	public LoginController() {

	}

	@FXML
	private void attemptLogin(ActionEvent event) throws SQLException{
		this.primaryStage = (Stage) loginButton.getScene().getWindow();

		if(checkCredentials()){
			initRootLayout();
			if("Member".equalsIgnoreCase(group.getSelectedToggle().getUserData().toString())) {
				showMemberScene();
			}
			else{
				showAdminScene();
			}
		}
		else{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Incorrect username/password or selection");
			alert.setTitle("Oops");
			alert.setHeaderText("Something Wrong :)");
			alert.show();
		}
	}

	private boolean checkCredentials() throws SQLException {
		Record record = new Record();
		record.setMessage("login");
		record.setUserName(userName.getText());
		record.setPassword(password.getText());
		records = new ArrayList<>();
		records.add(record);

		Client client = new Client();
		records = client.startClient(records);

		return records == null || records.size() == 0 || !records.get(0).getType().equalsIgnoreCase(group.getSelectedToggle().getUserData().toString())? false : true;
	}

	private void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Client.class.getResource("/view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void showMemberScene() {
		try {
			// Load the fxml file and set into the center of the main layout
			FXMLLoader loader = new FXMLLoader(Client.class.getResource("/view/Member.fxml"));
			
			MemberController.currentRecords =  records;
			
			AnchorPane overviewPage = (AnchorPane) loader.load();
			rootLayout.setCenter(overviewPage);

		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}
	}

	private void showAdminScene() {
		try {
			// Load the fxml file and set into the center of the main layout
			FXMLLoader loader = new FXMLLoader(Client.class.getResource("/view/Admin.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			rootLayout.setCenter(overviewPage);

		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		group = new ToggleGroup();

		member.setToggleGroup(group);
		member.setSelected(true);
		member.setUserData("Member");

		admin.setToggleGroup(group); 
		admin.setUserData("Admin");

	}
}
