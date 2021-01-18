package controller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import dao.IngredientDao;
import dao.ProductDao;
import dao.UnitDao;
import form.EditableListFieldContainer;
import form.FieldContainer;
import model.Ingredient;
import util.HibernateUtil;

public class IngredientInformation extends BaseInformation {
	private static final long serialVersionUID = 1775908299271902575L;

	public IngredientInformation(MainWindowControl c, Ingredient ingredient) {
		super(c, ingredient);
		if (ingredient == null) {
			ingredient = new Ingredient();
		}
		final var ingredientFinal= ingredient;
		var ingredientDao = new IngredientDao();
		var productDao = new ProductDao();
		var unitDao = new UnitDao();
		// left of the screen, ingredient's information
		
		var ingredientForm = new JPanel();
		ingredientForm.setPreferredSize(new Dimension(500, 0));
		ingredientForm.setLayout(new BoxLayout(ingredientForm, BoxLayout.Y_AXIS));
		
		var titleIngredientInformation = new JLabel("Ingrédient");
		ingredientForm.add(titleIngredientInformation);
		
		var descriptionFieldContainer = new FieldContainer("Description", this);
		descriptionFieldContainer.bind(
				ingredientFinal.getName(),
				(s)-> ingredientFinal.setName(s),
				(fieldValue)->productDao.findOneBy("name", fieldValue) == null);
		ingredientForm.add(descriptionFieldContainer);
		
		var codeFieldContainer = new FieldContainer("Référence", this);
		codeFieldContainer.bind(
				ingredientFinal.getReference(),
				(s)-> ingredientFinal.setReference(s),
				(fieldValue)->productDao.findOneBy("reference", fieldValue) == null);
		ingredientForm.add(codeFieldContainer);
		
		var unitListContainer = new EditableListFieldContainer("Unité", this);
		unitListContainer.getList().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		


		unitDao.findAll().forEach(unit -> {
			unitListContainer.getListModel().addElement(unit.getName());				
		});
		unitListContainer.bindMultiple(
			ingredientFinal.getUnitsNames(),
			(s)->ingredientFinal.setUnitsFromNames(s) );
		ingredientForm.add(unitListContainer);

		ingredientForm.add(unitListContainer);

		ingredientForm.add(Box.createVerticalGlue());
		
		this.add(ingredientForm, BorderLayout.WEST);
		
		this.buttonValidate.addActionListener( e->{
			try{
				/*productDao.saveOrUpdate(ingredientFinal.getProduct());
				HibernateUtil.getSession().getTransaction().commit();
				var insertedProduct = productDao.findOneBy("reference", ingredientFinal.getProduct().getReference());
				ingredientFinal.setIdProduct(insertedProduct.getIdProduct());*/
				ingredientDao.saveOrUpdate(ingredientFinal);
				HibernateUtil.getSession().getTransaction().commit();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this,
				    "Veuillez vérifier les champs en orange.",
				    "Paramètres invalides",
				    JOptionPane.WARNING_MESSAGE);
			}
			this.mainControl.getIngredientDirectory().getEntityList().refresh();
			this.mainControl.remove(this);
			this.mainControl.setSelectedComponent(this.mainControl.getIngredientDirectory());
		});
		
		this.buttonCancel.addActionListener( e->{
			this.mainControl.setSelectedComponent(this.mainControl.getIngredientDirectory());
		});
	}
}