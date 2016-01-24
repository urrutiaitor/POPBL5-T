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
import java.util.concurrent.Semaphore;

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
import operation.BuzonSincrono;

public class Tab2 extends JPanel implements ListSelectionListener, ActionListener, Observer {

	Window window;
	
	JList<String> list;
	DefaultListModel<String> listModel;
	ArrayList<Action> actionList;
	
	JLabel userLabel, dateLabel;
	JButton acceptButton, denyButton;

	private JPanel confirmationPanel;
	private JPanel buttonPanel;
	private JPanel historicalPanel;
	private JPanel infoPanel;
	private JLabel inputLabel;
	
	String defaultMessage;
	String message = null;
	BuzonSincrono buzon;
	
	
	public Tab2 (Window window) {
		this.window = window;
		defaultMessage = window.getObjectsName()[16][window.getSelectedLenguage()];
		
		this.setLayout(new GridLayout(1, 2, 10, 10));
		this.add(confirmationPanel());
		this.add(historicalPanel());
		
	}

	private Component confirmationPanel() {
		confirmationPanel = new JPanel(new BorderLayout());
		buttonPanel = new JPanel(new GridLayout(1, 2));
		
		confirmationPanel.add(inputPanel(), BorderLayout.CENTER);
		confirmationPanel.add(buttonPanel(), BorderLayout.SOUTH);
		
		confirmationPanel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[8][window.getSelectedLenguage()]));
		
		return confirmationPanel;
	}

	private Component inputPanel() {
		inputLabel = new JLabel(defaultMessage);
		
		inputLabel.setHorizontalTextPosition(JLabel.CENTER);
		inputLabel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[9][window.getSelectedLenguage()]));
		
		return inputLabel;
	}

	private Component buttonPanel() {
		buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		
		buttonPanel.add(acceptButton = new JButton(window.getObjectsName()[10][window.getSelectedLenguage()]));
		buttonPanel.add(denyButton = new JButton(window.getObjectsName()[11][window.getSelectedLenguage()]));
		
		acceptButton.setEnabled(false);
		denyButton.setEnabled(false);
		
		acceptButton.addActionListener(this);
		denyButton.addActionListener(this);
		
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
		actionList = window.getActionHistorical();
		
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
		
		if (inputLabel.getText().equals(defaultMessage)) {
			defaultMessage = window.getObjectsName()[15][window.getSelectedLenguage()];
			inputLabel.setText(defaultMessage);
		} else {
			defaultMessage = window.getObjectsName()[15][window.getSelectedLenguage()];
		}
		
	}
	
	public void setMessage(int user, int action, BuzonSincrono buzon) {
		if (window.getSelectedLenguage() == 0) message = user + " erabiltzaileak " + action + " akzioa eskatu du";
		if (window.getSelectedLenguage() == 1) message = "Usuario " + user + " ha pedido acción " + action;
		if (window.getSelectedLenguage() == 2) message = "User " + user + " has requested action " + action;
		
		System.out.println("set message");
		
		acceptButton.setEnabled(true);
		denyButton.setEnabled(true);
		
		inputLabel.setText(message);
		this.buzon = buzon;
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
		if (window.getObjectsName()[10][window.getSelectedLenguage()] == e.getActionCommand()) { /* ACCEPTED */
			System.out.println("accepted");
			if (buzon == null) return;
			acceptButton.setEnabled(false);
			denyButton.setEnabled(false);
			
			inputLabel.setText(defaultMessage);
			buzon.send("ACCEPTED");
			
			
			
			buzon = null;
		}
		
		if (window.getObjectsName()[11][window.getSelectedLenguage()] == e.getActionCommand()) { /* DENIED */
			System.out.println("denied");
			if (buzon == null) return;
						
			buzon.send("DENIED");

			acceptButton.setEnabled(false);
			denyButton.setEnabled(false);
			
			inputLabel.setText(defaultMessage);
			
			buzon = null;
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		Action action = (Action) arg;
		System.out.println("Action add");
		actionList.add(action);
		listModel.addElement(window.getActionsName()[action.getAction()][window.getSelectedLenguage()]);
			
	}

	public void deleteHistorial() {
		listModel.removeAllElements();
		actionList = new ArrayList<Action>();
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
