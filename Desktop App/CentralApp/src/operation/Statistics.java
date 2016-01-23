package operation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.ListIterator;

public class Statistics {

	public static ArrayList<Double> getHistorical (String pathname, String name, int action1, int action2) {
		String[] data;
		ArrayList<Action> conversion = new ArrayList<Action>();
		ArrayList<Double> output = new ArrayList<Double>();
		
		int i = 0;
		while (true) {
			
			data = Reader.getString(pathname, name + String.valueOf(i));
			
			i++;
			
			if (data != null) {
				
				int action = Integer.parseInt(data[2]);
				int user = Integer.parseInt(data[3]);
				long time = Long.parseLong(data[0]);
				conversion.add(new Action(action, user, time));
			} else {
				break;
			}
		
		}
		
		/*
		 * Interpretar esta informacion
		 * OLE MARRON!
		 */
		
		filterActions(conversion, action1, action2);
		
		Calendar cActual = Calendar.getInstance();
		Calendar cInit = Calendar.getInstance();
		
		cInit.set(cActual.get(Calendar.YEAR), cActual.get(Calendar.MONTH) - 1,
				cActual.get(Calendar.DATE), cActual.get(Calendar.HOUR_OF_DAY),
				cActual.get(Calendar.MINUTE), cActual.get(Calendar.SECOND));
		long timeInit = cInit.getTimeInMillis();
		
		cActual.set(cInit.get(Calendar.YEAR), cInit.get(Calendar.MONTH) + 1,
				cInit.get(Calendar.DATE), cInit.get(Calendar.HOUR_OF_DAY),
				cInit.get(Calendar.MINUTE), cInit.get(Calendar.SECOND));
		long timeActual = cActual.getTimeInMillis();
		
		long timeDelta = 1000*60*60*24;
		
		filterActions(conversion, timeInit, timeActual);
		
		output = getDuration (conversion, timeInit, timeActual, timeDelta);
		
		return output;
	}

	public static void filterActions(ArrayList<Action> list, int action1, int action2) {
		ListIterator<Action> listIt = list.listIterator();
		
		while (listIt.hasNext()) {
			int action = listIt.next().getAction();
			
			if (action != action1 && action != action2) listIt.remove();
		}
	}
	
	public static void filterActions(ArrayList<Action> list, long dateInit, long dateFinal) {
		ListIterator<Action> listIt = list.listIterator();
		
		while (listIt.hasNext()) {
			long date = listIt.next().getTime();
			
			if (date < dateInit || date > dateFinal) listIt.remove();
		}
	}
	
	private static ArrayList<Double> getDuration(ArrayList<Action> conversion, long timeInit, long timeActual, long timeDelta) {
		ArrayList<Double> output = new ArrayList<Double>();
		return null;
	}
	
	private static double getSum(ArrayList<Action> conversion, long timeInit, long timeFinal) {
		return (Double) null;
	}
}
