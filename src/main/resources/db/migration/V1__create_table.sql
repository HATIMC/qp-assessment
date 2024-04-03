create table grocery_inventory (
	grocery_id int AUTO_INCREMENT PRIMARY KEY,
	created_date timestamp,
	updated_date timestamp,
	grocery_name varchar(255),
	grocery_price float(53),
	is_deleted bit default 0
);

create table grocery_level (
	level_id int AUTO_INCREMENT PRIMARY KEY,
	grocery_id int UNIQUE,
	created_date timestamp,
	updated_date timestamp,
	level int
);

create table user (
	user_id int AUTO_INCREMENT PRIMARY KEY,
	created_date timestamp,
	updated_date timestamp,
	user_name varchar(100) UNIQUE,
	password varchar(100),
	user_type varchar(20)
);

create table api_key (
	user_id int PRIMARY KEY,
	api_key varchar(40) UNIQUE,
	created_date timestamp,
	expiry_date timestamp
);

create table user_order (
	cart_id int AUTO_INCREMENT PRIMARY KEY,
	user_id int,
	grocery_id int,
	level int,
	created_date timestamp,
	updated_date timestamp
);