# SawitPro JWT

## How to use it
If you wanna run only the database execute this
```shell
docker-compose up -d db
```
and change the urls in properties
from
```properties
spring.datasource.url = jdbc:postgresql://db:5432/sawitprodb
```
to
```properties
spring.datasource.url = jdbc:postgresql://localhost:5432/sawitprodb
```

if just wanna use it direcly no need to change just execute this
```shell
docker-compose up -d
```
and the server will be running in port `8080`

Due the issue from Java Swagger itself dont wanna send the header, the Postman Testing file already generated
just import the file in the root of project with name `SawitPro.postman_collection.json`
