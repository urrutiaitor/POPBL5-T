package graphicInterface;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;

public class MyAction extends AbstractAction {
	String text;
	Window window;

	public MyAction(Window window, String text, Icon imagen, Integer nemonic) {
		super(text, imagen);
		this.text = text;
		this.putValue(Action.MNEMONIC_KEY, nemonic);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case window.getMenuName()[1][window.selectedLenguage]:
			System.out.println("SubFile");
			break;
		case window.getMenuName()[3][window.selectedLenguage]:
			System.out.println("SubEdit");
			break;
		case window.getMenuName()[6][window.selectedLenguage]:
			window.setSelectedLenguage(0);
			window.repaint();
			break;
		case window.getMenuName()[7][window.selectedLenguage]:
			window.setSelectedLenguage(1);
			window.repaint();
			break;
		case window.getMenuName()[8][window.selectedLenguage]:
			window.setSelectedLenguage(2);
			window.repaint();
			break;

		default:
			break;
		}
	}
}