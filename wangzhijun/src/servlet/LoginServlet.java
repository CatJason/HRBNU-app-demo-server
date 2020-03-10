package servlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

public class LoginServlet extends HttpServlet {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 8838520085923306296L;
	// JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/mysql";
 
    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "root";
    
    public LoginServlet(){
        super();
    }
     
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String useraccount = request.getParameter("useraccount");
        String userpassword = request.getParameter("userpassword");
        System.out.println("账号："+useraccount);
        System.out.println("密码："+userpassword);
        String result = login(useraccount,userpassword);
        System.out.println("结果："+result);
        response.getOutputStream().write(result.getBytes("utf-8"));
    }
 
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
         
    }
 
        public String login(String useraccount,String userpassword) {
            Connection conn = null;
            Statement stmt = null;
            String password = null;
            String name = null;
            String avatar = null;
            String tmp = null;
            try{
                // 注册 JDBC 驱动
                Class.forName("com.mysql.jdbc.Driver");
            
                // 打开链接
                System.out.println("连接数据库...");
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
            
                // 执行查询
                System.out.println(" 实例化Statement对象...");
                String sql;
                sql = "SELECT account, password, name, avatar FROM mxlake_user WHERE account = "+useraccount;
                stmt = conn.createStatement();    
                ResultSet rs = stmt.executeQuery(sql);
                // 展开结果集数据库
                rs.next();
	            // 通过字段检索
	            password = rs.getString("password");
	            name = rs.getString("name");
	            avatar = rs.getString("avatar");
	            tmp = "[{\"name\":\""+name+"\",\"avatar\":\""+avatar+"\"}]";
	            rs.close();
                stmt.close();
                conn.close();
            }catch(SQLException se){
                // 处理 JDBC 错误
                se.printStackTrace();
            }catch(Exception e){
                // 处理 Class.forName 错误
                e.printStackTrace();
            }finally{
                // 关闭资源
                try{
                    if(stmt!=null) stmt.close();
                }catch(SQLException se2){
                }// 什么都不做
                try{
                    if(conn!=null) conn.close();
                }catch(SQLException se){
                    se.printStackTrace();
                }
  
            }
        if(password.equals(userpassword)) {
        		return tmp;
            }else {
            	return "false";
            }
  
        }
}