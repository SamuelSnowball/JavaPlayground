package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;


public class IntegrationDaoLayerTest {
/*
  static MySQLContainer<?> mysql = new MySQLContainer<>(
    "mysql:9.1.0"
  );

  //CustomerService customerService;

  @BeforeAll
  static void beforeAll() {
    mysql.start();
  }

  @AfterAll
  static void afterAll() {
    mysql.stop();
  }

  @BeforeEach
  void setUp() {
    DBConnectionProvider connectionProvider = new DBConnectionProvider(
      mysql.getJdbcUrl(),
      mysql.getUsername(),
      mysql.getPassword()
    );
    customerService = new CustomerService(connectionProvider);
  }

  @Test
  void shouldGetCustomers() {
    customerService.createCustomer(new Customer(1L, "George"));
    customerService.createCustomer(new Customer(2L, "John"));

    List<Customer> customers = customerService.getAllCustomers();
    assertEquals(2, customers.size());
  }
    */

}