package com.interswitchng.onlinebookstore.dto;

public class PageParams implements SearchParams{
  private static final int PAGE_NUM_DEFAULT = 1;
  private static final int PAGE_SIZE_DEFAULT = 20;

  private Integer pageNum;
  private Integer pageSize;

  public PageParams() {
    pageNum = PAGE_NUM_DEFAULT;
    pageSize = PAGE_SIZE_DEFAULT;
  }

  public PageParams(Integer pageNum, Integer pageSize) {
    this.pageNum = pageNum;
    this.pageSize = pageSize;
  }

  public Integer getPageNum() {
    return (pageNum == null || pageNum < 1) ?
        PAGE_NUM_DEFAULT : pageNum;
  }

  public void setPageNum(Integer pageNum) {
    this.pageNum = pageNum;
  }

  public Integer getPageSize() {
    return (pageSize == null || pageSize < 1) ?
        PAGE_SIZE_DEFAULT : pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

}
