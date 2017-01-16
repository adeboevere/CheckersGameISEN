package isen.checkers;

import java.io.IOException;

import isen.checkers.service.StageService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application{

	
	private void showScreen() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
			App.class.getResource("view/CheckerBoard.fxml"));
			AnchorPane rootElement = loader.load();
			
			Scene scene = new Scene(rootElement);
			
			StageService.getInstance().getPrimaryStage().setScene(scene);
			StageService.getInstance().getPrimaryStage().show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		StageService.getInstance().setPrimaryStage(primaryStage);
		primaryStage.setTitle("Checkers ISEN");		
		this.showScreen();
	}

}
