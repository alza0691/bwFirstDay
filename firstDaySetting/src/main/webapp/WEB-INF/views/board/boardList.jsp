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
    li{
    	float: left;
    }
    .textCenter{
    	text-align: center;
    	float: right;
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
                <c:forEach items="${list}" var="list" varStatus="status">
                	<tr>
	                	<td>${status.count }</td>
	                	<td><a href="/bw/board/contentPage.do?boardNo=${list.boardNo }">${list.boardTitle }</a></td>
	                	<td>${list.boardWriter }</td>
	                	<td>${list.boardDate }</td>
	                </tr>
	            </c:forEach>
			</table>
			<div class="paging">
				<div class="textCenter">
					<div class="pagingBlock">
						<ul style="text-align:center;">
							<li>${pageNavi }</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
<script>
</script>
</html>