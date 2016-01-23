package graphicInterface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import operation.Action;
import operation.Reader;

public class Tab2 extends JPanel implements ListSelectionListener, ActionListener, Observer {

	Window window;
	
	JList<String> list;
	DefaultListModel<String> listModel;
	ArrayList<Action> actionList;
	
	JLabel userLabel, dateLabel;
	JButton acceptButton, denyButton;

	private JPanel statisticsPanel;
	private JPanel historicalPanel;
	private JPanel infoPanel;
	
	private JPanel blindStatPanel;
	private JPanel heatingStatPanel;
	private JPanel lightStatPanel;
	private JPanel lightDivisionPanel;
	
	private JLabel blindLabel1;
	private JLabel blindLabel2;
	
	private JLabel heatingLabel1;
	private JLabel heatingLabel2;
	
	private JLabel lightLabel1;
	private JLabel lightLabel2;

	private JLabel light1Label1;
	private JLabel light1Label2;
	private JLabel light2Label1;
	private JLabel light2Label2;
	private JLabel light3Label1;
	private JLabel light3Label2;
	private JLabel light4Label1;
	private JLabel light4Label2;
	
	
	public Tab2 (Window window) {
		this.window = window;
		
		this.setLayout(new GridLayout(1, 2, 10, 10));
		this.add(historicalPanel());
		refreshHistoricList();
		
		this.add(statisticsPanel());

	}
	
