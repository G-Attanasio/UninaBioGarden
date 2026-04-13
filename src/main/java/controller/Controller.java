package controller;

import model.*;
import service.Service;
import view.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import dao.*;
import dto.AnomaliaDTO;
import dto.AttivitaImminenteDTO;
import dto.IrrigazioneDTO;
import dto.LottoDTO;
import dto.ProgettoDTO;
import dto.RaccoltaDTO;
import dto.SeminaColturaDTO;
import dto.SeminaDTO;
import dto.UtenteDTO;
import exceptions.EmailUsernameGiàEsistentiException;
import exceptions.ErroreDatabaseException;
import exceptions.RisorsaNonTrovataException;
import exceptions.UtenteNonTrovatoException;
import exceptions.ValidazioneException;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.JFreeChart;


public class Controller {

	private static Utente utenteLoggato;
	private Service service;
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
	private UtenteDAO utenteDao;
	private LottoDAO lottoDao;
	private ColturaDAO colturaDao;
	private AttivitaDAO attivitaDao; 
	private ProgettoDAO progettoDao;
	private NotificaDAO notificaDao;
	private ReportDAO reportDao;
	private FinestraIscrizioneColtivatore finestraIscrizioneColtivatore;
	private FinestraIscrizioneProprietario finestraIscrizioneProprietario;
	private FinestraIscriviLotto finestraIscrizioneLotto;
	private FinestraSceltaRuolo finestraSceltaRuolo;
	private FinestraProprietario finestraProprietario;
	private FinestraColtivatore finestraColtivatore;
	private FinestraProprietarioColtivatore finestraProprietarioColtivatore;
	private FinestraVisualizzaLotti finestraVisualizzaLotti;
	private FinestraCreaLotto finestraCreaLotto;
	private FinestraVisualizzaColture finestraVisualizzaColture;
	private FinestraCreaProgetto finestraCreaProgetto;
	private FinestraAttivitaAssegnate finestraAttivitaAssegnate;
	private FinestraVisualizzaProgetti finestraVisualizzaProgetti;
	private FinestraCreaNotifica finestraCreaNotifica;
	private FinestraVisualizzaNotifiche finestraVisualizzaNotifiche;
	private FinestraVisualizzaAttivita finestraVisualizzaAttivita;
	private FinestraReport finestraReport;

    public Controller() {
          	
    	this.service= new Service();
        this.frame= new MainFrame(this);
        this.cardPanel= frame.getCardPanel();
        this.finestraLogin= frame.getCardPanel().getFinestraLogin();
        this.utenteDao= new UtenteDAO();
        this.lottoDao= new LottoDAO();
        this.colturaDao= new ColturaDAO();
        this.attivitaDao= new AttivitaDAO();
        this.progettoDao= new ProgettoDAO();
        this.progettoDao.sincronizzaSistema();
        this.notificaDao= new NotificaDAO();
        this.reportDao= new ReportDAO();
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
        this.finestraColtivatore= frame.getCardPanel().getFinestraColtivatore();
        this.finestraProprietarioColtivatore= frame.getCardPanel().getFinestraProprietarioColtivatore();
        this.finestraVisualizzaLotti= finestraProprietario.getFinVisualizzaLotti();
        this.finestraCreaLotto=finestraProprietario.getFinCreaLotto();
        this.finestraVisualizzaColture=finestraProprietario.getFinVisualizzaColture();
        this.finestraCreaProgetto= finestraProprietario.getFinCreaProgetto();
        this.finestraAttivitaAssegnate=finestraProprietario.getFinAttivitaAssegnate();
        this.finestraVisualizzaProgetti=finestraProprietario.getFinVisualizzaProgetti();
        this.finestraCreaNotifica=finestraProprietario.getFinCreaNotifica();
        this.finestraVisualizzaNotifiche=finestraProprietario.getFinVisualizzaNotifiche();
        this.finestraReport= finestraProprietario.getFinReport();
        this.finestraVisualizzaAttivita= finestraColtivatore.getFinVisualizzaAttivita();
    }
    
