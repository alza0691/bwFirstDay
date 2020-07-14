<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 수정</title>
</head>
<body>
	<section>
		<div class="container">
		<h1>게시판 수정</h1>
			<form action="/bw/board/boardUpdate.do">
				<table>
					<tr>
						<td>날짜</td>
						<td>${boardVo.boardDate }</td>
	                </tr>
					<tr>
						<td>글쓴이</td>
						<td>${boardVo.boardWriter }</td>
					</tr>
					<tr>
						<td>제목</td>
						<td><input type="text" name="boardTitle" required value="${boardVo.boardTitle }"></td>
					</tr>
					<tr>
						<td>내용</td>
						<td>
							<textarea name="boardContent" name="boardContent" cols="30" rows="7"
							placeholder="내용을 입력하세요" required>${boardVo.boardContent }</textarea>
						</td>
					</tr>
				</table>
				<input type="hidden" name="boardNo" value="${boardVo.boardNo }">
				<button type="submit">수정</button>
			</form>
		</div>
	</section>	
</body>
</html>