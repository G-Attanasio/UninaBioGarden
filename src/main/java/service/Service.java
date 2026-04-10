package service;

import java.sql.SQLException;

import dao.AttivitaDAO;
import dao.ColturaDAO;
import dao.LottoDAO;
import dao.NotificaDAO;
import dao.ProgettoDAO;
import dao.ReportDAO;
import dao.UtenteDAO;
import dto.LottoDTO;
import dto.UtenteDTO;
import exceptions.EmailUsernameGiàEsistentiException;
import exceptions.UtenteNonTrovatoException;
import model.LottoColtivabile;
import model.Utente;

public class Service {

	private static Utente utenteLoggato;
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
	
	public UtenteDTO effettuaLogin(String username, String password) throws UtenteNonTrovatoException, SQLException {
        Utente u = utenteDAO.preleva(username, password);   
        if(u == null) {
        	throw new UtenteNonTrovatoException();
        }    
        return new UtenteDTO(u.getIdUtente(), u.getNome(), u.getCognome(),u.getUsername(),u.getPassword(),u.getEmail(),u.getDataNascita(),u.getRuolo());
    }
	
	public UtenteDTO registraColtivatore(UtenteDTO dto) throws EmailUsernameGiàEsistentiException {
	    Utente u = new Utente(
	        dto.getNome(), dto.getCognome(), dto.getUsername(), 
	        dto.getPassword(), dto.getEmail(), dto.getDataNascita(), dto.getRuolo()
	    );
	    try {
	    boolean salvato = utenteDAO.salva(u);
	    if (salvato) {
	        dto.setIdUtente(u.getIdUtente()); 
	        return dto;
	        }
	    }catch(SQLException e){
	    	e.printStackTrace();
	    	throw new EmailUsernameGiàEsistentiException();	    }
	    return null;
	}
	
	public UtenteDTO registraProprietario(UtenteDTO uDto,LottoDTO lDto)throws EmailUsernameGiàEsistentiException {
		Utente u= new Utente( uDto.getNome(), uDto.getCognome(), uDto.getUsername(), 
		        uDto.getPassword(), uDto.getEmail(), uDto.getDataNascita(), uDto.getRuolo()
			    );
		LottoColtivabile lc= new LottoColtivabile( lDto.getTessitura(),lDto.getDimensioni(),lDto.getPh(),
				lDto.getMorfologia(),lDto.getAltitudine(),lDto.getLocalita(),lDto.getComune(),lDto.getProvincia(),u);
		try {
		boolean salvato= utenteDAO.salvaConLotto(u, lc);
		if(salvato) {
			uDto.setIdUtente(u.getIdUtente());
			lDto.setCodLotto(lc.getCodLotto());
			return uDto;
		    }
		}catch(SQLException e) {
			e.printStackTrace();
			throw new EmailUsernameGiàEsistentiException();
		}
		return null;
	}
}
