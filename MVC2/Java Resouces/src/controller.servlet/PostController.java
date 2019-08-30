package controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//1.HttpServelt상속
public class PostController extends HttpServlet {
	//2.POST방식으로 요청이 들어옴:doPost오버라이딩
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//3.파라미터 받기
		String username=req.getParameter("username");
		String password=req.getParameter("password");
		//4.리퀘스트 영역에 데이타 저장
		req.setAttribute("method_post",String.format("POST방식 요청입니다-아이디:%s,비번:%s",username,password));
		//5.뷰로(JSP페이지)로 포워딩
		req.getRequestDispatcher("/Servlet12_1/Basic.jsp").forward(req, resp);
	}
}
