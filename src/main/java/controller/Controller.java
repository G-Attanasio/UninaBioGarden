package controller;

import model.*;
import service.Service;
import view.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import dto.*;
import exceptions.*;



public class Controller {

	private static Utente utenteLoggato;
	private Service service;
	private InputUtenteDTO proprietarioInIscrizione;
	private LottoColtivabile lottoSelezionato;
	private ArrayList<Attivita> attivitaTemporanee;
	private ArrayList<SeminaColtura> listaSeminaColtura;
	private MainFrame frame;
	private CardPanel cardPanel;
	private FinestraLogin finestraLogin;
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
        this.attivitaTemporanee= new ArrayList<Attivita>();
        new ArrayList<Attivita>();
        this.listaSeminaColtura= new ArrayList<SeminaColtura>();
        new ArrayList<ProgettoStagionale>();
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
		}catch(UtenteNonTrovatoException e) {
			finestraLogin.nonTrovato();
    	}catch(ErroreDatabaseException e) {
    		finestraLogin.mostraMessaggio(e.getMessage());
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
    
    public void aggiungiLotto(InputLottoDTO dto)  {
    	ArrayList<String> errori= new ArrayList<String>();
    	
    	Integer dimensioniInt = parseInt(dto.getDimensioni(), "dimensioni int", errori);   	
    	Double phDouble=parseDouble(dto.getPh(), "ph numero", errori);    	
    	Integer altitudineInt=parseInt(dto.getAltitudine(), "altitudine int", errori);
    	
    	if(!errori.isEmpty()) {
    		finestraCreaLotto.gestisciErrori(errori);
    		return;
    	}	
    	TipoTessitura tessituraEnum = TipoTessitura.valueOf(dto.getTessitura().toUpperCase());
    	TipoMorfologia morfologiaEnum = TipoMorfologia.valueOf(dto.getMorfologia().toUpperCase());
    			
    	LottoDTO lDTO= new LottoDTO(tessituraEnum,dimensioniInt, phDouble, morfologiaEnum, altitudineInt, dto.getLocalita(), dto.getComune(), dto.getProvincia(),getUtenteLoggato().getIdUtente()); 	
    	try{
    		service.registraLotto(lDTO);
    		caricaLotti();
    		finestraCreaLotto.pulisciCampi();
    		mostraPanelInterno("visualizza lotti");		
    	}catch(ValidazioneException v) {
			finestraCreaLotto.gestisciErrori(v.getErrori());
		}catch(ErroreDatabaseException e) {
			finestraCreaLotto.mostraMessaggio(e.getMessage());
		}
    }
    
    public void caricaLotti() {
        try {
            int idUtente = getUtenteLoggato().getIdUtente();
            ArrayList<LottoDTO> listaLotti = service.caricaLottiUtente(idUtente);   
            finestraVisualizzaLotti.mostraLotti(listaLotti);
        } catch (RisorsaNonTrovataException e) {
            finestraVisualizzaLotti.mostraMessaggio("Nessun lotto trovato.");
        } catch (ErroreDatabaseException e) {
            finestraVisualizzaLotti.mostraMessaggio(e.getMessage());
        }
    }
    
    public void caricaAttivitaAssegnate() {
        try {
            ArrayList<AttivitaDTO> lista = service.caricaAttivitaAssegnate(utenteLoggato.getIdUtente());
            finestraAttivitaAssegnate.mostraAttivita(lista);
        } catch (RisorsaNonTrovataException e) {
            finestraAttivitaAssegnate.mostraMessaggio("Nessuna attività trovata");
        } catch (ErroreDatabaseException e) {
            finestraAttivitaAssegnate.mostraMessaggio(e.getMessage());
        }
    }
    
    public void caricaAttivitaColtivatore() {
        try {
            ArrayList<AttivitaDTO> lista = service.caricaAttivitaColtivatore(getUtenteLoggato().getIdUtente());          
            finestraVisualizzaAttivita.mostraAttivita(lista);
        }catch (ErroreDatabaseException e) {
            finestraVisualizzaAttivita.mostraMessaggio(e.getMessage());
        }catch(RisorsaNonTrovataException r) {
        	finestraVisualizzaAttivita.mostraMessaggio("Alcune o tutte Attività non trovate.");
        }
    }
    
    public void registraRaccolta(int codAttivita, double quantita) {
        try {
            service.registraRaccolta(codAttivita, quantita);
            caricaAttivitaColtivatore();
        } catch (ErroreDatabaseException e) {
            finestraVisualizzaAttivita.mostraMessaggio(e.getMessage());
        }
    }
    
    public void caricaProgetti() {
        try {
            ArrayList<ProgettoDTO> lista = service.caricaProgettiProprietario(getUtenteLoggato().getIdUtente());
            finestraVisualizzaProgetti.mostraProgetti(lista);
        } catch (ErroreDatabaseException e) {
            finestraVisualizzaProgetti.mostraMessaggio(e.getMessage());
        }catch(RisorsaNonTrovataException r) {
        	finestraVisualizzaProgetti.mostraMessaggio("Alcuni o tutti progetti non trovati.");
        }
    }
    
    public void caricaColture() {
        try {
            ArrayList<ColturaDTO> lista = service.caricaColture();
           finestraVisualizzaColture.mostraColture(lista);
        }catch(RisorsaNonTrovataException e) {
        	finestraVisualizzaColture.mostraMessaggio("Nessuna coltura trovata.");
        }catch (ErroreDatabaseException e) {
            finestraVisualizzaColture.mostraMessaggio(e.getMessage());
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
            ArrayList<String> lista = service.caricaNomiColture();
            finestraCreaProgetto.popolaColture(lista);
        }catch(RisorsaNonTrovataException e) {
        	finestraCreaProgetto.mostraMessaggio("Colture non trovate.");
        }catch (ErroreDatabaseException e) {
            finestraCreaProgetto.mostraMessaggio(e.getMessage());
        } 
    }
    
    public void caricaColtivatoriInProgetto() {
        try {
            ArrayList<String> listaColtivatori =
                service.caricaUsernameColtivatori();
            finestraCreaProgetto.setElencoColtivatori(listaColtivatori);
        } catch (ErroreDatabaseException e) {
            finestraCreaProgetto.mostraMessaggio(e.getMessage());
        }catch(UtenteNonTrovatoException r) {
        	finestraCreaProgetto.mostraMessaggio("Alcuni o tutti coltivatori non trovati.");
        }
    }
    
    public void caricaColtivatoriInAttivitaProgetto(String coltura) {
        try {
            ArrayList<String> lista =
                service.caricaUsernameColtivatori();
            finestraCreaProgetto.setElencoColtivatori(lista);
            finestraCreaProgetto.pianificaAttivita(lista, coltura);
        } catch (ErroreDatabaseException e) {
            finestraCreaProgetto.mostraMessaggio(e.getMessage());
        } catch(UtenteNonTrovatoException u) {
        	finestraCreaProgetto.mostraMessaggio("Alcuni o tutti coltivatori non trovati");
        }
    }
    	
    public void caricaColtivatoriComeDestinatari() {
        try {
            ArrayList<String> lista =
                service.caricaUsernameColtivatori();
            finestraCreaNotifica.setElencoColtivatori(lista);
        } catch (ErroreDatabaseException e) {
            finestraCreaNotifica.mostraMessaggio(e.getMessage());
        } catch(UtenteNonTrovatoException u) {
        	finestraCreaNotifica.mostraMessaggio("Alcuni o tutti coltivatori non trovati.");
        }
    }
    
    public void caricaNotificheInviate() {
        try {
            ArrayList<NotificaDTO> lista = service.caricaNotificheInviate(getUtenteLoggato().getIdUtente());        
            finestraVisualizzaNotifiche.mostraNotificheInviate(lista, true);
        } catch (ErroreDatabaseException e) {
            finestraVisualizzaNotifiche.mostraMessaggio(e.getMessage());
        } catch(RisorsaNonTrovataException r) {
        	finestraVisualizzaNotifiche.mostraMessaggio("Alcune o tutte notifiche non trovate.");
        }
    }
    
    public void caricaNotificheRicevute() {
        try {
            ArrayList<NotificaDTO> lista = service.caricaNotificheRicevute(getUtenteLoggato().getIdUtente());
            finestraVisualizzaNotifiche.mostraNotificheRicevute(lista, false);
        } catch (ErroreDatabaseException e) {
            finestraVisualizzaNotifiche.mostraMessaggio(e.getMessage());
        } catch(RisorsaNonTrovataException r) {
        	finestraVisualizzaNotifiche.mostraMessaggio("Alcune o tutte notifiche non trovate");
        }
    }
    
    public void caricaDatiReport(int codProgetto) {
        try {
            ArrayList<DatiReportDTO> dati =service.caricaDatiReport(codProgetto);
            if (getUtenteLoggato().getRuolo() == TipoRuolo.PROPRIETARIO) {
                finestraReport = finestraProprietario.getFinReport();
            } else {
                finestraReport = finestraProprietarioColtivatore.getFinReport();
            }
            finestraReport.costruisciGrafico(dati);
            mostraPanelInterno("report");
        } catch (ErroreDatabaseException e) {
            finestraReport.mostraMessaggio(e.getMessage());
        }catch(RisorsaNonTrovataException r) {
        	finestraReport.mostraMessaggio("Alcuni o tutti dati report non trovati.");
        }
    }
    
    public void eliminaLotto(int codLotto) {
        try {
            service.eliminaLotto(codLotto);  	            
                caricaLotti();    	                
        } catch(ErroreDatabaseException e) {
        	finestraVisualizzaLotti.mostraMessaggio(e.getMessage());
        }
    }
    
    public void eliminaProgetto(int codProgetto) { 	   	
	    try {
	        service.eliminaProgetto(codProgetto);
	        caricaProgetti();
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
    
    public void validaIscrizioneUtenteColtivatore(InputUtenteDTO dto)  {
    	 ArrayList<String> errori= new ArrayList<String>(); 	   	 
    	 finestraIscrizioneColtivatore.resetBordi();   	   	 
    	 LocalDate dataNascitaParse = parseDate(dto.getDataNascita(), "formato data", errori);	   	 
    	 if(!errori.isEmpty()) {
    		 finestraIscrizioneColtivatore.gestisciErrori(errori);
    		 return;
    	 }
    	UtenteDTO uDTO = new UtenteDTO(dto.getNome(), dto.getCognome(), dto.getUsername(), dto.getPassword(), dto.getEmail(), dataNascitaParse, TipoRuolo.COLTIVATORE);
    	uDTO.setConfermaPassword(dto.getConfermaPassword());
    	try{   		
    		Utente u=service.registraColtivatore(uDTO);
    		if(u!=null) {
    			setUtenteLoggato(u);
    			finestraIscrizioneColtivatore.pulisciCampi();
    			cardPanel.mostraPanel("coltivatore");  			
    		}    		
    	}
    	catch(EmailUsernameGiàEsistentiException e) {
    		finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpEmail(), "Email o Username già esistenti.");
    		finestraIscrizioneColtivatore.messaggioErrore(finestraIscrizioneColtivatore.getCmpUsername(), "Email o Username già esistenti.");
    	}catch(ValidazioneException v) {
    		finestraIscrizioneColtivatore.gestisciErrori(v.getErrori());
    	}catch(ErroreDatabaseException e) {
    		finestraIscrizioneColtivatore.mostraMessaggio(e.getMessage());
    	}
    }
    
    public void validaIscrizioneUtenteProprietario(InputLottoDTO lDTO) {
    	 ArrayList<String> errori= new ArrayList<String>();
    	 String ruolo= finestraSceltaRuolo.getSceltaRuolo();  	 
    	 String ruoloComeEnum= ruolo.replace("/", "_").trim().toString();    	 
    	 finestraIscrizioneProprietario.resetBordi();  	 
    	 LocalDate dataNascitaParse = parseDate(proprietarioInIscrizione.getDataNascita(),"formato data",errori);  	 
    	 TipoRuolo tipoRuoloEnum= TipoRuolo.valueOf(ruoloComeEnum.toUpperCase());   	
    	 UtenteDTO uDTO = new UtenteDTO(proprietarioInIscrizione.getNome(), proprietarioInIscrizione.getCognome(), proprietarioInIscrizione.getUsername(), proprietarioInIscrizione.getPassword(), proprietarioInIscrizione.getEmail(), dataNascitaParse, tipoRuoloEnum);
    	 uDTO.setConfermaPassword(proprietarioInIscrizione.getConfermaPassword());   	
    	 TipoTessitura tessituraEnum = TipoTessitura.valueOf(lDTO.getTessitura().toUpperCase());
    	 TipoMorfologia morfologiaEnum = TipoMorfologia.valueOf(lDTO.getMorfologia().toUpperCase());
      	
    	 Integer dimensioniInt = parseInt(lDTO.getDimensioni(), "dimensioni int",errori);   	
    	 Double phDouble= parseDouble(lDTO.getPh(), "ph numero",errori);    	
    	 Integer altitudineInt= parseInt(lDTO.getAltitudine(), "altitudine int",errori);
    	if(!errori.isEmpty()) {
    		finestraIscrizioneProprietario.gestisciErrori(errori);
    		finestraIscrizioneLotto.gestisciErrori(errori);
    		return;
    	}   		
    	LottoDTO lc= new LottoDTO(tessituraEnum,dimensioniInt, phDouble, morfologiaEnum, altitudineInt, lDTO.getLocalita(), lDTO.getComune(), lDTO.getProvincia(),uDTO.getIdUtente()); 	
    		try {
    			Utente u = service.registraProprietario(uDTO, lc,errori);
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
    			finestraIscrizioneProprietario.gestisciErrori(v.getErrori());
    			finestraIscrizioneLotto.gestisciErrori(v.getErrori());
    		}catch(ErroreDatabaseException ed) {
    			finestraIscrizioneProprietario.mostraMessaggio(ed.getMessage());
    		}
    }
    
    public ArrayList<String> validaPrimaParteProgetto() {
    	ArrayList<String> errori= new ArrayList<String>();
    	String nome= finestraCreaProgetto.getCmpNome();
    	String durata= finestraCreaProgetto.getCmpDurata();
    	String dataInizioP= finestraCreaProgetto.getCmpDataInizio();  	
    	
    	if (nome.isEmpty() || dataInizioP.isEmpty() || durata.isEmpty()) {
            errori.add("errore campi progetto");
        }   	    	   	
    	Integer durataInt=parseInt(durata, "durata int", errori); 	
    	LocalDate dataInizioProgetto=parseDate(dataInizioP, "data progetto formato", errori);
    	    
    	if(!errori.isEmpty()) {
    		finestraCreaProgetto.gestisciErroriProgetto1(errori);
    		return errori;
    	}	
    	try {
    		service.validaProgetto(nome, durataInt, dataInizioProgetto);
    	}catch(ValidazioneException v) {
    		errori.addAll(v.getErrori());
    	}   	
    	try {
	     service.validaSovrapposizioneProgetti(dataInizioProgetto, durataInt,lottoSelezionato.getCodLotto());		 
    	}catch(ValidazioneException v) {
    		errori.addAll(v.getErrori());
    	}catch(ErroreDatabaseException e) {
    		finestraCreaLotto.mostraMessaggio(e.getMessage());
    	}
	     return errori;
    }
    
    public ArrayList<String> validaCreazioneAttivitaProgetto(String nomeColtura, InputSeminaDTO inputSDTO, InputRaccoltaDTO inputRDTO){ 	
    	ArrayList<String> errori= new ArrayList<String>();   	
    	String durata= finestraCreaProgetto.getCmpDurata();
    	String dataInizioP= finestraCreaProgetto.getCmpDataInizio();  
    	int durataInt=parseInt(durata, "durata int", errori); 	
    	LocalDate dataInizioProgetto=parseDate(dataInizioP, "data progetto formato", errori);    	    
    	if(!errori.isEmpty()) {
    		finestraCreaProgetto.gestisciErroriProgetto1(errori);
    		return errori;
    	}   	
     	LocalDate dataInizioRaccolta=parseDate(inputRDTO.getDataInizioRaccolta(), "errore formato inizio raccolta", errori); 
    	LocalDate dataFineRaccolta=parseDate(inputRDTO.getDataFineRaccolta(), "errore formato fine raccolta", errori); 
     	double quantitaSemi=parseDouble(inputSDTO.getQuantitaSemi(), "quantita semi double", errori);
    	double quantitaPrevistaRaccolta=parseDouble(inputRDTO.getQuantitaPrevista(), "quantita prevista double", errori);    	
    	LocalDate dataInizioSemina= parseDate(inputSDTO.getDataInizioSemina(), "errore formato inizio semina", errori);
    	LocalDate dataFineSemina=parseDate(inputSDTO.getDataFineSemina(), "errore formato fine semina", errori);  	    	
	    	if(!errori.isEmpty()) {
	    		return errori;
	    	}
    	try {
    		service.validaDateAttivitaSemina(dataInizioSemina, dataFineSemina);
    	}catch(ValidazioneException v) {
    		return v.getErrori();
    	} 		
    	try {
    		service.validaDateAttivitaRaccolta(dataInizioRaccolta, dataFineRaccolta);
    	}catch(ValidazioneException v) {
    		return v.getErrori();
    	}
    	
    	TipoSemina seminaEnum= TipoSemina.valueOf(inputSDTO.getMetodoSemina().toString().toUpperCase());
    	TipoRaccolta raccoltaEnum= TipoRaccolta.valueOf(inputRDTO.getMetodoRaccolta().toString().toUpperCase());  	
    	try{
    			SeminaDTO sDTO= new SeminaDTO(dataInizioSemina,dataFineSemina,0,0,seminaEnum);
    			RaccoltaDTO rDTO= new RaccoltaDTO(dataInizioRaccolta,dataFineRaccolta,0,0,raccoltaEnum,quantitaPrevistaRaccolta,0);
    			service.validaAttivitaSeminaRaccolta(nomeColtura, inputSDTO.getColtivatoreSemina(), inputRDTO.getColtivatoreRaccolta(), sDTO, rDTO, quantitaSemi, attivitaTemporanee, listaSeminaColtura, durataInt, dataInizioProgetto, lottoSelezionato);
    			
    	}catch(UtenteNonTrovatoException e) {
    		finestraCreaProgetto.mostraMessaggio("coltivatore non trovato");
    	}catch(RisorsaNonTrovataException r) {
    		finestraCreaProgetto.mostraMessaggio("coltura non trovata");
    	}catch(ValidazioneException v) {
    		return v.getErrori();
    	}catch(ErroreDatabaseException er) {
    		finestraCreaProgetto.mostraMessaggio(er.getMessage());
    	}
    	return errori;
    }
    
    public ArrayList<String> creaNotifica(InputNotificaDTO dto) {
    	ArrayList<String> errori= new ArrayList<String>();
    	LivelloGravita livelloEnum= LivelloGravita.valueOf(dto.getLivelloGravita().toString().toUpperCase());   	 
    	 if (dto.getDestinatari().isEmpty()) {
    	      errori.add("errore destinatari vuoti");
    	 } 
    	 LocalDate oggi = LocalDate.now();
    	 Integer estensioneInt;
    	 try {
    	        if (dto.getTipoNotifica().equals("Attività Imminente")) {
    	            LocalDate scadenza = parseDate(dto.getDataScadenza(), "errore formato data", errori);
    	            if(!errori.isEmpty()) {
    	            	return errori;
    	            }
    	            AttivitaImminenteDTO attDTO = new AttivitaImminenteDTO(oggi,getUtenteLoggato().getIdUtente(),dto.getDescrizioneVeloce(),dto.getDescrizione(),scadenza, dto.getDestinatari());
    	            service.registraAttivitaImminente(attDTO);
    	        } else {
    	            if(dto.getEstensione()!= null && !dto.getEstensione().trim().isEmpty()) {  	            	
    	            		estensioneInt = parseInt(dto.getEstensione(), "errore formato estensione", errori);
    	            		if(!errori.isEmpty()) {
    	    	            	return errori;
    	    	            }
    	            }else {
    	            	estensioneInt=0;
    	            }
    	            AnomaliaDTO anomDTO = new AnomaliaDTO(oggi, getUtenteLoggato().getIdUtente(), dto.getDescrizioneVeloce(), dto.getDescrizione(), livelloEnum, estensioneInt,dto.getDestinatari());
    	            service.registraAnomalia(anomDTO);	        
    	        }
         }catch(UtenteNonTrovatoException u) {
    		 finestraCreaNotifica.mostraMessaggio("Alcuni o tutti utenti non trovati");
    	 }catch(ValidazioneException v) {
    		 return v.getErrori();
    	 }catch(ErroreDatabaseException er) {
    		 finestraCreaNotifica.mostraMessaggio(er.getMessage());
    	 }
         return errori;
    }
    	   
    public ArrayList<String> salvaProgetto(InputProgettoDTO inputPDTO, InputIrrigazioneDTO inputIDTO) {
    	ArrayList<String> errori= new ArrayList<String>();
    	 if (listaSeminaColtura == null || listaSeminaColtura.isEmpty()) {
    	        errori.add("errore lista vuota");
    	    }   	
    	String periodoComeEnum=inputPDTO.getPeriodo().replace("-", "_");
    	Stagione periodoEnum= Stagione.valueOf(periodoComeEnum.toString().toUpperCase()); 	
    	TipoIrrigazione irrigazioneEnum= TipoIrrigazione.valueOf(inputIDTO.getMetodoIrrigazione().toString().toUpperCase());    	
    	LocalDate dataI= parseDate(inputIDTO.getDataInizioIrrigazione(), "errore formato date irrigazione", errori);
    	LocalDate dataF= parseDate(inputIDTO.getDataFineIrrigazione(), "errore formato date irrigazione", errori);
    	if(!errori.isEmpty()) {
    		return errori;
    	} 	
    	Integer durata= Integer.parseInt(inputPDTO.getDurata());
		LocalDate dataP= LocalDate.parse(inputPDTO.getDataInizio());
    	try {
    			service.validaDateAttivitaIrrigazione(dataI, dataF);
    			IrrigazioneDTO iDTO= new IrrigazioneDTO(dataI,dataF,0,0,irrigazioneEnum);		
    			service.validaAttivitaIrrigazione(inputIDTO.getColtivatoreIrrigazione(), iDTO, dataI, durata, lottoSelezionato, attivitaTemporanee);
    			ProgettoDTO pDTO= new ProgettoDTO(finestraCreaProgetto.getCmpNome(),periodoEnum,durata,dataP,0,0);
        		service.registraProgetto(pDTO, utenteLoggato, lottoSelezionato, attivitaTemporanee, listaSeminaColtura);
    			
    	}catch(UtenteNonTrovatoException e) {
    		finestraCreaProgetto.mostraMessaggio("Coltivatore non trovato");
    	}catch(ValidazioneException v) {
    		return v.getErrori();
    	}catch(ErroreDatabaseException er) {
    		finestraCreaProgetto.mostraMessaggio(er.getMessage());
    	} catch(RisorsaNonTrovataException r) {
    		finestraCreaProgetto.mostraMessaggio("Tutte o alcune attività non trovate.");
    	}
		return errori;
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
	
	public void prendiDTOProprietario(InputUtenteDTO dto) {
		this.proprietarioInIscrizione= dto;
	}

	public static Utente getUtenteLoggato() {
		return utenteLoggato;
	}
	
	public static void setUtenteLoggato(Utente utenteLoggato) {
		Controller.utenteLoggato = utenteLoggato;
	}
	
	public Integer parseInt(String value, String errore, ArrayList<String> errori) {
	    try {
	        return Integer.parseInt(value.trim());
	    } catch (NumberFormatException e) {
	        errori.add(errore);
	        return null;
	    }
	}
	
	public Double parseDouble(String value, String errore, ArrayList<String> errori) {
	    try {
	        return Double.parseDouble(value.replace(",", "."));
	    } catch (NumberFormatException e) {
	        errori.add(errore);
	        return null;
	    }
	}
	
	public LocalDate parseDate(String value, String errore, ArrayList<String> errori) {
	    try {
	        return LocalDate.parse(value);
	    } catch (DateTimeParseException e) {
	        errori.add(errore);
	        return null;
	    }
	}
}
