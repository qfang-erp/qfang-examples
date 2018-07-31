package com.qfang.examples.cloud;

import java.io.Serializable;
import java.util.List;

/**
 * @author huxianyong
 * @date 2017/7/20
 * @since 1.0
 */
public class Pagination<T>  implements Serializable {
    private static final int DEFAULT_PAGE = 1;
    public static int DEFAULT_PAGE_SIZE = 30;
    private List<T> items;
    private int recordCount;
    private int pageSize = DEFAULT_PAGE_SIZE;
    private int currentPageStartIndex = 0;
    private int currentPage = DEFAULT_PAGE;
    private String sortField;
    private Sort sort = Sort.DESC; // 排序方式 DESC/ASC

    public static enum Sort {
        DESC, ASC
    }

    public Pagination() {
        this(DEFAULT_PAGE_SIZE, DEFAULT_PAGE);
    }
    public Pagination(int pageSize, int page) {
        if (pageSize < 1) {
            throw new IllegalArgumentException("Count should be greater than zero!");
        } else if (currentPage < 1) {
            page = 1;
        } else {
            this.pageSize = pageSize;
            this.currentPage = page;
        }
    }
    public int getPageCount() {
        return (recordCount == 0) ? 1 : ((recordCount % pageSize == 0) ? (recordCount / pageSize)
                : (recordCount / pageSize) + 1);
    }

    public int getPreviousPage() {
        if(currentPage > 1) return currentPage - 1;
        else return DEFAULT_PAGE;
    }

    public int getNextPage() {
        if(currentPage < getPageCount()) return currentPage + 1;
        else return getPageCount();
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPageStartIndex() {
        return currentPageStartIndex;
    }

    public void setCurrentPageStartIndex(int currentPageStartIndex) {
        this.currentPageStartIndex = currentPageStartIndex;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }
}
