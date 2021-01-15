package application;


import java.util.List;

import javax.swing.JFrame;

import controller.MainWindowControl;
import model.*;
import util.HibernateUtil;

public class NestiStokMain {
	public static void createAndShowGUI() {

        //Create and set up the window.
        JFrame frame = new JFrame("Nesti Stok");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		var mainWindow = new MainWindowControl();
        frame.getContentPane().add(mainWindow);
        frame.pack();
        frame.setVisible(true);
	}
   public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater( () -> createAndShowGUI() );
    }
}
