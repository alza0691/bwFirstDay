<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type='text/javascript' src='http://code.jquery.com/jquery-3.3.1.js'></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>답글쓰기</title>
</head>
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
    	magin-left: 10px;
    }
    
    .right{
    	text-align: right;
    }
    
</style>
<body>
	<section>
		<div class="container">
		<h1>답글쓰기</h1>
			<form action="/bw/board/replyWrite.do" method="post" id="form">
				<table>
					<tr>
						<td width="12%" class="right">날짜</td>
						<td width="78%">
							<span id="dateShow"></span>	
						</td>
	                </tr>
					<tr>
						<td class="right">글쓴이</td>
						<td><input type="text" name="boardWriter" id="boardWriter" required placeholder="10자 이하로 작성하세요."></td>
					</tr>
					<tr>
						<td class="right">제목</td>
						<td><input type="text" name="boardTitle" id="boardTitle" required placeholder="40자 이하로 작성하세요."></td>
					</tr>
					<tr>
						<td class="right">내용</td>
						<td>
							<textarea name="boardContent" id="boardContent" class="autosize" cols="30" rows="20" style="width:100%; min-height:300px; resize: none;"
							placeholder="1000자 이하로 작성하세요." required></textarea>
							<span id="counter">0</span>/1000
						</td>
					</tr>
					<tr>
	                    <td class="right" rowspan='2'>첨부파일</td>
	                    <td>
	                    	<span id="showName1" class="showName">${boardVo.showFilename1 }</span><br>
	                    	<span id="showName2" class="showName">${boardVo.showFilename2 }</span><br>
	                    	<span id="showName3" class="showName">${boardVo.showFilename3 }</span>
<!-- 	                        <input type="file" name="uploadfile" placeholder="파일 선택" id="uploadfile" style="width:0%; float: left;"> -->
								<input multiple="multiple" type="file" id="uploadfile" name="uploadfile[]" accept=".jpg, .jpeg, .png, .gif, .bmp">
	                    </td>
	                </tr>
	                <tr>
						<td>
							<button type="button" id='button'>파일찾기</button>
							<button type="button" id="deleteButton">파일삭제</button>
							<span>5MB 이하의 .jpg, .jpeg, .png, .gif, .bmp 파일만 가능합니다. (총 3개)</span>
						</td>
					</tr>
					<tr>
						<td class="right">비밀번호</td>
						<td><input type="password" name="boardPw" id="boardPw" required placeholder="숫자, 문자, 특수문자를 조합한 6이상 20이하의 비밀번호를 입력해 주세요."></td>
					</tr>
				</table>
				<input type="hidden" name="boardRef" id="boardRef" value="${boardNo }">
				<input type="hidden" name="keyword" id="keyword" value="${data.keyword }">
				<input type="hidden" name="type" id="type" value="${data.type }">
				<input type="hidden" name="reqPage" id="reqPage" value="${data.reqPage }">
			</form>
			<button type="button" id="submit" class="button" style="width: 70px; margin-left: 10px;">제출</button>
			<button type="button" class="button return" style="width: 70px; margin-left: 10px;">목록으로</button>
			<button type="button" class="button back" style="width: 70px; margin-left: 10px;">본문으로</button>
		</div>
	</section>	
