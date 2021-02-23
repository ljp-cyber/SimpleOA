drop database if exists db_simpleoa;
create database db_simpleoa char set utf8;
use db_simpleoa;

/*部门表*/
create table department
(
department_id int not null auto_increment primary key,
department_sn varchar(10) unique not null,
department_name varchar(20) not null,
address varchar(100)
);

/*员工表*/
create table worker
(
worker_id int not null auto_increment primary key,
worker_sn varchar(10) unique not null,
worker_pw varchar(20) not null,
worker_name varchar(20) not null,
department_id int,
post varchar(20),
FOREIGN KEY(department_id) REFERENCES department(department_id)
);

/*报销单*/
create table receipts
(
receipts_id int not null auto_increment primary key,
creater_id int,
next_deal_id int,
creat_time datetime not null,
cause varchar(100) not null,
totalmoney decimal not null,
state varchar(20) not null,
FOREIGN KEY(creater_id) REFERENCES worker(worker_id),
FOREIGN KEY(next_deal_id) REFERENCES worker(worker_id)
);

/*报销单明细*/
create table receipts_details
(
receipts_details_id int not null auto_increment primary key,
receipts_id int,
cost_type varchar(20) not null,
cost_money decimal not null,
represention varchar(100) ,
FOREIGN KEY(receipts_id) REFERENCES receipts(receipts_id)
);

/*报销单处理记录*/
create table receipts_record
(
receipts_record_id int not null auto_increment primary key,
creater_id int,
next_deal_id int,
deal_time datetime not null,
deal_type varchar(10) not null,
deal_result varchar(10) not null,
represention varchar(100) ,
FOREIGN KEY(creater_id) REFERENCES receipts(receipts_id),
FOREIGN KEY(next_deal_id) REFERENCES worker(worker_id)
);

insert into department values(null,'10001','总经理办公室','星星大厦E座1201');
insert into department values(null,'10002','财务部','星星大厦E座1202');
insert into department values(null,'10003','事业部','星星大厦E座1101');

insert into worker values(null,'10001','000000','刘备',1,'总经理');
insert into worker values(null,'10002','000000','孙尚香',2,'财务');
insert into worker values(null,'10003','000000','关羽',3,'部门经理');
insert into worker values(null,'10004','000000','周仓',3,'员工');
