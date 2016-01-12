package operation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;



public class House extends Observable{

	boolean states[];
	Semaphore mutex; 
	ArrayList<Semaphore> socketsReaders; //hilos sockets leer a estado de la casa o a cambios, cada cliente un semaforo.
	ArrayList<Semaphore> socketsWriters; //hilos sockets peticion de accion, cada cliente un semaforo.
	ArrayList<Action> changes;
	Semaphore serialWriter; // hilo recibirCambioEnPlaca: recibir del serial y cambiar estado.
	Semaphore serialReader; //hilo hacerCambiosEnPlaca: mandar por serial a placa el cambio.
	ArrayList<Integer> changesIndex;

	/*
	 * Se guardarán todos los cambios
	 * Se tendra que hacer una funcion para ver si se han efectuado cambios
	 * A esa funcion se accederá por fuera desde otros usuarios
	 */
	
	
	public House (int numActions) {
		changes = new ArrayList<Action>();
		states = new boolean[numActions/2];
		mutex = new Semaphore(1);
		serialWriter = new Semaphore(1);
		socketsReaders = new ArrayList<>();
		socketsWriters = new ArrayList<>();
		for(int i=0; i<socketsReaders.size();i++){
			Semaphore aux = new Semaphore(1);
			socketsReaders.add(aux);
			Semaphore aux2 = new Semaphore(1);
			socketsWriters.add(aux2);
		}
		serialReader = new Semaphore(1);
		changesIndex = new ArrayList<>();
		for(int i=0;i<changesIndex.size();i++){
			changesIndex.set(i, 0);
		}
	}
	
	public boolean saveAction () {
		int ind, action, user;
		String data[], infor;
		boolean state;
		
		try {
			mutex.acquire();
			serialReader.acquire();
			//leer del serial la accion que le manda
			infor = serial.receive();
			data = infor.split("%");
			action = Integer.parseInt(data[0]);
			user = Integer.parseInt(data[1]);
			ind = action/2;
			Calendar calendar = Calendar.getInstance();
			long time =  calendar.getTimeInMillis();
			Action aux = new Action(action, user, time);
			if (action%2 == 1) {
				state = false;
			} else {
				state = true;
			}
			changes.add(aux);
			states[ind] = state;
			notifyObservers();
			mutex.release();
			serialReader.release();
			for(int i=0;i<socketsReaders.size();i++){
				socketsReaders.get(i).release();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true; //DENA ONDO JOAN DELAKO
	}
	public boolean makeAction (int user, int action) {
		int ind = action/2;
		boolean state;
		
		try {
			mutex.acquire();
			serialWriter.acquire();
			//mandar por serial la accion que se quiere llevar a cabo
			serial.send(user,action);
			serialWriter.release();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		saveAction(user, action);
		mutex.release();
		return true; //DENA ONDO JOAN DELAKO
	}

	public int[] getChanges(int user) {
		int data[] = new int[3];
		
		try {
			mutex.acquire();
			socketsReaders.get(user).acquire();
//			while(changes.get(changesIndex.get(user)).user==user){
//				changesIndex.set(user, (changesIndex.get(user)+1));
//			}
			data[0] = changes.get(changesIndex.get(user)).action;
			data[1] = changes.get(changesIndex.get(user)).user;
			data[2] = (int) (changes.get(changesIndex.get(user)).time);
			changesIndex.set(user, (changesIndex.get(user)+1));
			/*
			 * Devuelve un array de int con los siguientes datos
			 * dato[0] = accionId
			 * datos[1] = usuarioId
			 * datos[2] = tiempo
			 */
			
			mutex.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	public boolean isBlinds() {
		// TODO Auto-generated method stub
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		if(states[1]==true){
			mutex.release();
			return true;
		}else{
			mutex.release();
			return false;
		}
		
	}

	public boolean isHeating() {
		// TODO Auto-generated method stub
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		if(states[2]==true){
			mutex.release();
			return true;
		}else{
			mutex.release();
			return false;
		}
	}

	public boolean isDoor() {
		// TODO Auto-generated method stub
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		if(states[3]==true){
			mutex.release();
			return true;
		}else{
			mutex.release();
			return false;
		}
	}

	public boolean isAlarm() {
		// TODO Auto-generated method stub
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		if(states[4]==true){
			mutex.release();
			return true;
		}else{
			mutex.release();
			return false;
		}
	}

	public boolean isLight1() {
		// TODO Auto-generated method stub
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		if(states[5]==true){
			mutex.release();
			return true;
		}else{
			mutex.release();
			return false;
		}
	}

	public boolean isLight2() {
		// TODO Auto-generated method stub
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		if(states[6]==true){
			mutex.release();
			return true;
		}else{
			mutex.release();
			return false;
		}
	}

	public boolean isLight3() {
		// TODO Auto-generated method stub
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		if(states[7]==true){
			mutex.release();
			return true;
		}else{
			mutex.release();
			return false;
		}
	}

	public boolean isLight4() {
		// TODO Auto-generated method stub
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		if(states[8]==true){
			mutex.release();
			return true;
		}else{
			mutex.release();
			return false;
		}
	}

//	public boolean hasChanged(int action){
//		boolean currentState;
//		boolean changed = false;
//		
//		try {
//			if(receivedAction.tryAcquire(1, TimeUnit.SECONDS)){
//				receivedAction.acquire();
//				if(action%2 == 0){
//				currentState = false;
//				}else{
//					currentState = true;
//				}
//				if(states[action/2] == currentState){
//					changed = true;
//				}
//				else{
//					changed = false;
//				}
//			}
//			
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return changed;
//		
//	}

	
	
	
}
