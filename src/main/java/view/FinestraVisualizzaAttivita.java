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
import dto.AttivitaDTO;

public class FinestraVisualizzaAttivita extends JPanel {

	private static final long serialVersionUID = 1L;

	private Controller controller;
	private JScrollPane scroll;
	private JTable tabella;
	private String[] titoli= {"codice","Tipo","Coltura","Progetto","Metodo","Inizio","Fine","Stato"};
	private DefaultTableModel modello;
	private JButton registraRaccolta;
	
	public FinestraVisualizzaAttivita(Controller controller) {
		this.controller=controller;
		setLayout(new BorderLayout());
		JPanel pnlBottoni= new JPanel(new FlowLayout());
		registraRaccolta= new JButton("Registra raccolta");
		registraRaccolta=creaBottonePersonale("Registra raccolta", new Color(135, 206, 250), new Color(70, 130, 180));
		pnlBottoni.add(registraRaccolta);
		add(pnlBottoni,BorderLayout.SOUTH);
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
		tabella.getColumnModel().getColumn(0).setMinWidth(0);
		tabella.getColumnModel().getColumn(0).setMaxWidth(0);
		tabella.getColumnModel().getColumn(0).setPreferredWidth(0);
		tabella.getTableHeader().setReorderingAllowed(false);
		scroll= new JScrollPane(tabella);
		add(scroll,BorderLayout.CENTER);
		
		registraRaccolta.addActionListener(e -> {
		    int riga = tabella.getSelectedRow();
		    if (riga != -1) {
		        String tipo = tabella.getValueAt(riga, 1).toString();
		        String stato = tabella.getValueAt(riga, 7).toString();

		        if (tipo.equalsIgnoreCase("RACCOLTA") && stato.equalsIgnoreCase("COMPLETATO")) {
		            int codAttivita = Integer.parseInt(tabella.getValueAt(riga, 0).toString());
		            String input=JOptionPane.showInputDialog(null, "Inserisci la Quantità Reale Raccolta (Kg):", 
                            "Registrazione Raccolto", JOptionPane.QUESTION_MESSAGE);
		            if (input != null && !input.isEmpty()) {
		                try {
		                    double quantita = Double.parseDouble(input.replace(",", "."));	                   
		                    controller.registraRaccolta(codAttivita, quantita);
		                } catch (NumberFormatException ex) {
		                    JOptionPane.showMessageDialog(this, "Inserire un numero valido");
		                }
		            }
		        } else {
		            JOptionPane.showMessageDialog(this, "Puoi registrare quantità solo per attività di RACCOLTA con stato COMPLETATO.");
		        }
		    } else {
		        JOptionPane.showMessageDialog(this, "Seleziona una riga");
		    }
		});
				
		}
	
		public void mostraAttivita(ArrayList<AttivitaDTO> lista) {
	    	svuotaTabella();

	    	for (AttivitaDTO dto : lista) {
	        	Object[] riga = {
	        		dto.getCodAttivita(),
	            	dto.getTipo(),
	            	dto.getColtura(),
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

			Dimension grandezza= new Dimension(200,30);
			bottone.setPreferredSize(grandezza);
		    bottone.setContentAreaFilled(false);
		    bottone.setBorderPainted(false);
		    bottone.setFocusPainted(false);
		    bottone.setForeground(Color.WHITE);
		    bottone.setFont(new Font("Arial",Font.PLAIN,17));
		    
		    return bottone;
		}
	}

