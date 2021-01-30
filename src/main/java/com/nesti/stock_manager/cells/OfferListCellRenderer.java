package com.nesti.stock_manager.cells;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.nesti.stock_manager.model.Offer;

/**
 * Offer list item renderer
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
public class OfferListCellRenderer extends DefaultListCellRenderer {
	private static final long serialVersionUID = 1L;

	public Component getListCellRendererComponent(
			JList list,
			Object value,
			int index,
			boolean isSelected,
			boolean cellHasFocus) {
        if (value instanceof Offer) {
        	var offer = (Offer)value;
        	// Show supplier and price
            value = offer.getSupplier().getName() + " (" + offer.getPrice() + ")";
        }
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        return this;
    }
}