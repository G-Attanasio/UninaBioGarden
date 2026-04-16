package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import database.DBConnection;
import exceptions.EmailUsernameGiàEsistentiException;
import exceptions.ErroreDatabaseException;
import exceptions.UtenteNonTrovatoException;
import model.Attivita;
import model.LottoColtivabile;
import model.TipoRuolo;
import model.Utente;

public class UtenteDAO {

	public void salva(Utente u) throws EmailUsernameGiàEsistentiException, ErroreDatabaseException {
		String sql= "INSERT INTO UTENTE (NOME,COGNOME,USERNAME,PASSWORD,EMAIL,DATANASCITA,RUOLO) VALUES(?,?,?,?,?,?,?::TipoRuolo) ";
		
		try (Connection conn = DBConnection.getConnection();
				 PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {				
				ps.setString(1, u.getNome());
				ps.setString(2, u.getCognome());
				ps.setString(3, u.getUsername());
				ps.setString(4, u.getPassword());
				ps.setString(5, u.getEmail());
				ps.setObject(6, u.getDataNascita());
				ps.setString(7, u.getRuolo().toString());				
				int righe = ps.executeUpdate();				
				if (righe > 0) {
					try (ResultSet rs = ps.getGeneratedKeys()) {
						if (rs.next()) {
							u.setIdUtente(rs.getInt(1)); 
						}
					}
				}
				
			}catch (SQLException e) {
			    if (e.getSQLState().equals("23505")) {
			        throw new EmailUsernameGiàEsistentiException();
			    }
			    e.printStackTrace();
			    throw new ErroreDatabaseException();
			}
		}
			

	
	public Utente prelevaPerLogin(String username, String password) throws SQLException,UtenteNonTrovatoException {
	    String sql = "SELECT * FROM UTENTE WHERE USERNAME = ? AND PASSWORD = ?";    
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {        
	        ps.setString(1, username);
	        ps.setString(2, password);	        
	        ResultSet rs = ps.executeQuery(); 
	        if (rs.next()) {	            
	            return new Utente(
	                rs.getInt("IDUTENTE"),
	                rs.getString("NOME"),
	                rs.getString("COGNOME"),
	                rs.getString("USERNAME"),
	                rs.getString("PASSWORD"), 
	                rs.getString("EMAIL"),
	                rs.getObject("DATANASCITA", LocalDate.class),
	                TipoRuolo.valueOf(rs.getString("RUOLO").toUpperCase())
	            );
	        }
	    } 
	    return null; 
	}
	
	public Utente prelevaPerId(int id) throws SQLException,UtenteNonTrovatoException {
	    String sql = "SELECT * FROM UTENTE WHERE IDUTENTE=?";    
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {        
	        ps.setInt(1, id);        
	        ResultSet rs = ps.executeQuery(); 
	        if (rs.next()) {	            
	            return new Utente(
	                rs.getInt("IDUTENTE"),
	                rs.getString("NOME"),
	                rs.getString("COGNOME"),
	                rs.getString("USERNAME"),
	                rs.getString("PASSWORD"), 
	                rs.getString("EMAIL"),
	                rs.getDate("DATANASCITA").toLocalDate(),
	                TipoRuolo.valueOf(rs.getString("RUOLO").toUpperCase())
	            );
	        }else {
	        	throw new UtenteNonTrovatoException();
	        }
	    } 
	     
	}
	
	public boolean salvaConLotto(Utente u, LottoColtivabile lc) throws SQLException {
		 Connection conn = null;
		    try {
		        conn = DBConnection.getConnection();
		        conn.setAutoCommit(false);        
		        String sql = "INSERT INTO UTENTE (NOME, COGNOME, USERNAME, PASSWORD, EMAIL, DATANASCITA, RUOLO) VALUES (?,?,?,?,?,?,?::TipoRuolo)";
		        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
		            ps.setString(1, u.getNome());
		            ps.setString(2, u.getCognome());
		            ps.setString(3, u.getUsername());
		            ps.setString(4, u.getPassword());
		            ps.setString(5, u.getEmail());
		            ps.setObject(6, u.getDataNascita());
		            ps.setString(7, u.getRuolo().toString());
		            
		            ps.executeUpdate();
		            try (ResultSet rs = ps.getGeneratedKeys()) {
		                if (rs.next()) {
		                    u.setIdUtente(rs.getInt(1)); 
		                }
		            }
		        }	       
		        LottoDAO lottoDao = new LottoDAO();
		        lc.getProprietario().setIdUtente(u.getIdUtente()); 
		        lottoDao.salvaInTransazione(lc, conn);
		        conn.commit();
		        return true;
		    } catch (SQLException e) {	        
		            if (conn != null) conn.rollback(); 
		            throw e;	        
		    } finally {   
		            if (conn != null) {
		                conn.setAutoCommit(true);
		                conn.close();
		       }
		 }   
	}
	
	public ArrayList<String> prelevaPerProgetto()throws SQLException{
		ArrayList<String> usernames= new ArrayList<>();
		String sql= "SELECT USERNAME FROM UTENTE WHERE RUOLO::text IN ('PROPRIETARIO_COLTIVATORE','COLTIVATORE')";
		try(Connection conn= DBConnection.getConnection();
				PreparedStatement ps= conn.prepareStatement(sql)) {
			ResultSet rs= ps.executeQuery();		
			while(rs.next()) {
				usernames.add(rs.getString("USERNAME"));
			}
		}
		return usernames;
	}
	
	public Utente prelevaDaUsername(String username) throws SQLException,UtenteNonTrovatoException {
	    String sql = "SELECT * FROM UTENTE WHERE USERNAME = ?";
	    
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {	        
	        ps.setString(1, username);        
	        ResultSet rs = ps.executeQuery(); 	        
	        if (rs.next()) {	            
	            return new Utente(
	                rs.getInt("IDUTENTE"),
	                rs.getString("NOME"),
	                rs.getString("COGNOME"),
	                rs.getString("USERNAME"),
	                rs.getString("PASSWORD"), 
	                rs.getString("EMAIL"),
	                rs.getObject("DATANASCITA", LocalDate.class),
	                TipoRuolo.valueOf(rs.getString("RUOLO").toUpperCase())
	            );
	        }
	    } 
	    return null; 
	}
}
