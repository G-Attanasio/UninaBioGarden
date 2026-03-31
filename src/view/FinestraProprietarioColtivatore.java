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
	private JButton attivitaAssegnate;
	private JButton visualizzaLotti;
	private JButton visualizzaProgetti;
	private JButton visualizzaColture;
	private JButton visualizzaReport;
	private JButton creaNotifiche;
	private JButton esci;
	private CardLayout layoutInterno;
	private JPanel pnlCard;
	private FinestraCreaProgetto finCreaProgetto;
	private FinestraAttivitaAssegnate finAttivitaAssegnate;	
	private FinestraVisualizzaLotti finVisualizzaLotti;
	private FinestraCreaLotto finCreaLotto;
	private FinestraVisualizzaProgetti finVisualizzaProgetti;
	private FinestraVisualizzaColture finVisualizzaColture;
	private FinestraReport finReport;
	private FinestraCreaNotifica finCreaNotifica;
	private FinestraVisualizzaNotifiche finVisualizzaNotifiche;
	private FinestraVisualizzaAttivita finVisualizzaAttivita;
	private JButton leMieAttivita;
	private JButton notificheRicevute;
	
	public FinestraProprietarioColtivatore(Controller controller) {
		this.controller=controller;
		finCreaProgetto= new FinestraCreaProgetto(controller);
		finAttivitaAssegnate= new FinestraAttivitaAssegnate(controller);
		finVisualizzaLotti= new FinestraVisualizzaLotti(controller);
		finCreaLotto= new FinestraCreaLotto(controller);
		finVisualizzaProgetti= new FinestraVisualizzaProgetti(controller);
		finVisualizzaColture= new FinestraVisualizzaColture(controller);
		finReport= new FinestraReport(controller);
		finCreaNotifica= new FinestraCreaNotifica(controller);
		finVisualizzaNotifiche= new FinestraVisualizzaNotifiche(controller);
		finVisualizzaAttivita=new FinestraVisualizzaAttivita(controller);
		setLayout(new BorderLayout());
		JPanel pnlNord= new JPanel(new FlowLayout());
		pnlNord.setBackground(new Color(245,235,220));
		Dimension area= new Dimension(400,150);
		pnlNord.setPreferredSize(area);
		add(pnlNord, BorderLayout.NORTH);
	
		JPanel pnlOvest= new JPanel();
		leMieAttivita= new JButton("Le mie attività");
		leMieAttivita.setAlignmentX(CENTER_ALIGNMENT);
		attivitaAssegnate= new JButton("Attività assegnate");
		attivitaAssegnate.setAlignmentX(CENTER_ALIGNMENT);
		visualizzaLotti= new JButton("I miei lotti");
		visualizzaLotti.setAlignmentX(CENTER_ALIGNMENT);
		visualizzaProgetti= new JButton("I miei progetti");
		visualizzaProgetti.setAlignmentX(CENTER_ALIGNMENT);
		visualizzaColture= new JButton("Lista colture");
		visualizzaColture.setAlignmentX(CENTER_ALIGNMENT);
		visualizzaReport= new JButton("Report raccolte");
		visualizzaReport.setAlignmentX(CENTER_ALIGNMENT);
		creaNotifiche= new JButton("Visualizza notifiche");
		creaNotifiche.setAlignmentX(CENTER_ALIGNMENT);
		notificheRicevute= new JButton("Notifiche ricevute");
		notificheRicevute.setAlignmentX(CENTER_ALIGNMENT);
		esci= new JButton("Esci");
		esci.setAlignmentX(CENTER_ALIGNMENT);
		pnlOvest.setBackground(new Color(200,230,200));
		pnlOvest.setLayout(new BoxLayout(pnlOvest,BoxLayout.Y_AXIS));
		pnlOvest.setPreferredSize(new Dimension(250,0));
		pnlOvest.add(Box.createVerticalGlue());
		
		pnlOvest.add(attivitaAssegnate);
		pnlOvest.add(Box.createVerticalStrut(40));
		pnlOvest.add(leMieAttivita);
		pnlOvest.add(Box.createVerticalStrut(40));
		pnlOvest.add(visualizzaLotti);
		pnlOvest.add(Box.createVerticalStrut(40));
		pnlOvest.add(visualizzaProgetti);
		pnlOvest.add(Box.createVerticalStrut(40));
		pnlOvest.add(visualizzaColture);
		pnlOvest.add(Box.createVerticalStrut(40));
		pnlOvest.add(creaNotifiche);
		pnlOvest.add(Box.createVerticalStrut(40));
		pnlOvest.add(notificheRicevute);
		pnlOvest.add(Box.createVerticalStrut(40));
		pnlOvest.add(esci);
		pnlOvest.add(Box.createVerticalGlue());
		add(pnlOvest,BorderLayout.WEST);
		
		layoutInterno= new CardLayout();
		pnlCard= new JPanel(layoutInterno);
		JPanel pnlBianco= new JPanel();
		pnlBianco.setBackground(Color.WHITE);
		pnlCard.add(pnlBianco,"prima carta");
		pnlCard.add(finCreaProgetto,"crea progetto");
		pnlCard.add(finAttivitaAssegnate,"attivita assegnate");
		pnlCard.add(finVisualizzaAttivita,"visualizza attivita");
		pnlCard.add(finVisualizzaLotti,"visualizza lotti");
		pnlCard.add(finCreaLotto,"crea lotto");
		pnlCard.add(finVisualizzaProgetti,"visualizza progetti");
		pnlCard.add(finReport,"report");
		pnlCard.add(finVisualizzaColture,"lista colture");
		pnlCard.add(finVisualizzaNotifiche,"visualizza notifiche");
		pnlCard.add(finCreaNotifica,"crea notifica");
		
		add(pnlCard,BorderLayout.CENTER);
		
		attivitaAssegnate.addActionListener(e->{
			controller.caricaAttivitaAssegnate();
			controller.mostraPanelInterno("attivita assegnate");
		});
		visualizzaLotti.addActionListener(e->{
			controller.caricaLotti();
			controller.mostraPanelInterno("visualizza lotti");
		});
		leMieAttivita.addActionListener(e->{
			controller.caricaAttivitaColtivatore();
			controller.mostraPanelInterno("visualizza attivita");
		});
		visualizzaProgetti.addActionListener(e->{
			controller.caricaIMieiProgetti();
			controller.mostraPanelInterno("visualizza progetti");
		});
		visualizzaColture.addActionListener(e->{
			controller.caricaColture();
			controller.mostraPanelInterno("lista colture");
		});
		creaNotifiche.addActionListener(e->{
			controller.caricaNotificheInviate();
			controller.mostraPanelInterno("visualizza notifiche");
		});
		notificheRicevute.addActionListener(e->{
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
	public FinestraCreaProgetto getFinCreaProgetto() {
		return finCreaProgetto;
	}
	public FinestraAttivitaAssegnate getFinAttivitaAssegnate() {
		return finAttivitaAssegnate;
	}
	public FinestraVisualizzaLotti getFinVisualizzaLotti() {
		return finVisualizzaLotti;
	}
	public FinestraCreaLotto getFinCreaLotto() {
		return finCreaLotto;
	}
	public FinestraVisualizzaProgetti getFinVisualizzaProgetti() {
		return finVisualizzaProgetti;
	}
	public FinestraVisualizzaColture getFinVisualizzaColture() {
		return finVisualizzaColture;
	}
	public FinestraReport getFinReport() {
		return finReport;
	}
	public FinestraCreaNotifica getFinCreaNotifica() {
		return finCreaNotifica;
	}
	public FinestraVisualizzaNotifiche getFinVisualizzaNotifiche() {
		return finVisualizzaNotifiche;
	}
	public FinestraVisualizzaAttivita getFinVisualizzaAttivita() {
		return finVisualizzaAttivita;
	}
	public void setFinVisualizzaAttivita(FinestraVisualizzaAttivita finVisualizzaAttivita) {
		this.finVisualizzaAttivita = finVisualizzaAttivita;
	}
	
}
