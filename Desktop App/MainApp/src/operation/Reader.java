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
		FileReader fr;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e1) {
			System.err.println("Error trying to start reading file " + pathname + " to find " + name);
			return null;
		}
		
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(fr);

		try {
			while ((line = br.readLine()) != null) {
				data = line.split("%");
				if (data[0] == null) {
					System.err.println("Erros trying to split line in file " + pathname + " to find " + name);
					return null;
				}
				if (data[0].matches(name)) {
					String returnData[] = new String[data.length - 1];
					for (int i = 0; i < data.length - 1; i++) {
						returnData[i] = data[i + 1];
					}
					return returnData;
				}
			}
		} catch (IOException e) {
			System.err.println("Error trying to read line from file " + pathname + " to find " + name);
		}

		System.err.println("Error trying to find type " + name + ": NOT FOUND");
		return null;

	}
	
}
