package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.concurrent.Flow;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.Border;

import controller.Controller;

public class FinestraCreaNotifica extends JPanel {

	private static final long serialVersionUID = 1L;

	private Controller controller;
	private JTextField cmpDescrizioneVeloce;
	private JTextArea cmpDescrizione;
	private JScrollPane scrollText;
	private CardLayout pnlInterno;
	private JComboBox<String> sceltaNotifica= new JComboBox<String>(new String[]{"Attività Imminente","Anomalia"});
	private JPanel sceltaPanel;
	private JTextField cmpDataScadenza;
	private JComboBox<String> cmpLivelloGravita= new JComboBox<String>(new String[] {"BASSO","MEDIO","ALTO","CRITICO"});
	private JTextField cmpEstensione;
	private JButton invia;
	private JButton cancella;
	private JComboBox<String> listaDestinatari;
	private ArrayList<String> elencoColtivatori;
	private JButton aggiungiDestinatario;
	private JButton aggiungiTutti;
	private DefaultListModel<String> modelloScelti;
	private JList<String> listaScelti;
	private JScrollPane scrollScelti;
	private JButton rimuovi;
	
	public FinestraCreaNotifica(Controller controller) {
		this.controller=controller;
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		JPanel pnlDescrizioneVeloce= new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel pnlDescrizione= new JPanel (new FlowLayout(FlowLayout.LEFT));
		JPanel pnlSceltaNotifica= new JPanel(new FlowLayout());
		JLabel descrizioneVeloce= new JLabel("Inserisci una descrizione(obbligatorio):");
		
		cmpDescrizioneVeloce= new JTextField(30);
		pnlDescrizioneVeloce.setPreferredSize(new Dimension(250,50));
		pnlDescrizioneVeloce.add(descrizioneVeloce);
		pnlDescrizioneVeloce.add(cmpDescrizioneVeloce);
		JLabel descrizione= new JLabel("Inserisci una descrizione dettagliata(facoltativo):");
		cmpDescrizione= new JTextArea(8,50);
		cmpDescrizione.setLineWrap(true);      
		cmpDescrizione.setWrapStyleWord(true);
		scrollText= new JScrollPane(cmpDescrizione);
		scrollText.setMaximumSize(new Dimension(200, 100));
		scrollText.setMinimumSize(new Dimension(200, 100));
		pnlDescrizione.add(descrizione);
		pnlDescrizione.add(scrollText);
		
		JLabel scelta= new JLabel("Tipo notifica:");
		pnlSceltaNotifica.add(scelta);
		pnlSceltaNotifica.add(sceltaNotifica);
		
	
		JPanel pnlAttivita= new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel dataScadenza= new JLabel("Inserire data di scadenza dell'attività imminente");
		cmpDataScadenza= new JTextField(10);
		pnlAttivita.add(dataScadenza);
		pnlAttivita.add(cmpDataScadenza);
		JPanel pnlAnomalia= new JPanel(new FlowLayout());
		JLabel livelloGravita= new JLabel("Indica il livello di gravità:");
		JLabel estensione= new JLabel("Estensione in metri quadri(facoltativo):");
		cmpEstensione= new JTextField(12);
		pnlAnomalia.add(livelloGravita);
		pnlAnomalia.add(cmpLivelloGravita);
		pnlAnomalia.add(estensione);
		pnlAnomalia.add(cmpEstensione);
		pnlInterno= new CardLayout();
		sceltaPanel= new JPanel(pnlInterno);
		sceltaPanel.add(pnlAttivita,"attivita imminente");
		sceltaPanel.add(pnlAnomalia,"anomalia");
		
		JPanel pnlDestinatari= new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel sceltaDestinatari= new JLabel("Scegli destinatari");
		this.elencoColtivatori= new ArrayList<String>();
		listaDestinatari= new JComboBox<String>(elencoColtivatori.toArray(new String[0]));
		aggiungiDestinatario= new JButton("Aggiungi destinatario");
		aggiungiTutti= new JButton("Aggiungi tutti come destinatario");
		modelloScelti = new DefaultListModel<>();
		listaScelti = new JList<>(modelloScelti);
		scrollScelti = new JScrollPane(listaScelti);
		scrollScelti.setPreferredSize(new Dimension(150, 80));
		rimuovi= new JButton("Rimuovi");
		pnlDestinatari.add(sceltaDestinatari);
		pnlDestinatari.add(listaDestinatari);
		pnlDestinatari.add(aggiungiDestinatario);
		pnlDestinatari.add(aggiungiTutti);
		pnlDestinatari.add(scrollScelti);
		pnlDestinatari.add(rimuovi);
		
		
		JPanel pnlBottoni= new JPanel(new FlowLayout());
		invia= new JButton("Invia");
		cancella= new JButton("Cancella");
		pnlBottoni.add(invia);
		pnlBottoni.add(cancella);
		
		add(Box.createVerticalGlue());
		add(pnlDescrizioneVeloce);
		add(pnlDescrizione);
		add(Box.createVerticalStrut(50));
		add(pnlSceltaNotifica);
		add(Box.createVerticalStrut(50));
		add(sceltaPanel);
		add(pnlDestinatari);
		add(Box.createVerticalStrut(80));
		add(pnlBottoni);
		add(Box.createVerticalGlue());
		
		sceltaNotifica.addActionListener(e->{
			String s=(String) sceltaNotifica.getSelectedItem();
			if(s.equals("Attività Imminente")) {
				pnlInterno.show(sceltaPanel,"attivita imminente");
			}
			else{
				pnlInterno.show(sceltaPanel,"anomalia");
			}
		});
		
		aggiungiDestinatario.addActionListener(e -> {
		    String scelto = (String) listaDestinatari.getSelectedItem();
		    if (scelto != null) {
		        if (!modelloScelti.contains(scelto)) {
		            modelloScelti.addElement(scelto);
		        } else {
		            JOptionPane.showMessageDialog(this, "Destinatario già inserito", "", JOptionPane.WARNING_MESSAGE);
		        }
		    }
		});
		
		aggiungiTutti.addActionListener(e -> {
		    modelloScelti.clear();		    
		    int numeroElementi = listaDestinatari.getItemCount();
		    if (numeroElementi == 0) {
		        JOptionPane.showMessageDialog(this, "Non ci sono coltivatori da aggiungere", "Errore", JOptionPane.ERROR_MESSAGE);
		        return;
		    }

		    for (int i = 0; i < numeroElementi; i++) {
		        String nome = listaDestinatari.getItemAt(i);
		        modelloScelti.addElement(nome);
		    }
		});
		
		rimuovi.addActionListener(e -> {
		    int indice = listaScelti.getSelectedIndex();
		    if (indice != -1) {
		        modelloScelti.remove(indice);
		    } else {
		        JOptionPane.showMessageDialog(this, "Seleziona un nome dalla lista per rimuoverlo.", "", JOptionPane.INFORMATION_MESSAGE);
		    }
		});
		
		invia.addActionListener(e->{
			String esito=controller.CreaNotifica();
			if(esito.equals("ok")) {
				pulisciCampi();
				controller.caricaNotificheInviate();
				controller.mostraPanelInterno("visualizza notifiche");
			}else {
				gestisciErrori(esito);
			}
		});
		
		cancella.addActionListener(e->{
			pulisciCampi();
			controller.caricaNotificheInviate();
			controller.mostraPanelInterno("visualizza notifiche");
		});
	}
	
