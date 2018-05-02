package org.casadocodigo.loja.conf;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Profile("heroku_db")
public class JPAHerokuConfiguration {
	
	@Autowired
	private	Environment	environment;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws URISyntaxException {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] { "org.casadocodigo.loja.models" });
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());
		return em;
	}

	@Bean
	public DataSource dataSource() throws	URISyntaxException{
		DriverManagerDataSource	dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		URI dbUrl= new	URI(environment.getProperty("DATABASE_URL"));
		dataSource.setUrl("jdbc:postgresql://ynxuhswdaomjzn:89d7eb5aefe00ee6cec55989018448d0fea419a3bf7c3da1d7c6938c35965eb3@" + dbUrl.getHost() + ":" + dbUrl.getPort()+ "/" + dbUrl.getPath());
		/*dataSource.setUrl("jdbc:postgresql://user:pass@" + dbUrl.getHost() + ":" + dbUrl.getPort()+ "/" + dbUrl.getPath());*/
		dataSource.setUsername("ynxuhswdaomjzn");
		dataSource.setPassword("89d7eb5aefe00ee6cec55989018448d0fea419a3bf7c3da1d7c6938c35965eb3");
		return	dataSource;
	}

	private Properties additionalProperties() {
		Properties properties = new Properties();		
		properties.setProperty("hibernate.connection.characterEncoding", "utf8");
		properties.setProperty("hibernate.connection.useUnicode", "true");
		properties.setProperty("hibernate.connection.charSet", "UTF-8");
		properties.setProperty("connection.characterEncoding", "utf8");
		properties.setProperty("connection.useUnicode", "true");
		properties.setProperty("connection.charSet", "UTF-8");
		return properties;
	}
		
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}	

}
