package graphicInterface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import operation.Action;
import operation.Reader;

public class Tab2 extends JPanel implements ListSelectionListener, ActionListener {

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
	
	
	public Tab2 (Window window) {
		this.window = window;
		
		this.setLayout(new GridLayout(1, 2, 10, 10));
		this.add(statisticsPanel());
		this.add(historicalPanel());
		
		refreshHistoricList();
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
		blindStatPanel = new JPanel();
		
		blindStatPanel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[9][window.getSelectedLenguage()]));
		
		return blindStatPanel;
	}

	private Component heatingStatistics() {
		heatingStatPanel = new JPanel();
		
		heatingStatPanel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[10][window.getSelectedLenguage()]));
		
		return heatingStatPanel;
	}

	private Component lightStatistics() {
		lightStatPanel = new JPanel();
		
		lightStatPanel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[11][window.getSelectedLenguage()]));
		
		return lightStatPanel;
	}

	private Component lightDivisionStatistics() {
		lightDivisionPanel = new JPanel();
		
		lightDivisionPanel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[12][window.getSelectedLenguage()]));
		
		return lightDivisionPanel;
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
		ArrayList<Action> actionList = window.getActionHistorical();
		
		for (int i = 0; i < actionList.size(); i++) {
			listModel.addElement(window.getActionsName()[actionList.get(0).getAction()][window.getSelectedLenguage()]);
		}
		
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
}
