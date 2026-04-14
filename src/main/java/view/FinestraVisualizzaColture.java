package view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.Controller;

public class FinestraVisualizzaColture extends JPanel {

	private static final long serialVersionUID = 1L;

	private Controller controller;
	private JScrollPane scroll;
	private JTable tabella;
	private String[] titoli= {"Codice","Nome","Specie","Famiglia","Tempo maturazione","Destinazione d'uso","periodo ideale"};
	private DefaultTableModel modello;
	
	public FinestraVisualizzaColture(Controller controller) {
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
		tabella.getTableHeader().setReorderingAllowed(false);
		scroll= new JScrollPane(tabella);
		add(scroll,BorderLayout.CENTER);
		
		
		
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
