package launcher;

import java.util.Scanner;

import connection.MultiThreadedServer;
import graphicInterface.Window;
import operation.House;

public class Main {
	
	House house;
	Window window;
	MultiThreadedServer server;

	public static void main(String[] args) {
		Main main = new Main ();
		
		main.loop();
	}
	
	public Main () {
		house = new House();
		window = new Window(house);
		
		house.addObserver(window);
		
		server = new MultiThreadedServer(9000, house);
		new Thread(server).start();
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
