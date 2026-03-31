package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import database.DBConnection;
import model.Anomalia;
import model.AttivitaImminente;
import model.LivelloGravita;
import model.Notifica;
import model.Utente;

public class NotificaDao {

	public void salvaNotifica(Notifica n) throws SQLException{
		 Connection conn = DBConnection.getConnection();
		 try {
		        conn.setAutoCommit(false); 
		        int id = 0;
		        
		        if (n instanceof Anomalia) {
		            Anomalia a = (Anomalia) n;
		            String sql = "CALL PR_REGISTRA_NOTIFICA_ANOMALIA(?, ?, ?::livellogravita, ?, ?, ?)";
		            try (CallableStatement cs = conn.prepareCall(sql)) {
		                cs.setString(1, a.getTipoAnomalia()); 
		                cs.setString(2, a.getDescrizione()); 
		                cs.setString(3, a.getGravita().toString()); 
		                cs.setInt(4, (int) a.getEstensione()); 
		                cs.setInt(5, n.getCreatore().getIdUtente()); 
		                cs.registerOutParameter(6, java.sql.Types.INTEGER); 
		                cs.execute();
		                id = cs.getInt(6);
		            }
		        }else if (n instanceof AttivitaImminente) {
		            AttivitaImminente i = (AttivitaImminente) n;
		            String sql = "CALL P_REGISTRA_NOTIFICA_ATTIVITAIMMINENTE(?, ?, ?, ?, ?)";
		            try (CallableStatement cs = conn.prepareCall(sql)) {
		                cs.setString(1,i.getTipoAttivitaImminente()); 
		                cs.setString(2, i.getDescrizione()); 
		                cs.setDate(3, java.sql.Date.valueOf(i.getDataScadenza())); 
		                cs.setInt(4, n.getCreatore().getIdUtente()); 
		                cs.registerOutParameter(5, java.sql.Types.INTEGER);
		                cs.execute();
		                id = cs.getInt(5);
		            }
		        }
		        
		        String sqlPonte = "CALL P_INVIA_A_DESTINATARIO(?, ?)";
		        try (CallableStatement csPonte = conn.prepareCall(sqlPonte)) {
		            for (Utente u : n.getDestinatari()) {
		                csPonte.setInt(1, id);
		                csPonte.setInt(2, u.getIdUtente());
		                csPonte.addBatch(); 
		            }
		            csPonte.executeBatch();
		        }
		        
		        conn.commit();
		 } catch (SQLException e) {
		        conn.rollback(); 
		        throw e;
		    } finally {
		        conn.setAutoCommit(true);
		        conn.close();
		        
		    }
	}
	
	public ArrayList<Notifica> prelevaNotificheInviate(int idProprietario) throws SQLException {
	    ArrayList<Notifica> lista = new ArrayList<>();
	    String sql = "SELECT N.*, A.TIPOANOMALIA,A.DESCRIZIONE AS DESC_A, A.GRAVITA, A.ESTENSIONE, I.TIPOATTIVITAIMMINENTE,I.DESCRIZIONE AS DESC_I, I.DATASCADENZA " +
	                 "FROM NOTIFICA N " +
	                 "LEFT JOIN ANOMALIA A ON N.CODNOTIFICA = A.FK_CODNOTIFICA " +
	                 "LEFT JOIN ATTIVITAIMMINENTE I ON N.CODNOTIFICA = I.FK_CODNOTIFICA " +
	                 "WHERE N.FK_CREATORE = ? "+
	                 "ORDER BY N.DATAINVIO DESC";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, idProprietario);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	 int codice = rs.getInt("CODNOTIFICA");
	        	 LocalDate dataInvio = rs.getDate("DATAINVIO").toLocalDate();
	             ArrayList<Utente> u= new ArrayList<Utente>();
	             if (rs.getString("TIPOANOMALIA") != null) {
	                 lista.add(new Anomalia(
	                     codice,
	                	 dataInvio,
	                     null, 
	                     rs.getString("TIPOANOMALIA"),
	                     rs.getString("DESC_A"),
	                     LivelloGravita.valueOf(rs.getString("GRAVITA").toUpperCase()),
	                     rs.getInt("ESTENSIONE"),
	                     u
	                 ));
	             } else {
	                 lista.add(new AttivitaImminente(
	                	 codice,	 
	                     dataInvio,
	                     null,
	                     rs.getString("TIPOATTIVITAIMMINENTE"),
	                     rs.getString("DESC_I"),
	                     rs.getDate("DATASCADENZA").toLocalDate(),
	                     u
	                 ));
	             }
	        }
	    }
	    return lista;
	}
	
	public ArrayList<Notifica> prelevaNotificheRicevute(int idColtivatore) throws SQLException {
	    ArrayList<Notifica> lista = new ArrayList<>();
	    String sql = "SELECT N.*, A.TIPOANOMALIA, A.GRAVITA, A.ESTENSIONE,A.DESCRIZIONE,I.DESCRIZIONE, I.TIPOATTIVITAIMMINENTE, I.DATASCADENZA " +
	                 "FROM NOTIFICA N " +
	                 "JOIN NOTIFICADESTINATARIO ND ON N.CODNOTIFICA = ND.FK_CODNOTIFICA " +
	                 "LEFT JOIN ANOMALIA A ON N.CODNOTIFICA = A.FK_CODNOTIFICA " +
	                 "LEFT JOIN ATTIVITAIMMINENTE I ON N.CODNOTIFICA = I.FK_CODNOTIFICA " +
	                 "WHERE ND.FK_DESTINATARIO = ? ORDER BY N.DATAINVIO DESC";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, idColtivatore);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	 LocalDate dataInvio = rs.getDate("DATAINVIO").toLocalDate();
	             ArrayList<Utente> u= new ArrayList<Utente>();
	             if (rs.getString("TIPOANOMALIA") != null) {
	                 lista.add(new Anomalia(
	                	 dataInvio,
	                     null,
	                     rs.getString("TIPOANOMALIA"), 
	                     rs.getString("DESCRIZIONE"),
	                     LivelloGravita.valueOf(rs.getString("GRAVITA").toUpperCase()),
	                     rs.getInt("ESTENSIONE"),
	                     u
	                 ));
	             } else {
	                 lista.add(new AttivitaImminente(
	                     dataInvio,
	                     null,
	                     rs.getString("TIPOATTIVITAIMMINENTE"),
	                     rs.getString("DESCRIZIONE"),
	                     rs.getDate("DATASCADENZA").toLocalDate(),
	                     u
	                 ));
	             }
	        }
	    }
	    return lista;
	}
	
	public boolean eliminaNotifica(int codNotifica) throws SQLException {
	    String sql = "DELETE FROM NOTIFICA WHERE CODNOTIFICA = ?";	    
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, codNotifica);
	        int righeCancellate = ps.executeUpdate();
	        return righeCancellate > 0;
	    }
	}
	
	
}