	public void setElencoColtivatori(ArrayList<String> elenco) {
		this.listaDestinatari.removeAllItems();
		 if (elenco != null && !elenco.isEmpty()) {
		        for (String nome : elenco) {
		            this.listaDestinatari.addItem(nome);
		        }
		    }else {
		    	JOptionPane.showMessageDialog(null, 
	                    "Non ci sono coltivatori nel sistema.", 
	                    "Errore Configurazione", 
	                    JOptionPane.WARNING_MESSAGE);  
		    }
	}
	
	public ArrayList<String> getNomiDestinatariSelezionati() {
	    ArrayList<String> listaNomi = new ArrayList<>();
	    for (int i = 0; i < modelloScelti.getSize(); i++) {
	        listaNomi.add(modelloScelti.getElementAt(i));
	    }
	    return listaNomi; 
	}
	
	public void messaggioErrore(JTextField campo, String messaggio) {
		campo.setBorder(BorderFactory.createLineBorder(Color.RED,1));
		campo.setToolTipText(messaggio);
		ToolTipManager.sharedInstance().setInitialDelay(0);
	}
	
	public void resetBordi() {
		Border bordo= UIManager.getBorder("TextField.border");
		cmpDescrizioneVeloce.setBorder(bordo);
		cmpDescrizioneVeloce.setToolTipText(null);
		cmpDescrizione.setBorder(bordo);
		cmpDescrizione.setToolTipText(null);
		cmpDataScadenza.setBorder(bordo);
		cmpDataScadenza.setToolTipText(null);	
		cmpEstensione.setBorder(bordo);
		cmpEstensione.setToolTipText(null);
		
	}
	
