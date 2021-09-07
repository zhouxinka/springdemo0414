<%--
  Created by IntelliJ IDEA.
  User: zhou.peng
  Date: 2021/4/14
  Time: 10:38
  To change this template use File | Settings | File Templates.
--%>
<%--引用JSTL标签--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/taglib/taglib.jsp"%>
<html>
<head>
    <title>Index</title>
    <link href="http://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.mim.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../css/test.css"/>
    <script src="../js/jquery_3.2.1.js"></script>
</head>
<script type="text/javascript">
    $(function () {
        $.ajax({
            url:"${ctx}/findAllUser",
            dataType:"JSON",
            success:function (data) {
                console.log(data);
                $.each(data,function (i,user) {
                    $("#userInfo").append("<tr><td>"+user.id+"</td><td>"
                        +user.name+"</td><td>"
                        +user.age+"</td><td>"
                        +user.gender+"</td><td>"
                        +"<a href=\"${ctx}/delete?id="+user.id
                        +"\" style=\"text-decoration:none\" >删除</a>"
                        +"<a href=\"${ctx}/details?id="+user.id
                        +"\" style=\"text-decoration:none\">编辑</a></td></tr>");
                });
            }
        })
    })
</script>
<body>
<p align="center">User Information</p>
<sys:messages content="${message}"/>
<table>
    <thead>
        <tr>
            <td>id</td>
            <td>NAME</td>
            <td>AGE</td>
            <td>GENDER</td>
            <td>OPERTION</td>
        </tr>
    </thead>
    <tbody id="userInfo">
    
    </tbody>
</table>
<a href="${ctx}/addUser" style="text-decoration:none">新增用户</a>
<div class="col-md-12 text-center" style="text-align: center">
    <input id="btnBack" class="btn btn-default" type="button" value="返回" onclick="javascript:history.go(-1)">
</div>
</body>
</html>
