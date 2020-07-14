<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type='text/javascript' src='http://code.jquery.com/jquery-3.3.1.js'></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
</head>
<style>
</style>
<body>
	<section>
		<div class="container">
		<h1>글쓰기</h1>
			<form action="/boardWrite.do">
				<table>
					<tr>
						<td>날짜</td>
						<td>
							<span id="dateShow"></span>	
						</td>
	                </tr>
					<tr>
						<td>글쓴이</td>
						<td><input type="text" name="boardWriter" required></td>
					</tr>
					<tr>
						<td>제목</td>
						<td><input type="text" name="boardTitle" required></td>
					</tr>
					<tr>
						<td>내용</td>
						<td>
							<textarea name="boardContent" name="boardContent" cols="30" rows="7"
							placeholder="내용을 입력하세요" required></textarea>
						</td>
					</tr>
				</table>
				<input type="submit" class="button">
			</form>
		</div>
	</section>	
</body>
	<script>
		var today = new Date();
		var dd = today.getDate();
		var mm = today.getMonth() + 1;
		var yy = today.getFullYear() - 2000;
		if (dd < 10) {
		  dd = '0' + dd;
		} 
		if (mm < 10) {
		  mm = '0' + mm;
		} 
		var today = yy+'/'+mm+'/'+dd;
		document.getElementById ('dateShow').innerHTML = today;		
	</script>
</html>