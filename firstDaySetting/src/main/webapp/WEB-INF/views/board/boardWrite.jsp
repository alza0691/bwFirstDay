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
    	magin-left: 10px;
    }
    
    .right{
    	text-align: right;
    }
    
</style>
<body>
	<section>
		<div class="container">
		<h1>글쓰기</h1>
			<form class="boardWrite" action="/bw/board/boardWrite.do" method="post">
				<table>
					<tr>
						<td width="15%" class="right">날짜</td>
						<td width="70%">
							<span id="dateShow"></span>	
						</td>
	                </tr>
					<tr>
						<td class="right">글쓴이</td>
						<td><input type="text" name="boardWriter" id="boardWriter" required></td>
					</tr>
					<tr>
						<td class="right">제목</td>
						<td><input type="text" name="boardTitle" id="boardTitle" required></td>
					</tr>
					<tr>
						<td class="right">내용</td>
						<td>
							<textarea name="boardContent" id="boardContent" cols="30" rows="20" style="width:100%; resize: none;"
							placeholder="내용을 입력하세요" required></textarea>
						</td>
					</tr>
					<tr>
						<td class="right">비밀번호</td>
						<td><input type="password" name="boardPw" required></td>
					</tr>
				</table>
			</form>
			<button type="button" id="submit" class="button" style="width: 70px; margin-left: 10px;" onclick="buttonClick();">제출</button>
			<button type="button" class="button return" style="width: 70px; margin-left: 10px;">뒤로가기</button>
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
		
		$(function(){
			$(".return").click(function(){
				location.href="/bw/board/boardList.do";
			});
		});
			
			//trim = 공백제거
			//onclick으로 메소드 만들어서 리턴줘서 다시 돌아가서 submit 안되게
			//onclick으로 하면 trim이 필요없음

		function buttonClick(){
			var titleCheck= /^[0-9a-zA-Zㄱ-ㅎ가-힣~!@#$%^&*/()<>_+`=&lt;&gt;][ 1-9A-Za-zㄱ-ㅎ가-힣~!@#$%^&*/()<>_+`=&lt;&gt;]{1,30}$/;
			var contentCheck = /[ 0-9A-Za-zㄱ-ㅎ가-힣~!@#$%^&*()<>_+`=]{2,1000}$/;
			var writerCheck = /(^[0-9a-zA-Zㄱ-ㅎ가-힣][1-9a-zA-Zㄱ-ㅎ가-힣]{1,4})$/;
			
			if(!writerCheck.test($('#boardWriter').val())){
				alert("글쓴이를 규칙에 맞게 입력해 주세요. 첫글자는 공백이 안되고 1글자 이상 5글자 이하로만 가능 합니다.");
				$("#boardWriter").focus();
				return false;
			}
			
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
			$(".boardWrite").submit();
		};

			
	</script>
</html>