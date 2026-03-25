package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import controller.Controller;

public class FinestraProprietarioColtivatore extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	private JButton creaProgetto;
	//private JButton creaAttivita;
	private JButton visualizzaLotti;
	private JButton visualizzaProgetti;
	private JButton creaNotifiche;
	private JButton esci;
	private JButton visualizzaAttivita;
	private JButton visualizzaNotifiche;
	private JButton visualizzaReport;
	
	public FinestraProprietarioColtivatore(Controller controller) {
		this.controller=controller;
		// lato nord
		setLayout(new BorderLayout());
		JPanel pnlNord= new JPanel(new FlowLayout());
		pnlNord.setBackground(new Color(245,235,220));
		Dimension area= new Dimension(400,150);
		pnlNord.setPreferredSize(area);
		add(pnlNord, BorderLayout.NORTH);
		// lato ovest
		JPanel pnlOvest= new JPanel();
		creaProgetto= new JButton("Crea progetto");
		creaProgetto.setAlignmentX(CENTER_ALIGNMENT);
		//creaAttivita= new JButton("Crea attività");
		//creaAttivita.setAlignmentX(CENTER_ALIGNMENT);
		visualizzaAttivita= new JButton("Le mie attività");
		visualizzaAttivita.setAlignmentX(CENTER_ALIGNMENT);
		visualizzaLotti= new JButton("I miei lotti");
		visualizzaLotti.setAlignmentX(CENTER_ALIGNMENT);
		visualizzaProgetti= new JButton("I miei progetti");
		visualizzaProgetti.setAlignmentX(CENTER_ALIGNMENT);
		visualizzaReport= new JButton("Report raccolte");
		visualizzaReport.setAlignmentX(CENTER_ALIGNMENT);
		creaNotifiche= new JButton("Crea notifica");
		creaNotifiche.setAlignmentX(CENTER_ALIGNMENT);
		visualizzaNotifiche= new JButton("Visualizza Notifiche");
		visualizzaNotifiche.setAlignmentX(CENTER_ALIGNMENT);
		esci= new JButton("Esci");
		esci.setAlignmentX(CENTER_ALIGNMENT);
		pnlOvest.setBackground(new Color(200,230,200));
		pnlOvest.setLayout(new BoxLayout(pnlOvest,BoxLayout.Y_AXIS));
		pnlOvest.setPreferredSize(new Dimension(250,0));	
		pnlOvest.add(Box.createVerticalGlue());
		pnlOvest.add(creaProgetto);
		pnlOvest.add(Box.createVerticalStrut(40));
		//pnlOvest.add(creaAttivita);
		//pnlOvest.add(Box.createVerticalStrut(40));
		pnlOvest.add(visualizzaAttivita);
		pnlOvest.add(Box.createVerticalStrut(40));
		pnlOvest.add(visualizzaLotti);
		pnlOvest.add(Box.createVerticalStrut(40));
		pnlOvest.add(visualizzaProgetti);
		pnlOvest.add(Box.createVerticalStrut(40));
		pnlOvest.add(visualizzaReport);
		pnlOvest.add(Box.createVerticalStrut(40));
		pnlOvest.add(creaNotifiche);
		pnlOvest.add(Box.createVerticalStrut(40));
		pnlOvest.add(visualizzaNotifiche);
		pnlOvest.add(Box.createVerticalStrut(40));
		pnlOvest.add(esci);
		pnlOvest.add(Box.createVerticalGlue());
		add(pnlOvest,BorderLayout.WEST);
		
		JPanel pnlCard= new JPanel(new CardLayout());
		add(pnlCard,BorderLayout.CENTER);
		//pnlCard.add(pnlCard)
	}

	
}
