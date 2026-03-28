package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.concurrent.Flow;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
		scrollText= new JScrollPane(cmpDescrizione);
		pnlDescrizione.add(descrizione);
		pnlDescrizione.add(cmpDescrizione);
		
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

	public JComboBox<String> getSceltaNotifica() {
		return sceltaNotifica;
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
	
	
	
}
