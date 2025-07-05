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
	type          varchar(200) not null default 'user',
	status        varchar(200),
	detail        varchar(200),
	signature     varchar(200),
	account       varchar(200)
);
alter table users auto_increment = 1048576;

insert into users(email, password, first_name, last_name, type)
values('user@email.com', upper(sha2('User1234', 512)), 
'First', 'Last', 'administrator');

/*
User Type
unknown
administrator
staff
user
*/

create table friends
(
	source int not null,
	target int not null,
	since  timestamp default now()
);

create table friend_requests
(
	source int not null,
	target int not null,
	since  timestamp default now()
);

/*
insert into users(email, password, first_name, last_name, type)
values('alice@email.com', upper(sha2('Alice1234', 512)), 
'Alice', 'Atlanta', 'user');

insert into users(email, password, first_name, last_name, type)
values('bob@email.com', upper(sha2('Bob1234', 512)), 
'Bob', 'Boston', 'user');

insert into users(email, password, first_name, last_name, type)
values('cecil@email.com', upper(sha2('Cecil1234', 512)), 
'Cecil', 'Chicago', 'user');

insert into users(email, password, first_name, last_name, type)
values('david@email.com', upper(sha2('David1234', 512)), 
'David', 'Detroit', 'user');

insert into users(email, password, first_name, last_name, type)
values('emma@email.com', upper(sha2('Emma1234', 512)), 
'Emma', 'Exeter', 'user');

insert into users(email, password, first_name, last_name, type)
values('frank@email.com', upper(sha2('Frank1234', 512)), 
'Frank', 'Fremont', 'user');

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




