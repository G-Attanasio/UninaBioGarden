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

