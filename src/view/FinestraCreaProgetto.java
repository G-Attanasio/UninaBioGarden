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
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.Border;

import controller.Controller;

public class FinestraCreaProgetto extends JPanel {

	private static final long serialVersionUID = 1L;

	private Controller controller;
	private String[] stagioni= {"Inverno-Primavera","Primavera-Estate","Autunno-Inverno"};
	private JComboBox<String> stagioneDiRiferimento= new JComboBox<>(stagioni);
	private JTextField cmpNome;
	private JTextField cmpDurata;
	private JTextField cmpDataInizio;
	private ArrayList<String> listaColture;
	private JComboBox<String> cmpListaColture;
	private JButton aggiungiColtura;
	private JList coltureAggiunte;
	private String[] irrigazioni= {"ASPERSIONE","LOCALIZZATA","SCORRIMENTO","SOMMERSIONE"};
	private JComboBox<String> metodiIrrigazione= new JComboBox<>(irrigazioni);
	private JButton salva;
	private JButton annulla;
	private DefaultListModel<String> modelloLista= new DefaultListModel<String>();
	private String colturaScelta;
	private JComboBox<String> metodiSemina= new JComboBox<>(new String[]{"SPAGLIO","FILE","POSTARELLE","IDROSEMINA"});
	private JTextField cmpQuantitaSemi;
	private JTextField cmpDataInizioSemina;
	private JTextField cmpDataFineSemina;
	private JComboBox<String> metodiRaccolta= new JComboBox<>(new String[] {"MANUALE","MECCANIZZATA","MECCANICA"});
	private JTextField cmpQuantitaPrevista;
	private JTextField cmpDataInizioRaccolta;
	private JTextField cmpDataFineRaccolta;
	private JButton conferma;
	private JButton annullaAttivita;
	
	
	
