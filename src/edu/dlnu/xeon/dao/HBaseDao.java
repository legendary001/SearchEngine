package edu.dlnu.xeon.dao;

import edu.dlnu.xeon.domain.HTML;

public interface HBaseDao {
	
	//根据行键查询HTML文档
	public  HTML queryByRowKey(String rowKey);
}
