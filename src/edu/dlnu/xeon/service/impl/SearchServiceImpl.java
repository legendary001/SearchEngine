package edu.dlnu.xeon.service.impl;

import edu.dlnu.xeon.dao.HTMLIndexDao;
import edu.dlnu.xeon.dao.impl.HBaseDaoImpl;
import edu.dlnu.xeon.dao.impl.HTMLIndexDaoImpl;
import edu.dlnu.xeon.domain.HTML;
import edu.dlnu.xeon.domain.QueryResult;
import edu.dlnu.xeon.service.SearchService;

public class SearchServiceImpl implements SearchService{
	private HTMLIndexDao indexDao=new HTMLIndexDaoImpl();
	
	
	//分页查询
	public QueryResult<HTML> pagingSearch(String queryString,QueryResult<HTML> queryResult){
		return indexDao.query(queryString,queryResult);
	}
	
	//返回查询网页的快照
	public byte[] getPageShot(String title){
		HBaseDaoImpl hbaseDao=new HBaseDaoImpl("html");
		HTML html=hbaseDao.queryByRowKey(title);
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
