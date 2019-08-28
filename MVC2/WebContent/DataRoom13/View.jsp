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
    <title>View</title>

    <!-- 부트스트랩 -->
    
    <link href="<c:url value='/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet">

    <!-- IE8 에서 HTML5 요소와 미디어 쿼리를 위한 HTML5 shim 와 Respond.js -->
    <!-- WARNING: Respond.js 는 당신이 file:// 을 통해 페이지를 볼 때는 동작하지 않습니다. -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
    	/* 수정/삭제/목록 버튼 사이 간격 주기 */
    	.btn-success {
    		margin-right:5px;
    	}
    </style> 
    
  </head>
  <body>
  	<!-- 네비게이션바 시작 -->
  	<jsp:include page="/Template/DataRoomTop.jsp"/>  	  
  	<!-- 네비게이션바 끝 -->
  	<!-- 실제 내용 시작 -->
    <div class="container">
    	<!-- 점보트론(Jumbotron) -->
    	<div class="jumbotron">
    		<h1>자료실<small>상세보기 페이지</small></h1>
    	</div>
    	<div class="row">
    		<div class="col-md-offset-2 col-md-8">
    			<table class="table table-bordered table-striped">
    				<tr>
    					<th class="col-md-2 text-center">번호</th>
    					<td>${record.no}</td>
    				</tr>
    				<tr>
    					<th class="text-center">제목</th>
    					<td>${record.title}</td>
    				</tr>
    				<tr>
    					<th class="text-center">작성자</th>
    					<td>${record.name}</td>
    				</tr>
    				<tr>
    					<th class="text-center">첨부파일</th>
    					<td><a href="<c:url value='/DataRoom/Download.kosmo?no=${record.no}&filename=${record.attachedFile}'/>">${record.attachedFile}</a></td>
    				</tr>
    				<tr>
    					<th class="text-center">다운로드</th>
    					<td>${record.downCount}</td>
    				</tr>
    				<tr>
    					<th class="text-center">등록일</th>
    					<td>${record.postDate}</td>
    				</tr>
    				<tr>
    					<th class="text-center" colspan="2">내용</th>    					
    				</tr>
    				<tr>
    					<td colspan="2">${record.content}</td>    					
    				</tr>    			
    			</table>
    		</div>    	
    	</div><!-- row --> 
    	<div class="row">
    		<div class="col-md-offset-2 col-md-8">
    			<!-- .center-block 사용시 해당 블락의 크기를 지정하자 -->
    			<ul id="pillMenu" class="nav nav-pills center-block" style="width:200px">
    				<li><a class="btn btn-success" href="javascript:password('UPDATE')">수정</a></li>
    				<li><a class="btn btn-success" href="javascript:password('')">삭제</a></li>
    				<li><a class="btn btn-success" href="<c:url value='/DataRoom/List.kosmo'/>">목록</a></li>
    			</ul>   		
    		</div>
    	</div><!-- row --> 
    	
    	   	
    </div><!-- container -->
    
	<!-- 실제 내용 끝 -->
    <jsp:include page="/Template/DataRoomFooter.jsp"/>
   	<form id="frm">
   		<input type="hidden" name="password" /> 
   		<input type="hidden" name="mode" />  		 	
   	</form>   	
   	<script>
   		
   		function password(flag){
   			if(flag != 'UPDATE'){
   				if(confirm('정말로 삭제 하시겠습니까?'))
   					window.open('/JSPProj/DataRoom13/Password.jsp?mode='+flag,'win',"width=250,height=100,left=100,top=100");
   			}
   			else{
   				window.open('/JSPProj/DataRoom13/Password.jsp?mode='+flag,'win',"width=250,height=100,left=100,top=100");
   			}
   		}
   		function isCorrect(){
   			location.href="/JSPProj/DataRoom/Password.kosmo?no=${record.no}&password="+document.getElementById('frm').password.value+"&mode="+document.getElementById('frm').mode.value;
   		}
   	
   	</script>
   
    
  </body>
</html>
