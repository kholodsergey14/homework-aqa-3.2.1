CREATE DATABASE app;
CREATE USER 'app'@'localhost' IDENTIFIED BY 'pass';
GRANT ALL PRIVILEGES ON app.* TO 'app'@'localhost';
SET GLOBAL time_zone = 'Europe/Moscow';
SET time_zone = 'Europe/Moscow';