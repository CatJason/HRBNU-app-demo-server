package servlet;

import java.io.File;
import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.SmartUpload;

public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = -8200412753505066169L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		//设置文件保存路径		
		String filePath = request.getSession().getServletContext().getRealPath("/")+"upload";
		System.out.print(filePath);
		File file = new File(filePath);
		if(!file.exists()){
			System.out.println("指定目录不存在，重新创建");
			file.mkdirs();
		}
		String result="上传成功";
		SmartUpload su = new SmartUpload();
		//初始化对象
		su.initialize(this.getServletConfig(), request, response);
		//设置上传文件大小
		su.setMaxFileSize(1024*1024*100);
		//设置允许上传的文件类型
		su.setAllowedFilesList("txt,zip,rar,7z,html,jpg,jsp,js,html,css,png,mp4,mp3,json");		
		try {
			//设置不允许上传的文件
			su.setDeniedFilesList("docx");
			su.upload();//上传文件
			//String pid = su.getRequest().getParameter("pid");
			//String ext =su.getFiles().getFile(0).getFileExt();//获取文件扩展名
			String fileName = su.getFiles().getFile(0).getFileName();//获取文件名
			System.out.println("上传的文件名：" + fileName);
			/*String name=request.getRemoteAddr();//获取当前ip
			for(int i=0;i<4;i++){
				name = name+(int)(Math.random()*10);//文件命名
			}*/			
			su.getFiles().getFile(0).saveAs(filePath+java.io.File.separator+fileName);
		} catch (Exception e) {
			result="上传失败";
			if(e.getMessage().indexOf("1015")!=-1){
				result="上传失败：类型不正确";
			}else if(e.getMessage().indexOf("1010")!=-1){
				result="上传失败：类型不正确";
			}else if(e.getMessage().indexOf("1105")!=-1){
				result="上传失败：文件过大";
			}
			
		}
		request.setAttribute("result", result);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

}