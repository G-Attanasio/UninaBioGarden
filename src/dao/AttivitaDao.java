package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnection;
import model.Attivita;
import model.Irrigazione;
import model.ProgettoStagionale;
import model.Raccolta;
import model.Semina;
import model.Stato;
import model.TipoIrrigazione;
import model.TipoRaccolta;
import model.TipoRuolo;
import model.TipoSemina;
import model.Utente;

public class AttivitaDao {

	public ArrayList<Attivita> prelevaTutteAttivitaPerColtivatore(String username )throws SQLException{
		ArrayList<Attivita> lista= new ArrayList<>();  
        try (Connection conn= DBConnection.getConnection())
              {
            	  String sqlS = "SELECT A.*,S.*,U.USERNAME FROM ATTIVITA A JOIN SEMINA S ON A.CODATTIVITA = S.FK_CODATTIVITA "+
           			   "JOIN UTENTE U ON U.IDUTENTE=A.FK_COLTIVATORE "+
           			   "WHERE U.USERNAME =?";
        	try(PreparedStatement psS = conn.prepareStatement(sqlS)){
        		psS.setString(1, username);
        	
           try { 
        		
        	   ResultSet rsS= psS.executeQuery();
        	  
        			   
        	   while (rsS.next()) {
        		   Utente u= new Utente(0,null,null,null,null,null,null,null);
            	   u.setUsername(rsS.getString("USERNAME"));
               Semina s=new Semina(
            		rsS.getInt("FK_CODATTIVITA"),   
            		Stato.valueOf(rsS.getString("STATOESECUZIONE").toString().toUpperCase()),
                    rsS.getDate("DATAINIZIO").toLocalDate(),
                    rsS.getDate("DATAFINE").toLocalDate(),
                    u,
                    null,
                    TipoSemina.valueOf(rsS.getString("METODOSEMINA").toString().toUpperCase())
                );
                lista.add(s);
            }
        }catch(SQLException e) {
        	throw e;
        }
        }catch(SQLException e) {
        	throw e;
        }
	
	
        	

	 String sqlR = "SELECT A.*,R.*,U.USERNAME FROM ATTIVITA A JOIN RACCOLTA R ON A.CODATTIVITA = R.FK_CODATTIVITA "+
 			   "JOIN UTENTE U ON U.IDUTENTE=A.FK_COLTIVATORE "+
 			   "WHERE U.USERNAME =?";
        try (PreparedStatement psR = conn.prepareStatement(sqlR)){
        	psR.setString(1, username);
        	try 
             (ResultSet rsR = psR.executeQuery()) {
            while (rsR.next()) {
            	Utente u= new Utente(0,null,null,null,null,null,null,null);
            	u.setUsername(rsR.getString("USERNAME"));
                Raccolta r= new Raccolta(
                	rsR.getInt("FK_CODATTIVITA"),	
                	Stato.valueOf(rsR.getString("STATOESECUZIONE").toString().toUpperCase()),
                    rsR.getDate("DATAINIZIO").toLocalDate(),
                    rsR.getDate("DATAFINE").toLocalDate(),
                    u,
                    null,
                    TipoRaccolta.valueOf(rsR.getString("METODORACCOLTA").toString().toUpperCase()),
                    rsR.getDouble("quantitaprevista"),
                    0,
                    null
                );
                lista.add(r);
            }
        }catch(SQLException e) {
        	throw e;
        }
        }catch(SQLException e) {
        	throw e;
        }
        
   	 String sqlI = "SELECT A.*,I.*,U.USERNAME FROM ATTIVITA A JOIN IRRIGAZIONE I ON A.CODATTIVITA = I.FK_CODATTIVITA "+
			   "JOIN UTENTE U ON U.IDUTENTE=A.FK_COLTIVATORE "+
			   "WHERE U.USERNAME =?";
      try (PreparedStatement psI = conn.prepareStatement(sqlI)){
      	psI.setString(1, username);
      	try 
           (ResultSet rsI = psI.executeQuery()) {
          while (rsI.next()) {
          	Utente u= new Utente(0,null,null,null,null,null,null,null);
          	u.setUsername(rsI.getString("USERNAME"));
              Irrigazione i= new Irrigazione(
              	rsI.getInt("FK_CODATTIVITA"),	
              	Stato.valueOf(rsI.getString("STATOESECUZIONE").toString().toUpperCase()),
                  rsI.getDate("DATAINIZIO").toLocalDate(),
                  rsI.getDate("DATAFINE").toLocalDate(),
                  u,
                  null,
                  TipoIrrigazione.valueOf(rsI.getString("METODOIRRIGAZIONE").toString().toUpperCase())
                  
              );
              lista.add(i);
          }
      }catch(SQLException e) {
      	throw e;
      	}
      }catch(SQLException e) {
      	throw e;
       }
	}catch(SQLException e) {
		throw e;
		}
        return lista;
}
}