</body>
	<script>
	$(".autosize").on("keyup", function() {
		var autosize = $(".autosize"); 
		var size = autosize.prop('scrollHeight');
		autosize.css("height",size);
	});
	
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
				location.href="/bw/board/boardList.do?boardNo="+${boardNo }+ "&reqPage=" + ${data.reqPage} + "&type=${data.type}&keyword=${data.keyword}";
			});
		});
		$(function(){
			$(".back").click(function(){
				location.href="/bw/board/contentPage.do?boardNo="+${boardNo }+ "&reqPage=" + ${data.reqPage} + "&type=${data.type}&keyword=${data.keyword}";
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

			$("#boardTitle").on("change keyup mousedown", function(){
				if($("#boardTitle").val().length != ""){
					var checkCount = $(this).val().length;
					var boardTitle = $(this).val();					
					var remain = 40-checkCount;
					if(remain < 0){
						alert("40글자를 초과할 수 없습니다.");
						$("#boardTitle").val(boardTitle.slice(0,40));
						return false;
					} else if ($.trim($("#boardTitle").val())==""){
							alert("빈칸을 입력할 수 없습니다.")
							$("#boardTitle").val(boardTitle.slice(0,0));
							return false;
					}
				}
			});
					
			$("#boardContent").on("change keyup mousedown", function(){
				
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
		$("#boardPw").on("change keyup mousedown", function(){
			var checkCount = $(this).val().length;
			var boardPw = $(this).val();					
			var remain = 20-checkCount;
			if(remain < 0){
				alert("20글자를 초과할 수 없습니다.");
				$("#boardPw").val(boardPw.slice(0,0));
				return false;
			} 
		});
		
		$("#submit").click(function(){
			console.log($("#boardPw").val());
			var regExp = /^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[~!@#$%^&*()_+=]).{6,20}$/;
			var boardPwLength = $("#boardPw").val().length;
			if($("#boardWriter").val() == ""){
				$("#boardWriter").focus();
				alert("글쓴이를 입력해 주세요.");
			} else if ($("#boardTitle").val() == ""){
				$("#boardTitle").focus();
				alert("제목을 입력해 주세요.");
			} else if ($("#boardContent").val() ==""){
				$("#boardContent").focus();
				alert("콘텐츠를 입력해 주세요.");
			} else if($("boardPw").val()==""){
				$("#boardPw").focus();
				alert("비밀번호를 입력해 주세요.");
			} else if($("#boardPw").val().length < 6){ 
				alert("비밀번호를 확인해주세요. \n숫자, 문자, 특수문자를 조합한 6이상 20이하의 비밀번호를 입력해 주세요.")
				$("#boardPw").val("").focus();
			} else if(!regExp.test($("#boardPw").val())){
				alert("비밀번호를 확인해주세요. \n숫자, 문자, 특수문자를 조합한 6이상 20이하의 비밀번호를 입력해 주세요.")
				$("#boardPw").val("").focus();
			} else{
				$("#form").submit();
			}
		});	
		
		$(function(){
			$("#boardContent").on("change keyup paste", function(e){
				var content = $(this).val();
				$(this).height(((content.split('\n').length + 1) * 1.5) + 'em');
			    $('#counter').html(content.length);	
			});
			$("#deleteButton").click(function(){
				$(".showName").html("");
				if ($.browser.msie) { // ie 일때  input[type=file] init. 
					$("#uploadfile").replaceWith( $("#uploadfile").clone(true) ); 
				} else { // other browser 일때 input[type=file] init. 
					$("#uploadfile").val(""); 
				}
			});
			$('#button').click(function(){
				$("input[type='file']").trigger('click');
				
			});
			$("input[type='file']").change(function(){
				$('#showName1').text("");
				$('#showName2').text("");
				$('#showName3').text("");
				var fileInput = document.getElementById("uploadfile");
				var files = fileInput.files;
 				var fileSize = new Array;
 				var browser=navigator.appName;
 				var arr = new Array;
 				
 				if (browser=="Microsoft Internet Explorer"){
 					var oas = new ActiveXObject("Scripting.FileSystemObject");
 					fileSize = oas.getFile( fileInput.value ).size;
 				} else{
 					for(var i = 0; i < files.length; i++){
 						fileSize.push(fileInput.files[i].size);
 					}
 				}
 				
 				if(files.length > 3){
 					alert("파일은 3개까지 첨부할 수 있습니다.");
 					$("#uploadfile").val(""); 
 					$('#showName1').text("");
 					$('#showName2').text("");
 					$('#showName3').text("");
 				}
 				
 				
 				for(var i = 0; i < files.length; i++){
 					if(fileSize[i] > 625000){
 						alert("파일사이즈를 5MB 이하로 업로드 해주세요");
 						$("#uploadfile").val(""); 
 						$('#showName1').text("");
 						$('#showName2').text("");
 						$('#showName3').text("");
 					} else{
 						for (var i = 0; i < files.length; i++) {
						arr.push(fileInput.files[i].name);
		            }
					$('#showName1').text(arr[0]);
					$('#showName2').text(arr[1]);
					$('#showName3').text(arr[2]);
					break;
					}
 				}
			});
		});
	</script>
</html>