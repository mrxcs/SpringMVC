package org.casadocodigo.loja.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.casadocodigo.loja.models.Product;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDAO {
	
	@PersistenceContext
	private EntityManager manager;
	
	public Integer save(Product product) {
		
		if (product.getId() == null) {
			System.out.println(product);
			manager.persist(product);
			System.out.println("2");
		} else {
			update(product);
		}
		
		manager.flush();
		return  product.getId();

	}

	public List<Product> list() {
		return manager.createQuery("select distinct(p) from "
				+ "Product p join fetch p.prices",Product.class)
				.getResultList();
	}
	
	public void delete(Integer productID) {
		Product p = manager.find(Product.class, productID);
		manager.remove(p);
	}
	
	private void update(Product product) {
		manager.merge(product);
	}
	
	public Product find(Integer productID) {
		
		Product p = manager.createQuery("select distinct(p) from "
				+ "Product p join fetch p.prices WHERE p.id="+productID+"",Product.class)
				.getSingleResult();
		return p;
	}

}
