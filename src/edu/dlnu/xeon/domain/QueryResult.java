package edu.dlnu.xeon.domain;

import java.util.List;

public class QueryResult<T> {
	private List<T> list;
	private int pageCount;
	private int pageSize=10;
	private int pageNow=0;
	private int rowCount;
	public QueryResult(){}
	public QueryResult(List<T> list, int rowCount) {
		this.list = list;
		this.rowCount = rowCount;
		if(this.rowCount%pageSize==0){
			pageCount=this.rowCount/pageSize;
		}else{
			pageCount=this.rowCount/pageSize+1;
		}
	}
	
	public QueryResult(List<T> list) {
		super();
		this.list = list;
	}
	public QueryResult(int pageNow) {
		this.pageNow = pageNow;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getPageNow() {
		return pageNow;
	}
	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	@Override
	public String toString() {
		return "QueryResult [list=" + list + ", pageCount=" + pageCount
				+ ", pageSize=" + pageSize + ", pageNow=" + pageNow
				+ ", rowCount=" + rowCount + "]";
	}
	
	
	
	
}
