package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnection;
import exceptions.ErroreDatabaseException;
import exceptions.RisorsaNonTrovataException;
import model.Coltura;

public class ColturaDAO {

	public ArrayList<Coltura> preleva() throws ErroreDatabaseException {
		ArrayList<Coltura> listaColture= new ArrayList<Coltura>();
		String sql= "SELECT * FROM COLTURA ORDER BY CODCOLTURA";
		try(Connection conn= DBConnection.getConnection();
				PreparedStatement ps= conn.prepareStatement(sql)){
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {				
				Coltura c = new Coltura(
						rs.getInt("CODCOLTURA"),
						rs.getString("NOME"),
						rs.getString("SPECIE"),
						rs.getString("FAMIGLIA"),
						rs.getInt("TEMPOMATURAZIONE"),
						rs.getString("DESTINAZIONEUSO"),
						rs.getString("PERIODOIDEALE"));
				
				listaColture.add(c);
			}
		}catch(SQLException e) {
			e.printStackTrace();
			throw new ErroreDatabaseException();
		}
		return listaColture;
	}
	
	public Coltura prelevaColturaDaNome(String nome) throws RisorsaNonTrovataException,ErroreDatabaseException {
	    String sql = "SELECT* FROM COLTURA WHERE NOME = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, nome);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (!rs.next()) {
	            	throw new RisorsaNonTrovataException();
	            }
	            	Coltura c= new Coltura(
	            			rs.getInt("CODCOLTURA"),
	            			rs.getString("NOME"),
	            			rs.getString("SPECIE"),
	            			rs.getString("FAMIGLIA"),
	            			rs.getInt("TEMPOMATURAZIONE"),
	            			rs.getString("DESTINAZIONEUSO"),
	            			rs.getString("PERIODOIDEALE")
	            			);
	            	return c;         
	        }
	    }catch(SQLException e) {
        	e.printStackTrace();
        	throw new ErroreDatabaseException();
        }
	}
}
