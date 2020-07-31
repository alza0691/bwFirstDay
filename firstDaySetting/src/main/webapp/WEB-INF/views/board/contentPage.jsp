<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type='text/javascript' src='http://code.jquery.com/jquery-3.3.1.js'></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    	clear: both;
    	border: 1px solid #ccc;
      	border-radius: 5px;
       	overflow: hidden;
    }
        
    .commentList>li {
     	float: left;
     	color: black;
     	text-decoration: none;
    }  
    button{
	    float: right;
	    margin-left: 10px;
    }
    ul{
    	list-style: none;
    	border: 1px solid #ccc;
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
						<td style="width: 78%;">${b.boardDate }</td>
	                </tr>
					<tr>
						<td style="text-align: right;">글쓴이</td>
						<td>${b.boardWriter2 }</td>
					</tr>
					<tr>
						<td style="text-align: right;">제목</td>
						<td>${b.boardTitle2 }</td>
					</tr>
					<tr>
						<td style="text-align: right;">내용</td>
						<td style="height: 300px;">
							<textarea class="autosize" rows="20" cols="30" style="width:100%;" readonly>${b.boardContent2 }</textarea>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">비밀번호</td>
						<td><input type="password" id="boardPw" name="boardPw" style="width: 100%" required></td>
					</tr>
				</table>
				<br>
				<input type="hidden" id="boardContent2" value="${b.boardContent }">
				<input type="hidden" name="boardNo" id="boardNo" value="${b.boardNo }">
				<button type="button" class="update">수정</button>
				<button type="button" class="delete">삭제</button>
				<button type="button" class="return">목록으로</button>
				<button type="button" class="reply">답글달기</button>
				<br><br><br>
			</form>
		</div>
		
            <div class="comment-write">
                <form action="/bw/board/boardCommentInsert.do" method="post">
                    <!-- 작성자, 게시글 번호, 참조, 댓글 레벨 -->
                    <input type="hidden" name="boardRef" id="boardRef" value="${b.boardNo }">
                    <input type="hidden" name="boardCommentLevel" id="boardCommentLevel" value="1">
                    <input type="hidden" name="boardCommentRef" id="boardCommentRef" value="0">
                    <table class="table">
                        <tr>
                        	<td width="20%">
                        		<input type="text" name="boardCommentWriter" id="boardCommentWriter" placeholder="작성자를 입력해 주세요">
                        	</td>
                            <td width="70%">
                                <textarea class="form-control" style="width: 100%;"name="boardCommentContent" id="boardCommentContent" placeholder="댓글은 내 얼굴입니다."></textarea>
                            </td>
                            <td width="10%">
                            	<input type="password" placeholder="비밀번호를 입려하세요">
                                <button type="button" class="btn btn-primary" id="commentBtn">댓글 작성</button>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="test commentList" style="float:left;"></div>
            
		<div class="comment-wrapper">
            <c:forEach items="${commentList }" var="bc">
	                <ul class="commentList">
	                    <li style="width: 10%; text-align: center;"><span>${bc.boardCommentWriter }</span></li>
	                    <li style="width: 65%; margin-left: 50px;">
	                    	<span style="float: left;">${bc.boardCommentContent }</span>
	                    	<textarea class="form-control" name="boardCommentContent" style="display: none;">${bc.boardCommentContent }</textarea></li>
	                    <li style="width: 10%"><span>${bc.boardCommentDate }</span></li>
	                    <li style="width: 15%; text-align: center">
	                                <a href="javascript:void(0)" onclick="modifyComment(this, '${bc.boardCommentNo}', '${bc.boardRef }')">수정</a>
	                                <a href="javascript:void(0)" onclick="deleteComment('${bc.boardCommentNo }', '${bc.boardRef }')">삭제</a>
	                    </li>
	                </ul>
            </c:forEach>
        </div>

	</section>	
</body>

<script>
	$(document).ready(function() {
		var autosize = $(".autosize"); 
		var size = autosize.prop('scrollHeight');
		autosize.css("height",size);
	});
	var boardPw = $("#boardPw");
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
                        location.href="/bw/board/boardUpdateFrm.do?boardNo="+${b.boardNo};
                    } else {
                        alert('비밀번호를 확인해 주세요');
                        $("#boardPw").val("").focus();
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
		
		$("#commentBtn").click(function(){
			$.ajax({
				url: "/bw/board/boardCommentInsert.do",
				data: 
					{
						boardCommentWriter:$("#boardCommentWriter").val()
						, boardRef:$("#boardRef").val()
						, boardCommentLevel:$("#boardCommentLevel").val()
						, boardCommentRef:$("#boardCommentRef").val()
						, boardCommentContent:$("#boardCommentContent").val()
					},
				type: "get",
				success: function(data){
					if(data=='1'){
						$("#boardCommentContent").val("");
						
						//댓글 불러오기
						$.ajax({
							url: "/bw/board/selectOneComment.do",
							data:
								{
									boardNo: $("#boardNo").val()
								},
							type: "get",
							success: function(data){
									console.log("코멘트 불러오기 성공");
									var html = "";
										html += "<ul><li>" + data.boardCommentDate + "</li>";
										html += "<li>" + data.boardCommentWriter + "</li>";
										html += "<ul><li>" + data.boardCommentContent + "</li></ul>";
									$(".test").append(html);
							},
							error: function(){
								console.log("댓글 불러오기 ajax통신 실패"); 
							}
						}); //댓글 불러오기 끝
						
						
					} else{
						console.log("코멘트달기 실패");
					}
				},//success 끝
				error: function(){
					alert("관리자에게 문의해 주세요");
				}
			});//아작스 끝
		});
			
		$(".reply").click(function(){
			location.href="/bw/board/replyWriteFrm.do?boardNo="+${b.boardNo };
		});
	});

</script>
</html>