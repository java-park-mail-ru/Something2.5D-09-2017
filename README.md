# tanks-backend

[![Travis](https://api.travis-ci.org/java-park-mail-ru/Something2.5D-09-2017.svg?branch=developer&style=flat)]()

## requirements
Install MariaDB from official website: 

    https://downloads.mariadb.org/mariadb/repositories/#mirror=mephi

Next create database and user:
    
    CREATE DATABASE IF NOT EXISTS tanksdb;
    CREATE USER 'tanks-admin' IDENTIFIED BY 'tanks-password';
    GRANT ALL PRIVILEGES ON tanksdb. * TO 'tanks-admin';
    FLUSH PRIVILEGES;
    
#### For testing
    docker pull mariadb:latest
    docker run --name testdb -e MYSQL_ROOT_PASSWORD=tanks-password -e MYSQL_DATABASE=tanksdb_test -e MYSQL_USER=tanks-admin -e MYSQL_PASSWORD=tanks-password -p 52000:3306 -d mariadb

    


