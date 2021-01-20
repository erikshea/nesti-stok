package com.nesti.stock_manager.dao;

import com.nesti.stock_manager.model.Supplier;
import com.nesti.stock_manager.util.HibernateUtil;

public class SupplierDao extends BaseDao<Supplier> {


	public static Supplier getSupplier(int articleId) { 
        String hql = " FROM offers o  LEFT JOIN article a ON o.id_article = a.id_article\r\n"
        		+ "        LEFT JOIN supplier s ON o.id_supplier = s.id_supplier WHERE a.id = :my_parameter";
               
        var query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("my_parameter",articleId);
        var results = query.list();
        Supplier result = null;
        
        if (results.size() > 0) { // si le fournisseur existe, c'est le r�sultat. sinon il reste null
            result = (Supplier) results.get(0); 
        }
        return result;
    }
	
}