package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import operation.Action;
import operation.House;

public class WorkerRunnable implements Runnable, Observer {

	House house;
	
	protected Socket clientSocket = null;
	protected String serverText = null;

	public WorkerRunnable(Socket clientSocket, House house) {
		this.clientSocket = clientSocket;
		this.house = house;
	}

	@Override
	public void run() {
		
		try {
			InputStream input = clientSocket.getInputStream();
			OutputStream output = clientSocket.getOutputStream();
			
			DataInputStream dataInput = new DataInputStream(input);
			DataOutputStream dataOutput = new DataOutputStream(output);
			
			System.out.println("Socket connection established");
			
			while (true) {
				String line = dataInput.readLine();
				System.out.println("Line recived: " + line);
				String data[] = line.split("%");
				
				switch (data[0]) {
				case "REQUEST":
					house.makeAction(Short.valueOf(data[1]), Short.valueOf(data[2]));
					break;
				case "INIT":
					sendInfo(dataInput, dataOutput);
					break;
				}
			}
			
		} catch (IOException e) {
			System.err.println("Connection closed");
		}
	}

	private boolean send (int data[]) {
		OutputStream output = null;
		try {
			output = clientSocket.getOutputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		DataOutputStream dataOutput = new DataOutputStream(output);
		
		try {
			dataOutput.writeBytes("CHANGE%" + String.valueOf(data[1] + "%" + String.valueOf(data[0]) + "\n"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	private void sendInfo(DataInputStream dataInput, DataOutputStream dataOutput) {
		int blinds = 0, heating = 0, door = 0, alarm = 0;
		int light1 = 0, light2 = 0, light3 = 0, light4 = 0;
		
		if (house.isBlinds()) blinds = 1;
		if (house.isHeating()) heating = 1;
		if (house.isDoor()) door = 1;
		if (house.isAlarm()) alarm = 1;
		if (house.isLight1()) light1 = 1;
		if (house.isLight2()) light2 = 1;
		if (house.isLight3()) light3 = 1;
		if (house.isLight4()) light4 = 1;
		
		String out = "INFO%" + blinds + "%" + heating + "%" + 
				door + "%" + alarm + "%" + light1 + "%" +
				light2 + "%" + light3 + "%" + light4 + "\n";
		
		try {
			dataOutput.writeBytes(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(Observable o, Object arg) {
		Action action = (Action) arg;
		int data[] = new int[2];
		data[1] = action.getAction();
		data[0] = action.getUser();
		send(data);
	}

}
