package edu.dlnu.xeon.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.util.NumericUtils;
import org.wltea.analyzer.lucene.IKQueryParser;
import org.wltea.analyzer.lucene.IKSimilarity;

import edu.dlnu.xeon.domain.HTML;
import edu.dlnu.xeon.domain.QueryResult;
import edu.dlnu.xeon.utils.HTMLDocumentUtils;
import edu.dlnu.xeon.utils.LuceneUtils;


public class HTMLIndexDao {
	
	/**
	 * 保存javaBean到索引库
	 * @param HTML 要保存的javaBean
	 */
	public void save(HTML html){
		Document doc=HTMLDocumentUtils.HTML2Document(html);
		doc.setBoost(0.5F); // 1F表示正常得分，大于1表示高分，小于1表示低分
		IndexWriter indexWriter=LuceneUtils.getIndexWriter();
		try {
			indexWriter.addDocument(doc);//添加
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void indexCommit() throws CorruptIndexException, IOException{
		LuceneUtils.getIndexWriter().commit();
	}
	/**
	 * 分页查询
	 * @param queryString 执行查询的条件
	 * @param first 为刚开始查询的记录索引
	 * @param max 为查询的最大记录数
	 * @return
	 */
	
	public QueryResult<HTML> query(String queryString,QueryResult<HTML> result){
		List<HTML> list=new ArrayList<HTML>();
		//1.首先转换查询对象(从多个字段中查询),则要用到QueryParser的子类MultiFieldQueryParser
//		QueryParser queryParser=new MultiFieldQueryParser(Version.LUCENE_30,new String[]{"title","content"}, LuceneUtils.getAnalyzer());
		IndexSearcher indexSearcher=null;
		int rowCount=0;
		int first=result.getPageNow()*result.getPageSize();
		try {
			Query query=IKQueryParser.parseMultiField(new String[]{"title","description"}, queryString);
			indexSearcher=new IndexSearcher(LuceneUtils.getDirectory());
			indexSearcher.setSimilarity(new IKSimilarity());
			Sort sort = new Sort(new SortField("title", SortField.STRING, true));//设置排序条件
			TopDocs topDocs=indexSearcher.search(query,null,first+result.getPageSize(),sort);
			// ========================================================================================== 【创建高亮器】
			Query myQuery = query; // 查询条件
			String preTag = "<font color='red'>"; // 前缀
			String postTag = "</font>"; // 后缀
			int size = 40; // 摘要大小

			Formatter formatter = new SimpleHTMLFormatter(preTag, postTag); // 前缀、后缀
			Scorer scorer = new QueryScorer(myQuery);
			Highlighter highlighter = new Highlighter(formatter, scorer);
			highlighter.setTextFragmenter(new SimpleFragmenter(size)); // 摘要大小（字数）
			// ==========================================================================================
			
			rowCount=topDocs.totalHits;
		//2.返回中间过程处理结果
			ScoreDoc[] scoreDocs=topDocs.scoreDocs;
			HTML html=null;
		//3.处理得到最终结果
			int endIndex=Math.min(result.getPageSize()+first,scoreDocs.length);
			for(int i=first;i<endIndex;i++){
				int docId=scoreDocs[i].doc;
				Document doc=indexSearcher.doc(docId);
				
				// ======================================================================================== 【使用高亮器】
				// 一次高亮一个字段，返回高亮后的结果，如果要高亮的字段值中没有出现关键字，就会返回null
				String text = highlighter.getBestFragment(LuceneUtils.getAnalyzer(), "description", doc.get("description"));
				if (text != null) {
					doc.getField("description").setValue(text); // 使用高亮后的文本替换原始内容
				}
				text=highlighter.getBestFragment(LuceneUtils.getAnalyzer(), "title", doc.get("title"));
				if (text != null) {
					doc.getField("title").setValue(text);
				}
				// ========================================================================================
				
				html=HTMLDocumentUtils.document2HTML(doc);
				list.add(html);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(indexSearcher!=null){
				try {
					indexSearcher.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return new QueryResult<HTML>(list,rowCount);
	}
	
	public QueryResult<HTML> query(String queryString){
		List<HTML> list=new ArrayList<HTML>();
		//1.首先转换查询对象(从多个字段中查询),则要用到QueryParser的子类MultiFieldQueryParser
//		QueryParser queryParser=new MultiFieldQueryParser(Version.LUCENE_30,, LuceneUtils.getAnalyzer());
		IndexSearcher indexSearcher=null;
		try {
			Query query=IKQueryParser.parseMultiField(new String[]{"title","description"}, queryString);
			indexSearcher=new IndexSearcher(LuceneUtils.getDirectory());
			indexSearcher.setSimilarity(new IKSimilarity());
			TopDocs topDocs=indexSearcher.search(query, 1000);
		//2.返回中间过程处理结果
			ScoreDoc[] scoreDocs=topDocs.scoreDocs;
			HTML html=null;
		//3.处理得到最终结果
			for(ScoreDoc scoreDoc:scoreDocs){
				int docId=scoreDoc.doc;
				Document doc=indexSearcher.doc(docId);
				html=HTMLDocumentUtils.document2HTML(doc);
				list.add(html);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(indexSearcher!=null){
				try {
					indexSearcher.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return new QueryResult<HTML>(list);
	}
	
	
	/**
	 * 删除索引库中的记录
	 * Term为最小查询单位，查询某个字段的值
	 * @param id
	 */
	public void delete(Integer id){
		IndexWriter indexWriter=null;
		try {
			//用唯一标示符id来删除，删除的一定是唯一的一条记录，根据目录区的索引来删除数据区中的记录
			Term term=new Term("id", NumericUtils.intToPrefixCoded(id));
			indexWriter=new IndexWriter(LuceneUtils.getDirectory(), LuceneUtils.getAnalyzer(), MaxFieldLength.LIMITED);
			indexWriter.deleteDocuments(term);//删除
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 更新索引
	 * 更新就是先删除后创建
	 * @param HTML
	 */
	public void update(HTML HTML){
		Document doc=HTMLDocumentUtils.HTML2Document(HTML);
		IndexWriter indexWriter=null;
		
		try {
//			Term term=new Term("id",NumericUtils.intToPrefixCoded(HTML.getId()));
//			indexWriter=new IndexWriter(LuceneUtils.getDirectory(), LuceneUtils.getAnalyzer(), MaxFieldLength.LIMITED);
//			indexWriter.updateDocument(term, doc);//更新索引库中的记录
			//indexWriter.deleteDocuments(term);
			//indexWriter.addDocument(doc);//添加
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
