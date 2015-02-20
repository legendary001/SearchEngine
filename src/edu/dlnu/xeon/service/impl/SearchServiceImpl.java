package edu.dlnu.xeon.service.impl;

import edu.dlnu.xeon.dao.impl.HBaseDao;
import edu.dlnu.xeon.dao.impl.HTMLIndexDao;
import edu.dlnu.xeon.domain.HTML;
import edu.dlnu.xeon.domain.QueryResult;

public class SearchServiceImpl {
	private HTMLIndexDao indexDao=new HTMLIndexDao();
	private HBaseDao hbaseDao=new HBaseDao("html");
	public QueryResult<HTML> search(String queryString){
		return indexDao.query(queryString);
	}
	public QueryResult<HTML> pagingSearch(String queryString,QueryResult<HTML> queryResult){
		return indexDao.query(queryString,queryResult);
	}
	public byte[] getPageShot(String title){
		HTML html=hbaseDao.queryByCondition1(title);
		
		return html.getContent();
	}
//	public static void main(String args[]){
//		List<HTML> list=new SearchServiceImpl().search("视频");
//		System.out.println("总结果数："+list.size());
//		for(HTML html:list){
//			System.out.println("title:"+html.getTitle());
//			System.out.println("description:"+html.getDescription());
//			System.out.println("date:"+html.getDate());
//			System.out.println("content:"+html.getContent());
//			System.out.println("===========================================");
//		}
//	}
	
}
