package org.casadocodigo.loja.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.casadocodigo.loja.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO implements UserDetailsService{
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private PasswordEncoder Encoder;
	
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
	String jpql = "select u from User u	where u.login = :login";
	List<User> users = em.createQuery(jpql,User.class).setParameter("login", username).getResultList();
	if(users.isEmpty()){
		throw new UsernameNotFoundException ("O usuario "+username+" não existe");
	 }
	System.out.println(users.get(0).getLogin());
	System.out.println(users.get(0).getPassword());
	return users.get(0);
	}
	
	public String save(User user) {
		Boolean exists = true;
		
		System.out.println("No DAO: "+ user.getLogin()+ " e " + user.getName());
		
		try {
			loadUserByUsername(user.getUsername());
		} catch (UsernameNotFoundException e) {
			exists = false;
		}
		if (!(exists)) {
			System.out.println("Cadastrando "+user.getUsername());
			user.setPassword(encodePassword(user.getPassword()));
			em.persist(user);
		} else {
			update(user);
		}
		em.flush();
		return  user.getUsername(); 
	}
	
	private void update(User user) {
		User dbUser = loadUserByUsername(user.getUsername());
		if (!(user.getPassword().equals(dbUser.getPassword()))) {
			user.setPassword(encodePassword(user.getPassword()));
		}
		em.merge(user);
	}
	
	public void enable (User user) {
		user.setEnabled(true);
		save(user);
	}
	
	public void disable (User user) {
		user.setEnabled(false);
		save(user);
	}
	
	private String encodePassword (String p) {
		CharSequence pass = p;
		return Encoder.encode(pass);	
	}
	
}
