package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.Border;

import controller.Controller;

public class FinestraLogin extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	private JTextField inserisciUsername;
	private JPasswordField inserisciPassword;

	public FinestraLogin(Controller controller) {
		this.controller=controller;
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.setOpaque(false);
		Font fontGrande= new Font("Arial",Font.PLAIN,20);
		Dimension grandezza= new Dimension(180,30);
		inserisciUsername= new JTextField(20);
		inserisciPassword= new JPasswordField(20);
		JLabel username= new JLabel("Username:");
		username.setFont(fontGrande);
		JLabel password= new JLabel("Password:");
		password.setFont(fontGrande);
		JPanel pnlUsername= new JPanel();
		JPanel pnlPassword= new JPanel();
		JButton btnAccedi= new JButton("Accedi");
		btnAccedi.setMaximumSize(grandezza);
		JButton btnIndietro= new JButton("Indietro");
		btnIndietro.setMaximumSize(grandezza);
		
		inserisciUsername.setFont(fontGrande);
		inserisciPassword.setFont(fontGrande);
		pnlUsername.setLayout(new FlowLayout());
		pnlUsername.setOpaque(false);
		Dimension riga= new Dimension(300,30);
		Dimension panel= new Dimension(600,200);
		inserisciUsername.setPreferredSize(riga);
		inserisciPassword.setPreferredSize(riga);
		pnlUsername.setMaximumSize(panel);
		pnlUsername.add(username);
		pnlUsername.add(inserisciUsername);
		pnlPassword.setLayout(new FlowLayout());
		pnlPassword.setOpaque(false);
		pnlPassword.setMaximumSize(panel);
		pnlPassword.add(password);
		pnlPassword.add(inserisciPassword);
		add(Box.createVerticalGlue());
		add(pnlUsername);
		add(Box.createVerticalStrut(50));
		add(pnlPassword);
		add(Box.createVerticalStrut(50));
		add(btnAccedi);
		add(Box.createVerticalStrut(30));
		add(btnIndietro);
		add(Box.createVerticalGlue());
		btnAccedi.setAlignmentX(CENTER_ALIGNMENT);
		btnIndietro.setAlignmentX(CENTER_ALIGNMENT);
		
		btnAccedi.addActionListener(e->{
			controller.validaLogin();
		});
		btnIndietro.addActionListener(e->{
			pulisciCampi();
			controller.mostraPanel("prima pagina");
		});
		
		
		
		
	}
	
	public void erroreLogin() {
		inserisciUsername.setBorder(BorderFactory.createLineBorder(Color.RED,1));
		inserisciUsername.setToolTipText("Username o Password non corretto");
		ToolTipManager.sharedInstance().setInitialDelay(0);
		inserisciPassword.setBorder(BorderFactory.createLineBorder(Color.RED,1));
		inserisciPassword.setToolTipText("Username o password non corretto");
		ToolTipManager.sharedInstance().setInitialDelay(0);
	}
	
	public void nonTrovato() {
		inserisciUsername.setBorder(BorderFactory.createLineBorder(Color.RED,1));
		inserisciUsername.setToolTipText("Utente non trovato");
		ToolTipManager.sharedInstance().setInitialDelay(0);
		inserisciPassword.setBorder(BorderFactory.createLineBorder(Color.RED,1));
		inserisciPassword.setToolTipText("Utente non trovato");
		ToolTipManager.sharedInstance().setInitialDelay(0);
	}
	
	public void pulisciCampi() {
	    inserisciUsername.setText("");
	    inserisciPassword.setText("");
	    resetBordi();
	}
	
	public void resetBordi() {
		Border bordo= UIManager.getBorder("TextField.border");
		inserisciUsername.setBorder(bordo);
		inserisciUsername.setToolTipText(null);
		inserisciPassword.setBorder(bordo);
	    inserisciPassword.setToolTipText(null);
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
	
	public String getUsername() {
		String s= inserisciUsername.getText().trim();
		return s;
	}
	
	public char[] getPassword() {
		return inserisciPassword.getPassword();
	}
	
	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
	
}
