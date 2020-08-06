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
    .commentPw, .commentPwButton{
    	display: none;
    	float: left;
    }
    
</style>
<body>
	<section>
		<div class="container">
		<h1>콘텐츠</h1>
			<form method="post" action="/bw/board/boardFileDownload.do" id="form">
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
						<td style="text-align: right;">첨부파일</td>
						<td><a id="download">${b.filename }</a></td>
						<%-- <td><a href="javascript:fileDownload('${b.filename }', '${b.filepath }')">${b.filename }</a></td> --%>
					</tr>
					<tr>
						<td style="text-align: right;">비밀번호</td>
						<td><input type="password" id="boardPw" name="boardPw" style="width: 100%" required></td>
						
					</tr>
					
				</table>
				<br>
				<input type="hidden" name="filename" value="${b.filename }">
				<input type="hidden" name="filepath" value="${b.filepath }">
				<input type="hidden" id="boardContent2" value="${b.boardContent }">
				<input type="hidden" name="boardNo" id="boardNo" value="${b.boardNo }">
				<button type="button" class="update">수정</button>
				<button type="button" class="delete">삭제</button>
				<button type="button" class="return">목록으로</button>
				<button type="button" class="reply">답글달기</button>
				<br>
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
                        	<td>
                        		<input type="text" name="boardCommentWriter" id="boardCommentWriter" placeholder="작성자를 입력해 주세요" style="width: 100%;">
                        	</td>
                        	<td>
                            	<input type="password" placeholder="비밀번호를 입력해 주세요" name="boardCommentPw" id="boardCommentPw" style="width: 100%;">
                        	</td>
                        </tr>
                        <tr>
                        	<td width="70%" colspan="2">
                                <textarea class="form-control" style="width: 100%;" name="boardCommentContent" id="boardCommentContent" placeholder="댓글은 내 얼굴입니다."></textarea>
                            </td>
                        </tr>
                        <tr>
                        	<td colspan="2">
                               <button type="button" class="btn btn-primary" id="commentBtn">댓글 작성</button>
                        	</td>
                        </tr>
                    </table>
                </form>
            </div>
            
		<div class="comment-wrapper">
            <c:forEach items="${commentList }" var="bc">
	                <ul class="commentList">
	                    <li><span>${bc.boardCommentWriter }</span></li>
	                    <li><span>${bc.boardCommentDate }</span></li>
	                    <li>
	                    	<span>${bc.boardCommentContent }</span>
	                    	<textarea class="form-control" name="boardCommentContent" style="display: none;">${bc.boardCommentContent }</textarea>
	                    	<button type="submit" name="commentModifyButton" style="display: none;">확인</button>
	                    </li>
	                    <li>
	                    	<a href="javascript:void(0)" onclick="modifyComment(this, '${bc.boardCommentNo}', '${bc.boardRef }')" class="left">수정</a>
	                    	<a href="javascript:void(0)" onclick="deleteComment(this, '${bc.boardCommentNo }', '${bc.boardRef }')" class="left">삭제</a>
	                    	<input type="password" class="commentPw" name="commentPw" placeholder="비밀번호를 입력하세요">
	                    	<input type="button" class="commentPwButton" name="commentPwButton" value="입력">
	                    </li>
	                </ul>
            </c:forEach>
        </div>

	</section>	
</body>

