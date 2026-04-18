package service;


import java.time.LocalDate;
import java.util.ArrayList;


import dao.*;
import dto.*;
import exceptions.*;
import model.*;



public class Service {


	private UtenteDAO utenteDAO;
	private LottoDAO lottoDAO;
	private ColturaDAO colturaDAO;
	private AttivitaDAO attivitaDAO; 
	private ProgettoDAO progettoDAO;
	private NotificaDAO notificaDAO;
	private ReportDAO reportDAO;
	
	public Service() {
		this.utenteDAO= new UtenteDAO();
		this.lottoDAO= new LottoDAO();
		this.colturaDAO= new ColturaDAO();
		this.attivitaDAO= new AttivitaDAO();
		this.progettoDAO= new ProgettoDAO();
		this.notificaDAO= new NotificaDAO();
		this.reportDAO= new ReportDAO();
	}
	
	public Utente effettuaLogin(String username, String password) throws UtenteNonTrovatoException, ErroreDatabaseException {		
        	progettoDAO.sincronizzaSistema();
        	Utente u = utenteDAO.prelevaPerLogin(username, password);   
        	return u;
    }
	
	public Utente registraColtivatore(UtenteDTO dto) throws EmailUsernameGiàEsistentiException, ErroreDatabaseException {
		ArrayList<String> errori= new ArrayList<String>();
		validaUtente(dto,errori);
		 if(!errori.isEmpty()) {
    		 throw new ValidazioneException(errori);
    	 }
	    Utente u = new Utente(
	        dto.getNome(), dto.getCognome(), dto.getUsername(), 
	        dto.getPassword(), dto.getEmail(), dto.getDataNascita(), dto.getRuolo()
	    );
	    utenteDAO.salva(u);
	    return u;
	}
	
	public Utente registraProprietario(UtenteDTO uDto,LottoDTO lDto) throws EmailUsernameGiàEsistentiException, ErroreDatabaseException {
		ArrayList<String> errori= new ArrayList<String>();
		validaUtente(uDto, errori);	
		Utente u= new Utente( uDto.getNome(), uDto.getCognome(), uDto.getUsername(), 
		        uDto.getPassword(), uDto.getEmail(), uDto.getDataNascita(), uDto.getRuolo()
			    );
		validaLotto(lDto, errori);
		 if(!errori.isEmpty()) {
    		 throw new ValidazioneException(errori);
    	 }
		LottoColtivabile lc= new LottoColtivabile( lDto.getTessitura(),lDto.getDimensioni(),lDto.getPh(),
				lDto.getMorfologia(),lDto.getAltitudine(),lDto.getLocalita(),lDto.getComune(),lDto.getProvincia(),u);		
		utenteDAO.salvaConLotto(u, lc);
		return u;		
	}
	
	public void registraLotto(LottoDTO lDTO) throws ErroreDatabaseException {
		ArrayList<String> errori= new ArrayList<String>();
		validaLotto(lDTO, errori);
		if(!errori.isEmpty()) {
    		 throw new ValidazioneException(errori);
    	}
		LottoColtivabile lc= new LottoColtivabile( lDTO.getTessitura(),lDTO.getDimensioni(),lDTO.getPh(),
				lDTO.getMorfologia(),lDTO.getAltitudine(),lDTO.getLocalita(),lDTO.getComune(),lDTO.getProvincia(),null);	
		lottoDAO.salva(lc, lDTO.getIdProprietario());	
	}
	
	public LottoColtivabile avviaProgetto(int codLotto) throws RisorsaNonTrovataException,ErroreDatabaseException {
			LottoColtivabile lc=lottoDAO.preleva(codLotto);
			return lc;	
	}
	
	public void validaProgetto(String nome, int durata, LocalDate dataInizio) {
		 ArrayList<String> errori= new ArrayList<String>();
		 if(!ProgettoStagionale.isLunghezzaNomeValida(nome)) {
	    		errori.add("errore lunghezza nome");
	    	}
		 if(!ProgettoStagionale.isDurataValida(durata)) {
 				errori.add("errore durata");
 			}
		 if(!ProgettoStagionale.isDataInizioValida(dataInizio)) {
 				errori.add("errore data progetto");
 			}
		 if(!errori.isEmpty()) {
    		 throw new ValidazioneException(errori);
    	 }
	}
	
