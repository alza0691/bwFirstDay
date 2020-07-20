<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <script type='text/javascript' src='http://code.jquery.com/jquery-3.3.1.js'></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 수정</title>
<style>
	section{
		text-align: center;
	}
    .container{
        padding: 15px;
        margin: auto;
        width: 600px;
    }
    table, input{
    	width: 100%;
    }
    table, tr,td{
    	border: 1px solid black;
    }
    .button{
    	float:right;
    }
    
    .right{
    	text-align: right;
    }
</style>
<body>
	<section>
		<div class="container">
		<h1>게시판 수정</h1>
			<form action="/bw/board/boardUpdate.do" method="post" id="boardUpdate">
				<table>
					<tr>
						<td class="right" style="width:85px;">날짜</td>
						<td>${boardVo.boardDate }</td>
	                </tr>
					<tr>
						<td class="right">글쓴이</td>
						<td>${boardVo.boardWriter2 }</td>
					</tr>
					<tr>
						<td class="right">제목</td>
						<td><input type="text" name="boardTitle" id="boardTitle" required value="${boardVo.boardTitle2 }"></td>
					</tr>
					<tr>
						<td class="right">내용</td>
						<td>
							<textarea name="boardContent" name="boardContent" id="boardContent" cols="30" rows="20"  style="width:100%; resize: none;"
							placeholder="내용을 입력하세요" required>${boardVo.boardContent }</textarea>
						</td>
					</tr>
				</table>
				<input type="hidden" id="boardNo" name="boardNo" value="${boardVo.boardNo }">
			</form>
			<button type="button" id="modifyBtn" class="button" style="margin-top: 10px; margin-left: 10px;" onclick="buttonClick();">수정</button>
			<button type="button" class="button return" style="width: 70px; margin-top: 10px; margin-left: 10px;">뒤로가기</button>
		</div>
	</section>	
</body>
<script>

	$(function(){
			$(".return").click(function(){
				location.href="/bw/board/contentPage.do?boardNo="+${boardVo.boardNo};
			});
		});
	function buttonClick(){
		var titleCheck= /^[0-9a-zA-Zㄱ-ㅎ가-힣~!@#$%^&*()<>_+`=&lt;&gt;][ 1-9A-Za-zㄱ-ㅎ가-힣~!@#$%^&*/()<>_+`=&lt;&gt;]{1,30}$/;
		var contentCheck = /[ 0-9A-Za-zㄱ-ㅎ가-힣~!@#$%^&*()<>_+`=]{2,1000}$/;
		
		if(!titleCheck.test($('#boardTitle').val())){
			alert("제목을 규칙에 맞게 입력해 주세요. 첫글자는 공백이 안되고 2글자 이상 30글자 이하만 가능합니다.");
			$("#boardTitle").focus();
			return false;
		}

		if(!contentCheck.test($('#boardContent').val())){
			alert("내용을 규칙에 맞게 입력해 주세요. 2글자 이상 1000자 이하만 가능합니다.");
			$("#boardContent").focus();
			return false;
		}
		$("#boardUpdate").submit();
	};
</script>
</html>

    		           		        