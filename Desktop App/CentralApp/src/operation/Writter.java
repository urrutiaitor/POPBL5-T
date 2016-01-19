package operation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writter {

	public static boolean setAction(String pathname, Action action) {
		
		File file = new File(pathname);
		FileWriter fw = null;
		try {
			fw = new FileWriter(file);
		} catch (IOException e2) {
			System.err.println("Error openin File Writer to write action " + action + " in file " + pathname);
			try {
				fw.close();
			} catch (IOException e) {
				System.err.println("Error trying to close File Writer after error");
			}
		}
		BufferedWriter bw = new BufferedWriter(fw);

		int position = Reader.getLastId(pathname);
		
		String str = "ACTION" + position + "%" + action.getTime() + "%" + action.getAction() + "%" + action.getUser();
		
		try {
			bw.write(str);
			bw.close();
			fw.close();
			
			return true;
		} catch (IOException e) {
			System.err.println("Error trying to write action " + action + " in file " + pathname);
			try {
				bw.close();
				fw.close();
			} catch (IOException e1) {
				System.err.println("Error trying to close file after a writting error");
				return false;
			}
			
			return false;
		}

	}
	
	public static boolean setRegAction(String pathname, Action action){
		
		File file = new File(pathname);
		FileWriter fw = null;
		try {
			fw = new FileWriter(file);
		} catch (IOException e2) {
			System.err.println("Error openin File Writer to write action " + action + " in file " + pathname);
			try {
				fw.close();
			} catch (IOException e) {
				System.err.println("Error trying to close File Writer after error");
			}
		}
		BufferedWriter bw = new BufferedWriter(fw);

		int position = Reader.getLastId(pathname);
		
		String str = "REG" + Reader.getLastId(pathname) + "%" + System.currentTimeMillis() +
				"%" + action.getAction() + "%" + action.getUser();
		
		try {
			bw.write(str);
			bw.close();
			fw.close();
			
			return true;
		} catch (IOException e) {
			System.err.println("Error trying to write action " + action + " in file " + pathname);
			try {
				bw.close();
				fw.close();
			} catch (IOException e1) {
				System.err.println("Error trying to close file after a writting error");
				return false;
			}
			
			return false;
		}
	}
}
