package controller;

import model.*;

import view.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import dao.*;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.JFreeChart;


public class Controller {

	private static Utente utenteLoggato;
	private LottoColtivabile lottoSelezionato;
	private ArrayList<Attivita> attivitaTemporanee;
	private ArrayList<Attivita> attivitaTotali;
	private ArrayList<SeminaColtura> listaSeminaColtura;
	private ArrayList<ProgettoStagionale> progettiperLotto;
	private ArrayList<ProgettoStagionale> progettiTotaliEsistenti;
	private ArrayList<Notifica> notifiche;
	private MainFrame frame;
	private CardPanel cardPanel;
	private FinestraLogin finestraLogin;
	private UtenteDao utenteDao;
	private LottoDao lottoDao;
	private ColturaDao colturaDao;
	private AttivitaDao attivitaDao; 
	private ProgettoDao progettoDao;
	private NotificaDao notificaDao;
	private ReportDao reportDao;
	private FinestraIscrizioneColtivatore finestraIscrizioneColtivatore;
	private FinestraIscrizioneProprietario finestraIscrizioneProprietario;
	private FinestraIscriviLotto finestraIscrizioneLotto;
	private FinestraSceltaRuolo finestraSceltaRuolo;
	private FinestraProprietario finestraProprietario;
	private FinestraVisualizzaLotti finestraVisualizzaLotti;
	private FinestraCreaLotto finestraCreaLotto;
	private FinestraVisualizzaColture finestraVisualizzaColture;
	private FinestraCreaProgetto finestraCreaProgetto;
	private FinestraAttivitaAssegnate finestraAttivitaAssegnate;
	private FinestraVisualizzaProgetti finestraVisualizzaProgetti;
	private FinestraCreaNotifica finestraCreaNotifica;
	private FinestraVisualizzaNotifiche finestraVisualizzaNotifiche;
	private FinestraReport finestraReport;

    public Controller() {
       
        this.frame= new MainFrame(this);
        this.cardPanel= frame.getCardPanel();
        this.finestraLogin= frame.getCardPanel().getFinestraLogin();
        this.utenteDao= new UtenteDao();
        this.lottoDao= new LottoDao();
        this.colturaDao= new ColturaDao();
        this.attivitaDao= new AttivitaDao();
        this.progettoDao= new ProgettoDao();
        this.notificaDao= new NotificaDao();
        this.reportDao= new ReportDao();
        this.attivitaTemporanee= new ArrayList<Attivita>();
        this.attivitaTotali= new ArrayList<Attivita>();
        this.listaSeminaColtura= new ArrayList<SeminaColtura>();
        this.progettiperLotto= new ArrayList<ProgettoStagionale>();
        this.notifiche= new ArrayList<Notifica>();
        this.finestraIscrizioneColtivatore= frame.getCardPanel().getFinestraIscrizioneColtivatore();
        this.finestraIscrizioneProprietario= frame.getCardPanel().getFinestraIscrizioneProprietario();
        this.finestraIscrizioneLotto= frame.getCardPanel().getFinestraIscriviLotto();
        this.finestraSceltaRuolo=frame.getCardPanel().getFinestraRuolo();
        this.finestraProprietario= frame.getCardPanel().getFinestraProprietario();
        this.finestraVisualizzaLotti= finestraProprietario.getFinVisualizzaLotti();
        this.finestraCreaLotto=finestraProprietario.getFinCreaLotto();
        this.finestraVisualizzaColture=finestraProprietario.getFinVisualizzaColture();
        this.finestraCreaProgetto= finestraProprietario.getFinCreaProgetto();
        this.finestraAttivitaAssegnate=finestraProprietario.getFinAttivitaAssegnate();
        this.finestraVisualizzaProgetti=finestraProprietario.getFinVisualizzaProgetti();
        this.finestraCreaNotifica=finestraProprietario.getFinCreaNotifica();
        this.finestraVisualizzaNotifiche=finestraProprietario.getFinVisualizzaNotifiche();
        this.finestraReport= finestraProprietario.getFinReport();
    }
    
