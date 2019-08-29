package controller.dataroom;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import model.PBKDF2;
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
			//비밀번호는 암호화:https://gist.github.com/jtan189/3804290
			try {
				password = PBKDF2.createHash(mr.getParameter("password"));
				System.out.println("password:"+password);
			} catch (Exception e) {e.printStackTrace();	} 
			
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
		req.setAttribute("SUCFAIL",successOrFail);
		//5-2]컨트롤러 구분용-입력:INS,수정:EDT,삭제:DEL
		req.setAttribute("WHERE", "INS");
		//6]뷰로 이동:성공시 바로 목록으로 ,성공이 아닐시 메시지를 뿌려주는 JSP페이지로 이동
		
		if(successOrFail ==1) {//파일업로드 및 DB입력 성공
			//[이동방법1]-바로 목록으로 이동:반드시 List.jsp가 아닌 List.kosmo로
			//req.getRequestDispatcher("/DataRoom/List.kosmo").forward(req, resp);
			//[이동방법2]-메시지 뿌려주는 페이지로 이동후 목록으로 이동
			req.getRequestDispatcher("/DataRoom13/Message.jsp").forward(req, resp);
		}
		else {//파일 용량 초과 혹은 파일업로드는 됬으나 DB입력 실패한 경우
			//[이동방법1]-자스의 history.back()으로 입력폼으로 이동
			//서블릿에서 브라우저로 직접 출력시 -한글처리]
			/*
			resp.setContentType("text/html;charset=UTF-8");
			PrintWriter out=resp.getWriter();
			out.println("<script>");
			out.println("alert('"+(successOrFail ==0 ?"DB입력 실패":"파일 용량 초과")+"');");
			out.println("history.back();");
			out.println("</script>");*/
			//[이동방법2]메시지 뿌려주는 페이지로 이동후 다시 입력폼으로
			req.getRequestDispatcher("/DataRoom13/Message.jsp").forward(req, resp);
			
			//이동방법3-비추천]비밀번호도 기존값 유지하기 위한 방법-역시 입력폼으로 이동
			//※MultipartRequest가 파라미터를 가로채니까
			//포워드하더라도 전달안됨 그래서 영역에 저장]
			/*
			if(mr == null) {
				//"UTF-8"생략시 파라미터로 받은 한글이 깨짐]
				mr= new MultipartRequest(
						req,
						req.getServletContext().getRealPath("/Upload"),"UTF-8");
				
				
			}			
			req.setAttribute("name", mr.getParameter("name"));
			req.setAttribute("title", mr.getParameter("title"));
			req.setAttribute("content", mr.getParameter("content"));
			req.setAttribute("pass", mr.getParameter("pass"));
			req.setAttribute("attachedfile",mr.getOriginalFileName("attachedfile"));
			req.setAttribute("error", sucOrFail==0?"Input Failure":"Exceed File Size");
			req.getRequestDispatcher("/DataRoom13/Write.jsp").forward(req, resp);
			*/
			
			
		}
		
	}///////////////////////doPost
	
	
}
