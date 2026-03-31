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

public class FinestraColtivatore extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	private JButton visualizzaAttivita;
	private JButton visualizzaNotifiche;
	private JButton esci;
	private CardLayout layoutInterno;
	private JPanel pnlCard;
	private FinestraVisualizzaAttivita finVisualizzaAttivita;
	private FinestraVisualizzaNotifiche finVisualizzaNotifiche;
	
	public FinestraColtivatore( Controller controller) {
		this.controller=controller;
		finVisualizzaAttivita= new FinestraVisualizzaAttivita(controller);
		finVisualizzaNotifiche= new FinestraVisualizzaNotifiche(controller);
		// lato nord
		setLayout(new BorderLayout());
		JPanel pnlNord= new JPanel(new FlowLayout());
		pnlNord.setBackground(new Color(245,235,220));
		Dimension area= new Dimension(400,150);
		pnlNord.setPreferredSize(area);
		add(pnlNord, BorderLayout.NORTH);
		// lato ovest
		JPanel pnlOvest= new JPanel();
		visualizzaAttivita= new JButton("Le mie Attività");
		visualizzaAttivita.setAlignmentX(CENTER_ALIGNMENT);
		visualizzaNotifiche= new JButton("Visualizza Notifiche");
		visualizzaNotifiche.setAlignmentX(CENTER_ALIGNMENT);
		esci= new JButton("Esci");
		esci.setAlignmentX(CENTER_ALIGNMENT);
		pnlOvest.setBackground(new Color(200,230,200));
		pnlOvest.setLayout(new BoxLayout(pnlOvest,BoxLayout.Y_AXIS));
		pnlOvest.setPreferredSize(new Dimension(250,0));		
		pnlOvest.add(Box.createVerticalGlue());
		pnlOvest.add(visualizzaAttivita);
		pnlOvest.add(Box.createVerticalStrut(50));
		pnlOvest.add(visualizzaNotifiche);
		pnlOvest.add(Box.createVerticalStrut(50));
		pnlOvest.add(esci);
		pnlOvest.add(Box.createVerticalGlue());
				
		add(pnlOvest,BorderLayout.WEST);
		
		layoutInterno= new CardLayout();
		pnlCard= new JPanel(layoutInterno);
		JPanel pnlBianco= new JPanel();
		pnlBianco.setBackground(Color.WHITE);
		pnlCard.add(pnlBianco,"prima carta");
		pnlCard.add(finVisualizzaAttivita,"visualizza attivita");
		pnlCard.add(finVisualizzaNotifiche,"visualizza notifiche");
		add(pnlCard,BorderLayout.CENTER);
		visualizzaAttivita.addActionListener(e->{
			controller.caricaAttivitaColtivatore();
			controller.mostraPanelInterno("visualizza attivita");
		});
		
		visualizzaNotifiche.addActionListener(e->{
			controller.caricaNotificheRicevute();
			controller.mostraPanelInterno("visualizza notifiche");
		});
		
		esci.addActionListener(e->{
			controller.mostraPanelInterno("prima carta");
			controller.mostraPanel("prima pagina");
		});
	}
	
	public void mostraPanelInterno(String testo) {
		layoutInterno.show(pnlCard, testo);
	}

	public FinestraVisualizzaAttivita getFinVisualizzaAttivita() {
		return finVisualizzaAttivita;
	}

	public FinestraVisualizzaNotifiche getFinVisualizzaNotifiche() {
		return finVisualizzaNotifiche;
	}



	
	
	
}
