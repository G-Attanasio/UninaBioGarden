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
    
    public void aggiungiLotto()  {
    	ArrayList<String> errori= new ArrayList<String>();
    	String tessitura= finestraCreaLotto.getTipoTessitura().replace(" ", "_");
    	String dimensioni= finestraCreaLotto.getDimensioni();
    	String ph= finestraCreaLotto.getPh().replace(",", ".");
    	String morfologia= finestraCreaLotto.getTipoMorfologia();
    	String altitudine= finestraCreaLotto.getAltitudine();
    	String localita= finestraCreaLotto.getLocalità();
    	String comune= finestraCreaLotto.getComune();
    	String provincia= finestraCreaLotto.getProvincia().toUpperCase();
    	
    	int dimensioniInt = parseInt(dimensioni, "dimensioni int", errori);
    	
    	double phDouble=parseDouble(ph, "ph numero", errori);
    	
    	int altitudineInt=parseInt(altitudine, "altitudine int", errori);
    	
    	if(!errori.isEmpty()) {
    		finestraCreaLotto.gestisciErrori(errori);
    		return;
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
			finestraCreaLotto.gestisciErrori(v.getErrori());
		}
    }
    
    public void caricaLotti() {
    	finestraVisualizzaLotti.svuotaTabella();
        try {
            int idUtente = getUtenteLoggato().getIdUtente();
            ArrayList<LottoDTO> listaLotti = service.caricaLottiUtente(idUtente);          
            for (LottoDTO lc : listaLotti) {
                Object[] riga = {
                    lc.getCodLotto(),
                    lc.getTessitura().toString().replace("_", " "),
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
        } catch (RisorsaNonTrovataException e) {
            finestraVisualizzaLotti.mostraMessaggio("Nessun lotto trovato.");
        } catch (ErroreDatabaseException e) {
            finestraVisualizzaLotti.mostraMessaggio(e.getMessage());
        }
    }
    
    public void caricaAttivitaAssegnate() {
    	finestraAttivitaAssegnate.svuotaTabella();
        try {
            ArrayList<AttivitaDTO> lista = service.caricaAttivitaAssegnate(utenteLoggato.getIdUtente());
            for (AttivitaDTO dto : lista) {
                Object[] riga = {
                    dto.getTipo(),
                    dto.getUsernameColtivatore(),
                    dto.getNomeProgetto(),
                    dto.getMetodo(),
                    dto.getDataInizio(),
                    dto.getDataFine(),
                    dto.getStatoEsecuzione()
                };
                finestraAttivitaAssegnate.aggiungiRigaTabella(riga);
            }
        } catch (RisorsaNonTrovataException e) {
            finestraAttivitaAssegnate.mostraMessaggio("Nessuna attività trovata");
        } catch (ErroreDatabaseException e) {
            finestraAttivitaAssegnate.mostraMessaggio(e.getMessage());
        }
    }
    
    public void caricaAttivitaColtivatore() {
    	 finestraVisualizzaAttivita.svuotaTabella();
        try {
            ArrayList<AttivitaDTO> lista = service.caricaAttivitaColtivatore(getUtenteLoggato().getIdUtente());          
            for (AttivitaDTO dto : lista) {
                Object[] riga = {
                    dto.getCodAttivita(),
                    dto.getTipo(),
                    dto.getColtura(),
                    dto.getNomeProgetto(),
                    dto.getMetodo(),
                    dto.getDataInizio(),
                    dto.getDataFine(),
                    dto.getStatoEsecuzione()
                };

                finestraVisualizzaAttivita.aggiungiRigaTabella(riga);
            }
        }catch (ErroreDatabaseException e) {
            finestraVisualizzaAttivita.mostraMessaggio(e.getMessage());
        }
    }
    
    public void registraRaccolta(int codAttivita, double quantita) {
        try {
            service.registraRaccolta(codAttivita, quantita);
            caricaAttivitaColtivatore();
        } catch (RisorsaNonTrovataException e) {
            finestraVisualizzaAttivita.mostraMessaggio("Attività non trovata");
        } catch (ErroreDatabaseException e) {
            finestraVisualizzaAttivita.mostraMessaggio(e.getMessage());
        }
    }
    
    public void caricaIMieiProgetti() {
        finestraVisualizzaProgetti.svuotaTabella(); 
        try {
            ArrayList<ProgettoDTO> lista =
                service.caricaProgettiProprietario(getUtenteLoggato().getIdUtente());
            for (ProgettoDTO p : lista) {
                Object[] riga = {
                    p.getCodProgetto(),
                    p.getNomeProgetto(),
                    p.getLottoImpegnato(),
                    p.getStagioneDiRiferimento(),
                    p.getDataInizio(),
                    p.getDurata(),
                    p.getStatoEsecuzione()
                };
                finestraVisualizzaProgetti.aggiungiRigaTabella(riga);
            }
        } catch (ErroreDatabaseException e) {
            finestraVisualizzaProgetti.mostraMessaggio(e.getMessage());
        }
    }
    
    public void caricaColture() {
        finestraVisualizzaColture.svuotaTabella();
        try {
            ArrayList<ColturaDTO> lista = service.caricaColture();
            for (ColturaDTO c : lista) {
                Object[] riga = {
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
        }
    }
    	
    public void caricaColtivatoriComeDestinatari() {
        try {
            ArrayList<String> lista =
                service.caricaUsernameColtivatori();
            finestraCreaNotifica.setElencoColtivatori(lista);
        } catch (ErroreDatabaseException e) {
            finestraCreaNotifica.mostraMessaggio(e.getMessage());
        }
    }
    
    public void caricaNotificheInviate() {
        finestraVisualizzaNotifiche.svuotaTabella();
        try {
            ArrayList<NotificaDTO> lista = service.caricaNotificheInviate(getUtenteLoggato().getIdUtente());        
            finestraVisualizzaNotifiche.onOffAggiungi(true);
            for (NotificaDTO n : lista) {

                Object[] riga = {
                    n.getTipo(),
                    n.getDescrizioneVeloce(),
                    n.getDataScadenza(),
                    n.getGravità(),                
                    n.getEstensione(),
                    n.getCodNotifica(),
                    n.getDescrizione()
                };
                finestraVisualizzaNotifiche.aggiungiRigaTabella(riga);
            }
        } catch (ErroreDatabaseException e) {
            finestraVisualizzaNotifiche.mostraMessaggio(e.getMessage());
        }
    }
    
    public void caricaNotificheRicevute() {
        finestraVisualizzaNotifiche.svuotaTabella();
        try {
            ArrayList<NotificaDTO> lista = service.caricaNotificheRicevute(getUtenteLoggato().getIdUtente());
            finestraVisualizzaNotifiche.onOffAggiungi(false);
            for (NotificaDTO n : lista) {
                Object[] riga = {
                    n.getTipo(),
                    n.getDescrizioneVeloce(),
                    n.getDataScadenza(),
                    n.getGravità(),
                    n.getEstensione(),
                    n.getCodNotifica(),
                    n.getDescrizione()
                };
                finestraVisualizzaNotifiche.aggiungiRigaTabella(riga);
            }
        } catch (ErroreDatabaseException e) {
            finestraVisualizzaNotifiche.mostraMessaggio(e.getMessage());
        }
    }
    
    public void caricaDatiReport(int codProgetto) {

        try {
            ArrayList<DatiReportDTO> dati =
                service.caricaDatiReport(codProgetto);
            if (getUtenteLoggato().getRuolo() == TipoRuolo.PROPRIETARIO) {
                finestraReport = finestraProprietario.getFinReport();
            } else {
                finestraReport = finestraProprietarioColtivatore.getFinReport();
            }
            finestraReport.costruisciGrafico(dati);
            mostraPanelInterno("report");
        } catch (ErroreDatabaseException e) {
            finestraReport.mostraMessaggio(e.getMessage());
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
    	}			
	     return errori;
    }
    
    public ArrayList<String> validaCreazioneAttivitaProgetto(String nomeColtura, InputSeminaDTO inputSDTO, InputRaccoltaDTO inputRDTO) { 	
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
    	}
    	return errori;
    }
    
    public ArrayList<String> creaNotifica() {
    	ArrayList<String> errori= new ArrayList<String>();
    	String descrizioneVeloce= finestraCreaNotifica.getCmpDescrizioneVeloce();
    	String descrizione= finestraCreaNotifica.getCmpDescrizione();
    	String tipoNotifica= finestraCreaNotifica.getSceltaNotifica();
    	String livelloGravita=finestraCreaNotifica.getCmpLivelloGravita();
    	String estensione= finestraCreaNotifica.getCmpEstensione();
    	ArrayList<String> nomi= finestraCreaNotifica.getNomiDestinatariSelezionati();	
    	LivelloGravita livelloEnum= LivelloGravita.valueOf(livelloGravita.toString().toUpperCase());   	 
    	 if (nomi.isEmpty()) {
    	      errori.add("errore destinatari vuoti");
    	 } 
    	 LocalDate oggi = LocalDate.now();
    	 Integer estensioneInt;
    	 try {
    	        if (tipoNotifica.equals("Attività Imminente")) {
    	            LocalDate scadenza = parseDate(finestraCreaNotifica.getCmpDataScadenza(), "errore formato data", errori);
    	            if(!errori.isEmpty()) {
    	            	return errori;
    	            }
    	            AttivitaImminenteDTO attDTO = new AttivitaImminenteDTO(oggi,getUtenteLoggato().getIdUtente(),descrizioneVeloce,descrizione,scadenza, nomi);
    	            service.salvaAttivitaImminente(attDTO);
    	        } else {
    	            if(estensione!= null && !estensione.trim().isEmpty()) {  	            	
    	            		estensioneInt = parseInt(finestraCreaNotifica.getCmpEstensione(), "errore formato estensione", errori);
    	            		if(!errori.isEmpty()) {
    	    	            	return errori;
    	    	            }
    	            }else {
    	            	estensioneInt=0;
    	            }
    	            AnomaliaDTO anomDTO = new AnomaliaDTO(oggi, getUtenteLoggato().getIdUtente(), descrizioneVeloce, descrizione, livelloEnum, estensioneInt,nomi);
    	            service.salvaAnomalia(anomDTO);	        
    	        }
         }catch(UtenteNonTrovatoException u) {
    		 finestraCreaNotifica.mostraMessaggio("Alcuni o tutti utenti non trovati");
    	 }catch(ValidazioneException v) {
    		 return v.getErrori();
    	 }
    	 if(errori.isEmpty()) {
    	        finestraCreaNotifica.getModelloScelti().clear();
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
    			
    	}catch(UtenteNonTrovatoException e) {
    		finestraCreaProgetto.mostraMessaggio("coltivatore non trovato");
    	}catch(ValidazioneException v) {
    		return v.getErrori();
    	}
    	ProgettoDTO pDTO= new ProgettoDTO(finestraCreaProgetto.getCmpNome(),periodoEnum,durata,dataP,0,0);
    		service.salvaProgetto(pDTO, utenteLoggato, lottoSelezionato, attivitaTemporanee, listaSeminaColtura);
    		
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