	public void validaSovrapposizioneProgetti(LocalDate dataInizio,int durata, int codLotto) throws ErroreDatabaseException{
		ArrayList<String> errori= new ArrayList<String>();
		LocalDate dataFine= dataInizio.plusDays(durata);
		ArrayList<ProgettoStagionale> listaProgetti= new ArrayList<ProgettoStagionale>();
		listaProgetti= progettoDAO.prelevaProgettiPerLotto(codLotto);
		for(ProgettoStagionale ps : listaProgetti ) {
   		 LocalDate inizioEsistente = ps.getDataInizio();
   	        LocalDate fineEsistente = inizioEsistente.plusDays(ps.getDurata());
   	        
   	        if(!dataInizio.isAfter(fineEsistente) && !dataFine.isBefore(inizioEsistente)) {
   	        	errori.add("errore sovr progetti");
   	        	throw new ValidazioneException(errori);
   	        }	      
		}					
	}
	
	public void validaDateAttivitaSemina(LocalDate dataInizio, LocalDate dataFine) {
		ArrayList<String> errori= new ArrayList<String>();
		if(!Attivita.isDataInizioValida(dataInizio)) {
			errori.add("errore datainizio semina");
		}
		if(!Attivita.isDataFineValida(dataInizio, dataFine)) {
			errori.add("errore datafine semina");
		}
		if(!errori.isEmpty()) {
   		 throw new ValidazioneException(errori);
		}
	}
	
	public void validaDateAttivitaRaccolta(LocalDate dataInizio, LocalDate dataFine) {
		ArrayList<String> errori= new ArrayList<String>();
		if(!Attivita.isDataInizioValida(dataInizio)) {
    		errori.add("errore datainizio raccolta");
    	}
    	if(!Attivita.isDataFineValida(dataInizio, dataFine)) {
    		errori.add("errore datafine raccolta");
    	}
		if(!errori.isEmpty()) {
   		 throw new ValidazioneException(errori);
		}
	}
	
	public void validaDateAttivitaIrrigazione(LocalDate dataInizio, LocalDate dataFine) {
		ArrayList<String> errori= new ArrayList<String>();
		if(!Attivita.isDataInizioValida(dataInizio)) {
    		errori.add("errore datainizio irrigazione");
    	}
    	if(!Attivita.isDataFineValida(dataInizio, dataFine)) {
    		errori.add("errore datafine");
    	}
		if(!errori.isEmpty()) {
   		 throw new ValidazioneException(errori);
		}
	}
	
	public void validaAttivitaSeminaRaccolta(String nomeColtura, String coltS, String coltR, SeminaDTO sDTO, RaccoltaDTO rDTO,double quantitaSemi, ArrayList<Attivita> attTemp,ArrayList<SeminaColtura> listaSC,int durataP, LocalDate inizioP, LottoColtivabile lotto)throws UtenteNonTrovatoException,RisorsaNonTrovataException, ErroreDatabaseException {
		ArrayList<String> errori= new ArrayList<String>();	
			Coltura coltura= colturaDAO.prelevaColturaDaNome(nomeColtura);
			Utente coltivatoreS= utenteDAO.prelevaDaUsername(coltS);
			Utente coltivatoreR= utenteDAO.prelevaDaUsername(coltR);
			Semina semina= new Semina(sDTO.getDataInizio(),sDTO.getDataFine(),coltivatoreS,null,sDTO.getMetodoSemina());
			Raccolta raccolta= new Raccolta(rDTO.getDataInizio(),rDTO.getDataFine(),coltivatoreR, null, rDTO.getMetodoRaccolta(),rDTO.getQuantitaPrevista(),coltura);
			SeminaColtura seminaColtura= new SeminaColtura(coltura,semina,quantitaSemi);
			if(!Raccolta.isQuantitaPrevistaValida(rDTO.getQuantitaPrevista())) {
				errori.add("errore <0");
			}
			if(!SeminaColtura.isQuantitaSemiValida(quantitaSemi)) {
				errori.add("errore <1");
			}
			if(!isAttivitaNonSovrapposta(coltS, semina, attTemp)) {
				errori.add("colt semina sovr attivita");
			}
			if(!isAttivitaNonSovrapposta(coltR, raccolta, attTemp)) {
				errori.add("colt raccolta sovr attivita");
			}
			if(!durataAttivitaProgetto(semina, inizioP, durataP)) {
				errori.add("errore semina sovr progetto");
			}
			if(!durataAttivitaProgetto(raccolta, inizioP, durataP)) {
				errori.add("errore raccolta sovr progetto");
			}
			if(!coerenzaSeminaDurata(semina.getDataFine(), inizioP, coltura.getTempoMaturazione(), durataP)) {
				errori.add("errore coerenzaSeminaDurata");
			}
			if(!dataInizioRaccoltaValida(semina, raccolta)) {
				errori.add("dataInizioRaccoltaNonValida");
			}
			if(!metodoRaccoltaMontagna(lotto, raccolta)) {
				errori.add("errore meccanica montagna");
			}
			if(!errori.isEmpty()) {
	    		 throw new ValidazioneException(errori);
	    	}
			attTemp.add(semina);
			attTemp.add(raccolta);
			listaSC.add(seminaColtura);
	}
	
