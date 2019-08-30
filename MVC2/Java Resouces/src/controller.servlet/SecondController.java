package controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecondController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//First컨트롤러로 포워드
		//뷰(jsp페이지) 뿐만 아니라 컨트롤러로 요청을 보내는 URL도 지정가능
		req.getRequestDispatcher("/Basic/first.kosmo").forward(req, resp);
		
	}
}
