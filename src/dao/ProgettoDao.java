package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnection;
import model.Attivita;
import model.LottoColtivabile;
import model.ProgettoStagionale;
import model.SeminaColtura;
import model.Stagione;
import model.Stato;

public class ProgettoDao {

	public ArrayList<ProgettoStagionale> prelevaProgettiPerLotto(int idLotto) throws SQLException {
	    ArrayList<ProgettoStagionale> lista = new ArrayList<>();
	    String sql = "SELECT * FROM PROGETTO WHERE id_lotto = ?";
	    
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, idLotto);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	            	LottoColtivabile lc;
	                lista.add(new ProgettoStagionale(
	                    rs.getInt("CODPROGETTO"),
	                    rs.getString("NOME"),
	                    Stagione.valueOf(rs.getString("STAGIONEDIRIFERIMENTO").toString().toUpperCase()),
	                    rs.getInt("DURATA"),
	                    rs.getDate("DATAINIZIO").toLocalDate(),
	                    null,
	                    Stato.valueOf(rs.getString("STATOESECUZIONE").toString().toUpperCase()),
	                    lc= new LottoColtivabile(rs.getInt("FK_CODLOTTO"),null,0,0,null,0,null,null,null,null,true),
	                    null                  
	                ));
	            }
	        }catch(SQLException e) {
	        	throw e;
	        }
	    }catch(SQLException e) {
	    	throw e;
	    }
	    return lista;
	}
	
	public boolean salvaProgettoCompleto(ProgettoStagionale progetto, ArrayList<Attivita> listaAttivita, ArrayList<SeminaColtura> semine) throws SQLException {
		Connection conn = DBConnection.getConnection();
		try {
			conn.setAutoCommit(false); 
			
			String sqlP = "{CALL P_REGISTRA_PROGETTO_STAGIONALE(?,?::STAGIONE,?,?,?::STATO,?,?)}";
			int codProgettoGenerato = -1;
			 try (CallableStatement cs = conn.prepareCall(sqlP)) {
		            cs.setString(1, progetto.getNomeProgetto());
		            cs.setString(2, progetto.getStagioneDiRiferimento().toString().toUpperCase());
		            cs.setInt(3, progetto.getDurata());
		            cs.setObject(4, progetto.getDataInizio());
		            cs.setString(5, progetto.getStatoEsecuzione().toString().toUpperCase());
		            cs.setInt(6, progetto.getLottoImpegnato().getCodLotto());
		            cs.setInt(7, progetto.getCreatore().getIdUtente()); 		            
		            cs.registerOutParameter(8, java.sql.Types.INTEGER);	            
		            cs.execute();
		            codProgettoGenerato = cs.getInt(8); 
		        }
			 AttivitaDao attivitaDao = new AttivitaDao();
		        for (Attivita a : listaAttivita) {
		            attivitaDao.salvaAttivita(a, codProgettoGenerato, conn,semine);
		}
		        conn.commit();
		        return true;
	}catch (SQLException e) {
        if (conn != null) {
            conn.rollback();
        	}
        throw e;
		}finally {
			if (conn != null) {
	            conn.setAutoCommit(true);
	            conn.close();
			}
		}
	}
}