	public void validaAttivitaIrrigazione(String coltI, IrrigazioneDTO iDTO, LocalDate dataInizioP, int durataP, LottoColtivabile lottoSelezionato, ArrayList<Attivita> attivitaTemporanee )throws UtenteNonTrovatoException, ErroreDatabaseException, RisorsaNonTrovataException {
		ArrayList<String> errori= new ArrayList<String>();
		Utente coltivatoreI= utenteDAO.prelevaDaUsername(coltI);
		Irrigazione irrigazione= new Irrigazione(iDTO.getDataInizio(),iDTO.getDataFine(),coltivatoreI,null,iDTO.getMetodoIrrigazione());
		if(!isAttivitaNonSovrapposta(coltI, irrigazione, attivitaTemporanee)) {
			errori.add("colt irrigazione sovr attivita");
		}
		if(!durataAttivitaProgetto(irrigazione, dataInizioP, durataP)) {
			errori.add("errore irrigazione sovr progetto");
		}
		if(!metodoIrrigazionePendenza(lottoSelezionato, irrigazione)) {
			errori.add("errore pendenza sommersione");
		}
		if(!errori.isEmpty()) {
   		 throw new ValidazioneException(errori);
   	}
		attivitaTemporanee.add(irrigazione);		
	}
	
	public void registraProgetto(ProgettoDTO pDTO, Utente utenteLoggato, LottoColtivabile lottoSelezionato, ArrayList<Attivita> attivitaTemporanee, ArrayList<SeminaColtura> listaSeminaColtura) throws ErroreDatabaseException {	
			ProgettoStagionale ps= new ProgettoStagionale(pDTO.getNomeProgetto(),pDTO.getStagioneDiRiferimento(),pDTO.getDurata(),pDTO.getDataInizio(),utenteLoggato,lottoSelezionato);
			progettoDAO.salvaProgettoCompleto(ps, attivitaTemporanee, listaSeminaColtura);		
				attivitaTemporanee.clear();
				listaSeminaColtura.clear();
	}
	
	public void registraAttivitaImminente(AttivitaImminenteDTO attDTO) throws UtenteNonTrovatoException, ErroreDatabaseException {
		ArrayList<String> errori= new ArrayList<String>();
		if(!Notifica.isNotificaLunghezzaValida(attDTO.getTipoAttivitaImminente())) {
   		 	errori.add("errore descrizione veloce");
   	    }
   	    if(!Notifica.isDescrizioneLunghezzaValida(attDTO.getDescrizione())) {
   		    errori.add("errore descrizione");
   	    }
   	    if(attDTO.getDataScadenza().isBefore(LocalDate.now())) {
   	    	errori.add("errore data scadenza");
   	    }
   	    if(!errori.isEmpty()) {
   	    	throw new ValidazioneException(errori);
	    }
		ArrayList<Utente> destinatari= new ArrayList<Utente>();
			Utente creatore= utenteDAO.prelevaPerId(attDTO.getIdCreatore());
		for(String username: attDTO.getDestinatari()) {
			Utente u= utenteDAO.prelevaDaUsername(username);
			destinatari.add(u);
		}
		AttivitaImminente att= new AttivitaImminente(attDTO.getDataInvio(),creatore,attDTO.getTipoAttivitaImminente(),attDTO.getDescrizione(),attDTO.getDataScadenza(),destinatari);
		notificaDAO.salvaNotifica(att);	
	}
	
