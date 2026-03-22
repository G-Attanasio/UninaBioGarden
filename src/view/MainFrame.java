package view;

import javax.swing.*;

import controller.Controller;


public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private CardPanel cardPanel;
	
	public MainFrame(Controller controller) {
		setTitle("UninaBioGarden");
		setSize(1200,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);
	    setVisible(true);
	    
	    cardPanel= new CardPanel(controller);
	    add(cardPanel);
	}
	 public CardPanel getCardPanel() {
	        return cardPanel;
	    }
}
