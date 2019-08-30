package controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BothController extends HttpServlet {
	//하나의 컨트롤러로 get 및 post요청 처리하기
	//방법1]-service오버라이딩
	/*
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("method_both",req.getMethod()+"방식 요청");
		req.getRequestDispatcher("/Servlet12_1/Basic.jsp").forward(req, resp);
	}////////////service
	*/
	//방법2]doGet과 doPost오버라이딩해서 한쪽으로 몰아주자
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}////////
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("method_both",req.getMethod()+"방식 요청");
		req.getRequestDispatcher("/Servlet12_1/Basic.jsp").forward(req, resp);;
	}//////////
}
