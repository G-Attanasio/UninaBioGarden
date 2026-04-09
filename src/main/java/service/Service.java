package service;

import java.sql.SQLException;

import dao.AttivitaDAO;
import dao.ColturaDAO;
import dao.LottoDAO;
import dao.NotificaDAO;
import dao.ProgettoDAO;
import dao.ReportDAO;
import dao.UtenteDAO;
import dto.UtenteDTO;
import exceptions.UtenteNonTrovatoException;
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
	}
	
	public UtenteDTO effettuaLogin(String username, String password) throws UtenteNonTrovatoException, SQLException {
        Utente u = utenteDAO.preleva(username, password);   
        if(u != null) {
        	setUtenteLoggato(u);
        }
        else {
        	throw new UtenteNonTrovatoException();
        }
        return new UtenteDTO(u.getIdUtente(), u.getNome(), u.getCognome(),u.getUsername(),u.getPassword(),u.getEmail(),u.getDataNascita(),u.getRuolo());
    }

	public static Utente getUtenteLoggato() {
		return utenteLoggato;
	}

	public static void setUtenteLoggato(Utente utenteLoggato) {
		Service.utenteLoggato = utenteLoggato;
	}
}
