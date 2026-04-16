package service;

import java.sql.SQLException;
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
        try{
        	progettoDAO.sincronizzaSistema();
        	Utente u = utenteDAO.prelevaPerLogin(username, password);   
        if(u == null) {
        	throw new UtenteNonTrovatoException();
            }
        return u;
        }catch(SQLException e) {
        	e.printStackTrace();
        	throw new ErroreDatabaseException();
        }
    }
	
	public Utente registraColtivatore(UtenteDTO dto) throws EmailUsernameGiàEsistentiException, ErroreDatabaseException {
		 ArrayList<String> errori= new ArrayList<String>();
		 
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
	
	public Utente registraProprietario(UtenteDTO uDto,LottoDTO lDto,ArrayList<String> erroriParse) throws EmailUsernameGiàEsistentiException {
		 ArrayList<String> errori= new ArrayList<String>(erroriParse);
		
		 if(!Utente.isSoloLettere(uDto.getNome())) {
    		 errori.add("lettere nome");
    	 }
    	 if(!Utente.isLunghezzaValida(uDto.getNome())) {
    		 errori.add("lunghezza nome");
    	 }
    	 if(!Utente.isSoloLettere(uDto.getCognome())) {
    		 errori.add("lettere cognome");
    	 }
    	 if (!Utente.isLunghezzaValida(uDto.getCognome())) {
    		 errori.add("lunghezza cognome");
    	 }
    	 if(!Utente.isLunghezzaValida(uDto.getUsername())) {
    		 errori.add("lunghezza username");
    	 }
    	 if(!Utente.isEmailValida(uDto.getEmail())) {
    		 errori.add("email non valida");
    	 }
    	 if(!Utente.isLunghezzaValida(uDto.getEmail())) {
    		 errori.add("lunghezza email");
    	 }
    	 if(!Utente.isEtaCoerente(uDto.getDataNascita())) {
 			errori.add("data nascita");
 		 }
    	 if (uDto.getPassword().isEmpty() || uDto.getPassword().length() < 4 || uDto.getPassword().length() > 30) {
  		    errori.add("lunghezza password");
  		 }
  	     if (!uDto.getPassword().equals(uDto.getConfermaPassword())) {
  		    errori.add("password");
  	     }
		Utente u= new Utente( uDto.getNome(), uDto.getCognome(), uDto.getUsername(), 
		        uDto.getPassword(), uDto.getEmail(), uDto.getDataNascita(), uDto.getRuolo()
			    );
		if(!LottoColtivabile.isValidDimensioni(lDto.getDimensioni())) {
			errori.add("dimensioni");
		}
		if(!LottoColtivabile.isPhValidoMioDominio(lDto.getPh())) {
			errori.add("ph");
		}
		if(!LottoColtivabile.isAltitudineValida(lDto.getAltitudine())) {
			errori.add("altitudine");
		}
		if(!LottoColtivabile.isLunghezzaValida(lDto.getLocalita())) {
    		errori.add("lunghezza localita");
    	}
    	if(!LottoColtivabile.isSoloLettere(lDto.getLocalita())) {
    		errori.add("lettere localita");
    	}
    	if(!LottoColtivabile.isLunghezzaValida(lDto.getComune())) {
    		errori.add("lunghezza comune");
    	}
    	if(!LottoColtivabile.isSoloLettere(lDto.getComune())) {
    		errori.add("lettere comune");
    	}
    	if (!LottoColtivabile.isLunghezzaProvinciaValida(lDto.getProvincia())) {
    	    errori.add("lunghezza provincia");
    	}
    	if (!LottoColtivabile.isSoloLettere(lDto.getProvincia())) {
    	    errori.add("lettere provincia");
    	} 
		 if(!errori.isEmpty()) {
    		 throw new ValidazioneException(errori);
    	 }
		LottoColtivabile lc= new LottoColtivabile( lDto.getTessitura(),lDto.getDimensioni(),lDto.getPh(),
				lDto.getMorfologia(),lDto.getAltitudine(),lDto.getLocalita(),lDto.getComune(),lDto.getProvincia(),u);
		try {
		boolean salvato= utenteDAO.salvaConLotto(u, lc);
		if(salvato) {
			return u;
		    }
		throw new EmailUsernameGiàEsistentiException();
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean salvaLotto(LottoDTO lDTO) {
		ArrayList<String> errori= new ArrayList<String>();
		
		if(!LottoColtivabile.isValidDimensioni(lDTO.getDimensioni())) {
			errori.add("dimensioni");
		}
		if(!LottoColtivabile.isPhValidoMioDominio(lDTO.getPh())) {
			errori.add("ph");
		}
		if(!LottoColtivabile.isAltitudineValida(lDTO.getAltitudine())) {
			errori.add("altitudine");
		}
		if(!LottoColtivabile.isLunghezzaValida(lDTO.getLocalita())) {
    		errori.add("lunghezza localita");
    	}
    	if(!LottoColtivabile.isSoloLettere(lDTO.getLocalita())) {
    		errori.add("lettere localita");
    	}
    	if(!LottoColtivabile.isLunghezzaValida(lDTO.getComune())) {
    		errori.add("lunghezza comune");
    	}
    	if(!LottoColtivabile.isSoloLettere(lDTO.getComune())) {
    		errori.add("lettere comune");
    	}
    	if (!LottoColtivabile.isLunghezzaProvinciaValida(lDTO.getProvincia())) {
    	    errori.add("lunghezza provincia");
    	}
    	if (!LottoColtivabile.isSoloLettere(lDTO.getProvincia())) {
    	    errori.add("lettere provincia");
    	} 
		if(!errori.isEmpty()) {
    		 throw new ValidazioneException(errori);
    	}
		LottoColtivabile lc= new LottoColtivabile( lDTO.getTessitura(),lDTO.getDimensioni(),lDTO.getPh(),
				lDTO.getMorfologia(),lDTO.getAltitudine(),lDTO.getLocalita(),lDTO.getComune(),lDTO.getProvincia(),null);
		try {
		boolean salvato= lottoDAO.salva(lc, lDTO.getIdProprietario());
		if(salvato) {
			return true;
		    }
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public LottoColtivabile avviaProgetto(int codLotto) throws RisorsaNonTrovataException,ErroreDatabaseException {
		try{
			LottoColtivabile lc=lottoDAO.preleva(codLotto);
			return lc;
		}catch(SQLException e) {
			e.printStackTrace();
			throw new ErroreDatabaseException();
		}
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
	
	public void validaSovrapposizioneProgetti(LocalDate dataInizio,int durata, int codLotto){
		ArrayList<String> errori= new ArrayList<String>();
		LocalDate dataFine= dataInizio.plusDays(durata);
		ArrayList<ProgettoStagionale> listaProgetti= new ArrayList<ProgettoStagionale>();
		try {
		listaProgetti= progettoDAO.prelevaProgettiPerLotto(codLotto);
		for(ProgettoStagionale ps : listaProgetti ) {
   		 LocalDate inizioEsistente = ps.getDataInizio();
   	        LocalDate fineEsistente = inizioEsistente.plusDays(ps.getDurata());
   	        
   	        if(!dataInizio.isAfter(fineEsistente) && !dataFine.isBefore(inizioEsistente)) {
   	        	errori.add("errore sovr progetti");
   	        	throw new ValidazioneException(errori);
   	        }	      
		}		
		}catch(SQLException e) {
			e.printStackTrace();			
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
	
	public void validaAttivitaSeminaRaccolta(String nomeColtura, String coltS, String coltR, SeminaDTO sDTO, RaccoltaDTO rDTO,double quantitaSemi, ArrayList<Attivita> attivitaTemporanee,ArrayList<SeminaColtura> listaSeminaColtura,int durataProgetto, LocalDate dataInizioProgetto, LottoColtivabile lotto)throws UtenteNonTrovatoException,RisorsaNonTrovataException {
		ArrayList<String> errori= new ArrayList<String>();
		
		try{
			Coltura coltura= colturaDAO.prelevaColturaDaNome(nomeColtura);
			if(coltura== null) {
				throw new RisorsaNonTrovataException();
			}
			Utente coltivatoreS= utenteDAO.prelevaDaUsername(coltS);
			Utente coltivatoreR= utenteDAO.prelevaDaUsername(coltR);
			if(coltivatoreS== null || coltivatoreR == null ) {
				throw new UtenteNonTrovatoException();
			}
			Semina semina= new Semina(sDTO.getDataInizio(),sDTO.getDataFine(),coltivatoreS,null,sDTO.getMetodoSemina());
			Raccolta raccolta= new Raccolta(rDTO.getDataInizio(),rDTO.getDataFine(),coltivatoreR, null, rDTO.getMetodoRaccolta(),rDTO.getQuantitaPrevista(),coltura);
			SeminaColtura seminaColtura= new SeminaColtura(coltura,semina,quantitaSemi);
			if(!Raccolta.isQuantitaPrevistaValida(rDTO.getQuantitaPrevista())) {
				errori.add("errore <0");
			}
			if(!SeminaColtura.isQuantitaSemiValida(quantitaSemi)) {
				errori.add("errore <1");
			}
			if(!isAttivitaNonSovrapposta(coltS, semina, attivitaTemporanee)) {
				errori.add("colt semina sovr attivita");
			}
			if(!isAttivitaNonSovrapposta(coltR, raccolta, attivitaTemporanee)) {
				errori.add("colt raccolta sovr attivita");
			}
			if(!durataAttivitaProgetto(semina, dataInizioProgetto, durataProgetto)) {
				errori.add("errore semina sovr progetto");
			}
			if(!durataAttivitaProgetto(raccolta, dataInizioProgetto, durataProgetto)) {
				errori.add("errore raccolta sovr progetto");
			}
			if(!coerenzaSeminaDurata(semina.getDataFine(), dataInizioProgetto, coltura.getTempoMaturazione(), durataProgetto)) {
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
			attivitaTemporanee.add(semina);
			attivitaTemporanee.add(raccolta);
			listaSeminaColtura.add(seminaColtura);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void validaAttivitaIrrigazione(String coltI, IrrigazioneDTO iDTO, LocalDate dataInizioP, int durataP, LottoColtivabile lottoSelezionato, ArrayList<Attivita> attivitaTemporanee )throws UtenteNonTrovatoException {
		ArrayList<String> errori= new ArrayList<String>();
		try {
		Utente coltivatoreI= utenteDAO.prelevaDaUsername(coltI);
		if(coltivatoreI== null) {
			throw new UtenteNonTrovatoException();
		}
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
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void salvaProgetto(ProgettoDTO pDTO, Utente utenteLoggato, LottoColtivabile lottoSelezionato, ArrayList<Attivita> attivitaTemporanee, ArrayList<SeminaColtura> listaSeminaColtura) {
		try {
			ProgettoStagionale ps= new ProgettoStagionale(pDTO.getNomeProgetto(),pDTO.getStagioneDiRiferimento(),pDTO.getDurata(),pDTO.getDataInizio(),utenteLoggato,lottoSelezionato);
			boolean salvataggio= progettoDAO.salvaProgettoCompleto(ps, attivitaTemporanee, listaSeminaColtura);
			if(salvataggio) {
				attivitaTemporanee.clear();
				listaSeminaColtura.clear();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void salvaAttivitaImminente(AttivitaImminenteDTO attDTO) throws UtenteNonTrovatoException {
		ArrayList<String> errori= new ArrayList<String>();
		if(!Notifica.isNotificaLunghezzaValida(attDTO.getTipoAttivitaImminente())) {
   		 	errori.add("errore descrizione veloce");
   	    }
   	    if(!Notifica.isDescrizioneLunghezzaValida(attDTO.getDescrizione())) {
   		    errori.add("errore descrizione");
   	    }
   	    if(!errori.isEmpty()) {
   	    	throw new ValidazioneException(errori);
	    }
		ArrayList<Utente> destinatari= new ArrayList<Utente>();
		try {
			Utente creatore= utenteDAO.prelevaPerId(attDTO.getIdCreatore());
		for(String username: attDTO.getDestinatari()) {
			Utente u= utenteDAO.prelevaDaUsername(username);
			destinatari.add(u);
		}
		AttivitaImminente att= new AttivitaImminente(attDTO.getDataInvio(),creatore,attDTO.getTipoAttivitaImminente(),attDTO.getDescrizione(),attDTO.getDataScadenza(),destinatari);
		notificaDAO.salvaNotifica(att);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void salvaAnomalia(AnomaliaDTO anomDTO) throws UtenteNonTrovatoException{
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
		try {
			Utente creatore= utenteDAO.prelevaPerId(anomDTO.getIdCreatore());
		for(String username: anomDTO.getDestinatari()) {
			Utente u= utenteDAO.prelevaDaUsername(username);
			destinatari.add(u);
		}
		Anomalia anom= new Anomalia(anomDTO.getDataInvio(),creatore,anomDTO.getTipoAnomalia(),anomDTO.getDescrizione(),anomDTO.getGravita(),anomDTO.getEstensione(),destinatari);
		notificaDAO.salvaNotifica(anom);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<LottoDTO> caricaLottiUtente(int idUtente) throws RisorsaNonTrovataException, ErroreDatabaseException {
	    try {
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
	    } catch (SQLException e) {
	        throw new ErroreDatabaseException();
	    }
	}
	
	public void registraRaccolta(int codAttivita, double quantita) throws RisorsaNonTrovataException, ErroreDatabaseException {
	    try {
	        boolean ok = attivitaDAO.aggiornaQuantitaReale(codAttivita, quantita);
	        if (!ok) {
	            throw new RisorsaNonTrovataException();
	        }
	    } catch (SQLException e) {
	    	e.printStackTrace();
	        throw new ErroreDatabaseException();
	    }
	}
	
	public ArrayList<AttivitaDTO> caricaAttivitaAssegnate(int idUtente) throws RisorsaNonTrovataException, ErroreDatabaseException {
	    try {
	        ArrayList<Attivita> tutte = attivitaDAO.prelevaAttivitaAssegnateDaProprietario(idUtente);
	        if(tutte==null || tutte.isEmpty()) {
	        	throw new RisorsaNonTrovataException();
	        }
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
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new ErroreDatabaseException();
	    }
	}
	
	public ArrayList<AttivitaDTO> caricaAttivitaColtivatore(int idUtente) throws ErroreDatabaseException {
	    try {
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
	    } catch (SQLException e) {
	    	e.printStackTrace();
	        throw new ErroreDatabaseException();
	    }
	}
	
	public ArrayList<ProgettoDTO> caricaProgettiProprietario(int idUtente) throws ErroreDatabaseException {
	    try {	        
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
	    } catch (SQLException e) {
	    	e.printStackTrace();
	        throw new ErroreDatabaseException();
	    }
	}
	
	public ArrayList<ColturaDTO> caricaColture() throws RisorsaNonTrovataException, ErroreDatabaseException {
	    try {
	        ArrayList<Coltura> colture = colturaDAO.preleva();
	        if(colture== null) {
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
	    } catch (SQLException e) {
	    	e.printStackTrace();
	        throw new ErroreDatabaseException();
	    }
	}
	
	public ArrayList<String> caricaNomiColture() throws RisorsaNonTrovataException, ErroreDatabaseException {
	    try {
	        ArrayList<Coltura> lista = colturaDAO.preleva();
	        if(lista==null) {
	        	throw new RisorsaNonTrovataException();
	        }

	        ArrayList<String> nomi = new ArrayList<>();
	        for (Coltura c : lista) {
	            nomi.add(c.getNome());
	        }
	        return nomi;
	    } catch (SQLException e) {
	    	e.printStackTrace();
	        throw new ErroreDatabaseException();
	    }
	}
	
	public ArrayList<String> caricaUsernameColtivatori() throws ErroreDatabaseException {
	    try {
	        ArrayList<String> usernames = utenteDAO.prelevaPerProgetto();
	        return usernames;
	    } catch (SQLException e) {
	    	e.printStackTrace();
	        throw new ErroreDatabaseException();
	    }
	}
	
	public ArrayList<NotificaDTO> caricaNotificheInviate(int idUtente) throws ErroreDatabaseException {
	    try {
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
	            		 dto.setDataScadenza(ai.getDataScadenza());	            		 
	            		 dto.setGravità("---");
	            		 
	            }
	            else if (n instanceof Anomalia an) {
	                dto.setTipo("Anomalia");
	                dto.setDescrizioneVeloce(an.getTipoAnomalia());
	                dto.setDescrizione(an.getDescrizione());
	                dto.setGravità(an.getGravita().toString());
	                dto.setEstensione(an.getEstensione());
	            }
	            lista.add(dto);
	        }
	        return lista;
	    } catch (SQLException e) {
	    	e.printStackTrace();
	        throw new ErroreDatabaseException();
	    }
	}
	
	public ArrayList<NotificaDTO> caricaNotificheRicevute(int idUtente) throws ErroreDatabaseException {
	    try {
	        ArrayList<Notifica> notifiche =
	            notificaDAO.prelevaNotificheRicevute(idUtente);
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
	                dto.setDataScadenza(ai.getDataScadenza());
	                dto.setGravità("---");
	                
	            }
	            else if (n instanceof Anomalia an) {
	                dto.setTipo("Anomalia");
	                dto.setDescrizioneVeloce(an.getTipoAnomalia());
	                dto.setDescrizione(an.getDescrizione());
	                dto.setGravità(an.getGravita().toString());
	                dto.setEstensione(an.getEstensione());
	            }
	            lista.add(dto);
	        }
	        return lista;
	    } catch (SQLException e) {
	    	e.printStackTrace();
	        throw new ErroreDatabaseException();
	    }
	}
	
	public ArrayList<DatiReportDTO> caricaDatiReport(int codProgetto) throws ErroreDatabaseException {
	    try {
	        ArrayList<DatiReport> dati = reportDAO.prelevaDatiReport(codProgetto);
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
	    } catch (SQLException e) {
	    	e.printStackTrace();
	        throw new ErroreDatabaseException();
	    }
	}
	
	public void eliminaLotto(int codLotto) throws RisorsaNonTrovataException, ErroreDatabaseException {
	    try {	        
	        if (!lottoDAO.cancellaLotto(codLotto)) {
	            throw new RisorsaNonTrovataException();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new ErroreDatabaseException();
	    }
	}
	
	public void eliminaProgetto(int codProgetto) throws RisorsaNonTrovataException,ErroreDatabaseException {
	    try {	       
	        if (!progettoDAO.eliminaProgetto(codProgetto)) {
	            throw new RisorsaNonTrovataException();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();	
	        throw new ErroreDatabaseException();
	    }
	}
	
	public void eliminaNotificaInviata(int codNotifica)throws RisorsaNonTrovataException, ErroreDatabaseException {
	    try {
	        if(!notificaDAO.eliminaNotificaInviata(codNotifica)) {
	        	throw new RisorsaNonTrovataException();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); 
	        throw new ErroreDatabaseException();
	    }
	}
	
	public void eliminaNotificaRicevuta(int codNotifica, int idUtente)throws RisorsaNonTrovataException, ErroreDatabaseException {
	    try {
	        if(!notificaDAO.eliminaNotificaRicevuta(codNotifica, idUtente)) {
	        	throw new RisorsaNonTrovataException();
        	}
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new ErroreDatabaseException();
	    }
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
    
    public boolean isAttivitaNonSovrapposta(String username,Attivita attivita, ArrayList<Attivita> listaAttivita) {
    	try {	
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
    	    
    }catch(RisorsaNonTrovataException e) {
    	
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

    public boolean metodoIrrigazionePendenza(LottoColtivabile lc, Irrigazione i) {
    	if(i.getMetodoIrrigazione()==TipoIrrigazione.SOMMERSIONE && (lc.getMorfologia()==TipoMorfologia.COLLINARE || lc.getMorfologia()==TipoMorfologia.MONTUOSO)) {
    		return false;
    	}
    	return true;
    }
	
}
