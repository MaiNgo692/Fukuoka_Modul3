-- tạo mới CSDL
create database ra_fukuoka;
-- chọn CSDL để truy vấn
use ra_fukuoka;
-- tạo bảng trong CSDL
create table categories(
	id varchar(36) primary key,
    name varchar(250) unique not null,
    status bit default 1
);
create table products(
	id varchar(36) primary key,
    name varchar(255) not null unique,
    categoryId varchar(36) not null,
    price float check(price > 1000),
    description varchar(4000) default 'Mô tả',
    status bit,
    foreign key (categoryId) references categories(id)
);
-- xoa bang
drop table categories;
drop table products;
-- cap nhat
alter table categories modify column name varchar(100);
alter table categories
drop column name;
alter table categories
add column name varchar(250) not null;

-- DML(data manipulation language)
insert into categories values(uuid(),1,'New category');

-- truy van csdl
select * from categories;
select * from products;
-- cap nhat csdl
update categories set name = 'New category' where id ='35258f8d-bab6-11ee-ab76-00090ffe0001';

-- xoa du lieu
delete from categories where name ='Update category';


insert into products(id,name,price,categoryId,status) values(uuid(),'Products 2',1500,'35258f8d-bab6-11ee-ab76-00090ffe0001',1);

