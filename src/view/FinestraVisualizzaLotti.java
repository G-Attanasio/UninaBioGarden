package view;

import javax.swing.JPanel;

import controller.Controller;

public class FinestraVisualizzaLotti extends JPanel {

	private static final long serialVersionUID = 1L;

	private Controller controller;
	
	public FinestraVisualizzaLotti (Controller controller) {
		
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
}
