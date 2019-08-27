package controller.dataroom;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//리퀘스트 영역에 저장]
		req.setAttribute("MAIN","누구나 자료를 올릴 수 있습니다");
		//메인 페이지(Index.jsp)로 페이지 이동]
		req.getRequestDispatcher("/DataRoom13/Index.jsp").forward(req, resp);
	}	
	
}
