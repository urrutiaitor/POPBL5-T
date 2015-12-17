package operation;

import java.util.ArrayList;
import java.util.Observable;

public class House extends Observable{

	boolean blinds;
	boolean heating;
	boolean door;
	boolean alarm;
	
	boolean light1;
	boolean light2;
	boolean light3;
	boolean light4;
	
	ArrayList<Action> changes; //ALDATU LEIKE
	
	public House () {
		changes = new ArrayList<Action>();
	}
	
	public boolean makeAction(int  user, int action) {
		switch (action) {
		
		case Action.BLIND_UP:
			return upBlinds();
			
		case Action.BLIND_DOWN:
			return downBlinds();
			
		case Action.HEATING_START:
			return startHeating();
			
		case Action.HEATING_STOP:
			return stopHeating();
			
		case Action.DOOR_OPEN:
			return openDoor();
			
		case Action.DOOR_CLOSE:
			return closeDoor();
			
		case Action.ALARM_START:
			return startAlarm();
			
		case Action.ALARM_STOP:
			return stopAlarm();
			
		case Action.LIGHT1_ON:
			return switchOnLight1();
			
		case Action.LIGTH1_OFF:
			return switchOffLight1();
			
		case Action.LIGHT2_ON:
			return switchOnLight2();
			
		case Action.LIGHT2_OFF:
			return switchOffLight2();
			
		case Action.LIGHT3_ON:
			return switchOnLight3();
			
		case Action.LIGHT3_OFF:
			return switchOffLight3();
			
		case Action.LIGHT4_ON:
			return switchOnLight4();
			
		case Action.LIGHT4_OFF:
			return switchOffLight4();
			
		default:
				return false;
		}
	}
	
	public boolean upBlinds () {
		notifyObservers();
		return false;
	}
	
	public boolean downBlinds () {
		notifyObservers();
		return false;
	}
	
	public boolean startHeating () {
		notifyObservers();
		return false;
	}
	
	public boolean stopHeating () {
		notifyObservers();
		return false;
	}
	
	public boolean openDoor () {
		notifyObservers();
		return false;
	}
	
	public boolean closeDoor () {
		notifyObservers();
		return false;
	}
	
	public boolean startAlarm () {
		notifyObservers();
		return false;
	}
	
	public boolean stopAlarm () {
		notifyObservers();
		return false;
	}
	
	public boolean switchOnLight1 () {
		notifyObservers();
		return false;
	}
	
	public boolean switchOffLight1 () {
		notifyObservers();
		return false;
	}
	
	public boolean switchOnLight2 () {
		notifyObservers();
		return false;
	}
	
	public boolean switchOffLight2 () {
		notifyObservers();
		return false;
	}
	
	public boolean switchOnLight3 () {
		notifyObservers();
		return false;
	}
	
	public boolean switchOffLight3 () {
		notifyObservers();
		return false;
	}
	
	public boolean switchOnLight4 () {
		notifyObservers();
		return false;
	}
	
	public boolean switchOffLight4 () {
		notifyObservers();
		return false;
	}

	public int[] getChanges(int user) {
		int data[] = new int[3];
		
		/*
		 * Devuelve un array de int con los siguientes datos
		 * dato[0] = accionId
		 * datos[1] = usuarioId
		 * datos[2] = tiempo
		 */
		
		return data;
	}

	public boolean isBlinds() {
		return blinds;
	}

	public boolean isHeating() {
		return heating;
	}

	public boolean isDoor() {
		return door;
	}

	public boolean isAlarm() {
		return alarm;
	}

	public boolean isLight1() {
		return light1;
	}

	public boolean isLight2() {
		return light2;
	}

	public boolean isLight3() {
		return light3;
	}

	public boolean isLight4() {
		return light4;
	}

	public ArrayList<Action> getChanges() {
		return changes;
	}
	
	
}
