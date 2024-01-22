Rest service with the following requirements
1) Make a REST service using JDBC and Servlet
2) Any functionality to choose from, a minimum CRUD service with several types of entity
3) It is forbidden to use Spring, Hibernate, Lombok
4) You can use Hikari CP, Mapstruckt
5) The database connection parameters should be placed in a file
6) The ManayToOne(OneToMany), ManyToMany links must be implemented https://en.wikibooks.org/wiki/Java_Persistence/ManyToOne , https://en.wikipedia.org/wiki/Many-to-many_ (data_model), https://en.wikipedia.org/wiki/One-to-many_ (data_model)
7) Relationships should be reflected in the code (there should be corresponding collections within the entity)
8) The servlet must return a DTO, we do not return an Entity, we also accept a DTO
9) There must be the right architecture https://habr.com/ru/articles/269589/
10) There should be a service layer
11) The principles of OOP, Solid must be respected
12) There should be unit tests, use Mockito and Junit, to check the work of the repository (DAO) with the database, use testcontainers: https://testcontainers.com /, https://habr.com/ru/articles/444982/
13) The test coverage should be more than 80%
14) All layers of the application must be tested
15) Servlet layer, service layer to test using Mockito
16) Database of your choice Pestgres, MySQL
17) Install the SonarLint plugin