<script>
$("#download").click(function(){
	$("#form").submit();
});
	function fileDownload(filename, filepath) {
		var newFilename = encodeURIComponent(filename);
	    var newFilepath = encodeURIComponent(filepath);
	    
	    
	}
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
						, boardCommentPw:$("#boardCommentPw").val()
					},
				type: "get",
				success: function(data){
					if(data=='1'){
						$("#boardCommentWriter").val("");
						$("#boardCommentPw").val("");
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
										html += "<ul class='commentList'><li><span>" + data.boardCommentWriter + "</span></li>";
										html += "<li><span>" + data.boardCommentDate + "</span></li>";
										html += "<li><span>" + data.boardCommentContent + "</span>";
										html += "<textarea class='form-control' name='boardCommentContent' style='display: none;'>" + data.boardCommentContent + "</textarea></li>"
										html += "<li><a href='javascript:void(0)' onclick='modifyComment(this, &#39;" + data.boardCommentNo + "&#39;, &#39;" + data.boardRef + "&#39;)' class='left'>수정</a>";
										html += "<a href='javascript:void(0)' onclick='deleteComment(this, &#39;" + data.boardCommentNo + "&#39;, &#39;" + data.boardRef + "&#39;)' class='left'>삭제</a></li>";
									location.reload();
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
	
    function deleteComment(obj, boardCommentNo, boardRef) {
    	$(obj).next().attr("style", "display: block;");
    	$(obj).next().next().attr("style", "display: block;");
    	$(obj).next().next().click(function(){
        	$.ajax({
    		url: "/bw/board/commentPwCheck.do",
    		data: {
    				boardCommentPw : $(obj).next().val()
    				, boardCommentNo : boardCommentNo
    			},
    		type: "get",
    		success: function(data){
    			if (data == '1') {
    				if(confirm("삭제하시겠습니까?")){
    					$.ajax({
    						url: "/bw/board/deleteComment.do",
    			    		data: {
	    			    			boardCommentPw : $(obj).next().val()
	    		    				, boardCommentNo : boardCommentNo
    			    			},
    			    		type: "get",
    			    		success:function(data){
    			    			if(data == '1'){
    			    				console.log("삭제완료");
    			    				location.reload();
    			    			}
    			    		}
    					});
    				}
                } else{
                	alert("비밀번호를 확인해 주세요.");
                }
    		},
    		error: function(){
    			alert("관리자에게 문의해 주세요");
    		}
    	});
      });
    }
    
    function modifyComment(obj, boardCommentNo, boardRef) {
    	$(obj).next().next().attr("style", "display: block;");
    	$(obj).next().next().next().attr("style", "display: block;");
    	$(obj).next().next().next().click(function(){
        	$.ajax({
    		url: "/bw/board/commentPwCheck.do",
    		data: {
    				boardCommentPw : $(obj).next().next().val()
    				, boardCommentNo : boardCommentNo
    			},
    		type: "get",
    		success: function(data){
    			if (data == '1') {
    				$(obj).parent().prev().find('textarea').show();
    				$(obj).parent().prev().find('button').show();
    				$(obj).parent().prev().find('span').hide();
    	        	var $form=$("<form action='/bw/board/modifyComment.do' method='post'></form>");
    	        	$form.append($("<input type='hidden' name='boardCommentNo' value='" + boardCommentNo + "'>"));
    	            $form.append($("<input type='hidden' name='boardRef' value='" + boardRef + "'>"));
    	            $form.append($(obj).parent().parent().find("textarea"));
    	            $(obj).parent().prev().append($form);
    	            $(obj).parent().prev().find('button').click(function(){
    					$.ajax({
						url: "/bw/board/modifyComment.do",
			    		data: {
	    			    			boardCommentContent : $(obj).parent().prev().find('textarea').val()
	    		    				, boardCommentNo : boardCommentNo
			    			},
			    		type: "get",
			    		success:function(data){
				    			if(data == '1'){
				    				console.log("수정완료");
				    				location.reload();
				    			}
			    			}
						});
    	            });

    				
                } else{
                	alert("비밀번호를 확인해 주세요.");
                }
    		},
    		error: function(){
    			alert("관리자에게 문의해 주세요");
    		}
    	});
      });
    }
//     $("#download").click(function(){
//         var fileName = "${b.filename }";
//         window.open("/bw/board/test.do?&fileName="+fileName);
    
// //     	$.ajax({
// //     		url: "/bw/board/fileDownload.do",
// //     		data: {
// //     					filePath : filePath, 
// //     					fileName : fileName
// //     			},
// //     		type: "get",
// //     		success: function(data){
// //     			console.log(data);
// //     			window.open("/bw/board/fileDownload.do?filePath="+filePath+"&fileName="+fileName);
// //     		},
// //     		error: function(){
// //     			console.log("실패");
    			
// //     		}
// //     	});
        
//      });


</script>
</html>