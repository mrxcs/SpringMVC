package org.casadocodigo.loja.conf;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "org.casadocodigo.loja.repositories")
@Profile("local_db")
public class JPAConfiguration {

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] { "org.casadocodigo.loja.models", "org.casadocodigo.loja.repositories" });
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());
		return em;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost/wd43?useUnicode=yes&characterEncoding=UTF-8&characterSetResults=UTF-8");/*Char Enc também não funciona*/
		dataSource.setUsername("root");
		dataSource.setPassword("Kakame2010");
		return dataSource;
	}

	private Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");		
		properties.setProperty("hibernate.connection.characterEncoding", "utf8");/*Não funciona, teria que sobre sobre escrever parte de como hibernate funciona, no caso é mais fácil deixar ele criar a tabela (org.hibernate.dialect.MySQL5Dialect) depois alterar para UTF 8 direto no MySQL */
		properties.setProperty("hibernate.connection.useUnicode", "true");
		properties.setProperty("hibernate.connection.charSet", "UTF-8");
		properties.setProperty("connection.characterEncoding", "utf8");
		properties.setProperty("connection.useUnicode", "true");
		properties.setProperty("connection.charSet", "UTF-8");/**/
		/*properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5UTF8InnoDBDialect");/*Também não funcionou, ou não muda ou perde a habildiade de criar tabelas quando elas não existem,*/
		System.out.println(properties.getProperty("hibernate.dialect"));
		return properties;
	}
		
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}	

}
