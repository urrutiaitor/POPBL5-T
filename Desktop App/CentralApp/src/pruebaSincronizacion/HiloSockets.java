package pruebaSincronizacion;
import java.util.Random;


public class HiloSockets extends Thread {

	House house;
	Random aleatorio;
	boolean continuar;
	
	public HiloSockets(House house){
		this.house = house;
		aleatorio = new Random();
		continuar = true;
	}
	
	public void run (){
		short user, action;
		user = (short) aleatorio.nextInt(7);
		action = (short) aleatorio.nextInt();
		while(continuar){
			house.makeAction(user, action);
			house.getChanges(user);
		}
	}
}
