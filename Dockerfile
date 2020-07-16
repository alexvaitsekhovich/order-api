FROM openjdk:12-alpine
WORKDIR /
ADD ./build/$ARTIFACT_NAME order-api.jar
# ADD ./target/order-api-1.0.0-spring-boot.jar order-api.jar
EXPOSE 8080
CMD java -jar -Dspring.profiles.active=dev order-api.jar
