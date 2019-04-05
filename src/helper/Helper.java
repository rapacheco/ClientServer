package helper;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Helper {
	public void loadScene(String fxmlFile, String title) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource(String.format("/fxml/%s", fxmlFile)));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
		Stage stage = new Stage();
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
	}
	
	public void closeStage(Node node) {
		Stage stage = (Stage)node.getScene().getWindow();
		stage.close();
	}
}
