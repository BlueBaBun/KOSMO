<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 위 3개의 메타 태그는 *반드시* head 태그의 처음에 와야합니다; 어떤 다른 콘텐츠들은 반드시 이 태그들 *다음에* 와야 합니다 -->
    <title>Edit</title>

    <!-- 부트스트랩 -->
    
    <link href="<c:url value='/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet">

    <!-- IE8 에서 HTML5 요소와 미디어 쿼리를 위한 HTML5 shim 와 Respond.js -->
    <!-- WARNING: Respond.js 는 당신이 file:// 을 통해 페이지를 볼 때는 동작하지 않습니다. -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
      
    
  </head>
  <body>
  	<!-- 네비게이션바 시작 -->
  	<jsp:include page="/Template/DataRoomTop.jsp"/>  	  
  	<!-- 네비게이션바 끝 -->
  	<!-- 실제 내용 시작 -->
    <div class="container">
    	<!-- 점보트론(Jumbotron) -->
    	<div class="jumbotron">
    		<h1>자료실<small>수정 페이지</small></h1>
    	</div>
    	<div class="row">
    		<div class="col-md-12">
    			<form class="form-horizontal" method="post" action="<c:url value='/DataRoom/Edit.kosmo'/>" enctype="multipart/form-data">
    				<input type="hidden" name="no" value="${record.no}"/>
    				<div class="form-group">
    					<label class="col-md-2 control-label">작성자</label>
    					<div class="col-md-3">
    						<input value="${record.name}" type="text" class="form-control" name="name" id="name" placeholder="이름을 입력하세요"/>    					
    					</div>
    				</div>
    				<div class="form-group">
    					<label class="col-md-2 control-label">제목</label>
    					<div class="col-md-5">
    						<input value="${record.title}" type="text" class="form-control" name="title" id="title" placeholder="제목을 입력하세요"/>    					
    					</div>
    				</div>
    				<div class="form-group">
    					<label class="col-md-2 control-label">파일 업로드</label>
    					<div class="col-md-5">
    						<!-- class="form-control" 생략 -->
    						<input type="file" name="attachedFile" id="attachedFile"/> 
    						<p class="help-block">${record.attachedFile}</p>   					
    					</div>
    				</div>
    				
    				<div class="form-group">
    					<label class="col-md-2 control-label">내용</label>
    					<div class="col-md-5">
    						<textarea rows="10" name="content" id="content" class="form-control" placeholder="내용을 입력하세요">${record.content}</textarea>
    					</div>
    				</div>
    				<div class="form-group">
    					<div class="col-md-offset-2 col-md-10">
    						<button type="submit" class="btn btn-info">수정</button>
    					</div>
    				
    				</div>
    			</form>    		
    		</div>    	
    	</div><!-- row -->    	
    </div><!-- container -->
    <!-- 경고창 모달 시작-->
    <div class="modal fade"  id="small-modal" data-backdrop="static">
   		<div class="modal-dialog modal-sm">
   			<div class="modal-content">   				   				
   				<div class="modal-body"> 
   					<button class="close" data-dismiss="modal">
   						<span>&times;</span>
   					</button>
   					<h3 class="modal-title"><span class="glyphicon glyphicon-bullhorn"> 경고메시지</span></h3>  					
   					<h4 id="alertMessage"></h4>   					
   				</div>    				
   			</div>    		
   		</div>   	
   	</div>     	
    <!-- 경고창 모달 끝 -->
    
	<!-- 실제 내용 끝 -->
    <jsp:include page="/Template/DataRoomFooter.jsp"/>
    <script>
	    /*    	
		//파일 사이즈(바이트):파일객체.files[0].size
		//파일 명:파일객체.files[0].name
		//파일 컨텐츠 타입:파일객체.files[0].type
		*/
		$(function(){
			
			$('form').on('submit',function(){
				
				if($('#name').val() == ""){
					$('#alertMessage').html("아이디를 입력하세요");
					$('#small-modal').modal('show');
					$('#name').focus();
					return false;
				}
				if($('#title').val() == ""){
					$('#alertMessage').html("제목을 입력하세요");
					$('#small-modal').modal('show');
					$('#title').focus();
					return false;
				}
				if($('#attachedFile').val() == ""){
					$('#alertMessage').html("파일을 첨부하세요");
					$('#small-modal').modal('show');
					$('#attachedFile').focus();
					return false;
				}
				else{//파일을 첨부한 경우:파일 업로드 최대 용량 체크
					if($('#attachedFile').get(0).files[0].size > 500 *1024){
						$('#alertMessage').html("업로드 최대용량(500KB)을 초과 했어요");
						$('#small-modal').modal('show');
						return false;
					}
					
				}
				if($('#password').val() == ""){
					$('#alertMessage').html("비밀번호를 입력하세요");
					$('#small-modal').modal('show');
					$('#password').focus();
					return false;
				}
				if($('#content').val() == ""){
					$('#alertMessage').html("내용을 입력하세요");
					$('#small-modal').modal('show');
					$('#content').focus();
					return false;
				}	
				
			});	
			
			
		});
    
    </script>
    
  </body>
</html>
