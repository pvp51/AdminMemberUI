package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.DataObject;
import model.Record;

public class Client extends Application{
	private Stage primaryStage;
	private BorderPane rootLayout;

	public static void main(String[] arg){
			launch(arg);
	}
	
	public ArrayList<Record> startClient(ArrayList<Record> records) {
		Socket socketToServer = null;
		ObjectOutputStream outToServer = null;
		ObjectInputStream inFromServer = null;

		try {
			socketToServer = new Socket("127.0.0.1", 3000); 
			//socketToServer = new Socket("afsconnect1.njit.edu", 3000);
			outToServer = new ObjectOutputStream(socketToServer.getOutputStream());
			inFromServer = new ObjectInputStream(socketToServer.getInputStream()); 
			outToServer.writeObject(records);
			records = (ArrayList<Record>) inFromServer.readObject();
			outToServer.close(); 
			inFromServer.close(); 
			socketToServer.close();
		} catch (ClassNotFoundException | IOException e) {
			
			System.out.println("Server Connection is closed now. Thank you :)" );
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