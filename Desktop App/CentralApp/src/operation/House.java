package operation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import connection.LineaSerie;



public class House extends Observable{

	boolean states[];
	Semaphore mutex; 
	ArrayList<Semaphore> socketsReaders; //hilos sockets leer a estado de la casa o a cambios, cada cliente un semaforo.
	ArrayList<Semaphore> socketsWriters; //hilos sockets peticion de accion, cada cliente un semaforo.
	ArrayList<Action> changes;
	Semaphore serialWriter; // hilo recibirCambioEnPlaca: recibir del serial y cambiar estado.
	Semaphore serialReader; //hilo hacerCambiosEnPlaca: mandar por serial a placa el cambio.
	ArrayList<Integer> changesIndex;
	LineaSerie serial;
	/*
	 * Se guardar�n todos los cambios
	 * Se tendra que hacer una funcion para ver si se han efectuado cambios
	 * A esa funcion se acceder� por fuera desde otros usuarios
	 */
	
	
	public House (int numActions, LineaSerie serial) {
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
		this.serial=serial;
	}
	
	public boolean saveAction () {
		int ind, action, user;
		String infor;
		boolean state;
		int data [] = new int[2];
		
		try {
			
			serialReader.acquire();
			mutex.acquire();
			infor = serial.leer();
			int inforInt=Integer.valueOf(infor);
			data = interpretarInformacion(inforInt);
			action = data[0];
			user = data[1];
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
			
			serialReader.release();
			for(int i=0;i<socketsReaders.size();i++){
				socketsReaders.get(i).release();
			}
			
			mutex.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true; //DENA ONDO JOAN DELAKO
	}
	private int[] interpretarInformacion(int inforInt) {
		// TODO Auto-generated method stub
		int datos[] = new int[2];
		String binary = "";
		
       while (inforInt > 0) {
           int rem = inforInt % 2;
           binary = rem + binary;
           inforInt = inforInt / 2;
       }
       datos[0] = 0;
       datos[1] = 0;
       for(int j = 0; j <3; j++){//ultimos 3 bits para usuario
    	   int valor=Integer.valueOf(binary.charAt(binary.length()-j-1))-48;
    	   datos[1] = datos[1] + (int) ((valor)* Math.pow(2, j));
       }
       for(int k = 0; k<binary.length()-3; k++){
    	   int valor = Integer.valueOf(binary.charAt(binary.length()-k-4))-48;
    	   datos[0] = datos[0] + (int) ((valor)* Math.pow(2, k));
       }
      
		return datos;
	}

	public boolean makeAction (int user, int action) {
				
		try {
			
			serialWriter.acquire();
			mutex.acquire();
			//mandar por serial la accion que se quiere llevar a cabo
			//inversa de interpretar informacion
			mutex.release();
			serialWriter.release();
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true; //DENA ONDO JOAN DELAKO
	}

	public int[] getChanges(int user) {
		int data[] = new int[3];
		
		try {
			
			socketsReaders.get(user).acquire();
//			while(changes.get(changesIndex.get(user)).user==user){
//				changesIndex.set(user, (changesIndex.get(user)+1));
//			}
			mutex.acquire();
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
