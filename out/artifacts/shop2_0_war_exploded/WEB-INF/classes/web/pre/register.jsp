<%@page contentType="text/html; charset=gbk" pageEncoding="gbk" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="gbk">
    <title>电商平台用户登录</title>

    <!--js-->
    <script src="js/jquery-1.8.2.min.js"></script>
    <script src="js/common.js"></script>
    <script src="js/jquery.easing.1.3.js"></script>
    <script src="js/ddsmoothmenu.js"></script>
    <script src="js/jquery.flexslider.js"></script>
    <script src="js/jquery.elastislide.js"></script>
    <script src="js/jquery.jcarousel.min.js"></script>
    <script src="js/jquery.accordion.js"></script>
    <script src="js/light_box.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $(".inline").colorbox({
                inline: true,
                width: "50%"
            });
        });
    </script>
    <!--end js-->

    <!-- Mobile Specific Metas ================================================== -->
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1">

    <!-- CSS -->

    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/orange.css">
    <link rel="stylesheet" href="css/skeleton.css">
    <link rel="stylesheet" href="css/layout.css">
    <link rel="stylesheet" href="css/ddsmoothmenu.css"/>
    <link rel="stylesheet" href="css/elastislide.css"/>
    <link rel="stylesheet" href="css/home_flexslider.css"/>

    <link rel="stylesheet" href="css/light_box.css"/>
    <script src="js/html5.js"></script>
    <script type="text/javascript" src="js/prototype.js"></script>
    <script>
        //给邮箱发送验证码的ajax
        function register() {
            var mail = document.getElementById("mail").value;
            //创建 XMLHttpRequest
            var xhr = new XMLHttpRequest();
            //开启
            xhr.open("POST", "code.pre", true);
            xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4 || xhr.status == 200) {
                    var msg = xhr.responseText;
                    document.getElementById("send_msg").innerHTML = msg;
                    if (msg == "验证码已发至邮箱,请注意查收") {
                        document.getElementById("send_msg").style.color = "green";
                    } else {
                        document.getElementById("send_msg").style.color = "red";
                    }
                }
            };
            //post的提交信息
            xhr.send("mail=" + mail);
        }

        //验证用户名能否使用的ajax
        function verify() {
            //创建 XMLHttpRequest
            var xhr = new XMLHttpRequest();
            var username = document.getElementById("username").value;

            //开启
            xhr.open("POST", "register.pre", true);
            xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4 || xhr.status == 200) {
                    var rgmsg = xhr.responseText;
                    if (rgmsg == "用户名可以使用") {
                        document.getElementById("rgmsg").style.color = "green";
                        document.getElementById("rgmsg").innerHTML = rgmsg;
                    } else {
                        document.getElementById("rgmsg").style.color = "red";
                        document.getElementById("rgmsg").innerHTML = rgmsg;
                    }

                }
            };
            //post的提交信息
            xhr.send("username=" + username);
        }

        //判断两次输入的密码是否一致的js
        function check() {
            var pwd = document.getElementById("psw").value;
            var repwd = document.getElementById("repsw").value;
            if (pwd != repwd) {
                document.getElementById("msg").style.color = "red";
                document.getElementById("msg").innerHTML = "两次输入的密码不一致";
            } else {
                document.getElementById("msg").style.color = "green";
                document.getElementById("msg").innerHTML = "可以注册";
            }

        }

    </script>

</head>
<body>
<div class="mainContainer sixteen container">
    <!--Header Block-->
    <div class="header-wrapper">
        <%@include file="header.jsp" %>

    </div>
    <!--Content Block-->
    <section class="content-wrapper">
        <div class="content-container container">
            <div class="main">
                <h1 class="page-title">登录|注册</h1>
                <div class="account-login">
                    <div class="new-users">
                        <div class="content">
                            <h2>新用户</h2>

                            <form action="register.pre" method="post">
                                <div class="registered-users">
                                    <div class="content">

                                        <ul class="form-list">
                                            <li><label class="required" for="email">用户名<em>*</em>
                                            </label>
                                                <div class="input-box">
                                                    <input type="text" title="用户名" id="username" onblur="verify()"
                                                           class="input-text required-entry validate-email" value=""
                                                           name="username"/>
                                                    <span style="position: absolute;top: 119px;left: 350px;"
                                                          id="rgmsg"></span>
                                                </div>
                                                <div class="clear"></div>
                                            </li>
                                            <li><label class="required" for="pass">密码<em>*</em></label>
                                                <div class="input-box">
                                                    <input type="password" title="密码" name="pwd" id="psw"
                                                           class="input-text required-entry validate-password"/>
                                                </div>
                                                <div class="clear"></div>
                                            </li>
                                            <li><label class="required" for="pass">确认密码<em>*</em></label>
                                                <div class="input-box">
                                                    <input type="password" title="密码" name="repwd" onblur="check()"
                                                           id="repsw"
                                                           class="input-text required-entry validate-password"/>
                                                    <span style="position: absolute;top: 240px;left: 350px"
                                                          id="msg"></span>
                                                </div>
                                                <div class="clear"></div>
                                            </li>
                                            <li><label class="required" for="pass">电话<em>*</em></label>
                                                <div class="input-box">
                                                    <input type="text" title="电话" name="phone"
                                                           class="input-text required-entry validate-password"/>
                                                </div>
                                                <div class="clear"></div>
                                            </li>

                                            <li><label class="required" for="mail">电子邮箱<em>*</em></label>
                                                <div class="input-box">
                                                    <input type="text" title="电子邮箱" name="mail" id="mail"
                                                           class="input-text required-entry validate-password"/>
                                                </div>
                                                <div class="clear"></div>
                                                <span>我们会往您的电子邮箱发送验证码，请完成注册</span>
                                                <input onclick="register()"
                                                       style="position: absolute;top: 356px;left: 262px" type="button"
                                                       class="orange-btn" value="发送邮件"/>
                                                <span id="send_msg"
                                                      style="position: absolute;left: 350px;top: 364px"></span>
                                            </li>

                                            <li><label class="required" for="pass">激活码<em>*</em></label>
                                                <div class="input-box">
                                                    <input type="text" title="电话" name="code"
                                                           class="input-text required-entry validate-password"/>
                                                    <span style="color: red">${err_msg}</span>
                                                </div>
                                                <div class="clear"></div>
                                            </li>
                                            </table>
                                            <li><label class="required" for="pass">地址<em>*</em></label>
                                                <div class="input-box">
                                                    <textarea class="input-text required-entry validate-password"
                                                              rows="9" cols="40" name="addr"></textarea>
                                                </div>
                                                <div class="clear"></div>
                                            </li>
                                        </ul>
                                        <p class="required">* 必填项</p>
                                    </div>
                                    <div class="buttons-set">
                                        <a class="f-left" href="#">忘记密码?</a> <input class="orange-btn"
                                                                                    type="submit" value="注册"/></a>
                                        <div class="clear"></div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <div class="clear"></div>
                </div>
            </div>
            <div class="clearfix"></div>
            <div class="news-letter-container">
                <div class="free-shipping-block">
                    <h1>ENJOY FREE SHIPPING</h1>
                    <p>on all orders as our holiday gift for you!</p>
                </div>
                <div class="news-letter-block">
                    <h2>SIGN UP FOR OUR NEWSLETTER</h2>
                    <input type="text" value="Enter email address" title=""/> <input
                        type="submit" value="Submit" title="Submit"/>
                </div>
            </div>
        </div>
    </section>
</div>
<!--页脚开始-->
<section class="footer-wrapper">
    <%@include file="footer.jsp" %>
</section>
<!--页脚结束-->
</body>
</html>