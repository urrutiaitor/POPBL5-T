package launcher;

import java.util.Scanner;

import connection.LineaSerie;
import connection.MultiThreadedServer;
import connection.WorkerRunnable;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import graphicInterface.Window;
import operation.Action;
import operation.House;

public class Main {
	
	House house;
	Window window;
	MultiThreadedServer server;
	SerialPortEventListener listener;
	LineaSerie bufferSerie;
	WorkerRunnable workerRunnable;
	
	public Main () {
		listener = new SerialPortEventListener() {
			
			@Override
			public void serialEvent(SerialPortEvent arg0) {
				if(arg0.getEventType() == SerialPortEvent.DATA_AVAILABLE){
					house.saveAction();
				}
			}
		};
		bufferSerie = new LineaSerie();
		bufferSerie.initialize(listener);
		
		house = new House(Action.getNumActions(),bufferSerie);
		window = new Window(house);
		
		house.addObserver(window);
		
		server = new MultiThreadedServer(8080, house);
		new Thread(server).start();
	}
	
	public static void main(String[] args) {
		Main main = new Main ();
		
		main.loop();
	}
	
	public void loop () {
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
		server.stop();
		window.close();
		
	}
	
}
