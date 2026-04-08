package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

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
		Font fontGrande= new Font("Arial",Font.PLAIN,20);
		Dimension grandezza= new Dimension(180,30);
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.setOpaque(false);
		indietro= new JButton("Indietro");
		indietro.setAlignmentX(CENTER_ALIGNMENT);
		indietro.setFont(fontGrande);
		indietro.setMinimumSize(grandezza);
		indietro.setMaximumSize(grandezza);
		indietro.setPreferredSize(grandezza);
		sceltaRuolo.setAlignmentX(CENTER_ALIGNMENT);
		sceltaRuolo.setMaximumSize(new Dimension(250,32));
		sceltaRuolo.setPreferredSize(new Dimension(250,32));
		sceltaRuolo.setMinimumSize(new Dimension(250,32));
		sceltaRuolo.setFont(new Font("Arial",Font.PLAIN,17));
		conferma= new JButton("Conferma");
		conferma.setAlignmentX(CENTER_ALIGNMENT);
		conferma.setFont(fontGrande);
		conferma.setPreferredSize(grandezza);
		conferma.setMaximumSize(grandezza);
		conferma.setMinimumSize(grandezza);
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
	 @Override
	    protected void paintComponent(Graphics g) {
	        Graphics2D g2d = (Graphics2D) g;
	        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);	        
	        int w = getWidth();
	        int h = getHeight();
	        Color c1 = new Color(150, 250, 150); 
	        Color c2 = new Color(200,200,200);
	        GradientPaint gp = new GradientPaint(0, 0, c1, 0, h, c2);	        
	        g2d.setPaint(gp);
	        g2d.fillRect(0, 0, w, h);	        
	        super.paintComponent(g); 
	    }

	public String getSceltaRuolo() {
		return sceltaRuolo.getSelectedItem().toString();
	}
	
}
