package edu.dlnu.xeon.utils;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class LuceneUtils {
	private static Directory directory;
	private static Analyzer analyzer;
	private static IndexWriter indexWriter;
	
	static{
		try {
			directory=FSDirectory.open(new File("F:\\JavaEE-workspace\\Web\\SearchEngine\\indexDirectory"));
			analyzer=new IKAnalyzer();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public static IndexWriter getIndexWriter() {
		// 在第一次使用IndexWriter是进行初始化
		if (indexWriter == null) {
			synchronized (LuceneUtils.class) { // 注意线程安全问题
				if (indexWriter == null) {
					try {
						indexWriter = new IndexWriter(directory, analyzer, MaxFieldLength.LIMITED);
						indexWriter.setMergeFactor(5);
						System.out.println("=== 已经初始化 IndexWriter ===");
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}

			// 指定一段代码，会在JVM退出之前执行。
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					try {
						indexWriter.close();
						System.out.println("=== 已经关闭 IndexWriter ===");
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			});
		}

		return indexWriter;
	}
	
	public static Directory getDirectory() {
		return directory;
	}

	public static void setDirectory(Directory directory) {
		LuceneUtils.directory = directory;
	}

	public static Analyzer getAnalyzer() {
		return analyzer;
	}

	public static void setAnalyzer(Analyzer analyzer) {
		LuceneUtils.analyzer = analyzer;
	}
	
	
}
