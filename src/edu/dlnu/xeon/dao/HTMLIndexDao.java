package edu.dlnu.xeon.dao;

import edu.dlnu.xeon.domain.HTML;
import edu.dlnu.xeon.domain.QueryResult;

public interface HTMLIndexDao {
	//HTML文档保存到索引库
	public void save(HTML html);
	
	//分页查询,返回对象QueryResult来进行分页显示
	public QueryResult<HTML> query(String queryString,QueryResult<HTML> result);
}
