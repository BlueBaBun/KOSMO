package controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FirstController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("MenuController:doGet-입력폼으로 포워드");
		//입력폼으로 포워드
		req.getRequestDispatcher("/Servlet12_1/Form.jsp").forward(req, resp);
		
	}
}
