package Client;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import helper.Helper;

public class ClientController extends Application {

	@FXML private TextField txtName;
	@FXML private Label lblName;
	@FXML private TextArea txtInput;
	@FXML private TextArea txtChat;
	
	private static Helper help = new Helper();
	private static String name;
	private static Client client;
	
	public void setName(String newName) {
		name = newName;
	}
	
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		help.loadScene("clientNameInput.fxml", "New Client");		
	}
	
	@FXML
	private void handleNameClick(ActionEvent event) throws IOException {
		handleName();
	}
	
	@FXML
	private void handleNameEnter(KeyEvent event) throws IOException {
		if (event.getCode() == KeyCode.ENTER) {
			handleName();
		}
	}
	
	private void handleName() throws IOException {
		String txt = txtName.getText();
		if (txt.trim().length() < 1) {
			txtName.getStyleClass().add("error");
			lblName.setText("This field is mandatory");
		} else {
			help.loadScene("clientNameChat.fxml", txt);
			setName(txt.trim());
			help.closeStage(txtName);
			client = new Client(txtChat);
		}
	}
	
	@FXML
	private void initialize() {
		if (client != null) {
			client.setTa(txtChat);
		}
	}
	
	@FXML
	private void handleSubmitEnter(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			handleChat();
		}
	}
	
	@FXML
	private void handleSubmitClick(ActionEvent event) {
		handleChat();
	}
	
	private void handleChat() {
		String txt = txtInput.getText();
		if (txt.trim().length() > 0) {
			client.setTa(txtChat);
			client.sendToServer(String.format("%s: %s", name, txt));
			txtInput.setText("");
		}
		txtInput.requestFocus();
	}

}
