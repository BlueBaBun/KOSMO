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

//1]HttpServlet상속-컨트롤러가 됨 즉 서블릿이 됨
public class WriteController extends HttpServlet {
	//[입력 폼으로 이동]
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//3]요청분석- 입력폼 요청
		//4]모델호출 및 결과값 받기
		//5]결과값이 있으면 ,리퀘스트 영역에 저장
		//6]뷰 선택
		/*
		 *  절대경로 지정시
		 * 	포워딩:컨텍스트 루트 경로 미 포함
		 *  리다이렉트:컨텍스트 루트 포함.
		 *  단, server.xml에 Context태그의 path속성 값을
		 *  지울시에는 신경 쓸 필요없다.
		 */
		//포워드]	
		req.getRequestDispatcher("/DataRoom13/Write.jsp").forward(req,resp);
		//리다이렉트]
		//resp.sendRedirect(req.getContextPath()+"/DataRoom13/Write.jsp");
		
	}///doGet
	//[입력 처리 즉 파일 업로드 및 데이타베이스 입력]
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//한글처리]
		req.setCharacterEncoding("UTF-8");
		//3]요청분석- 입력처리 요청
		//4]모델호출 및 결과값 받기
		//파일 업로드와 관련된 모델(비지니스 로직) 호출
		MultipartRequest mr= FileUtils.upload(req, req.getServletContext().getRealPath("/Upload"));
		//DB입력 성공시에는 1,실패시 0, 파일용량 초과시에는 -1 저장
		int successOrFail;
		//오류시 입력값 보존을 위한 변수 선언]
		String name,title,password=null,content;
		if(mr !=null) {//파일 업로드 성공일때 DB 입력처리]
			//기타 파라미터 받아서 테이블에 입력처리]
			name = mr.getParameter("name");
			title= mr.getParameter("title");
			password = mr.getParameter("password");
			content = mr.getParameter("content");
			String attachedFile = mr.getFilesystemName("attachedFile");
			//데이타베이스 CRUD작업과 관련된 모델 호출]
			DataRoomDAO dao = new DataRoomDAO(req.getServletContext());
			DataRoomDTO dto = new DataRoomDTO();
			dto.setAttachedFile(attachedFile);
			dto.setContent(content);
			dto.setName(name);
			dto.setPassword(password);
			dto.setTitle(title);
			successOrFail=dao.insert(dto);
			if(successOrFail==0) {//데이터 입력 실패시 업로된 파일 삭제
				//파일 삭제 로직 호출]
				FileUtils.deleteFile(req,"/Upload",attachedFile);
			}
			dao.close();
			
		}
		else {//파일 용량 초과시
			successOrFail = -1;
		}
		//5]리퀘스트 영역에 결과값 혹은 필요한 값 저장
		req.setAttribute("SUCFAILL",successOrFail);
		//5-2]컨트롤러 구분용-입력:INS,수정:EDT,삭제:DEL
		req.setAttribute("WHERE", "INS");
		//6]뷰로 이동:성공시 바로 목록으로 ,성공이 아닐시 메시지를 뿌려주는 JSP페이지로 이동
		req.getRequestDispatcher("/DataRoom/List.kosmo").forward(req, resp);
	}///////////////////////doPost
	
	
}
