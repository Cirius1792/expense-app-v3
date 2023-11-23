package com.clt.domain.commons;

public class Page {
  int pageSize;
  int pageNumber;

  public Page(int pageSize, int pageNumber) {
    this.pageSize = pageSize;
    this.pageNumber = pageNumber;
  }

  @Override
  public String toString() {
    return "Page{" + "pageSize=" + pageSize + ", pageNumber=" + pageNumber + '}';
  }

  public int startFrom() {
    return (this.pageNumber - 1) * this.pageSize;
  }

  public int endAt() {
    return this.startFrom() + this.pageSize;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }
}
