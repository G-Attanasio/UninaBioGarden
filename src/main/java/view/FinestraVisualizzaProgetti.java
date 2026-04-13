package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.Controller;

public class FinestraVisualizzaProgetti extends JPanel {

	private static final long serialVersionUID = 1L;

	private Controller controller;
	private JScrollPane scroll;
	private JTable tabella;
	private String[] titoli= {"codice","Nome","Codice lotto","Periodo","Data inizio","Durata","Stato"};
	private DefaultTableModel modello;
	private JButton cancella;
	private JButton report;
	
	public FinestraVisualizzaProgetti(Controller controller) {
		this.controller=controller;
		setLayout(new BorderLayout());
		JPanel pnlSud= new JPanel ( new FlowLayout(FlowLayout.LEFT));
		cancella= new JButton("Cancella progetto");	
		report= new JButton("Visualizza report raccolte");
		pnlSud.add(cancella);		
		pnlSud.add(report);
		add(pnlSud,BorderLayout.SOUTH);		
		modello= new DefaultTableModel(titoli, 0){
			private static final long serialVersionUID = 1L;
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false; 
		    }
		};
		tabella= new JTable(modello);
		tabella.getTableHeader().setReorderingAllowed(false);
		tabella.getColumnModel().getColumn(0).setMinWidth(0);
		tabella.getColumnModel().getColumn(0).setMaxWidth(0);
		tabella.getColumnModel().getColumn(0).setPreferredWidth(0);
		scroll= new JScrollPane(tabella);
		add(scroll,BorderLayout.CENTER);
		
		cancella.addActionListener(e -> {		    
		    int rigaSelezionata = tabella.getSelectedRow();
		    if (rigaSelezionata != -1) { 
		        int codProgetto = (int) modello.getValueAt(rigaSelezionata, 0);
		        int scelta = JOptionPane.showConfirmDialog(
		            this, 
		            "Sei sicuro di voler eliminare il progetto con codice: " + codProgetto + "?", 
		            "Conferma", 
		            JOptionPane.YES_NO_OPTION, 
		            JOptionPane.WARNING_MESSAGE
		        );
		        if (scelta == JOptionPane.YES_OPTION) {
		            controller.eliminaProgetto(codProgetto);
		        }
		    } else {
		        JOptionPane.showMessageDialog(this, "Seleziona un progetto dalla tabella.", "Nessuna selezione", JOptionPane.INFORMATION_MESSAGE);
		    }
		});
		
		report.addActionListener(e -> {
		    int riga = tabella.getSelectedRow();		    
		    if (riga != -1) {
		        String stato = tabella.getValueAt(riga, 6).toString();
		        if (stato.equals("COMPLETATO")) {
		            int codProgetto = Integer.parseInt(tabella.getValueAt(riga, 0).toString());		        
		            controller.caricaDatiReport(codProgetto);
		        } else {
		            JOptionPane.showMessageDialog(this, 
		                "L'analisi è disponibile solo per i progetti con stato 'COMPLETATO'.\n" +
		                "Stato attuale: " + stato, 
		                "Report non disponibile", 
		                JOptionPane.WARNING_MESSAGE);
		        }
		    } else {
		        JOptionPane.showMessageDialog(this, "Seleziona un progetto dalla tabella.");
		    }
		});
		
		
	}
	
	
	public void mostraMessaggio(String testo) {
		JOptionPane.showMessageDialog(this, testo);
	}
	
	public void aggiungiRigaTabella(Object[] riga) {
	    modello.addRow(riga);
	}
	public void svuotaTabella() {
	    modello.setRowCount(0);
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	public JTable getTabella() {
		return tabella;
	}

	public DefaultTableModel getModello() { 
	    return modello;
	}
	
}
