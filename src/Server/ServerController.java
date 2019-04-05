package Server;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ServerController extends Application {

	@FXML private TextArea txtServer;
	@FXML private Button btnServer;
	
	private Thread thread;
	
	public static void main(String[] args) {
		 launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		 loadScene("serverChat.fxml", "Server");
	}
	
	private void loadScene(String fxmlFile, String title) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource(String.format("/fxml/%s", fxmlFile)));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
		Stage stage = new Stage();
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void initialize() {
		thread = new Thread(new Server(txtServer));
		thread.start();
	}
}
