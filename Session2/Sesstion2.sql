create table categories(
	id int auto_increment primary key,
    name nvarchar(100) not null unique,
    keyword nvarchar(1000),
    description nvarchar(2000),
    status bit
);
INSERT INTO categories (name, keyword, description, status) 
VALUES 
('Electronics', 'electronics, gadgets, technology', 'Electronics and gadgets category', 1),
('Clothing', 'clothing, fashion, apparel', 'Clothing and fashion category', 1),
('Books', 'books, literature, reading', 'Books and literature category', 1);
create table products (
	id varchar(36) primary key,
    name nvarchar(250) unique not null,
    category_id int,
    constraint FK_Pro_Cate foreign key (category_id) 
    references categories(id),
    image varchar(1000),
    list_img varchar(1000),
    price float,
    sale_price float,
    description nvarchar(2000),
    keyword nvarchar(1000),
    content nvarchar(4000),
    status bit
);
INSERT INTO products (id, name, category_id, image, list_img, price, sale_price, description, keyword, content, status) 
VALUES 
('1', 'Smartphone X', 1, 'image_url', 'list_image_url', 500, 450, 'Description of Smartphone X', 'smartphone, tech', 'Product content goes here', 1),
('2', 'Laptop Y', 1, 'image_url', 'list_image_url', 800, NULL, 'Description of Laptop Y', 'laptop, tech', 'Product content goes here', 1),
('3', 'T-Shirt Z', 2, 'image_url', 'list_image_url', 20, NULL, 'Description of T-Shirt Z', 'clothing, fashion', 'Product content goes here', 1),
('4', 'Book A', 3, 'image_url', 'list_image_url', 15, NULL, 'Description of Book A', 'books, literature', 'Product content goes here', 1),
('5', 'Tablet Z', 1, 'tablet_image_url', 'tablet_list_image_url', 300, NULL, 'Description of Tablet Z', 'tablet, tech', 'Product content goes here', 1),
('6', 'Smartwatch Q', 1, 'smartwatch_image_url', 'smartwatch_list_image_url', 150, NULL, 'Description of Smartwatch Q', 'smartwatch, tech', 'Product content goes here', 1),
('7', 'Dress X', 2, 'dress_image_url', 'dress_list_image_url', 50, NULL, 'Description of Dress X', 'clothing, fashion', 'Product content goes here', 1),
('8', 'Jeans Y', 2, 'jeans_image_url', 'jeans_list_image_url', 40, NULL, 'Description of Jeans Y', 'clothing, fashion', 'Product content goes here', 1),
('9', 'Novel B', 3, 'novel_image_url', 'novel_list_image_url', 10, NULL, 'Description of Novel B', 'books, literature', 'Product content goes here', 1),
('10', 'Cookbook C', 3, 'cookbook_image_url', 'cookbook_list_image_url', 20, NULL, 'Description of Cookbook C', 'books, cooking', 'Product content goes here', 1),
('11', 'TV X', 1, 'tv_image_url', 'tv_list_image_url', 700, NULL, 'Description of TV X', 'electronics, tv', 'Product content goes here', 1),
('12', 'Headphones Y', 1, 'headphones_image_url', 'headphones_list_image_url', 100, NULL, 'Description of Headphones Y', 'electronics, headphones', 'Product content goes here', 1),
('13', 'Sweater Z', 2, 'sweater_image_url', 'sweater_list_image_url', 30, NULL, 'Description of Sweater Z', 'clothing, fashion', 'Product content goes here', 1),
('14', 'Shorts A', 2, 'shorts_image_url', 'shorts_list_image_url', 25, NULL, 'Description of Shorts A', 'clothing, fashion', 'Product content goes here', 1);
create table customers(
	id int primary key auto_increment,
    name nvarchar(100),
    address nvarchar(100),
    phone varchar(50),
    email varchar(100)
);
INSERT INTO customers (name, address, phone, email) 
VALUES 
('Alice Johnson', '789 Oak St, Village', '555-123-4567', 'alice@example.com'),
('Bob Williams', '456 Maple St, Town', '555-987-6543', 'bob@example.com'),
('Charlie Brown', '321 Pine St, City', '555-234-5678', 'charlie@example.com'),
('David Lee', '987 Birch St, Village', '555-876-5432', 'david@example.com'),
('Eve Taylor', '654 Cedar St, Town', '555-345-6789', 'eve@example.com'),
('Frank Martinez', '210 Walnut St, City', '555-765-4321', 'frank@example.com'),
('Grace Wilson', '123 Elm St, Village', '555-456-7890', 'grace@example.com'),
('Henry Garcia', '876 Cherry St, Town', '555-654-3210', 'henry@example.com'),
('Ivy Rodriguez', '543 Spruce St, City', '555-567-8901', 'ivy@example.com'),
('Jack Moore', '234 Acorn St, Village', '555-543-2109', 'jack@example.com');
create table orders(
	id int primary key auto_increment,
    customer_id int,
    constraint FK_Order_Cus foreign key(customer_id)
    references customers(id),
    total float,
    created datetime,
    status bit
);
INSERT INTO orders (customer_id, total, created, status) 
VALUES 
(1, 380, '2024-01-26 12:00:00', 1),
(1, 480, '2024-01-26 12:00:00', 1),
(1, 580, '2024-01-26 12:00:00', 1),
(2, 120, '2024-01-26 13:00:00', 1),
(3, 90, '2024-01-26 14:00:00', 1),
(4, 260, '2024-01-26 15:00:00', 1),
(5, 70, '2024-01-26 16:00:00', 1),
(6, 150, '2024-01-26 17:00:00', 1),
(7, 50, '2024-01-26 18:00:00', 1),
(8, 110, '2024-01-26 19:00:00', 1),
(9, 40, '2024-01-26 20:00:00', 1),
(10, 75, '2024-01-26 21:00:00', 1);
create table order_details(
	id int primary key auto_increment,
    product_id varchar(100),
    constraint FK_Order_details_Pro foreign key (product_id)
    references products(id),
    order_id int,
    constraint FK_Order_details_Order foreign key (order_id)
    references orders(id),
    price float,
    quantity int
);
-- Order 1 Details
INSERT INTO order_details (product_id, order_id, price, quantity) 
VALUES 
('5', 1, 300, 2),
('8', 1, 40, 3),
('8', 1, 40, 3),
('14', 1, 25, 1);
-- Order 2 Details
INSERT INTO order_details (product_id, order_id, price, quantity) 
VALUES 
('9', 2, 10, 5),
('10', 2, 20, 2),
('10', 2, 20, 2),
('10', 2, 20, 2),
('12', 2, 100, 1);
-- Order 3 Details
INSERT INTO order_details (product_id, order_id, price, quantity) 
VALUES 
('7', 3, 50, 1),
('11', 3, 700, 1);
-- Order 4 Details
INSERT INTO order_details (product_id, order_id, price, quantity) 
VALUES 
('6', 4, 150, 2),
('13', 4, 30, 3),
('14', 4, 25, 1);
-- Order 5 Details
INSERT INTO order_details (product_id, order_id, price, quantity) 
VALUES 
('5', 5, 300, 1),
('9', 5, 10, 4),
('12', 5, 100, 2);
-- Order 6 Details
INSERT INTO order_details (product_id, order_id, price, quantity) 
VALUES 
('8', 6, 40, 2),
('10', 6, 20, 3),
('11', 6, 700, 1);
-- Order 7 Details
INSERT INTO order_details (product_id, order_id, price, quantity) 
VALUES 
('7', 7, 50, 3),
('9', 7, 10, 2),
('9', 7, 10, 2),
('13', 7, 30, 1);
-- Order 8 Details
INSERT INTO order_details (product_id, order_id, price, quantity) 
VALUES 
('6', 8, 150, 1),
('11', 8, 700, 1),
('12', 8, 100, 3);
-- Order 9 Details
INSERT INTO order_details (product_id, order_id, price, quantity) 
VALUES 
('7', 9, 50, 2),
('10', 9, 20, 1),
('14', 9, 25, 3);
-- Order 10 Details
INSERT INTO order_details (product_id, order_id, price, quantity) 
VALUES 
('5', 10, 300, 3),
('8', 10, 40, 1),
('13', 10, 30, 2);
-- Bài tập
-- 1. Hiển thị danh sách các hóa đơn bao gồm: Mã đơn hàng, Tên khách hàng, Tổng tiền, Trạng thái
select od.id, cus.name, sum(odt.price*odt.quantity) as 'Tong tien', od.status from order_details odt
join orders od on order_id = od.id
join customers cus on od.customer_id = cus.id
group by od.id;
-- 2. Hiển thị danh Danh sách khách hàng gồm: Mã KH, Tên KH, Tổng ĐH đã mua
select cus.id, cus.name, count(od.id) as 'Tong DH' from customers cus
join orders od on od.customer_id = cus.id
group by cus.id, cus.name;
select * from orders where customer_id = 1;
-- 3. Hiển thị danh sách sản phẩm kèm số lượng đã bán gồm: Mã SP, Tên SP, Số lượng (đã bán)
select p.id, p.name, sum(odt.quantity) as'So luong' from products p
join order_details odt on p.id = odt.product_id
group by odt.product_id
order by p.id asc;
select * from order_details where product_id = 10;
-- 4. Hiển thị đơn hàng mua nhiều hơn 2 sản phẩm
select od.id, count(odt.product_id) as sanpham from orders od
join order_details odt on od.id= odt.order_id
group by od.id
having sanpham > 2;

-- 5. Hiển thị top 3 sản phẩm bán chạy nhất
select od.id, count(odt.product_id) as sanpham from orders od
join order_details odt on od.id= odt.order_id
group by od.id
order by sanpham desc
limit 3;

-- 6. Hiển thị danh Danh sách khách hàng gồm: Mã KH, Tên KH, Tổng tiền đã mua
