<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type='text/javascript' src='http://code.jquery.com/jquery-3.3.1.js'></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 내용</title>
</head>
<style>
	table, tr, td{
		border: 1px solid black;
	}
	.pagingBlock>ul>li{
		display: block;
		text-decoration: none;
	}
    .container{
        padding: 15px;
        margin: auto;
        width: 800px;
    }
    section{
        position: absolute;
        margin: auto;
        width: 100%;
    }
    table{
        width: 100%;
        margin: auto;
    }
    h1, .textCenter{
    	text-align: center;
    }
    span{
    	float:right;
    }
    .table-wrapper, .comment-write, .comment-wrapper {
        width: 600px;
        margin: 0 auto;
    }
    
    .comment-write td {
        text-align: center;
    }
        
    .container textarea {
        resize: none;
        border: 0;
        outline: none;  
    }
        
    .commentList {
    	width: 100%;
    	clear: both;
    	border: 1px solid #ccc;
      	border-radius: 5px;
       	overflow: hidden;
    }
        
    .commentList>li {
     	float: left;
     	color: black;
    }  
    button{
	    float: right;
	    margin-left: 10px;
    }

</style>
<body>
	<section>
		<div class="container">
		<h1>콘텐츠</h1>
			<form method="post">
				<table style="word-break:break-all">
					<tr>
						<td style="width: 12%; text-align: right;">날짜</td>
						<td style="width: 78%;">${oneContent.boardDate }</td>
	                </tr>
					<tr>
						<td style="text-align: right;">글쓴이</td>
						<td>${oneContent.boardWriter2 }</td>
					</tr>
					<tr>
						<td style="text-align: right;">제목</td>
						<td>${oneContent.boardTitle2 }</td>
					</tr>
					<tr>
						<td style="text-align: right;">내용</td>
						<td style="height: 300px;">
							<textarea class="autosize" rows="20" cols="30" style="width:100%;" readonly>${oneContent.boardContent2 }</textarea>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">비밀번호</td>
						<td><input type="password" id="boardPw" name="boardPw" style="width: 100%" required></td>
					</tr>
				</table>
				<br>
				<input type="hidden" id="boardContent2" value="${oneContent.boardContent }">
				<input type="hidden" name="boardNo" id="boardNo" value="${oneContent.boardNo }">
				<button type="button" class="update">수정</button>
				<button type="button" class="delete">삭제</button>
				<button type="button" class="return">목록으로</button>
				<br><br><br>
			</form>
		</div>
			

	</section>	
</body>
<script>
	$(document).ready(function() {
		var autosize = $(".autosize"); 
		var size = autosize.prop('scrollHeight');
		autosize.css("height",size);
	});

	$(function(){
		$(".update").click(function(){
			$.ajax({
				url: "/bw/board/pwCheck.do",
				data:{
					boardPw:$("#boardPw").val(),
					boardNo:$("#boardNo").val()
					},
				type: "get",
				success: function(data){
					if (data == '1') {
                        location.href="/bw/board/boardUpdateFrm.do?boardNo="+${oneContent.boardNo};
                    } else {
                        alert('비밀번호를 확인해 주세요');
                    }
				},
				error: function(){
					alert("관리자에게 문의해주세요")
				}
			});
		});
		$(".return").click(function(){
			location.href="/bw/board/boardList.do";
		});
		
		
		$(".delete").click(function(){
			$.ajax({
				url: "/bw/board/pwCheck.do",
				data:{
					boardPw:$("#boardPw").val(),
					boardNo:$("#boardNo").val()
					},
				type: "get",
				success: function(data){
					if (data == '1') {
						if(confirm("삭제하시겠습니까?")){
						$.ajax({
		                    url: "/bw/board/boardDelete.do",
		                    data: {
		                    	boardNo: $("#boardNo").val(),
		                    	boardPw: $("#boardPw").val()
		                    },
		                    type: "get",
		                    success: function(data) {
		                        if (data == '1') {
		            				location.href="/bw/board/boardList.do";
		                        }
				         },
				      error : function(){
				    	  alert("관리자에게 문의해 주세요");
				      }
				    }) 		
						}
					} else{
						alert("비밀번호를 확인해 주세요");
					}
				},
				error: function(){
					alert("관리자에게 문의해주세요")
				}
			});
		})//delete click end
			
			
	});

</script>
</html>