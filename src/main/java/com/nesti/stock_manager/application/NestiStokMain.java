package com.nesti.stock_manager.application;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.nesti.stock_manager.controller.ConnectionForm;
import com.nesti.stock_manager.util.AppAppereance;
import com.nesti.stock_manager.util.HibernateUtil;
import com.nesti.stock_manager.util.SwingUtil;

/**
 * Application main class
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
public class NestiStokMain {
	static JFrame frame;
	public static void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("Nesti Stok");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        AppAppereance.setDefault();
	   	HibernateUtil.setCurrentEnvironment("dev"); // Database environment for app
		changeFrameContent(new ConnectionForm());	// Show connection form (which may immediately redirect to main controller if user has already logged in)

        frame.setVisible(true);
       
	}
   public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater( () -> createAndShowGUI() );
    }
   
	/**
	 * Change the panel that is displayed inside application window
	 * @param new panel to display
	 */
	public static void changeFrameContent(JPanel p) {
		frame.getContentPane().removeAll();  // Clear frame content
		frame.getContentPane().add(p);	
		frame.getContentPane().doLayout();
		frame.pack();						// change size to panel size
		frame.update(frame.getGraphics());	// refresh
		SwingUtil.centerFrame(frame);
	}

	public static JFrame getFrame() {
		return frame;
	}
}
