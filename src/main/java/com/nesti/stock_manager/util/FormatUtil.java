package com.nesti.stock_manager.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtil {
	public static double round(Double value, Integer places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(Double.toString(value));
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
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
