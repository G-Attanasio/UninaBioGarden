package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.Controller;

public class FinestraLogin extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	private JTextField inserisciUsername;
	private JPasswordField inserisciPassword;

	public FinestraLogin(Controller controller) {
		this.controller=controller;
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		inserisciUsername= new JTextField(20);
		inserisciPassword= new JPasswordField(20);
		JLabel username= new JLabel("Username:");
		JLabel password= new JLabel("Password:");
		JPanel pnlUsername= new JPanel();
		JPanel pnlPassword= new JPanel();
		JButton btnAccedi= new JButton("Accedi");
		Font fontGrande= new Font("Arial",Font.PLAIN,20);
		inserisciUsername.setFont(fontGrande);
		inserisciPassword.setFont(fontGrande);
		pnlUsername.setLayout(new FlowLayout());
		Dimension riga= new Dimension(300,30);
		Dimension panel= new Dimension(600,200);
		inserisciUsername.setPreferredSize(riga);
		inserisciPassword.setPreferredSize(riga);
		pnlUsername.setMaximumSize(panel);
		pnlUsername.add(username);
		pnlUsername.add(inserisciUsername);
		pnlPassword.setLayout(new FlowLayout());
		pnlPassword.setMaximumSize(panel);
		pnlPassword.add(password);
		pnlPassword.add(inserisciPassword);
		add(Box.createVerticalGlue());
		add(pnlUsername);
		add(Box.createVerticalStrut(50));
		add(pnlPassword);
		add(Box.createVerticalStrut(50));
		add(btnAccedi);
		add(Box.createVerticalGlue());
		btnAccedi.setAlignmentX(CENTER_ALIGNMENT);
		
		btnAccedi.addActionListener(e->{
			controller.validaLogin();
		});
		
		
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
