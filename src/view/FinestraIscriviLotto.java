package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.Controller;

public class FinestraIscriviLotto extends JPanel {

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
	private JButton indietro;
	
	public FinestraIscriviLotto(Controller controller) {
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
		pnlTessMorf.add(Box.createRigidArea(new Dimension(70,0)));
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
		pnlDimPhAlt.add(Box.createRigidArea(new Dimension(50,0)));
		pnlDimPhAlt.add(ph);
		pnlDimPhAlt.add(cmpPh);
		pnlDimPhAlt.add(Box.createRigidArea(new Dimension(50,0)));
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
		pnlLuogo.add(Box.createRigidArea(new Dimension(50,0)));
		pnlLuogo.add(comune);
		pnlLuogo.add(cmpComune);
		pnlLuogo.add(Box.createRigidArea(new Dimension(40,0)));
		pnlLuogo.add(provincia);
		pnlLuogo.add(cmpProvincia);
		pnlCenter.add(pnlLuogo);
		
		JPanel pnlBottoni= new JPanel(new FlowLayout());
		pnlBottoni.setPreferredSize(new Dimension(100,100));
		salva= new JButton("Salva");
		salva.setPreferredSize(riga);
		salva.setFont(fontGrande);
		indietro= new JButton("Indietro");
		indietro.setPreferredSize(riga);
		indietro.setFont(fontGrande);
		pnlBottoni.add(salva);
		pnlBottoni.add(Box.createRigidArea(new Dimension(120,0)));
		pnlBottoni.add(indietro);
		pnlCenter.add(pnlBottoni);
		pnlCenter.add(Box.createVerticalGlue());
		
	}

}