    public void validaLogin()  {
    	String username= finestraLogin.getUsername();
    	String password= new String(finestraLogin.getPassword());   
    	if(username.isEmpty() || password.isEmpty()) {
    		finestraLogin.erroreLogin();
    	}
    	Utente u;
		try {
			u = utenteDao.preleva(username, password);
			if(u != null) {
	    		setUtenteLoggato(u);
	    		if(u.getRuolo()==TipoRuolo.PROPRIETARIO) {
	    			finestraLogin.pulisciCampi();
	    			cardPanel.mostraPanel("proprietario");
	    		}
	    		if (u.getRuolo()==TipoRuolo.COLTIVATORE) {
	    			finestraLogin.pulisciCampi();
	    			cardPanel.mostraPanel("coltivatore");
	    		}
	    		if (u.getRuolo()==TipoRuolo.PROPRIETARIO_COLTIVATORE) {
	    			finestraLogin.pulisciCampi();
	    			cardPanel.mostraPanel("proprietario-coltivatore");
	    		}	    		
	    	}	    		
		} catch (SQLException e) {			
			finestraLogin.erroreLogin();			
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
    		finestraCreaLotto.pulisciCampi();
    		mostraPanelInterno("visualizza lotti");
    		}
    	}catch(SQLException e) {
    		e.printStackTrace();
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
    
    public void caricaAttivitaAssegnate() {
    	   try { 
    	        ArrayList<Attivita> tutte = attivitaDao.prelevaAttivitaAssegnateDaProprietario(utenteLoggato.getIdUtente());
    	        finestraAttivitaAssegnate.svuotaTabella(); 	         	       
    	        for (Attivita a : tutte) {   	            
    	        	String tipo = "";
    	        	String metodo="";
    	        	if (a instanceof Semina) {
    	                tipo = "Semina";
    	                metodo = ((Semina) a).getMetodoSemina().toString(); 
    	            } 
    	            else if (a instanceof Raccolta) {
    	                tipo = "Raccolta";
    	                metodo = ((Raccolta) a).getMetodoRaccolta().toString(); 
    	            } 
    	            else if (a instanceof Irrigazione) {
    	                tipo = "Irrigazione";
    	                metodo = ((Irrigazione) a).getMetodoIrrigazione().toString();
    	            }
    	            Object[] riga = {
    	            	tipo,
    	                a.getColtivatore().getUsername(),
    	                a.getProgetto().getNomeProgetto(),
    	                metodo,
    	                a.getDataInizio(),
    	                a.getDataFine(),
    	                a.getStatoEsecuzione()
    	            };
    	            finestraAttivitaAssegnate.aggiungiRigaTabella(riga);
    	        }
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	       
    	    }
    }
    
    public void caricaIMieiProgetti() {
    	try {
    		ArrayList<ProgettoStagionale> lista= progettoDao.prelevaProgettiPerProprietario(utenteLoggato.getIdUtente());
    		finestraVisualizzaProgetti.svuotaTabella();
    		 for (ProgettoStagionale p : lista) {
    	            Object[] riga = {
    	            	p.getCodProgetto(),	
    	                p.getNomeProgetto(),
    	                p.getLottoImpegnato().getCodLotto(),
    	                p.getStagioneDiRiferimento(),
    	                p.getDataInizio(),
    	                p.getDurata() + " giorni",
    	                p.getStatoEsecuzione()
    	            };
    	            finestraVisualizzaProgetti.aggiungiRigaTabella(riga);
    	        }
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    }
    	
    }
    
    public void caricaColture() {
    	try {
    		ArrayList<Coltura> listaColture;
    		finestraVisualizzaColture.svuotaTabella();
    		listaColture=colturaDao.preleva();
    		for(Coltura c : listaColture) {
    			Object[] riga= {
    					c.getCodColtura(),
    					c.getNome(),
    					c.getSpecie(),
    					c.getFamiglia(),
    					c.getTempoMaturazione(),
    					c.getDestinazioneUso(),
    					c.getPeriodoIdeale()
    			};
    			finestraVisualizzaColture.aggiungiRigaTabella(riga);
    		}
    		
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    public void avviaProgetto(int codLotto) {
    	 try {
    	        this.lottoSelezionato = lottoDao.preleva(codLotto);
    	        if (this.lottoSelezionato != null) {
    	            caricaColtureInCreaProgetto();
    	            finestraProprietario.mostraPanelInterno("crea progetto");    	            
    	        }
    	    } catch (SQLException e) {
    	    	
    	    }
    }
    
    public void caricaColtureInCreaProgetto() {
    	try {
    		ArrayList<Coltura> listaColture;
    		ArrayList<String> lista= new ArrayList<>();
    		listaColture= colturaDao.preleva();
    		for(Coltura c : listaColture) {
    			 lista.add(c.getNome());
    	}
    		finestraCreaProgetto.popolaColture(lista);
    }catch(SQLException e) {
    	e.printStackTrace();
    }
    }
    
    public void caricaColtivatoriInProgetto() {
    	try{
    		ArrayList<String> listaColtivatori= new ArrayList<String>();
    		listaColtivatori=utenteDao.prelevaPerProgetto();
    		finestraCreaProgetto.setElencoColtivatori(listaColtivatori); 		
    	}catch(SQLException e) {
    		e.printStackTrace();
    		
        }
    }
    
    public void caricaColtivatoriInAttivitaProgetto(String coltura){
    	try{
    		ArrayList<String> listaColtivatori= new ArrayList<String>();
    		listaColtivatori=utenteDao.prelevaPerProgetto();
    		finestraCreaProgetto.setElencoColtivatori(listaColtivatori);
    		finestraCreaProgetto.pianificaAttivita(listaColtivatori,coltura);  		
    	}catch(SQLException e) {
    		e.printStackTrace();
    		
        }
    }
    	
    public void caricaColtivatoriComeDestinatari() {
    	try{
    		ArrayList<String> listaColtivatori= new ArrayList<String>();
    		listaColtivatori=utenteDao.prelevaPerProgetto();
    		finestraCreaNotifica.setElencoColtivatori(listaColtivatori);  		
    	}catch(SQLException e) {
    		e.printStackTrace();
    		
        }
    }
    
    public void caricaNotificheInviate() {
    	try{
    		ArrayList<Notifica> listaNotifiche= new ArrayList<Notifica>();
    		listaNotifiche=notificaDao.prelevaNotificheInviate(utenteLoggato.getIdUtente());
    		finestraVisualizzaNotifiche.svuotaTabella();	
    		
    		for (Notifica n : listaNotifiche) {
                String tipo = "";
                String scadenza = "---";
                String descrizioneVeloce= "---";
                String gravita = "---";
                String estensione = "---";
                String descrizione="---";

              
                if (n instanceof AttivitaImminente) {
                    tipo = "IMMINENTE";
                    AttivitaImminente imm = (AttivitaImminente) n;
                    scadenza = imm.getDataScadenza().toString();
                    descrizioneVeloce=imm.getTipoAttivitaImminente();
                    descrizione=imm.getDescrizione();
                } 
                else if (n instanceof Anomalia) {
                    tipo = "ANOMALIA";
                    Anomalia ano = (Anomalia) n;
                    gravita = ano.getGravita().toString();
                    descrizioneVeloce= ano.getTipoAnomalia();
                    descrizione=ano.getDescrizione();
                    if(ano.getEstensione() > 0) {
                    	estensione= ano.getEstensione()+" MQ";
                    }else {
                    	estensione="N.D";
                    }
                }                        
                Object[] riga = {
                    tipo,                       
                    descrizioneVeloce,                   
                    scadenza,                   
                    gravita,                  
                    estensione,
                    descrizione,
                    n.getCodNotifica()
                };
                notifiche.add(n);
                finestraProprietario.getFinVisualizzaNotifiche().aggiungiRigaTabella(riga);
            }
    		
    	}catch(SQLException e) {
    		e.printStackTrace();
    		
        }
    }
    
    public void caricaDatiReport(int codProgetto) {
    	try {
    		ArrayList<DatiReport> dati= new ArrayList<DatiReport>();
    		dati= reportDao.prelevaDatiReport(codProgetto);
    		ArrayList<Object[]> datiDaPassare= new ArrayList<>();   		
    		for(DatiReport r: dati) {
    			Object[] riga= {
    				r.getNomeColtura(),
    				r.getSemi(),
    				r.getPrevista(),
    				r.getReale()
    			};
    			datiDaPassare.add(riga);
    		}
    		for (Object[] riga : datiDaPassare) {
    		    System.out.println("BARRA: " + riga[0] + " Valori: " + riga[1] + ", " + riga[2] + ", " + riga[3]);
    		}

    		finestraReport.costruisciGrafico(datiDaPassare);
    		mostraPanelInterno("report");
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    public void eliminaLotto() {
    	 int riga = finestraVisualizzaLotti.getTabella().getSelectedRow();
    	    if (riga != -1) {    	        
    	        int codLotto = (int) finestraVisualizzaLotti.getModello().getValueAt(riga, 0);
    	        try {
    	            boolean successo = lottoDao.cancellaLotto(codLotto);
    	            if (successo) {
    	                caricaLotti();    	                
    	            }
    	        } catch (SQLException e) {
    	            e.printStackTrace();
    	        }
    	    }   	
    }
    
    public void eliminaProgetto(int codProgetto) { 	
    	try {      
            boolean cancellato = progettoDao.eliminaProgetto(codProgetto);
            if (cancellato) {
                caricaIMieiProgetti(); 
            } else {
            	System.out.println("errore cancellazione progetto");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            
        	}
    }
    
    public void eliminaNotifica(int codNotifica) {
    		try {
    			boolean successo= notificaDao.eliminaNotifica(codNotifica);
    			if(successo) {
    				caricaNotificheInviate();
    			}
    		}catch(SQLException e) {
    			e.printStackTrace();
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
    		 return;
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
    		return;
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
    
    public String validaCreazioneAttivitaProgetto(String nomeColtura) {
    	
    	String nome= finestraCreaProgetto.getCmpNome();
    	String periodo= finestraCreaProgetto.getStagioneDiRiferimento();;
    	String durata= finestraCreaProgetto.getCmpDurata();
    	String dataInizioP= finestraCreaProgetto.getCmpDataInizio();
    	
    	String periodoComeEnum=periodo.replace("-", "_");
    	Stagione periodoEnum= Stagione.valueOf(periodoComeEnum.toString().toUpperCase());
    	
    	String quantitaS=finestraCreaProgetto.getCmpQuantitaSemi();
    	String quantitaR=finestraCreaProgetto.getCmpQuantitaPrevista();
    	String inizioS=finestraCreaProgetto.getCmpDataInizioSemina();
    	String fineS=finestraCreaProgetto.getCmpDataFineSemina();
    	String inizioR=finestraCreaProgetto.getCmpDataInizioRaccolta();
    	String fineR= finestraCreaProgetto.getCmpDataFineRaccolta();
    	String metodoS=finestraCreaProgetto.getMetodiSemina();
    	String metodoR= finestraCreaProgetto.getMetodiRaccolta();
    	String coltS=finestraCreaProgetto.getListaColtivatoriS();
    	String coltR=finestraCreaProgetto.getListaColtivatoriR();
    	if (nome.isEmpty() || dataInizioP.isEmpty() || durata.isEmpty()) {
           return "errore campi progetto";
        }   	    	
    	if(!ProgettoStagionale.isLunghezzaNomeValida(nome)) {
    		return"errore lunghezza nome";
    	}
    	int durataInt=0;
    	try{
    		durataInt= Integer.parseInt(durata);
    		if(!ProgettoStagionale.isDurataValida(durataInt)) {
    			return"errore durata";
    		}
    	}catch(NumberFormatException e) {
    		return "durata numero intero";
    	}
    	LocalDate dataInizioProgetto=null;
    	try{
    		dataInizioProgetto= LocalDate.parse(dataInizioP);
    		if(!ProgettoStagionale.isDataInizioValida(dataInizioProgetto)) {
    			return"errore data progetto";
    		}
    	}catch(DateTimeParseException e) {
    		return"data progetto formato";
    	}
    	 try{
    		 ArrayList<ProgettoStagionale> listaAggiornata = progettoDao.prelevaProgettiPerLotto(lottoSelezionato.getCodLotto());
    		 ProgettoStagionale nuovoProgetto= new ProgettoStagionale(finestraCreaProgetto.getCmpNome(),periodoEnum,durataInt,dataInizioProgetto,utenteLoggato,lottoSelezionato);
    	    	if(!sovrapposizioneProgetti(nuovoProgetto, listaAggiornata)) {
    				return"errore sovr progetti";
    			}
    	 }catch(SQLException e) {
    		 e.printStackTrace();
    	 }
    	
    	
    	double quantitaSemi=0.0;
    	double quantitaPrevistaRaccolta=0.0;
    	
    	try {
    		quantitaSemi= Double.parseDouble(quantitaS.replace(",", "."));
    		if(!SeminaColtura.isQuantitaSemiValida(quantitaSemi)) {
    			return "errore <1.";
    		}
    		
    	}catch(NumberFormatException e) {
    		return "errore non double quantità semi";
    	}
    	try {
    		quantitaPrevistaRaccolta= Double.parseDouble(quantitaR.replace(",", "."));
    	}catch(NumberFormatException e) {
    		return"errore non double quantita prevista raccolta";
    	}
    	
    	LocalDate dataInizioSemina= null;
    	LocalDate dataFineSemina= null;
    	
    	try{
    		dataInizioSemina=LocalDate.parse(inizioS);
    		dataFineSemina=LocalDate.parse(fineS);
    		
    		if(!Attivita.isDataInizioValida(dataInizioSemina)) {
    			return "errore datainizio semina";
    	}
    	if(!Attivita.isDataFineValida(dataInizioSemina, dataFineSemina)) {
    		return "errore datafine semina";
    		}
    	}catch(DateTimeParseException e) {
    		return "errore formato semina";
    	}
    	
    	LocalDate dataInizioRaccolta=null; 
    	LocalDate dataFineRaccolta=null; 
    	
    	try{
    		dataInizioRaccolta=LocalDate.parse(inizioR);
    		dataFineRaccolta= LocalDate.parse(fineR);
    		if(!Attivita.isDataInizioValida(dataInizioRaccolta)) {
    		return "errore datainizio raccolta";
    	}
    	if(!Attivita.isDataFineValida(dataInizioRaccolta, dataFineRaccolta)) {
    		return "errore datafine raccolta";
    	}
    	}catch(DateTimeParseException e) {
    		return "errore formato raccolta";
    	}
    	
    	TipoSemina seminaEnum= TipoSemina.valueOf(metodoS.toString().toUpperCase());
    	TipoRaccolta raccoltaEnum= TipoRaccolta.valueOf(metodoR.toString().toUpperCase());
    	
    	try{
    		Coltura coltura= colturaDao.prelevaColturaDaNome(nomeColtura);
    		Utente coltivatoreS= utenteDao.prelevaDaUsername(coltS);
    		Utente coltivatoreR= utenteDao.prelevaDaUsername(coltR);
    		
    		if(coltura!=null) {
    			Semina semina= new Semina(dataInizioSemina,dataFineSemina,coltivatoreS,null,seminaEnum);
    			Raccolta raccolta= new Raccolta(dataInizioRaccolta,dataFineRaccolta,coltivatoreR,null,raccoltaEnum,quantitaPrevistaRaccolta,coltura);
    			SeminaColtura seminaColtura= new SeminaColtura(coltura,semina,quantitaSemi);
    			if(!isAttivitaNonSovrapposta(coltivatoreS.getUsername(), semina)) {
    				return"colt semina sovr attivita";
    			}
    			if(!isAttivitaNonSovrapposta(coltivatoreR.getUsername(), raccolta)) {
    				return"colt raccolta sovr attivita";
    			}
    			if(!durataAttivitaProgetto(semina, dataInizioProgetto, durataInt)) {
    				return"errore semina sovr progetto";
    			}
    			if(!durataAttivitaProgetto(raccolta, dataInizioProgetto, durataInt)) {
    				return"errore raccolta sovr progetto";
    			}
    			if(!coerenzaSeminaDurata(dataFineSemina, dataInizioProgetto, coltura.getTempoMaturazione(), durataInt)) {
    				return"errore coerenzaSeminaDurata";
    			}
    			if(!dataInizioRaccoltaValida(semina, raccolta)) {
    				return"dataInizioRaccoltaNonValida";
    			}
    			
    			if(!metodoRaccoltaMontagna(lottoSelezionato, raccolta)) {
    				return "errore meccanica montagna";
    			}
    			attivitaTemporanee.add(semina);
    			attivitaTemporanee.add(raccolta);
    			listaSeminaColtura.add(seminaColtura);
    		}
    	}catch(SQLException e) {
    		return "errore generico db";
    	}
    	return "ok";
    }
    
    public String CreaNotifica() {
    	String descrizioneVeloce= finestraCreaNotifica.getCmpDescrizioneVeloce();
    	String descrizione= finestraCreaNotifica.getCmpDescrizione();
    	String tipoNotifica= finestraCreaNotifica.getSceltaNotifica();
    	String livelloGravita=finestraCreaNotifica.getCmpLivelloGravita();
    	String estensione= finestraCreaNotifica.getCmpEstensione();
    	ArrayList<String> nomi= finestraCreaNotifica.getNomiDestinatariSelezionati();
    	
    	LivelloGravita livelloEnum= LivelloGravita.valueOf(livelloGravita.toString().toUpperCase());
    	
    	
    	
    	 if(!Notifica.isNotificaLunghezzaValida(descrizioneVeloce)) {
    		 return"errore descrizione veloce";
    	 }
    	 if(!Notifica.isDescrizioneLunghezzaValida(descrizione)) {
    		 return"errore descrizione";
    	 }
    	 
    	 if (nomi.isEmpty()) {
    	        return "errore destinatari vuoti";
    	    }
    	 
    	 ArrayList<Utente> destinatari = new ArrayList<>();
    	 try {
    	    for (String username : nomi) {
    	        Utente u = utenteDao.prelevaDaUsername(username);
    	        if (u != null) destinatari.add(u);
    	    }
    	 }catch(SQLException e) {
    		 return"errore dal db";
    	 }
    	 
    	 Notifica n = null;
    	 LocalDate oggi = LocalDate.now();
    	 int estensioneInt;
    	 try {
    	        if (tipoNotifica.equals("Attività Imminente")) {
    	            LocalDate scadenza = LocalDate.parse(finestraCreaNotifica.getCmpDataScadenza());
    	            n = new AttivitaImminente(oggi, utenteLoggato,descrizioneVeloce,descrizione,scadenza, destinatari);
    	        } else {
    	            if(estensione!= null && !estensione.trim().isEmpty()) {
    	            	try{
    	            		estensioneInt = Integer.parseInt(finestraCreaNotifica.getCmpEstensione());
    	            	}catch(NumberFormatException e) {
    	            		return"errore formato estensione";
    	            	}
    	            }else {
    	            	estensioneInt=0;
    	            }
    	            n = new Anomalia(oggi, utenteLoggato, descrizioneVeloce, descrizione, livelloEnum, estensioneInt,destinatari);
    	        }
    	 }catch(DateTimeParseException e) {
    		 return"errore formato data";
    	 }
    	 
    	 try {
    		 
    	 notificaDao.salvaNotifica(n);
    	 finestraCreaNotifica.getModelloScelti().clear();
          
         return "ok";

     } catch (SQLException e) {
    	 e.printStackTrace();
        
     }
    	 return"errore";
		
}
    	 
    
    
    public String salvaProgetto() {
    	 if (listaSeminaColtura == null || listaSeminaColtura.isEmpty()) {
    	        return "errore lista vuota";
    	    }
    	String periodo= finestraCreaProgetto.getStagioneDiRiferimento();;
    	String periodoComeEnum=periodo.replace("-", "_");
    	Stagione periodoEnum= Stagione.valueOf(periodoComeEnum.toString().toUpperCase());
    	String metodoI= finestraCreaProgetto.getMetodiIrrigazione().toString().toUpperCase();
    	TipoIrrigazione irrigazioneEnum= TipoIrrigazione.valueOf(metodoI.toString().toUpperCase());
    	String coltI= finestraCreaProgetto.getListaColtivatoriI();
    	String dataInizioIrrigazione= finestraCreaProgetto.getCmpDataInizioIrrigazione();
    	String dataFineIrrigazione= finestraCreaProgetto.getCmpDataFineIrrigazione();
    	
    	LocalDate dataI= null;
    	LocalDate dataF= null;
    	try {
    		dataI=LocalDate.parse(dataInizioIrrigazione);
    		dataF= LocalDate.parse(dataFineIrrigazione);
    		if(!Attivita.isDataInizioValida(dataI)) {
    			return"errore datainizio irrigazione";
    		}
    		if(!Attivita.isDataFineValida(dataI, dataF)) {
    			return"errore datafine irrigazione";
    		}
    	}catch(DateTimeParseException e) {
    		return"errore formato date irrigazione";
    	}
    	int durata= Integer.parseInt(finestraCreaProgetto.getCmpDurata());
		LocalDate dataP= LocalDate.parse(finestraCreaProgetto.getCmpDataInizio());
    	try {
    		Utente coltivatoreI= utenteDao.prelevaDaUsername(coltI);
    		if(coltivatoreI!= null) {
    			Irrigazione irrigazione= new Irrigazione(dataI,dataF,coltivatoreI,null,irrigazioneEnum);
    			if(!isAttivitaNonSovrapposta(coltivatoreI.getUsername(), irrigazione)) {
    				return"colt irrigazione sovr attivita";
    			}
    			
    			if(!durataAttivitaProgetto(irrigazione, dataP, durata)) {
    				return"errore irrigazione sovr progetto";
    			}
    			if(!metodoIrrigazionePendenza(lottoSelezionato, irrigazione)) {
    				return"errore pendenza sommersione";
    			}
    			attivitaTemporanee.add(irrigazione);
    		}
    	}catch(SQLException e) {
    		e.printStackTrace();
    		return"errore db";
    	}
    	ProgettoStagionale nuovoProgetto= new ProgettoStagionale(finestraCreaProgetto.getCmpNome(),periodoEnum,durata,dataP,utenteLoggato,lottoSelezionato);
    	try{
    		progettiperLotto=progettoDao.prelevaProgettiPerLotto(lottoSelezionato.getCodLotto());
    		
    	}catch(SQLException e) {
    		e.printStackTrace();
    		return"errore da db";
    	}
    	
    	try {
    		boolean salvataggio= progettoDao.salvaProgettoCompleto(nuovoProgetto, attivitaTemporanee, listaSeminaColtura);
    		if(salvataggio) {
    			attivitaTemporanee.clear();
    			listaSeminaColtura.clear();
    			return"ok";
    		}
    	}catch(SQLException e) {
    		e.printStackTrace();
    		return"errore da db";
    	}
    	return "errore sconosciuto";
    }
    
    public void annullaCreazioneProgetto() {
        attivitaTemporanee.clear(); 
        listaSeminaColtura.clear(); 
        finestraProprietario.mostraPanelInterno("visualizza lotti");
    }
    public boolean sovrapposizioneProgetti(ProgettoStagionale progetto, ArrayList<ProgettoStagionale> lista) {
    	LocalDate dataFine=progetto.getDataInizio().plusDays(progetto.getDurata());
    	
    	for(ProgettoStagionale ps : lista ) {
    		 LocalDate inizioEsistente = ps.getDataInizio();
    	        LocalDate fineEsistente = inizioEsistente.plusDays(ps.getDurata());
    	        
    	        if(!progetto.getDataInizio().isAfter(fineEsistente) && !dataFine.isBefore(inizioEsistente)) {
    	        	return false;
    	        }
    	}
    	return true;
    }
    
    public boolean durataAttivitaProgetto(Attivita attivita,LocalDate dataInizioProgetto,int durataProgetto) {
    	LocalDate dataFineProgetto = dataInizioProgetto.plusDays(durataProgetto);     
        if (attivita.getDataFine().isAfter(dataFineProgetto)) {
            return false;
        }
        if(attivita.getDataFine().isBefore(dataInizioProgetto)){
        	return false;
        }
        if(attivita.getDataInizio().isAfter(dataFineProgetto)) {
        	return false;
        }
        if (attivita.getDataInizio().isBefore(dataInizioProgetto)) {
            return false;
        }
        return true;
    }
    
    public boolean isAttivitaNonSovrapposta(String username,Attivita attivita) {
    	try {	
    	ArrayList<Attivita> tutteLeAttivita=new ArrayList<Attivita>();
    	tutteLeAttivita=attivitaDao.prelevaTutteAttivitaPerColtivatore(username);
    	tutteLeAttivita.addAll(attivitaTemporanee);
    	 for (Attivita a : tutteLeAttivita) {  	     
    	        if (a.getColtivatore().getUsername().equals(username)) {  
    	            if (!attivita.getDataInizio().isAfter(a.getDataFine()) && !attivita.getDataFine().isBefore(a.getDataInizio())) {
    	                return false; 
    	            }
    	        }
    	    }
    	    
    }catch(SQLException e) {
    	return false;
    }
    	return true;
    }
    
    public boolean coerenzaSeminaDurata(LocalDate dataFineSemina,LocalDate dataInizioProgetto,int tempoMaturazione, int durataProgetto) {
    	 LocalDate dataScadenzaProgetto = dataInizioProgetto.plusDays(durataProgetto);
    	 LocalDate data = dataFineSemina.plusDays(tempoMaturazione);
    	    if (dataScadenzaProgetto.isBefore(data)) {
    	        return false;
    	    }
    	    
    	    return true;
    }
    
    public boolean dataInizioRaccoltaValida(Semina semina, Raccolta raccolta) {
    	if (raccolta.getDataInizio().isBefore(semina.getDataFine())) {
            return false;
        }
        return true;
    }
    
    public boolean metodoRaccoltaMontagna(LottoColtivabile lc,Raccolta raccolta) {
    	if(raccolta.getMetodoRaccolta()==TipoRaccolta.MECCANICA && lc.getMorfologia()==TipoMorfologia.MONTUOSO) {
    		return false;
    	}
    	return true;
    }
    
    public boolean metodoIrrigazioneCollinaMontagna(LottoColtivabile lc, Irrigazione irrigazione) {
    	if(irrigazione.getMetodoIrrigazione()==TipoIrrigazione.SOMMERSIONE && (lc.getMorfologia()==TipoMorfologia.COLLINARE || lc.getMorfologia()==TipoMorfologia.MONTUOSO)) {
    		return false;
    	}
    	return true;
    }
    
    public boolean metodoIrrigazionePendenza(LottoColtivabile lc, Irrigazione i) {
    	if(i.getMetodoIrrigazione()==TipoIrrigazione.SOMMERSIONE && (lc.getMorfologia()==TipoMorfologia.COLLINARE || lc.getMorfologia()==TipoMorfologia.MONTUOSO)) {
    		return false;
    	}
    	return true;
    }

	public static Utente getUtenteLoggato() {
		return utenteLoggato;
	}

	public static void setUtenteLoggato(Utente utenteLoggato) {
		Controller.utenteLoggato = utenteLoggato;
	}
}
