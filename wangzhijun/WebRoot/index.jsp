<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>" />
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>test</title>
    <link rel="stylesheet" href="">
</head>
<body>
    文件上传
    <hr>
    <form action="upload" method="post" 
        enctype="multipart/form-data">
        你选择一个文件进行上传：
        <input type="file" name="myfile">
        <input type="submit" value="上传">
    </form>    
    ${result}
</body>
</html>