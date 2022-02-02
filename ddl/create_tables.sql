create database IF NOT EXISTS CEMS;
use CEMS;
drop table MY_FIRST_TABLE;
create table MY_FIRST_TABLE (
	MY_FIRST_ATTRIBUTE INT auto_increment PRIMARY KEY
);

insert into MY_FIRST_TABLE() VALUES ();
select * from MY_FIRST_TABLE;
