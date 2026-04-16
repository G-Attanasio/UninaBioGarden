package view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import javax.swing.JTextField;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.Border;

import controller.Controller;
import dto.InputUtenteDTO;

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
		this.setOpaque(false);
		Dimension grandezza= new Dimension(180,30);
		Dimension pnl= new Dimension(1000,70);
		Dimension riga= new Dimension(300,25);
		Font fontGrande= new Font("Arial",Font.PLAIN,20);
		
		JPanel pnlNomeCognome = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlNomeCognome.setBorder(BorderFactory.createEmptyBorder(0,60,0,0));
		pnlNomeCognome.setOpaque(false);
		pnlNomeCognome.setMaximumSize(pnl);
		JLabel nome= new JLabel("Nome:");
		nome.setFont(fontGrande);
		JLabel cognome= new JLabel("Cognome:");
		cognome.setFont(fontGrande);
		cmpNome= new JTextField(20);
		cmpNome.setPreferredSize(riga);
		cmpNome.setFont(fontGrande);
		cmpCognome= new JTextField(20);
		cmpCognome.setPreferredSize(riga);
		cmpCognome.setFont(fontGrande);
		pnlNomeCognome.add(nome);
		pnlNomeCognome.add(cmpNome);
		pnlNomeCognome.add(cognome);
		pnlNomeCognome.add(cmpCognome);
		add(Box.createVerticalGlue());
		add(pnlNomeCognome);
		
		JPanel pnlUsername= new JPanel (new FlowLayout(FlowLayout.LEFT));
		pnlUsername.setBorder(BorderFactory.createEmptyBorder(0,60,0,0));
		pnlUsername.setOpaque(false);
		pnlUsername.setMaximumSize(pnl);
		JLabel username= new JLabel ("Username:");
		username.setFont(fontGrande);
		cmpUsername= new JTextField(20);
		cmpUsername.setPreferredSize(riga);
		cmpUsername.setFont(fontGrande);
		pnlUsername.add(username);
		pnlUsername.add(cmpUsername);
		add(Box.createVerticalStrut(30));
		add(pnlUsername);
		
		JPanel pnlEmail= new JPanel (new FlowLayout(FlowLayout.LEFT));
		pnlEmail.setBorder(BorderFactory.createEmptyBorder(0,60,0,0));
		pnlEmail.setOpaque(false);
		pnlEmail.setMaximumSize(pnl);
		JLabel email= new JLabel("Email:");
		email.setFont(fontGrande);
		cmpEmail= new JTextField(20);
		cmpEmail.setFont(fontGrande);
		cmpEmail.setPreferredSize(riga);
		pnlEmail.add(email);
		pnlEmail.add(cmpEmail);
		add(Box.createVerticalStrut(30));
		add(pnlEmail);
		
		JPanel pnlData= new JPanel (new FlowLayout(FlowLayout.LEFT));
		pnlData.setBorder(BorderFactory.createEmptyBorder(0,60,0,0));
		pnlData.setMaximumSize(pnl);
		pnlData.setOpaque(false);
		JLabel dataNascita= new JLabel ("Data di nascita:");
		dataNascita.setFont(fontGrande);
		cmpDataNascita= new JTextField(20);
		cmpDataNascita.setFont(fontGrande);
		cmpDataNascita.setPreferredSize(riga);
		pnlData.add(dataNascita);
		pnlData.add(cmpDataNascita);
		add(Box.createVerticalStrut(30));
		add(pnlData);
		
		JPanel pnlPassword= new JPanel( new FlowLayout(FlowLayout.LEFT));
		pnlPassword.setBorder(BorderFactory.createEmptyBorder(0,60,0,0));
		pnlPassword.setMaximumSize(pnl);
		pnlPassword.setOpaque(false);
		JLabel password= new JLabel("Password:");
		JLabel confermaPassword= new JLabel("Conferma password:");
		password.setFont(fontGrande);
		confermaPassword.setFont(fontGrande);
		cmpPassword= new JPasswordField(15);
		cmpPassword.setFont(fontGrande);
		cmpPassword.setPreferredSize(riga);
		cmpConfermaPassword= new JPasswordField(15);
		cmpConfermaPassword.setFont(fontGrande);
		cmpConfermaPassword.setPreferredSize(riga);
		pnlPassword.add(password);
		pnlPassword.add(cmpPassword);
		pnlPassword.add(confermaPassword);
		pnlPassword.add(cmpConfermaPassword);
		add(Box.createVerticalStrut(30));
		add(pnlPassword);
		
		JPanel pnlBottoni= new JPanel(new FlowLayout(FlowLayout.CENTER,80,10));
		pnlBottoni.setMaximumSize(pnl);
		pnlBottoni.setOpaque(false);
		iscriviti= new JButton("Iscriviti");
		iscriviti.setPreferredSize(grandezza);
		iscriviti.setMaximumSize(grandezza);
		iscriviti.setAlignmentX(CENTER_ALIGNMENT);
		esci= new JButton("Esci");
		esci.setPreferredSize(grandezza);
		esci.setMaximumSize(grandezza);
		esci.setAlignmentX(CENTER_ALIGNMENT);
		pnlBottoni.add(iscriviti);
		pnlBottoni.add(esci);
		add(Box.createVerticalStrut(30));
		add(pnlBottoni);
		add(Box.createVerticalGlue());
		
		
		
		esci.addActionListener(e->{
			pulisciCampi();
			controller.mostraPanel("prima pagina");
		});
		iscriviti.addActionListener(e->{
			controller.validaIscrizioneUtenteColtivatore(getInputUtenteDTO());
		});
	}
	
	public void messaggioErrore(JTextField campo, String messaggio) {
		campo.setBorder(BorderFactory.createLineBorder(Color.RED,1));
		campo.setToolTipText(messaggio);
		ToolTipManager.sharedInstance().setInitialDelay(0);
	}
	
	public void messaggioErroreBottone(JButton campo, String messaggio) {
		campo.setBorder(BorderFactory.createLineBorder(Color.RED,1));
		campo.setToolTipText(messaggio);
		ToolTipManager.sharedInstance().setInitialDelay(0);
	}
	
	public void gestisciErrori(ArrayList<String> errori) {
	    resetBordi();
	    for (String errore : errori) {
	        switch (errore) {
	            case "lettere nome":
	                messaggioErrore(cmpNome, "Il nome deve essere di sole lettere.");
	                break;
	            case "lunghezza nome":
	                messaggioErrore(cmpNome, "Inserire tra 1 e 30 caratteri.");
	                break;
	            case "lettere cognome":
	                messaggioErrore(cmpCognome, "Il cognome deve essere di sole lettere.");
	                break;
	            case "lunghezza cognome":
	                messaggioErrore(cmpCognome, "Inserire tra 1 e 30 caratteri.");
	                break;
	            case "lunghezza username":
	                messaggioErrore(cmpUsername, "Inserire tra 1 e 30 caratteri.");
	                break;
	            case "email non valida":
	                messaggioErrore(cmpEmail, "Email non valida: inserire @ e . in ordine corretto");
	                break;
	            case "lunghezza email":
	                messaggioErrore(cmpEmail, "Inserire tra 1 e 30 caratteri.");
	                break;
	            case "data nascita":
	                messaggioErrore(cmpDataNascita, "Devi avere tra 18 e 120 anni.");
	                break;
	            case "lunghezza password":
	            	messaggioErrore(cmpPassword, "Minimo 4 caratteri e massimo 30.");
	            	break;
	            case "password":
	            	messaggioErrore(cmpPassword, "Le password non coincidono");
	            	messaggioErrore(cmpConfermaPassword, "le password non coincidono");
	            	break;
	            case "formato data":
	            	messaggioErrore(cmpDataNascita, "Usa il formato YYYY-MM-DD (es. 1995-05-20)");
	            	break;
	        }
	    }
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
	
	public void pulisciCampi() {
	    cmpNome.setText("");
	    cmpCognome.setText("");
	    cmpUsername.setText("");
	    cmpEmail.setText("");
	    cmpDataNascita.setText("");
	    cmpPassword.setText("");
	    cmpConfermaPassword.setText("");
	    resetBordi();
	}
	
	public void mostraMessaggio(String testo) {
		JOptionPane.showMessageDialog(this, testo);
	}


	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public String getNome() {
		return cmpNome.getText().trim();
	}

	public void setCmpNome(JTextField cmpNome) {
		this.cmpNome = cmpNome;
	}
	
	public String getCognome() {
		return cmpCognome.getText().trim();
	}

	public void setCmpCognome(JTextField cmpCognome) {
		this.cmpCognome = cmpCognome;
	}

	public String getUsername() {
		return cmpUsername.getText().trim();
	}

	public void setCmpUsername(JTextField cmpUsername) {
		this.cmpUsername = cmpUsername;
	}

	public String getEmail() {
		return cmpEmail.getText().trim();
	}

	public void setCmpEmail(JTextField cmpEmail) {
		this.cmpEmail = cmpEmail;
	}

	public String getDataNascita() {
		return cmpDataNascita.getText().trim();
	}

	public void setCmpDataNascita(JTextField cmpDataNascita) {
		this.cmpDataNascita = cmpDataNascita;
	}

	public String getPassword() {
		return new String(cmpPassword.getPassword());
	}

	public void setCmpPassword(JPasswordField cmpPassword) {
		this.cmpPassword = cmpPassword;
	}

	public String getConfermaPassword() {
		return new String(cmpConfermaPassword.getPassword());
	}

	public void setCmpConfermaPassword(JPasswordField cmpConfermaPassword) {
		this.cmpConfermaPassword = cmpConfermaPassword;
	}

	public JButton getEsci() {
		return esci;
	}

	public void setEsci(JButton esci) {
		this.esci = esci;
	}

	public JTextField getCmpUsername() {
		return cmpUsername;
	}

	public JTextField getCmpEmail() {
		return cmpEmail;
	}

	public JTextField getCmpDataNascita() {
		return cmpDataNascita;
	}

	public JPasswordField getCmpPassword() {
		return cmpPassword;
	}

	public JPasswordField getCmpConfermaPassword() {
		return cmpConfermaPassword;
	}

	public JTextField getCmpNome() {
		return cmpNome;
	}

	public JTextField getCmpCognome() {
		return cmpCognome;
	}
	 public JButton getIscriviti() {
		return iscriviti;
	}
	 
	 public InputUtenteDTO getInputUtenteDTO() {
		    return new InputUtenteDTO(getNome(),getCognome(),getUsername(),getEmail(),getDataNascita(),getPassword(),getConfermaPassword());
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
}	 