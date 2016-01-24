package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import operation.Action;
import operation.House;

public class WorkerRunnable implements Runnable {

	House house;
	int user;
	long initTime;
	
	SocketConnection socketConn;

	public WorkerRunnable(SocketConnection socketConn, House house) {
		this.socketConn = socketConn;
		this.house = house;
	}

	@Override
	public void run() {
		String line = null;
		
		line = "INIT%" + user + "\n";
		socketConn.tryWrite(line);
		System.out.println("Init request");
		
		while (true) {
			if ((line = socketConn.tryRead()) != null) {
				System.out.println("Line recived: " + line);
				String data[] = line.split("%");
				
				switch (data[0]) {
				case "INFO":
					for(int i = 1; i < data.length; i++) {
						house.setState(i - 1, Integer.valueOf(data[i]));
					}
					break;
				case "CHANGE":
					house.changeState(Integer.valueOf(data[1]), Integer.valueOf(data[2]));
					break;
					
				}
			}
		}
		
		
	}

}
