create database session3_hw;
use session3_hw;

create table tblPhim (
	PhimID int,
    Ten_Phim nvarchar(30),
    Loai_Phim nvarchar(25),
    Thoi_Gian int
);
alter table tblPhim 
add primary key (PhimID);
drop table tblPhim;

insert into tblPhim(PhimID,Ten_Phim,Loai_Phim,Thoi_Gian) 
values
(1,'Em bé Hà Nội', 'Tâm lý', 90 ),
(2,'Nhiệm vụ bất khả thi','Hành động', 100 ),
(3,'Dị nhân','Viễn tưởng',90),
(4,'Cuốn theo chiều gió','Tình cảm',120);

create table tblPhong(
	PhongID int,
    Ten_Phong nvarchar(20),
    Trang_thai tinyint
);
alter table tblPhong 
add primary key (PhongID);
drop table tblPhong;

insert into tblPhong(PhongID,Ten_Phong,Trang_thai)
values(1,'Phòng chiếu 1',1),
(2,'Phòng chiếu 2',1),
(3,'Phòng chiếu 3',0);


create table tblGhe(
	GheID int,
    PhongID int,
    So_ghe varchar(10)
);
alter table tblGhe 
add primary key (GheID);
alter table tblGhe 
add foreign key(PhongID) references tblPhong(PhongID);
drop table tblGhe;

insert into tblGhe(GheID,PhongID,So_ghe)
values(1,1,'A3'),
(2,1,'B5'),
(3,2,'A7'),
(4,2,'D1'),
(5,3,'T2');

create table tblVe(
	PhimID int,
    GheID int,
    Ngay_chieu datetime,
    Trang_thai nvarchar(20)
);
alter table tblVe 
add foreign key(PhimID) references  tblPhim(PhimID);
alter table tblVe 
add foreign key(GheID) references tblGhe(GheID);
drop table tblVe;
insert into tblVe(PhimID,GheID,Ngay_chieu,Trang_thai)
values(1,1,'2008-10-20','Đã bán'),
(1,3,'2008-12-20','Đã bán'),
(1,4,'2008-12-23','Đã bán'),
(2,1,'2009-02-14','Đã bán'),
(3,1,'2009-02-14','Đã bán'),
(2,5,'2009-08-03','Chưa bán'),
(2,3,'2009-08-03','Chưa bán');


-- 2.Hiển thị danh sách các phim (chú ý: danh sách phải được sắp xếp theo trường Thoi_gian)
select p.Ten_phim ,p.Thoi_gian from tblPhim p
order by p.Thoi_gian;
-- 3.Hiển thị Ten_phim có thời gian chiếu dài nhất
select p.Ten_phim ,p.Thoi_gian from tblPhim p
order by p.Thoi_gian desc
limit 1;
-- 4.Hiển thị Ten_Phim có thời gian chiếu ngắn nhất
select p.Ten_phim  from tblPhim p
where p.Thoi_gian = (select min(Thoi_gian) from tblPhim);

-- 5.Hiển thị danh sách So_Ghe mà bắt đầu bằng chữ ‘A’
select * from tblGhe g
where g.So_ghe like 'A%';

-- 6.Sửa cột Trang_thai của bảng tblPhong sang kiểu nvarchar(25)
alter table tblPhong modify Trang_thai nvarchar(25);
-- 7.Cập nhật giá trị cột Trang_thai của bảng tblPhong theo các luật sau:
-- Nếu Trang_thai=0 thì gán Trang_thai=’Đang sửa’
-- Nếu Trang_thai=1 thì gán Trang_thai=’Đang sử dụng’
-- Nếu Trang_thai=null thì gán Trang_thai=’Unknow’
-- Sau đó hiển thị bảng tblPhong

UPDATE tblPhong SET Trang_thai = IF( Trang_thai = 0 , 'Đang sửa' , IF( Trang_thai = 1 , 'Đang sử dụng' , IF( Trang_thai is null , 'Unknow' , Trang_thai)));
select * from tblPhong;
        
-- 8.Hiển thị danh sách tên phim mà có độ dài >15 và < 25 ký tự
select p.Ten_phim from tblPhim p where length(p.Ten_phim) between 15 and 25;

-- 9.Hiển thị Ten_Phong và Trang_Thai trong bảng tblPhong trong 1 cột với tiêu đề ‘Trạng thái phòng chiếu’
select concat(p.Ten_Phong,' ', p.Trang_thai) as 'Trạng thái phòng chiếu' from tblPhong p;

-- 10.Tạo bảng mới có tên tblRank với các cột sau: STT(thứ hạng sắp xếp theo Ten_Phim), TenPhim, Thoi_gian
create table tblRank(
	STT int,
    TenPhim nvarchar(30),
    Thoi_gian int
);
-- 11.Trong bảng tblPhim :
-- a. Thêm trường Mo_ta kiểu nvarchar(max)
alter table tblPhim add Mo_ta nvarchar(255);
-- b. Cập nhật trường Mo_ta: thêm chuỗi “Đây là bộ phim thể loại ” + nội dung trường LoaiPhim
update  tblPhim p set Mo_ta = concat('Đây là bộ phim thể loại', ' ',p.Loai_phim);
-- c. Hiển thị bảng tblPhim sau khi cập nhật
select * from tblPhim;
-- d. Cập nhật trường Mo_ta: thay chuỗi “bộ phim” thành chuỗi “film”
update  tblPhim p set Mo_ta = replace(Mo_ta,'bộ phim','phim');
-- e. Hiển thị bảng tblPhim sau khi cập nhật
select * from tblPhim;

-- 12.Xóa tất cả các khóa ngoại trong các bảng trên.
alter table tblGhe 
drop constraint tblghe_ibfk_1;
alter table tblVe 
drop constraint tblve_ibfk_1;
alter table tblVe 
drop constraint tblve_ibfk_2;
-- 13.Xóa dữ liệu ở bảng tblGhe
truncate table tblGhe;
-- 14.Hiển thị ngày giờ hiện tại và ngày giờ hiện tại cộng thêm 5000 phút
select now() as 'Ngày giờ hiện tại', date_add(now(), INTERVAL 5000 MINUTE) as 'ngày giờ hiện tại cộng thêm 5000 phút';

