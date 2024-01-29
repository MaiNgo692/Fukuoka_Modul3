create table Khoa(
	makhoa char(10) primary key,
    tenkhoa char(30),
    dienthoai char(10)
);
select * from Khoa;
create table GiangVien(
	magv int AUTO_INCREMENT PRIMARY KEY,
    hotengv char(30),
    luong decimal(5,2),
    makhoa char(10),
    FOREIGN KEY (makhoa) REFERENCES Khoa(makhoa)
);
create table SinhVien(
	masv int AUTO_INCREMENT PRIMARY KEY,
    hotensv char(30),
    makhoa char(10),
    namsinh int,
    quequan char(30),
    FOREIGN KEY (makhoa) REFERENCES Khoa(makhoa)
);
create table DeTai(
	madt char(10) primary key,
    tendt char(30),
    kinhphi int,
    NoiThucTap char(30)
);
create table HuongDan(
	masv int,
    madt char(10),
    magv int,
    ketqua decimal(5,2),
    FOREIGN KEY (masv) REFERENCES SinhVien(masv),
    FOREIGN KEY (madt) REFERENCES DeTai(madt),
    FOREIGN KEY (magv) REFERENCES GiangVien(magv)
);

INSERT INTO Khoa VALUES
 ('Geo','Dia ly va QLTN',3855413),
 ('Math','Toan',3855411),
 ('Bio','Cong nghe sinh hoc',3855412);
 INSERT INTO GiangVien VALUES
 (11,'Thanh Binh',700,'Geo'), 
 (12,'Thu Huong',500,'Math'),
 (13,'Chu Vinh',650,'Geo'),
 (14,'Le Thi Ly',500,'Bio'),
 (15,'Tran Son',900,'Math');
 INSERT INTO SinhVien VALUES
 (1,'Le Van Son','Bio',1990,'Nghe An'),
 (2,'Nguyen Thi Mai','Geo',1990,'Thanh Hoa'),
 (3,'Bui Xuan Duc','Math',1992,'Ha Noi'),
 (4,'Nguyen Van Tung','Bio',null,'Ha Tinh'),
 (5,'Le Khanh Linh','Bio',1989,'Ha Nam'),
 (6,'Tran Khac Trong','Geo',1991,'Thanh Hoa'),
 (7,'Le Thi Van','Math',null,'null'),
 (8,'Hoang Van Duc','Bio',1992,'Nghe An');
 INSERT INTO DeTai VALUES
 ('Dt01','GIS',100,'Nghe An'),
 ('Dt02','ARC GIS',500,'Nam Dinh'),
 ('Dt03','Spatial DB',100, 'Ha Tinh'),
 ('Dt04','MAP',300,'Quang Binh' );
 INSERT INTO HuongDan VALUES
 (1,'Dt01',13,8),
 (2,'Dt03',14,0),
 (3,'Dt03',12,10),
 (5,'Dt04',14,7),
 (6,'Dt01',13,Null),
 (7,'Dt04',11,10),
 (8,'Dt03',15,6);
 -- 1. Đưa ra thông tin gồm mã số, họ tên và tên khoa của tất cả các giảng viên
 select gv.magv, gv.hotengv, k.tenkhoa from GiangVien gv
 join Khoa k on gv.makhoa = k.makhoa;
 
 -- 2. Đưa ra thông tin gồm mã số, họ tên và tên khoa của các giảng viên của khoa ‘Dia ly và QLTN’
 select gv.magv, gv.hotengv, k.tenkhoa from GiangVien gv
 join Khoa k on gv.makhoa = k.makhoa
 having k.tenkhoa = 'Dia ly va QLTN';
 
 -- 3. Cho biết số sinh viên của khoa ‘Cong nghe sinh hoc’
 select count(k.makhoa) as 'so luong sinh vien khoa sinh hoc' from sinhvien sv
 join khoa k on sv.makhoa = k.makhoa
 where k.tenkhoa = 'Cong nghe sinh hoc';
 
-- 4. Đưa ra danh sách gồm mã số, họ tên và tuổi của các sinh viên khoa ‘Toan’
 select sv.masv, sv.hotensv , 2024 - sv.namsinh as 'Tuoi'from sinhvien sv
 join khoa k on sv.makhoa = k.makhoa
 where k.tenkhoa = 'Toan';
 
-- 5. Cho biết số giảng viên của khoa ‘Cong nghe sinh hoc’

select count(k.makhoa) as 'so luong giang vien khoa sinh hoc' from giangvien gv
 join khoa k on gv.makhoa = k.makhoa
 where k.tenkhoa = 'Cong nghe sinh hoc';
 
-- 6. Cho biết thông tin về sinh viên không tham gia thực tập

select sv.masv, sv.hotensv, hd.madt from sinhvien sv
 left join  huongdan hd on sv.masv = hd.masv
 where hd.madt is null;
				
-- 7. Đưa ra mã khoa, tên khoa và số giảng viên của mỗi khoa	
select k.makhoa, k.tenkhoa, count(gv.magv) as 'So giang vien' from khoa k
join giangvien gv on k.makhoa = gv.makhoa
group by gv.makhoa;

-- 8. Cho biết số điện thoại của khoa mà sinh viên có tên ‘Le Van Son’ đang theo học	
select k.makhoa, k.tenkhoa ,k.dienthoai from khoa k
join sinhvien sv on k.makhoa = sv.makhoa
where sv.hotensv ='Le Van Son';		

