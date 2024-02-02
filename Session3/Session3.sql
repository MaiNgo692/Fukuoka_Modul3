CREATE TABLE Categories (
    CategoryID INT AUTO_INCREMENT PRIMARY KEY,
    CategoryName VARCHAR(100) NOT NULL,
    Description TEXT,
    Status  bit
);
select * from categories;
drop table categories;
CREATE TABLE Products (
    ProductID INT AUTO_INCREMENT PRIMARY KEY,
    ProductName VARCHAR(250) NOT NULL,
    CategoryID INT,
    Price DECIMAL(10, 2),
    Description TEXT,
    ImageURL VARCHAR(1000),
    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID)
);
drop table Products;
CREATE TABLE Customers (
    CustomerID INT AUTO_INCREMENT PRIMARY KEY,
    FullName VARCHAR(100) NOT NULL,
    Email VARCHAR(100),
    Phone VARCHAR(20),
    Address VARCHAR(255)
);
CREATE TABLE Orders (
    OrderID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerID INT,
    OrderDate DATETIME,
    TotalAmount DECIMAL(10, 2),
    Status ENUM('Pending', 'Shipped', 'Delivered', 'Cancelled'),
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);
CREATE TABLE OrderDetails (
    OrderDetailID INT AUTO_INCREMENT PRIMARY KEY,
    OrderID INT,
    ProductID INT,
    Quantity INT,
    Price DECIMAL(10, 2),
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);
drop table OrderDetails;
CREATE TABLE Reviews (
    ReviewID INT AUTO_INCREMENT PRIMARY KEY,
    ProductID INT,
    CustomerID INT,
    Rating INT,
    Comment TEXT,
    ReviewDate DATETIME,
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID),
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);
drop table Reviews;


INSERT INTO Categories (CategoryName, Description, Status)
VALUES 
('Men', 'Men''s Clothing', 1),
('Women', 'Women''s Clothing', 1),
('Shirts', 'Men''s Shirts', 1),
('Pants', 'Men''s Pants', 1),
('Dresses', 'Women''s Dresses', 0),
('Skirts', 'Women''s Skirts', 0);

INSERT INTO Products (ProductName, CategoryID, Price, Description, ImageURL)
VALUES 
('Men''s Casual Shirt', 3, 29.99, 'Casual shirt for men', 'image_url'),
('Men''s Dress Pants', 4, 49.99, 'Formal dress pants for men', 'image_url'),
('Women''s Summer Dress', 5, 39.99, 'Lightweight summer dress for women', 'image_url'),
('Women''s A-Line Skirt', 6, 34.99, 'A-line skirt for women', 'image_url');

INSERT INTO Customers (FullName, Email, Phone, Address)
VALUES 
('John Doe', 'john@example.com', '123-456-7890', '123 Main St'),
('Jane Smith', 'jane@example.com', '456-789-1230', '456 Elm St'),
('Alice Johnson', 'alice@example.com', '555-123-4567', '789 Oak St, Village'),
('Bob Williams', 'bob@example.com', '555-987-6543', '456 Maple St, Town'),
('Charlie Brown', 'charlie@example.com', '555-234-5678', '321 Pine St, City'),
('David Lee', 'david@example.com', '555-876-5432', '987 Birch St, Village'),
('Eve Taylor', 'eve@example.com', '555-345-6789', '654 Cedar St, Town');

INSERT INTO Orders (CustomerID, OrderDate, TotalAmount, Status)
VALUES 
(1, '2024-01-26 10:00:00', 79.98, 'Delivered'),
(2, '2024-01-27 11:00:00', 74.98, 'Pending'),
(3, '2024-01-28 12:00:00', 150.97, 'Shipped'),
(4, '2024-01-29 13:00:00', 95.98, 'Pending'),
(5, '2024-01-30 14:00:00', 230.96, 'Delivered'),
(1, '2024-01-31 15:00:00', 180.98, 'Pending'),
(2, '2024-02-01 16:00:00', 125.99, 'Shipped');

INSERT INTO OrderDetails (OrderID, ProductID, Quantity, Price)
VALUES 
(1, 1, 2, 29.99),
(1, 4, 1, 49.99),
(2, 3, 1, 39.99),
(2, 4, 1, 34.99);

-- Order 3 Details
INSERT INTO OrderDetails (OrderID, ProductID, Quantity, Price)
VALUES 
(3, 1, 3, 29.99),
(3, 4, 2, 49.99);

-- Order 4 Details
INSERT INTO OrderDetails (OrderID, ProductID, Quantity, Price)
VALUES 
(4, 2, 1, 39.99),
(4, 3, 1, 39.99),
(4, 4, 2, 34.99);

-- Order 5 Details
INSERT INTO OrderDetails (OrderID, ProductID, Quantity, Price)
VALUES 
(5, 1, 2, 29.99),
(5, 2, 1, 49.99),
(5, 3, 3, 39.99),
(5, 4, 2, 34.99);

-- Order 6 Details
INSERT INTO OrderDetails (OrderID, ProductID, Quantity, Price)
VALUES 
(6, 1, 1, 29.99),
(6, 4, 2, 49.99),
(6, 3, 1, 39.99),
(6, 2, 3, 49.99);

-- Order 7 Details
INSERT INTO OrderDetails (OrderID, ProductID, Quantity, Price)
VALUES 
(7, 2, 2, 49.99),
(7, 3, 1, 39.99),
(7, 4, 1, 34.99),
(7, 1, 1, 29.99);


