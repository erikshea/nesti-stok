package util;

import java.lang.reflect.Field;

public class ReflectionProperty {
	public static boolean set(Object object, String fieldName, Object fieldValue) {
	    Class<?> c = object.getClass();
	    while (c != null) {
	        try {
	            Field field = c.getDeclaredField(fieldName);
	            field.setAccessible(true);
	            field.set(object, fieldValue);
	            return true;
	        } catch (NoSuchFieldException e) {
	            c = c.getSuperclass();
	        } catch (Exception e) {
	            throw new IllegalStateException(e);
	        }
	    }
	    return false;
	}
	
	@SuppressWarnings("unchecked")
	public static <V> V get(Object object, String fieldName) {
	    Class<?> c = object.getClass();
	    while (c != null) {
	        try {
	            Field field = c.getDeclaredField(fieldName);
	            field.setAccessible(true);
	            return (V) field.get(object);
	        } catch (NoSuchFieldException e) {
	            c = c.getSuperclass();
	        } catch (Exception e) {
	            throw new IllegalStateException(e);
	        }
	    }
	    return null;
	}
}
