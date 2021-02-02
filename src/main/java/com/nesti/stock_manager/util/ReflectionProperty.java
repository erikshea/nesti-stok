package com.nesti.stock_manager.util;

import java.lang.reflect.Field;

/**
 * @author Emmanuelle Gay, Erik Shea
 * uses reflection to set and get an object's property
 */
public class ReflectionProperty {
	
	/**
	 * set an object property from a String property name, and a value
	 * @param object object for which property should be set
	 * @param propertyName name of property
	 * @param propertyValue
	 * @return
	 */
	public static boolean set(Object object, String propertyName, Object propertyValue) {
	    var currentClass = object.getClass(); // start with  object class
	    var result = false;
	    while (!result && currentClass != null) { // Will loop through all superclasses to see if one contains target property
	        try {
	            Field field = currentClass.getDeclaredField(propertyName); // try to use Field object to find a corresponding property in class definition
	            field.setAccessible(true); // allows access to property
	            field.set(object, propertyValue);
	            result = true;
	        } catch (NoSuchFieldException e) { // if property not found by Field object
	            currentClass = currentClass.getSuperclass(); // current class becomes superclass
	        } catch (Exception e) {
	            throw new IllegalStateException(e);
	        }
	    }
	    return result;
	}
	
	/**
	 * Gets an object property's value from a property name String
	 * @param <T> generic property type
	 * @param object object on which to get property value
	 * @param propertyName String name of property to get
	 * @return property value
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(Object object, String propertyName) {
	    var currentClass = object.getClass();  // start with  object class
	    
	    while (currentClass != null) { // Will loop through all superclasses to see if one contains target property
	        try {
	            Field field = currentClass.getDeclaredField(propertyName); // try to use Field object to find a corresponding property in class definition
	            field.setAccessible(true); // allows access to property
	            return (T) field.get(object); // get property, cast as property type
	        } catch (NoSuchFieldException e) { // if property not found by Field object
	            currentClass = currentClass.getSuperclass(); // current class becomes superclass
	        } catch (Exception e) {
	            throw new IllegalStateException(e);
	        }
	    }
	    return null;
	}
	
}
