package controller.dataroom;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dataroom.DataRoomDAO;
import model.dataroom.FileUtils;

public class DownloadController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//파라미터 받기]
		String no = req.getParameter("no");
		String filename = req.getParameter("filename");
		//다운로드 관련 모델 호출]
		//1.파일 다운로드 로직 호출]
		FileUtils.download(req, resp, filename, "/Upload");
		//2.테이블의 다운로드수 컬럼 증가용 데이타베이스 관련 로직 호출]
		DataRoomDAO dao = new DataRoomDAO(req.getServletContext());
		dao.updateDownloadCount(no);
		dao.close();
	}//////////////doGet
}
