package com.nesti.stock_manager.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Holds formatting-related convenience methods
 * 
 * @author Emmanuelle Gay, Erik Shea
 */
public class FormatUtil {
	/**
	 * Round a double to n decimal places
	 * @param value
	 * @param places
	 * @return rounded double
	 */
	public static double round(Double value, Integer places) {
	    if (places < 0) { // Can't round to negative places
	    	throw new IllegalArgumentException();
	    }
	    // Need to convert to BigDecimal because Double is not precise enough to render to an exact
	    // decimal place without generating extraneous residue numbers for some operations
	    BigDecimal bd = new BigDecimal(Double.toString(value));
	    bd = bd.setScale(places, RoundingMode.HALF_UP); // Rounds to nearest neighbor, or upwards if equidistant
	    return bd.doubleValue(); // Convert back into Double
	}
	
	
	public static Date stringToDate(String dateString) {
		var formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		Date result = null;
		try{
			result = formatter.parse(dateString);
		}catch (Exception e) {}
		
		return result;
	}
}
