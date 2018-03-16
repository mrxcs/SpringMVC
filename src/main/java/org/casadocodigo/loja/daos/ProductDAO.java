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
	
	public void save(Product product) {
		
		if (product.getId() == null) {
			manager.persist(product);
		} else {
			update(product);
		}

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
	
	public void update(Product product) {
		manager.merge(product);
	}
	
	public Product find(Integer productID) {
		
		Product p = manager.createQuery("select distinct(p) from "
				+ "Product p join fetch p.prices WHERE p.id="+productID+"",Product.class)
				.getSingleResult();
		return p;
	}

}
