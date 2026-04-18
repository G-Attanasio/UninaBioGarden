package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
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
		leMieAttivita= new JButton("Le mie Attività");
		leMieAttivita= creaBottonePersonale("Le mie Attività");
		leMieAttivita.setFont(fontGrande);
		attivitaAssegnate= new JButton("Attività assegnate");
		attivitaAssegnate= creaBottonePersonale("Attività assegnate");
		attivitaAssegnate.setFont(fontGrande);
		visualizzaLotti= new JButton("I miei Lotti");
		visualizzaLotti=creaBottonePersonale("I miei Lotti");
		visualizzaLotti.setFont(fontGrande);
		visualizzaProgetti= new JButton("I miei progetti");
		visualizzaProgetti= creaBottonePersonale("I miei Progetti");
		visualizzaProgetti.setFont(fontGrande);
		visualizzaColture= new JButton("Lista colture");
		visualizzaColture= creaBottonePersonale("Lista colture");
		visualizzaColture.setFont(fontGrande);
		creaNotifiche= new JButton("Invia Notifica");
		creaNotifiche= creaBottonePersonale("Invia Notifica");
		creaNotifiche.setFont(fontGrande);
		notificheRicevute= new JButton("Notifiche ricevute");
		notificheRicevute=creaBottonePersonale("Notifiche ricevute");
		notificheRicevute.setFont(fontGrande);
		esci= new JButton("Esci");
		esci= creaBottonePersonale("Esci");
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
	
	public static JButton creaBottonePersonale(String testo) {
	    JButton bottone = new JButton(testo) {
	        @Override
	        protected void paintComponent(Graphics g) {
	            Graphics2D g2 = (Graphics2D) g.create();
	            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	            Color bianco = new Color(255, 255, 255); 
	            Color grigioMetallo = new Color(200, 205, 200);                
	            GradientPaint gp = new GradientPaint(0, 0, bianco,0, getHeight(), grigioMetallo);
	            
	            if (getModel().isRollover()) {
	                Color biancoMorbido = new Color(245, 255, 245); 
	                Color grigioChiaroHover = new Color(215, 220, 215);
	                gp = new GradientPaint(0, 0, biancoMorbido, 0, getHeight(), grigioChiaroHover);
	            } 
	            int round = getHeight(); 
	            g2.setPaint(gp);
	            g2.fillRoundRect(0, 0, getWidth(), getHeight(), round, round);
	            g2.setColor(new Color(150, 160, 150)); 
	            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, round, round);
	            g2.dispose();
	            super.paintComponent(g);
	        }
	    };
	    Dimension d = new Dimension(200, 32); 
	    bottone.setPreferredSize(d);
	    bottone.setMinimumSize(d);
	    bottone.setMaximumSize(d);
	    bottone.setContentAreaFilled(false);
	    bottone.setBorderPainted(false);
	    bottone.setFocusPainted(false);
	    bottone.setForeground(new Color(20, 20, 20));
	    bottone.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    bottone.setAlignmentX(CENTER_ALIGNMENT);
	    return bottone;
	}
	
}
