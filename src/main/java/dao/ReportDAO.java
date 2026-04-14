package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnection;
import exceptions.RisorsaNonTrovataException;
import model.DatiReport;

public class ReportDAO {

	public ArrayList<DatiReport> prelevaDatiReport(int codProgetto) throws SQLException {
	    ArrayList<DatiReport> datiReport= new ArrayList<DatiReport>();
	     
	    String sql ="SELECT NOME, SUM(SEMI) AS S, SUM(PREVISTA) AS P, SUM(REALE) AS R FROM (" +
                "SELECT C.NOME, SC.QUANTITASEMI AS SEMI, 0 AS PREVISTA, 0 AS REALE FROM ATTIVITA A " +
                "JOIN SEMINACOLTURA SC ON A.CODATTIVITA = SC.FK_CODATTIVITA JOIN COLTURA C ON SC.FK_CODCOLTURA = C.CODCOLTURA WHERE A.FK_CODPROGETTO = ? " +
                "UNION ALL " +
                "SELECT C.NOME, 0 AS SEMI, R.QUANTITAPREVISTA AS PREVISTA, R.QUANTITAREALE AS REALE FROM ATTIVITA A " +
                "JOIN RACCOLTA R ON A.CODATTIVITA = R.FK_CODATTIVITA JOIN COLTURA C ON R.FK_CODCOLTURA = C.CODCOLTURA WHERE A.FK_CODPROGETTO = ?)" +
                " AS T GROUP BY NOME";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {	        
	        ps.setInt(1, codProgetto);
	        ps.setInt(2, codProgetto);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            DatiReport dr=new DatiReport(
	            		rs.getString("NOME"),
	                    rs.getDouble("S"),
	                    rs.getDouble("P"),
	                    rs.getDouble("R")
	            		);
	           datiReport.add(dr);
	        }
	    }
	    return datiReport;
	}
}
