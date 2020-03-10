package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.SmartUpload;

public class DownloadServlet extends HttpServlet{

	private static final long serialVersionUID = 5993027137128485725L;

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String fileName = request.getParameter("fileName");
		//String contentType = fileName.substring(fileName.indexOf(".")+1);
		SmartUpload su = new SmartUpload();
		su.initialize(this.getServletConfig(), request, response);
		su.setContentDisposition(null);//设置下载表头
		try {
			su.downloadFile("upload/"+fileName);
		} catch (Exception e) {	
			request.setAttribute("result", "下载失败:文件不存在");
			request.getRequestDispatcher("/Download").forward(request, response);			
		}
	}
	
	

	
	
}