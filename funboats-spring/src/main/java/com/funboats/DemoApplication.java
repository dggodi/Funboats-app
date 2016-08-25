package com.funboats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication  
@ComponentScan("com.funboats.controllers, com.funboats.repositories")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	/*
	@Bean
    public DataSource getDataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
        dataSource.setUrl("xxx");
        dataSource.setUsername("xxx");
        dataSource.setPassword("xxx");
        return dataSource;
    }
	
	@Bean
    public JdbcTemplate getJdbcTemplate() {

        return new JdbcTemplate(getDataSource());
    }
    */
}
