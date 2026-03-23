package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import database.DBConnection;
import model.TipoRuolo;
import model.Utente;

public class UtenteDao {

	public boolean salva(Utente u) throws SQLException {
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
					return true;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
			

	
	public Utente preleva(String username, String password) throws SQLException {
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
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null; 
	}

	
}
