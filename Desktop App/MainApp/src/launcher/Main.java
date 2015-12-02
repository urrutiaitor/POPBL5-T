package launcher;

import graphicInterface.Window;
import operation.House;

public class Main {

	public static void main(String[] args) {
		House house = new House();
		Window window = new Window(house);
	}

}
