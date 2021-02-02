package com.nesti.stock_manager.util;

/**
 * @author Emmanuelle Gay, Erik Shea
 * Signals an article is unavailable in shopping-cart relating methods
 */
@SuppressWarnings("serial")
public class UnavailableArticleException extends Exception { 
    public UnavailableArticleException (String errorMessage) {
        super(errorMessage);
    }
    
    public UnavailableArticleException () {
    	super();
    }
}
