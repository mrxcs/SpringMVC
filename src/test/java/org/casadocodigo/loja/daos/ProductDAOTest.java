package org.casadocodigo.loja.daos;

import static org.junit.Assert.*;

import javax.transaction.Transactional;

import org.casadocodigo.loja.models.Product;
import org.casadocodigo.loja.models.ProductTest;
import org.casadocodigo.loja.repositories.ProductDAO_SpringDataJPA_Interface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.casadocodigo.loja.conf.JPAConfiguration;
import org.casadocodigo.loja.conf.JPAlocalPostgreeSQLConfiguration;


@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(classes={JPAConfiguration.class, JPAlocalPostgreeSQLConfiguration.class, ProductDAO.class} )
@Transactional
@ActiveProfiles("local_db")
/*@ActiveProfiles("localPostgree_db")*/
public class ProductDAOTest {

	@Autowired	
	private ProductDAO DAO;
	
	@Autowired	
	private ProductDAO_SpringDataJPA_Interface SpringDAO;

	@Test
	public void CRUDTeste() {
		
		ProductTest teste = new ProductTest();				
		Product p = teste.retornaProdutoSemID();
		
		if (DAO == null) { System.out.println("Yes");}
		Integer ID = DAO.save(p);
		p.setId(ID);		
		Product pReturned = DAO.find(ID);
		assertEquals(p.toString(), pReturned.toString());
				
		p.setPages(143);
		DAO.save(p);
		pReturned = DAO.find(ID);
		assertEquals(p.getPages(), pReturned.getPages());
		
		DAO.delete(ID);
		try {
			pReturned = DAO.find(ID);
		} catch (javax.persistence.NoResultException e) {
			pReturned = null;
		}
		assertEquals(pReturned, null);	
		
	}
	
	@Test
	public void SpringDataJPA() {
		
		ProductTest teste = new ProductTest();				
		Product p = teste.retornaProdutoSemID();
		
		Product resposta = SpringDAO.save(p);
		p.setId(resposta.getId());		
		Product pReturned = SpringDAO.findById(resposta.getId());
		assertEquals(p.toString(), pReturned.toString());
				
		p.setPages(143);
		pReturned = SpringDAO.save(p);
		assertEquals(p.getPages(), pReturned.getPages());
		
		SpringDAO.delete(p);
		try {
			pReturned = SpringDAO.findById(p.getId());
		} catch (javax.persistence.NoResultException e) {
			pReturned = null;
		}
		assertEquals(pReturned, null);	
		
	}

}
