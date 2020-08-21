<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <script type='text/javascript' src='http://code.jquery.com/jquery-3.3.1.js'></script>
<!DOCTYPE html>
<html>
<head>
<% 
  String filename = request.getAttribute("filename").toString();
  response.setHeader("Content-Type", "application/vnd.ms-xls");
  response.setHeader("Content-Disposition", "inline; filename=Monitoring_" + filename + ".xls");
%>​ 
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<table>
				<tr>
					<td width="10%">번호</td>
					<td width="50%">제목</td>
					<td width="30px">첨부파일</td>
					<td width="20%">글쓴이</td>
					<td width="30%">날짜</td>
                </tr>
          				

				<c:forEach items="${list}" var="list" varStatus="status">
                	<tr>
	                	<td>${list.rnum}</td>
	                	<td class="title" style="text-align: left;">
		                	<p>
		                		<c:forEach begin="2" end="${list.boardLevel }" >
		                			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                		</c:forEach>

		                		${list.boardTitle2 }
		                				<c:if test="${list.commentCount != 0}">(${list.commentCount })</c:if>
		                			
		                	</p>
	                	</td>
	                	<td><c:if test="${list.filename != null }">유첨</c:if></td>
	                	<td>${list.boardWriter2 }</td>
	                	<td>${list.boardDate }</td>
	                </tr>
	            </c:forEach>
			</table>
</body>
</html>