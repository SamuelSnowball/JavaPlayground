
# JOOQ with code generation

1. Run the database with: docker-compose -f docker-compose-jooq.yml up --build
2. Run codegen with: cd demo && mvn clean compile exec:java -Dexec.mainClass=com.example.demo.DemoApplication -Dexec.cleanupDaemonThreads=false

So JOOQ will connect to the database, pull the schema and generate the entity classes from there. In this case, we can be sure that the generated SQL exactly matches the SQL in the database.

This application has nothing to do with Spring but I've left it to start as a springbootapp as I initially created it that way

Overview document: https://www.jooq.org/doc/latest/manual/code-generation/codegen-configuration/

You can run the jOOQ code generation jars manually, via eclipse or via the integration with Maven.

For setup via maven plugin, following the guide: https://hellokoding.com/jooq-example-with-mysql/ 

I needed to update all of the jOOQ versions as otherwise it complained about a JAXB dependency.

Note the sample database script from the jOOQ manual uses the oracle dialect so won't work with MySQL.

I am now following https://www.jooq.org/doc/latest/manual/getting-started/tutorials/jooq-in-7-steps/jooq-in-7-steps-step2/ 

Some of the codegen packages were renamed between versions, so I changed the driver package name in the pom file.

Debugging
- Delete the data folder / volume here: C:\Users\SamSnowball\Desktop\Projects\jooqmysqlvolume
- Recreate the container

Note the logs say root@localhost is created with an empty password so you can't login via adminer, not an issue for now.

This is the contents of the schema.sql file we pass in as a volume:

```
create database if not exists mydatabase;
use mydatabase;
CREATE TABLE `author` (
  `id` int NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
-- The below insert statements didn't know, didn't look into why 
-- INSERT INTO author(1, 'Hello', 'Koding');
-- INSERT INTO author(2, 'jOOQ', 'Example');
```

