package operation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import connection.LineaSerie;



public class House extends Observable{
	
	final String tabFilePath = "tabFile.txt";
	final String menuFilePath = "menuFile.txt";
	final String actionsFilePath = "actionsFile.txt";
	final String objectsFilePath = "objectsFile.txt";
	final String actionsRegisterFilePath = "actionsRegisterFile.txt";

	final static int USERBITS = 3;
	final static int ACTIONBITS = 5;
	boolean states[];
	Semaphore mutex; 
	ArrayList<Semaphore> socketsReaders; //hilos sockets leer a estado de la casa o a cambios, cada cliente un semaforo.
	ArrayList<Action> changes;
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
		socketsReaders = new ArrayList<>();
		for(int i=0; i<socketsReaders.size();i++){
			Semaphore aux = new Semaphore(1);
			socketsReaders.add(aux);
		}
		changesIndex = new ArrayList<>();
		for(int i=0;i<changesIndex.size();i++){
			changesIndex.set(i, 0);
		}
		this.serial=serial;
	}
	
	public boolean saveAction () {
		int ind, action, user;
		byte[] infor;
		boolean state;
		short[] data = new short[2];
		Action auxAction;
		
		try {
			
			mutex.acquire();
			infor = serial.leer();
			for(int i = 0; i < infor.length; i++){
				data = interpretarInformacion(infor[i]);
				
				action = data[1];
				user = data[0];
				ind = action/2;
				long time =  System.currentTimeMillis();
				Action aux = new Action(action, user, time);
				if (action%2 == 1) {
					state = false;
				} else {
					state = true;
				}
				changes.add(aux);
				states[ind] = state;
				
				for(int x=0;x<socketsReaders.size();x++){
					socketsReaders.get(x).release();
				}
			}
			
			mutex.release();
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		notifyObservers(auxAction = new Action(data[1], data[0], System.currentTimeMillis()));
		Writter.setRegAction(actionsRegisterFilePath, auxAction);
		
		return true; //DENA ONDO JOAN DELAKO
	}
	private short[] interpretarInformacion(short inforInt) {
		// TODO Auto-generated method stub
		boolean bits[] = new boolean[8];
		short divisor = 128;
		short multiplicador = 1;
		short accion = 0, usuario = 0;
		short datos[] = new short[2];
		
		for (int i = 0; i < 8; i++){
			if (inforInt/divisor > 0){
				inforInt = (short) (inforInt - divisor);
				divisor = (short)(divisor/2);
				bits[i] = true;
			} else {
				divisor = (short) (divisor/2);
				bits[i] = false;
			}
		}
				
		for (int i = 0; i < 5; i++){
			if (bits[7 - i]){
				accion = (short) (accion + multiplicador);
			}
			multiplicador = (short) (multiplicador*2);
		}
		
		multiplicador = 1;
		for (int i = 0; i < 3; i++){
			if (bits[2 - i]){
				usuario = (short) (usuario + multiplicador);
			}
			multiplicador = (short) (multiplicador*2);
		}
		datos[1] = accion;
		datos[0] = usuario;
	      
		return datos;
	}
	private char generarInformacion(short[] data) {
		// TODO Auto-generated method stub
		boolean bits[] = new boolean [8];
		short multiplicador;
		short resultado = 0; 
		short divisor = 4;
		char caracter;
		
		for( int i= 0; i<3;i++){
		
			if((data[0]/divisor)>0){
				data[0] = (short) (data[0] - divisor);
				bits[i] = true;
			}else{
				bits[i] = false;
			}
			divisor = (short) (divisor/2);
			
		}
		divisor = 16;
		for(int i= 3; i<8;i++){
			
			if((data[1]/divisor)>0){
				data[1] = (short) (data[1] - divisor);
				bits[i] = true;
			}else{
				bits[i] = false;
			}
			divisor = (short) (divisor/2);
			
		}
		
		multiplicador = 1;
		for(int i=7;i>=0;i--){
			if(bits[i]){
				resultado = (short) (resultado + multiplicador);
			}
			multiplicador = (short) (multiplicador*2);
		}
		caracter = (char) resultado;
		return caracter;
	}

	public boolean makeAction (short user, short action) {
				
		short[] data = new short[2];
		data[0] = user;
		data[1] = action;
		try {
			
			mutex.acquire();
			char c = generarInformacion(data);
			serial.escribir(c);
			mutex.release();
		
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
		if(states[0]==true){
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
		if(states[1]==true){
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
		if(states[2]==true){
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
		if(states[3]==true){
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
		if(states[4]==true){
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
		if(states[5]==true){
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
		if(states[6]==true){
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
		if(states[7]==true){
			mutex.release();
			return true;
		}else{
			mutex.release();
			return false;
		}
	}
	
}
