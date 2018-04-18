package org.casadocodigo.loja.daos;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.casadocodigo.loja.conf.JPAConfiguration;
import org.casadocodigo.loja.conf.SecurityConfiguration;
import org.casadocodigo.loja.models.Role;
import org.casadocodigo.loja.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(classes={JPAConfiguration.class, User.class, Role.class, UserDAO.class, SecurityConfiguration.class} )
@Transactional
public class UserDAOTest {
	
	@Autowired	
	private UserDAO DAO;

	@Test
	public void test() {
		
		List<Role> acessos = new ArrayList<>();
		Role acesso = new Role();
		acesso.setName("ROLE_ADMIN");
		acessos.add(acesso);
		
		User user = new User();
		user.setLogin("mrxcs");
		user.setPassword("123456");
		user.setMatchingPassword("123456");
		user.setName("adminTesteN");
		user.setRoles(acessos);
		
		
		DAO.novo(user);
		assertEquals(DAO.loadUserByUsername(user.getLogin()).getLogin(), user.getLogin());
		
		user.setName("Mudou");
		DAO.update(user);
		assertEquals(DAO.loadUserByUsername(user.getLogin()).getLogin(), user.getLogin());
		
		DAO.disable(user);
		User retorno = DAO.loadUserByUsername(user.getLogin());
		assertEquals(retorno.isEnabled(), false);
		
		DAO.enable(user);
		retorno = DAO.loadUserByUsername(user.getLogin());
		assertEquals(retorno.isEnabled(), true);
		
		for (GrantedAuthority i : retorno.getAuthorities()) {
			System.out.println(i.getAuthority());
		}
		
		
		
		
		
		
	}

}