	public void registraAnomalia(AnomaliaDTO anomDTO) throws UtenteNonTrovatoException, ErroreDatabaseException{
		ArrayList<String> errori= new ArrayList<String>();
		if(!Notifica.isNotificaLunghezzaValida(anomDTO.getTipoAnomalia())) {
   		 	errori.add("errore descrizione veloce");
   	    }
   	    if(!Notifica.isDescrizioneLunghezzaValida(anomDTO.getDescrizione())) {
   	    	errori.add("errore descrizione");
   	    }
   	    if(!Anomalia.isEstensioneValida(anomDTO.getEstensione())) {
			errori.add("estensione <0");
		}
   	    if(!errori.isEmpty()) {
   	    	throw new ValidazioneException(errori);
	    }
		ArrayList<Utente> destinatari= new ArrayList<Utente>();
			Utente creatore= utenteDAO.prelevaPerId(anomDTO.getIdCreatore());
		for(String username: anomDTO.getDestinatari()) {
			Utente u= utenteDAO.prelevaDaUsername(username);
			destinatari.add(u);
		}
		Anomalia anom= new Anomalia(anomDTO.getDataInvio(),creatore,anomDTO.getTipoAnomalia(),anomDTO.getDescrizione(),anomDTO.getGravita(),anomDTO.getEstensione(),destinatari);
		notificaDAO.salvaNotifica(anom);		
	}
	
	public ArrayList<LottoDTO> caricaLottiUtente(int idUtente) throws RisorsaNonTrovataException, ErroreDatabaseException {
	        ArrayList<LottoColtivabile> lotti =
	            lottoDAO.prelevaLottiPerProprietario(idUtente);
	        if (lotti == null || lotti.isEmpty()) {
	            throw new RisorsaNonTrovataException();
	        }
	        ArrayList<LottoDTO> lista = new ArrayList<>();
	        for (LottoColtivabile l : lotti) {
	            LottoDTO dto = new LottoDTO(
	                l.getCodLotto(),
	                l.getTessitura(),
	                l.getDimensioni(),
	                l.getPh(),
	                l.getMorfologia(),
	                l.getAltitudine(),
	                l.getLocalita(),
	                l.getComune(),
	                l.getProvincia(),
	                idUtente,
	                l.isAttivo()
	            );

	            lista.add(dto);
	        }
	        return lista;
	}
	
	public void registraRaccolta(int codAttivita, double quantita) throws ErroreDatabaseException {
	      attivitaDAO.aggiornaQuantitaReale(codAttivita, quantita);	    
	}
	
	public ArrayList<AttivitaDTO> caricaAttivitaAssegnate(int idUtente) throws ErroreDatabaseException {
	        ArrayList<Attivita> tutte = attivitaDAO.prelevaAttivitaAssegnateDaProprietario(idUtente);
	        ArrayList<AttivitaDTO> lista = new ArrayList<>();
	        for (Attivita a : tutte) {
	            AttivitaDTO attDTO = new AttivitaDTO(
	            a.getCodAttivita(),
	            a.getStatoEsecuzione(),
	            a.getDataInizio(),
	            a.getDataFine(),
	            a.getColtivatore().getIdUtente(),
	            a.getProgetto().getCodProgetto()
	            );
	            attDTO.setUsernameColtivatore(a.getColtivatore().getUsername());
	            attDTO.setNomeProgetto(a.getProgetto().getNomeProgetto());
	            if (a instanceof Semina s) {
	                attDTO.setTipo("Semina");
	                attDTO.setMetodo(s.getMetodoSemina().toString());
	            } else if (a instanceof Raccolta r) {
	                attDTO.setTipo("Raccolta");
	                attDTO.setMetodo(r.getMetodoRaccolta().toString());
	            } else if (a instanceof Irrigazione i) {
	                attDTO.setTipo("Irrigazione");
	                attDTO.setMetodo(i.getMetodoIrrigazione().toString());
	            }            		
	            lista.add(attDTO);
	        }
	        return lista;    
	}
	
