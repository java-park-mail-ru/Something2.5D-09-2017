FROM maven:latest

COPY ./target/ /server/target/

EXPOSE 8080

CMD ["/usr/bin/java", "-jar", "/server/target/tanks-1.5.6.RELEASE.jar"]
