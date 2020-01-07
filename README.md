# azure-webapp-spring-data

Sample Java Web Application deployed to Azure using Docker, Spring Boot, and Spring Data

## Links

### Spring Framework

- [Spring Framework](https://spring.io)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data and JPA](https://spring.io/projects/spring-data)
- [Spring Initializr](https://start.spring.io)
- [Spring Boot with Docker](https://spring.io/guides/gs/spring-boot-docker/)

### Docker

- [Docker](https://www.docker.com)
- [Docker Hub](https://hub.docker.com)

See **Azure Container Registry**, below.

### Azure

- [Azure SQL DB](https://azure.microsoft.com/en-us/services/sql-database/)
- [Azure App Service for Containers](https://azure.microsoft.com/en-us/services/app-service/containers/)
- [Azure CLI (Command Line Interface)](https://docs.microsoft.com/en-us/cli/azure/?view=azure-cli-latest)
- [Azure SQL DB with Spring Data JPA)](https://docs.microsoft.com/en-us/azure/java/spring-framework/configure-spring-data-jpa-with-azure-sql-server)
- [Azure Container Registry](https://azure.microsoft.com/en-us/services/container-registry/)

---

## This Project

### Workstation Requirements

- Java 8
- Maven
- Docker
- Azure CLI
- Bash shell scripts in this repo are oriented to linux and macOS, but can be easily adapted for Windows PowerShell

Alternatively, you can use an [Azure Data Science Virtual Machine](
https://azure.microsoft.com/en-us/services/virtual-machines/data-science-virtual-machines/)
or [Azure DevOps](https://azure.microsoft.com/en-us/services/devops/)
to build and deploy your application.

### Clone this Repo

Clone this repository and use it as your starting point:
```
$ git clone https://github.com/cjoakim/azure-webapp-spring-data
```

**Rename the cjoakim/xxx container image per your Dockerhub account and/or your Azure Container Registry**

### Database Setup

- Provision an **Azure SQL Database**, without the AdventureWorksLT sample database
- Create and populate the **Airports** and **Todo** tables with the following DDL files and a DB UI client:
  - ddl/airports.ddl
  - ddl/todo.ddl

airports.ddl
```
DROP TABLE IF EXISTS dbo.Airports;
GO

CREATE TABLE dbo.Airports(
  id            INT  IDENTITY  PRIMARY KEY,
  name          varchar(128) NOT NULL,
  city          varchar(128) NOT NULL,
  country       varchar(128) NOT NULL,
  iata_code     varchar(12)  NOT NULL UNIQUE,
  icao_code     varchar(12)  NOT NULL,
  latitude      float NOT NULL,
  longitude     float NOT NULL,
  altitude      float NOT NULL,
  timezone_num  float NOT NULL,
  dst           varchar(12)  NOT NULL,
  timezone_code varchar(128) NOT NULL
);
GO

INSERT INTO dbo.Airports (name,city,country,iata_code,icao_code,latitude,longitude,altitude,timezone_num,dst,timezone_code) VALUES ('Goroka','Goroka','Papua New Guinea','GKA','AYGA',-6.081689,145.391881,5282,10,'U','Pacific/Port_Moresby')
... many INSERT statements ...
```


The resulting Airports table will contain 5240 world airports:
```
select count(id) from Airports
5240
```

## Development

### Environment Variables

This app expects the following **environment variables** to be set per your Azure Resources:
```
AZURE_SQL_DATABASE
SPRING_DATASOURCE_INITIALIZATION_MODE
SPRING_DATASOURCE_PLATFORM
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
```

Modify file **app-env.sh**  per your Azure Resource values.

Do **not** embed the environment variable **values** into your cloned application code.


### Compile and Create the Docker Container

```
$ build.sh
```

### Execute and test the Docker container locally with Compose

```
$ ./compose.sh up
$ ./compose.sh ps
$ ./compose.sh down
```

Note that the Spring Boot application is packaged as a **jar file, including all dependencies**,
and it includes the **Tomcat Server**.

```
Creating azure-webapp-spring-data_web_1 ... done
Attaching to azure-webapp-spring-data_web_1
web_1  |
web_1  |   .   ____          _            __ _ _
web_1  |  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
web_1  | ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
web_1  |  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
web_1  |   '  |____| .__|_| |_|_| |_\__, | / / / /
web_1  |  =========|_|==============|___/=/_/_/_/
web_1  |  :: Spring Boot ::        (v2.2.2.RELEASE)
web_1  |
web_1  | 2020-01-07 18:19:12.715  INFO 1 --- [           main] c.c.azure.sdweb.SdwebApplication         : Starting SdwebApplication v0.1.0 on ee8fc9f07ed5 with PID 1 (/usr/local/app/app.jar started by root in /)
web_1  | 2020-01-07 18:19:12.719  INFO 1 --- [           main] c.c.azure.sdweb.SdwebApplication         : The following profiles are active: sql
web_1  | 2020-01-07 18:19:13.864  INFO 1 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
web_1  | 2020-01-07 18:19:13.949  INFO 1 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 66ms. Found 2 JPA repository interfaces.
web_1  | 2020-01-07 18:19:14.348  INFO 1 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration' of type [org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
web_1  | 2020-01-07 18:19:14.719  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 80 (http)
web_1  | 2020-01-07 18:19:14.738  INFO 1 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
web_1  | 2020-01-07 18:19:14.738  INFO 1 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.29]
web_1  | 2020-01-07 18:19:14.816  INFO 1 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
...
web_1  | 2020-01-07 18:19:19.408  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 80 (http) with context path ''
web_1  | 2020-01-07 18:19:19.411  INFO 1 --- [           main] c.c.azure.sdweb.SdwebApplication         : Started SdwebApplication in 7.298 seconds (JVM running for 7.941)
```

Invoke the local app with curl.  
See file [create-azure-webapp.sh](admin/api-curls.sh)

### Push Docker Image Docker Hub and/or ACR

```
$ cd admin
$ ./push-to-registies.sh both
```

See file [push-to-registies](admin/push-to-registies.sh)

### Deploy the Container to an Azure App Service with the CLI

```
$ cd admin
$ ./create-azure-webapp.sh
```

See file [create-azure-webapp.sh](admin/create-azure-webapp.sh)

Invoke the deployed app in Azure with the same curl script.
See file [create-azure-webapp.sh](admin/api-curls.sh)

For example:

```
$ curl "http://cjoakim-spring-data.azurewebsites.net:80/airports/findByIataCode/CLT" | jq

{
  "id": 2812,
  "name": "Charlotte Douglas Intl",
  "city": "Charlotte",
  "country": "United States",
  "iataCode": "CLT",
  "icaoCode": "KCLT",
  "latitude": 35.214,
  "longitude": -80.943139,
  "altitude": 748,
  "timezoneNum": -5,
  "dst": "A",
  "timezoneCode": "America/New_York"
}
```

---

## Spring Data Sample Code 

This project uses the JPA, the **Java Persistence API**, with Spring.

See https://spring.io/guides/gs/accessing-data-jpa/

### Model

```
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Airports")
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ddl is -> id  INT  IDENTITY  PRIMARY KEY,
    private Integer id;

    private String name;
    private String city;
    private String country;
    private String iataCode;
    private String icaoCode;
    private Double latitude;
    private Double longitude;
    private Double altitude;
    private Double timezoneNum;
    private String dst;
    private String timezoneCode;

    ... getter, setter, and toString() methods not shown ...
```

### Repository

See https://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html

The following is the **entire code** for the **Spring Data Repository** which accesses the database.

Notice how it is simply a Java Interface, and the code is synthesized by the Spring Data framework
(i.e. - no SQL statements are required).  Explicit SQL can be provided, however.  This design was inspired
by the "Active Record" library in the Ruby on Rails web framework.

```
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chrisjoakim.azure.sdweb.models.Airport;

@Repository
public interface AirportRepository extends CrudRepository<Airport, Integer> {
		
	Optional<Airport> findById(Integer id);
	
	Optional<Airport> findByIataCode(String code);
	
	List<Airport> findByCity(String city);
	
	List<Airport> findByCountry(String country);
}
```

---

## Alternatives

- [Deploy this same container to Azure Kubernetes Service (AKS)](https://azure.microsoft.com/en-us/services/kubernetes-service/)
- [.Net Core and ASP.NET Core](https://docs.microsoft.com/en-us/aspnet/core/?view=aspnetcore-3.1)
