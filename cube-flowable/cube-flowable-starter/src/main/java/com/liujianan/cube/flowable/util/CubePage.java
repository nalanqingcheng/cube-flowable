package com.liujianan.cube.flowable.util;

import java.util.ArrayList;

/**
 * @version v1.0
 * @ProjectName: cube-flowable
 * @ClassName: CubePage
 * @Description: cube工作流引擎分页类
 * @Author: 风清扬 [刘佳男]
 * @Date: 2022/11/16 14:27
 */

public class CubePage<E> extends ArrayList<E> {

    private static final long serialVersionUID = 1L;
    private int pageNum;
    private int pageSize;
    private long total;
    private int pages;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

}
