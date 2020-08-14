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
    .hide{
    	display: none;
    }
    .show{
    	display: block;
    }
	input[type='file'] {
	  opacity:0    
	}
	#uploadfile{
    	display: none;
    }
</style>
<body>
	<section>
		<div class="container">
		<h1>게시판 수정</h1>
			<form action="/bw/board/boardUpdate.do" method="post" id="boardUpdate" enctype="multipart/form-data">
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
					<tr>
						<td class="right" rowspan='2'>첨부파일</td>
						<td>
							<a href="javascript:fileDownload('${boardVo.filename1 }', '${boardVo.filepath }')"><span id="showName1" class="showName"></span></a><br>
                    		<a href="javascript:fileDownload('${boardVo.filename2 }', '${boardVo.filepath }')"><span id="showName2" class="showName"></span></a><br>
                    		<a href="javascript:fileDownload('${boardVo.filename3 }', '${boardVo.filepath }')"><span id="showName3" class="showName"></span></a>
							<input multiple="multiple" type="file" id="uploadfile" name="uploadfile[]" accept=".jpg, .jpeg, .png, .gif, .bmp" value="\fakepath\김기창-1 (1).jpg">
						</td>
					</tr>
					<tr>
						<td>
							<button type="button" id='button'>파일찾기</button>
							<button type="button" id="deleteButton">파일삭제</button>
							<span>5MB 이하의 .jpg, .jpeg, .png, .gif, .bmp 파일만 가능합니다. (총 3개)</span>
						</td>
					</tr>
				</table>
				<input type="hidden" id="boardNo" name="boardNo" value="${boardVo.boardNo }">
				<input type="hidden" name="keyword" id="keyword" value="${data.keyword }">
				<input type="hidden" name="type" id="type" value="${data.type }">
				<input type="hidden" name="reqPage" id="reqPage" value="${data.reqPage }">
			</form>
			<button type="button" id="modifyBtn" class="button" style="margin-top: 10px; margin-left: 10px;">수정</button>
			<button type="button" id="cancelBtn" class="button" style="margin-top: 10px; margin-left: 10px;">취소</button>
			<button type="button" class="button return" style="width: 70px; margin-top: 10px; margin-left: 10px;">목록으로</button>
			<br><br><br>
		</div>
	</section>	
</body>
<script>
function fileDownload(filename, filepath) {
	var newFilename = encodeURIComponent(filename);
    var newFilepath = encodeURIComponent(filepath);
    
    location.href = "/bw/board/download.do?filename=" + newFilename + "&filepath=" + newFilepath;
}
	$(document).ready(function() {
		var autosize = $(".autosize"); 
		var size = autosize.prop('scrollHeight');
		autosize.css("height",size);
		
		var content = $("#boardContent").val();
		$("#boardContent").height(((content.split('\n').length + 1) * 1.5) + 'em');
	    $('#counter').html(content.length);		    
	    
	    var fileInput = document.getElementById("uploadfile");
		console.log(fileInput.value);
	});

	$(function(){
			$(".return").click(function(){
				location.href="/bw/board/boardList.do";
			});
			$("#cancelBtn").click(function(){
				location.href="/bw/board/contentPage.do?boardNo="+${boardVo.boardNo}+"&reqPage=" + ${data.reqPage} + "&type=${data.type}&keyword=${data.keyword}";
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
			console.log(fileInput.value);
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
				
 				for(var i=0; i< files.length; i++){
 					if(files[i].name.length > 23){
 						alert("파일 이름이 20자가 넘습니다.");
 						$("#uploadfile").val(""); 
 					}
 				};
				
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

    		           		        