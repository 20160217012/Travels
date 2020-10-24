-- 用户表
create table t_user(
	id int(6) PRIMARY KEY auto_increment,
	username VARCHAR(60),
	password VARCHAR(60),
	email VARCHAR(60)
);

-- 省份表
create table t_province(
	id int(6) PRIMARY KEY auto_increment,
	name VARCHAR(60),
	tags VARCHAR(100),
	placecounts int(4)
);

-- 景点表（需要省份表的ID）
create table t_place(
	id int(6) PRIMARY KEY auto_increment,
	name VARCHAR(60),
	picpath VARCHAR(100),
	hottime TIMESTAMP,
	hotticke DOUBLE(7,2),
	dimticket DOUBLE(7,2),
	placedes VARCHAR(400),
	provinceid int(6) REFERENCES t_province(id)
);