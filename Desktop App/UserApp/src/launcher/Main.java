package launcher;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

import connection.LineaSerie;
import connection.SocketConnection;
import connection.WorkerRunnable;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import graphicInterface.Window;
import operation.Action;
import operation.BuzonSincrono;
import operation.House;

public class Main {
	
	House house;
	Window window;
	SerialPortEventListener listener;
	LineaSerie bufferSerie;
	SocketConnection socketConn;
	
	public static void main(String[] args) {
		Main main = new Main();
		main.loop();
	}
	
	public Main () {
		listener = new SerialPortEventListener() {
			
			@Override
			public void serialEvent(SerialPortEvent arg0) {
				if(arg0.getEventType() == SerialPortEvent.DATA_AVAILABLE){
					byte[] infor = bufferSerie.leer();
					short[] data = house.interpretarInformacion((short)infor[0]);
					String str = "REQUEST%" + data[0] + "%" + data[1] + "\n";
					System.out.println(str);
					
					BuzonSincrono buzon = new BuzonSincrono();
					window.getTab2().setMessage(data[0], data[1], buzon);
					String str1 = (String) buzon.receive();
					if (str1.equals("ACCEPTED")) {
						System.out.println("to send serial");
						socketConn.tryWrite(str);
					}
					if (str1.equals("DENIED")) {
						System.out.println("Request denied");
					}
				}
			}
		};
		
		
		house = new House(Action.getNumActions(),bufferSerie);
		window = new Window(house);
		
		house.addObserver(window.getTab1());
		house.addObserver(window.getTab2());
		
		socketConn = new SocketConnection(house);
		WorkerRunnable wr = new WorkerRunnable(socketConn, house);
		new Thread(wr).start();
		
		bufferSerie = new LineaSerie(house, window, socketConn);
		bufferSerie.initialize(listener);
	}
	
	private void loop() {
		Scanner keyboard = new Scanner(System.in);
		String line;
		
		while (!(line = keyboard.nextLine().toUpperCase()).matches("STOP")) {
			switch (line) {
			case "CONTINUE":
				System.out.println("Continue");
				break;

			default:
				break;
			}
		}
		
		stop();
	}
	
	public void stop () {
		window.dispose();
		
	}

}