	public ArrayList<AttivitaDTO> caricaAttivitaColtivatore(int idUtente) throws ErroreDatabaseException {
	        ArrayList<Attivita> tutte = attivitaDAO.prelevaAttivitaColtivatore(idUtente);
	        ArrayList<SeminaColtura> dettagli = attivitaDAO.prelevaDettagliColturePerColtivatore(idUtente);
	        ArrayList<AttivitaDTO> lista = new ArrayList<>();
	        for (Attivita a : tutte) {
	            AttivitaDTO attDTO = new AttivitaDTO(
	            		a.getCodAttivita(),
	            		a.getStatoEsecuzione(),
	            		a.getDataInizio(),
	            		a.getDataFine(),
	            		idUtente,
	            		a.getProgetto().getCodProgetto()
	            		);

	            if (a instanceof Semina s) {
	                attDTO.setTipo("Semina");
	                attDTO.setMetodo(s.getMetodoSemina().toString());
	                for (SeminaColtura sc : dettagli) {
	                    if (sc.getSemina().getCodAttivita() == s.getCodAttivita()) {
	                        attDTO.setColtura(sc.getColtura().getNome());
	                    }
	                }
	                attDTO.setNomeProgetto(s.getProgetto().getNomeProgetto());
	            }
	            else if (a instanceof Raccolta r) {
	                attDTO.setTipo("Raccolta");
	                attDTO.setMetodo(r.getMetodoRaccolta().toString());
	                attDTO.setColtura(r.getColtura().getNome());
	                attDTO.setNomeProgetto(r.getProgetto().getNomeProgetto());
	            }
	            else if (a instanceof Irrigazione i) {
	                attDTO.setTipo("Irrigazione");
	                attDTO.setMetodo(i.getMetodoIrrigazione().toString());
	                attDTO.setNomeProgetto(i.getProgetto().getNomeProgetto());
	            }
	            lista.add(attDTO);
	        }
	        return lista;
	}
	
	public ArrayList<ProgettoDTO> caricaProgettiProprietario(int idUtente) throws ErroreDatabaseException {	        
	        ArrayList<ProgettoStagionale> progetti = progettoDAO.prelevaProgettiPerProprietario(idUtente);
	        ArrayList<ProgettoDTO> lista = new ArrayList<>();
	        for (ProgettoStagionale p : progetti) {
	            ProgettoDTO dto = new ProgettoDTO(
	            		p.getCodProgetto(),
	            		p.getNomeProgetto(),
	            		p.getStagioneDiRiferimento(),
	            		p.getDurata(),
	            		p.getDataInizio(),
	            		p.getDataFine(),
	            		p.getStatoEsecuzione(),
	            		p.getLottoImpegnato().getCodLotto(),
	            		idUtente
	            		);

	            lista.add(dto);
	        }
	        return lista;
	}
	
	public ArrayList<ColturaDTO> caricaColture() throws RisorsaNonTrovataException, ErroreDatabaseException {
	        ArrayList<Coltura> colture = colturaDAO.preleva();
	        if(colture== null || colture.isEmpty()) {
	        	throw new RisorsaNonTrovataException();
	        }
	        ArrayList<ColturaDTO> lista = new ArrayList<>();
	        for (Coltura c : colture) {
	            ColturaDTO dto = new ColturaDTO(
	                c.getCodColtura(),
	                c.getNome(),
	                c.getSpecie(),
	                c.getFamiglia(),
	                c.getTempoMaturazione(),
	                c.getDestinazioneUso(),
	                c.getPeriodoIdeale()
	            );
	            lista.add(dto);
	        }
	        return lista;
	}
	
	public ArrayList<String> caricaNomiColture() throws RisorsaNonTrovataException, ErroreDatabaseException {
	        ArrayList<Coltura> lista = colturaDAO.preleva();
	        if(lista==null || lista.isEmpty()) {
	        	throw new RisorsaNonTrovataException();
	        }

	        ArrayList<String> nomi = new ArrayList<>();
	        for (Coltura c : lista) {
	            nomi.add(c.getNome());
	        }
	        return nomi;    
	}
	
	public ArrayList<String> caricaUsernameColtivatori() throws ErroreDatabaseException, UtenteNonTrovatoException {
	        ArrayList<String> usernames = utenteDAO.prelevaPerProgetto();
	        if(usernames== null || usernames.isEmpty()) {
	        	throw new UtenteNonTrovatoException();
	        }
	        return usernames;    
	}
	
