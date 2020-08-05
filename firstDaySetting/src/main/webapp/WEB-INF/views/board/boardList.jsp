<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <script type='text/javascript' src='http://code.jquery.com/jquery-3.3.1.js'></script>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>보더리스트</title>
</head>
<style>
	table, tr, td{
		border: 1px solid black;
		text-align: center;
	}
	.pagingBlock>ul>li{
		display: block;
		text-decoration: none;
	}
    .container{
        padding: 15px;
        margin: auto;
        width: 1000px;
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
    
    .textCenter{
    	text-align: center;
    }
    .serachBox>div>form>* {
		float: left;
	}
	
	.serachBox>div {
		display: inline-block;
		overflow: hidden;
	}
	
	.serachBox {
		text-align: center;
		margin: 0 auto;
	}
	
	.input {
		padding: 1px 6px;
		height: 26px;
		box-sizing: border-box;
		float: right;
	}
	.title>p{
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
		width: 470px;
		height: 20px;
	}
	
	.wrapper{
		margin: 0 auto;
	}
	
	.pagingBlock>a{
		margin: 10px;
	}
	

</style>
<body>
	<section>
		<div class="container">
		<h1>리스트</h1>
		<span><a href="/bw/board/boardWriteFrm.do">글쓰기</a></span>
			<table>
				<tr>
					<td width="10%">번호</td>
					<td width="50%">제목</td>
					<td width="20%">글쓴이</td>
					<td width="30%">날짜</td>
                </tr>
          				
				<c:set var="num" value="${totalCount - ((reqPage-1) * numPerPage) }"/>
				<c:forEach items="${list}" var="list" varStatus="status">
                	<tr>
	                	<td>${num}</td>
	                	<td class="title" style="text-align: left;"><p><c:forEach begin="2" end="${list.boardLevel }" >
	                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                	</c:forEach><a href="/bw/board/contentPage.do?boardNo=${list.boardNo }">${list.boardTitle2 }(${list.commentCount })</a></p></td>
	                	<td>${list.boardWriter2 }</td>
	                	<td>${list.boardDate }</td>
	                </tr>
	                
	                <c:set var="num" value="${num-1 }"></c:set>
	            </c:forEach>
			</table>
			<br>
		<div class="serachBox">
		<div class="wrapper">
		<form action="/bw/board/boardList.do" method="get">
				<input type="hidden" name="reqPage" value="1">
					<select name="type" class="input">
						<c:choose>
							<c:when test="${type eq 'boardTitle' }">
								<option value="boardTitle" selected>제목</option>
								<option value="boardWriter">작성자</option>
							</c:when>
							<c:when test="${type eq 'boardWriter' }">
								<option value="boardTitle">제목</option>
								<option value="boardWriter" selected>작성자</option>
							</c:when>
							<c:otherwise>
								<option value="boardTitle">제목</option>
								<option value="boardWriter">작성자</option>
							</c:otherwise>
						</c:choose>
					</select>
				<input type="text" name="keyword" class="input" value="${keyword }">
				<input type="submit" value="검색" class="input">
		</form>
		</div>
		</div>
		<div class="paging">
			<div class="textCenter">
				<div class="pagingBlock">
					<a href="/bw/board/boardList.do?reqPage=1&type=${type}&keyword=${keyword}">맨앞</a>
					${pageNavi }
					<a href="/bw/board/boardList.do?reqPage=${totalPage }&type=${type}&keyword=${keyword}">맨뒤</a>
				</div>
				<div>
					<a href="/bw/board/boardList.do" style="disply: block; float: right;">목록으로</a>
				</div>
			</div>
		</div>
		</div>
	</section>
</body>
<script>

</script>
</html>