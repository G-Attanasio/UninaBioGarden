package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
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
		JButton btnAccedi= new JButton("Accedi");
		JButton btnRegistrati= new JButton("Registrati");
		JLabel lblTitolo= new JLabel("Unina Bio Garden");
		add(Box.createVerticalStrut(150));
		add(lblTitolo);
		add(Box.createVerticalStrut(40));
		add(btnRegistrati);
		add(Box.createVerticalStrut(50));
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
		
	}
	
	

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	
}
