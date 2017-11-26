FROM maven:latest

COPY ./ /server/
WORKDIR /server/

RUN mvn install -DskipTests=true -Dmaven.javadoc.skip=true -B -V

EXPOSE 8080

CMD ["/usr/bin/java", "-jar", "./target/tanks-0.0.1-SNAPSHOT.jar.jar"]
