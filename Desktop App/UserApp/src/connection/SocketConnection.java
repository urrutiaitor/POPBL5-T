package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import operation.House;

public class SocketConnection {

	House house;
	int user;
	long initTime;
	
	Socket clientSocket = null;
	protected int serverPort = 8080;
	String hostName = "172.17.16.86";
	
	Semaphore mutex;
	
	InputStream input;
	OutputStream output;
	
	DataInputStream dataInput;
	DataOutputStream dataOutput;
	
	public SocketConnection(House house){
		this.house = house;
		
		try {
			clientSocket = new Socket(hostName, serverPort);
			System.out.println("Socket created in port " + clientSocket.getLocalPort());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mutex = new Semaphore(1);
		
		try {
			input = clientSocket.getInputStream();
			output = clientSocket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dataInput = new DataInputStream(input);
		dataOutput = new DataOutputStream(output);
	}
	
	public String tryRead(){
		String line = null;
		
		try {
			mutex.acquire();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			line = dataInput.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mutex.release();
			return null;
		}
		
		mutex.release();
		
		return line;
	}
	
	public boolean tryWrite(String str){
		try {
			dataOutput.writeBytes(str);
			System.out.println("send serial");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
}
