package view;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import controller.Controller;

public class FinestraSceltaRuolo extends JPanel {

	private static final long serialVersionUID = 1L;

	private Controller controller;
	private String[] ruoli= {"PROPRIETARIO","COLTIVATORE","PROPRIETARIO/COLTIVATORE"};
	private JComboBox<String> sceltaRuolo= new JComboBox<>(ruoli);
	private JButton conferma;
	private JButton indietro;
	
	public FinestraSceltaRuolo(Controller controller) {
		this.controller=controller;
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		indietro= new JButton("Indietro");
		indietro.setAlignmentX(CENTER_ALIGNMENT);
		sceltaRuolo.setAlignmentX(CENTER_ALIGNMENT);
		sceltaRuolo.setMaximumSize(new Dimension(200,70));
		conferma= new JButton("Conferma");
		conferma.setAlignmentX(CENTER_ALIGNMENT);
		add(Box.createVerticalGlue());
		add(sceltaRuolo);
		add(Box.createVerticalStrut(70));
		add(conferma);
		add(Box.createVerticalStrut(70));
		add(indietro);
		add(Box.createVerticalGlue());
		
		conferma.addActionListener(e->{
			String scelta= (String) sceltaRuolo.getSelectedItem();
			controller.gestisciSceltaRuolo(scelta);
		});
		indietro.addActionListener(e->{
			controller.mostraPanel("prima pagina");
		});
		
	}
}
