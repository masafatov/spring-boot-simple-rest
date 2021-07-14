#### Description
App consuming RESTful service, save data in mongoDB and produce simple RESTful web service.

Cron job timer runs every 10 seconds and pulls cryptocurrency last prices from [cex.io](https://cex.io/rest-api). App pulls last prices for the pairs: BTC/USD, ETH/USD and XRP/USD. To change pairs - edit application.properties file. 

REST Endpoints  
GET ```/cryptocurrencies/minprice?name=[currency_name]``` - return record with the lowest price of selected cryptocurrency.  
GET ```/cryptocurrencies/maxprice?name=[currency_name]``` - return record with the highest price of selected cryptocurrency.  
GET ```/cryptocurrencies?name=[currency_name]&page=[page_number]&size=[page_size]``` - return a selected page with selected number of elements from lowest to highest. Default values page=0, size=10.  
GET ```/cryptocurrencies/csv``` - return report with the following fields: Cryptocurrency Name, Min Price, Max Price. 

#### How to start
Setup database connection in application.properties.

```
$ git clone https://github.com/masafatov/spring-boot-simple-rest.git 
$ cd spring-boot-simple-rest
$ mvn spring-boot:run
```