    public void validaLogin()  {
    	String username= finestraLogin.getUsername();
    	String password= new String(finestraLogin.getPassword());  
    	
    	if(username.isEmpty() || password.isEmpty()) {
    		finestraLogin.erroreLogin();
    		return;
    	}    	
		try {
			Utente u= service.effettuaLogin(username, password);
			if(u!= null) {
				setUtenteLoggato(u);
	    		if(u.getRuolo()==TipoRuolo.PROPRIETARIO) {
	    			inizializzaFinestraProprietario();
	    		}
	    		if (u.getRuolo()==TipoRuolo.COLTIVATORE) {		
	    	       inizializzaFinestraColtivatore();
	    		}
	    		if (u.getRuolo()==TipoRuolo.PROPRIETARIO_COLTIVATORE) {
	    			inizializzaFinestraProprietarioColtivatore();
	    		}	
			}
			else {
				finestraLogin.erroreLogin();
			}
		}catch(UtenteNonTrovatoException e) {
			finestraLogin.nonTrovato();
    	} 
    }
    
    public void mostraPanelInterno(String testo) {
    	String ruolo = getUtenteLoggato().getRuolo().toString();     
        if (ruolo.equalsIgnoreCase("COLTIVATORE")) {            
            finestraColtivatore.mostraPanelInterno(testo);
        } else if (ruolo.equalsIgnoreCase("PROPRIETARIO")) {           
            finestraProprietario.mostraPanelInterno(testo);
        }else if(ruolo.equalsIgnoreCase("PROPRIETARIO_COLTIVATORE")) {
        	finestraProprietarioColtivatore.mostraPanelInterno(testo);
        }
    }
    
