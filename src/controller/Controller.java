package controller;

import model.*;
import view.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import dao.*;

public class Controller {

	private static Utente utenteLoggato;
	private MainFrame frame;
	private CardPanel cardPanel;
	private FinestraLogin finestraLogin;
	private UtenteDao utenteDao;
	private LottoDao lottoDao;
	private FinestraIscrizioneColtivatore finestraIscrizioneColtivatore;
	private FinestraIscrizioneProprietario finestraIscrizioneProprietario;
	private FinestraIscriviLotto finestraIscrizioneLotto;
	private FinestraSceltaRuolo finestraSceltaRuolo;
	private FinestraProprietario finestraProprietario;
	private FinestraVisualizzaLotti finestraVisualizzaLotti;
	private FinestraCreaLotto finestraCreaLotto;

    public Controller() {
       
        this.frame= new MainFrame(this);
        this.cardPanel= frame.getCardPanel();
        this.finestraLogin= frame.getCardPanel().getFinestraLogin();
        this.utenteDao= new UtenteDao();
        this.lottoDao= new LottoDao();
        this.finestraIscrizioneColtivatore= frame.getCardPanel().getFinestraIscrizioneColtivatore();
        this.finestraIscrizioneProprietario= frame.getCardPanel().getFinestraIscrizioneProprietario();
        this.finestraIscrizioneLotto= frame.getCardPanel().getFinestraIscriviLotto();
        this.finestraSceltaRuolo=frame.getCardPanel().getFinestraRuolo();
        this.finestraProprietario= frame.getCardPanel().getFinestraProprietario();
        this.finestraVisualizzaLotti= finestraProprietario.getFinVisualizzaLotti();
        this.finestraCreaLotto=finestraProprietario.getFinCreaLotto();
        
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
    
    public void mostraPanelInterno(String testo) {
    	finestraProprietario.mostraPanelInterno(testo);
    }
    
    
    public void aggiungiLotto()  {
    	String tessitura= finestraCreaLotto.getTipoTessitura();
    	String dimensioni= finestraCreaLotto.getDimensioni();
    	String ph= finestraCreaLotto.getPh();
    	String morfologia= finestraCreaLotto.getTipoMorfologia();
    	String altitudine= finestraCreaLotto.getAltitudine();
    	String localita= finestraCreaLotto.getLocalità();
    	String comune= finestraCreaLotto.getComune();
    	String provincia= finestraCreaLotto.getProvincia().toUpperCase();
    	
    	int dimensioniInt = 0;
    	try {
    	   
    	    dimensioniInt = Integer.parseInt(finestraCreaLotto.getDimensioni());
    	    if (!LottoColtivabile.isValidDimensioni(dimensioniInt)) {
    	        finestraCreaLotto.messaggioErrore(finestraCreaLotto.getCmpDimensioni(), "Superficie non valida, deve essere compresa tra i 1000 e 1000000 mq.");
    	        return; 
    	    }
    	} catch (NumberFormatException e) {
    	    finestraCreaLotto.messaggioErrore(finestraCreaLotto.getCmpDimensioni(), "Inserire un numero intero.");
    	    return;
    	}
    	
    	double phDouble=0.0;
    	try {
    		phDouble= Double.parseDouble(finestraCreaLotto.getPh());
    		if(!LottoColtivabile.isPhValidoMioDominio(phDouble)) {
    			finestraCreaLotto.messaggioErrore(finestraCreaLotto.getCmpPh(), "Ph non valido, deve essere compreso tra 4 e 9.");
    			return;
    		}
    	}catch(NumberFormatException e) {
    		finestraCreaLotto.messaggioErrore(finestraCreaLotto.getCmpPh(), "Inserire un numero.");
    		return;
    	}
    	
    	int altitudineInt=0;
    	try {
    		altitudineInt= Integer.parseInt(finestraCreaLotto.getAltitudine());
    		if(!LottoColtivabile.isAltitudineValida(altitudineInt)) {
    			finestraCreaLotto.messaggioErrore(finestraCreaLotto.getCmpAltitudine(), "Altitudine non valida, deve essere compresa tra -20 e 3000.");
    			return;
    		}
    	}catch(NumberFormatException e) {
    		finestraCreaLotto.messaggioErrore(finestraCreaLotto.getCmpAltitudine(), "Inserire un numero intero.");
    		return;
    	}
    	
    	if(!LottoColtivabile.isLunghezzaValida(localita)) {
    		finestraCreaLotto.messaggioErrore(finestraCreaLotto.getCmpLocalità(), "Inserire un numero di caratteri compreso tra 1 e 30.");
    		return;
    	}
    	if(!LottoColtivabile.isSoloLettere(localita)) {
    		finestraCreaLotto.messaggioErrore(finestraCreaLotto.getCmpLocalità(), "Inserire solo lettere.");
    		return;
    	}
    	if(!LottoColtivabile.isLunghezzaValida(comune)) {
    		finestraCreaLotto.messaggioErrore(finestraCreaLotto.getCmpComune(), "Inserire un numero di caratteri compreso tra 1 e 30.");
    		return;
    	}
    	if(!LottoColtivabile.isSoloLettere(comune)) {
    		finestraCreaLotto.messaggioErrore(finestraCreaLotto.getCmpComune(),"Inserire solo lettere.");
    		return;
    	}
    	if(!LottoColtivabile.isLunghezzaProvinciaValida(provincia)) {
    		finestraCreaLotto.messaggioErrore(finestraCreaLotto.getCmpProvincia(), "Inserire 2 caratteri.");
    		return;
    	}
    	if(!LottoColtivabile.isSoloLettere(provincia)) {
    		finestraCreaLotto.messaggioErrore(finestraCreaLotto.getCmpProvincia(), "Inserire 2 caratteri.");
    	}
    	
    	TipoTessitura tessituraEnum = TipoTessitura.valueOf(tessitura.toUpperCase());
    	TipoMorfologia morfologiaEnum = TipoMorfologia.valueOf(morfologia.toUpperCase());
    			
    	LottoColtivabile lc= new LottoColtivabile(tessituraEnum,dimensioniInt, phDouble, morfologiaEnum, altitudineInt, localita, comune, provincia,utenteLoggato);
    	
    	try{
    		if(lottoDao.salva(lc)) { 
    		caricaLotti();
    		mostraPanelInterno("visualizza lotti");
    		}
    	}catch(SQLException e) {
    		e.printStackTrace();
    		//System.out.println("salvataggio non riuscito.");
    		return;
    	}
    }
    
    public void caricaLotti() {
    	try{
    		ArrayList<LottoColtivabile> listaLotti;
    	int idUtente= utenteLoggato.getIdUtente();
    	listaLotti= lottoDao.prelevaLottiPerProprietario(idUtente);
    	finestraVisualizzaLotti.svuotaTabella();
    	
    	for(LottoColtivabile lc : listaLotti) {
    		Object[] riga= {
    				lc.getCodLotto(),      
    	            lc.getTessitura(),     
    	            lc.getDimensioni(),    
    	            lc.getPh(),            
    	            lc.getMorfologia(),
    	            lc.getAltitudine(),
    	            lc.getLocalita(),
    	            lc.getComune(),
    	            lc.getProvincia()
    		};
    		finestraVisualizzaLotti.aggiungiRigaTabella(riga);
    	}
    	
    	}catch(SQLException e){
    		System.out.println("errore");
    	}
    }
    	
    public void eliminaLotto() {
    	 int riga = finestraVisualizzaLotti.getTabella().getSelectedRow();
    	    if (riga != -1) {
    	        
    	        int idLotto = (int) finestraVisualizzaLotti.getModello().getValueAt(riga, 0);
    	        try {
    	            boolean successo = lottoDao.cancellaLotto(idLotto);
    	            if (successo) {
    	                caricaLotti(); 
    	                System.out.println("Lotto " + idLotto + " eliminato con successo.");
    	            }
    	        } catch (SQLException e) {
    	            e.printStackTrace();
    	        }
    	    } else {
    	        System.out.println("Nessun lotto selezionato.");
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
    		finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpEmail(), "Email o Username già esistenti.");
    		finestraIscrizioneLotto.messaggioErrore(finestraIscrizioneColtivatore.getCmpUsername(), "Email o Username già esistenti");
    	}
    	
    }
    
