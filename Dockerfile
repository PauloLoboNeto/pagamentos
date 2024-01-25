FROM maven:3.8.5-openjdk-17 AS build
COPY . /root/app/
WORKDIR /root/app
RUN mvn clean package

FROM openjdk:17-jdk-slim
EXPOSE 9090
COPY --from=build /root/app/ /home/app/
WORKDIR /home/app
ENTRYPOINT ["java", "-jar", "-Xmx1512m", "./target/fiappagamentos-1.0.0.jar"]

