# Getting Started
 
# Basic structure


```plaintext
medical-app-exams/
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── medical/
│   │   │           └── app/
│   │   │               └── exams/
│   │   │                   ├── ExamsApplication.java
│   │   │                   ├── config/
│   │   │                   │   ├── OpenAPIConfig.java
│   │   │                   │   ├── SecurityConfig.java
│   │   │                   │   └── JwtAuthenticationFilter.java
│   │   │                   ├── controller/
│   │   │                   │   ├── AuthController.java
│   │   │                   │   ├── ExamController.java
│   │   │                   │   ├── ExamAnalysisController.java
│   │   │                   │   ├── ExamSchedulingController.java
│   │   │                   │   ├── AuditController.java
│   │   │                   │   └── MonitoringController.java
│   │   │                   ├── entity/
│   │   │                   │   ├── Organization.java
│   │   │                   │   ├── Patient.java
│   │   │                   │   ├── ExamAnalysis.java
│   │   │                   │   ├── ExamAnalysisAudit.java
│   │   │                   │   ├── AuthToken.java
│   │   │                   │   ├── MedicalExam.java
│   │   │                   │   └── ExamScheduling.java
│   │   │                   ├── repository/
│   │   │                   │   ├── OrganizationRepository.java
│   │   │                   │   ├── PatientRepository.java
│   │   │                   │   ├── ExamAnalysisRepository.java
│   │   │                   │   ├── ExamAnalysisAuditRepository.java
│   │   │                   │   ├── AuthTokenRepository.java
│   │   │                   │   ├── MedicalExamRepository.java
│   │   │                   │   └── ExamSchedulingRepository.java
│   │   │                   ├── service/
│   │   │                   │   ├── OrganizationResolver.java
│   │   │                   │   ├── AuthTokenService.java
│   │   │                   │   ├── ExamService.java
│   │   │                   │   ├── ExamAnalysisService.java
│   │   │                   │   ├── ExamAnalysisAuditService.java
│   │   │                   │   └── ExamSchedulingService.java
│   │   │                   └── dto/                (optional – DTOs can be records inside controllers)
│   │   └── resources/
│   │       ├── application.yml
│   │       └── .env                               (optional; can be placed in project root)
│   └── test/
│       └── java/
│           └── com/
│               └── medical/
│                   └── app/
│                       └── exams/
│                           └── ExamsApplicationTests.java
├── .gitattributes
├── .gitignore
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md                                        (this file)
```

This tree reflects the exact package and file organization we have built throughout the conversation.

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/4.0.2/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/4.0.2/maven-plugin/build-image.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/4.0.2/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/4.0.2/reference/using/devtools.html)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/4.0.2/reference/web/reactive.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

### Instructions to run

``bash
java -jar target/exams-0.0.1-SNAPSHOT.jar --server.port=9999
``bash

- Base url: http://localhost:9999/swagger-ui.html
- swagger: http://localhost:9999/swagger-ui/index.html

<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/a438b7ff-a135-4e5b-8bb8-6db32670e589" />

