<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/8
  Time: 2:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页11</title>
</head>
<body>
<form action="login" method="get">
    <table>
        <tr>
            <td>
                <input name="account" type="text" placeholder="账号"/>
            </td>
        </tr>
        <tr>
            <td>
                <input name="password" type="password" placeholder="密码"/>
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="提交">
            </td>
        </tr>
    </table>
</form>

<form action="register">
    <table>
        <tr>
            <td>
                <input name="nick" type="text" placeholder="昵称"/>
            </td>
        </tr>
        <tr>
            <td>
                <input name="addr" type="text" placeholder="地址"/>
            </td>
        </tr>
        <tr>
            <td>
                <input name="account" type="text" placeholder="账号"/>
            </td>
        </tr>
        <tr>
            <td>
                <input name="password" type="password" placeholder="密码"/>
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="提交">
            </td>
        </tr>
    </table>
</form>
<br>

<a href="findNanny">查寻</a><br>
<a href="findNannyById?id=115">查询一个</a><br>
<a href="getUser?account=abcd&password=123456">查询一个</a><br>

<a href="sendPic?id=114">相片</a><br>
<a href="sendPic?id=115">相片</a><br>
<a href="sendPic?id=120">相片</a><br>
<a href="sendPic?id=118">相片</a><br>
<a href="sendPic?id=122">相片</a><br>
<hr/>
<form action="saveOrder" method="post">
    <table>
        <tr>
            <td>
                <input name="beginTime" type="text" placeholder="2020-05-01"/>
            </td>
        </tr>
        <tr>
            <td>
                <input name="price" type="text" placeholder="3000.0"/>
            </td>
        </tr>
        <tr>
            <td>
                <input name="endTime" type="text" placeholder="2020-05-21"/>
            </td>
        </tr>
        <tr>
            <td>
                <input name="uid" type="text" placeholder="18"/>
            </td>
        </tr>
        <tr>
            <td>
                <input name="bid" type="text" placeholder="121"/>
            </td>
        </tr>
        <tr>
            <td>
                <input name="remark" type="text" placeholder="我带长大"/>
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="提交">
            </td>
        </tr>
    </table>
</form>
<br>


</body>
</html>
