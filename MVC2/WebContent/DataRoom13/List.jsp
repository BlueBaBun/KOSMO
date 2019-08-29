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
    <title>List</title>

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
    		<h1>자료실<small>목록 페이지</small></h1>
    	</div>
    	
    	<!-- 작성하기 버튼 -->
    	<div class="row" style="margin-bottom:10px">
    		<div class="col-md-12 text-right">
    			<a href="<c:url value='/DataRoom/Write.kosmo'/>" class="btn btn-success">등록</a>
    		</div>
    	</div>
    	
    	<div class="row">
    		<!-- 테이블 전체 가로폭은 테이블을 감싸는  div에 col-*-*로 조정 -->    		
    		<div class="col-md-12">
    			<table class="table table-bordered table-hover text-center">
    				<tr>
    					<!-- 각 컬럼의 폭은 <td>계열에 class="col-*-*"추가 -->
    					<th class="col-md-1 text-center">번호</th>
    					<th class="text-center">제목</th>
    					<th class="col-md-1 text-center">작성자</th>
    					<th class="col-md-2 text-center">첨부파일</th>
    					<th class="col-md-1 text-center">다운로드</th>
    					<th class="col-md-2 text-center">등록일</th>
    				</tr>
    				<c:if test="${empty list }"  var="isEmpty">
	    				<tr>
	    					<td colspan="6">등록된 게시물이 없습니다</td>  				
	    				</tr>
    				</c:if>
    				<c:if test="${not isEmpty }">
    					<c:forEach var="item" items="${list}" varStatus="loop">
		    				<tr>
		    					<td>${totalRecordCount - (((nowPage - 1) * pageSize) + loop.index)}</td>
		    					<td class="text-left"><a href="<c:url value='/DataRoom/View.kosmo?no=${item.no}&nowPage='/><c:out value='${param.nowPage}' default='1'/>">${item.title }</a></td>
		    					<td>${item.name }</td>
		    					<td><a class="downfile" title="${loop.count}" href="<c:url value='/DataRoom/Download.kosmo?filename=${item.attachedFile }&no=${item.no}'/>">${item.attachedFile }</a></td>  
		    					<td id="downcount${loop.count}">${item.downCount}</td> 
		    					<td>${item.postDate}</td> 				
		    				</tr>
	    				</c:forEach>
    				</c:if>
    				
    			</table>
    		</div><!-- col-md-12 -->   		
    	</div><!-- row -->
    	<!-- 페이징 -->
    	<div class="row">
    		<div class="col-md-12 text-center">
    		${pagingString}
    		</div>
    	</div>
    	
    </div><!-- container -->    
	<!-- 실제 내용 끝 -->
    <jsp:include page="/Template/DataRoomFooter.jsp"/>
    <script>    	
    	$(function(){
    		$('.downfile').click(function(){
    			
    			var numbering = $(this).attr('title');
    			//console.log(numbering);
    			var downcount=$('#downcount'+numbering).html();
    			$('#downcount'+numbering).html(parseInt(downcount)+1);
    		});
    		
    	});    
    </script>
  </body>
</html>