    public void aggiungiLotto()  {
    	String tessitura= finestraCreaLotto.getTipoTessitura().replace(" ", "_");
    	String dimensioni= finestraCreaLotto.getDimensioni();
    	String ph= finestraCreaLotto.getPh().replace(",", ".");
    	String morfologia= finestraCreaLotto.getTipoMorfologia();
    	String altitudine= finestraCreaLotto.getAltitudine();
    	String localita= finestraCreaLotto.getLocalità();
    	String comune= finestraCreaLotto.getComune();
    	String provincia= finestraCreaLotto.getProvincia().toUpperCase();
    	
    	int dimensioniInt = 0;
    	try {
    	   
    	    dimensioniInt = Integer.parseInt(dimensioni);
    	} catch (NumberFormatException e) {
    	    finestraCreaLotto.messaggioErrore(finestraCreaLotto.getCmpDimensioni(), "Inserire un numero intero.");
    	    return;
    	}
    	
    	double phDouble=0.0;
    	try {
    		phDouble= Double.parseDouble(ph);
    	}catch(NumberFormatException e) {
    		finestraCreaLotto.messaggioErrore(finestraCreaLotto.getCmpPh(), "Inserire un numero.");
    		return;
    	}
    	
    	int altitudineInt=0;
    	try {
    		altitudineInt= Integer.parseInt(altitudine);
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
    			
    	LottoDTO lDTO= new LottoDTO(tessituraEnum,dimensioniInt, phDouble, morfologiaEnum, altitudineInt, localita, comune, provincia,getUtenteLoggato().getIdUtente());
    	
    	try{
    		if(service.salvaLotto(lDTO)) { 
    		caricaLotti();
    		finestraCreaLotto.pulisciCampi();
    		mostraPanelInterno("visualizza lotti");
    		}
    	}catch(ValidazioneException v) {
			switch(v.getErrore()) {
			case "dimensioni":
				finestraCreaLotto.messaggioErrore(finestraCreaLotto.getCmpDimensioni(), "Superficie non valida, deve essere compresa tra i 1000 e 1000000 mq.");
				break;
			case "ph":
				finestraCreaLotto.messaggioErrore(finestraCreaLotto.getCmpPh(), "Ph non valido, deve essere compreso tra 4 e 9.");
				break;
			case "altitudine":
				finestraCreaLotto.messaggioErrore(finestraCreaLotto.getCmpAltitudine(), "Altitudine non valida, deve essere compresa tra -20 e 3000.");
				break;
			}
		}
    }
    
    public void caricaLotti() {
    	try{
    		ArrayList<LottoColtivabile> listaLotti;
    	int idUtente= getUtenteLoggato().getIdUtente();
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
    	
    	}catch(RisorsaNonTrovataException e) {
    		
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
    }
    
    public void caricaAttivitaAssegnate() {  	
    	   try { 
    		   progettoDao.sincronizzaSistema();
    	        ArrayList<Attivita> tutte = attivitaDao.prelevaAttivitaAssegnateDaProprietario(getUtenteLoggato().getIdUtente());
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
    	    }catch(RisorsaNonTrovataException e) {
    	    	 
    	    }catch (SQLException e) {
    	        e.printStackTrace();
    	       
    	    }
    }
    
    public void caricaAttivitaColtivatore() {
    	  try { 
   		   progettoDao.sincronizzaSistema();
   	        ArrayList<Attivita> tutte = attivitaDao.prelevaAttivitaColtivatore(getUtenteLoggato().getIdUtente());
   	        ArrayList<SeminaColtura> lista= attivitaDao.prelevaDettagliColturePerColtivatore(getUtenteLoggato().getIdUtente());
   	        finestraVisualizzaAttivita.svuotaTabella(); 	         	       
   	        for (Attivita a : tutte) {   	            
   	        	String tipo = "";
   	        	String metodo="";
   	        	String progetto="";
   	        	String coltura="";
   	        	if (a instanceof Semina) {
   	                tipo = "Semina";
   	                metodo = ((Semina)a).getMetodoSemina().toString(); 
   	                progetto= ((Semina)a).getProgetto().getNomeProgetto();
   	                for(SeminaColtura sc: lista) {
   	                	if(sc.getSemina().getCodAttivita()== ((Semina)a).getCodAttivita()) {
   	                		coltura= sc.getColtura().getNome();
   	                	}
   	                }
   	            } 
   	            else if (a instanceof Raccolta) {
   	                tipo = "Raccolta";
   	                metodo = ((Raccolta)a).getMetodoRaccolta().toString(); 
   	                coltura=((Raccolta)a).getColtura().getNome();
   	            } 
   	            else if (a instanceof Irrigazione) {
   	                tipo = "Irrigazione";
   	                metodo = ((Irrigazione)a).getMetodoIrrigazione().toString();
   	            }
   	            Object[] riga = {
   	            	a.getCodAttivita(),	
   	            	tipo,
   	                coltura,
   	                a.getProgetto().getNomeProgetto(),
   	                metodo,
   	                a.getDataInizio(),
   	                a.getDataFine(),
   	                a.getStatoEsecuzione()
   	            };
   	            finestraVisualizzaAttivita.aggiungiRigaTabella(riga);
   	        }
   	    }catch(RisorsaNonTrovataException e) {
   	    	
   	    }catch (SQLException e) {
   	        e.printStackTrace();
   	       
   	    }
    }
    
    public void registraRaccolta(int codAttivita,double quantita) {
    	try {
            attivitaDao.aggiornaQuantitaReale(codAttivita, quantita);
            caricaAttivitaColtivatore();            
        }catch(RisorsaNonTrovataException e) {
        	
        }catch (SQLException e) {
            e.printStackTrace();          
        }
    }
    
    public void caricaIMieiProgetti() {  	
    	try {
    		progettoDao.sincronizzaSistema();
    		ArrayList<ProgettoStagionale> lista= progettoDao.prelevaProgettiPerProprietario(getUtenteLoggato().getIdUtente());
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
    	    }catch(RisorsaNonTrovataException e) {
    	    	
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
    	        this.lottoSelezionato = service.avviaProgetto(codLotto);   	        
    	            caricaColtureInCreaProgetto();
    	            finestraCreaProgetto.pulisciCampi();	        
    	    } catch(RisorsaNonTrovataException e) {
    	    	 finestraVisualizzaLotti.mostraMessaggio("Lotto non trovato.");  	
    	    }catch(ErroreDatabaseException e) {
    	    	finestraVisualizzaLotti.mostraMessaggio(e.getMessage());
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
    	}catch(UtenteNonTrovatoException e) {
    		
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
    	}catch(UtenteNonTrovatoException e) {
    		
    	}catch(SQLException e) {
    		e.printStackTrace();
    		
        }
    }
    	
    public void caricaColtivatoriComeDestinatari() {
    	try{
    		ArrayList<String> listaColtivatori= new ArrayList<String>();
    		listaColtivatori=utenteDao.prelevaPerProgetto();
    		finestraCreaNotifica.setElencoColtivatori(listaColtivatori);  		
    	}catch(UtenteNonTrovatoException e) {
    		finestraCreaNotifica.gestisciErrori("destinatari non trovati");
    	}catch(SQLException e) {
    		e.printStackTrace();
    		
        }
    }
    
    public void caricaNotificheInviate() {
    	try{
    		this.notifiche.clear();
    		ArrayList<Notifica> listaNotifiche= new ArrayList<Notifica>();
    		listaNotifiche=notificaDao.prelevaNotificheInviate(getUtenteLoggato().getIdUtente());
    		finestraVisualizzaNotifiche.svuotaTabella();	
    		finestraVisualizzaNotifiche.onOffAggiungi(true);
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
                    n.getCodNotifica(),
                    descrizione
                    
                };
                notifiche.add(n);
                finestraVisualizzaNotifiche.aggiungiRigaTabella(riga);
            }
    		
    	}catch(RisorsaNonTrovataException e) {
    		
        }catch(SQLException e) {
    		e.printStackTrace();
        }
    }
    
