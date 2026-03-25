package view;



import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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
	
	
	
	public FinestraCreaProgetto(Controller controller) {
		this.setController(controller);
		setLayout(new BorderLayout());
		JPanel pnlInterno= new JPanel();
		pnlInterno.setLayout(new BoxLayout(pnlInterno,BoxLayout.Y_AXIS));
		JScrollPane scroll= new JScrollPane(pnlInterno);
		add(scroll,BorderLayout.CENTER);
		
		JPanel pnlNomeStagione= new JPanel(new FlowLayout());
		JLabel nome= new JLabel("Scegli il nome:");
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
		
		
		
		
		
		
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
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
