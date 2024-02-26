delimiter //
create procedure Cost_statistic_by_date (out cost float,in date DATE,in billType boolean )
begin
    select
        Sum(bd.Price*bd.Quantity) as 'Chi phí'
    from
        bills b
            join
        bill_details bd on b.Bill_Id = bd.Bill_Id
    where b.Bill_Status = 2 and b.Bill_Type = billType and b.Auth_Date = date
    group by
        b.Auth_Date ;
end
//
delimiter ;

delimiter //
create procedure Cost_statistic_over_period (out cost float,in fromDate DATE,in toDate DATE,in billType boolean )
begin
    select
        Sum(bd.Price*bd.Quantity)
    from
        bills b
            join
        bill_details bd on b.Bill_Id = bd.Bill_Id
    where b.Bill_Status = 2 and b.Bill_Type = billType and b.Auth_Date between fromDate and toDate
    group by
        b.Bill_Status ;
end
//
delimiter ;

delimiter //
create procedure Emp_statistic_by_status (out count int,in empStatus int )
begin
    select
        count(*)
    from
        employees emp
    where emp.Emp_Status=empStatus
    group by
        emp.Emp_Status ;
end
//
delimiter ;

delimiter //
create procedure Most_product_quantity_over_period (out name varchar(150),out quantity int,in fromDate DATE,in toDate DATE,in billType boolean )
begin
    select
        p.Product_Name,
        Sum(bd.Quantity) as total_product_quantity
    from
        bills b
            join
        bill_details bd on b.Bill_Id = bd.Bill_Id
            join products p on p.Product_Id = bd.Product_Id
    where b.Bill_Status = 2 and b.Bill_Type = billType and b.Auth_Date between fromDate and toDate
    group by
        p.Product_Id
    order by total_product_quantity desc
    limit 1;
end
//
delimiter ;

delimiter //
create procedure Least_product_quantity_over_period (out name varchar(150),out quantity int,in fromDate DATE,in toDate DATE,in billType boolean )
begin
    select
        p.Product_Name,
        Sum(bd.Quantity) as total_product_quantity
    from
        bills b
            join
        bill_details bd on b.Bill_Id = bd.Bill_Id
            join products p on p.Product_Id = bd.Product_Id
    where b.Bill_Status = 2 and b.Bill_Type = billType and b.Auth_Date between fromDate and toDate
    group by
        p.Product_Id
    order by total_product_quantity asc
    limit 1;
end
//
delimiter ;

