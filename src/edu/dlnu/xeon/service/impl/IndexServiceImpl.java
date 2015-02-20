package edu.dlnu.xeon.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.index.CorruptIndexException;

import edu.dlnu.xeon.dao.impl.HBaseDao;
import edu.dlnu.xeon.dao.impl.HTMLIndexDao;
import edu.dlnu.xeon.domain.HTML;
import edu.dlnu.xeon.utils.LuceneUtils;

public class IndexServiceImpl {
	private HBaseDao hbaseDao=new HBaseDao("html");
	private HTMLIndexDao indexDao=new HTMLIndexDao();
	
	public void createIndex() throws CorruptIndexException, IOException{
		List<HTML> list=hbaseDao.queryAll();
		for(HTML html:list){
			indexDao.save(html);
		}
		indexDao.indexCommit();
	}
	
	public void optimize() throws CorruptIndexException, IOException{
		LuceneUtils.getIndexWriter().optimize();
	}
	
	public static void main(String args[]) throws CorruptIndexException, IOException{
		new IndexServiceImpl().createIndex();
	}
}

