package view;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

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
		avanti= new JButton("Avanti");
		avanti.setAlignmentX(CENTER_ALIGNMENT);
		esci= new JButton("Esci");
		esci.setAlignmentX(CENTER_ALIGNMENT);
		pnlPassword.add(password);
		pnlPassword.add(cmpPassword);
		pnlPassword.add(confermaPassword);
		pnlPassword.add(cmpConfermaPassword);
		pnlPassword.setAlignmentX(CENTER_ALIGNMENT);
		pnlPassword.setMaximumSize(new Dimension(800,50));
		JPanel pnlAlmenoUnLotto= new JPanel(new FlowLayout());
		radioAlmenoUnLotto= new JRadioButton();
		JLabel almenoUnLotto= new JLabel("Spunta se hai almeno un lotto da dichiarare.");
		pnlAlmenoUnLotto.add(almenoUnLotto);
		pnlAlmenoUnLotto.add(radioAlmenoUnLotto);
		pnlAlmenoUnLotto.setAlignmentX(CENTER_ALIGNMENT);
		pnlAlmenoUnLotto.setMaximumSize(new Dimension(800,50));
		add(Box.createVerticalStrut(200));
		add(pnlNomeCognome);
		add(Box.createVerticalStrut(10));
		add(pnlUsername);
		add(pnlEmail);
		add(pnlData);
		add(pnlPassword);
		add(pnlAlmenoUnLotto);
		add(avanti);
		add(Box.createVerticalStrut(50));
		add(esci);
		add(Box.createVerticalGlue());
		
		esci.addActionListener(e->{
			controller.mostraPanel("prima pagina");
		});
		avanti.addActionListener(e->{
			controller.validaUtenteIscrittoColtivatore();
			controller.mostraPanel("crea lotto");
		});
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public JTextField getCmpNome() {
		return cmpNome;
	}

	public void setCmpNome(JTextField cmpNome) {
		this.cmpNome = cmpNome;
	}

	public JTextField getCmpCognome() {
		return cmpCognome;
	}

	public void setCmpCognome(JTextField cmpCognome) {
		this.cmpCognome = cmpCognome;
	}

	public JTextField getCmpUsername() {
		return cmpUsername;
	}

	public void setCmpUsername(JTextField cmpUsername) {
		this.cmpUsername = cmpUsername;
	}

	public JTextField getCmpEmail() {
		return cmpEmail;
	}

	public void setCmpEmail(JTextField cmpEmail) {
		this.cmpEmail = cmpEmail;
	}

	public JTextField getCmpDataNascita() {
		return cmpDataNascita;
	}

	public void setCmpDataNascita(JTextField cmpDataNascita) {
		this.cmpDataNascita = cmpDataNascita;
	}

	public JPasswordField getCmpPassword() {
		return cmpPassword;
	}

	public void setCmpPassword(JPasswordField cmpPassword) {
		this.cmpPassword = cmpPassword;
	}

	public JPasswordField getCmpConfermaPassword() {
		return cmpConfermaPassword;
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
