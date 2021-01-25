package com.nesti.stock_manager.util;

@SuppressWarnings("serial")
public class UnavailableArticleException extends Exception { 
    public UnavailableArticleException (String errorMessage) {
        super(errorMessage);
    }
    
    public UnavailableArticleException () {
    	super();
    }
}
