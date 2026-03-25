package view;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import controller.Controller;

public class FinestraCreaProgetto extends JPanel {

	private static final long serialVersionUID = 1L;

	private Controller controller;
	private String[] stagioni= {"Inverno-Primavera","Primavera-Estate","Autunno-Inverno"};
	private JComboBox<String> stagioneDiRiferimento= new JComboBox<>(stagioni);
	private JTextField cmpNome;
	private JTextField cmpDurata;
	private JTextField cmpDataInizio;
	private JTextField cmpDataFine;
	private ArrayList<String> listaColture;
	private JComboBox<String> cmpListaColture;
	private JButton aggiungiColtura;
	private JList coltureAggiunte;
	private String[] irrigazioni= {"ASPERSIONE","LOCALIZZATA","SCORRIMENTO","SOMMERSIONE"};
	private JComboBox<String> metodiIrrigazione= new JComboBox<>(irrigazioni);
	private JButton salva;
	private JButton annulla;
	private DefaultListModel<String> modelloLista;
	
	
	
	
	
	public FinestraCreaProgetto(Controller controller) {
		this.setController(controller);
		setLayout(new BorderLayout());
		JPanel pnlInterno= new JPanel();
		pnlInterno.setLayout(new BoxLayout(pnlInterno,BoxLayout.Y_AXIS));
		JScrollPane scroll= new JScrollPane(pnlInterno);
		add(scroll,BorderLayout.CENTER);
		
		JPanel pnlNomeStagione= new JPanel(new FlowLayout());
		JLabel nome= new JLabel("Scegli il nome del progetto:");
		cmpNome= new JTextField(20);
		JLabel stagione= new JLabel("Scegli periodo:");
		pnlNomeStagione.add(nome);
		pnlNomeStagione.add(cmpNome);
		pnlNomeStagione.add(stagione);
		pnlNomeStagione.add(stagioneDiRiferimento);
		pnlInterno.add(pnlNomeStagione);
		
		JPanel pnlDurDate= new JPanel (new FlowLayout());
		JLabel durata= new JLabel("Durata:");
		JLabel dataInizio= new JLabel("Data inizio:");
		JLabel dataFine= new JLabel("Data fine:");
		cmpDurata= new JTextField(4);
		cmpDataInizio= new JTextField(10);
		cmpDataFine= new JTextField(10);
		pnlDurDate.add(durata);
		pnlDurDate.add(cmpDurata);
		pnlDurDate.add(dataInizio);
		pnlDurDate.add(cmpDataInizio);
		pnlDurDate.add(dataFine);
		pnlDurDate.add(cmpDataFine);
		pnlInterno.add(pnlDurDate);
		
		JPanel pnlColture = new JPanel(new FlowLayout());
		JLabel sceltaColtura= new JLabel("Scegli una coltura:");
		cmpListaColture= new JComboBox<>();
		modelloLista= new DefaultListModel<>();
		aggiungiColtura= new JButton("Aggiungi coltura");
		coltureAggiunte= new JList<>(modelloLista);
		coltureAggiunte.setModel(modelloLista);
		pnlColture.add(sceltaColtura);
		pnlColture.add(cmpListaColture);
		pnlColture.add(aggiungiColtura);
		pnlColture.add(coltureAggiunte);
		pnlInterno.add(pnlColture);
		
		JPanel pnlIrrigazione= new JPanel( new FlowLayout());
		JLabel sceltaIrrigazione= new JLabel("Scegli il metodo di irrigazione:");
		pnlIrrigazione.add(sceltaIrrigazione);
		pnlIrrigazione.add(metodiIrrigazione);
		pnlInterno.add(pnlIrrigazione);
		
		JPanel pnlBottoni= new JPanel (new FlowLayout());
		salva= new JButton("Salva");
		annulla= new JButton("Annulla");
		pnlBottoni.add(salva);
		pnlBottoni.add(annulla);
		pnlInterno.add(pnlBottoni);
		
		aggiungiColtura.addActionListener(e->{
			 String colturaScelta = (String) cmpListaColture.getSelectedItem();
			    
			    if (colturaScelta != null) {
			    	pianificaAttivita(listaColture);
			    } else {
			        JOptionPane.showMessageDialog(null, "Seleziona prima una coltura!");
			    }
		});
		
		
	}
	
