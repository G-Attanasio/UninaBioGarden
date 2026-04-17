package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
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
	private String[] titoli= {"Tipo","Titolo","Data Scadenza","Livello gravità","Data invio","Estensione","Codice","Descrizione"};
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
		tabella.setFont(new Font("Arial",Font.ITALIC,14));
		tabella.setRowHeight(20);
		tabella.getTableHeader().setFont(new Font("Arial",Font.BOLD,12));
		tabella.getColumnModel().getColumn(5).setMinWidth(0);
		tabella.getColumnModel().getColumn(5).setMaxWidth(0);
		tabella.getColumnModel().getColumn(5).setPreferredWidth(0);
		tabella.getColumnModel().getColumn(6).setMinWidth(0);
		tabella.getColumnModel().getColumn(6).setMaxWidth(0);
		tabella.getColumnModel().getColumn(6).setPreferredWidth(0);		
		tabella.getColumnModel().getColumn(7).setMinWidth(0);
		tabella.getColumnModel().getColumn(7).setMaxWidth(0);
		tabella.getColumnModel().getColumn(7).setPreferredWidth(0);		
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
		            	String tipo = String.valueOf(modello.getValueAt(riga, 0));
		            	String titolo = String.valueOf(modello.getValueAt(riga, 1));
		            	String scadenza = String.valueOf(modello.getValueAt(riga, 2));
		            	String gravita = String.valueOf(modello.getValueAt(riga, 3));
		            	String estensione = String.valueOf(modello.getValueAt(riga, 5));
		            	String descrizione = String.valueOf(modello.getValueAt(riga, 7));
		                StringBuilder sb = new StringBuilder();
		                sb.append("Tipo: ").append(tipo).append("\n");
		                sb.append("------------------\n");
		                sb.append("Titolo: ").append(titolo).append("\n");
		                sb.append("------------------\n");
		                sb.append("Descrizione: ").append(descrizione).append("\n");
		                sb.append("------------------\n");
		                if (tipo.equals("Anomalia")) {
		                    sb.append("Gravità: ").append(gravita).append("\n");
		                    sb.append("------------------\n");
		                    sb.append("Estensione: ").append(estensione).append("\n");
		                } else {
		                    sb.append("Scadenza: ").append(scadenza).append("\n");
		                }
		                javax.swing.JTextArea textArea = new javax.swing.JTextArea(sb.toString());
		                textArea.setColumns(40); 
		                textArea.setRows(15);
		                textArea.setLineWrap(true); 
		                textArea.setWrapStyleWord(true); 
		                textArea.setEditable(false); 
		                textArea.setOpaque(true); 
		                textArea.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
		                JOptionPane.showMessageDialog(null, new javax.swing.JScrollPane(textArea), "Dettagli Notifica", JOptionPane.INFORMATION_MESSAGE);
		            }
		        }
		    }
		});
		
	}
	
	public void onOffAggiungi(boolean scelta) {
		aggiungi.setVisible(scelta);
		inviata=scelta;
		
		if (scelta) {
	        tabella.getColumnModel().getColumn(4).setHeaderValue("Data Invio");
	    } else {
	        tabella.getColumnModel().getColumn(4).setHeaderValue("Data Ricevuta");
	    }
	    tabella.getTableHeader().repaint();
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
	            n.getScadenza(),
	            n.getGravità(),
	            n.getDataInvio(),
	            n.getEstens(),
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
	            n.getScadenza(),
	            n.getGravità(),
	            n.getDataInvio(),
	            n.getEstens(),
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
