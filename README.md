**

## Arquivei Recruitment Project

**

**Objectives**
Create a project that loads a list of NFEs from [https://sandbox-api.arquivei.com.br](https://sandbox-api.arquivei.com.br/) , store them in a local database and expose a API that returns the total value of a NFE for a givem access-key (unique for each NFE).

**How to run (with Docker)**

 - Check if ports 8080 and 5432 are not in use. 
 - Clone this repository. 
 -  Inside ArquiveiNFE directory run:
> docker-compose build

> docker-compose run

**Check Swagger Documentation**

You can check the API interface opening in the browser [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) (assuming you are running in you local machine)

**Check Database**

You can connect to the database and run queries in your PostgreSQL server of choice :
POSTGRES_CONNECTION: localhost

POSTGRES_PORT: 5432

POSTGRES_USER: postgres  

POSTGRES_PASSWORD: postgres  

POSTGRES_DB: postgres  
