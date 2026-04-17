package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;

public class PrimaPaginaPanel extends JPanel {

	
	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	
	public PrimaPaginaPanel(Controller controller) {
		this.controller=controller;
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.setOpaque(false);
	
		
		Dimension grandezza= new Dimension(220,40);
		JButton btnAccedi= new JButton("Accedi");
		btnAccedi=creaBottoneArrotondato("Accedi");
		
		JButton btnRegistrati= new JButton("Registrati");
		btnRegistrati= creaBottoneArrotondato("Registrati");
		
		
		JLabel lblTitolo= new JLabel("Unina Bio Garden") {
			@Override
		    protected void paintComponent(Graphics g) {
		        Graphics2D g2 = (Graphics2D) g.create();
		        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		        Font font = new Font("Serif", Font.BOLD, 50);
		        g2.setFont(font);
		        FontMetrics fm = g2.getFontMetrics();
		        int x = (getWidth() - fm.stringWidth(getText())) / 2;
		        int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
		        GradientPaint gp = new GradientPaint(0, 0, new Color(144, 238, 144), 0, getHeight(), new Color(14, 119, 14));
		        g2.setPaint(gp);
		        g2.drawString(getText(), x, y);
		        g2.dispose();
		    }
		};
		lblTitolo.setMinimumSize(new Dimension(1000,180));
		lblTitolo.setMaximumSize(new Dimension(1000,180));
		lblTitolo.setPreferredSize(new Dimension(1000,180));
		btnAccedi.setMaximumSize(grandezza);
		btnRegistrati.setMaximumSize(grandezza);
		add(Box.createVerticalStrut(120));
		add(lblTitolo);
		add(Box.createVerticalStrut(60));
		add(btnAccedi);
		add(Box.createVerticalStrut(50));
		add(btnRegistrati);
		add(Box.createVerticalGlue());
		btnAccedi.setAlignmentX(CENTER_ALIGNMENT);
		btnRegistrati.setAlignmentX(CENTER_ALIGNMENT);
		lblTitolo.setAlignmentX(CENTER_ALIGNMENT);
		
		btnAccedi.addActionListener(e->{
			controller.mostraPanel("login");
		});
		btnRegistrati.addActionListener(e->{
			controller.mostraPanel("scelta ruolo");
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
	
	 public JButton creaBottoneArrotondato(String testo) {
		    JButton bottone = new JButton(testo) {
		        private static final long serialVersionUID = 1L;
		        @Override
		        protected void paintComponent(Graphics g) {
		            Graphics2D gr = (Graphics2D) g.create();
		            gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		            gr.setColor(new Color(34, 139, 34));
		            gr.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);	            
		            gr.dispose();
		            super.paintComponent(g);
		        }
		    };
		    bottone.setContentAreaFilled(false); 
		    bottone.setBorderPainted(false);    
		    bottone.setFocusPainted(false);     
		    bottone.setForeground(Color.WHITE);  
		    bottone.setFont(new Font("SansSerif", Font.BOLD, 15));
		    bottone.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)); 

		    return bottone;
		}

	

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	
}
