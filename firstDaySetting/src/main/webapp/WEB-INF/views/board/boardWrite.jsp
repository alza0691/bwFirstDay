<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type='text/javascript' src='http://code.jquery.com/jquery-3.3.1.js'></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
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
    #uploadfile{
    	display: none;
    }
</style>
<body>
	<section>
		<div class="container">
		<h1>글쓰기</h1>
			<form class="boardWrite" action="/bw/board/boardWrite.do" method="post" id="uploadForm" enctype="multipart/form-data">
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
	                    <td id="dropZone" height=80px;>파일을 드래그 하세요<br>5MB 이하의 .jpg, .jpeg, .png, .gif 파일만 가능합니다. (총 3개)</td>
	                </tr>
	                <tr>
	                	<td height= 100px; id="fileTableTbody"></td>
					</tr>
					<tr>
						<td class="right">비밀번호</td>
						<td><input type="password" name="boardPw" id="boardPw" required placeholder="숫자, 문자, 특수문자를 조합한 6이상 20이하의 비밀번호를 입력해 주세요."></td>
					</tr>
				</table>
			</form>
			<button type="button" id="submit" class="button" style="width: 70px; margin-left: 10px;" onclick="uploadFile();">제출</button>
			<button type="button" class="button return" style="width: 70px; margin-left: 10px;">목록으로</button>
		</div>
	</section>	

	
	<br><br><br><br>
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
				location.href="/bw/board/boardList.do";
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
				
		$(function(){
			$("#boardContent").on("change keyup paste", function(e){
				var content = $(this).val();
				$(this).height(((content.split('\n').length + 1) * 1.5) + 'em');
			    $('#counter').html(content.length);	
			});
		});
		   // 파일 리스트 번호
	    var fileIndex = 0;
	    // 등록할 전체 파일 사이즈
	    var totalFileSize = 0;
	    // 파일 리스트
	    var fileList = new Array();
	    // 파일 사이즈 리스트
	    var fileSizeList = new Array();
	    // 등록 가능한 파일 사이즈 MB
	    var uploadSize = 5;
	    // 등록 가능한 총 파일 사이즈 MB
	    var maxUploadSize = 15;
	 
	    $(function (){
	        // 파일 드롭 다운
	        fileDropDown();
	    });
	 
	    // 파일 드롭 다운
	    function fileDropDown(){
	        var dropZone = $("#dropZone");
	        //Drag기능 
	        dropZone.on('dragenter',function(e){
	            e.stopPropagation();
	            e.preventDefault();
	            // 드롭다운 영역 css
	            dropZone.css('background-color','#E3F2FC');
	        });
	        dropZone.on('dragleave',function(e){
	            e.stopPropagation();
	            e.preventDefault();
	            // 드롭다운 영역 css
	            dropZone.css('background-color','#FFFFFF');
	        });
	        dropZone.on('dragover',function(e){
	            e.stopPropagation();
	            e.preventDefault();
	            // 드롭다운 영역 css
	            dropZone.css('background-color','#E3F2FC');
	        });
	        dropZone.on('drop',function(e){
	            e.preventDefault();
	            // 드롭다운 영역 css
	            dropZone.css('background-color','#FFFFFF');
	            
	            var files = e.originalEvent.dataTransfer.files;
	            if(files != null){
	                if(files.length < 1){
	                    alert("폴더 업로드 불가");
	                    return;
	                }
	                selectFile(files)
	            }else{
	                alert("ERROR");
	            }
	        });
	    }
	 
	    // 파일 선택시
	    function selectFile(files){
	        // 다중파일 등록
	    	if(files != null){
	            for(var i = 0; i < files.length; i++){
	                // 파일 이름
	                var fileName = files[i].name;
	                var fileNameArr = fileName.split("\.");
	                // 확장자
	                var ext = fileNameArr[fileNameArr.length - 1];
	                // 파일 사이즈(단위 :MB)
	                var fileSize = files[i].size / 1024 / 1024;
	                
	                if($.inArray(ext, ['jpg', 'jpeg', 'png', 'gif']) == -1){
	                    // 확장자 체크
	                    alert("5M 이하의 .jpg, .jpeg, .png, .gif 파일들만 가능합니다.");
	                    break;
	                }else if(fileSize > uploadSize){
	                    // 파일 사이즈 체크
	                    alert("용량 초과\n업로드 가능 용량 : " + uploadSize + " MB");
	                    break;
	                } else if(files[i].name.length > 23){
	                	// 파일 이름 체크
	                	alert("파일 이름이 20자가 넘습니다.");
	                } else if(fileIndex+1 > 3){
                   		alert("3개 이하로 첨부해 주세요");
                   		break;
	                }else{
	                    // 전체 파일 사이즈
	                    totalFileSize += fileSize;
	                    
	                    // 파일 배열에 넣기
	                    fileList[fileIndex] = files[i];
	                    
	                    // 파일 사이즈 배열에 넣기
	                    fileSizeList[fileIndex] = fileSize;
	 
	                    // 업로드 파일 목록 생성
	                    addFileList(fileIndex, fileName, fileSize);
	 
	                    // 파일 번호 증가
	                    fileIndex++;
	                }
	            }
	        }else{
	            alert("ERROR");
	        }
	    }
	 
	    // 업로드 파일 목록 생성
	    function addFileList(fIndex, fileName, fileSize){
	        var html = "";
	        html += "<span id='fileTr_" + fIndex + "'>";
	        html +=         fileName +  "<a href='#' onclick='deleteFile(" + 0 + "); return false;' class='btn small bg_02'>삭제</a><br>"
	        html += "</span>"
	 
	        $('#fileTableTbody').append(html);
	    }
	 
	    // 업로드 파일 삭제
	    function deleteFile(fIndex){
	        // 전체 파일 사이즈 수정
	        totalFileSize -= fileSizeList[fIndex];
	        
	        // 파일 배열에서 삭제
	        delete fileList[fIndex];
	        
	        // 파일 사이즈 배열 삭제
	        delete fileSizeList[fIndex];
	        
	        // 업로드 파일 테이블 목록에서 삭제
	        $("#fileTr_" + fIndex).remove();
	        fileIndex--;
	        
	    }
	 
	    // 파일 등록
	    function uploadFile(){
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
	        // 등록할 파일 리스트
	        var uploadFileList = Object.keys(fileList);
	 
	        // 파일이 있는지 체크
// 	        if(uploadFileList.length == 0){
// 	            // 파일등록 경고창
// 	            alert("파일이 없습니다.");
// 	            return;
// 	        }
	        
	        // 용량을 15MB를 넘을 경우 업로드 불가
	        if(totalFileSize > maxUploadSize){
	            // 파일 사이즈 초과 경고창
	            alert("총 용량 초과\n총 업로드 가능 용량 : " + maxUploadSize + " MB");
	            return;
	        }

	            // 등록할 파일 리스트를 formData로 데이터 입력
	            var form = $('#uploadForm');
	            var formData = new FormData(form[0]);
	            for(var i = 0; i < uploadFileList.length; i++){
	                formData.append('files', fileList[uploadFileList[i]]);
	            }
	            $.ajax({
	                url:"/bw/board/boardWrite.do",
	                data:formData,
	                type:'POST',
	                enctype:'multipart/form-data',
	                processData:false,
	                contentType:false,
	                dataType:'json',
	                cache:false,
	                success:function(data){
	                    if(data == '1'){
	                        location.href="/bw/board/boardList.do";
	                    }else{
	                        alert("관리자에게 문의하세요");
	                        location.reload();
	                    }
	                }
	            })
	    	}
	    }
	</script>
</html>