package com.example.demo.database;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

// Should I use this?
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Component
public class MyDataSource {
    
    @Bean
    public DataSource mysqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/mydatabase?serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("mysql");

        return dataSource;
    }
}
