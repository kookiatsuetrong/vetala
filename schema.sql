create database sample default character set 'UTF8';
create user me identified by 'password';
grant all on sample.* to me;

use sample;

create table users
(
	number        int unique not null auto_increment,
	email         varchar(200) unique not null,
	password      varchar(200) not null,
	first_name    varchar(200) not null,
	last_name     varchar(200) not null,
	type          varchar(200) not null default 'user'
);

insert into users(email, password, first_name, last_name, type)
values('user@email.com', upper(sha2('User1234', 512)), 
'First', 'Last', 'administrator');

-- type of user
-- unknown
-- administrator
-- staff
-- user

/*
insert into users(email, password, first_name, last_name, type)
values('alice@email.com', upper(sha2('Alice1234', 512)), 
'Alice', 'Family', 'user');

insert into users(email, password, first_name, last_name, type)
values('bob@email.com', upper(sha2('Bob1234', 512)), 
'Bob', 'Family', 'user');

insert into users(email, password, first_name, last_name, type)
values('charlotte@email.com', upper(sha2('Charlotte1234', 512)), 
'Charlotte', 'Family', 'user');

insert into users(email, password, first_name, last_name, type)
values('david@email.com', upper(sha2('David1234', 512)), 
'David', 'Family', 'user');
*/


create table messages
(
	number        int unique not null auto_increment,
	topic         varchar(200) not null,
	detail        varchar(4000),
	email         varchar(200) not null,
	time          timestamp
);

insert into messages(topic,email,time) 
	values('Topic 1', 'email@sample.com', utc_timestamp());




