package org.casadocodigo.loja.daos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.casadocodigo.loja.models.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	public String save(Role role) throws IllegalArgumentException{
		if (!(role.getAuthority().startsWith("ROLE_"))){
			throw new IllegalArgumentException ("Deve começar com 'ROLE_'"); 					
		}
		em.persist(role);
		em.flush();
		return role.getAuthority();
	}

	public List<Role> getRoleList(){
		String jpql = "select i from Role i";
		try {
		List<Role> roles = em.createQuery(jpql,Role.class).getResultList();
		return roles;
		}
		catch (Error e) {
			return new ArrayList<Role>();
		}
	}
}
