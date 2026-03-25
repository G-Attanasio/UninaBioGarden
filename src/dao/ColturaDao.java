package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnection;
import model.Coltura;

public class ColturaDao {

	public ArrayList<Coltura> preleva() throws SQLException {
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
			throw e;
		}
		return listaColture;
	}
}
