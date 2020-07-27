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
        width: 800px;
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
						<td class="right" style="width:13.5%;">날짜</td>
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
							<textarea name="boardContent" id="boardContent" class="autosize" cols="30" rows="20"  style="width:100%; min-height:300px; resize: none;"
							placeholder="1000자 이하로 작성하세요" required>${boardVo.boardContent2 }</textarea>
							<span id="counter"></span>/1000
						</td>
					</tr>
				</table>
				<input type="hidden" id="boardNo" name="boardNo" value="${boardVo.boardNo }">
			</form>
			<button type="button" id="modifyBtn" class="button" style="margin-top: 10px; margin-left: 10px;">수정</button>
			<button type="button" id="cancelBtn" class="button" style="margin-top: 10px; margin-left: 10px;">취소</button>
			<button type="button" class="button return" style="width: 70px; margin-top: 10px; margin-left: 10px;">목록으로</button>
			<br><br><br>
		</div>
	</section>	
</body>
<script>
	$(document).ready(function() {
		var autosize = $(".autosize"); 
		var size = autosize.prop('scrollHeight');
		autosize.css("height",size);
		
		var content = $("#boardContent").val();
		$("#boardContent").height(((content.split('\n').length + 1) * 1.5) + 'em');
	    $('#counter').html(content.length);	
	});

	$(function(){
			$(".return").click(function(){
				location.href="/bw/board/boardList.do";
			});
			$("#cancelBtn").click(function(){
				location.href="/bw/board/contentPage.do?boardNo="+${boardVo.boardNo};
			});
		});
	
		$(function(){			
			$("#boardWriter").on("change keyup mousedown", function(){
				if($("#boardWriter").val().length != ""){
					var checkCount = $(this).val().length;
					var boardWriter = $(this).val();					
					var remain = 10-checkCount;
					if(remain < 0){
						alert("10를 초과할 수 없습니다.");
						$("#boardWriter").val(boardWriter.slice(0,10));
						return false;
					} else if($.trim($("#boardWriter").val())==""){
						alert("빈칸을 입력할 수 없습니다.")
						$("#boardWriter").val(boardWriter.slice(0,0));
						return false;
					}
				}
			});
			
		$("#boardContent").on("change keyup down", function(){
			var checkCount = $(this).val().length;
			var boardContent = $(this).val();					
			var remain = 1000-checkCount;
			if(remain < 0){
				alert("1000글자를 초과할 수 없습니다.");
				$("#boardContent").val(boardContent.slice(0,1000));
				return false;
			} 
		});
	});
	
	$("#modifyBtn").click(function(){
		if ($("#boardTitle").val() == ""){
			$("#boardTitle").focus();
			alert("제목을 입력해 주세요.");
		} else if ($("#boardContent").val() ==""){
			$("#boardContent").focus();
			alert("콘텐츠를 입력해 주세요.");
		} else{
			$("#boardUpdate").submit();
		}
	});	
	
	$(function(){
		$("#boardContent").on("change keyup paste", function(e){
			var content = $(this).val();
			$(this).height(((content.split('\n').length + 1) * 1.5) + 'em');
		    $('#counter').html(content.length);	
		});
	});

</script>
</html>

    		           		        