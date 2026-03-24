package controller;

import model.*;
import view.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import dao.*;

public class Controller {

	private static Utente utenteLoggato;
	private MainFrame frame;
	private CardPanel cardPanel;
	private FinestraLogin finestraLogin;
	private UtenteDao utenteDao;
	private FinestraIscrizioneColtivatore finestraIscrizioneColtivatore;
	private FinestraIscrizioneProprietario finestraIscrizioneProprietario;
	private FinestraIscriviLotto finestraIscrizioneLotto;

    public Controller() {
       
        this.frame= new MainFrame(this);
        this.cardPanel= frame.getCardPanel();
        this.finestraLogin= frame.getCardPanel().getFinestraLogin();
        this.utenteDao= new UtenteDao();
        this.finestraIscrizioneColtivatore= frame.getCardPanel().getFinestraIscrizioneColtivatore();
        this.finestraIscrizioneProprietario= frame.getCardPanel().getFinestraIscrizioneProprietario();
        
    }
    
    public void validaLogin()  {
    	String username= finestraLogin.getUsername();
    	String password= new String(finestraLogin.getPassword());
    	
    	Utente u;
		try {
			u = utenteDao.preleva(username, password);
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
	    		
		} catch (SQLException e) {
			
			
			
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
    
    public void validaIscrizioneUtenteColtivatore()  {
    	 String nome = finestraIscrizioneColtivatore.getNomeInviato();
    	 String cognome = finestraIscrizioneColtivatore.getCognomeInviato();
    	 String username= finestraIscrizioneColtivatore.getUsernameInviato();
    	 String email = finestraIscrizioneColtivatore.getEmailInviata();
    	 String dataNascita= finestraIscrizioneColtivatore.getDataNascitaInviata();
    	 String password = finestraIscrizioneColtivatore.getPasswordInviata();
    	 String confermaPassword = finestraIscrizioneColtivatore.getConfermaPasswordInviata();
    	 
    	 finestraIscrizioneColtivatore.resetBordi();
    	 
    	 
    	 if(Utente.isSoloLettere(nome)==false) {
    		 finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpNome(),"Il nome deve essere di sole lettere.");
    		 return;
    	 }
    	 if(Utente.isLunghezzaValida(nome)==false) {
    		 finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpNome(),"La lunghezza del nome deve essere inferiore alle 30 lettere e superiore ad 1.");
    		 return;
    	 }
    	 if(Utente.isLunghezzaValida(cognome)==false) {
    		 finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpCognome(), "Il cognome deve essere di sole lettere.");
    		 return;
    	 }
    	 if (Utente.isSoloLettere(cognome)==false) {
    		 finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpCognome(),"La lunghezza del cognome deve essere inferiore alle 30 lettere e superiore ad 1.");
    		 return;
    	 }
    	 if(Utente.isLunghezzaValida(username)==false) {
    		 finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpUsername(),"La lunghezza dell'username deve essere inferiore alle 30 lettere e superiore a 1.");
    		 return;
    	 }
    	 if(Utente.isEmailValida(email)==false) {
    		 finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpEmail(), "L'email deve contenere una @ e un punto al suo interno nell'ordine giusto.");
    		 return;
    	 }
    	 if(Utente.isLunghezzaValida(email)==false) {
    		 finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpEmail(),"La lunghezza dell'email deve essere inferiore di 30 caratteri.");
    	 }
 
    	 LocalDate dataNascitaParse = null;

    	 try {
    	     dataNascitaParse = LocalDate.parse(dataNascita); 
    	     if (!Utente.isEtaCoerente(dataNascitaParse)) {
    	         finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpDataNascita(), "Devi avere tra 18 e 120 anni!");
    	         return;
    	     }
    	 } catch (DateTimeParseException e) {
    	     finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpDataNascita(), "Usa il formato YYYY-MM-DD (es. 1995-05-20)");
    	     return; 
    	 }
    	 
    	 if (password.isEmpty() || password.length() < 4) {
    		    finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpPassword(), "Minimo 4 caratteri");
    		    return;
    		}

    	if (!password.equals(confermaPassword)) {
    		    finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpConfermaPassword(), "Le password non coincidono!");
    		    return;
    		}
    	
    	Utente u = new Utente(nome, cognome, username, password, email, dataNascitaParse, TipoRuolo.COLTIVATORE);
    	try{
    		boolean salvato=utenteDao.salva(u);
    		if(salvato) {
    			cardPanel.mostraPanel("coltivatore");
    			setUtenteLoggato(u);
    		}
    		
    	}
    	catch(SQLException e) {
    		finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpEmail(), "Email già esistente.");
    	}
    	
    }
    
    public void validaIscrizioneUtenteProprietario() {
    	 String nome = finestraIscrizioneProprietario.getCmpNome();
    	 String cognome = finestraIscrizioneProprietario.getCmpCognome();
    	 String username= finestraIscrizioneProprietario.getCmpUsername();
    	 String email = finestraIscrizioneProprietario.getCmpEmail();
    	 String dataNascita= finestraIscrizioneProprietario.getCmpDataNascita();
    	 String password = finestraIscrizioneProprietario.getCmpPassword();
    	 String confermaPassword = finestraIscrizioneProprietario.getCmpConfermaPassword();
    	 
    	 finestraIscrizioneProprietario.resetBordi();
    	 
    	 
    	 
    	 if(Utente.isSoloLettere(nome)==false) {
    		 finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneColtivatore.getCmpNome(),"Il nome deve essere di sole lettere.");
    		 return;
    	 }
    	 if(Utente.isLunghezzaValida(nome)==false) {
    		 finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneColtivatore.getCmpNome(),"La lunghezza del nome deve essere inferiore alle 30 lettere e superiore ad 1.");
    		 return;
    	 }
    	 if(Utente.isLunghezzaValida(cognome)==false) {
    		 finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneColtivatore.getCmpCognome(), "Il cognome deve essere di sole lettere.");
    		 return;
    	 }
    	 if (Utente.isSoloLettere(cognome)==false) {
    		 finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneColtivatore.getCmpCognome(),"La lunghezza del cognome deve essere inferiore alle 30 lettere e superiore ad 1.");
    		 return;
    	 }
    	 if(Utente.isLunghezzaValida(username)==false) {
    		 finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneColtivatore.getCmpUsername(),"La lunghezza dell'username deve essere inferiore alle 30 lettere e superiore a 1.");
    		 return;
    	 }
    	 if(Utente.isEmailValida(email)==false) {
    		 finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneColtivatore.getCmpEmail(), "L'email deve contenere una @ e un punto al suo interno nell'ordine giusto.");
    		 return;
    	 }
    	 if(Utente.isLunghezzaValida(email)==false) {
    		 finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneColtivatore.getCmpEmail(),"La lunghezza dell'email deve essere inferiore di 30 caratteri.");
    	 }
 
    	 LocalDate dataNascitaParse = null;

    	 try {
    	     dataNascitaParse = LocalDate.parse(dataNascita); 
    	     if (!Utente.isEtaCoerente(dataNascitaParse)) {
    	         finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneColtivatore.getCmpDataNascita(), "Devi avere tra 18 e 120 anni!");
    	         return;
    	     }
    	 } catch (DateTimeParseException e) {
    	     finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneColtivatore.getCmpDataNascita(), "Usa il formato YYYY-MM-DD (es. 1995-05-20)");
    	     return; 
    	 }
    	 
    	 if (password.isEmpty() || password.length() < 4) {
    		    finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneColtivatore.getCmpPassword(), "Minimo 4 caratteri");
    		    return;
    		}

    	if (!password.equals(confermaPassword)) {
    		    finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneColtivatore.getCmpConfermaPassword(), "Le password non coincidono!");
    		    return;
    		}
    	
    	Utente u = new Utente(nome, cognome, username, password, email, dataNascitaParse, TipoRuolo.PROPRIETARIO);
    	
    	String tessitura= finestraIscrizioneLotto.getTipoTessitura();
    	String dimensioni= finestraIscrizioneLotto.getDimensioni();
    	String ph= finestraIscrizioneLotto.getPh();
    	String morfologia= finestraIscrizioneLotto.getTipoMorfologia();
    	String altitudine= finestraIscrizioneLotto.getAltitudine();
    	String localita= finestraIscrizioneLotto.getLocalità();
    	String comune= finestraIscrizioneLotto.getComune();
    	String provincia= finestraIscrizioneLotto.getProvincia().toUpperCase();
    	
    	int dimensioniInt = 0;
    	try {
    	   
    	    dimensioniInt = Integer.parseInt(finestraIscrizioneLotto.getDimensioni());
    	    if (LottoColtivabile.isValidDimensioni(dimensioniInt)) {
    	        finestraIscrizioneLotto.messaggioErrore(finestraIscrizioneLotto.getCmpDimensioni(), "Superficie non valida, deve essere compresa tra i 1000 e 1000000 mq.");
    	        return; 
    	    }
    	} catch (NumberFormatException e) {
    	    finestraIscrizioneLotto.messaggioErrore(finestraIscrizioneLotto.getCmpDimensioni(), "Inserire un numero intero.");
    	    return;
    	}
    	
    	double phDouble=0.0;
    	try {
    		phDouble= Double.parseDouble(finestraIscrizioneLotto.getPh());
    		if(LottoColtivabile.isPhValidoMioDominio(phDouble)) {
    			finestraIscrizioneLotto.messaggioErrore(finestraIscrizioneLotto.getCmpPh(), "Ph non valido, deve essere compreso tra 4 e 9.");
    			return;
    		}
    	}catch(NumberFormatException e) {
    		finestraIscrizioneLotto.messaggioErrore(finestraIscrizioneLotto.getCmpPh(), "Inserire un numero.");
    		return;
    	}
    	
    	int altitudineInt=0;
    	try {
    		altitudineInt= Integer.parseInt(finestraIscrizioneLotto.getAltitudine());
    		if(LottoColtivabile.isAltitudineValida(altitudineInt)) {
    			finestraIscrizioneLotto.messaggioErrore(finestraIscrizioneLotto.getCmpAltitudine(), "Altitudine non valida, deve essere compresa tra -20 e 3000.");
    			return;
    		}
    	}catch(NumberFormatException e) {
    		finestraIscrizioneLotto.messaggioErrore(finestraIscrizioneLotto.getCmpAltitudine(), "Inserire un numero intero.");
    		return;
    	}
    	
    	if(LottoColtivabile.isLunghezzaValida(localita)) {
    		finestraIscrizioneLotto.messaggioErrore(finestraIscrizioneLotto.getCmpLocalità(), "Inserire un numero di caratteri compreso tra 1 e 30.");
    		return;
    	}
    	if(LottoColtivabile.isSoloLettere(localita)) {
    		finestraIscrizioneLotto.messaggioErrore(finestraIscrizioneLotto.getCmpLocalità(), "Inserire solo lettere.");
    		return;
    	}
    	if(LottoColtivabile.isLunghezzaValida(comune)) {
    		finestraIscrizioneLotto.messaggioErrore(finestraIscrizioneLotto.getCmpComune(), "Inserire un numero di caratteri compreso tra 1 e 30.");
    		return;
    	}
    	if(LottoColtivabile.isSoloLettere(comune)) {
    		finestraIscrizioneLotto.messaggioErrore(finestraIscrizioneLotto.getCmpComune(),"Inserire solo lettere.");
    		return;
    	}
    	if(LottoColtivabile.isLunghezzaProvinciaValida(provincia)) {
    		finestraIscrizioneLotto.messaggioErrore(finestraIscrizioneLotto.getCmpProvincia(), "Inserire 2 caratteri.");
    		return;
    	}
    	if(LottoColtivabile.isSoloLettere(provincia)) {
    		finestraIscrizioneLotto.messaggioErrore(finestraIscrizioneLotto.getCmpProvincia(), "Inserire 2 caratteri.");
    	}
    	
    	TipoTessitura tessituraEnum = TipoTessitura.valueOf(tessitura.toUpperCase());
    	TipoMorfologia morfologiaEnum = TipoMorfologia.valueOf(morfologia.toUpperCase());
    			
    	LottoColtivabile lc= new LottoColtivabile(tessituraEnum,dimensioniInt, phDouble, morfologiaEnum, altitudineInt, localita, comune, provincia,u);
    	
    	
    	
    		boolean salvato=utenteDao.salvaConLotto(u, lc);
    		if(salvato) {
    			cardPanel.mostraPanel("proprietario");
    			setUtenteLoggato(u);
    		}
    		else {
    			
    		
    	}
    	
    	
    	
    	
    }

	public static Utente getUtenteLoggato() {
		return utenteLoggato;
	}

	public static void setUtenteLoggato(Utente utenteLoggato) {
		Controller.utenteLoggato = utenteLoggato;
	}
}
