<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <script type='text/javascript' src='http://code.jquery.com/jquery-3.3.1.js'></script>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>엑셀 업로드</title>
</head>
<body>
	<form name="excelUp" id="excelUp" enctype="multipart/form-data" method="post" action="bw/board/excelUp.do">
		<input type="file" id="excelFile" name="excelFile" value="엑셀 업로드">
	</form>
</body>
<script>
	$("#excelUp").change(function(){
	    var form = $("#excelUp")[0];
	
	    var data = new FormData(form);
	    $.ajax({
	       enctype:"multipart/form-data",
	       method:"post",
	       url: 'bw/board/excelUp.do',
	       processData: false,   
	       contentType: false,
	       cache: false,
	       data: data,
	       success: function(result){  
	           alert("업로드 성공!!");
	       }
	    });
	});
</script>
</html>