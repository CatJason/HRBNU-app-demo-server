package servlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

public class NoGoodServlet extends HttpServlet {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 4263306000464851879L;
	// JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/mysql";
 
    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "root";
    public NoGoodServlet(){
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
            String tmp2 = null;
            try{
                // 注册 JDBC 驱动
                Class.forName("com.mysql.jdbc.Driver");

                // 打开链接
                System.out.println("连接数据库...");
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
                try
                {
                	
                    int tmp = Integer.parseInt(getTemperature(count));
                    tmp--;	
                    PreparedStatement psql = conn.prepareStatement("UPDATE mxlake_news SET temperature = "+tmp+" WHERE newid=" +count);  //用preparedStatement预处理来执行sql语句
                    psql.execute();  //参数准备后执行语句
                    psql.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }catch (Exception e) {
                    e.printStackTrace();
                }finally{
                        System.out.println("数据库数据插入成功！"+"\n");
                        tmp2 = "sucess";
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
           return tmp2;
        }
                 

        
        
        public String getTemperature(String newid) {
            Connection conn = null;
            String tmp = null;
            Statement stmt = null;
            try{
                // 注册 JDBC 驱动
                Class.forName("com.mysql.jdbc.Driver");
            
                // 打开链接
                System.out.println("连接数据库...");
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
            
                // 执行查询
                System.out.println(" 实例化Statement对象...");
                String sql= "SELECT temperature FROM mxlake_news WHERE newid ="+newid+";";
                stmt = conn.createStatement();
                
                ResultSet rs = stmt.executeQuery(sql);
                // 展开结果集数据库
                rs.next();
            	 // 通过字段检索
	           	tmp = rs.getString("temperature");
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