package org.casadocodigo.loja.repositories;

import java.util.List;

import org.casadocodigo.loja.models.Product;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface ProductDAO_SpringDataJPA_Interface extends	Repository<Product,	Integer>{

	public	List<Product> findByPagesGreaterThan(@Param("pages")int pages);
	
	public	List<Product> findAll();
	
	public	Product	save (Product product);
	
	public void delete (Product product);
	
	public Product findById (Integer ID);
	
	/*https://docs.spring.io/spring-data/jpa/docs/2.0.7.RELEASE/reference/html/#jpa.query-methods.at-query*/
	/*https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html*/
}
