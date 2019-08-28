package controller.dataroom;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dataroom.DataRoomDAO;
import model.dataroom.DataRoomDTO;

public class PasswordController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String no=req.getParameter("no");
		String password=req.getParameter("password");
		String mode=req.getParameter("mode");		
		DataRoomDAO dao = new DataRoomDAO(req.getServletContext());
		boolean flag=dao.isCorrect(no,password);
		
		if(!flag) {
			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter out=resp.getWriter();
			out.println("<script>");
			out.println("alert('비밀번호가 일치하지 않아요');");
			out.println("history.back();");
			out.println("</script>");			
		}
		else {
			//비번이 일치시
			if("UPDATE".equals(mode)) {//수정폼으로 이동
				DataRoomDTO record=dao.selectOne(no);
				req.setAttribute("record", record);
				req.getRequestDispatcher("/DataRoom13/Edit.jsp").forward(req, resp);
			}
			else {//삭제처리
				int sucOrFail=dao.delete(no);
				req.setAttribute("SUCFAIL", sucOrFail);
				req.getRequestDispatcher("/DataRoom13/Message.jsp").forward(req, resp);
			}
		}
		dao.close();
	}
}
