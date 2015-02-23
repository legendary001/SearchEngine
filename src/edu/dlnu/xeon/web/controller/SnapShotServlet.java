package edu.dlnu.xeon.web.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.dlnu.xeon.service.SearchService;
import edu.dlnu.xeon.service.impl.SearchServiceImpl;

public class SnapShotServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SearchService searchService=new SearchServiceImpl();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title=new String(request.getParameter("title").getBytes("iso-8859-1"),"utf-8");
		byte[] content=searchService.getPageShot(title);
		if(content==null){
			request.getRequestDispatcher("/WEB-INF/ErrorPage/resourceNotExist.jsp").forward(request, response);;
			return;
		}
		InputStream in=new ByteArrayInputStream(content);
		response.setContentType("text/html;charset=utf-8");
		OutputStream out=response.getOutputStream();
		byte[] b=new byte[1024];
		int len=0;
		while((len=in.read(b))!=-1){
			out.write(b,0, len);
		}
		in.close();
		out.close();
	}

	
}
