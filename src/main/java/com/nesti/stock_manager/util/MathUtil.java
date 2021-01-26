package com.nesti.stock_manager.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil {
	public static double round(Double value, Integer places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(Double.toString(value));
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
