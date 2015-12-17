package graphicInterface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

public class Tab2 extends JPanel implements ListSelectionListener, ActionListener {

	Window window;
	
	JList<String> list;
	DefaultListModel<String> listModel;
	
	JLabel userLabel, dateLabel;
	JButton acceptButton, denyButton;

	private JPanel confirmationPanel;
	private JPanel buttonPanel;
	private JPanel historicalPanel;
	private JPanel infoPanel;
	private JLabel inputLabel;
	
	
	public Tab2 (Window window) {
		this.window = window;
		
		this.setLayout(new GridLayout(1, 2, 10, 10));
		this.add(confirmationPanel());
		this.add(historicalPanel());
	}

	private Component confirmationPanel() {
		confirmationPanel = new JPanel(new BorderLayout());
		
		confirmationPanel.add(inputPanel(), BorderLayout.CENTER);
		confirmationPanel.add(buttonPanel(), BorderLayout.CENTER);
		confirmationPanel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[8][window.getSelectedLenguage()]));
		
		return confirmationPanel;
	}

	private Component inputPanel() {
		inputLabel = new JLabel();
		
		inputLabel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[9][window.getSelectedLenguage()]));
		
		
		return inputLabel;
	}

	private Component buttonPanel() {
		buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		
		buttonPanel.add(acceptButton = new JButton(window.getObjectsName()[10][window.getSelectedLenguage()]));
		buttonPanel.add(denyButton = new JButton(window.getObjectsName()[11][window.getSelectedLenguage()]));
		
		return buttonPanel;
	}

	private Component historicalPanel() {
		historicalPanel = new JPanel(new BorderLayout());
		historicalPanel.add(listPanel(), BorderLayout.CENTER);
		historicalPanel.add(infoPanel(), BorderLayout.SOUTH);
		historicalPanel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[12][window.getSelectedLenguage()]));

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
		userLabel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[14][window.getSelectedLenguage()]));
		infoPanel.add(userLabel);
		
		dateLabel = new JLabel();
		dateLabel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[14][window.getSelectedLenguage()]));
		infoPanel.add(dateLabel);

		infoPanel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[13][window.getSelectedLenguage()]));
		
		return infoPanel;
	}
	
	public void refresh () {
		confirmationPanel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[8][window.getSelectedLenguage()]));
		historicalPanel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[12][window.getSelectedLenguage()]));
		
		inputLabel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[9][window.getSelectedLenguage()]));
		acceptButton.setText(window.getObjectsName()[10][window.getSelectedLenguage()]);
		denyButton.setText(window.getObjectsName()[11][window.getSelectedLenguage()]);
	
		infoPanel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[13][window.getSelectedLenguage()]));
		userLabel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[14][window.getSelectedLenguage()]));
		dateLabel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[14][window.getSelectedLenguage()]));
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
