
Technologies Used :

1. Java11
2. Spring Boot and microservice.
3. Docker.
4. OpenApi Specification for the endpoints.
6. In memory H2 based solution with offline storage.
7. Spring data JPA.

How to build and run the application :

1. After importing the project just do mvn clean install and run the ChallengeApplication.

2. The swagger ui is hosted and you can directly access using the below url and invoke the rest end points.

http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config#/task/getPercentageDistByMake

4.You can access the H2 database by using the below URL to see the entities.

http://localhost:8080/h2/

5.The application is fully dockerized and you can run the below commands to create and execute the container.

create image - docker build -t manoj/challenge-java-autoscout-docker . 
run container - docker run -p 8080:8080 manoj/challenge-java-autoscout-docker

For testing upload the swagger ui and upload the csv files listing and contacts separately.

Once the data is stored in the database you can start executing the reports based on the endpoints given.

These are the end points for accessing and testing the reports.

POST
​/api​/csv​/upload
Upload multi part file
GET
​/api​/reports​/most-contacted-avg-price
GET
​/api​/reports​/distribution
GET
​/api​/reports​/contact-listings-per-month
GET
​/api​/reports​/avg_listing_selling_price


The test cases covered for the controller and service layer. But we can write more tests cases.

As busy with my personal and professional commitments not have much time for writing 100% test cases.

I have not written enough test but just given some test case for illustration purpose.






