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
    		if(u.getRuolo()==TipoRuolo.PROPRIETARIO) {
    			cardPanel.mostraPanel("proprietario");
    		}
    		if (u.getRuolo()==TipoRuolo.COLTIVATORE) {
    			cardPanel.mostraPanel("coltivatore");
    		}
    		if (u.getRuolo()==TipoRuolo.PROPRIETARIO_COLTIVATORE) {
    			cardPanel.mostraPanel("proprietario-coltivatore");
    		}
    		
    	}
    		else {
    			System.out.println("NO");
    		}
    	}
    	
    
    
    public void mostraPanel(String testo) {
    	cardPanel.mostraPanel(testo);
    }
    
    public void gestisciSceltaRuolo(String ruolo) {
    	if(ruolo=="PROPRIETARIO") {
    		cardPanel.mostraPanel("iscrizione proprietario");
    	}
    	else if(ruolo=="COLTIVATORE") {
    		cardPanel.mostraPanel("iscrizione coltivatore");
    	}
    	else {
    		cardPanel.mostraPanel("iscrizione proprietario");
    	}
    }

	public static Utente getUtenteLoggato() {
		return utenteLoggato;
	}

	public static void setUtenteLoggato(Utente utenteLoggato) {
		Controller.utenteLoggato = utenteLoggato;
	}
}
