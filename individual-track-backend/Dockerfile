FROM openjdk:17 as build
#LABEL maintainer=""
COPY . /app
CMD java -jar /app/build/libs/school-0.0.1-SNAPSHOT.jar


#FROM openjdk:11 as build
#LABEL maintainer="alexVpenev"
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]