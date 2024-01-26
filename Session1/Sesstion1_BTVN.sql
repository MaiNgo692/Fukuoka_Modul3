-- create schema ra_sesstion1_HW;
create table Banners (
	id varchar(36) primary key,
	title varchar(50),
	image varchar(225),
	sumary varchar(255),
	link varchar(255),
	order_by varchar(255),
	status bit default(1)
);
create table Contacts(
	id varchar(36) primary key,
    email varchar(250),
    address varchar(255),
    message varchar(4000),
    sattus bit default(1)
);

create table Blogs(
	id varchar(36) primary key,
    title varchar(255),
    image varchar(255),
    content varchar(4000),
    status bit default(1)
);
create table Customers(
	id varchar(36) primary key,
    name varchar(50),
    address varchar(250),
    phone varchar(10),
    email varchar(255)
);
create table Orders(
	id varchar(36) primary key,
    customer_id varchar(36),
    total float,
    status bit default(1),
     foreign key (customer_id) references Customers(id)
);
create table Categorys(
	id varchar(36) primary key,
    name varchar(250),
    keyword varchar(250),
    description varchar(4000),
    status bit default(1)
);
create table Products(
	id varchar(36) primary key,
    name varchar(50),
    category_id varchar(36),
    image varchar(255),
    list_image varchar(250),
    price float,
    sale_price float,
    description varchar(500),
    keyword varchar(250),
    content varchar(4000),
    status bit default(1),
    foreign key (category_id) references Categorys(id)
);
create table order_details(
	id varchar(36) primary key,
    product_id varchar(36),
    order_id varchar(36),
    price float,
    quantity int,
    foreign key (order_id) references Orders(id),
    foreign key (product_id) references Products(id)
);


create table Abouts(
	id varchar(36) primary key,
    title varchar(250),
    image varchar(255),
    content varchar(4000),
    status bit default(1)
);

create table Services(
	id varchar(36) primary key,
    name varchar(250),
    summary varchar(4000),
    content varchar(4000),
    image varchar(255),
    order_by varchar(255),
    status bit default(1)
);
create table Users(
	id varchar(36) primary key
    
);
create table Books(
	id varchar(36) primary key,
    user_id varchar(36),
    service_id varchar(36),
    name varchar(250),
    phone varchar(10),
    date datetime,
    foreign key (user_id) references Users(id),
    foreign key(service_id) references Services(id)
);
create table Comments(
	id varchar(36) primary key,
    user_id varchar(36),
    product_id varchar(36),
    message varchar(4000),
    status bit default(1),
    foreign key(user_id) references Users(id),
    foreign key(product_id) references Products(id) 
);