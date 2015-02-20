package edu.dlnu.xeon.domain;

import java.util.Arrays;
import java.util.Date;

public class HTML {
	private String title;
	private String description;
	private Date date;
	private byte[] content;
	private String url;
	public HTML(String title, String description, Date date, byte[] content,
			String url) {
		this.title = title;
		this.description = description;
		this.date = date;
		this.content = content;
		this.url = url;
	}
	public HTML(){}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "HTML [title=" + title + ", description=" + description
				+ ", date=" + date + ", content=" + Arrays.toString(content)
				+ ", url=" + url + "]";
	}
	
	
	
}
