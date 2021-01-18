package test;

import dao.*;

public class TestFeatures {
	public static void main(String[] args) {
		var test = (new UnitDao()).findOneBy("idUnit", 3);
		
		System.out.println(test.getIdUnit());;
		
		

	}

}
