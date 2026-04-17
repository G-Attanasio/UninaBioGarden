package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import dto.NotificaDTO;

public class FinestraVisualizzaNotifiche extends JPanel {

	private static final long serialVersionUID = 1L;

	private Controller controller;
	private JButton cancella;
	private JButton aggiungi;
	private JScrollPane scroll;
	private JTable tabella;
	private String[] titoli= {"Tipo","Titolo","Data Scadenza","Livello gravità","Estensione","Codice","Descrizione"};
	private DefaultTableModel modello;
	private boolean inviata;
	
	
	public FinestraVisualizzaNotifiche(Controller controller) {
		this.controller=controller;
		setLayout(new BorderLayout());
		JPanel pnlSud= new JPanel ( new FlowLayout(FlowLayout.LEFT));
		cancella= new JButton("Cancella notifica");
		aggiungi= new JButton("Crea notifica");
		
		pnlSud.add(cancella);
		pnlSud.add(aggiungi);
		add(pnlSud,BorderLayout.SOUTH);
		
		modello= new DefaultTableModel(titoli, 0){
			private static final long serialVersionUID = 1L;
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false; 
		    }
		};
		tabella= new JTable(modello);
		tabella.getColumnModel().getColumn(5).setMinWidth(0);
		tabella.getColumnModel().getColumn(5).setMaxWidth(0);
		tabella.getColumnModel().getColumn(5).setPreferredWidth(0);
		tabella.getColumnModel().getColumn(6).setMinWidth(0);
		tabella.getColumnModel().getColumn(6).setMaxWidth(0);
		tabella.getColumnModel().getColumn(6).setPreferredWidth(0);		
		tabella.getTableHeader().setReorderingAllowed(false);
		scroll= new JScrollPane(tabella);
		add(scroll,BorderLayout.CENTER);
		
		cancella.addActionListener(e -> {
		    
		    int riga = tabella.getSelectedRow();
		    if (riga != -1) {
		    	int codNotifica = Integer.parseInt(tabella.getValueAt(riga, 5).toString());
		        int scelta = JOptionPane.showConfirmDialog(
		            this, 
		            "Sei sicuro di voler eliminare questa notifica?", 
		            "Conferma", 
		            JOptionPane.YES_NO_OPTION, 
		            JOptionPane.WARNING_MESSAGE
		        );
		        if (scelta == JOptionPane.YES_OPTION) {
		            controller.eliminaNotifica(codNotifica,inviata);
		            controller.mostraPanelInterno("visualizza notifiche");
		        }
		    } else {
		        JOptionPane.showMessageDialog(this, "Seleziona una notifica dalla tabella.", "Nessuna selezione", JOptionPane.INFORMATION_MESSAGE);
		    }
		});
		
		aggiungi.addActionListener(e->{
			controller.caricaColtivatoriComeDestinatari();
			controller.mostraPanelInterno("crea notifica");
		});
		
		tabella.addMouseListener(new MouseAdapter() {
			 @Override
			    public void mouseClicked(java.awt.event.MouseEvent e) {
			        if (e.getClickCount() == 2) { 
			            int riga = tabella.getSelectedRow();
			            if (riga != -1) {
			            	String titolo = tabella.getValueAt(riga, 1).toString();		               
			                Object desc = tabella.getValueAt(riga, 6);
			                String descrizione;
			                if(desc != null) {
			                	descrizione=desc.toString(); 
			                }else {
			                	descrizione="Nessun dettaglio presente.";
			                }
			                mostraMessaggioNotifica(titolo,descrizione);
			            }
			        }
			    }
			
		});
		
	}
	
	public void onOffAggiungi(boolean scelta) {
		aggiungi.setVisible(scelta);
		inviata=scelta;
	}
	
	public void mostraMessaggioNotifica(String titolo, String messaggio) {
	    JOptionPane.showMessageDialog(null, messaggio, "Descrizione Notifica: " + titolo, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void mostraMessaggio(String testo) {
		JOptionPane.showMessageDialog(this, testo);
	}
	
	public void mostraNotificheInviate(ArrayList<NotificaDTO> lista, boolean inviate) {
	    svuotaTabella();
	    onOffAggiungi(inviate);
	    for (NotificaDTO n : lista) {
	        Object[] riga = {
	            n.getTipo(),
	            n.getDescrizioneVeloce(),
	            n.getDataScadenza(),
	            n.getGravità(),
	            n.getEstensione(),
	            n.getCodNotifica(),
	            n.getDescrizione()
	        };
	        aggiungiRigaTabella(riga);
	    }
	}
	
	public void mostraNotificheRicevute(ArrayList<NotificaDTO> lista, boolean inviate) {
	    svuotaTabella();
	    onOffAggiungi(inviate);
	    for (NotificaDTO n : lista) {
	        Object[] riga = {
	            n.getTipo(),
	            n.getDescrizioneVeloce(),
	            n.getDataScadenza(),
	            n.getGravità(),
	            n.getEstensione(),
	            n.getCodNotifica(),
	            n.getDescrizione()
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

	public JButton getAggiungi() {
		return aggiungi;
	}

	public boolean isInviata() {
		return inviata;
	}

	public void setInviata(boolean inviata) {
		this.inviata = inviata;
	}
	
}
