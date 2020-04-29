# Microblog
![](https://img.shields.io/badge/PROJECT%20TYPE-SCHOOL-blue?style=for-the-badge&logo=google-scholar)
![](https://img.shields.io/badge/LICENSE-UNLICENSE-brightgreen?style=for-the-badge)
![](https://img.shields.io/badge/IDE-INTELLIJ%20IDEA-blue?style=for-the-badge&logo=IntelliJ-IDEA)
![](https://img.shields.io/badge/language-Java-lightblue?style=for-the-badge&logo=java&logoColor=red)

## Overview 
School project consisting of a simple RESTful blog developed with Spring Boot.
Without registering, you can only view the posts already entered and the related comments. By default it is possible to register as basic users who can only comment on the posts. To register as a _ADMIN_ user it is necessary to change the _roles_ field of the _USER_ database table from _USER_ to _ADMIN_.

## Usage Guide
Once the Microblog is downloaded, you can open it with any IDE that supports the JDK (JDK 11 or higher is recommended).

### Web Application
After running the application, you must go to the browser at:  ```https://localhost:8443```. The browser will report the site as unsafe (because the certificate for HTTPS is self-signed), you will need to click on "Advanced" and then on "continue on localhost".

### RESTful API
To test the RESTful APIs it is possible to use an external software such as [Postman](https://www.postman.com/) (by disabling the "SSL cerificate verification" entry, as already written, the certificate is self-signed). Or you can use the [Microblog Client](https://github.com/Alessio789/MicroblogClient), which I developed.

### Database Console
The database used is H2 (in-memory database), to insert data permanently it is necessary to insert it in the [data.sql](https://github.com/Alessio789/Microblog/blob/master/src/main/resources/data.sql) file. To access the database console you need to run the application and go to the browser with the address:  ```https://localhost:8443/h2```.

 ## Swagger Documentation
 To see the swagger documentation you need to start the application and go to the link:
 ```https://localhost:8443/swagger-ui.html```
