package controller.dataroom;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dataroom.DataRoomDAO;
import model.dataroom.DataRoomDTO;
import model.dataroom.FileUtils;

public class PasswordController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//한글처리]
		req.setCharacterEncoding("UTF-8");
		//파라미터 받기]
		String no=req.getParameter("no");
		String password=req.getParameter("password");
		String mode=req.getParameter("mode");
		String originalFilename=req.getParameter("originalFilename");
		//현재 페이비 번호 받기-삭제시 필요 .해당 페이지로 가기위해
		int nowPage =Integer.parseInt(req.getParameter("nowPage"));
		//모델호출 및 결과값 받기]		
		DataRoomDAO dao = new DataRoomDAO(req.getServletContext());
		boolean flag=dao.isCorrectPassword(no,password);
		dao.close();
		//뷰 선택 후 포워딩]
		//[ 비밀번호가 틀린 경우는 이전 페이지로]	
		if(!flag) {
			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter out=resp.getWriter();
			out.println("<script>");
			out.println("alert('비밀번호가 일치하지 않아요');");
			out.println("history.back();");
			out.println("</script>");
			return;
		}
		
		//[비밀번호가 일치하는 경우]
		if("UPDATE".equals(mode)) {//수정폼으로 이동			
			req.getRequestDispatcher("/DataRoom/Edit.kosmo").forward(req, resp);
		}
		else {//삭제를 누른 경우-삭제처리후 목록으로 이동
			dao = new DataRoomDAO(req.getServletContext());
			int sucOrFail=dao.delete(no);
			//삭제후 해당 페이지로 가기위한 코드(페이지가 줄어든 경우 포함)
			int total=dao.getTotalRecordCount();
			int pageSize = Integer.parseInt(this.getInitParameter("DATAROOM_PAGESIZE"));
			int totalPage = (int)Math.ceil((double)total/pageSize);
			if(totalPage < nowPage) nowPage=totalPage;		
			
			dao.close();
			if(sucOrFail==1) {//레코드삭제 성공시 업로드된 파일 삭제
				FileUtils.deleteFile(req, "/Upload", originalFilename);
			}
			
			req.setAttribute("nowPage", nowPage);
			req.setAttribute("SUCFAIL", sucOrFail);
			//메시지 뿌려주는 페이지로 이동
			req.getRequestDispatcher("/DataRoom13/Message.jsp").forward(req, resp);
		}
		
	}
}
