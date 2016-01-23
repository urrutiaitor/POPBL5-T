package operation;

public class SerialReader extends Thread {

	House house;
	
	public SerialReader(House house){
		this.house = house;
		
	}
	public void run(){
		house.saveAction();
	}
}