    public void validaIscrizioneUtenteProprietario() {
    	 String nome = finestraIscrizioneProprietario.getNome();
    	 String cognome = finestraIscrizioneProprietario.getCognome();
    	 String username= finestraIscrizioneProprietario.getUsername();
    	 String email = finestraIscrizioneProprietario.getEmail();
    	 String dataNascita= finestraIscrizioneProprietario.getDataNascita();
    	 String password = finestraIscrizioneProprietario.getPassword();
    	 String confermaPassword = finestraIscrizioneProprietario.getConfermaPassword();
    	 String ruolo= finestraSceltaRuolo.getSceltaRuolo();
    	 
    	 String ruoloComeEnum= ruolo.replace("/", "_").trim().toString();
    	 
    	 finestraIscrizioneProprietario.resetBordi();
    	
    	 
    	 
    	 
    	 if(Utente.isSoloLettere(nome)==false) {
    		 finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneProprietario.getCmpNome(),"Il nome deve essere di sole lettere.");
    		 return;
    	 }
    	 if(Utente.isLunghezzaValida(nome)==false) {
    		 finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneProprietario.getCmpNome(),"La lunghezza del nome deve essere inferiore alle 30 lettere e superiore ad 1.");
    		 return;
    	 }
    	 if(Utente.isLunghezzaValida(cognome)==false) {
    		 finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneProprietario.getCmpCognome(), "Il cognome deve essere di sole lettere.");
    		 return;
    	 }
    	 if (Utente.isSoloLettere(cognome)==false) {
    		 finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneProprietario.getCmpCognome(),"La lunghezza del cognome deve essere inferiore alle 30 lettere e superiore ad 1.");
    		 return;
    	 }
    	 if(Utente.isLunghezzaValida(username)==false) {
    		 finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneProprietario.getCmpUsername(),"La lunghezza dell'username deve essere inferiore alle 30 lettere e superiore a 1.");
    		 return;
    	 }
    	 if(Utente.isEmailValida(email)==false) {
    		 finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneProprietario.getCmpEmail(), "L'email deve contenere una @ e un punto al suo interno nell'ordine giusto.");
    		 return;
    	 }
    	 if(Utente.isLunghezzaValida(email)==false) {
    		 finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneProprietario.getCmpEmail(),"La lunghezza dell'email deve essere inferiore di 30 caratteri.");
    	 }
 
    	 LocalDate dataNascitaParse = null;

    	 try {
    	     dataNascitaParse = LocalDate.parse(dataNascita); 
    	     if (!Utente.isEtaCoerente(dataNascitaParse)) {
    	         finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneProprietario.getCmpDataNascita(), "Devi avere tra 18 e 120 anni!");
    	         return;
    	     }
    	 } catch (DateTimeParseException e) {
    	     finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneProprietario.getCmpDataNascita(), "Usa il formato YYYY-MM-DD (es. 1995-05-20)");
    	     return; 
    	 }
    	 
    	 if (password.isEmpty() || password.length() < 4) {
    		    finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneProprietario.getCmpPassword(), "Minimo 4 caratteri");
    		    return;
    		}

    	if (!password.equals(confermaPassword)) {
    		    finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneProprietario.getCmpConfermaPassword(), "Le password non coincidono!");
    		    return;
    		}
    	TipoRuolo tipoRuoloEnum= TipoRuolo.valueOf(ruoloComeEnum.toUpperCase());
    	
    	Utente u = new Utente(nome, cognome, username, password, email, dataNascitaParse, tipoRuoloEnum);
    	
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
    	    if (!LottoColtivabile.isValidDimensioni(dimensioniInt)) {
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
    		if(!LottoColtivabile.isPhValidoMioDominio(phDouble)) {
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
    		if(!LottoColtivabile.isAltitudineValida(altitudineInt)) {
    			finestraIscrizioneLotto.messaggioErrore(finestraIscrizioneLotto.getCmpAltitudine(), "Altitudine non valida, deve essere compresa tra -20 e 3000.");
    			return;
    		}
    	}catch(NumberFormatException e) {
    		finestraIscrizioneLotto.messaggioErrore(finestraIscrizioneLotto.getCmpAltitudine(), "Inserire un numero intero.");
    		return;
    	}
    	
    	if(!LottoColtivabile.isLunghezzaValida(localita)) {
    		finestraIscrizioneLotto.messaggioErrore(finestraIscrizioneLotto.getCmpLocalità(), "Inserire un numero di caratteri compreso tra 1 e 30.");
    		return;
    	}
    	if(!LottoColtivabile.isSoloLettere(localita)) {
    		finestraIscrizioneLotto.messaggioErrore(finestraIscrizioneLotto.getCmpLocalità(), "Inserire solo lettere.");
    		return;
    	}
    	if(!LottoColtivabile.isLunghezzaValida(comune)) {
    		finestraIscrizioneLotto.messaggioErrore(finestraIscrizioneLotto.getCmpComune(), "Inserire un numero di caratteri compreso tra 1 e 30.");
    		return;
    	}
    	if(!LottoColtivabile.isSoloLettere(comune)) {
    		finestraIscrizioneLotto.messaggioErrore(finestraIscrizioneLotto.getCmpComune(),"Inserire solo lettere.");
    		return;
    	}
    	if(!LottoColtivabile.isLunghezzaProvinciaValida(provincia)) {
    		finestraIscrizioneLotto.messaggioErrore(finestraIscrizioneLotto.getCmpProvincia(), "Inserire 2 caratteri.");
    		return;
    	}
    	if(!LottoColtivabile.isSoloLettere(provincia)) {
    		finestraIscrizioneLotto.messaggioErrore(finestraIscrizioneLotto.getCmpProvincia(), "Inserire 2 caratteri.");
    	}
    	
    	TipoTessitura tessituraEnum = TipoTessitura.valueOf(tessitura.toUpperCase());
    	TipoMorfologia morfologiaEnum = TipoMorfologia.valueOf(morfologia.toUpperCase());
    			
    	LottoColtivabile lc= new LottoColtivabile(tessituraEnum,dimensioniInt, phDouble, morfologiaEnum, altitudineInt, localita, comune, provincia,u);
    	
    	
    	
    		boolean salvato=utenteDao.salvaConLotto(u, lc);
    		if(salvato) {
    			setUtenteLoggato(u);
    			if(u.getRuolo()==TipoRuolo.PROPRIETARIO) {
    				cardPanel.mostraPanel("proprietario");
    			}
    			else if (u.getRuolo()== TipoRuolo.PROPRIETARIO_COLTIVATORE) {
    				cardPanel.mostraPanel("proprietario-coltivatore");
    				
    			}
    			
    		}
    		else {
    			finestraIscrizioneLotto.messaggioErroreBottone(finestraIscrizioneLotto.getSalva(), "Salvataggio non riuscito");
    			return;
    		
    	}
    	
    }

	public static Utente getUtenteLoggato() {
		return utenteLoggato;
	}

	public static void setUtenteLoggato(Utente utenteLoggato) {
		Controller.utenteLoggato = utenteLoggato;
	}
}
