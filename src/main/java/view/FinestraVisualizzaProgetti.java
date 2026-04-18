package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import dto.ProgettoDTO;

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
		cancella= new JButton("Cancella");	
		cancella=creaBottonePersonale("Cancella",  new Color(220, 50, 50),new Color(150, 20, 20));
		report= new JButton("Visualizza report raccolte");
		report= creaBottonePersonale("Visualizza report raccolte", new Color(135, 206, 250), new Color(70, 130, 180));
		report.setPreferredSize(new Dimension(220,30));
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
		tabella.setFont(new Font("Arial",Font.ITALIC,14));
		tabella.setRowHeight(20);
		tabella.getTableHeader().setReorderingAllowed(false);
		tabella.getTableHeader().setFont(new Font("Arial",Font.BOLD,12));
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
	
	public void mostraProgetti(ArrayList<ProgettoDTO> lista) {
	    svuotaTabella();
	    for (ProgettoDTO p : lista) {
	        Object[] riga = {
	            p.getCodProgetto(),
	            p.getNomeProgetto(),
	            p.getLottoImpegnato(),
	            p.getStagioneDiRiferimento(),
	            p.getDataInizio(),
	            p.getDurata()+" Giorni",
	            p.getStatoEsecuzione()
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
		this.controller = controller;
	}
	
	public JTable getTabella() {
		return tabella;
	}

	public DefaultTableModel getModello() { 
	    return modello;
	}
	
	public static JButton creaBottonePersonale(String testo, Color coloreAlto, Color coloreBasso) {
	    JButton bottone = new JButton(testo) {
	        private static final long serialVersionUID = 1L;
	        @Override
	        protected void paintComponent(Graphics g) {
	            Graphics2D g2 = (Graphics2D) g.create();
	            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	            GradientPaint gp = new GradientPaint(0, 0, coloreAlto, 0, getHeight(), coloreBasso);
	            g2.setPaint(gp);           
	            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
	            g2.setColor(coloreBasso.darker());
	            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
	            g2.dispose();
	            super.paintComponent(g);
	        }
	    };

		Dimension grandezza= new Dimension(150,30);
		bottone.setPreferredSize(grandezza);
	    bottone.setContentAreaFilled(false);
	    bottone.setBorderPainted(false);
	    bottone.setFocusPainted(false);
	    bottone.setForeground(Color.WHITE);
	    bottone.setFont(new Font("Arial",Font.PLAIN,17));
	    
	    return bottone;
	}
	
}
