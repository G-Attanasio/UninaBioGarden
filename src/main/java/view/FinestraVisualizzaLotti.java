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
import dto.LottoDTO;

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
		cancella= new JButton("Cancella lotto");
		cancella=creaBottonePersonale("Cancella", new Color(220, 50, 50),new Color(150, 20, 20));
		aggiungi= new JButton("Aggiungi un Lotto");
		aggiungi= creaBottonePersonale("Aggiungi Lotto", new Color(135, 206, 250), new Color(70, 130, 180));
		creaProgetto= new JButton("Crea un progetto stagionale");
		creaProgetto= creaBottonePersonale("Crea Progetto", new Color(255, 215, 0), new Color(218, 165, 32));
		creaProgetto.setForeground(new Color(60, 60, 20));
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
		tabella.setFont(new Font("Arial",Font.ITALIC,14));
		tabella.setRowHeight(20);
		tabella.getTableHeader().setReorderingAllowed(false);
		tabella.getTableHeader().setFont(new Font("Arial",Font.BOLD,12));
		scroll= new JScrollPane(tabella);
		add(scroll,BorderLayout.CENTER);
		
		cancella.addActionListener(e -> {	    
		    int rigaSelezionata = tabella.getSelectedRow();
		    if (rigaSelezionata != -1) { 
		        int codLotto = (int) modello.getValueAt(rigaSelezionata, 0);
		        int scelta = JOptionPane.showConfirmDialog(
		            this, 
		            "Sei sicuro di voler eliminare il lotto con codice: " + codLotto + "?", 
		            "Conferma", 
		            JOptionPane.YES_NO_OPTION, 
		            JOptionPane.WARNING_MESSAGE
		        );
		        if (scelta == JOptionPane.YES_OPTION) {
		            controller.eliminaLotto(codLotto);
		        }
		    } else {
		        JOptionPane.showMessageDialog(this, "Seleziona un lotto dalla tabella.", "Nessuna selezione", JOptionPane.INFORMATION_MESSAGE);
		    }
		});
		
		aggiungi.addActionListener(e->{
			controller.mostraPanelInterno("crea lotto");
		});
		creaProgetto.addActionListener(e->{
			 int riga = tabella.getSelectedRow();
			    if (riga != -1) {
			        int codLotto = (int) modello.getValueAt(riga, 0);			        
			        controller.avviaProgetto(codLotto); 			        
			        controller.caricaColtivatoriInProgetto();
			        controller.mostraPanelInterno("crea progetto");
			    } else {
			        JOptionPane.showMessageDialog(this, "Seleziona un lotto dalla lista!");
			    }
		});

		
		
		
	}
	
	public void mostraLotti(ArrayList<LottoDTO> lista) {
	    svuotaTabella();	    
	    for (LottoDTO lc : lista) {
	        Object[] riga = {
	            lc.getCodLotto(),
	            lc.getTessitura().toString().replace("_", " "),
	            lc.getDimensioni(),
	            lc.getPh(),
	            lc.getMorfologia(),
	            lc.getAltitudine(),
	            lc.getLocalita(),
	            lc.getComune(),
	            lc.getProvincia()
	        };
	        aggiungiRigaTabella(riga);
	    }
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
		this.controller=controller;
	}

	public JTable getTabella() {
		return tabella;
	}

	public JTable getModello() {
		return tabella;
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
