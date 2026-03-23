package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

import controller.Controller;

public class FinestraIscrizioneColtivatore extends JPanel {

	
	private static final long serialVersionUID = 1L;
 
	private Controller controller;
	private JTextField cmpNome;
	private JTextField cmpCognome;
	private JTextField cmpUsername;
	private JTextField cmpEmail;
	private JTextField cmpDataNascita;
	private JPasswordField cmpPassword;
	private JPasswordField cmpConfermaPassword;
	private JButton iscriviti;
	private JButton esci;
	
	public FinestraIscrizioneColtivatore(Controller controller) {
		
		this.controller=controller;
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		JPanel pnlNomeCognome = new JPanel(new FlowLayout());
		JLabel nome= new JLabel("Nome:");
		JLabel cognome= new JLabel("Cognome:");
		cmpNome= new JTextField(20);
		cmpCognome= new JTextField(20);
		pnlNomeCognome.add(nome);
		pnlNomeCognome.add(cmpNome);
		pnlNomeCognome.add(cognome);
		pnlNomeCognome.add(cmpCognome);
		pnlNomeCognome.setAlignmentX(CENTER_ALIGNMENT);
		pnlNomeCognome.setMaximumSize(new Dimension(800,400));
		JPanel pnlUsername= new JPanel (new FlowLayout());
		JLabel username= new JLabel ("Username:");
		cmpUsername= new JTextField(20);
		pnlUsername.add(username);
		pnlUsername.add(cmpUsername);
		pnlUsername.setAlignmentX(CENTER_ALIGNMENT);
		pnlUsername.setMaximumSize(new Dimension(800,50));
		JPanel pnlEmail= new JPanel (new FlowLayout());
		JLabel email= new JLabel("Email:");
		cmpEmail= new JTextField(20);
		pnlEmail.add(email);
		pnlEmail.add(cmpEmail);
		pnlEmail.setAlignmentX(CENTER_ALIGNMENT);
		pnlEmail.setMaximumSize(new Dimension(800,50));
		JPanel pnlData= new JPanel (new FlowLayout());
		JLabel dataNascita= new JLabel ("Data di nascita:");
		cmpDataNascita= new JTextField(20);
		pnlData.add(dataNascita);
		pnlData.add(cmpDataNascita);
		pnlData.setAlignmentX(CENTER_ALIGNMENT);
		pnlData.setMaximumSize(new Dimension(800,50));
		JPanel pnlPassword= new JPanel( new FlowLayout());
		JLabel password= new JLabel("Password:");
		JLabel confermaPassword= new JLabel("Conferma password:");
		cmpPassword= new JPasswordField(20);
		cmpConfermaPassword= new JPasswordField(20);
		iscriviti= new JButton("Iscriviti");
		iscriviti.setAlignmentX(CENTER_ALIGNMENT);
		esci= new JButton("Esci");
		esci.setAlignmentX(CENTER_ALIGNMENT);
		pnlPassword.add(password);
		pnlPassword.add(cmpPassword);
		pnlPassword.add(confermaPassword);
		pnlPassword.add(cmpConfermaPassword);
		pnlPassword.setAlignmentX(CENTER_ALIGNMENT);
		pnlPassword.setMaximumSize(new Dimension(800,50));
		add(Box.createVerticalStrut(200));
		add(pnlNomeCognome);
		add(Box.createVerticalStrut(10));
		add(pnlUsername);
		add(pnlEmail);
		add(pnlData);
		add(pnlPassword);
		add(iscriviti);
		add(Box.createVerticalStrut(50));
		add(esci);
		add(Box.createVerticalGlue());
		
		esci.addActionListener(e->{
			controller.mostraPanel("prima pagina");
		});
		iscriviti.addActionListener(e->{
			controller.validaUtenteIscrittoColtivatore();
		});
		
	}
	
	public void messaggioErrore(JTextField campo, String messaggio) {
		campo.setBorder(BorderFactory.createLineBorder(Color.RED,1));
		campo.setToolTipText(messaggio);
	}
	
	public void resetBordi() {
		Border bordo= UIManager.getBorder("TextField.border");
		cmpNome.setBorder(bordo);
		cmpNome.setToolTipText(null);
		cmpCognome.setBorder(bordo);
		cmpCognome.setToolTipText(null);
		cmpUsername.setBorder(bordo);
		cmpUsername.setToolTipText(null);
		cmpEmail.setBorder(bordo);
		cmpEmail.setToolTipText(null);
		cmpDataNascita.setBorder(bordo);
		cmpDataNascita.setToolTipText(null);
		cmpPassword.setBorder(bordo);
		cmpPassword.setToolTipText(null);
		cmpConfermaPassword.setBorder(bordo);
		cmpConfermaPassword.setToolTipText(null);
		
	}
	
	public String getNomeInviato() {
        return cmpNome.getText().trim();
    }

    public String getCognomeInviato() {
        return cmpCognome.getText().trim();
    }

    public String getUsernameInviato() {
        return cmpUsername.getText().trim();
    }

    public String getEmailInviata() {
        return cmpEmail.getText().trim();
    }

    public String getDataNascitaInviata() {
        return cmpDataNascita.getText().trim();
    }

    public String getPasswordInviata() {
        return new String (cmpPassword.getPassword());
    }

    public String getConfermaPasswordInviata() { 
        return new String(cmpConfermaPassword.getPassword());
    }

	public JTextField getCmpNome() {
		return cmpNome;
	}

	public JTextField getCmpCognome() {
		return cmpCognome;
	}

	public JTextField getCmpUsername() {
		return cmpUsername;
	}

	public JTextField getCmpEmail() {
		return cmpEmail;
	}

	public JPasswordField getCmpPassword() {
		return cmpPassword;
	}

	public JPasswordField getCmpConfermaPassword() {
		return cmpConfermaPassword;
	}

	public JTextField getCmpDataNascita() {
		return cmpDataNascita;
	}
    
	
}
