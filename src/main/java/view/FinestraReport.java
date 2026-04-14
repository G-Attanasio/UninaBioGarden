package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import controller.Controller;
import dto.DatiReportDTO;

public class FinestraReport extends JPanel {

	private static final long serialVersionUID = 1L;

	private Controller controller;
	private JButton indietro;
	private JPanel pnlGrafico;
	
	public FinestraReport(Controller controller) {
		this.controller= controller;
		setLayout(new BorderLayout());
		JPanel pnlBottoni= new JPanel(new FlowLayout());
		indietro= new JButton("Indietro");
		pnlBottoni.add(indietro);
		add(pnlBottoni,BorderLayout.SOUTH);
		pnlGrafico= new JPanel(new BorderLayout());
		add(pnlGrafico,BorderLayout.CENTER);
		pnlGrafico.revalidate(); 
	    pnlGrafico.repaint();
	    this.revalidate();
	    this.repaint();	
		indietro.addActionListener(e->{
			controller.mostraPanelInterno("visualizza progetti");
		});
		
	}
	
	public void costruisciGrafico(ArrayList<DatiReportDTO> datiDaPassare) {	
	    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	    for (DatiReportDTO d : datiDaPassare) {
	    	 String nome = d.getNomeColtura();
	         double semi = d.getQuantitaSemi();
	         double prevista = d.getPrevista();
	         double reale = d.getReale();
	        dataset.addValue(semi, "Quantità semi (Kg)", nome);
	        dataset.addValue(prevista, "Quantità prevista (Kg)", nome);
	        dataset.addValue(reale, "Quantità reale (Kg)", nome);
	    }
	    JFreeChart chart = ChartFactory.createBarChart(
	        "Analisi raccolte progetto", "Coltura", "Quantità (Kg)",
	        dataset, PlotOrientation.VERTICAL, true, true, false
	    );
	    CategoryPlot plot = chart.getCategoryPlot();
	    BarRenderer renderer = (BarRenderer) plot.getRenderer();
	    renderer.setSeriesPaint(0, Color.GREEN);
	    renderer.setSeriesPaint(1, Color.BLUE);
	    renderer.setSeriesPaint(2, Color.RED);
	    pnlGrafico.removeAll();
	    ChartPanel cp = new ChartPanel(chart);
	    pnlGrafico.add(cp, BorderLayout.CENTER);	    
	    revalidate();
	    repaint();
	}
	
	public void mostraMessaggio(String testo) {
		JOptionPane.showMessageDialog(this, testo);
	}
}
