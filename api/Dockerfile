FROM khipu/openjdk17-alpine

RUN mkdir /app

COPY target/pro-manager-api-0.0.1-SNAPSHOT.jar /app/pro-manager-api-0.0.1-SNAPSHOT.jar

EXPOSE 8080

WORKDIR /app

CMD ls -l && java -jar pro-manager-api-0.0.1-SNAPSHOT.jar