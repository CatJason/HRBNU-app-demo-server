package servlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

public class HotServlet extends HttpServlet {
 

	/**
	 * 
	 */
	private static final long serialVersionUID = -641515506771459710L;
	// JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/mysql";
 
    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "root";
    public HotServlet(){
    	super();
    }
     
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String count = request.getParameter("count");
        String result = null;
        result = refresh(count);
        System.out.print(result);
        response.getOutputStream().write(result.getBytes("utf-8"));
    }
 
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
         
    }
 
        public String refresh(String count) {
            Connection conn = null;
            String newid= null;
            String time = null;
            String newtitle= null;
            String account = null;
            String tag1 = null;
            String tag2 = null;
            String tag3 = null;
            String tmp = null;
            String userinfo = null;
            Statement stmt = null;
            try{
                // 注册 JDBC 驱动
                Class.forName("com.mysql.jdbc.Driver");
            
                // 打开链接
                System.out.println("连接数据库...");
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
            
                // 执行查询
                System.out.println(" 实例化Statement对象...");
                String sql= "SELECT newid, time, newtitle, account, tag1, tag2, tag3 FROM mxlake_news ORDER BY temperature DESC limit "+count+",10;";
                stmt = conn.createStatement();
                
                ResultSet rs = stmt.executeQuery(sql);
                // 展开结果集数据库
                tmp = "[";
                while(rs.next()){
                	 // 通过字段检索
                	newid = rs.getString("newid");
                	time = rs.getString("time");
    	           	newtitle = rs.getString("newtitle");
    	            account = rs.getString("account");
    	            tag1 = rs.getString("tag1");
    	            tag2 = rs.getString("tag2");
    	            tag3 = rs.getString("tag3");
    	            userinfo = getUserInfo(account);
    	            tmp = tmp+"{\"newid\":\""+newid+"\",\"time\":\""+time+"\",\"newtitle\":\""+newtitle+userinfo+"\",\"tag1\":\""+tag1+"\",\"tag2\":\""+tag2+"\",\"tag3\":\""+tag3+"\"},";
                }
                tmp = tmp+ "]";
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
    return tmp;	
        }

        
        
        public String getUserInfo(String account) {
            Connection conn = null;
            String tmp = null;
            String name = null;
            String avatar =null;
            Statement stmt = null;
            try{
                // 注册 JDBC 驱动
                Class.forName("com.mysql.jdbc.Driver");
            
                // 打开链接
                System.out.println("连接数据库...");
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
            
                // 执行查询
                System.out.println(" 实例化Statement对象...");
                String sql= "SELECT name,avatar FROM mxlake_user WHERE account ="+account+";";
                stmt = conn.createStatement();
                
                ResultSet rs = stmt.executeQuery(sql);
                // 展开结果集数据库
                rs.next();
            	 // 通过字段检索
	           	name = rs.getString("name");
	            avatar = rs.getString("avatar");
	            tmp = "\",\"name\":\""+name+"\",\"avatar\":\""+avatar;
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
    return tmp;	
        }


}