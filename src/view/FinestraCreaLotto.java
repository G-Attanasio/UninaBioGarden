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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.Border;

import controller.Controller;

public class FinestraCreaLotto extends JPanel {

private static final long serialVersionUID = 1L;
	
	private Controller controller;
	private String[] tessiture= {"SABBIOSO","ARGILLOSO","LIMOSO","MEDIO IMPASTO"};
	private JComboBox<String> tipoTessitura= new JComboBox<>(tessiture);
	private String [] morfologie= {"PIANEGGIANTE","COLLINARE","MONTUOSA"};
	private JComboBox<String> tipoMorfologia= new JComboBox<>(morfologie);
	private JTextField cmpDimensioni;
	private JTextField cmpPh;
	private JTextField cmpAltitudine;
	private JTextField cmpLocalità;
	private JTextField cmpComune;
	private JTextField cmpProvincia;
	private JButton salva;
	private JButton annulla;
	
	
	public FinestraCreaLotto(Controller controller) {
		this.controller=controller;
		setLayout(new BorderLayout());
		JPanel pnlNord= new JPanel(new FlowLayout());
		JPanel pnlSud= new JPanel (new FlowLayout());
		JPanel pnlEst= new JPanel( new FlowLayout());
		JPanel pnlOvest= new JPanel( new FlowLayout());
		JPanel pnlCenter= new JPanel();
		pnlCenter.setLayout(new BoxLayout(pnlCenter,BoxLayout.Y_AXIS));
		pnlNord.setBackground(Color.GREEN);
		pnlNord.setPreferredSize(new Dimension(600,50));
		pnlSud.setBackground(Color.GREEN);
		pnlSud.setPreferredSize(new Dimension(500,50));
		pnlEst.setBackground(Color.GREEN);
		pnlEst.setPreferredSize(new Dimension(50,500));
		pnlOvest.setBackground(Color.GREEN);
		pnlOvest.setPreferredSize(new Dimension(50,500));
		add(pnlNord,BorderLayout.NORTH);
		add(pnlSud,BorderLayout.SOUTH);
		add(pnlEst,BorderLayout.EAST);
		add(pnlOvest,BorderLayout.WEST);
		add(pnlCenter,BorderLayout.CENTER);
		Dimension riga= new Dimension(100,30);
		Font fontGrande= new Font("Arial",Font.PLAIN,20);
		
		JPanel pnlTessMorf= new JPanel(new FlowLayout());
		pnlTessMorf.setPreferredSize(new Dimension(100,100));
		JLabel tessitura= new JLabel("Tessitura:");
		tessitura.setFont(fontGrande);
		JLabel morfologia= new JLabel("Morfologia:");
		tipoTessitura.setFont(fontGrande);
		tipoMorfologia.setFont(fontGrande);
		morfologia.setFont(fontGrande);
		pnlTessMorf.add(tessitura);
		pnlTessMorf.add(tipoTessitura);
		pnlTessMorf.add(Box.createRigidArea(new Dimension(40,0)));
		pnlTessMorf.add(morfologia);
		pnlTessMorf.add(tipoMorfologia);
		pnlCenter.add(Box.createVerticalGlue());
		pnlCenter.add(pnlTessMorf);
		
		JPanel pnlDimPhAlt= new JPanel(new FlowLayout());
		pnlDimPhAlt.setPreferredSize(new Dimension(100,100));
		JLabel dimensioni= new JLabel("Dimensioni");
		dimensioni.setFont(fontGrande);
		JLabel ph= new JLabel("Ph:");
		ph.setFont(fontGrande);
		JLabel altitudine= new JLabel("Altitudine");
		altitudine.setFont(fontGrande);
		cmpDimensioni= new JTextField(10);
		cmpDimensioni.setPreferredSize(riga);
		cmpDimensioni.setFont(fontGrande);
		cmpPh= new JTextField(6);
		cmpPh.setPreferredSize(riga);
		cmpPh.setFont(fontGrande);
		cmpAltitudine= new JTextField(10);
		cmpAltitudine.setPreferredSize(riga);
		cmpAltitudine.setFont(fontGrande);
		pnlDimPhAlt.add(dimensioni);
		pnlDimPhAlt.add(cmpDimensioni);
		pnlDimPhAlt.add(Box.createRigidArea(new Dimension(30,0)));
		pnlDimPhAlt.add(ph);
		pnlDimPhAlt.add(cmpPh);
		pnlDimPhAlt.add(Box.createRigidArea(new Dimension(30,0)));
		pnlDimPhAlt.add(altitudine);
		pnlDimPhAlt.add(cmpAltitudine);
		pnlCenter.add(pnlDimPhAlt);
		
		JPanel pnlLuogo= new JPanel(new FlowLayout());
		pnlLuogo.setPreferredSize(new Dimension(100,100));
		JLabel località= new JLabel("Località:");
		località.setFont(fontGrande);
		JLabel comune= new JLabel("Comune:");
		comune.setFont(fontGrande);
		JLabel provincia= new JLabel("Provincia:");
		provincia.setFont(fontGrande);
		cmpLocalità= new JTextField(20);
		cmpLocalità.setPreferredSize(riga);
		cmpLocalità.setFont(fontGrande);
		cmpComune= new JTextField(15);
		cmpComune.setPreferredSize(riga);
		cmpComune.setFont(fontGrande);
		cmpProvincia= new JTextField(2);
		cmpProvincia.setPreferredSize(riga);
		cmpProvincia.setFont(fontGrande);
		
		pnlLuogo.add(località);
		pnlLuogo.add(cmpLocalità);
		pnlLuogo.add(Box.createRigidArea(new Dimension(20,0)));
		pnlLuogo.add(comune);
		pnlLuogo.add(cmpComune);
		pnlLuogo.add(Box.createRigidArea(new Dimension(20,0)));
		pnlLuogo.add(provincia);
		pnlLuogo.add(cmpProvincia);
		pnlCenter.add(pnlLuogo);
		
		JPanel pnlBottoni= new JPanel(new FlowLayout());
		pnlBottoni.setPreferredSize(new Dimension(100,100));
		salva= new JButton("Salva");
		salva.setPreferredSize(riga);
		salva.setFont(fontGrande);
		annulla= new JButton("Indietro");
		annulla.setPreferredSize(riga);
		annulla.setFont(fontGrande);
		pnlBottoni.add(salva);
		pnlBottoni.add(Box.createRigidArea(new Dimension(50,0)));
		pnlBottoni.add(annulla);
		pnlCenter.add(pnlBottoni);
		pnlCenter.add(Box.createVerticalGlue());
		
		annulla.addActionListener(e->{
			pulisciCampi();
	        controller.mostraPanelInterno("visualizza lotti");
	           
		});
		salva.addActionListener(e->{
			 controller.aggiungiLotto();			   
		});
		
	}
	
	
	