	public void pianificaAttivita(ArrayList<String> nomiColtivatori) {
		JPanel pnlpopup= new JPanel();
		pnlpopup.setLayout(new BoxLayout(pnlpopup,BoxLayout.Y_AXIS));
		
		JPanel pnlSeminaMetodo= new JPanel(new FlowLayout());
		JLabel metodoS= new JLabel("Metodo semina:");
		JComboBox<String> metodiSemina= new JComboBox<>(new String[]{"SPAGLIO","FILE","POSTARELLE","IDROSEMINA"});
		JLabel quantitaSemi= new JLabel("Quantità semi in kg:");
		JTextField cmpQuantitaSemi= new JTextField(5);
		pnlSeminaMetodo.add(metodoS);
		pnlSeminaMetodo.add(metodiSemina);
		pnlSeminaMetodo.add(quantitaSemi);
		pnlSeminaMetodo.add(cmpQuantitaSemi);
		pnlpopup.add(pnlSeminaMetodo);
		
		JPanel pnlDateS= new JPanel(new FlowLayout());
		JLabel dataInizioSemina= new JLabel("Data inizio:");
		JTextField cmpDataInizioSemina= new JTextField(10);
		JLabel dataFineSemina= new JLabel("Data fine:");
		JTextField cmpDataFineSemina= new JTextField(10);
		JLabel coltivatoreS= new JLabel("Coltivatore:");
		JComboBox<String> listaColtivatoriS= new JComboBox<String>(nomiColtivatori.toArray(new String[0]));
		pnlDateS.add(dataInizioSemina);
		pnlDateS.add(cmpDataInizioSemina);
		pnlDateS.add(dataFineSemina);
		pnlDateS.add(cmpDataFineSemina);
		pnlDateS.add(coltivatoreS);
		pnlDateS.add(listaColtivatoriS);
		pnlpopup.add(pnlDateS);
		
		JPanel pnlRaccoltaMetodo= new JPanel(new FlowLayout());
		JLabel metodoR= new JLabel("Metodo raccolta:");
		JComboBox<String> metodiRaccolta= new JComboBox<>(new String[] {"MANUALE","MECCANIZZATA","MECCANICA"});
		JLabel quantitaPrevista= new JLabel("Quantità prevista in kg:");
		JTextField cmpQuantitaPrevista= new JTextField(8);
		pnlRaccoltaMetodo.add(metodoR);
		pnlRaccoltaMetodo.add(metodiRaccolta);
		pnlRaccoltaMetodo.add(quantitaPrevista);
		pnlRaccoltaMetodo.add(cmpQuantitaPrevista);
		pnlpopup.add(pnlRaccoltaMetodo);
		
		JPanel pnlDateR= new JPanel(new FlowLayout());
		JLabel dataInizioRaccolta= new JLabel("Data inizio:");
		JTextField cmpDataInizioRaccolta= new JTextField(10);
		JLabel dataFineRaccolta= new JLabel("Data fine:");
		JTextField cmpDataFineRaccolta= new JTextField(10);
		JLabel coltivatoreR= new JLabel("Coltivatore:");
		JComboBox<String> listaColtivatoriR= new JComboBox<String>(nomiColtivatori.toArray(new String[0]));
		pnlDateR.add(dataInizioRaccolta);
		pnlDateR.add(cmpDataInizioRaccolta);
		pnlDateR.add(dataFineRaccolta);
		pnlDateR.add(cmpDataFineRaccolta);
		pnlDateR.add(coltivatoreR);
		pnlDateR.add(listaColtivatoriR);
		pnlpopup.add(pnlDateR);
		
		 JOptionPane pannelloOpzioni = new JOptionPane(pnlpopup, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		 JDialog finestra = pannelloOpzioni.createDialog(this, "Pianificazione Attività");
		
		//int result = JOptionPane.showConfirmDialog(null, pnlpopup, 
	      //       "Pianificazione Attività", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

	
		pannelloOpzioni.addPropertyChangeListener(e -> {
	        if (JOptionPane.VALUE_PROPERTY.equals(e.getPropertyName())) {
	            Object value = pannelloOpzioni.getValue();

	            if (value.equals(JOptionPane.OK_OPTION)) {
	            	
	    
	            	String quantitaS = cmpQuantitaSemi.getText();
	        	    String quantitaR = cmpQuantitaPrevista.getText();
	        	    String dInizioS = cmpDataInizioSemina.getText();
	        	    String dFineS = cmpDataFineSemina.getText();
	        	    String dInizioR = cmpDataInizioRaccolta.getText();
	        	    String dFineR = cmpDataFineRaccolta.getText();
	        	    String coltS = (String) listaColtivatoriS.getSelectedItem();
	        	    String coltR = (String) listaColtivatoriR.getSelectedItem();
	        	    String metS = (String) metodiSemina.getSelectedItem();
	        	    String metR = (String) metodiRaccolta.getSelectedItem();
	            	
	        	    
	        	    controller.validaAttivitaSeminaRaccolta(quantitaS, quantitaR, dInizioS, dFineS, dInizioR, dFineR, coltS, coltR, metS, metR);
	            }
	        	    else {
	        	    	finestra.dispose();
	        	    }
	            }
	        });
	}
	
	public void messaggioErrore(JTextField campo, String messaggio) {
		campo.setBorder(BorderFactory.createLineBorder(Color.RED,1));
		campo.setToolTipText(messaggio);
	}
	
	public void messaggioErroreBottone(JButton bottone, String messaggio) {
		bottone.setBorder(BorderFactory.createLineBorder(Color.RED,1));
		bottone.setToolTipText(messaggio);
	}
	
	public void aggiungiColturaLista(String testo) {
	    modelloLista.addElement(testo);
	}
		
	public void popolaColture(ArrayList<String> nomiColture) {
	    cmpListaColture.removeAllItems(); 
	    for (String nome : nomiColture) {
	        cmpListaColture.addItem(nome);
	    }
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public ArrayList<String> getListaColture() {
		return listaColture;
	}

	public void setListaColture(ArrayList<String> listaColture) {
		this.listaColture = listaColture;
	}

	public JButton getAggiungiColtura() {
		return aggiungiColtura;
	}

	public void setAggiungiColtura(JButton aggiungiColtura) {
		this.aggiungiColtura = aggiungiColtura;
	}

	public JComboBox<String> getCmpListaColture() {
		return cmpListaColture;
	}

	public void setCmpListaColture(JComboBox<String> cmpListaColture) {
		this.cmpListaColture = cmpListaColture;
	}

	public JList getColtureAggiunte() {
		return coltureAggiunte;
	}

	public void setColtureAggiunte(JList coltureAggiunte) {
		this.coltureAggiunte = coltureAggiunte;
	}

	public JComboBox<String> getMetodiIrrigazione() {
		return metodiIrrigazione;
	}

	public void setMetodiIrrigazione(JComboBox<String> metodiIrrigazione) {
		this.metodiIrrigazione = metodiIrrigazione;
	}

	public JButton getSalva() {
		return salva;
	}

	public void setSalva(JButton salva) {
		this.salva = salva;
	}

	public JButton getAnnulla() {
		return annulla;
	}

	public void setAnnulla(JButton annulla) {
		this.annulla = annulla;
	}

	public DefaultListModel<String> getModelloLista() {
		return modelloLista;
	}

	public void setModelloLista(DefaultListModel<String> modelloLista) {
		this.modelloLista = modelloLista;
	}
	
	/*btnIniziaProgetto.addActionListener(e -> {
	    int riga = tabella.getSelectedRow();
	    if (riga != -1) {
	        // Recuperiamo l'ID del lotto e magari la località per farla vedere nel form
	        int idLotto = (int) modello.getValueAt(riga, 0);
	        String localita = (String) modello.getValueAt(riga, 6);
	        
	        // Passiamo i dati al Controller
	        controller.preparaCreazioneProgetto(idLotto, localita);
	    } else {
	        JOptionPane.showMessageDialog(this, "Seleziona prima il lotto su cui vuoi lavorare!");
	    }
	});*/
	
}
