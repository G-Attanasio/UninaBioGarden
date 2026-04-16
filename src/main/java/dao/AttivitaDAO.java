package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnection;
import exceptions.RisorsaNonTrovataException;
import model.Attivita;
import model.Coltura;
import model.Irrigazione;
import model.ProgettoStagionale;
import model.Raccolta;
import model.Semina;
import model.SeminaColtura;
import model.Stato;
import model.TipoIrrigazione;
import model.TipoRaccolta;
import model.TipoSemina;
import model.Utente;

public class AttivitaDAO {

	public ArrayList<Attivita> prelevaTutteAttivitaPerColtivatore(String username )throws SQLException,RisorsaNonTrovataException{
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
                    rsR.getDouble("QUANTITAPREVISTA"),
                    0,
                    null
                );
                lista.add(r);
            }
        }
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
      }
      }
	}
        return lista;
}
	
	public boolean salvaAttivita(Attivita attivita,int codProgetto,Connection conn,ArrayList<SeminaColtura> semine)throws SQLException {
		 if (attivita instanceof Semina) {
		        salvaSemina((Semina) attivita, codProgetto, conn,semine);
		    } else if (attivita instanceof Raccolta) {
		        salvaRaccolta((Raccolta) attivita, codProgetto, conn,semine);
		    } else if (attivita instanceof Irrigazione) {
		        salvaIrrigazione((Irrigazione) attivita, codProgetto, conn);
		    }
		    return false;
	}
	
	public void salvaSemina(Semina semina, int codProgetto, Connection conn, ArrayList<SeminaColtura> semineColture) throws SQLException {	
		double quantitaSemi=0;
	    int codColtura=-1;
	    for (SeminaColtura sc : semineColture) {  
	        if (sc.getSemina() == semina) { 
	            quantitaSemi = sc.getQuantitaSemi();
	            codColtura = sc.getColtura().getCodColtura();
	            break; 
	        }
	    }	
	    String sql = "CALL P_REGISTRA_ATTIVITA_SEMINA(?, ?, ?::TIPOSEMINA, ?::DECIMAL, ?, ?, ?)";

	    try (CallableStatement cs = conn.prepareCall(sql)) {
	        
	        cs.setObject(1,semina.getDataInizio());               
	        cs.setObject(2,semina.getDataFine());                
	        cs.setString(3,semina.getMetodoSemina().toString().toUpperCase());       
	        cs.setDouble(4,quantitaSemi);        
	        cs.setInt(5,codColtura);                 
	        cs.setInt(6,codProgetto);    	       
	        cs.setInt(7,semina.getColtivatore().getIdUtente());
	        cs.execute();
	    }
	}
    public void salvaIrrigazione(Irrigazione i, int codProgetto, Connection conn) throws SQLException {  	    
    	    String sql = "CALL P_REGISTRA_ATTIVITA_IRRIGAZIONE(?, ?, ?::TIPOIRRIGAZIONE, ?, ?)";

    	    try (CallableStatement cs = conn.prepareCall(sql)) {
    	        cs.setObject(1, i.getDataInizio());
    	        cs.setObject(2, i.getDataFine());   	         	       
    	        cs.setString(3, i.getMetodoIrrigazione().toString().toUpperCase());   	        
    	        cs.setInt(4, codProgetto);
    	        cs.setInt(5, i.getColtivatore().getIdUtente());
    	        cs.execute();
    	    }
    	}
    public void salvaRaccolta(Raccolta r, int codProgetto, Connection conn,ArrayList<SeminaColtura> colture)throws SQLException { 	
    	 int codColtura=-1;
    	 for (SeminaColtura sc : colture) {  
 	        if (sc.getColtura().equals(r.getColtura())){ 
 	            codColtura = sc.getColtura().getCodColtura();
 	            break; 
 	        }
 	    }	
    	 
    	 String sql = "CALL P_REGISTRA_ATTIVITA_RACCOLTA(?, ?, ?::TIPORACCOLTA, ?::DECIMAL, ?, ?, ?)";

    	    try (CallableStatement cs = conn.prepareCall(sql)) {
    	        cs.setObject(1,r.getDataInizio());
    	        cs.setObject(2,r.getDataFine());
    	        cs.setString(3, r.getMetodoRaccolta().toString().toUpperCase());   	        
    	        cs.setDouble(4, r.getQuantitaPrevista());    	        
    	        cs.setInt(5, r.getColtivatore().getIdUtente());
    	        cs.setInt(6, codProgetto);
    	        cs.setInt(7, codColtura);
    	        cs.execute();
    	    }
	}
    
    public ArrayList<Attivita> prelevaAttivitaAssegnateDaProprietario(int idProprietario) throws SQLException {
        ArrayList<Attivita> lista = new ArrayList<>();
        String sql = "SELECT A.*, U.USERNAME, PS.NOMEPROGETTO, S.METODOSEMINA, R.METODORACCOLTA, I.METODOIRRIGAZIONE " +
                "FROM ATTIVITA A " +
                "JOIN PROGETTOSTAGIONALE PS ON A.FK_CODPROGETTO = PS.CODPROGETTO " +
                "JOIN UTENTE U ON A.FK_COLTIVATORE = U.IDUTENTE " +
                "LEFT JOIN SEMINA S ON A.CODATTIVITA = S.FK_CODATTIVITA " +
                "LEFT JOIN RACCOLTA R ON A.CODATTIVITA = R.FK_CODATTIVITA " +
                "LEFT JOIN IRRIGAZIONE I ON A.CODATTIVITA = I.FK_CODATTIVITA " +
                "WHERE PS.FK_CREATORE = ? "+
                "ORDER BY A.DATAINIZIO";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idProprietario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	 Utente u = new Utente(0,null,null,null,null,null,null,null);
                 u.setUsername(rs.getString("USERNAME"));
                 ProgettoStagionale prog= new ProgettoStagionale(0,null,null,0,null,null,null,null,null);
                 prog.setNomeProgetto(rs.getString("NOMEPROGETTO"));
                 if (rs.getString("METODOSEMINA") != null) {
                     lista.add(new Semina(
                    	 rs.getInt("CODATTIVITA"),
                    	 Stato.valueOf(rs.getString("STATOESECUZIONE").toString().toUpperCase()),
                         rs.getDate("DATAINIZIO").toLocalDate(),
                         rs.getDate("DATAFINE").toLocalDate(),
                         u,
                         prog,
                         TipoSemina.valueOf(rs.getString("METODOSEMINA").toUpperCase())
                     ));
                 } else if (rs.getString("METODORACCOLTA") != null) {
                     lista.add(new Raccolta(
                    	 rs.getInt("CODATTIVITA"),	 
                    	 Stato.valueOf(rs.getString("STATOESECUZIONE").toString().toUpperCase()),
                         rs.getDate("DATAINIZIO").toLocalDate(),
                         rs.getDate("DATAFINE").toLocalDate(),
                         u,
                         prog,
                         TipoRaccolta.valueOf(rs.getString("METODORACCOLTA").toUpperCase()),
                         0,
                         0,
                         null
                     ));
                 } else if (rs.getString("METODOIRRIGAZIONE") != null) {
                     lista.add(new Irrigazione(
                    	 rs.getInt("CODATTIVITA"),
                    	 Stato.valueOf(rs.getString("STATOESECUZIONE").toString().toUpperCase()),
                         rs.getDate("DATAINIZIO").toLocalDate(),
                         rs.getDate("DATAFINE").toLocalDate(),
                         u,
                         prog,
                         TipoIrrigazione.valueOf(rs.getString("METODOIRRIGAZIONE").toUpperCase())
                     ));
                 }         
        }
        return lista;
    }
}
        
        public ArrayList<Attivita> prelevaAttivitaColtivatore(int idColtivatore) throws SQLException{
            ArrayList<Attivita> lista = new ArrayList<>();
            String sql = "SELECT A.*, PS.NOMEPROGETTO, U.USERNAME, S.METODOSEMINA, R.METODORACCOLTA, I.METODOIRRIGAZIONE, " +
                    "COALESCE(C_R.NOME, C_S.NOME) AS COLT " + 
                    "FROM ATTIVITA A " +
                    "JOIN PROGETTOSTAGIONALE PS ON A.FK_CODPROGETTO = PS.CODPROGETTO " +
                    "JOIN UTENTE U ON A.FK_COLTIVATORE = U.IDUTENTE " +
                    "LEFT JOIN SEMINA S ON A.CODATTIVITA = S.FK_CODATTIVITA " +
                    "LEFT JOIN SEMINACOLTURA SC ON S.FK_CODATTIVITA = SC.FK_CODATTIVITA " + 
                    "LEFT JOIN RACCOLTA R ON A.CODATTIVITA = R.FK_CODATTIVITA " +
                    "LEFT JOIN IRRIGAZIONE I ON A.CODATTIVITA = I.FK_CODATTIVITA " +
                    "LEFT JOIN COLTURA C_R ON R.FK_CODCOLTURA = C_R.CODCOLTURA " + 
                    "LEFT JOIN COLTURA C_S ON SC.FK_CODCOLTURA = C_S.CODCOLTURA " + 
                    "WHERE A.FK_COLTIVATORE = ? " +
                    "ORDER BY A.DATAINIZIO";
            
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idColtivatore);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) { 
                	 Coltura c= new Coltura(0,null,null,null,0,null,null);
                	 c.setNome(rs.getString("COLT"));         
                     ProgettoStagionale prog= new ProgettoStagionale(0,null,null,0,null,null,null,null,null);
                     prog.setNomeProgetto(rs.getString("NOMEPROGETTO"));
                     if (rs.getString("METODOSEMINA") != null) {
                         lista.add(new Semina(
                        	 rs.getInt("CODATTIVITA"),
                        	 Stato.valueOf(rs.getString("STATOESECUZIONE").toString().toUpperCase()),
                             rs.getDate("DATAINIZIO").toLocalDate(),
                             rs.getDate("DATAFINE").toLocalDate(),
                             null,
                             prog,
                             TipoSemina.valueOf(rs.getString("METODOSEMINA").toUpperCase())
                         ));
                     } else if (rs.getString("METODORACCOLTA") != null) {
                         lista.add(new Raccolta(
                        	 rs.getInt("CODATTIVITA"),	 
                        	 Stato.valueOf(rs.getString("STATOESECUZIONE").toString().toUpperCase()),
                             rs.getDate("DATAINIZIO").toLocalDate(),
                             rs.getDate("DATAFINE").toLocalDate(),
                             null,
                             prog,
                             TipoRaccolta.valueOf(rs.getString("METODORACCOLTA").toUpperCase()),
                             0,
                             0,
                             c
                         ));
                     } else if (rs.getString("METODOIRRIGAZIONE") != null) {
                         lista.add(new Irrigazione(
                        	 rs.getInt("CODATTIVITA"),
                        	 Stato.valueOf(rs.getString("STATOESECUZIONE").toString().toUpperCase()),
                             rs.getDate("DATAINIZIO").toLocalDate(),
                             rs.getDate("DATAFINE").toLocalDate(),
                             null,
                             prog,
                             TipoIrrigazione.valueOf(rs.getString("METODOIRRIGAZIONE").toUpperCase())
                         ));
                     }
               
            }
            return lista;
        }
    }
    
            
      public ArrayList<SeminaColtura> prelevaDettagliColturePerColtivatore (int idColtivatore) throws SQLException {
                ArrayList<SeminaColtura> lista = new ArrayList<>();
                String sql = "SELECT SC.FK_CODATTIVITA, C.NOME " +
                             "FROM SEMINACOLTURA SC " +
                             "JOIN COLTURA C ON SC.FK_CODCOLTURA = C.CODCOLTURA " +
                             "JOIN ATTIVITA A ON SC.FK_CODATTIVITA = A.CODATTIVITA " +                            
                             "WHERE A.FK_COLTIVATORE = ?";              
                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, idColtivatore);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        Coltura c = new Coltura(0, rs.getString("NOME"), null, null, 0, null, null);                     
                        Semina s = new Semina(rs.getInt("FK_CODATTIVITA"), null, null, null, null, null, null);
                        lista.add(new SeminaColtura(c, s, 0));
                    }
                }
                return lista;
            }
            
      public boolean aggiornaQuantitaReale(int codAttivita, double kg) throws SQLException{
    	    String sql = "UPDATE RACCOLTA SET QUANTITAREALE = ? WHERE FK_CODATTIVITA = ?";   	    
    	    try (Connection conn = DBConnection.getConnection();
    	         PreparedStatement ps = conn.prepareStatement(sql)) {  	        
    	        ps.setDouble(1, kg);
    	        ps.setInt(2, codAttivita);  	        
    	        int righe = ps.executeUpdate();
    	        return righe >0;
    	    } 
    	}
}



