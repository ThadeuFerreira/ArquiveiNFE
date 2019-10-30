
## Arquivei Recruitment Project


**Objectives**

Create a project that loads a list of NFEs from [https://sandbox-api.arquivei.com.br](https://sandbox-api.arquivei.com.br/) , store them in a local database and expose a API that returns the total value of a NFE for a givem access-key (unique for each NFE).

**How to run (with Docker)**

 - Check if ports 8080 and 5432 are not in use. 
 - Clone this repository. 
 - Inside ArquiveiNFE directory run:
 
> docker-compose build

> docker-compose run

**Check Swagger Documentation**

You can check the API interface opening in the browser [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) (assuming you are running in you local machine)

**How to use**

Load the data from Arquivei endpoint.
> curl -X GET "http://localhost:8080/api/v1/update" -H "accept: */*"

See all NFE and values
> curl -X GET "http://localhost:8080/api/v1/values" -H "accept: */*"

See a particular NFE value
>curl -X GET "http://localhost:8080/api/v1/value/35140330290824000104550010003715421390782397" -H "accept: */*"

**Check Database**

You can connect to the database and run queries in your PostgreSQL server of choice :

POSTGRES_CONNECTION: localhost
POSTGRES_PORT: 5432
POSTGRES_USER: postgres  
POSTGRES_PASSWORD: postgres  
POSTGRES_DB: postgres  

> select * from arquivei_nfe;

|id| access_key  | xml |
|--|--|--|
| 202 |  35140330290824000104550010003715421390782397| PG5mZVByb2MgeG1sbnM9Imh0dHA6Ly....|


> select * from local_nfe;

|id| access_key  | nfe_total_value|
|--|--|--|
| 202 |  35140330290824000104550010003715421390782397| 365.89 |

OBS: This project has been tested in Windows 10 Pro and Linux Ubunto 16.6 server
