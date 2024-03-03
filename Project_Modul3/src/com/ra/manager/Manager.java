package com.ra.manager;

import com.ra.util.Console;
import com.ra.util.FontColor;

import java.util.List;

public class Manager<T> implements IManager<T>{
    int dataInPage = 10;
    int pages;
    int endPageData;
    int currentPage =1;
    @Override
    public void run() {

    }

    @Override
    public void showPage(List<T> listData) {
        if(listData.size()>dataInPage){
            pages = listData.size()/dataInPage;
            endPageData = listData.size()%dataInPage;
            if(endPageData!=0){
                pages++;
            }
            System.out.print("<");
            for(int i = 1; i<= pages;i++){
                System.out.print(" "+ i  +" ");
            }
            System.out.print(">\n");
            selectPage(listData,pages);
        }
    }

    @Override
    public void selectPage(List<T> listData, int pages) {
        System.out.println("Nhập trang muốn hiển thị: ");
        int selectPage = Integer.parseInt(Console.sc.nextLine());
        if(selectPage< 1 ||selectPage > pages){
            System.out.println(FontColor.warning("Hãy nhập từ 1 đến "+pages+"!"));
        }else {
            currentPage = selectPage;
             showPageCurrent(listData);
        }
    }

    @Override
    public void showPageCurrent(List<T> listData) {
    }

    @Override
    public List<T> showDataPage(List<T> listData) {
        if(listData.size()>dataInPage){
            if(currentPage == pages && endPageData>0){
                return listData.subList((currentPage-1)*dataInPage,(currentPage-1)*dataInPage+endPageData);
            }else {
                return listData.subList((currentPage-1)*dataInPage,(currentPage-1)*dataInPage+10);
            }
        }
        return listData;
    }
}
