<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Password.jsp</title>
<script>
	function isCheck(){
		var value=document.getElementById('password').value;
		opener.document.getElementById('frm').password.value=value;			
		opener.document.getElementById('frm').mode.value='${param.mode}';
		opener.isCorrect();
		self.close();
		
		
	}
</script>
</head>
<body>

	<input type="text" name="password" id="password"/>
	<input type="button" value="확인" onclick="isCheck();"/>
</body>
</html>
