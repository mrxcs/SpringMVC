package org.casadocodigo.loja.daos;

import javax.transaction.Transactional;

import org.casadocodigo.loja.conf.JPAConfiguration;

import org.casadocodigo.loja.models.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(classes={JPAConfiguration.class, Role.class, RoleDAO.class} )
@Transactional
public class RoleDAOTest {
	
	@Autowired
	RoleDAO dao;

	@Test(expected=IllegalArgumentException.class)
	public void nomeInvalido() {
		Role n1 = new Role();
		n1.setName("falho");
		dao.save(n1);	
	}
	
	@Test
	public void inserir() {
		Role n2 = new Role();
		n2.setName("ROLE_teste");
		dao.save(n2);
		
		for (Role i : dao.getRoleList()) {
			System.out.println(i);
		}
	}

}
