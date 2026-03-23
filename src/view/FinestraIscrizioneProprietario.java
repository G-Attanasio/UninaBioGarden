package view;

import javax.swing.JPanel;

import controller.Controller;

public class FinestraIscrizioneProprietario extends JPanel{

	private static final long serialVersionUID = 1L;

	private Controller controller;
	
	public FinestraIscrizioneProprietario(Controller controller) {
		this.setController(controller);
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
}
