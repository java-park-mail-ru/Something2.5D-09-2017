# Tanks-backend

[![Travis](https://api.travis-ci.org/java-park-mail-ru/Something2.5D-09-2017.svg?branch=developer&style=flat)]()

## Requirements
* Docker
* Docker Compose https://docs.docker.com/compose/install/
* Maven https://maven.apache.org/download.cgi

## Build

    mvn install -DskipTests=true -Dmaven.javadoc.skip=true -B -V
    
## Deploy
Сhange the directory to *runner* and run command

    ./start
    
The following parameters are supported:

| Parameter     | Description          |
| ------------- | :------------------- |
| -d            | Run server as daemon | 

    
When you first start you must make the scripts executable

    chmod +x ./start && chmod +x ./stop && chmod +x ./restart
    
## Manage

For managing server use next scripts:

| Command       | Description                                                  |
| ------------- | :----------------------------------------------------------- |
| start         | Build all containers and run                                 | 
| stop          | Stop all containers                                          | 
| restart       | Restart only api container. Database container isn't restart | 


## TEST
    docker pull mariadb:latest
    docker run --name testdb -e MYSQL_ROOT_PASSWORD=tanks-password -e MYSQL_DATABASE=tanksdb_test -e MYSQL_USER=tanks-admin -e MYSQL_PASSWORD=tanks-password -p 52000:3306 -d mariadb
    mvn test -B

    


