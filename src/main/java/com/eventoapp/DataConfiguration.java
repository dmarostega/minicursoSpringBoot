package com.eventoapp;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class DataConfiguration {
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/eventoapp");
		dataSource.setUsername("root");
		dataSource.setPassword("");
		return dataSource;		
	}
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		//criando conexao com hibernate
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		//Qual database 
		adapter.setDatabase(Database.MYSQL);
		//true: mostra todas as etapas ao executar sql
		adapter.setShowSql(true);
		//true: permite hibernate crie as tabelas automaticamento
		adapter.setGenerateDdl(true);
		//dialeto que será utilizado..
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
		//prepara o sql automaticqment ????
		adapter.setPrepareConnection(true);
		return adapter;
	}
}
