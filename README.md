# Library-Management-System
Getir - Spring Boot- final case.

Library Management System allows users(patron role) to list, search, get books with borrow, return and also view their history. Moreover, its librarians can operate crucial operations like adding books, deleting books and user as well as update user and books. Librarian can also view all users history and details.

## Technology Stack

- Java 21  
- Spring 6.2.6  
- Spring Boot 3.4.5  
- PostgreSQL  
- JUnit  
- Mockito  
- Docker

### To be able to run the application, just check docker-compose.yaml and make sure your PostgreSQL username, password and port match in file or not update it. After to do so,

**Open Docker Desktop Application (If you don't have you need to install https://docs.docker.com/desktop/)**
**Go to the project's path in your terminal.**
## be sure you are in a directory that you can see docker-compose.yaml file
**just run**
```
docker-compose up
```

**After you run this command, you will see an image consisting of postgre-db for database getir-project for application.**

## Note that you are not running feel free to click run button.

**Then here we are. App is up and running. You can send requests using postman collection that I uploadad in this directory. (getirlmscollection.json)**

## You can see and check all endpoints by visiting:
**http://localhost:8080/swagger-ui/index.html**


![image](https://github.com/user-attachments/assets/24ca3260-73d4-4a05-8c30-b1ed45e677ee)

![image](https://github.com/user-attachments/assets/9873c41b-4f90-4a5f-b0c0-1589409b6e02)

![image](https://github.com/user-attachments/assets/9bfe6b7b-9d54-4f16-beb8-cd956ce068ca)
## Schemas
![image](https://github.com/user-attachments/assets/93fa4ba3-74cf-4371-ab49-ca83a59508d3)

## Database Design
**I choose simplicity over complexity. I could have created tables for role,author, history so on and so forth. However, instead of making it full of table hell I chose to keep it simple as much as I can.**
![image](https://github.com/user-attachments/assets/08e021c3-ef9d-400d-8c3c-9b950741a338)

## If you cannot run the project with docker-compose up, you'd better check your db configuration in docker-compose.yaml file and edit it.