-- II								
-- 1. Cho biết mã số và tên của các đề tài do giảng viên ‘Tran Son’ hướng dẫn		
select dt.madt, dt.tendt 
from detai dt 
join huongdan hd
on dt.madt = hd.madt 
join giangvien gv
on hd.magv = gv.magv
where gv.hotengv ='Tran Son';	
					
-- 2. Cho biết tên đề tài không có sinh viên nào thực tập	
select dt.madt, dt.tendt 
from detai dt 
left join huongdan hd
on dt.madt = hd.madt 
left join sinhvien sv
on hd.masv = sv.masv
where sv.masv is null;		
					
-- 3. Cho biết mã số, họ tên, tên khoa của các giảng viên hướng dẫn từ 3 sinh viên trở lên.		
select * ,count(hd.masv)
from giangvien gv
join huongdan hd
on gv.magv = hd.magv
join khoa k
on k.makhoa = gv.makhoa
group by hd.magv
having 	count(hd.masv)=2;				
	
-- 4. Cho biết mã số, tên đề tài của đề tài có kinh phí cao nhất
select dt.madt, dt.tendt ,dt.kinhphi from detai dt
-- where dt.kinhphi = (select max(kinhphi) from detai);	
having 	dt.kinhphi = (select max(kinhphi) from detai);	
					
-- 5. Cho biết mã số và tên các đề tài có nhiều hơn 2 sinh viên tham gia thực tập
select dt.madt, dt.tendt 
from detai dt
join huongdan hd
on dt.madt = hd.madt
group by hd.madt
having count(hd.masv)>2;
								
-- 6. Đưa ra mã số, họ tên và điểm của các sinh viên khoa ‘Dia ly va QLTN’	
select sv.masv, sv.hotensv, hd.ketqua
from sinhvien sv
join huongdan hd
on sv.masv = hd.masv
join khoa k
on k.makhoa = sv.makhoa
where k.tenkhoa= 'Dia ly va QLTN';
						
-- 7. Đưa ra tên khoa, số lượng sinh viên của mỗi khoa	
select k.makhoa, k.tenkhoa, count(sv.masv) as'So luong sinh vien'
from khoa k
left join sinhvien sv
on sv.makhoa = 	k.makhoa
group by sv.makhoa;	
					
-- 8. Cho biết thông tin về các sinh viên thực tập tại quê nhà		
select sv.*, dt.NoiThucTap
from sinhvien sv
join huongdan hd
on sv.masv = hd.masv
join detai dt
on dt.madt = hd.madt
where sv.quequan = dt.NoiThucTap;
 					
-- 9. Hãy cho biết thông tin về những sinh viên chưa có điểm thực tập
select sv.*
from sinhvien sv
join huongdan hd
on hd.masv = sv.masv
where hd.ketqua is null;	
							
-- 10. Đưa ra danh sách gồm mã số, họ tên các sinh viên có điểm thực tập bằng 0		
select sv.masv,sv.hotensv
from sinhvien sv
join huongdan hd
on hd.masv = sv.masv
where hd.ketqua = 0.0;		
				
-- 								
-- III. 								
-- 1. Tính lương trung bình của giảng viên theo từng khoa:	
select k.tenkhoa, avg(gv.luong)
from khoa k
join giangvien gv
on k.makhoa = gv.makhoa
group by k.makhoa;			
				
-- 2. Tính số lượng sinh viên theo năm sinh và sắp xếp theo số lượng giảm dần	
select sv.namsinh, count(*) as sl
from sinhvien sv
group by sv.namsinh
order by sl desc;	
						
-- 3.  Tính số lượng đề tài đã được hướng dẫn bởi từng giảng viên và sắp xếp theo thứ tự giảm dần	
select count(dt.madt) as 'So luong de tai', gv.hotengv
from giangvien gv
join huongdan hd
on gv.magv = hd.magv
join detai dt
on dt.madt = hd.madt
group by hd.madt
order by count(dt.madt) desc;	
						
-- 4. Liệt kê các đề tài có kinh phí cao nhất trong từng khoa	
select khoa, max(kinhphidt) from (select k.tenkhoa as khoa, dt.tendt as detai, dt.kinhphi as kinhphidt
from khoa k
join sinhvien sv
on k.makhoa = sv.makhoa	
join huongdan hd
on hd.masv = sv.masv
join detai dt
on dt.madt = hd.madt) kp
group by khoa;
	
select khoa, max(kinhphidt) from
(select distinct
khoa.tenkhoa as khoa,
DT.tendt as  detai,
DT.kinhphi as kinhphidt
From DeTai DT
join HuongDan HD on DT.madt = HD.madt
join GiangVien GV on GV.magv = HD.magv
join Khoa on khoa.makhoa = GV.makhoa) as kinhphi
group by
khoa;	
		
-- 5. Liệt kê các sinh viên và thông tin đề tài họ đã đăng ký, nhưng chỉ hiển thị những sinh viên có kết quả hướng dẫn dưới mức trung bình 		
select sv.masv,sv.hotensv,dt.tendt,hd.ketqua
from sinhvien sv
join huongdan hd
on sv.masv= hd.masv	
join detai dt
on dt.madt = hd.madt
where hd.ketqua < 7	;						