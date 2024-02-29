package com.ra.manager;

import java.util.List;

public interface IManager<T> {
    void run();
    void showPage(List<T> listData);
    void selectPage(List<T> listData,int pages);
    void showPageCurrent(List<T> listData);
    List<T> showDataPage(List<T> listData);
}
