# Design notes:

### A quick mini project with a couple of hours work that aiming to build 2 APIs as per requirements
1. View account list for a given user
2. View account transactions for a given account

### On a high level, this project has 4 database tables, namely User, Account, AccountPermission and Transaction.
1. An user could have one or more accounts
2. An account could have one or more transactions
3. Account permission is designed to check authorization for a given user for a given account, an authenticated user must also have permission
setup in this table to access an account. (An authenticated user must not able to access someone else's account)
4. Transactions table stores transaction details
5. In a real scenario, these tables should be split to their own microservices and databases. i.e. User service, account service, auth service and transaction service. 
For the sake of time limitations, we use tables in this instance to illustrate the concept.

### Authentication and Authorization
1. Authentication and authorization are conducted by JWT token through OIDC/Oauth2, I have set up a test Auth0 identity provider (IDP) tenant and a couple of associated applications.
i.e. 
   1.1 A SPA application that Auth0 provided out of the box to simulate front end web applications. (I am able to get access token from SPA in browser and call APIs in this project)
   1.2 Machine to Machine application, so that we can utilize client_credentials grant type for our Rest assured integration tests
2. Additional authorization is conducted by checking if user has correct role as well as if user has sufficient permission for a given account.(An authenticated user must not able to access someone else's account) 

### API documentation
1. Swagger UI can be accessed via http://localhost:8080/swagger-ui/index.html#/ 

### Testing
1. Rest assured integration test built for account and transaction end points in AccountControllerIntegrationTest. These tests cover
Authentication test, authorization test and integration test with pagination etc.
2. Database integration test with real postgres database via test containers with Docker in AccountRepositoryTestContainerIntegrationTest.
These tests making sure we could connect to database and able to save and retrieve data as per our expectations
3. Some unit tests to test data mappings etc
4. In real projects, we typically having code coverage around 90% -100%. Due to time constrains, only the most important tests were written in this project
