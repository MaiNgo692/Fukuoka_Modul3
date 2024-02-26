create table products(
    Product_Id char(5) primary key ,
    Product_Name varchar(150) not null unique ,
    Manufacturer varchar(200) not null ,
    Created date default (curdate()),
    Batch smallint not null ,
    Quantity int not null default (0),
    Product_Status bit default (1)
);

create table employees(
    Emp_Id char(5) primary key ,
    Emp_Name varchar(100) not null unique ,
    Birth_Of_Date date ,
    Email varchar(100) not null ,
    Phone varchar(100) not null ,
    Address text not null ,
    Emp_Status smallint not null
);

create table accounts(
    Acc_Id int primary key auto_increment,
    User_Name varchar(30) not null unique ,
    Password varchar(30) not null ,
    Permission bit default (1),
    Emp_id char(5) not null unique ,
    Acc_Status bit default (1),
    CONSTRAINT FK_Employees FOREIGN KEY (Emp_id)
    REFERENCES employees(Emp_Id)
);

create table bills(
    Bill_Id bigint primary key  auto_increment,
    Bill_Code varchar(10) not null ,
    Bill_Type bit not null ,
    Emp_Id_Created char(5) not null ,
    Created date default (curdate()),
    Emp_Id_Auth char(5) not null ,
    Auth_Date date default (curdate()),
    Bill_Status smallint not null default (0),
    CONSTRAINT FK_Employees1 FOREIGN KEY (Emp_Id_Created)
    REFERENCES employees(Emp_Id),
    CONSTRAINT FK_Employees2 FOREIGN KEY (Emp_Id_Auth)
    REFERENCES employees(Emp_Id)
);
drop table bills;
create table bill_details(
    Bill_Detail_Id bigint primary key auto_increment,
    Bill_Id bigint not null ,
    Product_Id char(5) not null ,
    Quantity int not null check ( Quantity > 0 ),
    Price float not null check ( Price > 0 ),
    CONSTRAINT FK_Products FOREIGN KEY (Product_Id)
        REFERENCES products(Product_Id),
    CONSTRAINT FK_Bills FOREIGN KEY (Bill_Id)
        REFERENCES bills(Bill_Id)
);
drop table bill_details;
