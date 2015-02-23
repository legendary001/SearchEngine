package edu.dlnu.xeon.util;

import java.util.Date;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.util.NumericUtils;
import org.jruby.util.Numeric;

import edu.dlnu.xeon.domain.HTML;

public class HTMLDocumentUtils {
	/**
	 * HTML实体转Document
	 * @param html
	 * @return
	 */
	public static Document HTML2Document(HTML html){
		Document doc=new Document();
		doc.add(new Field("title",html.getTitle(),Store.YES,Index.ANALYZED));
		doc.add(new Field("description",html.getDescription(),Store.YES,Index.ANALYZED));
		doc.add(new Field("date",NumericUtils.longToPrefixCoded(html.getDate().getTime()),Store.YES,Index.NO));
		doc.add(new Field("url",html.getUrl(),Store.YES,Index.NO));
		return doc;
	}

	public static HTML document2HTML(Document doc) {
		String title=doc.get("title");
		String description=doc.get("description");
		Date date=new Date(NumericUtils.prefixCodedToLong(doc.get("date")));
		String url=doc.get("url");
		HTML html=new HTML(title,description,date,null,url);
		return html;
	}
}
