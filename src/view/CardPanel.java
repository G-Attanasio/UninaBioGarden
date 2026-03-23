package view;

import java.awt.CardLayout;
import javax.swing.JPanel;

import controller.Controller;

public class CardPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	private CardLayout cardLayout;
	private FinestraLogin finestraLogin;
	private PrimaPaginaPanel primaPagina;
	private FinestraProprietario finestraProprietario;
	private FinestraColtivatore finestraColtivatore;
	private FinestraProprietarioColtivatore finestraProprietarioColtivatore;
	private FinestraSceltaRuolo finestraRuolo;
	private FinestraIscrizioneProprietario finestraIscrizioneProprietario;
	private FinestraIscrizioneColtivatore finestraIscrizioneColtivatore;
	
	public CardPanel(Controller controller) {
		this.controller=controller;
		this.cardLayout= new CardLayout();
		this.primaPagina= new PrimaPaginaPanel(controller);
		this.finestraLogin= new FinestraLogin(controller);
		this.finestraProprietario= new FinestraProprietario(controller);
		this.finestraColtivatore= new FinestraColtivatore(controller);
		this.finestraProprietarioColtivatore= new FinestraProprietarioColtivatore(controller);
		this.finestraRuolo= new FinestraSceltaRuolo(controller);
		this.finestraIscrizioneProprietario= new FinestraIscrizioneProprietario(controller);
		this.finestraIscrizioneColtivatore= new FinestraIscrizioneColtivatore(controller);
		setLayout(this.cardLayout);
		add(primaPagina,"prima pagina");
		add(finestraLogin,"login");
		add(finestraProprietario,"proprietario");
		add(finestraColtivatore,"coltivatore");
		add(finestraProprietarioColtivatore,"proprietario-coltivatore");
		add(finestraRuolo,"scelta ruolo");
		add(finestraIscrizioneProprietario,"iscrizione proprietario");
		add(finestraIscrizioneColtivatore,"iscrizione coltivatore");
	}
	
	public void mostraPanel(String nome) {
		cardLayout.show(this, nome);
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public FinestraLogin getFinestraLogin() {
		return finestraLogin;
	}

	public void setFinestraLogin(FinestraLogin finestraLogin) {
		this.finestraLogin = finestraLogin;
	}

	public PrimaPaginaPanel getPrimaPagina() {
		return primaPagina;
	}

	public void setPrimaPagina(PrimaPaginaPanel primaPagina) {
		this.primaPagina = primaPagina;
	}

	public FinestraProprietario getFinestraProprietario() {
		return finestraProprietario;
	}

	public void setFinestraProprietario(FinestraProprietario finestraProprietario) {
		this.finestraProprietario = finestraProprietario;
	}
	public FinestraProprietarioColtivatore getFinestraProprietarioColtivatore() {
		return finestraProprietarioColtivatore;
	}

	public FinestraSceltaRuolo getFinestraRuolo() {
		return finestraRuolo;
	}

	public FinestraIscrizioneProprietario getFinestraIscrizioneProprietario() {
		return finestraIscrizioneProprietario;
	}

	public void setFinestraIscrizioneColtivatore(FinestraIscrizioneColtivatore finestraIscrizioneColtivatore) {
		this.finestraIscrizioneColtivatore = finestraIscrizioneColtivatore;
	}

	public FinestraIscrizioneColtivatore getFinestraIscrizioneColtivatore() {
		return finestraIscrizioneColtivatore;
	}
	
	
}
