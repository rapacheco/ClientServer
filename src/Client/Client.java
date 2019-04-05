package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javafx.scene.control.TextArea;

public class Client {
	public static final int PORT = 8080;
	
	private Socket socket;
	private PrintWriter printWriter;
	private ArrayList<ClientThread> threads = new ArrayList<ClientThread>();
	
	public Client(TextArea ta) throws UnknownHostException, IOException {
		socket = getSocket();
		printWriter = new PrintWriter(socket.getOutputStream(), true);
		threads.add(new ClientThread(socket, ta));
		threads.get(threads.size() - 1).start();
	}
	
	public void setTa(TextArea ta) {
		for (ClientThread th : threads) {
			th.setTa(ta);
		}
	}
	
	public void sendToServer(String msg) {
		printWriter.println(msg);
	}	
	
	private Socket getSocket() throws UnknownHostException, IOException {
		Socket socket = new Socket("localhost", PORT);
		return socket;
	}
	
	public class ClientThread extends Thread {
		
		private Socket socket;
		private TextArea ta;
		
		ClientThread(Socket socket, TextArea ta) {
			this.socket = socket;
			this.ta = ta;
		}
		
		public void setTa(TextArea ta) {
			this.ta = ta;
		}

		public void run() {
			try {
				String message = null;
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				while ((message = bufferedReader.readLine()) != null) {
					ta.appendText(message + String.format("%n"));
				}
				socket.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
