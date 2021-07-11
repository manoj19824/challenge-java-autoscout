FROM adoptopenjdk/openjdk11:slim
VOLUME /tmp
COPY /target/*-SNAPSHOT*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
