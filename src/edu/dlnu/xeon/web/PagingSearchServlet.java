package edu.dlnu.xeon.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.dlnu.xeon.domain.HTML;
import edu.dlnu.xeon.domain.QueryResult;
import edu.dlnu.xeon.service.impl.SearchServiceImpl;

public class PagingSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SearchServiceImpl searchService=new SearchServiceImpl();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		QueryResult<HTML> queryResult=null;
		if(request.getParameter("pageNow")!=null){
			int pageNow=Integer.parseInt(request.getParameter("pageNow"));
			queryResult=new QueryResult<HTML>(pageNow);
		}else{
			
			queryResult=new QueryResult<HTML>();
		}
		String queryString=new String(request.getParameter("queryString").getBytes("iso-8859-1"),"utf-8");
		queryResult=searchService.pagingSearch(queryString,queryResult);
	
		request.setAttribute("queryResult", queryResult);
		request.getRequestDispatcher("/jsp/searchResult.jsp").forward(request, response);;
		return;
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}
	
}
