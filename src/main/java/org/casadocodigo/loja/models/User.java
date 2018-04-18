package org.casadocodigo.loja.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;

import org.casadocodigo.loja.annotation.PasswordMatches;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@PasswordMatches
public class User implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Size(min=5, max=20, message="Entre {2} e {1} caracteres")
	private String login;
	@NotBlank
	private String password;
	private String matchingPassword;
	@Size(min=5, max=40, message="Entre {2} e {1} caracteres")
	private String name;
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Role> roles = new ArrayList<>();
	private Boolean enabled = true;
	
	@Override
	public String getUsername() {
		return login;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	@Override
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}
	
	public void setRoles(List<Role> r) {
		this.roles = r;
	}
	
	public List<Role> getRoles(){
		return this.roles;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(Boolean b) {
		this.enabled = b;
	}
	public String getMatchingPassword() {
		return matchingPassword;
	}
	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}
	
}
