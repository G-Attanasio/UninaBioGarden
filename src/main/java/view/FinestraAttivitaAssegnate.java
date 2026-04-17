package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import dto.AttivitaDTO;

public class FinestraAttivitaAssegnate extends JPanel {

	private static final long serialVersionUID = 1L;

	private Controller controller;
	private JScrollPane scroll;
	private JTable tabella;
	private String[] titoli= {"Attività","Coltivatore","Progetto","Metodo","Inizio","Fine","Stato"};
	private DefaultTableModel modello;
	
	
	public FinestraAttivitaAssegnate(Controller controller) {
		this.controller=controller;
		setLayout(new BorderLayout());
		modello= new DefaultTableModel(titoli, 0){
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false; 
		    }
		};
		tabella= new JTable(modello);
		tabella.setFont(new Font("Arial",Font.ITALIC,14));
		tabella.setRowHeight(20);
		tabella.getTableHeader().setReorderingAllowed(false);
		tabella.getTableHeader().setFont(new Font("Arial",Font.BOLD,12));
		scroll= new JScrollPane(tabella);
		add(scroll,BorderLayout.CENTER);
	}
	
	public void mostraAttivita(ArrayList<AttivitaDTO> lista) {
	    svuotaTabella();

	    for (AttivitaDTO dto : lista) {
	        Object[] riga = {
	            dto.getTipo(),
	            dto.getUsernameColtivatore(),
	            dto.getNomeProgetto(),
	            dto.getMetodo(),
	            dto.getDataInizio(),
	            dto.getDataFine(),
	            dto.getStatoEsecuzione()
	        };
	        aggiungiRigaTabella(riga);
	    }
	}
	
	public void aggiungiRigaTabella(Object[] riga) {
	    modello.addRow(riga);
	}
	public void svuotaTabella() {
	    modello.setRowCount(0);
	}
	public void mostraMessaggio(String testo) {
		JOptionPane.showMessageDialog(this, testo);
	}
}