-- Review 1
INSERT INTO Reviews (ProductID, CustomerID, Rating, Comment, ReviewDate)
VALUES (1, 1, 4, 'Great shirt, fits perfectly!', '2024-01-15 10:00:00');

-- Review 2
INSERT INTO Reviews (ProductID, CustomerID, Rating, Comment, ReviewDate)
VALUES (1, 2, 5, 'Love the quality and design.', '2024-01-16 11:00:00');

-- Review 3
INSERT INTO Reviews (ProductID, CustomerID, Rating, Comment, ReviewDate)
VALUES (2, 3, 3, 'The pants are a bit tight.', '2024-01-17 12:00:00');

-- Review 4
INSERT INTO Reviews (ProductID, CustomerID, Rating, Comment, ReviewDate)
VALUES (3, 4, 5, 'Beautiful dress, perfect for summer!', '2024-01-18 13:00:00');

-- Review 5
INSERT INTO Reviews (ProductID, CustomerID, Rating, Comment, ReviewDate)
VALUES (3, 5, 4, 'Comfortable and stylish.', '2024-01-19 14:00:00');

-- Review 6
INSERT INTO Reviews (ProductID, CustomerID, Rating, Comment, ReviewDate)
VALUES (4, 1, 4, 'Nice skirt, good quality material.', '2024-01-20 15:00:00');

-- Review 7
INSERT INTO Reviews (ProductID, CustomerID, Rating, Comment, ReviewDate)
VALUES (4, 2, 5, 'Fits perfectly, very happy with my purchase!', '2024-01-21 16:00:00');

-- Tạo index cho các bảng Products, Customers, Orders phù hợp với mục đích truy vấn
create index idx_products_id_name on Products (ProductID,ProductName);
create index idx_customers_phone on Customers(Phone);
create index idx_orders_id_cus_id on Orders(OrderID,CustomerID);
-- Tạo thủ tục
	-- 6. Liệt lấy ra n sản phẩm được đặt hàng nhiều nhất
    delimiter //
    create procedure Most_ordered_products (in size int)
    begin
		select
		P.ProductName as 'Tên SP',
		Max(Quantity) as 'Số Lượng'
		from 
		products P
		join 
		OrderDetails OD on P.productID = OD.productID
		group by
		P.ProductName
        limit size;
    end
    //
    delimiter ;
    call Most_ordered_products(4);
    -- 7. Tìm kiếm sản phẩm dựa trên mức đánh giá trung bình
	delimiter //
    create procedure Products_above_avg_rating (in avgRate float)
    begin
		select 
		P.ProductName as 'Tên SP',
		avg(Rating) as 'Điểm TB'
		From 
		products P
		Join reviews R on R.ProductID = P.ProductID
		group by
		P.ProductName
		having avg(R.Rating) >= avgRate;
    end
    //
    delimiter ;
    call Products_above_avg_rating(3);
    
    -- 9. Tổng doanh thu từng tháng trong của năm x (x là tham số in của thủ tục)
    delimiter //
    create procedure Total_revenue_for_each_month_in_year (in year int)
    begin
		SELECT
			MONTH(OrderDate) AS 'Tháng',
			SUM(TotalAmount) AS 'Tổng Doanh Thu'
		FROM
			Orders od
		where Year(od.OrderDate) = year
		GROUP BY MONTH(od.OrderDate);
	end
    //
    delimiter ;
    call Total_revenue_for_each_month_in_year(2023);
    -- 10. Insert dữ liệu vào bảng order, trả về id của order vừa được insert.
    delimiter //
    create procedure insert_order (in CustomerID int, in OrderDate DATETIME,in TotalAmount DECIMAL(10, 2),
    in Status ENUM('Pending', 'Shipped', 'Delivered', 'Cancelled'),  out order_id int)
    begin
		insert into Orders (CustomerID, OrderDate, TotalAmount, Status)
		VALUES (CustomerID,OrderDate,TotalAmount,Status);
        set order_id = last_insert_id();
	end
    //
    delimiter ;
    call insert_order(4, '2024-01-30 14:00:00', 230.96, 'Delivered',@order_id);
    select @order_id;
    
-- Tạo VIEW
	-- 2. Báo cáo số lượng sản phẩm đã bán theo danh mục
    create view Product_sold_by_category as
		select c.CategoryName, count(odt.ProductID) from Categories c
		join products p on p.CategoryID = c.CategoryID
		join OrderDetails odt on odt.ProductID = p.ProductID
		group by odt.ProductID;
        
	-- 3. Báo cáo danh sách khách hàng và số lượng đơn hàng mỗi khách hàng đã đặt
    create view rp_custome_order_number as
    select cus.FullName, count(od.CustomerID) from Customers cus
	join Orders od on cus.CustomerID = od.CustomerID
	group by od.CustomerID ;

	-- 4. Báo cáo tỷ lệ đơn hàng đã giao thành công
    create view rp_succ_rate_delivered as
    select sum(case when od.Status = 'Delivered' then 1 else 0 end) as 'Thành công' ,
	 count(*) as 'Tổng đơn',
	 round(100.0*sum(case when od.Status = 'Delivered' then 1 else 0 end)/count(*) ,1) as 'Tỉ lệ'
	from Orders od;