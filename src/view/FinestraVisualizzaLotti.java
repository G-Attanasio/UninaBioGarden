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

public class FinestraVisualizzaLotti extends JPanel {

	private static final long serialVersionUID = 1L;

	private Controller controller;
	private JButton cancella;
	private JButton aggiungi;
	private JButton creaProgetto;
	private JScrollPane scroll;
	private JTable tabella;
	private String[] titoli= {"Codice","Tessitura","Dimensioni","Ph","Morfologia","Altitudine","Località","Comune","Provincia"};
	private DefaultTableModel modello;
	
	
	public FinestraVisualizzaLotti (Controller controller) {
		
		this.controller=controller;
		setLayout(new BorderLayout());
		JPanel pnlSud= new JPanel ( new FlowLayout(FlowLayout.LEFT));
		cancella= new JButton("Cancella");
		aggiungi= new JButton("Aggiungi un lotto");
		creaProgetto= new JButton("Crea un progetto stagionale");
		pnlSud.add(cancella);
		pnlSud.add(aggiungi);
		pnlSud.add(creaProgetto);
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
		scroll= new JScrollPane(tabella);
		add(scroll,BorderLayout.CENTER);
		
		cancella.addActionListener(e -> {
		    
		    int rigaSelezionata = tabella.getSelectedRow();
		    if (rigaSelezionata != -1) { 
		        int idLotto = (int) modello.getValueAt(rigaSelezionata, 0);
		        int scelta = JOptionPane.showConfirmDialog(
		            this, 
		            "Sei sicuro di voler eliminare il lotto con codice: " + idLotto + "?", 
		            "Conferma", 
		            JOptionPane.YES_NO_OPTION, 
		            JOptionPane.WARNING_MESSAGE
		        );
		        if (scelta == JOptionPane.YES_OPTION) {
		            controller.eliminaLotto();
		        }
		    } else {
		        
		        JOptionPane.showMessageDialog(this, "Seleziona un lotto dalla tabella.", "Nessuna selezione", JOptionPane.INFORMATION_MESSAGE);
		    }
		});
		
		aggiungi.addActionListener(e->{
			controller.mostraPanelInterno("crea lotto");
		});
		creaProgetto.addActionListener(e->{
			controller.caricaColtureInCreaProgetto();
			controller.mostraPanelInterno("crea progetto");
		});

		
		
		
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
		this.controller=controller;
	}

	public JTable getTabella() {
		return tabella;
	}

	public JTable getModello() {
		return tabella;
	}
	
}
