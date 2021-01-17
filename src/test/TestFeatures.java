package test;
import model.*;
import util.HibernateUtil;
import util.ReflectionProperty;

import org.hibernate.query.Query;

import dao.*;

public class TestFeatures {
	public static void main(String[] args) {
		var test = (new UnitDao()).findOneBy("idUnit", 3);
		
		var test2 = new Unit();
		System.out.println(test2.getIdUnit());;
		
		var dao = new UnitDao();
		

		
		System.out.println(test.getName());

		System.out.println(ReflectionProperty.get(test, "name").toString());
        //Query<?> q = HibernateUtil.getSession().createQuery("from DocInfo item where item.id = :docId");
  
		
		
	}

}
