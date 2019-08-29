package controller.dataroom;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.PagingUtil;
import model.dataroom.DataRoomDAO;
import model.dataroom.DataRoomDTO;

//1]사용자 요청을 받을 수 있도록 서블릿 클래스로 만들기(HttpServlet상속)
//즉 컨트롤러로 만들기
public class ListController extends HttpServlet {
	/*2]사용자 요청이 GET방식이면 doGet(),POST방식이면 doPost() 오버라이딩
	
	※GET 및 POST방식을 구분하지 않고 요청을 받으려면
	service()를 오버라이딩 하거나
	혹은 위의 두 메소드 즉 doGet() ,doPost()를 오버라이딩해서
	한쪽 메소드에서 받은 요청을 다른 쪽으로 전달하면 된다.
	예]
	@Override
	protected void doPost(HttpServletRequest req, 
			              HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}///////////////////////////////////
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//여기서 컨트롤러(서블릿)이 할일을 구현
	}
	*/
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//System.out.println("POST방식으로 요청이 들어옴");
		//가]사용자 요청을 받는다	
		//나]요청을 분석한다.
		//다]모델에서 필요한 로직 호출해서 결과값이 있으면 받기 
		DataRoomDAO dao = new DataRoomDAO(req.getServletContext());
		Map map = new HashMap();
		//페이징을 위한 로직 시작]
	 	//전체 레코드수		
	 	int totalRecordCount=dao.getTotalRecordCount();
	 	//페이지 사이즈
	 	int pageSize = Integer.parseInt(this.getInitParameter("DATAROOM_PAGESIZE"));
	 	//블락페이지
	 	int blockPage = Integer.parseInt(this.getInitParameter("DATAROOM_BLOCKPAGE"));
	 	//전체 페이지수]
	 	int totalPage=(int)Math.ceil((double)totalRecordCount/pageSize);
	 	int nowPage=req.getParameter("nowPage")==null ? 1 : Integer.parseInt(req.getParameter("nowPage"));
	 	//시작 및 끝 ROWNUM구하기]
	 	int start = (nowPage-1)*pageSize+1;
	 	int end   = nowPage*pageSize;
	 	//페이징을 위한 로직 끝]	
		map.put("start", start);
		map.put("end", end);	
		
		List<DataRoomDTO> list=dao.selectList(map);
		dao.close();
		//라]결과값이 있으면 리퀘스트 영역에 저장
		req.setAttribute("list", list);
		//페이징 문자열을 리퀘스트 영역에 저장
		String pagingString=PagingUtil.pagingBootStrapStyle(totalRecordCount, pageSize, blockPage, nowPage,req.getContextPath()+"/DataRoom/List.kosmo?");
		//페이징과 관련된 속성들]
		req.setAttribute("pagingString",pagingString);
		req.setAttribute("totalRecordCount",totalRecordCount);
		req.setAttribute("nowPage",nowPage);
		req.setAttribute("pageSize",pageSize);
		//마]결과값을 뿌려줄 뷰(JSP페이지) 선택후 포워딩
		//뷰선택]
		RequestDispatcher dispatcher=req.getRequestDispatcher("/DataRoom13/List.jsp");
		//포워딩]
		dispatcher.forward(req, resp);
	}///////////////doPost
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("GET방식으로 요청이 들어옴");
		doPost(req, resp);
	}//////////////////
	
}
