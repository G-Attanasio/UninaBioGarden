package view;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
	private JTextField cmpDataInizioIrrigazione;
	private JTextField cmpDataFineIrrigazione;
	private ArrayList<String> elencoColtivatori;
	private JComboBox<String> listaColtivatoriI;
	private JComboBox<String> listaColtivatoriR;
	private JComboBox<String> listaColtivatoriS;
	private JButton conferma;
	private JButton annullaAttivita;
	JScrollPane scrollLista;
	
	
	
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
		scrollLista= new JScrollPane(coltureAggiunte);
		scrollLista.setPreferredSize(new Dimension(100,50));
		pnlColture.add(sceltaColtura);
		pnlColture.add(cmpListaColture);
		pnlColture.add(aggiungiColtura);
		pnlColture.add(scrollLista);
		pnlInterno.add(pnlColture);
		
		JPanel pnlIrrigazione= new JPanel( new FlowLayout());
		JLabel sceltaIrrigazione= new JLabel("Scegli il metodo di irrigazione:");
		JLabel coltivatoreI= new JLabel("Coltivatore:");
		this.elencoColtivatori= new ArrayList<String>();
		listaColtivatoriI= new JComboBox<String>(elencoColtivatori.toArray(new String[0]));
		pnlIrrigazione.add(sceltaIrrigazione);
		pnlIrrigazione.add(metodiIrrigazione);
		pnlIrrigazione.add(coltivatoreI);
		pnlIrrigazione.add(listaColtivatoriI);
		pnlInterno.add(pnlIrrigazione);
		
		JPanel pnlDateIrrigazione= new JPanel(new FlowLayout());
		JLabel dataInizioIrrigazione= new JLabel("Data inizio irrigazione:");
		cmpDataInizioIrrigazione= new JTextField(10);
		JLabel dataFineIrrigazione= new JLabel("Data fine irrigazione:");
		cmpDataFineIrrigazione= new JTextField(10);
		pnlDateIrrigazione.add(dataInizioIrrigazione);
		pnlDateIrrigazione.add(cmpDataInizioIrrigazione);
		pnlDateIrrigazione.add(dataFineIrrigazione);
		pnlDateIrrigazione.add(cmpDataFineIrrigazione);
		pnlInterno.add(pnlDateIrrigazione);
		
		JPanel pnlBottoni= new JPanel (new FlowLayout());
		salva= new JButton("Salva");
		annulla= new JButton("Annulla");
		pnlBottoni.add(salva);
		pnlBottoni.add(annulla);
		pnlInterno.add(pnlBottoni);
		
		aggiungiColtura.addActionListener(e->{
			 colturaScelta = (String) cmpListaColture.getSelectedItem();
			    if (colturaScelta != null) {
			    	controller.caricaColtivatoriInAttivitaProgetto(colturaScelta);
			    	
			    } else {
			        JOptionPane.showMessageDialog(null, "Seleziona prima una coltura!");
			    }
		});
		
		salva.addActionListener(e->{
			String esito= controller.salvaProgetto();
			
			if(esito.equals("ok")) {
				JOptionPane.showMessageDialog(this, 
			            "Salvataggio pianificazione avvenuta con successo", 
			            "Operazione Completata", 
			            JOptionPane.INFORMATION_MESSAGE);
				pulisciCampi();
				controller.mostraPanelInterno("visualizza lotti");
			}
			else {				
				gestisciErroriProgetto(esito);
			}
		});
		
		annulla.addActionListener(e->{
			pulisciCampi();
			controller.annullaCreazioneProgetto();
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
		listaColtivatoriS= new JComboBox<String>(nomiColtivatori.toArray(new String[0]));
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
		listaColtivatoriR= new JComboBox<String>(nomiColtivatori.toArray(new String[0]));
		pnlDateR.add(dataInizioRaccolta);
		pnlDateR.add(cmpDataInizioRaccolta);
		pnlDateR.add(dataFineRaccolta);
		pnlDateR.add(cmpDataFineRaccolta);
		pnlDateR.add(coltivatoreR);
		pnlDateR.add(listaColtivatoriR);
		pnlpopup.add(pnlDateR);
		
		conferma= new JButton("Conferma");
		annullaAttivita= new JButton("Annulla");
		Object opzioni[]= {conferma,annullaAttivita};
		JOptionPane pannelloOpzioni = new JOptionPane(pnlpopup, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION,null,opzioni);
		JDialog finestra = pannelloOpzioni.createDialog(this, "Pianificazione "+nomeColtura);

	
		conferma.addActionListener(e -> {
	        	
	     String coltS = (String) listaColtivatoriS.getSelectedItem();
	     String coltR = (String) listaColtivatoriR.getSelectedItem();       	
	     String esito=controller.validaCreazioneAttivitaProgetto(nomeColtura);
	     
	     if(esito.equalsIgnoreCase("ok")) {
	    	 resetBordiAttivita();
	    	 cmpDataInizio.setEditable(false);
	    	    cmpDurata.setEditable(false);
	    	    cmpDataInizio.setBackground(Color.LIGHT_GRAY);
	    	    cmpDurata.setBackground(Color.LIGHT_GRAY);
	     modelloLista.addElement(nomeColtura); 
	        	        finestra.dispose();
	        	    }else {
	        	    	gestisciErroriAttivita(esito);
	        	    	pannelloOpzioni.setValue(JOptionPane.UNINITIALIZED_VALUE);	        	    	
	        	    }
	        });
		
		annullaAttivita.addActionListener(e->{
			finestra.setVisible(false); 
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
		cmpDataInizioIrrigazione.setBorder(bordo);
		cmpDataInizioIrrigazione.setToolTipText(null);
		cmpDataFineIrrigazione.setBorder(bordo);
		cmpDataFineIrrigazione.setToolTipText(null);
		listaColtivatoriI.setBorder(bordo);
		listaColtivatoriI.setToolTipText(null);
		metodiIrrigazione.setBorder(bordo);
		metodiIrrigazione.setToolTipText(null);
		scrollLista.setBorder(bordo);
		scrollLista.setToolTipText(null);
	}
	
	public void gestisciErroriAttivita(String errore) {
		resetBordiAttivita();
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
		if(errore.equals("durata numero intero")) {
			messaggioErrore(cmpDurata, "Inserire un numero intero");
		}
		if(errore.equals("errore data progetto")) {
			messaggioErrore(cmpDataInizio, "Inserire una data valida");
		}
		if(errore.equals("data progetto formato")) {
			messaggioErrore(cmpDataInizio, "Inserire una data nel formato YYYY-MM-DD");
		}
		if(errore.equals("errore sovr progetti")) {
			messaggioErrore(cmpDataInizio, "Il lotto è già impegnato in un altro progetto, selezionare periodo temporale differente");
			messaggioErrore(cmpDurata, "Il lotto è già impegnato in un altro progetto, selezionare periodo temporale differente");
		}
		if(errore.equals("errore <1.")) {
			messaggioErrore(cmpQuantitaSemi, "Inserire almeno un kilo di semi o non superare le 10 tonnellate");
		}
		if(errore.equals("errore non double quantità semi")) {
			messaggioErrore(cmpQuantitaSemi, "Inserire cifre numeriche");
		}
		if(errore.equals("errore datainizio semina")) {
			messaggioErrore(cmpDataInizioSemina, "Inserire una data valida");
		}
		if(errore.equals("errore datafine semina")) {
			messaggioErrore(cmpDataFineSemina, "Inserire una data successiva alla data di inizio");
		}
		if(errore.equals("errore formato semina")) {
			messaggioErrore(cmpDataInizioSemina, "Inserire una data nel formato YYYY-MM-DD");
			messaggioErrore(cmpDataFineSemina, "Inserire una data nel formato YYYY-MM-DD");
		}
		if(errore.equals("errore datainizio raccolta")) {
			messaggioErrore(cmpDataInizioRaccolta,"Inserire una data valida");
		}
		if(errore.equals("errore datafine raccolta")) {
			messaggioErrore(cmpDataFineRaccolta, "Inserire una data successiva a quella di inizio");
		}
		if(errore.equals("errore formato raccolta")) {
			messaggioErrore(cmpDataInizioRaccolta, "Inserire una data nel formato YYYY-MM-DD");
			messaggioErrore(cmpDataFineRaccolta, "Inserire una data nel formato YYYY-MM-DD");
		}
		if(errore.equals("errore non double quantita prevista raccolta")) {
			messaggioErrore(cmpQuantitaPrevista, "Inserire cifre numeriche");
		}
		if(errore.equals("colt semina sovr attivita")) {
			messaggioErrore(cmpDataInizioSemina, "Il coltivatore scelto ha attività che si sovrappongono a queste date");
			messaggioErrore(cmpDataFineSemina, "Il coltivatore scelto ha attività che si sovrappongono a queste date");
		}
		if(errore.equals("colt raccolta sovr attivita")) {
			messaggioErrore(cmpDataInizioRaccolta, "Il coltivatore scelto ha attività che si sovrappongono a queste date");
			messaggioErrore(cmpDataFineRaccolta, "Il coltivatore scelto ha attività che si sovrappongono a queste date");
		}
		if(errore.equals("errore semina sovr progetto")) {
			messaggioErrore(cmpDataInizioSemina, "L'intervallo temporale di quest'attività non rientra nell'intervallo temporale del progetto");
			messaggioErrore(cmpDataFineSemina, "L'intervallo temporale di quest'attività non rientra nell'intervallo temporale del progetto");
		}
		if(errore.equals("errore raccolta sovr progetto")) {
			messaggioErrore(cmpDataInizioRaccolta, "L'intervallo temporale di quest'attività non rientra nell'intervallo temporale del progetto");
			messaggioErrore(cmpDataFineRaccolta, "L'intervallo temporale di quest'attività non rientra nell'intervallo temporale del progetto");
		}
		if(errore.equals("errore coerenzaSeminaDurata")) {
			messaggioErrore(cmpDataFineSemina, "La coltura piantata non raggiunge la maturazione entro la fine del progetto");
		}
		if(errore.equals("dataInizioRaccoltaNonValida")) {
			messaggioErrore(cmpDataInizioRaccolta, "La raccolta non può cominciare prima della fine della semina");
		}
		if(errore.equals("errore meccanica montagna")) {
			messaggioErroreCombo(metodiRaccolta, "Raccolta meccanica non consentita su lotto a morfologia montuosa");
		}
		if(errore.equals("errore coltura db")) {
			System.out.println(errore);
		}
		if(errore.equals("errore generico db")) {
			System.out.println(errore);
		}
		
	}
	
	public void gestisciErroriProgetto(String errore) {
		resetBordi();
		if(errore.equals("errore datainizio irrigazione")) {
			messaggioErrore(cmpDataInizioIrrigazione, "Inserire una data valida");
		}
		if(errore.equals("errore datafine irrigazione")) {
			messaggioErrore(cmpDataFineIrrigazione, "Inserire una data successiva a quella di inizio");
		}
		if(errore.equals("errore formato date irrigazione")) {
			messaggioErrore(cmpDataInizioIrrigazione, "Inserire una data nel formato YYYY-MM-DD");
			messaggioErrore(cmpDataFineIrrigazione, "Inserire una data nel formato YYYY-MM-DD");
		}
		if(errore.equals("colt irrigazione sovr attivita")) {
			messaggioErrore(cmpDataInizioIrrigazione, "Il coltivatore scelto ha attività che si sovrappongono a queste date");
			messaggioErrore(cmpDataFineIrrigazione, "Il coltivatore scelto ha attività che si sovrappongono a queste date");
		}
		if(errore.equals("errore irrigazione sovr progetto")) {
			messaggioErrore(cmpDataInizioIrrigazione, "L'intervallo temporale di quest'attività non rientra nell'intervallo temporale del progetto");
			messaggioErrore(cmpDataFineIrrigazione, "L'intervallo temporale di quest'attività non rientra nell'intervallo temporale del progetto");
		}
		if(errore.equals("errore pendenza sommersione")) {
			messaggioErroreCombo(metodiIrrigazione, "Irrigazione a sommersione non consentita su lotti con morfologia collinare o montuosa");
		}		
		if(errore.equals("errore lista vuota")) {
			messaggioErroreScroll(scrollLista, "Aggiungere almeno una coltura al progetto");
		}
	}
	public void resetBordiAttivita() {
		Border bordo= UIManager.getBorder("TextField.border");		
		cmpNome.setBorder(bordo);
		cmpNome.setToolTipText(null);
		cmpDurata.setBorder(bordo);
		cmpDurata.setToolTipText(null);
		cmpDataInizio.setBorder(bordo);
		cmpDataInizio.setToolTipText(null);
		cmpDataInizioRaccolta.setBorder(bordo);
		cmpDataInizioRaccolta.setToolTipText(null);
		cmpDataInizioSemina.setBorder(bordo);
		cmpDataInizioSemina.setToolTipText(null);
		cmpDataFineSemina.setBorder(bordo);
		cmpDataFineSemina.setToolTipText(null);
		cmpQuantitaSemi.setBorder(bordo);
		cmpQuantitaSemi.setToolTipText(null);
		cmpQuantitaPrevista.setBorder(bordo);
		cmpQuantitaPrevista.setToolTipText(null);
	}
	
	public void pulisciCampi() {
	    cmpNome.setText("");
	    cmpDurata.setText("");
	    cmpDataInizio.setText("");
	    cmpDataInizioIrrigazione.setText("");
	    cmpDataFineIrrigazione.setText("");
	    modelloLista.clear();
	    resetBordi();
	}
	
	public void erroreCampiProgetto() {
		JOptionPane.showMessageDialog(null, "Inserire nome, data inizio e durata prima di aggiungere colture.");
	}
	
	public void setElencoColtivatori(ArrayList<String> elenco) {
		this.listaColtivatoriI.removeAllItems();
		 if (elenco != null && !elenco.isEmpty()) {
		        for (String nome : elenco) {
		            this.listaColtivatoriI.addItem(nome);
		        }
		    }else {	    	
	                JOptionPane.showMessageDialog(null, 
	                    "Non ci sono coltivatori nel sistema.", 
	                    "Errore Configurazione", 
	                    JOptionPane.WARNING_MESSAGE);            
		    }
	}
	
	public void messaggioErroreCombo(JComboBox<String> campo, String messaggio) {
		campo.setBorder(BorderFactory.createLineBorder(Color.RED,1));
		campo.setToolTipText(messaggio);
		ToolTipManager.sharedInstance().setInitialDelay(0);
	}
	
	public void messaggioErroreScroll(JScrollPane scroll, String messaggio) {
		scroll.setBorder(BorderFactory.createLineBorder(Color.RED,2));
		scroll.setToolTipText(messaggio);
		ToolTipManager.sharedInstance().setInitialDelay(0);
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

	public String getMetodiIrrigazione() {
		return metodiIrrigazione.getSelectedItem().toString();
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

	public String getCmpDataInizioIrrigazione() {
		return cmpDataInizioIrrigazione.getText().trim();
	}

	public String getCmpDataFineIrrigazione() {
		return cmpDataFineIrrigazione.getText().trim();
	}

	public String getListaColtivatoriR() {
		return listaColtivatoriR.getSelectedItem().toString();
	}

	public String getListaColtivatoriS() {
		return listaColtivatoriS.getSelectedItem().toString();
	}

	public String getListaColtivatoriI() {
		return listaColtivatoriI.getSelectedItem().toString();
	}
	
	
	
}
