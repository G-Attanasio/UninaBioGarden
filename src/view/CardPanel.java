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
	
	public CardPanel(Controller controller) {
		this.controller=controller;
		this.cardLayout= new CardLayout();
		setLayout(this.cardLayout);
		add( new PrimaPaginaPanel(controller),"prima pagina");
		add(new FinestraLogin(controller),"login");
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
	
}
