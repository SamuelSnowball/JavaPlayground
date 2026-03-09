
# Commands #
cd demo && mvn spring-boot:run

# Adminer connection details #
System: PostgreSQL
Server: postgres # the docker container database name
Username: postgres
Password: postgres
Database: Leave blank

# To view the schema #
You need to select the drop down in the left panel, then you select the schema.

# Reset DB #
docker compose down -v 
docker compose up -d