	public FinestraCreaProgetto(Controller controller) {
		this.controller=controller;
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
		JLabel durata= new JLabel("Durata del progetto:");
		JLabel dataInizio= new JLabel("Data di inizio progetto:");
		cmpDurata= new JTextField(4);
		cmpDataInizio= new JTextField(10);
		pnlDurDate.add(durata);
		pnlDurDate.add(cmpDurata);
		pnlDurDate.add(dataInizio);
		pnlDurDate.add(cmpDataInizio);
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
			 colturaScelta = (String) cmpListaColture.getSelectedItem();
			    if (colturaScelta != null) {
			    	controller.caricaColtivatoriInProgetto(colturaScelta);
			    	
			    } else {
			        JOptionPane.showMessageDialog(null, "Seleziona prima una coltura!");
			    }
		});
		
		
	}
	
	public void pianificaAttivita(ArrayList<String> nomiColtivatori, String nomeColtura) {
		
		JPanel pnlpopup= new JPanel();
		pnlpopup.setLayout(new BoxLayout(pnlpopup,BoxLayout.Y_AXIS));
		
		JPanel pnlSeminaMetodo= new JPanel(new FlowLayout());
		JLabel metodoS= new JLabel("Metodo semina:");
		JLabel quantitaSemi= new JLabel("Quantità semi in kg:");
		cmpQuantitaSemi= new JTextField(5);
		pnlSeminaMetodo.add(metodoS);
		pnlSeminaMetodo.add(metodiSemina);
		pnlSeminaMetodo.add(quantitaSemi);
		pnlSeminaMetodo.add(cmpQuantitaSemi);
		pnlpopup.add(pnlSeminaMetodo);
		
		JPanel pnlDateS= new JPanel(new FlowLayout());
		JLabel dataInizioSemina= new JLabel("Data inizio:");
		cmpDataInizioSemina= new JTextField(10);
		JLabel dataFineSemina= new JLabel("Data fine:");
		cmpDataFineSemina= new JTextField(10);
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
		
		JLabel quantitaPrevista= new JLabel("Quantità prevista in kg:");
		cmpQuantitaPrevista= new JTextField(8);
		pnlRaccoltaMetodo.add(metodoR);
		pnlRaccoltaMetodo.add(metodiRaccolta);
		pnlRaccoltaMetodo.add(quantitaPrevista);
		pnlRaccoltaMetodo.add(cmpQuantitaPrevista);
		pnlpopup.add(pnlRaccoltaMetodo);
		
		JPanel pnlDateR= new JPanel(new FlowLayout());
		JLabel dataInizioRaccolta= new JLabel("Data inizio:");
		cmpDataInizioRaccolta= new JTextField(10);
		JLabel dataFineRaccolta= new JLabel("Data fine:");
		cmpDataFineRaccolta= new JTextField(10);
		JLabel coltivatoreR= new JLabel("Coltivatore:");
		JComboBox<String> listaColtivatoriR= new JComboBox<String>(nomiColtivatori.toArray(new String[0]));
		pnlDateR.add(dataInizioRaccolta);
		pnlDateR.add(cmpDataInizioRaccolta);
		pnlDateR.add(dataFineRaccolta);
		pnlDateR.add(cmpDataFineRaccolta);
		pnlDateR.add(coltivatoreR);
		pnlDateR.add(listaColtivatoriR);
		pnlpopup.add(pnlDateR);
		
		conferma= new JButton("Conferma");
		annullaAttivita= new JButton("Annulla");
		Object opzioni[]= {conferma,annulla};
		JOptionPane pannelloOpzioni = new JOptionPane(pnlpopup, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION,null,opzioni);
		JDialog finestra = pannelloOpzioni.createDialog(this, "Pianificazione "+nomeColtura);

	
		conferma.addActionListener(e -> {
	       
	            	
	    
	            	
	        	    String coltS = (String) listaColtivatoriS.getSelectedItem();
	        	    String coltR = (String) listaColtivatoriR.getSelectedItem();
	        	    String metS = (String) metodiSemina.getSelectedItem();
	        	    String metR = (String) metodiRaccolta.getSelectedItem();
	            	
	        	    
	        	    String esito=controller.validaCreazioneProgetto( coltS, coltR,nomeColtura);
	        	    if(esito.equalsIgnoreCase("ok")) {
	        	    	modelloLista.addElement(nomeColtura); 
	        	        finestra.dispose();
	        	    }else {
	        	    	gestisciErrori(esito);
	        	    	pannelloOpzioni.setValue(JOptionPane.UNINITIALIZED_VALUE);
	        	    	JOptionPane.showMessageDialog(finestra, "Errore: " + esito, "Attenzione", JOptionPane.WARNING_MESSAGE);
	        	    }
	        });
		
		annulla.addActionListener(e->{
			finestra.dispose();
		});
		finestra.setVisible(true);
	}
	
	
	
	public void resetBordi() {
		Border bordo= UIManager.getBorder("TextField.border");
		cmpNome.setBorder(bordo);
		cmpNome.setToolTipText(null);
		cmpDurata.setBorder(bordo);
		cmpDurata.setToolTipText(null);
		cmpDataInizio.setBorder(bordo);
		cmpDataInizio.setToolTipText(null);	
	}
	
	public void gestisciErrori(String errore) {
		if(errore.equals("errore campi progetto")) {
			messaggioErrore(cmpDataInizio, "Inserire data");
			messaggioErrore(cmpNome, "Inserire nome");
			messaggioErrore(cmpDurata, "Inserire durata");
		}
		if(errore.equals("errore lunghezza nome")) {
			messaggioErrore(cmpNome, "Nome troppo lungo, massimo 30 caratteri");
		}
		if(errore.equals("errore durata")) {
			messaggioErrore(cmpDurata, "La durata deve essere compresa tra 1 e 180 giorni");
		}
	}
	
	public void erroreCampiProgetto() {
		JOptionPane.showMessageDialog(null, "Inserire nome, data inizio e durata prima di aggiungere colture.");
	}
	
	public void messaggioErrore(JTextField campo, String messaggio) {
		campo.setBorder(BorderFactory.createLineBorder(Color.RED,1));
		campo.setToolTipText(messaggio);
		ToolTipManager.sharedInstance().setInitialDelay(0);
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

	public String getCmpNome() {
		return cmpNome.getText().trim();
	}

	public String getCmpDurata() {
		return cmpDurata.getText().trim();
	}

	public String getCmpDataInizio() {
		return cmpDataInizio.getText().trim();
	}

	public String getStagioneDiRiferimento() {
		return stagioneDiRiferimento.getSelectedItem().toString();
	}

	public String[] getIrrigazioni() {
		return irrigazioni;
	}

	public String getColturaScelta() {
		return colturaScelta;
	}

	public String getMetodiSemina() {
		return metodiSemina.getSelectedItem().toString();
	}

	public String getCmpQuantitaSemi() {
		return cmpQuantitaSemi.getText().trim();
	}

	public String getCmpDataInizioSemina() {
		return cmpDataInizioSemina.getText().trim();
	}

	public String getCmpDataFineSemina() {
		return cmpDataFineSemina.getText().trim();
	}

	public String getMetodiRaccolta() {
		return metodiRaccolta.getSelectedItem().toString();
	}

	public String getCmpQuantitaPrevista() {
		return cmpQuantitaPrevista.getText().trim();
	}

	public String getCmpDataInizioRaccolta() {
		return cmpDataInizioRaccolta.getText().trim();
	}

	public String getCmpDataFineRaccolta() {
		return cmpDataFineRaccolta.getText().trim();
	}

	public JButton getConferma() {
		return conferma;
	}

	public JButton getAnnullaAttivita() {
		return annullaAttivita;
	}
	
	
	
}
