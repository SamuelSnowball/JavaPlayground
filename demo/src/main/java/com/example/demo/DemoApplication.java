package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

// JOOQ code generation
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import java.sql.Connection;
import java.sql.DriverManager;

import static com.example.demo.generatedclasses.Tables.*;

@EnableAsync
@SpringBootApplication
public class DemoApplication {
    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	/*
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	*/

	
	public static void main(String[] args) {
        String user = System.getProperty("jdbc.user");
        String password = System.getProperty("jdbc.password");
        String url = System.getProperty("jdbc.url");
        String driver = System.getProperty("jdbc.driver");

        logger.info("Hello");

        
        try {
            Class.forName(driver).newInstance();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);
            Result<Record> result = dslContext.select().from(AUTHOR).fetch();

            for (Record r : result) {
                Integer id = r.getValue(AUTHOR.ID);
                String firstName = r.getValue(AUTHOR.FIRST_NAME);
                String lastName = r.getValue(AUTHOR.LAST_NAME);

                System.out.println("ID: " + id + " first name: " + firstName + " last name: " + lastName);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

		SpringApplication.run(DemoApplication.class, args);
	}


}
