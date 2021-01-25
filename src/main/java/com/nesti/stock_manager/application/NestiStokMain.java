package com.nesti.stock_manager.application;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.nesti.stock_manager.controller.ConnectionForm;
import com.nesti.stock_manager.util.HibernateUtil;

public class NestiStokMain {
	static JFrame frame;
	
	public static void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("Nesti Stok");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		changeFrameContent(new ConnectionForm());

        frame.setVisible(true);
       
	}
   public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
	   	HibernateUtil.setCurrentEnvironment("dev");
        javax.swing.SwingUtilities.invokeLater( () -> createAndShowGUI() );
    }
   
	public static void changeFrameContent(JPanel p) {
		frame.getContentPane().removeAll();
		frame.getContentPane().add(p);
		frame.getContentPane().doLayout();
		frame.pack();
		frame.update(frame.getGraphics());
	}

}
