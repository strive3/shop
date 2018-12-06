<%@page contentType="text/html; charset=gbk" pageEncoding="gbk" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="gbk"/>

    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1"/>
    <meta name="description" content="description of your site"/>
    <meta name="author" content="author of the site"/>
    <title>电商平台后台首页</title>
    <link rel="stylesheet" href="css/bootstrap.css"/>
    <link rel="stylesheet" href="css/bootstrap-responsive.css"/>
    <link rel="stylesheet" href="css/styles.css"/>
    <link rel="stylesheet" href="css/toastr.css"/>
    <link rel="stylesheet" href="css/fullcalendar.css"/>
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.js"></script>
    <script src="js/jquery.knob.js"></script>
    <script src="http://d3js.org/d3.v3.min.js"></script>
    <script src="js/jquery.sparkline.min.js"></script>
    <script src="js/toastr.js"></script>
    <script src="js/jquery.tablesorter.min.js"></script>
    <script src="js/jquery.peity.min.js"></script>
    <script src="js/fullcalendar.min.js"></script>
    <script src="js/gcal.js"></script>
    <script src="js/setup.js"></script>
</head>
<body>

<%@include file="../header.jsp" %>
<div class="page">
    <div class="page-container">
        <div class="container">
            <div class="row">
                <div class="span12">
                    <a href="#newUserModal" data-toggle="modal" class="btn pull-right">添加用户</a>
                    <h4 class="header">用户列表</h4>
                    <table class="table table-striped sortable">
                        <thead>
                        <tr>
                            <th>用户ID</th>
                            <th>用户名</th>
                            <th>密码</th>
                            <th>电话</th>
                            <th>送货地址</th>
                            <th>注册日期</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="u" items="${paging.objects }">
                            <tr>
                                <td>${u.id }</td>
                                <td>${u.username }</td>
                                <td>${u.password }</td>
                                <td>${u.phone }</td>
                                <td>${u.addr }<span class="label label-success">已发货</span></td>
                                <td>${u.rDate }</td>
                                <td>
                                    <div class="btn-group">
                                        <button class="btn">操作</button>
                                        <button data-toggle="dropdown" class="btn dropdown-toggle">
                                            <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu">
                                            <li><a
                                                    href="load.user?id=${u.id }">修改</a> <a href="#"
                                                                                           onclick="del(${u.id })">删除</a> <%-- <a href="delete.user?id=${u.id }">删除</a> --%>
                                                <script type="text/javascript">
                                                    function del(id) {
                                                        if (confirm("确定要删除吗")) {
                                                            window.location.href = "delete.user?id=" + id;
                                                        }
                                                    }
                                                </script>
                                            </li>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="pagination pagination-centered">
                        <form action="userlist.user" method="post">
                            <ul>
                                <li class="active"><a>第${paging.pageNumber }页</a></li>
                                <li><a href="userlist.user?pageNumber=1">首页</a></li>
                                <li><a href="userlist.user?pageNumber=${paging.pageNumber - 1}">上一页</a></li>
                                <li><a href="userlist.user?pageNumber=${paging.pageNumber + 1}">下一页</a></li>
                                <li><a href="userlist.user?pageNumber=${paging.pages }">尾页</a></li>
                                <li class="active"><a>共${paging.pages }页</a></li>
                                <li><span
                                        style="display:flex;height: 20px;line-height: 20px;border-color: transparent;">到第<input
                                        style="align-items: center;height: 10px;width: 23px" type="text"
                                        name="pageNumber" oninput="value=value.replace(/[^\d]/g,'')">页<input
                                        style="align-items: center;height: 20px;line-height: 16px;" type="submit"
                                        value="确定"></span></li>
                            </ul>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div id="newUserModal" class="modal hide fade">
            <div class="modal-header">
                <button type="button" data-dismiss="modal" aria-hidden="true"
                        class="close">&times;
                </button>
                <h3>新建用户</h3>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="add.user" method="post"/>
                <div class="control-group">
                    <label for="inputEmail" class="control-label">用户名</label>
                    <div class="controls">
                        <input id="inputEmail" type="text" name="username"
                               placeholder="请输入用户名"/>
                    </div>
                </div>
                <div class="control-group">
                    <label for="inputCurrentPassword" class="control-label">密码
                    </label>
                    <div class="controls">
                        <input id="inputCurrentPassword" type="password" name="pwd"
                               placeholder="请输入密码"/>
                    </div>
                </div>
                <div class="control-group">
                    <label for="inputCurrentPassword" class="control-label">密码
                    </label>
                    <div class="controls">
                        <input id="inputCurrentPassword" type="password" name="repwd"
                               placeholder="请重新输入密码"/>
                        <%--<span style="color: red;">${error_msg }</span>--%>
                    </div>
                </div>
                <div class="control-group">
                    <label for="inputCurrentPassword" class="control-label">电话
                    </label>
                    <div class="controls">
                        <input id="inputCurrentPassword" type="text" name="phone"
                               placeholder="请输入电话"/>
                    </div>
                </div>
                <div class="control-group">
                    <label for="inputCurrentPassword" class="control-label">送货地址
                    </label>
                    <div class="controls">
                        <textarea rows="9" cols="40" name="addr"></textarea>
                    </div>
                </div>

                <div class="modal-footer">
                    <a href="#" data-dismiss="modal" class="btn">关闭</a><input
                        type="submit" class="btn btn-primary" value="添加用户"/>
                </div>
            </div>
            </form>
        </div>
    </div>
</div>
<!-- footer -->
<%@include file="../footer.jsp" %>
</body>
<script src="js/d3-setup.js"></script>
<script>
    protocol = window.location.protocol === 'http:' ? 'ws://' : 'wss://';
    address = protocol + window.location.host + window.location.pathname
        + '/ws';
    socket = new WebSocket(address);
    socket.onmessage = function (msg) {
        msg.data == 'reload' && window.location.reload()
    }
</script>
</html>