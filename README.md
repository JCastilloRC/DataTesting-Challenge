# DataTesting-Challenge
Hello dear reader, this project contains the implementation of a JPA+Hibernate framework for testing of
a SQL database. A file (hibernateSettings.properties) is missing from the repo for security reasons
to run the program you may add the file to the /test/java/resources folder with the following structure:

PERSISTENCE_UNIT_NAME = persistence
URL = jdbc:mysql://localhost:XXXX/personas
USER = XXXX
PASSWORD = XXXX

then you just run it using maven i.e. mvn clean verify on console.
