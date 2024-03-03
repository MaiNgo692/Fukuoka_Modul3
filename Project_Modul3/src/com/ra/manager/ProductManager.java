package com.ra.manager;

import com.ra.entity.Product;
import com.ra.model.ConstStatus;
import com.ra.service.impl.ProductServiceImpl;
import com.ra.util.Console;
import com.ra.util.FontColor;
import java.util.Date;
import java.util.List;

public class ProductManager extends Manager<Product> {
    ProductServiceImpl productService = new ProductServiceImpl();
    @Override
    public void run() {
        boolean isExit = true;
        do{
            System.out.println("************PRODUCT MANAGEMENT*************");
            System.out.println("1. Danh sách sản phẩm");
            System.out.println("2. Thêm sản phẩm mới");
            System.out.println("3. Cập nhật sản phẩm");
            System.out.println("4. Tìm kiếm sản phẩm");
            System.out.println("5. Cập nhật trạng thái sản phẩm");
            System.out.println("6. Thoát");
            System.out.print(FontColor.info("Nhập lựa chọn:"));
            try {
                int select = Integer.parseInt(Console.sc.nextLine());
                switch (select){
                    case 1:
                        showAllProduct();
                        break;
                    case 2:
                        addNewProduct();
                        break;
                    case 3:
                        editProduct();
                        break;
                    case 4:
                        findByName();
                        break;
                    case 5:
                        updateProductStatus();
                        break;
                    case 6:
                        System.out.println(FontColor.success("Thoát"));
                        isExit = false;
                        break;
                    default:
                        System.out.println(FontColor.warning("Hãy nhập vào số từ 1 đến 6!"));
                        break;
                }
            }catch (Exception ex){
                System.out.println(FontColor.warning("Hãy nhập vào 1 số!"));
            }
        }while (isExit);
    }

    private void showAllProduct(){
        currentPage = 1;
        System.out.println("Danh sách sản phẩm:");
        List<Product> productList = productService.findAll();
        showPageCurrent(productList);
    }
    @Override
    public void showPageCurrent(List<Product> listData) {
        List<Product> showData=showDataPage(listData);
        printTitle();
        showData.forEach(Product::displayData);
        printFooter();
        showPage(listData);
    }
    private void addNewProduct(){
        System.out.println("Nhập thông tin sản phẩm:");
        System.out.print(FontColor.info("Nhập mã sản phẩm: "));
        String productId = Console.sc.nextLine();
        System.out.print(FontColor.info("Nhập tên sản phẩm: "));
        String productName = Console.sc.nextLine();
        System.out.print(FontColor.info("Nhập nhà sản xuất: "));
        String manufacturer = Console.sc.nextLine();
        Date created = new Date();
        System.out.print(FontColor.info("Nhập số lô: "));
        int batch = Integer.parseInt(Console.sc.nextLine());
        boolean productStatus = ConstStatus.ProductStt.ACTIVE;
        Product newProduct = new Product(productId,productName,manufacturer,created,batch,productStatus);
        productService.add(newProduct);
    }
    private void editProduct(){
        System.out.print(FontColor.info("Nhập mã sản phẩm cần cập nhật: "));
        String productId = Console.sc.nextLine();
        Product editProduct = productService.findId(productId);
        if(editProduct!=null){
            productService.edit(editFieldProduct(editProduct));
        }else {
            System.out.println("Không tìm thấy mã sản phẩm "+productId);
        }
    }
    private Product editFieldProduct(Product product){
        int select;
        boolean isExit=true;
        do {
            System.out.println("Chọn trường muốn cập nhật:");
            System.out.println("1. Tên sản phẩm");
            System.out.println("2. Nhà sản suất");
            System.out.println("3. Số lô");
            System.out.println("4. Trạng thái");
            System.out.println("5. Thoát");
            System.out.print(FontColor.info("Nhập lựa chọn:"));
            try {
                select = Integer.parseInt(Console.sc.nextLine());
                switch (select){
                    case 1:
                        System.out.println("Tên sản phẩm hiện tại: " + product.getProductName());
                        System.out.print(FontColor.info("Nhập tên mới cho sản phẩm:"));
                        String newName = Console.sc.nextLine();
                        product.setProductName(newName);
                        break;
                    case 2:
                        System.out.println("Tên nhà sản suất hiện tại : " + product.getManufacturer());
                        System.out.print(FontColor.info("Nhập tên nhà sản xuất mới:"));
                        String newManufacture = Console.sc.nextLine();
                        product.setManufacturer(newManufacture);
                        break;
                    case 3:
                        System.out.println("Số lô hiện tại : " + product.getBatch());
                        System.out.print(FontColor.info("Nhập số lô mới:"));
                        int batch = Integer.parseInt(Console.sc.nextLine());
                        product.setBatch(batch);
                        break;
                    case 4:
                        System.out.println("Chọn trạng thái cần update:");
                        System.out.println("1. Hoạt động");
                        System.out.println("2. Không hoạt động");
                        System.out.print(FontColor.info("Nhập lựa chọn:"));
                        int select1= inputProductStatus() ;
                        product.setProductStatus(select1 == 1);
                        break;
                    case 5:
                        isExit=false;
                        break;
                    default:
                        System.out.println(FontColor.warning("Hãy nhập từ 1 đến 5"));
                        break;
                }
            }catch (NumberFormatException ex){
                System.out.println(FontColor.warning("Hãy nhập 1 số!"));
            }

        }while (isExit);
        return product;
    }
    private int inputProductStatus(){
        int select1 ;
        do {
            try{
                select1 = Integer.parseInt(Console.sc.nextLine());
                if (select1 == 1 || select1 == 2) {
                    return select1;
                } else System.out.println(FontColor.warning("Hãy nhập 1 hoặc 2!"));
            }catch (NumberFormatException ex){
                System.out.println(FontColor.warning("Hãy nhập 1 số!"));
            }
        }while (true);
    }
    private void findByName(){
        System.out.print(FontColor.info("Nhập tên sản phẩm:"));
        String name = Console.sc.nextLine();
        List<Product> result = productService.findByName(name);
        if(!result.isEmpty()){
            showPageCurrent(result);
        }else {
            System.out.println(FontColor.warning("Không tìm thấy sản phẩm nào!"));
        }

    }
    private void updateProductStatus(){
        System.out.print(FontColor.info("Nhập mã sản phẩm cần cập nhật trạng thái: "));
        String productId = Console.sc.nextLine();
        Product updateProduct = productService.findId(productId);
        if(updateProduct!=null){
            System.out.println("Chọn trạng thái cần update:");
            System.out.println("1. Hoạt động");
            System.out.println("2. Không hoạt động");
            System.out.print(FontColor.info("Nhập lựa chọn:"));
            int select = inputProductStatus();
            updateProduct.setProductStatus(select==1);
            productService.edit(updateProduct);
        }else {
            System.out.println(FontColor.warning("Không tìm thấy mã sản phẩm: "+productId));
        }

    }
    private void printTitle() {
        System.out.printf("|%s|%s|%s|%s|%s|%s|%s|\n", FontColor.centerString(12,"Mã danh mục"), FontColor.centerString(30,"Tên danh mục"),
                FontColor.centerString(30,"Nhà sản xuất"),FontColor.centerString(30,"Ngày tạo") ,FontColor.centerString(10,"Số lô"),
                FontColor.centerString(10,"Số lượng"),FontColor.centerString(17,"Trạng thái"));
    }
    private void printFooter(){
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");
    }
}