	private Component historicalPanel() {
		historicalPanel = new JPanel(new BorderLayout());
		historicalPanel.add(listPanel(), BorderLayout.CENTER);
		historicalPanel.add(infoPanel(), BorderLayout.SOUTH);
		historicalPanel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[13][window.getSelectedLenguage()]));

		return historicalPanel;
	}
	
	private Component listPanel() {
		listModel = new DefaultListModel<String>();
		
		list = new JList<String>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.addListSelectionListener(this);
		
		JScrollPane listScrollPane = new JScrollPane(list);
		
		return listScrollPane;
	}

	private Component infoPanel() {
		infoPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		
		userLabel = new JLabel();
		userLabel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[15][window.getSelectedLenguage()]));
		infoPanel.add(userLabel);
		
		dateLabel = new JLabel();
		dateLabel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[16][window.getSelectedLenguage()]));
		infoPanel.add(dateLabel);

		infoPanel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[14][window.getSelectedLenguage()]));
		
		return infoPanel;
	}

	private Component statisticsPanel() {
		statisticsPanel = new JPanel(new GridLayout(2, 2));
		
		statisticsPanel.add(blindStatistics());
		statisticsPanel.add(heatingStatistics());
		statisticsPanel.add(lightStatistics());
		statisticsPanel.add(lightDivisionStatistics());
		statisticsPanel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[8][window.getSelectedLenguage()]));
		
		return statisticsPanel;
	}

	private Component blindStatistics() {
		blindStatPanel = new JPanel(new GridLayout(2, 1));
		
		int downTimes = 0;
		int upTimes = 0;
		
		for (int i = 0; i < actionList.size(); i++) {
			if (actionList.get(i).getAction() == Action.BLIND_DOWN) downTimes++;
			if (actionList.get(i).getAction() == Action.BLIND_UP) upTimes++;
		}

		String line1 = window.getActionsName()[Action.BLIND_UP - 1][window.getSelectedLenguage()] + ": " + upTimes;
		String line2 = window.getActionsName()[Action.BLIND_DOWN - 1][window.getSelectedLenguage()] + ": " + downTimes;
		
		blindLabel1 = new JLabel(line1);
		blindLabel2 = new JLabel(line2);
		
		blindStatPanel.add(blindLabel1);
		blindStatPanel.add(blindLabel2);
		
		blindStatPanel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[9][window.getSelectedLenguage()]));
		
		return blindStatPanel;
	}

	private Component heatingStatistics() {
		heatingStatPanel = new JPanel(new GridLayout(2, 1));
		
		int startTimes = 0;
		int stopTimes = 0;
		
		for (int i = 0; i < actionList.size(); i++) {
			if (actionList.get(i).getAction() == Action.HEATING_START) startTimes++;
			if (actionList.get(i).getAction() == Action.HEATING_STOP) stopTimes++;
		}

		String line1 = window.getActionsName()[Action.HEATING_START - 1][window.getSelectedLenguage()] + ": " + startTimes;
		String line2 = window.getActionsName()[Action.HEATING_STOP - 1][window.getSelectedLenguage()] + ": " + stopTimes;
		
		heatingLabel1 = new JLabel(line1);
		heatingLabel2 = new JLabel(line2);
		
		heatingStatPanel.add(heatingLabel1);
		heatingStatPanel.add(heatingLabel2);
		
		heatingStatPanel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[10][window.getSelectedLenguage()]));
		
		return heatingStatPanel;
	}

	private Component lightStatistics() {
		lightStatPanel = new JPanel(new GridLayout(2, 1));
		
		int onTimes = 0;
		int offTimes = 0;
		
		for (int i = 0; i < actionList.size(); i++) {
			if (actionList.get(i).getAction() == Action.LIGHT1_ON) onTimes++;
			if (actionList.get(i).getAction() == Action.LIGHT2_ON) onTimes++;
			if (actionList.get(i).getAction() == Action.LIGHT3_ON) onTimes++;
			if (actionList.get(i).getAction() == Action.LIGHT4_ON) onTimes++;
			if (actionList.get(i).getAction() == Action.LIGHT1_OFF) offTimes++;
			if (actionList.get(i).getAction() == Action.LIGHT2_OFF) offTimes++;
			if (actionList.get(i).getAction() == Action.LIGHT3_OFF) offTimes++;
			if (actionList.get(i).getAction() == Action.LIGHT4_OFF) offTimes++;
		}

		String line1 = window.getActionsName()[Action.LIGHT_ON - 1][window.getSelectedLenguage()] + ": " + onTimes;
		String line2 = window.getActionsName()[Action.LIGHT_OFF - 1][window.getSelectedLenguage()] + ": " + offTimes;
		
		lightLabel1 = new JLabel(line1);
		lightLabel2 = new JLabel(line2);
		
		lightStatPanel.add(lightLabel1);
		lightStatPanel.add(lightLabel2);
		
		lightStatPanel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[11][window.getSelectedLenguage()]));
		
		return lightStatPanel;
	}

	private Component lightDivisionStatistics() {
		lightDivisionPanel = new JPanel(new GridLayout(4, 2));
		
		int onTimes1 = 0, onTimes2 = 0, onTimes3 = 0, onTimes4 = 0;
		int offTimes1 = 0, offTimes2 = 0, offTimes3 = 0, offTimes4 = 0;
		
		for (int i = 0; i < actionList.size(); i++) {
			if (actionList.get(i).getAction() == Action.LIGHT1_ON) onTimes1++;
			if (actionList.get(i).getAction() == Action.LIGHT2_ON) onTimes2++;
			if (actionList.get(i).getAction() == Action.LIGHT3_ON) onTimes3++;
			if (actionList.get(i).getAction() == Action.LIGHT4_ON) onTimes4++;
			if (actionList.get(i).getAction() == Action.LIGHT1_OFF) offTimes1++;
			if (actionList.get(i).getAction() == Action.LIGHT2_OFF) offTimes2++;
			if (actionList.get(i).getAction() == Action.LIGHT3_OFF) offTimes3++;
			if (actionList.get(i).getAction() == Action.LIGHT4_OFF) offTimes4++;
		}

		String line1 = window.getActionsName()[Action.LIGHT1_ON - 1][window.getSelectedLenguage()] + ": " + onTimes1;
		String line2 = window.getActionsName()[Action.LIGHT1_OFF - 1][window.getSelectedLenguage()] + ": " + offTimes1;
		String line3 = window.getActionsName()[Action.LIGHT2_ON - 1][window.getSelectedLenguage()] + ": " + onTimes2;
		String line4 = window.getActionsName()[Action.LIGHT2_OFF - 1][window.getSelectedLenguage()] + ": " + offTimes2;
		String line5 = window.getActionsName()[Action.LIGHT3_ON - 1][window.getSelectedLenguage()] + ": " + onTimes3;
		String line6 = window.getActionsName()[Action.LIGHT3_OFF - 1][window.getSelectedLenguage()] + ": " + offTimes3;
		String line7 = window.getActionsName()[Action.LIGHT4_ON - 1][window.getSelectedLenguage()] + ": " + onTimes4;
		String line8 = window.getActionsName()[Action.LIGHT4_OFF - 1][window.getSelectedLenguage()] + ": " + offTimes4;

		light1Label1 = new JLabel(line1);
		light1Label2 = new JLabel(line2);
		light2Label1 = new JLabel(line3);
		light2Label2 = new JLabel(line4);
		light3Label1 = new JLabel(line5);
		light3Label2 = new JLabel(line6);
		light4Label1 = new JLabel(line7);
		light4Label2 = new JLabel(line8);
		
		lightDivisionPanel.add(light1Label1);
		lightDivisionPanel.add(light1Label2);
		lightDivisionPanel.add(light2Label1);
		lightDivisionPanel.add(light2Label2);
		lightDivisionPanel.add(light3Label1);
		lightDivisionPanel.add(light3Label2);
		lightDivisionPanel.add(light4Label1);
		lightDivisionPanel.add(light4Label2);
		
		lightDivisionPanel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[12][window.getSelectedLenguage()]));
		
		return lightDivisionPanel;
	}
	
	public void refresh () {
		statisticsPanel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[8][window.getSelectedLenguage()]));
		historicalPanel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[12][window.getSelectedLenguage()]));
		
	
		infoPanel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[13][window.getSelectedLenguage()]));
		userLabel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[14][window.getSelectedLenguage()]));
		dateLabel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[14][window.getSelectedLenguage()]));
		
		refreshHistoricList();
		
	}
	
	public void refreshHistoricList () {
		String data[];
		int i = 0;
		
		actionList = new ArrayList<Action>();
		listModel.removeAllElements();
		
		while ((data = Reader.getString(window.actionsRegisterFilePath, "REG" + String.valueOf(i))) != null) {
			actionList.add(new Action(Integer.valueOf(data[1]), Integer.valueOf(data[2]), Long.valueOf(data[0])));
			listModel.addElement(window.getActionsName()[actionList.get(i).getAction()][window.getSelectedLenguage()]);
			
			i++;
		}
		
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		long val = actionList.get(e.getFirstIndex()).getTime();
		Date date = new Date(val);
		SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
		String dateText = df.format(date);
		
		userLabel.setText(String.valueOf(actionList.get(e.getFirstIndex()).getUser()));
		dateLabel.setText(dateText);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Observable o, Object arg) {
		Action action = (Action) arg;
		
		actionList.add(action);
		listModel.addElement(window.getActionsName()[action.getAction()][window.getSelectedLenguage()]);
	}
	
	public void deleteHistorial() {
		listModel.removeAllElements();
	}

	public void copyHistorial() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		File desktop = new File(System.getProperty("user.home"), "Desktop");
		fileChooser.setCurrentDirectory(desktop);
		
		if (fileChooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
			String path = fileChooser.getSelectedFile().getAbsolutePath();
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
			String dateText = df.format(date);
			String name = "/historial(" + dateText + ").txt";
			PrintWriter writer = null;
			try {
				writer = new PrintWriter(path + name, "UTF-8");
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for (int i = 0; i < actionList.size(); i++) {
				writer.println(actionList.get(i).toString());
			}
			writer.close();
		} else {
			JOptionPane.showMessageDialog(null, "Alert", "Something has gone wrong", JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
