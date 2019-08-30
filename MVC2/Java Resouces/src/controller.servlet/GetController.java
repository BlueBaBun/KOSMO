package controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//1.HttpServelt상속
public class GetController extends HttpServlet {
	//2.GET방식으로 요청이 들어옴:doGet오버라이딩
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//3.리퀘스트 영역에 데이타 저장
		req.setAttribute("method_get", "GET방식 요청입니다");
		//4.뷰로(JSP페이지)로 포워딩
		req.getRequestDispatcher("/Servlet12_1/Basic.jsp").forward(req, resp);
	}
}
