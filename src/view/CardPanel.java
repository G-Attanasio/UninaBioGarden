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
	
	public CardPanel(Controller controller) {
		this.controller=controller;
		this.cardLayout= new CardLayout();
		this.primaPagina= new PrimaPaginaPanel(controller);
		this.finestraLogin= new FinestraLogin(controller);
		this.finestraProprietario= new FinestraProprietario(controller);
		setLayout(this.cardLayout);
		add( primaPagina,"prima pagina");
		add(finestraLogin,"login");
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
	
}
