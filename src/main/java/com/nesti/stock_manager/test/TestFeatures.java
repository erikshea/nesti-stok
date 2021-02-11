package com.nesti.stock_manager.test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TestFeatures {
	
	private static Map<String,String> settings = new HashMap<>();
	
    public static void main(String[] args) 
    {
    	String currentWorkingDir = System.getProperty("user.dir");
        System.out.println(currentWorkingDir);
    }
 

    
}
