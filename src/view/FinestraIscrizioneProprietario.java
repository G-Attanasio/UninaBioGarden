package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

import controller.Controller;

public class FinestraIscrizioneProprietario extends JPanel{

	private static final long serialVersionUID = 1L;

	private Controller controller;
	private JTextField cmpNome;
	private JTextField cmpCognome;
	private JTextField cmpUsername;
	private JTextField cmpEmail;
	private JTextField cmpDataNascita;
	private JPasswordField cmpPassword;
	private JPasswordField cmpConfermaPassword;
	private JButton avanti;
	private JButton esci;
	private JRadioButton radioAlmenoUnLotto;
	
	public FinestraIscrizioneProprietario(Controller controller) {
		this.controller=controller;
		setLayout(new BorderLayout());
		JPanel pnlNord= new JPanel(new FlowLayout());
		JPanel pnlSud= new JPanel (new FlowLayout());
		JPanel pnlEst= new JPanel( new FlowLayout());
		JPanel pnlOvest= new JPanel( new FlowLayout());
		JPanel pnlCenter= new JPanel();
		pnlCenter.setLayout(new BoxLayout(pnlCenter,BoxLayout.Y_AXIS));
		pnlNord.setBackground(Color.GREEN);
		pnlNord.setPreferredSize(new Dimension(600,100));
		pnlSud.setBackground(Color.GREEN);
		pnlSud.setPreferredSize(new Dimension(500,100));
		pnlEst.setBackground(Color.GREEN);
		pnlEst.setPreferredSize(new Dimension(100,500));
		pnlOvest.setBackground(Color.GREEN);
		pnlOvest.setPreferredSize(new Dimension(100,500));
		add(pnlNord,BorderLayout.NORTH);
		add(pnlSud,BorderLayout.SOUTH);
		add(pnlEst,BorderLayout.EAST);
		add(pnlOvest,BorderLayout.WEST);
		add(pnlCenter,BorderLayout.CENTER);
		Dimension riga= new Dimension(100,30);
		Font fontGrande= new Font("Arial",Font.PLAIN,20);
		
		JPanel pnlNomeCognome = new JPanel(new FlowLayout());
		JLabel nome= new JLabel("Nome:");
		JLabel cognome= new JLabel("Cognome:");
		cmpNome= new JTextField(20);
		cmpCognome= new JTextField(20);
		pnlNomeCognome.add(nome);
		pnlNomeCognome.add(cmpNome);
		pnlNomeCognome.add(cognome);
		pnlNomeCognome.add(cmpCognome);
		pnlCenter.add(pnlNomeCognome);
		
		JPanel pnlUsername= new JPanel (new FlowLayout());
		JLabel username= new JLabel ("Username:");
		cmpUsername= new JTextField(20);
		pnlUsername.add(username);
		pnlUsername.add(cmpUsername);
		pnlCenter.add(pnlUsername);
		
		JPanel pnlEmail= new JPanel (new FlowLayout());
		JLabel email= new JLabel("Email:");
		cmpEmail= new JTextField(20);
		pnlEmail.add(email);
		pnlEmail.add(cmpEmail);
		pnlCenter.add(pnlEmail);
		
		JPanel pnlData= new JPanel (new FlowLayout());
		JLabel dataNascita= new JLabel ("Data di nascita:");
		cmpDataNascita= new JTextField(20);
		pnlData.add(dataNascita);
		pnlData.add(cmpDataNascita);
		pnlCenter.add(pnlData);
		
		JPanel pnlPassword= new JPanel( new FlowLayout());
		JLabel password= new JLabel("Password:");
		JLabel confermaPassword= new JLabel("Conferma password:");
		cmpPassword= new JPasswordField(20);
		cmpConfermaPassword= new JPasswordField(20);
		pnlPassword.add(password);
		pnlPassword.add(cmpPassword);
		pnlPassword.add(confermaPassword);
		pnlPassword.add(cmpConfermaPassword);
		pnlCenter.add(pnlPassword);
		
		JPanel pnlBottoni= new JPanel(new FlowLayout());
		avanti= new JButton("Avanti");
		avanti.setAlignmentX(CENTER_ALIGNMENT);
		esci= new JButton("Esci");
		esci.setAlignmentX(CENTER_ALIGNMENT);
		pnlBottoni.add(avanti);
		pnlBottoni.add(esci);
		pnlCenter.add(pnlBottoni);
		
		esci.addActionListener(e->{
			controller.mostraPanel("prima pagina");
		});
		avanti.addActionListener(e->{
			controller.validaIscrizioneUtenteProprietario();
			controller.mostraPanel("crea lotto");
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

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public String getCmpNome() {
		return cmpNome.getText().trim();
	}

	public void setCmpNome(JTextField cmpNome) {
		this.cmpNome = cmpNome;
	}
	
	public String getCmpCognome() {
		return cmpCognome.getText().trim();
	}

	public void setCmpCognome(JTextField cmpCognome) {
		this.cmpCognome = cmpCognome;
	}

	public String getCmpUsername() {
		return cmpUsername.getText().trim();
	}

	public void setCmpUsername(JTextField cmpUsername) {
		this.cmpUsername = cmpUsername;
	}

	public String getCmpEmail() {
		return cmpEmail.getText().trim();
	}

	public void setCmpEmail(JTextField cmpEmail) {
		this.cmpEmail = cmpEmail;
	}

	public String getCmpDataNascita() {
		return cmpDataNascita.getText().trim();
	}

	public void setCmpDataNascita(JTextField cmpDataNascita) {
		this.cmpDataNascita = cmpDataNascita;
	}

	public String getCmpPassword() {
		return new String(cmpPassword.getPassword());
	}

	public void setCmpPassword(JPasswordField cmpPassword) {
		this.cmpPassword = cmpPassword;
	}

	public String getCmpConfermaPassword() {
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

	public JButton getAvanti() {
		return avanti;
	}

	public void setAvanti(JButton avanti) {
		this.avanti = avanti;
	}

	public JRadioButton getRadioAlmenoUnLotto() {
		return radioAlmenoUnLotto;
	}

	public void setRadioAlmenoUnLotto(JRadioButton almenoUnLotto) {
		this.radioAlmenoUnLotto = almenoUnLotto;
	}
}
