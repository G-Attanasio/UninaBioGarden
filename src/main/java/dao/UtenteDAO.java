package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
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
			    if ("23505".equals(e.getSQLState())) {
			        throw new EmailUsernameGiàEsistentiException();
			    }
			    e.printStackTrace();
			    throw new ErroreDatabaseException();
			}
		}
			

	
	public Utente prelevaPerLogin(String username, String password) throws UtenteNonTrovatoException, ErroreDatabaseException {
	    String sql = "SELECT * FROM UTENTE WHERE USERNAME = ? AND PASSWORD = ?";    
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {        
	        ps.setString(1, username);
	        ps.setString(2, password);	        
	        ResultSet rs = ps.executeQuery(); 
	        if (!rs.next()) {
	        	throw new UtenteNonTrovatoException();
	        }
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
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    	throw new ErroreDatabaseException();
	    }
	}
	
	public Utente prelevaPerId(int id) throws UtenteNonTrovatoException, ErroreDatabaseException {
	    String sql = "SELECT * FROM UTENTE WHERE IDUTENTE=?";    
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {        
	        ps.setInt(1, id);        
	        ResultSet rs = ps.executeQuery(); 
	        if (!rs.next()) {
	        	throw new UtenteNonTrovatoException();
	        }
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
	        
	    }catch (SQLException e) {
			e.printStackTrace();
			throw new ErroreDatabaseException();
		} 
	     
	}
	
	public void salvaConLotto(Utente u, LottoColtivabile lc) throws  ErroreDatabaseException, EmailUsernameGiàEsistentiException{
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
		        
		    } catch (SQLException e) {	 
		    	if (conn != null) {
					try {
					conn.rollback();
					}catch(SQLException ex) {
						throw new ErroreDatabaseException();
					}
				}
				e.printStackTrace();
				if("23505".equals(e.getSQLState())) {
					throw new EmailUsernameGiàEsistentiException();
				}
				throw new ErroreDatabaseException();
		    } finally {   
		    	try {
		            if (conn != null) {
		                conn.setAutoCommit(true);
		                conn.close();
		            }
		       }catch(SQLException exc) {	
		    	   throw new ErroreDatabaseException();
		       }
		 }   
	}
	
	public ArrayList<String> prelevaPerProgetto()throws ErroreDatabaseException{
		ArrayList<String> usernames= new ArrayList<>();
		String sql= "SELECT USERNAME FROM UTENTE WHERE RUOLO::text IN ('PROPRIETARIO_COLTIVATORE','COLTIVATORE')";
		try(Connection conn= DBConnection.getConnection();
				PreparedStatement ps= conn.prepareStatement(sql)) {
			ResultSet rs= ps.executeQuery();		
			while(rs.next()) {
				usernames.add(rs.getString("USERNAME"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
			throw new ErroreDatabaseException();
		}
		return usernames;
	}
	
	public Utente prelevaDaUsername(String username) throws UtenteNonTrovatoException, ErroreDatabaseException {
	    String sql = "SELECT * FROM UTENTE WHERE USERNAME = ?";
	    
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {	        
	        ps.setString(1, username);        
	        ResultSet rs = ps.executeQuery(); 	        
	        if (!rs.next()) {
	        	throw new UtenteNonTrovatoException();
	        }
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
	        
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    	throw new ErroreDatabaseException();
	    }	    
	}
}
