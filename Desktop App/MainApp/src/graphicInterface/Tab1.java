package graphicInterface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Tab1 extends JPanel implements Observer {

	Window window;
	
	final static String blindUpPath = "icons/blindUp.png";
	final static String blindDownPath = "icons/blindDown.png";
	final static String alarmOnPath = "icons/alarmOn.png";
	final static String alarmOffPath = "icons/alarmOff.png";
	final static String heatingOnPath = "icons/heatingOn.png";
	final static String heatingOffPath = "icons/heatingOff.png";
	final static String doorOpenedPath = "icons/doorOpened.png";
	final static String doorClosedPath = "icons/doorClosed.png";
	final static String lightOnPath = "icons/lightOn.png";
	final static String lightOffPath = "icons/lightOff.png";
	
	
	JLabel lightLabel[], blindLabel, alarmLabel, heatingLabel, doorLabel;
	ImageIcon lightOnIcon, lightOffIcon;
	ImageIcon blindUpIcon, blindDownIcon;
	ImageIcon alarmOnIcon, alarmOffIcon;
	ImageIcon heatingOnIcon, heatingOffIcon;
	ImageIcon doorOpenedIcon, doorClosedIcon;
	JLabel actionsLabel[];
	
	public Tab1 (Window window) {
		this.window = window;
		
		this.setLayout(new GridLayout(1, 2));
		this.add(ilustrationPanel());
		this.add(dataPanel());
	}

	private Component ilustrationPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1));
		
		JPanel subPanel = new JPanel(new GridLayout(2, 2));
		subPanel.add(createBlindPanel());
		subPanel.add(createHeatingPanel());
		subPanel.add(createDoorPanel());
		subPanel.add(createAlarmPanel());
		
		panel.add(subPanel);
		panel.add(createLightPanel());
		
		return panel;
	}

	private Component dataPanel() {
		JPanel panel = new JPanel(new GridLayout(8, 2));
		JPanel subPanel[] = new JPanel[16];
		actionsLabel = new JLabel[16];
		for (int i = 0; i < 16; i++) {
			subPanel[i] = new JPanel(new BorderLayout());
			subPanel[i].add(actionsLabel[i] = new JLabel(window.getActionsName()[i][window.getSelectedLenguage()]));
		}
		
		subPanel[0].add(new JLabel(blindUpIcon), BorderLayout.WEST);
		subPanel[1].add(new JLabel(blindDownIcon), BorderLayout.WEST);
		subPanel[2].add(new JLabel(alarmOnIcon), BorderLayout.WEST);
		subPanel[3].add(new JLabel(alarmOffIcon), BorderLayout.WEST);
		subPanel[4].add(new JLabel(heatingOnIcon), BorderLayout.WEST);
		subPanel[5].add(new JLabel(heatingOffIcon), BorderLayout.WEST);
		subPanel[6].add(new JLabel(doorOpenedIcon), BorderLayout.WEST);
		subPanel[7].add(new JLabel(doorClosedIcon), BorderLayout.WEST);
		subPanel[8].add(new JLabel(lightOnIcon), BorderLayout.WEST);
		subPanel[9].add(new JLabel(lightOffIcon), BorderLayout.WEST);
		subPanel[10].add(new JLabel(lightOnIcon), BorderLayout.WEST);
		subPanel[11].add(new JLabel(lightOffIcon), BorderLayout.WEST);
		subPanel[12].add(new JLabel(lightOnIcon), BorderLayout.WEST);
		subPanel[13].add(new JLabel(lightOffIcon), BorderLayout.WEST);
		subPanel[14].add(new JLabel(lightOnIcon), BorderLayout.WEST);
		subPanel[15].add(new JLabel(lightOffIcon), BorderLayout.WEST);
		
		for (int i = 0; i < 16; i++) {
			panel.add(subPanel[i]);
		}
		
		return panel;
	}
	
	private Component createBlindPanel() {
		blindUpIcon = new ImageIcon(blindUpPath);
		blindDownIcon = new ImageIcon(blindDownPath);
		blindLabel = new JLabel(blindDownIcon);
		blindLabel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[0][window.getSelectedLenguage()]));
		return blindLabel;
	}
	
	private Component createHeatingPanel() {
		heatingOnIcon = new ImageIcon(heatingOnPath);
		heatingOffIcon = new ImageIcon(heatingOffPath);
		heatingLabel = new JLabel(heatingOffIcon);
		heatingLabel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[1][window.getSelectedLenguage()]));
		return heatingLabel;
	}
	
	private Component createDoorPanel() {
		doorOpenedIcon = new ImageIcon(doorOpenedPath);
		doorClosedIcon = new ImageIcon(doorClosedPath);
		doorLabel = new JLabel(doorClosedIcon);
		doorLabel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[2][window.getSelectedLenguage()]));
		return doorLabel;
	}

	private Component createAlarmPanel() {
		alarmOnIcon = new ImageIcon(alarmOnPath);
		alarmOffIcon = new ImageIcon(alarmOffPath);
		alarmLabel = new JLabel(alarmOffIcon);
		alarmLabel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[3][window.getSelectedLenguage()]));
		return alarmLabel;
	}
	
	private Component createLightPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 2));
		
		lightLabel = new JLabel[4];
		
		lightOnIcon = new ImageIcon(lightOnPath);
		lightOffIcon = new ImageIcon(lightOffPath);
        for (int i = 0; i < 4; i++) {
        	lightLabel[i] = new JLabel(lightOffIcon);
        	lightLabel[i].setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[4 + i][window.getSelectedLenguage()]));
        	panel.add(lightLabel[i]);
        }
        
        return panel;
	}
	
	public void refresh() {
		blindLabel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[0][window.getSelectedLenguage()]));
		alarmLabel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[1][window.getSelectedLenguage()]));
		heatingLabel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[2][window.getSelectedLenguage()]));
		doorLabel.setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[3][window.getSelectedLenguage()]));
		
		for (int i = 0; i < 4; i++) {
			lightLabel[i].setBorder(BorderFactory.createTitledBorder(window.getObjectsName()[4 + i][window.getSelectedLenguage()]));
		}
		
		for (int i = 0; i < 16; i++) {
			actionsLabel[i].setText(window.getActionsName()[i][window.getSelectedLenguage()]);
		}

	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
}
