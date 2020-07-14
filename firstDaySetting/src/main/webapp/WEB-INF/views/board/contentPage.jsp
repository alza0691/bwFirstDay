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
        width: 600px;
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
        
    .table-wrapper>.table th {
        width: 20%;
    }
    
    .table-wrapper>.table td {
        width: 80%;
    }
    
    .comment-write td {
        text-align: center;
    }
        
    .container textarea {
        resize: none;
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
</style>
<body>
	<section>
		<div class="container">
		<h1>콘텐츠</h1>
			<table>
				<tr>
					<td>날짜</td>
					<td>${oneContent.boardDate }</td>
                </tr>
				<tr>
					<td>글쓴이</td>
					<td>${oneContent.boardWriter }</td>
				</tr>
				<tr>
					<td>제목</td>
					<td>${oneContent.boardTitle }</td>
				</tr>
				<tr>
					<td>내용</td>
					<td>${oneContent.boardContent }</td>
				</tr>
			</table>
			
			<br>
			<button type="button" class="update">수정</button>
			<button type="button" class="delete">삭제</button>
		</div>

	</section>	
</body>
<script>		        
	$(function(){
		$(".update").click(function(){
			location.href="/bw/board/boardUpdateFrm.do?boardNo="+${oneContent.boardNo};
		});
		$(".delete").click(function(){
			if(confirm("삭제하시겠습니까?")){
				location.href="/bw/board/boardDelete.do?boardNo="+${oneContent.boardNo};
			} 
		});
	});
</script>
</html>