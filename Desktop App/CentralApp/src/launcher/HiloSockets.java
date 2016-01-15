package launcher;
import java.util.Random;


public class HiloSockets extends Thread {

	House house;
	Random aleatorio;
	
	public HiloSockets(House house){
		this.house = house;
		aleatorio = new Random();
	}
	
	public void run (){
		short user, action;
		user = (short) aleatorio.nextInt(7);
		action = (short) aleatorio.nextInt();
		house.makeAction(user, action);
	}
}
