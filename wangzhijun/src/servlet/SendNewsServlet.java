package servlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

public class SendNewsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7961318790434962168L;
	// JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/mysql";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "root";
    
    public SendNewsServlet(){
        super();
    }
     
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String content = request.getParameter("content");
        String account = request.getParameter("account");
        String title = request.getParameter("title");
        String tag1 = request.getParameter("tag1");
        String tag2 = request.getParameter("tag2");
        String tag3 = request.getParameter("tag3");
        String place = request.getParameter("place");
        String result = sendNews(content,account,title,tag1,tag2,tag3,place);
        System.out.println("结果："+result);
        response.getOutputStream().write(result.getBytes("utf-8"));
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
         
    }
    
        public String sendNews(String content,String account,String title,String tag1,String tag2,String tag3,String place) {
            Connection conn = null;
            String tmp = "false";
            try{
                // 注册 JDBC 驱动
                Class.forName("com.mysql.jdbc.Driver");           
                // 打开链接
                System.out.println("连接数据库...");
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
              try
                {
                    PreparedStatement psql = conn.prepareStatement("insert into mxlake_news (newtitle, place, account, content, tag1, tag2, tag3)"+"values(?,?,?,?,?,?,?)");  //用preparedStatement预处理来执行sql语句
                    psql.setString(1, title);  //给其6个参量分别“赋值”
                    psql.setString(2, place);
                    psql.setString(3, account);
                    psql.setString(4, content);
                    psql.setString(5, tag1);
                    psql.setString(6, tag1);
                    psql.setString(7, tag3);
                    psql.execute();  //参数准备后执行语句
                    psql.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }catch (Exception e) {
                    e.printStackTrace();
                }finally{
                        System.out.println("数据库数据插入成功！"+"\n");
                        tmp = "sucess";
                }
            }catch(SQLException se){
                // 处理 JDBC 错误
                se.printStackTrace();
            }catch(Exception e){
                // 处理 Class.forName 错误
                e.printStackTrace();
            }finally{
                // 关闭资源
            	try{
                    if(conn!=null) conn.close();
                }catch(SQLException se){
                    se.printStackTrace();
                }
            }
           return tmp;
        }
}