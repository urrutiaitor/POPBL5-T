package operation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Reader {

	public static String[] getString(String pathname, String name) {
		String line = null;
		String data[];

		File file = new File(pathname);
		FileReader fr = null;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e1) {
			System.err.println("Error trying to start reading file " + pathname + " to find " + name);
			try {
				fr.close();
			} catch (IOException e) {
				System.err.println("Error trying to close file " + pathname + " to find" + name);
			}
			return null;
		}
		
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(fr);

		try {
			while ((line = br.readLine()) != null) {
				data = line.split("%");
				if (data[0] == null) {
					System.err.println("Erros trying to split line in file " + pathname + " to find " + name);
					br.close();
					fr.close();
					return null;
				}
				if (data[0].matches(name)) {
					String returnData[] = new String[data.length - 1];
					for (int i = 0; i < data.length - 1; i++) {
						returnData[i] = data[i + 1];
					}
					br.close();
					fr.close();
					return returnData;
				}
			}
		} catch (IOException e) {
			System.err.println("Error trying to read line from file " + pathname + " to find " + name);
		}

		System.err.println("Error trying to find type " + name + ": NOT FOUND");
		return null;

	}
	
	public static int getLastId (String pathname) {
		String line = null;
		String lineAux = null;
		String data[];

		File file = new File(pathname);
		FileReader fr = null;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e1) {
			System.err.println("Error trying to start reading file " + pathname);
			try {
				fr.close();
			} catch (IOException e) {
				System.err.println("Error trying to close file " + pathname);
			}
			return -1;
		}
		
		BufferedReader br = new BufferedReader(fr);

		try {
			while ((lineAux = br.readLine()) != null) {
				line = lineAux;
			}
			data = line.split("%");
			if (data[0] == null) {
				System.err.println("Erros trying to split line in file " + pathname);
				fr.close();
				br.close();
				return -1;
			}
			fr.close();
			br.close();
			return getNum(data[0]);
		} catch (IOException e) {
			System.err.println("Error trying to read line from file");
		}
		
		try {
			fr.close();
			br.close();
		} catch (IOException e) {
			System.err.println("Error trying to close file");
		}
		return -1;
	}

	private static int getNum(String string) {
		int firstIndex = 0;
		String result = "";
		
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) > '0' && string.charAt(i) < '9') {
				firstIndex = i;
				break;
			}
		}
		
		for (int i = firstIndex; i < string.length(); i++) {
			result = result.concat(String.valueOf(string.charAt(i)));
		}
		
		return Integer.parseInt(result);
	}

}
