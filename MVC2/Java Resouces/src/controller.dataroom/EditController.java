package controller.dataroom;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import model.dataroom.DataRoomDAO;
import model.dataroom.DataRoomDTO;
import model.dataroom.FileUtils;

public class EditController extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//한글처리]
		req.setCharacterEncoding("UTF-8");
		//3]요청분석- 수정처리 요청
		//4]모델호출 및 결과값 받기
		//파일 업로드와 관련된 모델(비지니스 로직) 호출
		MultipartRequest mr= FileUtils.upload(req, req.getServletContext().getRealPath("/Upload"));
		//DB수정 성공시에는 1,실패시 0, 파일용량 초과시에는 -1 저장
		int successOrFail;
		//오류시 수정값 보존을 위한 변수 선언]
		String name,title,password=null,content,no="";
		if(mr !=null) {//파일 업로드 성공일때 DB 수정처리]
			//기타 파라미터 받아서 테이블에 수정처리]
			no = mr.getParameter("no");
			name = mr.getParameter("name");
			title= mr.getParameter("title");			
			content = mr.getParameter("content");
			String attachedFile = mr.getFilesystemName("attachedFile");
			//데이타베이스 CRUD작업과 관련된 모델 호출]
			DataRoomDAO dao = new DataRoomDAO(req.getServletContext());
			DataRoomDTO dto = new DataRoomDTO();
			dto.setNo(no);
			dto.setAttachedFile(attachedFile);
			dto.setContent(content);
			dto.setName(name);			
			dto.setTitle(title);
			successOrFail=dao.update(dto);
			if(successOrFail==0) {//데이터 수정 실패시 업로된 파일 삭제
				//파일 삭제 로직 호출]
				FileUtils.deleteFile(req,"/Upload",attachedFile);
			}
			dao.close();
			
		}
		else {//파일 용량 초과시
			successOrFail = -1;
		}
		//5]리퀘스트 영역에 결과값 혹은 필요한 값 저장
		req.setAttribute("SUCFAIL",successOrFail);
		req.setAttribute("no",no);
		//5-2]컨트롤러 구분용-입력:INS,수정:EDT,삭제:DEL
		req.setAttribute("WHERE", "EDT");
		//6]뷰로 이동:성공시 바로 목록으로 ,성공이 아닐시 메시지를 뿌려주는 JSP페이지로 이동
		
		if(successOrFail ==1) {//파일업로드 및 DB입력 성공
			
			req.getRequestDispatcher("/DataRoom13/Message.jsp").forward(req, resp);
		}
		else {//파일 용량 초과 혹은 파일업로드는 됬으나 DB입력 실패한 경우			
			req.getRequestDispatcher("/DataRoom13/Message.jsp").forward(req, resp);
			
			
		}
	}//////////doPost
}
