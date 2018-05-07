package org.casadocodigo.loja.daos;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.casadocodigo.loja.conf.JPAConfiguration;
import org.casadocodigo.loja.conf.JPAlocalPostgreeSQLConfiguration;
import org.casadocodigo.loja.conf.SecurityConfiguration;
import org.casadocodigo.loja.models.Role;
import org.casadocodigo.loja.models.AUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(classes={JPAConfiguration.class, JPAlocalPostgreeSQLConfiguration.class, AUser.class, Role.class, UserDAO.class, SecurityConfiguration.class} )
@Transactional
/*@ActiveProfiles("local_db")*/
@ActiveProfiles("localPostgree_db")
public class AUserDAOTest {
	
	@Autowired	
	private UserDAO DAO;

	@Test
	public void test() {
		
		List<Role> acessos = new ArrayList<>();
		Role acesso = new Role();
		acesso.setName("ROLE_ADMIN");
		acessos.add(acesso);
		
		AUser user = new AUser();
		user.setLogin("mrxcs");
		user.setPassword("123456");
		user.setMatchingPassword("123456");
		user.setName("adminTesteN");
		user.setRoles(acessos);
		
		
		DAO.novo(user);
		assertEquals(DAO.loadUserByUsername(user.getLogin()).getLogin(), user.getLogin());
		
		user.setName("Marcelo R Moreira");
		DAO.update(user);
		assertEquals(DAO.loadUserByUsername(user.getLogin()).getLogin(), user.getLogin());
		
		DAO.disable(user);
		AUser retorno = DAO.loadUserByUsername(user.getLogin());
		assertEquals(retorno.isEnabled(), false);
		
		DAO.enable(user);
		retorno = DAO.loadUserByUsername(user.getLogin());
		assertEquals(retorno.isEnabled(), true);
		
		for (GrantedAuthority i : retorno.getAuthorities()) {
			System.out.println(i.getAuthority());
		}
		
		
		
		
		
		
	}

}
