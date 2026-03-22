package controller;

import model.*;
import view.*;
import dao.*;

public class Controller {

	private static Utente utenteLoggato;
	private MainFrame frame;
	private CardPanel cardPanel;
	private FinestraLogin finestraLogin;
	UtenteDao utenteDao;

    public Controller() {
       
        this.frame= new MainFrame(this);
        this.cardPanel= frame.getCardPanel();
        this.finestraLogin= frame.getCardPanel().getFinestraLogin();
        this.utenteDao= new UtenteDao();
        
        
    }
    
    public void validaLogin() {
    	String username= finestraLogin.getUsername();
    	String password= new String(finestraLogin.getPassword());
    	
    	Utente u=utenteDao.preleva(username, password);
    	
    	if(u != null) {
    		setUtenteLoggato(u);
    		
    		
    	}
    		else {
    			System.out.println("NO");
    		}
    	}
    	
    
    
    public void mostraLogin() {
    	cardPanel.mostraPanel("login");
    }

	public static Utente getUtenteLoggato() {
		return utenteLoggato;
	}

	public static void setUtenteLoggato(Utente utenteLoggato) {
		Controller.utenteLoggato = utenteLoggato;
	}
}
