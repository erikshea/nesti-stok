package com.nesti.stock_manager.test;

public class MyClass {
	private static MyClass instance;
	
    public static MyClass getInstance() {
        if(instance == null) {
        	instance = new MyClass();
        }
        return instance;
    }
}
