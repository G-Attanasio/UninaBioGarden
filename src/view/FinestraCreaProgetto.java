package view;

import java.awt.Color;

import javax.swing.JPanel;

import controller.Controller;

public class FinestraCreaProgetto extends JPanel {

	private static final long serialVersionUID = 1L;

	private Controller controller;
	
	public FinestraCreaProgetto(Controller controller) {
		this.controller=controller;
		
		
		setBackground(new Color(40,80,40));
	}
	

	
}
