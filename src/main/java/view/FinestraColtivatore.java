package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.Controller;
import dto.AttivitaDTO;
import dto.NotificaDTO;

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
		Font fontGrande= new Font("Segoe UI",Font.PLAIN,20);
		Dimension grandezza= new Dimension(220,27);
		finVisualizzaAttivita= new FinestraVisualizzaAttivita(controller);
		finVisualizzaNotifiche= new FinestraVisualizzaNotifiche(controller);
		// lato nord
		setLayout(new BorderLayout());
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
		pnlNord.setBackground(new Color(245,235,220));
		Dimension area= new Dimension(400,150);
		pnlNord.setPreferredSize(area);
		add(pnlNord, BorderLayout.NORTH);
		// lato ovest
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
		JLabel lblTitolo = new JLabel("U.B.G. Dashboard coltivatore");
		lblTitolo.setFont(new Font("SansSerif", Font.BOLD, 80));
		lblTitolo.setForeground(new Color(140, 140, 140)); 
		pnlNord.add(lblTitolo);
		visualizzaAttivita= new JButton("Le mie Attività");
		visualizzaAttivita= creaBottonePersonale("Le mie Attività");
		visualizzaAttivita.setFont(fontGrande);
		visualizzaNotifiche= new JButton("Notifiche ricevute");
		visualizzaNotifiche= creaBottonePersonale("Notifiche ricevute");
		visualizzaNotifiche.setFont(fontGrande);
		esci= new JButton("Esci");
		esci=creaBottonePersonale("Esci");
		esci.setFont(fontGrande);
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
	
	public void mostraMessaggio(String testo) {
		JOptionPane.showMessageDialog(this, testo);
	}

	public FinestraVisualizzaAttivita getFinVisualizzaAttivita() {
		return finVisualizzaAttivita;
	}

	public FinestraVisualizzaNotifiche getFinVisualizzaNotifiche() {
		return finVisualizzaNotifiche;
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
