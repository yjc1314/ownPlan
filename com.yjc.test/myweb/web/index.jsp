<%--
  Created by IntelliJ IDEA.
  User: yjc
  Date: 2020/4/7
  Time: 15:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>我的主页</title>
  </head>
  <body>
  hello world， 我的主页
  <div>
<%--    action 填写的是网址上相对于这个页面的路径--%>
    <form action="demo" method="post">
      <input type="text" name="name">姓名
      <br>
      <input type="password" name="pas">密码
      <button type="submit">
        提交

      </button>


      <hr>

    </form>

  </div>
  <br>
  </body>
</html>
