# Person API

This is a simple RESTful API for managing Person entities.

## Technologies used
* Java 19
* Spring Boot
* MapStruct 
* PostgreSql
* Docker

##   Installation

1.   Clone the repository  
`git clone https://github.com/<your_username>/person-api.git
`
2. Build the application using Maven  
   `cd person-api
   mvn clean package`
3. Start the application using Docker  
`docker build -t person-api .docker run -p 8080:8080 -t person-api`

##    Usage
The API supports the following operations:

### Get all Persons
`GET /v1/persons`
### Get a Person by ID
`GET /v1/persons/{id}`
### Create a Person
`POST /v1/persons`  
Request body:
`{
"firstName": "John",
"lastName": "Doe",
"age": 30
}`
### Update a Person
`PATCH /v1/persons/{id}`  
Request body:
`{
"firstName": "Jane",
"lastName": "Doe",
"age": 35
}`
### Delete a Person 
`DELETE /v1/persons/{id}`

