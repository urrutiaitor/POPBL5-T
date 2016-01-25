package operation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observable;
import java.util.concurrent.Semaphore;

import connection.LineaSerie;
public class House extends Observable{

	final static int USERBITS = 3;
	final static int ACTIONBITS = 5;
	boolean states[];
	Semaphore mutex;
	Semaphore bufferMutex;
	LineaSerie serial;
	
	ArrayList<Action> changeRequest;
	
	/*
	 * Se guardar�n todos los cambios
	 * Se tendra que hacer una funcion para ver si se han efectuado cambios
	 * A esa funcion se acceder� por fuera desde otros usuarios
	 */
	
	public House (int numActions, LineaSerie serial) {
		mutex = new Semaphore(1);
		bufferMutex = new Semaphore(1);
		states = new boolean[numActions/2];
		this.serial = serial;
		
		changeRequest = new ArrayList<Action>();
	}
	
	/*
	public boolean saveAction (byte[] infor) {
		int ind, action, user;
		boolean state;
		
		short[] data = interpretarInformacion(infor[0]);
		
		action = data[1];
		user = data[0];
		ind = action/2;
		long time = System.currentTimeMillis();
		
		Action auxAction = new Action(action, user, time);
		notifyObservers(auxAction);
		
		if (action%2 == 1) {
			state = false;
		} else {
			state = true;
		}
		states[ind] = state;
		
		return true;
	}
	*/
	
	

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
	
	public boolean setChangeRequest(Action action) {
		try {
			bufferMutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		changeRequest.add(action);
		
		bufferMutex.release();
		
		return true;
	}

	public Action getChangeRequest() {
		Action action = null;
		
		try {
			bufferMutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (changeRequest.size() > 0) {
			action = changeRequest.get(0);
			changeRequest.remove(0);
		}
		bufferMutex.release();
		
		return action;
	}

	public void setState(int i, int state) {
		if (state == 0){
			states[i] = false;
		}
		if (state == 1) {
			states[i] = true;
		}
		
		System.out.println("Set change");
		
		setChanged();
		notifyObservers(new Action((i*2) + state, 0, 0));
		
	}

	public void changeState(int user, int action) {
		if (action/2 == 1){
			states[action/2] = true;
		} else {
			states[action/2] = false;
		}
		System.out.println("Change change");
		setChanged();
		notifyObservers(new Action(action, user, System.currentTimeMillis()));
	}
	
}