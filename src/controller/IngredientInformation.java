package controller;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import dao.ArticleDao;
import dao.IngredientDao;
import dao.ProductDao;
import dao.UnitDao;
import form.EditableListFieldContainer;
import form.FieldContainer;
import form.ListFieldContainer;
import model.Article;
import model.Ingredient;
import model.Product;

public class IngredientInformation extends BaseInformation {
	private static final long serialVersionUID = 1775908299271902575L;

	public IngredientInformation(MainWindowControl c, Ingredient ingredient) {
		super(c, ingredient);
		if (ingredient == null) {
			ingredient = new Ingredient();
			ingredient.setProduct(new Product());
		}
		final var ingredientFinal= ingredient;
		var ingredientDao = new IngredientDao();
		var productDao = new ProductDao();
		// left of the screen, ingredient's information
		
		var ingredientForm = new JPanel();
		ingredientForm.setPreferredSize(new Dimension(500, 0));
		ingredientForm.setLayout(new BoxLayout(ingredientForm, BoxLayout.Y_AXIS));
		
		var titleIngredientInformation = new JLabel("Ingrédient");
		ingredientForm.add(titleIngredientInformation);
		
		var descriptionFieldContainer = new FieldContainer("Description");
		descriptionFieldContainer.bind(
				()-> ingredientFinal.getProduct().getName(),
				(s)-> ingredientFinal.getProduct().setName(s),
				(fieldValue)->productDao.findOneBy("name", fieldValue) == null);
		ingredientForm.add(descriptionFieldContainer);
		
		var codeFieldContainer = new FieldContainer("Référence");
		codeFieldContainer.bind(
				()-> ingredientFinal.getProduct().getReference(),
				(s)-> ingredientFinal.getProduct().setReference(s),
				(fieldValue)->productDao.findOneBy("reference", fieldValue) == null);
		ingredientForm.add(codeFieldContainer);
		
		var unitListContainer = new EditableListFieldContainer("Unité");
		unitListContainer.getList().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		
		var unitDao = new UnitDao();
		unitDao.findAll().forEach(unit -> {
			unitListContainer.getListModel().addElement(unit.getName());				
		});
		unitListContainer.bindMultiple(
			()->ingredientFinal.getUnitsNames(),
			(s)->ingredientFinal.setUnitsFromNames(s) );
		ingredientForm.add(unitListContainer);

		ingredientForm.add(unitListContainer);

		ingredientForm.add(Box.createVerticalGlue());
		
		this.add(ingredientForm, BorderLayout.WEST);
		
		this.buttonValidate.addActionListener( e->{
			try{
				ingredientDao.saveOrUpdate(ingredientFinal);
				productDao.saveOrUpdate(ingredientFinal.getProduct());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this,
				    "Veuillez vérifier les champs en orange.",
				    "Paramètres invalides",
				    JOptionPane.WARNING_MESSAGE);
			}
			this.mainControl.getIngredientDirectory().getEntityList().refresh();
			this.mainControl.remove(this);
		});
	}
}