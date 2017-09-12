# tanks-backend

## requarements
Install MariaDB from official website: 

    https://downloads.mariadb.org/mariadb/repositories/#mirror=mephi

Next create database and user:
    
    CREATE DATABASE IF NOT EXISTS tanksdb;
    CREATE USER 'tanks-admin' IDENTIFIED BY 'tanks-password';
    GRANT ALL PRIVILEGES ON tanksdb. * TO 'tanks-admin';
    FLUSH PRIVILEGES;

    