    public void caricaNotificheRicevute() {  	
    	try{
    		this.notifiche.clear();
    		ArrayList<Notifica> listaNotifiche= new ArrayList<Notifica>();
    		listaNotifiche=notificaDao.prelevaNotificheRicevute(getUtenteLoggato().getIdUtente());
    		finestraVisualizzaNotifiche.svuotaTabella();	
    		finestraVisualizzaNotifiche.onOffAggiungi(false);
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
                    n.getCodNotifica(),
                    descrizione
                    
                };
                notifiche.add(n);
                finestraVisualizzaNotifiche.aggiungiRigaTabella(riga);
            }
    		
    	}catch(RisorsaNonTrovataException e) {
    		
    	
    	}catch(SQLException e) {
    		e.printStackTrace();   		
    	}
    }
    
    public void caricaDatiReport(int codProgetto) {
    	try {
    		ArrayList<DatiReport> dati= new ArrayList<DatiReport>();
    		dati= reportDao.prelevaDatiReport(codProgetto);
    		ArrayList<Object[]> datiDaPassare= new ArrayList<>();   
    		FinestraReport vistaReale = null;
            if (getUtenteLoggato().getRuolo() == TipoRuolo.PROPRIETARIO) {
                vistaReale = finestraProprietario.getFinReport();
            } else {
                vistaReale = finestraProprietarioColtivatore.getFinReport();
            }
    		for(DatiReport r: dati) {
    			Object[] riga= {
    				r.getNomeColtura(),
    				r.getSemi(),
    				r.getPrevista(),
    				r.getReale()
    			};
    			datiDaPassare.add(riga);
    		}
    		if (vistaReale != null) {
                vistaReale.costruisciGrafico(datiDaPassare);               
            }
    		mostraPanelInterno("report");
    	}catch(RisorsaNonTrovataException e) {   		
    	
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    public void eliminaLotto(int codLotto) {
        try {
            service.eliminaLotto(codLotto);  	            
                caricaLotti();    	                
        } catch (RisorsaNonTrovataException e) {
            finestraVisualizzaLotti.mostraMessaggio("Lotto non trovato nel database.");
        } catch(ErroreDatabaseException e) {
        	finestraVisualizzaLotti.mostraMessaggio(e.getMessage());
        }
    }
    
    public void eliminaProgetto(int codProgetto) { 	   	
	    try {
	        service.eliminaProgetto(codProgetto);
	        caricaIMieiProgetti();
	    } catch (RisorsaNonTrovataException e) {
	        finestraVisualizzaProgetti.mostraMessaggio("Progetto non trovato.");
	    } catch(ErroreDatabaseException e) {
	    	finestraVisualizzaProgetti.mostraMessaggio(e.getMessage());
	    }
    }
    
    public void eliminaNotifica(int codNotifica, boolean inviata) {
        try {
            if (inviata) {
                service.eliminaNotificaInviata(codNotifica);
                caricaNotificheInviate();
            } else {
                service.eliminaNotificaRicevuta(codNotifica, getUtenteLoggato().getIdUtente());
                caricaNotificheRicevute();
            }

        } catch (RisorsaNonTrovataException e) {
            finestraVisualizzaNotifiche.mostraMessaggio("Notifica non trovata.");
        } catch(ErroreDatabaseException e) {
        	finestraVisualizzaNotifiche.mostraMessaggio(e.getMessage());
        }
    }
    
    public void mostraPanel(String testo) {
    	cardPanel.mostraPanel(testo);
    }
    
    public void gestisciSceltaRuolo(String ruolo) {
    	if(ruolo.equals("PROPRIETARIO")) {
    		cardPanel.mostraPanel("iscrizione proprietario");
    	}
    	else if(ruolo.equals("COLTIVATORE")) {
    		cardPanel.mostraPanel("iscrizione coltivatore");
    	}
    	else {
    		cardPanel.mostraPanel("iscrizione proprietario");
    	}
    }
    
    public void validaIscrizioneUtenteColtivatore()  {
    	 String nome = finestraIscrizioneColtivatore.getNome();
    	 String cognome = finestraIscrizioneColtivatore.getCognome();
    	 String username= finestraIscrizioneColtivatore.getUsername();
    	 String email = finestraIscrizioneColtivatore.getEmail();
    	 String dataNascita= finestraIscrizioneColtivatore.getDataNascita();
    	 String password = finestraIscrizioneColtivatore.getPassword();
    	 String confermaPassword = finestraIscrizioneColtivatore.getConfermaPassword();
    	 
    	 finestraIscrizioneColtivatore.resetBordi();
    	 
    	 
    	 if(!Utente.isSoloLettere(nome)) {
    		 finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpNome(),"Il nome deve essere di sole lettere.");
    		 return;
    	 }
    	 if(!Utente.isLunghezzaValida(nome)) {
    		 finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpNome(),"La lunghezza del nome deve essere inferiore alle 30 lettere e superiore ad 1.");
    		 return;
    	 }
    	 if(!Utente.isSoloLettere(cognome)) {
    		 finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpCognome(), "Il cognome deve essere di sole lettere.");
    		 return;
    	 }
    	 if (!Utente.isLunghezzaValida(cognome)) {
    		 finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpCognome(),"La lunghezza del cognome deve essere inferiore alle 30 lettere e superiore ad 1.");
    		 return;
    	 }
    	 if(!Utente.isLunghezzaValida(username)) {
    		 finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpUsername(),"La lunghezza dell'username deve essere inferiore alle 30 lettere e superiore a 1.");
    		 return;
    	 }
    	 if(!Utente.isEmailValida(email)) {
    		 finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpEmail(), "L'email deve contenere una @ e un punto al suo interno nell'ordine giusto.");
    		 return;
    	 }
    	 if(!Utente.isLunghezzaValida(email)) {
    		 finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpEmail(),"La lunghezza dell'email deve essere inferiore di 30 caratteri.");
    		 return;
    	 }
 
    	 LocalDate dataNascitaParse = null;

    	 try {
    	     dataNascitaParse = LocalDate.parse(dataNascita); 
    	 } catch (DateTimeParseException e) {
    	     finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpDataNascita(), "Usa il formato YYYY-MM-DD (es. 1995-05-20)");
    	     return; 
    	 }
    	 
    	 if (password.isEmpty() || password.length() < 4) {
    		    finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpPassword(), "Minimo 4 caratteri");
    		    return;
    		}

    	if (!password.equals(confermaPassword)) {
    		    finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpConfermaPassword(), "Le password non coincidono.");
    		    return;
    		}
    	
    	UtenteDTO uDTO = new UtenteDTO(nome, cognome, username, password, email, dataNascitaParse, TipoRuolo.COLTIVATORE);
    	try{   		
    		Utente u=service.registraColtivatore(uDTO);
    		if(u!=null) {
    			setUtenteLoggato(u);
    			finestraIscrizioneColtivatore.pulisciCampi();
    			cardPanel.mostraPanel("coltivatore");  			
    		}
    		else {
    			finestraIscrizioneColtivatore.messaggioErroreBottone(finestraIscrizioneLotto.getSalva(), "Errore salvataggio database.");
    		}
    	}
    	catch(EmailUsernameGiàEsistentiException e) {
    		finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpEmail(), "Email o Username già esistenti.");
    		finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpUsername(), "Email o Username già esistenti.");
    	}catch(ValidazioneException v) {
    		 finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpDataNascita(), "Devi avere tra 18 e 120 anni!");
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
    	   	 
    	 if(!Utente.isSoloLettere(nome)) {
    		 finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneProprietario.getCmpNome(),"Il nome deve essere di sole lettere.");
    		 return;
    	 }
    	 if(!Utente.isLunghezzaValida(nome)) {
    		 finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneProprietario.getCmpNome(),"La lunghezza del nome deve essere inferiore alle 30 lettere e superiore ad 1.");
    		 return;
    	 }
    	 if(!Utente.isLunghezzaValida(cognome)) {
    		 finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneProprietario.getCmpCognome(), "Il cognome deve essere di sole lettere.");
    		 return;
    	 }
    	 if (!Utente.isSoloLettere(cognome)) {
    		 finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneProprietario.getCmpCognome(),"La lunghezza del cognome deve essere inferiore alle 30 lettere e superiore ad 1.");
    		 return;
    	 }
    	 if(!Utente.isLunghezzaValida(username)) {
    		 finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneProprietario.getCmpUsername(),"La lunghezza dell'username deve essere inferiore alle 30 lettere e superiore a 1.");
    		 return;
    	 }
    	 if(!Utente.isEmailValida(email)) {
    		 finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneProprietario.getCmpEmail(), "L'email deve contenere una @ e un punto al suo interno nell'ordine giusto.");
    		 return;
    	 }
    	 if(!Utente.isLunghezzaValida(email)) {
    		 finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneProprietario.getCmpEmail(),"La lunghezza dell'email deve essere inferiore di 30 caratteri.");
    		 return;
    	 }
 
    	 LocalDate dataNascitaParse = null;

    	 try {
    	     dataNascitaParse = LocalDate.parse(dataNascita); 
    	 } catch (DateTimeParseException e) {
    	     finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneProprietario.getCmpDataNascita(), "Usa il formato YYYY-MM-DD (es. 1995-05-20)");
    	     return; 
    	 }
    	 
    	 if (password.isEmpty() || password.length() < 4) {
    		    finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneProprietario.getCmpPassword(), "Minimo 4 caratteri");
    		    return;
    		}

    	if (!password.equals(confermaPassword)) {
    		    finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneProprietario.getCmpConfermaPassword(), "Le password non coincidono.");
    		    return;
    		}
    	TipoRuolo tipoRuoloEnum= TipoRuolo.valueOf(ruoloComeEnum.toUpperCase());
    	
    	UtenteDTO uDTO = new UtenteDTO(nome, cognome, username, password, email, dataNascitaParse, tipoRuoloEnum);
    	
    	String tessitura= finestraIscrizioneLotto.getTipoTessitura().replace(" ","_" );
    	String dimensioni= finestraIscrizioneLotto.getDimensioni();
    	String ph= finestraIscrizioneLotto.getPh().replace(",", ".");
    	String morfologia= finestraIscrizioneLotto.getTipoMorfologia();
    	String altitudine= finestraIscrizioneLotto.getAltitudine();
    	String localita= finestraIscrizioneLotto.getLocalità();
    	String comune= finestraIscrizioneLotto.getComune();
    	String provincia= finestraIscrizioneLotto.getProvincia().toUpperCase();
    	
    	
    	int dimensioniInt = 0;
    	try {   
    	    dimensioniInt = Integer.parseInt(dimensioni);
    	} catch (NumberFormatException e) {
    	    finestraIscrizioneLotto.messaggioErrore(finestraIscrizioneLotto.getCmpDimensioni(), "Inserire un numero intero.");
    	    return;
    	}
    	
    	double phDouble=0.0;
    	try {
    		phDouble= Double.parseDouble(ph);
    	}catch(NumberFormatException e) {
    		finestraIscrizioneLotto.messaggioErrore(finestraIscrizioneLotto.getCmpPh(), "Inserire un numero.");
    		return;
    	}
    	
    	int altitudineInt=0;
    	try {
    		altitudineInt= Integer.parseInt(altitudine);
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
    			
    	LottoDTO lc= new LottoDTO(tessituraEnum,dimensioniInt, phDouble, morfologiaEnum, altitudineInt, localita, comune, provincia,uDTO.getIdUtente());
    	
    		try{
    			Utente u = service.registraProprietario(uDTO, lc);
    			if(u!=null) {
    			setUtenteLoggato(u);
    			finestraIscrizioneProprietario.pulisciCampi();
    			finestraIscrizioneLotto.pulisciCampi();
    			
    			if(u.getRuolo()==TipoRuolo.PROPRIETARIO) {
    				cardPanel.mostraPanel("proprietario");
    			}
    			else if (u.getRuolo()== TipoRuolo.PROPRIETARIO_COLTIVATORE) {
    				cardPanel.mostraPanel("proprietario-coltivatore");	
    			}
    		}else {
    			finestraIscrizioneLotto.messaggioErroreBottone(finestraIscrizioneLotto.getSalva(), "Errore salvataggio database.");
    		}
    		}catch(EmailUsernameGiàEsistentiException e) {
    			finestraIscrizioneLotto.messaggioErrore(finestraIscrizioneProprietario.getCmpEmail(),"Email o Username già esistenti.");
    			finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneProprietario.getCmpUsername(), "Email o Username già esistenti.");
    		}catch(ValidazioneException v) {
    			switch(v.getErrore()) {
    			case "data nascita":
    				 finestraIscrizioneProprietario.messaggioErrore(finestraIscrizioneProprietario.getCmpDataNascita(), "Devi avere tra 18 e 120 anni!");
    				 break;
    			case "dimensioni":
    				finestraIscrizioneLotto.messaggioErrore(finestraIscrizioneLotto.getCmpDimensioni(), "Superficie non valida, deve essere compresa tra i 1000 e 1000000 mq.");
    				break;
    			case "ph":
    				finestraIscrizioneLotto.messaggioErrore(finestraIscrizioneLotto.getCmpPh(), "Ph non valido, deve essere compreso tra 4 e 9.");
    				break;
    			case "altitudine":
    				finestraIscrizioneLotto.messaggioErrore(finestraIscrizioneLotto.getCmpAltitudine(), "Altitudine non valida, deve essere compresa tra -20 e 3000.");
    				break;
    			}
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
    	
	     if(!service.validaSovrapposizioneProgetti(dataInizioProgetto, durataInt,lottoSelezionato.getCodLotto())) {   		 
				return"errore sovr progetti";
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
    			SeminaDTO sDTO= new SeminaDTO(dataInizioSemina,dataFineSemina,0,0,seminaEnum);
    			RaccoltaDTO rDTO= new RaccoltaDTO(dataInizioRaccolta,dataFineRaccolta,0,0,raccoltaEnum,quantitaPrevistaRaccolta,0);
    			service.validaAttivitaSeminaRaccolta(nomeColtura, coltS, coltR, sDTO, rDTO, quantitaSemi, attivitaTemporanee, listaSeminaColtura, durataInt, dataInizioProgetto, lottoSelezionato);
    			
    	}catch(UtenteNonTrovatoException e) {
    		return"coltivatore non trovato";
    	}catch(RisorsaNonTrovataException r) {
    		return "coltura non trovata";
    	}catch(ValidazioneException v) {
    		return v.getErrore();
    	}
    	return "ok";
    }
    
    public String creaNotifica() {
    	String descrizioneVeloce= finestraCreaNotifica.getCmpDescrizioneVeloce();
    	String descrizione= finestraCreaNotifica.getCmpDescrizione();
    	String tipoNotifica= finestraCreaNotifica.getSceltaNotifica();
    	String livelloGravita=finestraCreaNotifica.getCmpLivelloGravita();
    	String estensione= finestraCreaNotifica.getCmpEstensione();
    	ArrayList<String> nomi= finestraCreaNotifica.getNomiDestinatariSelezionati();
    	
    	LivelloGravita livelloEnum= LivelloGravita.valueOf(livelloGravita.toString().toUpperCase());   	 
    	 if (nomi.isEmpty()) {
    	      return "errore destinatari vuoti";
    	 } 
    	 LocalDate oggi = LocalDate.now();
    	 int estensioneInt;
    	 try {
    	        if (tipoNotifica.equals("Attività Imminente")) {
    	            LocalDate scadenza = LocalDate.parse(finestraCreaNotifica.getCmpDataScadenza());
    	            AttivitaImminenteDTO attDTO = new AttivitaImminenteDTO(oggi,getUtenteLoggato().getIdUtente(),descrizioneVeloce,descrizione,scadenza, nomi);
    	            service.salvaAttivitaImminente(attDTO);
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
    	            AnomaliaDTO anomDTO = new AnomaliaDTO(oggi, getUtenteLoggato().getIdUtente(), descrizioneVeloce, descrizione, livelloEnum, estensioneInt,nomi);
    	            service.salvaAnomalia(anomDTO);
    	        }
    	 }catch(DateTimeParseException e) {
    		 return"errore formato data";
    	 }catch(UtenteNonTrovatoException u) {
    		 return "utenti non trovati";
    	 }catch(ValidazioneException v) {
    		 return v.getErrore();
    	 }
    	  finestraCreaNotifica.getModelloScelti().clear();
         return "ok";
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
    			IrrigazioneDTO iDTO= new IrrigazioneDTO(dataI,dataF,0,0,irrigazioneEnum);
    		
    			service.validaAttivitaIrrigazione(coltI, iDTO, dataI, durata, lottoSelezionato, attivitaTemporanee);
    	}catch(UtenteNonTrovatoException e) {
    		return"coltivatore non trovato";
    	}catch(ValidazioneException v) {
    		return v.getErrore();
    	}
    	ProgettoDTO pDTO= new ProgettoDTO(finestraCreaProgetto.getCmpNome(),periodoEnum,durata,dataP,0,0);
    		service.salvaProgetto(pDTO, utenteLoggato, lottoSelezionato, attivitaTemporanee, listaSeminaColtura);
    		return "ok";
    }
    
    public void annullaCreazioneProgetto() {
        attivitaTemporanee.clear(); 
        listaSeminaColtura.clear(); 
        finestraProprietario.mostraPanelInterno("visualizza lotti");
        finestraProprietarioColtivatore.mostraPanelInterno("visualizza lotti");
    }
    
	public void inizializzaFinestraProprietario() {
		this.finestraVisualizzaLotti= finestraProprietario.getFinVisualizzaLotti();
        this.finestraCreaLotto=finestraProprietario.getFinCreaLotto();
        this.finestraVisualizzaColture=finestraProprietario.getFinVisualizzaColture();
        this.finestraCreaProgetto= finestraProprietario.getFinCreaProgetto();
        this.finestraAttivitaAssegnate=finestraProprietario.getFinAttivitaAssegnate();
        this.finestraVisualizzaProgetti=finestraProprietario.getFinVisualizzaProgetti();
        this.finestraCreaNotifica=finestraProprietario.getFinCreaNotifica();
        this.finestraVisualizzaNotifiche=finestraProprietario.getFinVisualizzaNotifiche();
        this.finestraReport= finestraProprietario.getFinReport();
		finestraLogin.pulisciCampi();
		cardPanel.mostraPanel("proprietario");
	}
	
	public void inizializzaFinestraColtivatore() {
		 this.finestraVisualizzaNotifiche=finestraColtivatore.getFinVisualizzaNotifiche();	    	       
	        this.finestraVisualizzaAttivita= finestraColtivatore.getFinVisualizzaAttivita();	    	    
			finestraLogin.pulisciCampi();
			cardPanel.mostraPanel("coltivatore");
	}
	
	public void inizializzaFinestraProprietarioColtivatore() {
		this.finestraVisualizzaLotti= finestraProprietarioColtivatore.getFinVisualizzaLotti();
        this.finestraCreaLotto=finestraProprietarioColtivatore.getFinCreaLotto();
        this.finestraVisualizzaColture=finestraProprietarioColtivatore.getFinVisualizzaColture();
        this.finestraCreaProgetto= finestraProprietarioColtivatore.getFinCreaProgetto();
        this.finestraAttivitaAssegnate=finestraProprietarioColtivatore.getFinAttivitaAssegnate();
        this.finestraVisualizzaProgetti=finestraProprietarioColtivatore.getFinVisualizzaProgetti();
        this.finestraCreaNotifica=finestraProprietarioColtivatore.getFinCreaNotifica();
        this.finestraVisualizzaNotifiche=finestraProprietarioColtivatore.getFinVisualizzaNotifiche();
        this.finestraReport= finestraProprietarioColtivatore.getFinReport();
        this.finestraVisualizzaAttivita= finestraProprietarioColtivatore.getFinVisualizzaAttivita();    	    
		finestraLogin.pulisciCampi();
		cardPanel.mostraPanel("proprietario-coltivatore");
	}

	public static Utente getUtenteLoggato() {
		return utenteLoggato;
	}
	
	public static void setUtenteLoggato(Utente utenteLoggato) {
		Controller.utenteLoggato = utenteLoggato;
	}
	
}