	public void pulisciCampi() {
	    cmpDescrizioneVeloce.setText("");
	    cmpDescrizione.setText("");
	    cmpDataScadenza.setText("");
	    cmpEstensione.setText("");
	    sceltaNotifica.setSelectedIndex(0);
	    cmpLivelloGravita.setSelectedIndex(0);   
	    modelloScelti.clear();
	    resetBordi();
	}
	
	public void gestisciErrori(String errore) {
		resetBordi();
		if(errore.equals("errore descrizione veloce")) {
			messaggioErrore(cmpDescrizioneVeloce, "Inserire una descrizione che non superi i 30 caratteri");
		}		
		if(errore.equals("errore descrizione")) {
			messaggioErroreTextArea(cmpDescrizione, "Non superare i 500 caratteri");
			
		}
		if(errore.equals("errore destinatari vuoti")) {
			messaggioErroreScroll(scrollScelti, "Selezionare almeno un destinatario");			
		}
		if(errore.equals("errore formato data")) {
			messaggioErrore(cmpDataScadenza, "Inserire una data nel formato YYYY-MM-DD");			
		}
		if(errore.equals("errore formato estensione")) {
			messaggioErrore(cmpEstensione, "Inserire un numero intero.");
		}		
	}
	
	public void messaggioErroreTextArea(JTextArea campo,String testo) {
		campo.setBorder(BorderFactory.createLineBorder(Color.RED,1));
		campo.setToolTipText(testo);
		ToolTipManager.sharedInstance().setInitialDelay(0);
	}
	
	public void messaggioErroreScroll(JScrollPane campo,String messaggio) {
		campo.setBorder(BorderFactory.createLineBorder(Color.RED,1));
		campo.setToolTipText(messaggio);
		ToolTipManager.sharedInstance().setInitialDelay(0);
	}

	public Controller getController() {
		return controller;
	}

	public String getCmpDescrizioneVeloce() {
		return cmpDescrizioneVeloce.getText().trim();
	}

	public String getCmpDescrizione() {
		return cmpDescrizione.getText().trim();
	}

	public JScrollPane getScrollText() {
		return scrollText;
	}

	public String getSceltaNotifica() {
		return sceltaNotifica.getSelectedItem().toString();
	}

	public String getCmpDataScadenza() {
		return cmpDataScadenza.getText().trim();
	}

	public String getCmpLivelloGravita() {
		return cmpLivelloGravita.getSelectedItem().toString();
	}

	public String getCmpEstensione() {
		return cmpEstensione.getText().trim();
	}

	public JButton getInvia() {
		return invia;
	}

	public JButton getCancella() {
		return cancella;
	}

	public JComboBox<String> getListaDestinatari() {
		return listaDestinatari;
	}

	public void setListaDestinatari(JComboBox<String> listaDestinatari) {
		this.listaDestinatari = listaDestinatari;
	}

	public JButton getAggiungiDestinatario() {
		return aggiungiDestinatario;
	}

	public void setAggiungiDestinatario(JButton aggiungiDestinatario) {
		this.aggiungiDestinatario = aggiungiDestinatario;
	}

	public JButton getAggiungiTutti() {
		return aggiungiTutti;
	}

	public void setAggiungiTutti(JButton aggiungiTutti) {
		this.aggiungiTutti = aggiungiTutti;
	}

	public DefaultListModel<String> getModelloScelti() {
		return modelloScelti;
	}

	public void setModelloScelti(DefaultListModel<String> modelloScelti) {
		this.modelloScelti = modelloScelti;
	}

	public JList<String> getListaScelti() {
		return listaScelti;
	}

	public void setListaScelti(JList<String> listaScelti) {
		this.listaScelti = listaScelti;
	}
	
	
	
}
