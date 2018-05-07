package org.casadocodigo.loja.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService user;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    auth.userDetailsService(user).passwordEncoder(passwordEncoder());	    
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
	http.authorizeRequests()
	.antMatchers("/produtos/form").hasRole("Admin")
	.antMatchers("/produtos/form/").hasRole("Admin")
	.antMatchers(HttpMethod.GET, "/produtos/").hasRole("Admin")
	.antMatchers(HttpMethod.POST,"/produtos/").hasRole("Admin")
	.antMatchers("/produtos").hasRole("Admin")
	.antMatchers("/account/admin").hasRole("Admin")
	.antMatchers("/shopping/**").permitAll()
	.antMatchers("/produtos/**").permitAll()
	.antMatchers("/account/**").permitAll()
	.anyRequest().authenticated()
	.and().exceptionHandling().accessDeniedPage("/403")
	.and().formLogin()
    .loginPage("/acesso")
    .permitAll()
    .failureUrl("/acesso?error=true")
    .and()
    .logout().permitAll()
    .logoutSuccessUrl("/acesso?logout=true");
 	}
	
	@Override
	public void configure(WebSecurity	web) throws	Exception	{
		web.ignoring().antMatchers("/resources/**");
	}

}
