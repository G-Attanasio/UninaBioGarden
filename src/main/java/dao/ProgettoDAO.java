package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.jfree.data.category.DefaultCategoryDataset;

import database.DBConnection;
import exceptions.ErroreDatabaseException;
import exceptions.RisorsaNonTrovataException;
import model.Attivita;
import model.LottoColtivabile;
import model.ProgettoStagionale;
import model.SeminaColtura;
import model.Stagione;
import model.Stato;

public class ProgettoDAO {

	public ArrayList<ProgettoStagionale> prelevaProgettiPerLotto(int codLotto) throws ErroreDatabaseException {
	    ArrayList<ProgettoStagionale> lista = new ArrayList<>();
	    String sql = "SELECT * FROM PROGETTOSTAGIONALE WHERE FK_CODLOTTO = ?";
	    
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, codLotto);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	            	LottoColtivabile lc;
	                lista.add(new ProgettoStagionale(
	                    rs.getInt("CODPROGETTO"),
	                    rs.getString("NOMEPROGETTO"),
	                    Stagione.valueOf(rs.getString("STAGIONEDIRIFERIMENTO").toString().toUpperCase()),
	                    rs.getInt("DURATA"),
	                    rs.getDate("DATAINIZIO").toLocalDate(),
	                    null,
	                    Stato.valueOf(rs.getString("STATOESECUZIONE").toString().toUpperCase()),
	                    lc= new LottoColtivabile(rs.getInt("FK_CODLOTTO"),null,0,0,null,0,null,null,null,null,true),
	                    null                  
	                ));
	            }
	        }
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    	throw new ErroreDatabaseException();
	    }
	    return lista;
	}
	
	public void salvaProgettoCompleto(ProgettoStagionale progetto, ArrayList<Attivita> listaAttivita, ArrayList<SeminaColtura> semine) throws ErroreDatabaseException {
		Connection conn = DBConnection.getConnection();
		try {
			conn.setAutoCommit(false); 
			
			String sqlP = "CALL P_REGISTRA_PROGETTO_STAGIONALE(?,?::STAGIONE,?,?,?::STATO,?,?,?)";
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
			 AttivitaDAO attivitaDao = new AttivitaDAO();
		        for (Attivita a : listaAttivita) {
		            attivitaDao.salvaAttivita(a, codProgettoGenerato, conn,semine);
		}
		        conn.commit();
	}catch (SQLException e) {
		try {
        if (conn != null) {
            conn.rollback();
        	}
        	}catch(SQLException ex) {
        		throw new ErroreDatabaseException();
        	}
        e.printStackTrace();
        throw new ErroreDatabaseException();
		}finally {
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
	
	public ArrayList<ProgettoStagionale> prelevaProgettiPerProprietario(int idProprietario) throws ErroreDatabaseException {
	    ArrayList<ProgettoStagionale> lista = new ArrayList<>();
	    String sql = "SELECT * " +
	                 "FROM PROGETTOSTAGIONALE  " +                
	                 "WHERE FK_CREATORE = ? "+
	                 "ORDER BY DATAINIZIO";
	    
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, idProprietario);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            LottoColtivabile lc = new LottoColtivabile(0,null,0,0,null,0,null,null,null,null,true);
	            lc.setCodLotto(rs.getInt("FK_CODLOTTO"));            
	            java.sql.Date data = rs.getDate("DATAFINE");
	            LocalDate dataFine=null;	            
	            if (data != null) {
	                dataFine = data.toLocalDate();
	            }	            
	            ProgettoStagionale p = new ProgettoStagionale(
	                rs.getInt("CODPROGETTO"),
	                rs.getString("NOMEPROGETTO"),
	                Stagione.valueOf(rs.getString("STAGIONEDIRIFERIMENTO").toUpperCase()),
	                rs.getInt("DURATA"),
	                rs.getDate("DATAINIZIO").toLocalDate(),
	                dataFine,
	                Stato.valueOf(rs.getString("STATOESECUZIONE").toUpperCase()),
	                lc,
	                null 
	            );
	            lista.add(p);
	        }
	    }
	    catch(SQLException e) {
	    	e.printStackTrace();
	    	throw new ErroreDatabaseException();
	    }
	    return lista;
	}
	
	public void eliminaProgetto(int codProgetto) throws ErroreDatabaseException{
	    String sql = "DELETE FROM PROGETTOSTAGIONALE WHERE CODPROGETTO = ?";	    
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {        
	        ps.setInt(1, codProgetto);
	        ps.executeUpdate();
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    	throw new ErroreDatabaseException();
	    }
	}
	
	public void sincronizzaSistema() {
	    try (Connection conn = DBConnection.getConnection();
	         CallableStatement cs = conn.prepareCall("CALL P_SINCRONIZZA_TUTTO_AVVIO()")) {
	        cs.execute();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}	