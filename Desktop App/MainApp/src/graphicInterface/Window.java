package graphicInterface;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Window extends JFrame implements ActionListener, Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String tabName[][];
	String menuName[][];
	
	int selectedLenguage = 2;
	
	String tabFilePath = "tabFile.txt";
	String menuFilePath = "menuFile.txt";
	
	final static int LENGUAGECANT = 3;
	final static int TABCANT = 3;
	final static int MENUCANT = 8;
	
	AbstractAction accOptionMenu, accOptionEdit;
	AbstractAction accLenguage1, accLenguage2, accLenguage3;
	
	public Window () {
		initializeNames();
		createActions();
		this.setJMenuBar(createMenuBar());
		this.setContentPane(createFrame());
		this.setLocation(200, 200);
		this.setSize(640, 480);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void initializeNames () {
		tabName = new String[TABCANT][LENGUAGECANT];
		menuName = new String[MENUCANT][LENGUAGECANT];
		
		for (int i = 0; i < TABCANT; i++) {
			tabName[i] = getString(tabFilePath, "TAB" + String.valueOf(i));
		}
		
		for (int i = 0; i < MENUCANT; i++) {
			menuName[i] = getString(menuFilePath, "MENU" + String.valueOf(i));
		}
	}
	
	private void createActions() {
		accOptionMenu = new MyAction (this, menuName[1][selectedLenguage], new ImageIcon("icons/optionMenuIcon.png"), KeyEvent.VK_C);
		accOptionEdit = new MyAction (this, menuName[3][selectedLenguage], new ImageIcon("icons/optionEditIcon.png"), KeyEvent.VK_C);
		accLenguage1 = new MyAction (this, menuName[5][selectedLenguage], new ImageIcon("icons/lenguage1Icon.png"), KeyEvent.VK_C);
		accLenguage2 = new MyAction (this, menuName[6][selectedLenguage], new ImageIcon("icons/lenguage2Icon.png"), KeyEvent.VK_C);
		accLenguage3 = new MyAction (this, menuName[7][selectedLenguage], new ImageIcon("icons/lenguage3Icon.png"), KeyEvent.VK_C);
		
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
		
		tabbedPane.addTab(tabName[0][selectedLenguage], new ImageIcon("icons/tabIcon1.png"), new Tab1());
		tabbedPane.addTab(tabName[1][selectedLenguage], new ImageIcon("icons/tabIcon2.png"), new Tab2());
		tabbedPane.addTab(tabName[2][selectedLenguage], new ImageIcon("icons/tabIcon3.png"), new Tab3());
		
		return tabbedPane;
	}

	private JMenuBar createMenuBar() {
		JMenuBar bar = new JMenuBar();
		
		bar.add(createMenuFile());
		bar.add(createMenuEdit());
		bar.add(Box.createHorizontalGlue());
		bar.add(createMenuConfiguration());
		
		return bar;
	}

	private JMenu createMenuFile() {
		JMenu menuFile = new JMenu (menuName[0][selectedLenguage]);
		menuFile.add(createMenuFileInside());
		return menuFile;
	}

	private JMenuItem createMenuFileInside() {
		JMenuItem optionMenu = new JMenuItem(accOptionMenu);
		
		return optionMenu;
	}

	private JMenu createMenuEdit() {
		JMenu menuEdit = new JMenu (menuName[2][selectedLenguage]);
		menuEdit.add(createMenuEditInside());
		return menuEdit;
	}

	private JMenuItem createMenuEditInside() {
		JMenuItem optionEdit = new JMenuItem(accOptionEdit);
		
		return optionEdit;
	}

	private JMenu createMenuConfiguration() {
		JMenu menuConfiguration = new JMenu (menuName[4][selectedLenguage]);
		menuConfiguration.add(createMenuConfigurationLenguage());
		return menuConfiguration;
	}

	private JMenuItem createMenuConfigurationLenguage() {
		JMenu lenguage = new JMenu(menuName[5][selectedLenguage]);
		lenguage.add(accLenguage1);
		lenguage.add(accLenguage2);
		lenguage.add(accLenguage3);
		return lenguage;
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	public String[][] getTabName() {
		return tabName;
	}

	public String[][] getMenuName() {
		return menuName;
	}

	public int getSelectedLenguage() {
		return selectedLenguage;
	}

	public void setSelectedLenguage(int selectedLenguage) {
		this.selectedLenguage = selectedLenguage;
	}

}
