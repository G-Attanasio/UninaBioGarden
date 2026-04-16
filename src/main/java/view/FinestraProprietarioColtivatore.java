package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
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
		Font fontGrande= new Font("Segoe UI",Font.PLAIN,20);
		Dimension grandezza= new Dimension(220,27);
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
		this.setOpaque(false);
		JPanel pnlNord= new JPanel(new FlowLayout(FlowLayout.LEFT)) {
			@Override
			protected void paintComponent(Graphics g) {
			    Graphics2D g2d = (Graphics2D) g;
			    int h = getHeight();
			    int w = getWidth();		    
			    Color a = new Color(235, 235, 210);
			    Color b = new Color(230, 220, 190);			    
			    GradientPaint gp = new GradientPaint(0, 0, a, 0, h, b);
			    g2d.setPaint(gp);
			    g2d.fillRect(0, 0, w, h);
			}
		};
		pnlNord.setOpaque(false);
		Dimension area= new Dimension(400,150);
		pnlNord.setPreferredSize(area);
		add(pnlNord, BorderLayout.NORTH);
	
		JPanel pnlOvest= new JPanel() {
			@Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        Graphics2D g2d = (Graphics2D) g;
		        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		        int w = getWidth();
		        int h = getHeight();		       
		        Color a = new Color(150, 240, 150); 
		        Color b = new Color(0, 0, 0);
		        GradientPaint gp = new GradientPaint(0, 0, a, h, 0, b);
		        g2d.setPaint(gp);
		        g2d.fillRect(0, 0, w, h);
		    }
		};
		
		JLabel lblTitolo = new JLabel("U.B.G.-Dashboard");
		lblTitolo.setFont(new Font("SansSerif", Font.BOLD, 80));
		lblTitolo.setForeground(new Color(140, 140, 140)); 
		pnlNord.add(lblTitolo);
		leMieAttivita= new JButton("Le mie attività");
		leMieAttivita.setAlignmentX(CENTER_ALIGNMENT);
		leMieAttivita.setPreferredSize(grandezza);
		leMieAttivita.setFont(fontGrande);
		attivitaAssegnate= new JButton("Attività assegnate");
		attivitaAssegnate.setAlignmentX(CENTER_ALIGNMENT);
		attivitaAssegnate.setPreferredSize(grandezza);
		attivitaAssegnate.setFont(fontGrande);
		visualizzaLotti= new JButton("I miei lotti");
		visualizzaLotti.setAlignmentX(CENTER_ALIGNMENT);
		visualizzaLotti.setPreferredSize(grandezza);
		visualizzaLotti.setFont(fontGrande);
		visualizzaProgetti= new JButton("I miei progetti");
		visualizzaProgetti.setAlignmentX(CENTER_ALIGNMENT);
		visualizzaProgetti.setPreferredSize(grandezza);
		visualizzaProgetti.setFont(fontGrande);
		visualizzaColture= new JButton("Lista colture");
		visualizzaColture.setAlignmentX(CENTER_ALIGNMENT);
		visualizzaColture.setPreferredSize(grandezza);
		visualizzaColture.setFont(fontGrande);
		visualizzaReport= new JButton("Report raccolte");
		visualizzaReport.setAlignmentX(CENTER_ALIGNMENT);
		visualizzaReport.setPreferredSize(grandezza);
		visualizzaReport.setFont(fontGrande);
		creaNotifiche= new JButton("Visualizza notifiche");
		creaNotifiche.setAlignmentX(CENTER_ALIGNMENT);
		creaNotifiche.setPreferredSize(grandezza);
		creaNotifiche.setFont(fontGrande);
		notificheRicevute= new JButton("Notifiche ricevute");
		notificheRicevute.setAlignmentX(CENTER_ALIGNMENT);
		notificheRicevute.setPreferredSize(grandezza);
		notificheRicevute.setFont(fontGrande);
		esci= new JButton("Esci");
		esci.setAlignmentX(CENTER_ALIGNMENT);
		esci.setPreferredSize(grandezza);
		esci.setFont(fontGrande);
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
			controller.caricaProgetti();
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
