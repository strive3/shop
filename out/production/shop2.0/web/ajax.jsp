<%@page contentType="text/html; charset=gbk" pageEncoding="gbk"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<title></title>
		<script type="text/javascript" src="js/prototype.js"></script>
		<script type="text/javascript">
			function test(){
				//创建 XMLHttpRequest
				var xhr = new XMLHttpRequest();
				//alert(xhr);
				var name = document.getElementById("name").value;

				var pwd = document.getElementById("pwd").value;
				var all = name + pwd;
				//开启
				xhr.open("POST","register.ajax",true);
				xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
				//alert("ssss");
				xhr.onreadystatechange = function(){
					if(xhr.readyState == 4 || xhr.status ==200){
						document.getElementById("msg").innerHTML = xhr.responseText;
					}
				};
				//post的提交信息
				xhr.send("name=" + name);
			}
			
			
			function getUser(){
				var xhr = new XMLHttpRequest();
				
				xhr.open("get","list.ajax",true);
				xhr.onreadystatechange = function(){
					if(xhr.readyState == 4 || xhr.status ==200){
						var data = xhr.requestText();
						var users = data.evalJSON();
						for(i = 0;i < users.length;i++){
							alert("id= " + users[i].id+",username=" + user[i].name);
						}
					}
				}
				
				
			}
		</script>
	</head>
	
	<body>
		用户名注册<input id="name" type="text" name="name" onblur="test()"/><span id="msg"></span>
		<input id="pwd" type="text" name="name" />
	</body>


</html>