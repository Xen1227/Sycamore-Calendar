DROP DATABASE if exists Data;
CREATE DATABASE Data;
USE Data;

CREATE TABLE Users (
userID int(11) primary key not null auto_increment,
email varchar(100) not null,
fname varchar(50) not null,
lname varchar(50) not null,
URL varchar(200) not null,
listOfFollowing text not null,
listOfEvents text not null,
eventData text not null
);

