package operation;

import java.util.ArrayList;
import java.util.Observable;

public class House extends Observable{

	boolean states[];
	
	/*
	 * Se guardarán todos los cambios
	 * Se tendra que hacer una funcion para ver si se han efectuado cambios
	 * A esa funcion se accederá por fuera desde otros usuarios
	 */
	
	ArrayList<Action> changes;
	
	public House (int numActions) {
		changes = new ArrayList<Action>();
		states = new boolean[numActions];
	}
	
	public boolean makeAction (int user, int action) {
		int ind = action/2;
		boolean state;
		
		if (action%2 == 1) {
			state = false;
		} else {
			state = true;
		}
		
		states[ind] = state;
		notifyObservers();
		return true; //DENA ONDO JOAN DELAKO
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

	
	
	
}
