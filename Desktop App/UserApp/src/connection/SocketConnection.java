package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketConnection implements Runnable {

	final String host = "localhost";
	final int portNumber = 81;
	
	Socket socket = null;
	DataOutputStream os = null;
	DataInputStream is = null;
	
	String data;
	boolean accepted = false;
	
	public SocketConnection(String data) {
		this.data = data;
	}
	
	@Override
	public void run() {
		
		
		
		try {
			socket = new Socket(host, portNumber);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			os = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			is = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if ((socket != null) && (os != null) && (is != null)) {
			writeComand();
			waitConfirmation();
		}
	}

	private void writeComand() {
		try {
			os.writeBytes(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void waitConfirmation() {
		String response = null;
		
		try {
			response = is.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (response.toUpperCase().matches("ACCEPTED")) {
			accepted = true;
		}
	}

	public boolean isAccepted() {
		return accepted;
	}

	
	
}


