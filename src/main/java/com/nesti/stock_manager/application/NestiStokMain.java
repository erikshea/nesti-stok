package com.nesti.stock_manager.application;

import com.nesti.stock_manager.controller.*;
import javax.swing.JFrame;

import com.nesti.stock_manager.controller.MainWindowControl;

public class NestiStokMain {
	
	public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Nesti Stok");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	var mainWindow = new MainWindowControl();
		var formConnexion = new ConnexionForm(mainWindow,frame);
		
        frame.getContentPane().add(formConnexion);
        frame.pack();
        frame.setVisible(true);
        

	}
   public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater( () -> createAndShowGUI() );
    }
   
}
