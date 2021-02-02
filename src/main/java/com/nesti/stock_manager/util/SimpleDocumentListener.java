package com.nesti.stock_manager.util;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * @author Emmanuelle Gay, Erik Shea
 * interface for a document listener (ie TextField change)
 */
@FunctionalInterface
public interface SimpleDocumentListener extends DocumentListener {
    void update(DocumentEvent e);

    @Override
    default void insertUpdate(DocumentEvent e) {
        update(e);
    }
    @Override
    default void removeUpdate(DocumentEvent e) {
        update(e);
    }
    @Override
    default void changedUpdate(DocumentEvent e) {
        update(e);
    }
}