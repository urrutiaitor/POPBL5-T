package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import operation.House;

public class WorkerRunnable implements Runnable {

	House house;
	int user;
	long initTime;
	
	protected Socket clientSocket = null;
	protected String serverText = null;

	public WorkerRunnable(Socket clientSocket, House house) {
		this.clientSocket = clientSocket;
		this.house = house;
		user = 0;
		initTime = 0;
	}

	@Override
	public void run() {
		
		try {
			InputStream input = clientSocket.getInputStream();
			OutputStream output = clientSocket.getOutputStream();
			
			DataInputStream dataInput = new DataInputStream(input);
			DataOutputStream dataOutput = new DataOutputStream(output);
			
			while (true) {
				String line = dataInput.readLine();
				String data[] = line.split("%");
				
				switch (data[0]) {
				case "REQUEST":
					user = Integer.valueOf(data[1]);
					initTime = Integer.valueOf(data[2]);
					sendInfo(dataInput, dataOutput);
					break;
				case "INFO":
					
					break;
				case "CHANGE":
					tryChange(dataOutput, data);
					break;
				case "NULL":
					if (!send(dataOutput))
						dataOutput.writeBytes("NULL");
					
				}
			}
			
		} catch (IOException e) {
			// report exception somewhere.
			e.printStackTrace();
		}
	}

	private boolean send (DataOutputStream dataOutput) {
		int data[] = null;
		
		if ((data = house.getChanges(user)) == null) return false;
		try {
			dataOutput.writeBytes("CHANGE%" + String.valueOf(data[1] + "%" + String.valueOf(data[0])));
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
		
		String out = blinds + "%" + heating + "%" + 
				door + "%" + alarm + "%" + light1 + "%" +
				light2 + "%" + light3 + "%" + light4;
		
		try {
			
			do {
				dataOutput.writeBytes(out);
			}  while (dataInput.readLine() == "WRONG");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void tryChange(DataOutputStream dataOutput, String[] data) {
		String comand = null;
		
		if (house.makeAction(Integer.valueOf(data[1]), Integer.valueOf(data[2]))) {
			comand = "SUCCESSFUL";
		} else {
			comand = "WRONG";
		}
		
		try {
			dataOutput.writeBytes(comand);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
