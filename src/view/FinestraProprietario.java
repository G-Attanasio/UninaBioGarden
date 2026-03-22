package view;

import javax.swing.JPanel;

import controller.Controller;

public class FinestraProprietario extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	
	public FinestraProprietario(Controller controller) {
		this.controller=controller;
	}
}
