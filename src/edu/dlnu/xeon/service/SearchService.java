package edu.dlnu.xeon.service;

import edu.dlnu.xeon.domain.HTML;
import edu.dlnu.xeon.domain.QueryResult;

public interface SearchService {
	//分页查询
	public QueryResult<HTML> pagingSearch(String queryString,QueryResult<HTML> queryResult);
	
	//根据rowkey获得网页快照内容，此处rowkey为标题.getBytes()
	public byte[] getPageShot(String title);
}