	public void messaggioErrore(JTextField campo, String messaggio) {
		campo.setBorder(BorderFactory.createLineBorder(Color.RED,1));
		campo.setToolTipText(messaggio);
		ToolTipManager.sharedInstance().setInitialDelay(0);
		
	}
	
	public void messaggioErroreBottone(JButton bottone, String messaggio) {
		bottone.setBorder(BorderFactory.createLineBorder(Color.RED,1));
		bottone.setToolTipText(messaggio);
		ToolTipManager.sharedInstance().setInitialDelay(0);
	}
	
	public void resetBordi(){		
		Border bordo= UIManager.getBorder("TextField.border");
		cmpDimensioni.setBorder(bordo);
		cmpDimensioni.setToolTipText(null);
		cmpPh.setBorder(bordo);
		cmpPh.setToolTipText(null);
		cmpAltitudine.setBorder(bordo);
		cmpAltitudine.setToolTipText(null);
		cmpLocalità.setBorder(bordo);
		cmpLocalità.setToolTipText(null);
		cmpComune.setBorder(bordo);
		cmpComune.setToolTipText(null);
		cmpProvincia.setBorder(bordo);
		cmpProvincia.setToolTipText(null);
		salva.setBorder(bordo);
		salva.setToolTipText(null);
		annulla.setBorder(bordo);
		annulla.setToolTipText(null);
		
	}
	
	public void pulisciCampi() {
	    cmpDimensioni.setText("");
	    cmpPh.setText("");
	    cmpAltitudine.setText("");
	    cmpLocalità.setText("");
	    cmpComune.setText("");
	    cmpProvincia.setText("");;
	    resetBordi();
	}

	public Controller getController() {
		return controller;
	}

	public String[] getTessiture() {
		return tessiture;
	}

	public String getTipoTessitura() {
		return tipoTessitura.getSelectedItem().toString();
	}

	public String[] getMorfologie() {
		return morfologie;
	}

	public String getTipoMorfologia() {
		return tipoMorfologia.getSelectedItem().toString();
	}

	public String getDimensioni() {
		return cmpDimensioni.getText();
	}

	public String getPh() {
		return cmpPh.getText();
	}

	public String getAltitudine() {
		return cmpAltitudine.getText();
	}

	public String getLocalità() {
		return cmpLocalità.getText();
	}

	public String getComune() {
		return cmpComune.getText();
	}

	public String getProvincia() {
		return cmpProvincia.getText();
	}

	public JButton getSalva() {
		return salva;
	}

	public JButton getAnnulla() {
		return annulla;
	}

	public JTextField getCmpDimensioni() {
		return cmpDimensioni;
	}

	public JTextField getCmpPh() {
		return cmpPh;
	}

	public JTextField getCmpAltitudine() {
		return cmpAltitudine;
	}

	public JTextField getCmpLocalità() {
		return cmpLocalità;
	}

	public JTextField getCmpComune() {
		return cmpComune;
	}

	public JTextField getCmpProvincia() {
		return cmpProvincia;
	}
	public JComboBox<String> getCmpTessitura() {
		return tipoTessitura;
	}
	public JComboBox<String> getCmpMorfologia(){
		return tipoMorfologia;
	}

}
