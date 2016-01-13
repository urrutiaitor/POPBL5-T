package launcher;

import java.util.Scanner;

import connection.LineaSerie;
import connection.MultiThreadedServer;
import gnu.io.SerialPortEventListener;
import graphicInterface.Window;
import operation.Action;
import operation.House;

public class Main {
	
	House house;
	Window window;
	MultiThreadedServer server;
	LineaSerie serie;
	SerialPortEventListener serialListener;

	public static void main(String[] args) {
		Main main = new Main ();
		
		main.loop();
	}
	
	public Main () {
		house = new House(Action.getNumActions());
		window = new Window(house);
		
		house.addObserver(window);
		
		server = new MultiThreadedServer(9000, house);
		new Thread(server).start();
		serie = new LineaSerie();
		serie.initialize(serialListener);
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
