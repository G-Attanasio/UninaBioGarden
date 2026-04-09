package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.DBConnection;
import exceptions.RisorsaNonTrovataException;
import model.LottoColtivabile;
import model.TipoMorfologia;
import model.TipoTessitura;
import model.Utente;

public class LottoDAO {

	public boolean salva(LottoColtivabile lc) throws SQLException {
		String sql="INSERT INTO LOTTOCOLTIVABILE (TESSITURA,DIMENSIONI,PH,MORFOLOGIA,ALTITUDINE,LOCALITA,COMUNE,PROVINCIA,FK_IDPROPRIETARIO) VALUES (?::tipotessitura,?,?,?::tipomorfologia,?,?,?,?,?)";
		try (Connection conn= DBConnection.getConnection();
			PreparedStatement ps= conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {	
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
		throw e;
	}
	return false;
	}
	
	public LottoColtivabile preleva (int codLotto) throws SQLException,RisorsaNonTrovataException {
		String sql="SELECT * FROM LOTTOCOLTIVABILE WHERE CODLOTTO=?";
		try (Connection conn = DBConnection.getConnection();
		         PreparedStatement ps = conn.prepareStatement(sql)) {			
		        	 ps.setInt(1, codLotto);		        	 
		        	 ResultSet rs= ps.executeQuery();	        	 
		        	 if(rs.next()) {		        		 
		        		 Utente proprietario = new Utente(null,null,null,null,null,null,null);
		        		 proprietario.setIdUtente(rs.getInt("FK_IDPROPRIETARIO"));	        		 
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
		}
		return null;
	}
	
	public void salvaInTransazione(LottoColtivabile lc, Connection conn) throws SQLException {
	    
	    String sql = "INSERT INTO LOTTOCOLTIVABILE (TESSITURA, DIMENSIONI, PH, MORFOLOGIA, ALTITUDINE, LOCALITA, COMUNE, PROVINCIA, FK_IDPROPRIETARIO, ATTIVO) "
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
	
	public ArrayList<LottoColtivabile> prelevaLottiPerProprietario(int idUtente) throws SQLException,RisorsaNonTrovataException {
	    ArrayList<LottoColtivabile> lista = new ArrayList<>();
	   
	    String sql = "SELECT * FROM LOTTOCOLTIVABILE WHERE FK_IDPROPRIETARIO = ? AND ATTIVO = TRUE";
	    
	    Connection conn = DBConnection.getConnection();
	    PreparedStatement ps = conn.prepareStatement(sql);         
	    ps.setInt(1, idUtente);
	    ResultSet rs = ps.executeQuery();       
	        while (rs.next()) {
	            LottoColtivabile lc = new LottoColtivabile(
	                rs.getInt("CODLOTTO"),
	                TipoTessitura.valueOf(rs.getString("TESSITURA").toUpperCase()),
	                rs.getInt("DIMENSIONI"),
	                rs.getDouble("PH"),
	                TipoMorfologia.valueOf(rs.getString("MORFOLOGIA").toUpperCase()),
	                rs.getInt("ALTITUDINE"),
	                rs.getString("LOCALITA"),
	                rs.getString("COMUNE"),
	                rs.getString("PROVINCIA"),
	                null, 
	                true
	            );
	            lista.add(lc);
	        }
	        return lista;
	    }
	    
	public boolean cancellaLotto(int codLotto) throws SQLException {
		String sql = "CALL P_DISATTIVA_LOTTO(?)";		
		 try (Connection conn = DBConnection.getConnection();
		 CallableStatement cs= conn.prepareCall(sql)){		
		 cs.setInt(1, codLotto);
	     cs.execute();
	     return true;	        		
	}
	}
}
	
