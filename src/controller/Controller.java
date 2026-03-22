package controller;

import view.CardPanel;
import view.MainFrame;

public class Controller {

	private MainFrame frame;
	private CardPanel cardPanel;

    public Controller() {
       
        frame= new MainFrame(this);
        cardPanel= frame.getCardPanel();
        
    }
    
    public void mostraPanel(String testo) {
    	cardPanel.mostraPanel("login");
    }
}
