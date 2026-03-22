package view;

import javax.swing.JPanel;

import controller.Controller;

public class FinestraColtivatore extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	
	public FinestraColtivatore( Controller controller) {
		this.controller=controller;
	}
}
