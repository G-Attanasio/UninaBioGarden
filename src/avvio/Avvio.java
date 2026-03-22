package avvio;

import javax.swing.SwingUtilities;

import controller.Controller;

public class Avvio {

	public static void main(String[] args) {
		
		 SwingUtilities.invokeLater(() -> {
	            new Controller(); 
	        });

	}

}
