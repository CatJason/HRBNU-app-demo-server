package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2670710738780987466L;


	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String filepath = request.getSession().getServletContext().getRealPath("/")+"file";
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/file";		
		File file = new File(filepath);
		File[] fileList = file.listFiles();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>文件下载</TITLE></HEAD>");
		out.println("  <BODY>");
		for(File f : fileList){
			//out.print("<a href=\"download?fileName="+f.getName()+"\">");
			out.print("<a href=\""+basePath+f.getName()+"\">");
			out.print(f.getName());
			out.println("</a><br>");
		}		
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
	
}