	public ArrayList<NotificaDTO> caricaNotificheInviate(int idUtente) throws ErroreDatabaseException {	    
	        ArrayList<Notifica> notifiche =  notificaDAO.prelevaNotificheInviate(idUtente);
	        ArrayList<NotificaDTO> lista = new ArrayList<>();	              
	        for (Notifica n : notifiche) {
	        	 ArrayList<String> usernamesDestinatari = new ArrayList<>();	
	        	 for (Utente u : n.getDestinatari()) {
	 	            usernamesDestinatari.add(u.getUsername()); 
	 	        }
	        	 NotificaDTO dto = new NotificaDTO(
	        			 n.getCodNotifica(),
	        			 n.getDataInvio(),
	        			 idUtente,
	        			 usernamesDestinatari
	        			 );        	 
	            if (n instanceof AttivitaImminente ai) {
	            		 dto.setTipo("Imminente");
	            		 dto.setDescrizioneVeloce(ai.getTipoAttivitaImminente());
	            		 dto.setDescrizione(ai.getDescrizione());
	            		 dto.setScadenza(ai.getDataScadenza().toString());	            		 
	            		 dto.setGravità("-------");
	            		 
	            }
	            else if (n instanceof Anomalia an) {
	                dto.setTipo("Anomalia");
	                dto.setDescrizioneVeloce(an.getTipoAnomalia());
	                dto.setDescrizione(an.getDescrizione());
	                dto.setGravità(an.getGravita().toString());
	                dto.setEstens(an.getEstensione()+" MQ");
	                dto.setScadenza("-------");
	            }
	            lista.add(dto);
	        }
	        return lista;
	}
	
	public ArrayList<NotificaDTO> caricaNotificheRicevute(int idUtente) throws ErroreDatabaseException {
	        ArrayList<Notifica> notifiche =notificaDAO.prelevaNotificheRicevute(idUtente);
	        ArrayList<NotificaDTO> lista = new ArrayList<>();
	        for (Notifica n : notifiche) {
	        	ArrayList<String> usernamesDestinatari = new ArrayList<>();	
	        	 for (Utente u : n.getDestinatari()) {
	 	            usernamesDestinatari.add(u.getUsername()); 
	 	        }
	            NotificaDTO dto = new NotificaDTO(
	                n.getCodNotifica(),
	                n.getDataInvio(),
	                0,
	                usernamesDestinatari
	            );
	            if (n instanceof AttivitaImminente ai) {
	                dto.setTipo("Imminente");
	                dto.setDescrizioneVeloce(ai.getTipoAttivitaImminente());
	                dto.setDescrizione(ai.getDescrizione());
	                dto.setScadenza(ai.getDataScadenza().toString());
	                dto.setGravità("-------");
	                
	            }
	            else if (n instanceof Anomalia an) {
	                dto.setTipo("Anomalia");
	                dto.setDescrizioneVeloce(an.getTipoAnomalia());
	                dto.setDescrizione(an.getDescrizione());
	                dto.setGravità(an.getGravita().toString());
	                dto.setEstens(an.getEstensione()+ " MQ");
	                dto.setScadenza("-------");
	            }
	            lista.add(dto);
	        }
	        return lista;
	}
	
	public ArrayList<DatiReportDTO> caricaDatiReport(int codProgetto) throws ErroreDatabaseException, RisorsaNonTrovataException {    
	        ArrayList<DatiReport> dati = reportDAO.prelevaDatiReport(codProgetto);
	        if(dati==null || dati.isEmpty()) {
	        	throw new RisorsaNonTrovataException();
	        }
	        ArrayList<DatiReportDTO> lista = new ArrayList<>();
	        for (DatiReport r : dati) {
	            DatiReportDTO dto = new DatiReportDTO(
	                r.getNomeColtura(),
	                r.getSemi(),
	                r.getPrevista(),
	                r.getReale()
	            );
	            lista.add(dto);
	        }
	        return lista;
	}
	
	public void eliminaLotto(int codLotto) throws ErroreDatabaseException {
	    lottoDAO.cancellaLotto(codLotto);   
	}
	
	public void eliminaProgetto(int codProgetto) throws ErroreDatabaseException {
		progettoDAO.eliminaProgetto(codProgetto);
	     
	}
	
	public void eliminaNotificaInviata(int codNotifica)throws ErroreDatabaseException {
	    notificaDAO.eliminaNotificaInviata(codNotifica);
	     
	}
	
	public void eliminaNotificaRicevuta(int codNotifica, int idUtente)throws ErroreDatabaseException {
		notificaDAO.eliminaNotificaRicevuta(codNotifica, idUtente);
	    
	}
    
