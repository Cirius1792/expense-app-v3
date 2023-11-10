FROM amazoncorretto:17.0.5-alpine
COPY ./deploy/expenses-app.jar app.jar
CMD java -jar /app.jar
