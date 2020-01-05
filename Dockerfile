FROM openjdk:8-jdk-alpine
RUN  apk update && apk upgrade && apk add netcat-openbsd
RUN  mkdir -p /usr/local/app
ADD  target/app.jar /usr/local/app/

EXPOSE 80

CMD  java -Xmx200m -Dspring.profiles.active=sql -Ddebug-true -jar /usr/local/app/app.jar

