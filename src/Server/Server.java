package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import javafx.scene.control.TextArea;

public class Server implements Runnable {

	public static final int PORT = 8080;
	
	private ArrayList<Socket> sockets = new ArrayList<Socket>();
	private TextArea ta;
	
	public Server(TextArea txtServer) {
		ta = txtServer;
	}
	
	public void run() {
		Date date = new Date();
		try(ServerSocket serverSocket = new ServerSocket(PORT);) {
			ta.appendText("MultiThreadServer started at " + date + String.format("%n"));
			while (true) {
				Socket socket = serverSocket.accept();
				sockets.add(socket);
				new ServerThread(sockets, ta).start();
			}
		} catch (BindException e) {
			ta.appendText("Cannot open socket in port " + PORT + ". Please check if port is available." + String.format("%n"));			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public class ServerThread extends Thread {
		ArrayList<Socket> sockets;
		Socket socket;
		TextArea ta;
		ArrayList<PrintWriter> writers = new ArrayList<PrintWriter>();
		
		ServerThread(ArrayList<Socket> sockets, TextArea ta) {
			this.sockets = sockets;
			this.socket = sockets.get(sockets.size() - 1);
			this.ta = ta;
		}

		public void run() {
			try {
				String message = null;
				ta.appendText("Connection from " + socket + " at " + new Date() + String.format("%n"));
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				while ((message = bufferedReader.readLine()) != null) {
					updateSocketList();
					ta.appendText(message + String.format("%n"));
					broadCast(message);
				}
				socket.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		private void broadCast(String message) {
			for (PrintWriter w : writers) {
				w.println(message);
			}
		}
		
		private void updateSocketList() throws IOException {
			writers.clear();
			for (Socket s : sockets) {
				writers.add(new PrintWriter(s.getOutputStream(), true));
			}
		}
	}
}
