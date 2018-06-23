package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Record;

public class Client extends Application{
	private Stage primaryStage;
	private BorderPane rootLayout;

	public static void main(String[] arg){
		launch(arg);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Record> startClient(ArrayList<Record> records) {
		Socket socketToServer = null;
		ObjectOutputStream outToServer = null;
		ObjectInputStream inFromServer = null;

		try {
			socketToServer = new Socket("127.0.0.1", 3000); 
			//socketToServer = new Socket("afsconnect1.njit.edu", 3000);
			outToServer = new ObjectOutputStream(socketToServer.getOutputStream());
			inFromServer = new ObjectInputStream(socketToServer.getInputStream());
			System.out.println("Client, Message Out: " + records.size());
			outToServer.writeObject(records);
			records = (ArrayList<Record>) inFromServer.readObject();
			System.out.println("Client, Message In: " + records.size());
			outToServer.close(); 
			inFromServer.close(); 
			socketToServer.close();
		} catch (ClassNotFoundException | IOException e) {

			System.out.println("Server Connection is closed." );
		}

		return records;
	}

	@Override
	public void start(Stage stage) throws Exception {

		this.primaryStage = stage;
		this.primaryStage.setTitle("Admin Member App");

		initRootLayout();
		showloginScene();	
	}
	public void initRootLayout() {
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

	public void showloginScene() {
		try {
			// Load the fxml file and set into the center of the main layout
			FXMLLoader loader = new FXMLLoader(Client.class.getResource("/view/Login.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			rootLayout.setCenter(overviewPage);

		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}
	}

	@FXML
	private void logOut() throws Exception {
		this.primaryStage = new Stage();
		start(this.primaryStage);
	}

	@FXML
	private void close() throws Exception {
		Platform.exit();
	}

	@FXML
	private void handleAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText("Author: Parth Patel");
		alert.setTitle("About");
		alert.setHeaderText("CS602App");
		alert.show();
	}
	/*
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	public BorderPane getRootLayout() {
		return rootLayout;
	}


}