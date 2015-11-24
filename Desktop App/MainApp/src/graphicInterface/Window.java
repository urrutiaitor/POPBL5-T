package graphicInterface;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;

public class Window extends JFrame implements ActionListener, Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String tabName[][];
	
	int selectedLenguage = 0;
	
	final static String TABFILEPATH = "tabFile.txt";
	
	final static int LENGUAGECANT = 3;
	final static int TABCANT = 2;
	
	public Window () {
		this.setJMenuBar(createMenuBar());
		this.setContentPane(createFrame());
		this.setLocation(200, 200);
		this.setSize(640, 480);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void initializeNames () {
		tabName = new String[TABCANT][LENGUAGECANT];
		
		for (int i = 0; i < TABCANT; i++) {
			tabName[i] = getString(TABFILEPATH, "TAB" + String.valueOf(i));
		}
	}
	
	public String[] getString (String pathname, String name) {
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

	private Container createFrame() {
		JTabbedPane tabbedPane = new JTabbedPane();
		
		tabbedPane.addTab(tabName[0][selectedLenguage], new ImageIcon("iconoPestana1.png"), createTab1());
		tabbedPane.addTab(tabName[1][selectedLenguage], new ImageIcon("iconoPestana2.png"), createTab2());
		tabbedPane.addTab(tabName[2][selectedLenguage], new ImageIcon("iconoPestana3.png"), createTab3());
		
		return tabbedPane;
	}

	private Component createTab1() {
		// TODO Auto-generated method stub
		return null;
	}

	private Component createTab2() {
		// TODO Auto-generated method stub
		return null;
	}

	private Component createTab3() {
		// TODO Auto-generated method stub
		return null;
	}

	private JMenuBar createMenuBar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
