package view;

import javax.swing.JPanel;

import controller.Controller;

public class FinestraLogin extends JPanel {

	private Controller controller;

	public FinestraLogin(Controller controller) {
		this.controller=controller;
	}
	
	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
	
}
