package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;
import model.LottoColtivabile;
import model.TipoMorfologia;
import model.TipoTessitura;
import model.Utente;

public class LottoDao {

	public boolean salva(LottoColtivabile lc) {
		String sql="INSERT INTO LOTTOCOLTIVABILE (TESSITURA,DIMENSIONI,PH,MORFOLOGIA,ALTITUDINE,LOCALITA,COMUNE,PROVINCIA) VALUES (?::tipotessitura,?,?,?::tipomorfologia,?,?,?,?)";
		try{
			Connection conn= DBConnection.getConnection();
			
			PreparedStatement ps= conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, lc.getTessitura().toString());
			ps.setInt(2, lc.getDimensioni());
			ps.setDouble(3, lc.getPh());
			ps.setString(4, lc.getMorfologia().toString());
			ps.setInt(5, lc.getAltitudine());
			ps.setString(6, lc.getLocalita());
			ps.setString(7, lc.getComune());
			ps.setString(8, lc.getProvincia());
			ps.setInt(9, lc.getProprietario().getIdUtente());
			
			int righe= ps.executeUpdate();
			
			if (righe > 0) {
				try (ResultSet rs = ps.getGeneratedKeys()) {
					if (rs.next()) {
						lc.setCodLotto(rs.getInt(1)); 
					}
				}
				return true;
			}
			
	
	}catch(SQLException e) {
		e.printStackTrace();
	}
	return false;
	}
	
	public LottoColtivabile preleva (int codLotto) {
		String sql="SELECT * FROM LOTTOCOLTIVABILE WHERE CODLOTTO=?";
		try (Connection conn = DBConnection.getConnection();
		         PreparedStatement ps = conn.prepareStatement(sql)) {
			
		        	 ps.setInt(1, codLotto);
		        	 
		        	 ResultSet rs= ps.executeQuery();
		        	 
		        	 if(rs.next()) {
		        		 
		        		 Utente proprietario = new Utente(null,null,null,null,null,null,null);
		        		 proprietario.setIdUtente(rs.getInt("FK_PROPRIETARIO"));
		        		 
		        		 LottoColtivabile lotto= new LottoColtivabile(
		        		 rs.getInt("CODLOTTO"),
		        		 TipoTessitura.valueOf(rs.getString("TESSITURA").toUpperCase()),
		        		 rs.getInt("DIMENSIONI"),
		        		 rs.getDouble("PH"),
		        		 TipoMorfologia.valueOf(rs.getString("MORFOLOGIA").toUpperCase()),
		        		 rs.getInt("ALTITUDINE"),
		        		 rs.getString("LOCALITA"),
		        		 rs.getString("COMUNE"),
		        		 rs.getString("PROVINCIA"),
		        		 proprietario,
		        		 rs.getBoolean("ATTIVO")
		        		 );
		        		 
		        		 return lotto;
		        	 }
		        	 
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void salvaInTransazione(LottoColtivabile lc, Connection conn) throws SQLException {
	    
	    String sql = "INSERT INTO LOTTOCOLTIVABILE (TESSITURA, DIMENSIONI, PH, MORFOLOGIA, ALTITUDINE, LOCALITA, COMUNE, PROVINCIA, FK_PROPRIETARIO, ATTIVO) "
	                    + "VALUES (?::tipotessitura, ?, ?, ?::tipomorfologia, ?, ?, ?, ?, ?, ?)";

	    try (PreparedStatement psL = conn.prepareStatement(sql)) {
	        psL.setString(1, lc.getTessitura().toString());
	        psL.setDouble(2, lc.getDimensioni());
	        psL.setDouble(3, lc.getPh());
	        psL.setString(4, lc.getMorfologia().toString());
	        psL.setInt(5, lc.getAltitudine());
	        psL.setString(6, lc.getLocalita());
	        psL.setString(7, lc.getComune());
	        psL.setString(8, lc.getProvincia());
	        psL.setInt(9, lc.getProprietario().getIdUtente()); 
	        psL.setBoolean(10, true);

	        psL.executeUpdate();
	    }
	    
	}
}
	
