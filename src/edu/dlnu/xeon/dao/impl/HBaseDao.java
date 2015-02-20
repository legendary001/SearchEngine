package edu.dlnu.xeon.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import edu.dlnu.xeon.domain.HTML;


public class HBaseDao {
	public static Configuration configuration;
	public static HTablePool pool;
	private static HBaseAdmin hBaseAdmin;
	private String tableName;
	static{
		configuration=HBaseConfiguration.create();
		configuration.set("hbase.zookeeper.property.clientPort", "2222");
		configuration.set("hbase.zookeeper.quorum", "192.168.80.3");
		configuration.set("hbase.master","192.168.80.3:9000");
		pool=new HTablePool(configuration,1000);
		try {
			hBaseAdmin = new HBaseAdmin(configuration);
		} catch (MasterNotRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public HBaseDao(){}
	
	public HBaseDao(String tableName){
		this.tableName=tableName;
	}
	/**
	 *创建表
	 * @param tableName
	 */
	public  void createTable(String...args){
		System.out.println("start create table....");
		try {
			if(hBaseAdmin.tableExists(tableName)){
				hBaseAdmin.disableTable(tableName);
				hBaseAdmin.deleteTable(tableName);
				System.out.println(tableName+"is exist,delete....");
			}
			HTableDescriptor tableDescriptor=new HTableDescriptor(tableName);
			for(int i=0;i<args.length;i++){
				tableDescriptor.addFamily(new HColumnDescriptor(args[i]));
			}
			hBaseAdmin.createTable(tableDescriptor);
			System.out.println("end create table....");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 插入数据
	 * @param tableName
	 */
	public void insertData(HTML html){
		System.out.println("start insert data....");
		HTable table=(HTable)pool.getTable(tableName);
		Put put=new Put(html.getTitle().getBytes());
		put.add("title".getBytes(), null,html.getTitle().getBytes());
		put.add("description".getBytes(),null,html.getDescription().getBytes());
		put.add("date".getBytes(),null,String.valueOf(html.getDate().getTime()).getBytes());
		put.add("content".getBytes(),null,html.getContent());
		put.add("url".getBytes(), null, html.getUrl().getBytes());
		try {
			table.put(put);
			System.out.println("end insert data....");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 删除表格
	 * @param tableName
	 */
	public void dropTable(){
		try {
			if(hBaseAdmin.tableExists(tableName)){
				hBaseAdmin.disableTable(tableName);
				hBaseAdmin.deleteTable(tableName);
				System.out.println(tableName+" has been deleted...");
			}else{
				System.out.println(tableName+" does not exist...");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据行键删除某条记录
	 * @param tableName
	 * @param rowKey
	 */
	public  void deleteRow(String rowKey){
		try {
			HTable table = new HTable(configuration,tableName);
			Delete delete=new Delete(rowKey.getBytes());
			table.delete(delete);
			System.out.println("delete row record success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 查询所有元素
	 * @param tableName
	 */
	public List<HTML> queryAll(){
		List<HTML> list=new ArrayList<HTML>();
		HTable table=(HTable)pool.getTable(tableName);
		ResultScanner rs=null;
		String title=null;
		String description=null;
		String url=null;
		Date date=null;
		HTML html=null;
		try {
			rs=table.getScanner(new Scan());
			for(Result r:rs){
				for(KeyValue keyValue:r.raw()){
					String key=new String(keyValue.getFamily());
					if(key.equals("title")){
						title=new String(keyValue.getValue());
					}else if(key.equals("description")){
						description=new String(keyValue.getValue());
					}else if(key.equals("date")){
						date=new Date(Long.parseLong(new String(keyValue.getValue())));
					}else{
						url=new String(keyValue.getValue());
					}
				}
				html=new HTML(title,description,date,null,url);
				System.out.println(html);
				list.add(html);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(rs!=null){
				rs.close();
			}
		}
		return list;
	}
	
	/**
	 * 单条件单结果查询
	 * @param tableName
	 * @param rowKey
	 */
	public  HTML queryByCondition1(String rowKey){
		HTable table=(HTable)pool.getTable(tableName);
		Get get=new Get(rowKey.getBytes());
		HTML html=null;
		String key=null;
		try {
			Result r=table.get(get);
			for(KeyValue keyValue:r.raw()){
				key=new String(keyValue.getFamily());
				if(key.equals("content")){
					html=new HTML();
					html.setContent(keyValue.getValue());
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return html;
	}
	/**
	 * 单条件多结果查询
	 * @param tableName
	 * @param rowKey
	 */
	public  void queryByCondition2(String key){
		HTable table=(HTable)pool.getTable(tableName);
		Filter filter=new SingleColumnValueFilter(Bytes.toBytes("column1"), null, CompareOp.EQUAL, Bytes.toBytes(key));
		Scan scan=new Scan();
		scan.setFilter(filter);
		ResultScanner rs=null;
		try {
			rs=table.getScanner(scan);
			for(Result r:rs){
				System.out.println("rowKey:"+new String(r.getRow()));
				for(KeyValue keyValue:r.raw()){
					System.out.println("raw:"+new String(keyValue.getFamily())+"===value:"+new String(keyValue.getValue()));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(rs!=null){
				rs.close();
			}
		}
	}
	//组合查询
}
