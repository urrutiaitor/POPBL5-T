package graphicInterface;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import operation.House;
import operation.Reader;

public class Window extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	House house;

	String tabName[][];
	String menuName[][];
	String actionsName[][];
	String objectsName[][];

	int selectedLenguage = 2;

	String tabFilePath = "tabFile.txt";
	String menuFilePath = "menuFile.txt";
	String actionsFilePath = "actionsFile.txt";
	String objectsFilePath = "objectsFile.txt";

	final static int LENGUAGECANT = 3;
	final static int TABCANT = 3;
	final static int MENUCANT = 9;
	final static int ACTIONCANT = 16;
	final static int OBJECTCANT = 8;

	JTabbedPane tabbedPane;
	JMenu menuFile, menuEdit, menuConfiguration, lenguage;
	JMenuItem optionMenu, optionEdit;
	JMenuItem lenguage1, lenguage2, lenguage3;
	Tab1 tab1;
	Tab2 tab2;
	Tab3 tab3;

	public Window (House house) {
		this.house = house;
		
		initializeNames();
		
		this.setJMenuBar(createMenuBar());
		this.setContentPane(createFrame());
		this.setLocation(200, 200);
		this.setSize(640, 480);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void initializeNames() {
		tabName = new String[TABCANT][LENGUAGECANT];
		menuName = new String[MENUCANT][LENGUAGECANT];
		actionsName = new String[ACTIONCANT][LENGUAGECANT];
		objectsName = new String[OBJECTCANT][LENGUAGECANT];

		for (int i = 0; i < TABCANT; i++) {
			tabName[i] = Reader.getString(tabFilePath, "TAB" + String.valueOf(i));
		}

		for (int i = 0; i < MENUCANT; i++) {
			menuName[i] = Reader.getString(menuFilePath, "MENU" + String.valueOf(i));
		}
		
		for (int i = 0; i < ACTIONCANT; i++) {
			actionsName[i] = Reader.getString(actionsFilePath, "ACTION" + String.valueOf(i));
		}
		
		for (int i = 0; i < OBJECTCANT; i++) {
			objectsName[i] = Reader.getString(objectsFilePath, "OBJECT" + String.valueOf(i));
		}
	}

	

	private Container createFrame() {
		tabbedPane = new JTabbedPane();

		tabbedPane.addTab(tabName[0][selectedLenguage], new ImageIcon("icons/tabIcon1.png"), tab1 = new Tab1(this));
		tabbedPane.addTab(tabName[1][selectedLenguage], new ImageIcon("icons/tabIcon2.png"), tab2 = new Tab2());
		tabbedPane.addTab(tabName[2][selectedLenguage], new ImageIcon("icons/tabIcon3.png"), tab3 = new Tab3());

		house.addObserver((Observer) tab1);
		
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
		menuFile = new JMenu(menuName[0][selectedLenguage]);
		menuFile.add(createMenuFileInside());
		return menuFile;
	}

	private JMenuItem createMenuFileInside() {
		optionMenu = new JMenuItem(menuName[1][selectedLenguage]);
		optionMenu.addActionListener(this);
		return optionMenu;
	}

	private JMenu createMenuEdit() {
		menuEdit = new JMenu(menuName[2][selectedLenguage]);
		menuEdit.add(createMenuEditInside());
		return menuEdit;
	}

	private JMenuItem createMenuEditInside() {
		optionEdit = new JMenuItem(menuName[3][selectedLenguage]);
		optionEdit.addActionListener(this);
		return optionEdit;
	}

	private JMenu createMenuConfiguration() {
		menuConfiguration = new JMenu(menuName[4][selectedLenguage]);
		menuConfiguration.add(createMenuConfigurationLenguage());
		return menuConfiguration;
	}

	private JMenuItem createMenuConfigurationLenguage() {
		lenguage = new JMenu(menuName[5][selectedLenguage]);

		lenguage.add(lenguage1 = new JMenuItem(menuName[6][selectedLenguage]));
		lenguage.add(lenguage2 = new JMenuItem(menuName[7][selectedLenguage]));
		lenguage.add(lenguage3 = new JMenuItem(menuName[8][selectedLenguage]));

		lenguage1.addActionListener(this);
		lenguage2.addActionListener(this);
		lenguage3.addActionListener(this);

		return lenguage;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();

		if (menuName[1][selectedLenguage].matches(ac)) {
			System.out.println("SubFile");
		}
		if (menuName[3][selectedLenguage].matches(ac)) {
			System.out.println("SubEdit");
		}
		if (menuName[6][selectedLenguage].matches(ac)) {
			selectedLenguage = 0;
			refresh();
		}
		if (menuName[7][selectedLenguage].matches(ac)) {
			selectedLenguage = 1;
			refresh();
		}
		if (menuName[8][selectedLenguage].matches(ac)) {
			selectedLenguage = 2;
			refresh();
		}
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
	
	public String[][] getActionsName() {
		return actionsName;
	}
	
	public String[][] getObjectsName() {
		return objectsName;
	}

	public void refresh() {
		tabbedPane.setTitleAt(0, tabName[0][selectedLenguage]);
		tabbedPane.setTitleAt(1, tabName[1][selectedLenguage]);
		tabbedPane.setTitleAt(2, tabName[2][selectedLenguage]);

		menuFile.setText(menuName[0][selectedLenguage]);
		optionMenu.setText(menuName[1][selectedLenguage]);
		
		menuEdit.setText(menuName[2][selectedLenguage]);
		optionEdit.setText(menuName[3][selectedLenguage]);
		
		menuConfiguration.setText(menuName[4][selectedLenguage]);
		lenguage.setText(menuName[5][selectedLenguage]);
		lenguage1.setText(menuName[6][selectedLenguage]);
		lenguage2.setText(menuName[7][selectedLenguage]);
		lenguage3.setText(menuName[8][selectedLenguage]);
		
		tab1.refresh();

	}
	
}