    public boolean durataAttivitaProgetto(Attivita attivita,LocalDate dataInizioProgetto,int durataProgetto) {
    	LocalDate dataFineProgetto = dataInizioProgetto.plusDays(durataProgetto);     
    	return !attivita.getDataInizio().isBefore(dataInizioProgetto) && !attivita.getDataFine().isAfter(dataFineProgetto);
    }
    
    public boolean isAttivitaNonSovrapposta(String username,Attivita attivita, ArrayList<Attivita> listaAttivita) throws ErroreDatabaseException {	
    	ArrayList<Attivita> tutteLeAttivita=new ArrayList<Attivita>();
    	tutteLeAttivita=attivitaDAO.prelevaTutteAttivitaPerColtivatore(username);
    	tutteLeAttivita.addAll(listaAttivita);
    	 for (Attivita a : tutteLeAttivita) {  	     
    	        if (a.getColtivatore().getUsername().equals(username)) {  
    	            if (!attivita.getDataInizio().isAfter(a.getDataFine()) && !attivita.getDataFine().isBefore(a.getDataInizio())) {
    	                return false; 
    	            }
    	        }
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

    public boolean metodoIrrigazionePendenza(LottoColtivabile lc, Irrigazione i) {
    	if(i.getMetodoIrrigazione()==TipoIrrigazione.SOMMERSIONE && (lc.getMorfologia()==TipoMorfologia.COLLINARE || lc.getMorfologia()==TipoMorfologia.MONTUOSO)) {
    		return false;
    	}
    	return true;
    }
    
    public void validaUtente(UtenteDTO dto, ArrayList<String> errori) {
		 if(!Utente.isSoloLettere(dto.getNome())) {
    		 errori.add("lettere nome");
    	 }
    	 if(!Utente.isLunghezzaValida(dto.getNome())) {
    		 errori.add("lunghezza nome");
    	 }
    	 if(!Utente.isSoloLettere(dto.getCognome())) {
    		 errori.add("lettere cognome");
    	 }
    	 if (!Utente.isLunghezzaValida(dto.getCognome())) {
    		 errori.add("lunghezza cognome");
    	 }
    	 if(!Utente.isLunghezzaValida(dto.getUsername())) {
    		 errori.add("lunghezza username");
    	 }
    	 if(!Utente.isEmailValida(dto.getEmail())) {
    		 errori.add("email non valida");
    	 }
    	 if(!Utente.isLunghezzaValida(dto.getEmail())) {
    		 errori.add("lunghezza email");
    	 }
    	 if(!Utente.isEtaCoerente(dto.getDataNascita())) {
 			errori.add("data nascita");
 		 }
    	 if (dto.getPassword().isEmpty() || dto.getPassword().length() < 4 || dto.getPassword().length() > 30) {
 		    errori.add("lunghezza password");
 		 }
 	     if (!dto.getPassword().equals(dto.getConfermaPassword())) {
 		    errori.add("password");
 		 }
    }
    
    public void validaLotto(LottoDTO dto, ArrayList<String> errori) {
    	if(!LottoColtivabile.isValidDimensioni(dto.getDimensioni())) {
			errori.add("dimensioni");
		}
		if(!LottoColtivabile.isPhValidoMioDominio(dto.getPh())) {
			errori.add("ph");
		}
		if(!LottoColtivabile.isAltitudineValida(dto.getAltitudine())) {
			errori.add("altitudine");
		}
		if(!LottoColtivabile.isLunghezzaValida(dto.getLocalita())) {
    		errori.add("lunghezza localita");
    	}
    	if(!LottoColtivabile.isSoloLettere(dto.getLocalita())) {
    		errori.add("lettere localita");
    	}
    	if(!LottoColtivabile.isLunghezzaValida(dto.getComune())) {
    		errori.add("lunghezza comune");
    	}
    	if(!LottoColtivabile.isSoloLettere(dto.getComune())) {
    		errori.add("lettere comune");
    	}
    	if (!LottoColtivabile.isLunghezzaProvinciaValida(dto.getProvincia())) {
    	    errori.add("lunghezza provincia");
    	}
    	if (!LottoColtivabile.isSoloLettere(dto.getProvincia())) {
    	    errori.add("lettere provincia");
    	} 
    }
	
}
