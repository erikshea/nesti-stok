package application;


import java.util.List;

import javax.swing.JFrame;

import controller.MainWindowControl;
import dao.ArticleDao;
import model.*;
import util.HibernateUtil;

public class NestiStokMain {
	public static void createAndShowGUI() {

		var dao = new ArticleDao();
		var myArticle = dao.findOneBy("code", "OEUF6");
	//	System.out.println(myArticle.getName());
		
		myArticle.getOffers().forEach(o->{
			System.out.println(o.getSupplier().getName() + myArticle.getName() + o.getPrice());
		});;
		
		
//		var createdArticle = new Article();
//		createdArticle.setName("sucre");
//		createdArticle.setEan("1234567891523");
//		createdArticle.setCode("OEUF18");
//		createdArticle.setQuantity(1);
//		createdArticle.setStock(12);
		
		
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
