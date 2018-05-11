package org.casadocodigo.loja.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.casadocodigo.loja.models.Product;
import org.casadocodigo.loja.repositories.ProductDAO_SpringDataJPA_Interface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDAO {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private ProductDAO_SpringDataJPA_Interface SpringDAO;
	
	@CacheEvict(value="ProductList", allEntries=true)
	public Integer save(Product product) {
		
		/*if (product.getId() == null) {
			manager.persist(product);
		} else {
			update(product);
		}
		
		manager.flush();
		return  product.getId();*/

		return SpringDAO.save(product).getId();
	}

	@Cacheable("ProductList")
	public List<Product> list() {
		return manager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
		/*return manager.createQuery("select distinct(p) from "
				+ "Product p join fetch p.prices",Product.class)
				.getResultList();*/
	}
	
	public void delete(Integer productID) {
		Product p = manager.find(Product.class, productID);
		manager.remove(p);
	}
	
	/*private void update(Product product) {
		manager.merge(product);
	}*/
	
	public Product find(Integer productID) {
		return manager.createQuery("SELECT p FROM Product p WHERE p.id="+productID+"", Product.class).getSingleResult();
		/*Product p = manager.createQuery("select distinct(p) from "
				+ "Product p join fetch p.prices WHERE p.id="+productID+"",Product.class)
				.getSingleResult();
		return p;*/
	}

}
