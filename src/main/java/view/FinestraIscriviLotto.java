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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.Border;

import controller.Controller;
import dto.InputLottoDTO;

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
		Dimension grandezza= new Dimension(180,30);
		Dimension pnl= new Dimension(1000,70);
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.setOpaque(false);
		Dimension riga= new Dimension(100,30);
		Font fontGrande= new Font("Arial",Font.PLAIN,20);		
		JPanel pnlTessMorf= new JPanel(new FlowLayout());
		pnlTessMorf.setPreferredSize(pnl);
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
		add(Box.createVerticalStrut(200));
		add(pnlTessMorf);
		
		JPanel pnlDimPhAlt= new JPanel(new FlowLayout());
		pnlDimPhAlt.setPreferredSize(pnl);
		JLabel dimensioni= new JLabel("Dimensioni:");
		dimensioni.setFont(fontGrande);
		JLabel ph= new JLabel("Ph:");
		ph.setFont(fontGrande);
		JLabel altitudine= new JLabel("Altitudine:");
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
		add(Box.createVerticalStrut(30));
		add(pnlDimPhAlt);
		
		JPanel pnlLuogo= new JPanel(new FlowLayout());
		pnlLuogo.setPreferredSize(pnl);
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
		add(Box.createVerticalStrut(30));
		add(pnlLuogo);
		
		JPanel pnlBottoni= new JPanel(new FlowLayout());
		pnlBottoni.setPreferredSize(new Dimension(100,100));
		salva= new JButton("Salva");
		salva=creaBottoneArrotondato("Salva");
		salva.setPreferredSize(grandezza);
		indietro= new JButton("Indietro");
		indietro= creaBottoneArrotondato("Indietro");
		indietro.setPreferredSize(grandezza);
		pnlBottoni.add(salva);
		pnlBottoni.add(Box.createRigidArea(new Dimension(50,0)));
		pnlBottoni.add(indietro);
		add(Box.createVerticalStrut(30));
		add(pnlBottoni);
		add(Box.createVerticalGlue());
		pnlTessMorf.setOpaque(false);
		pnlDimPhAlt.setOpaque(false);
		pnlLuogo.setOpaque(false);
		pnlBottoni.setOpaque(false);
	
		
		indietro.addActionListener(e->{
			pulisciCampi();
	        controller.mostraPanel("iscrizione proprietario");
	           
		});
		salva.addActionListener(e->{
			 controller.validaIscrizioneUtenteProprietario(getInputLottoDTO());			   
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
	
	public void gestisciErrori(ArrayList<String> errori) {
	    resetBordi();
	    for (String errore : errori) {
	        switch (errore) {
	            case "dimensioni":
	                messaggioErrore(cmpDimensioni, "Superficie non valida, deve essere compresa tra i 1000 e 1000000 mq.");
	                break;
	            case "ph":
	                messaggioErrore(cmpPh, "Ph non valido, deve essere compreso tra 4 e 9.");
	                break;
	            case "altitudine":
	                messaggioErrore(cmpAltitudine, "Altitudine non valida, deve essere compresa tra -20 e 3000.");
	                break;
	            case "dimensioni int":
	                messaggioErrore(cmpDimensioni, "Inserire un numero intero.");
	                break;
	            case "ph numero":
	                messaggioErrore(cmpPh, "Inserire un numero.");
	                break;
	            case "altitudine int":
	                messaggioErrore(cmpAltitudine, "Inserire un numero intero.");
	                break;
	            case "lunghezza localita":
	            	messaggioErrore(cmpLocalità, "Inserire un numero di caratteri compreso tra 1 e 30.");
	            	break;
	            case "lettere localita":
	            	messaggioErrore(cmpLocalità, "Inserire solo lettere.");
	            	break;
	            case "lunghezza comune":
	            	messaggioErrore(cmpComune, "Inserire un numero di caratteri compreso tra 1 e 30.");
	            	break;
	            case "lettere comune":
	            	messaggioErrore(cmpComune, "Inserire solo lettere.");
	            	break;
	            case "lunghezza provincia":
	                messaggioErrore(cmpProvincia, "Provincia deve avere 2 lettere.");
	                break;
	            case "lettere provincia":
	                messaggioErrore(cmpProvincia, "Inserire solo lettere.");
	                break;
	        }
	    }
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
		indietro.setBorder(bordo);
		indietro.setToolTipText(null);
		
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
	
	public InputLottoDTO getInputLottoDTO() {
	    return new InputLottoDTO(getTipoTessitura().replace(" ","_"),getDimensioni(),getPh().replace(",","."),getTipoMorfologia(),getAltitudine(),getLocalità(),getComune(),getProvincia().toUpperCase());
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);	        
        int w = getWidth();
        int h = getHeight();
        Color c1 = new Color(245, 163, 96); 
        Color c2 = new Color(200,200,200);
        GradientPaint gp = new GradientPaint(0, 0, c1, 0, h, c2);	        
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);	        
        super.paintComponent(g); 
    }
	
	public JButton creaBottoneArrotondato(String testo) {
	    JButton bottone = new JButton(testo) {
	        private static final long serialVersionUID = 1L;
	        @Override
	        protected void paintComponent(Graphics g) {
	            Graphics2D gr = (Graphics2D) g.create();
	            gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	            gr.setColor(new Color(34, 139, 34));
	            gr.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);	            
	            gr.dispose();
	            super.paintComponent(g);
	        }
	    };
	    bottone.setContentAreaFilled(false); 
	    bottone.setBorderPainted(false);    
	    bottone.setFocusPainted(false);     
	    bottone.setForeground(Color.WHITE);  
	    bottone.setFont(new Font("SansSerif", Font.BOLD, 15));
	    bottone.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)); 
	    return bottone;
	}
}