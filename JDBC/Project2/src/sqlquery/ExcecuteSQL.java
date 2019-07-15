package sqlquery;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Vector;


/*
 * Statement계열의 execute()메소드]
 * 
 * Statement나 PreparedStatement객체로 일반 쿼리문 실행시
 * SELECT이면 executeQuery()
 * UPDATE/DELETE/INSERT이면 executeUpate()로 실행
 * 
 * 단,execute()메소드로 실행해도 쿼리가 실행됨
 * execute()메소드는
 * 쿼리문이 SELECT이면 true반환
 * 쿼리문이 그외 즉 UPDATE/DELETE/INSERT등이면 false반환

 * CallableStatement는 프로시저나 함수실행시 사용하는
 * Statement계열로
 * 실행시에는 반드시 execute()메소드로.....

 */
public class ExcecuteSQL extends service.IConnectImpl {
	@Override
	public void execute() throws Exception {
		
		//0]데이타 베이스 연결
		connect(ORACLE_URL, "USER2", "USER2");
		while(true) {
			//1]쿼리문 준비
			String query = getQueryString();
			if("EXIT".equalsIgnoreCase(query.trim())) {
				System.out.println("Oracle Database 11g Enterprise Edition Release 11.2.0.1.0 - 64bit Production\r\n" + 
						"With the Partitioning, OLAP, Data Mining and Real Application Testing options에서 분리되었습니다.");
				//자원반납]
				close();
				//프로그램 종료]
				System.exit(0);
			}
			//2]Statement계열 객체 생성-쿼리 실행용
			psmt = conn.prepareStatement(query);
			//3]쿼리 실행-execute():쿼리문이 미정 임으로
			/*
			execute()메소드로 쿼리 실행후 쿼리문이 SELECT인 경우
			ResultSet에 담긴 결과를 가져오려면 Statement계열 객체의
			getResultSet()메소드 호출
			
			쿼리문이 INSERT/DELETE/UPDATE일때
			영향받은 행의 수를 반환 받을때는 
			getUpdateCount()메소드 호출
			*/
			try {
				boolean bFlag=psmt.execute();
				if(bFlag) {//쿼리문이 SELECT
					//ResultSet객체 얻기]
					rs=psmt.getResultSet();
					//가]ResultSet객체의 getMetaData()로 ResultSetMetaData얻기
					ResultSetMetaData rsmd= rs.getMetaData();
					//나]총 컬럼수 얻기-ResultSetMetaData의 int getColumnCount()
					int columnCount = rsmd.getColumnCount();
					List<Integer> dashCount = new Vector<Integer>();
					for(int i=1; i <=columnCount ;i++) {
						//컬럼타입]
						int types = rsmd.getColumnType(i);
						//컬럼의 자리수]
						int length = rsmd.getPrecision(i);
						switch(types) {
							case Types.NCHAR:
							case Types.NVARCHAR:
								dashCount.add(length*2);break;
							case Types.TIMESTAMP:
							case Types.NUMERIC:
								dashCount.add(10);break;
							default:dashCount.add(length);
						}
						//컬럼명 출력]				
						//컬럼명이 대쉬의 숫자보다 크다면
						//예]GENDER CHAR(1) 
						/*
						 * GENDER
						 * -
						 * 
						 */
						String columnName = rsmd.getColumnName(i).length() > dashCount.get(i-1) ?
											rsmd.getColumnName(i).substring(0,dashCount.get(i-1)) :
											rsmd.getColumnName(i);
						System.out.print(String.format("%-"+(dashCount.get(i-1)+1)+"s", columnName));
						
					}////////for
					System.out.println();//줄바꿈
					//(-)DASH출력]
					for(Integer dash:dashCount) {
						for(int i=0;i < dash;i++) System.out.print("-");
						System.out.print(" ");
					}
					System.out.println();//줄바꿈
					//데이터 출력]
					while(rs.next()) {
						//각 컬럼값 뽑아오기]
						for(int i=1;i<=columnCount;i++) {
							int type=rsmd.getColumnType(i);
							if(type == Types.TIMESTAMP) {
								System.out.print(String.format("%-11s",rs.getDate(i)==null ? "" : rs.getDate(i)));
							}
							else {
								System.out.print(String.format("%-"+(dashCount.get(i-1)+1)+"s",rs.getString(i)==null ? "" : rs.getString(i)));
							}
						}///
						System.out.println();//줄바꿈
						
					}///////////while
				}
				else {//기타 쿼리문
					//영향받은 행의 수 얻기]
					int affected=psmt.getUpdateCount();
					if(query.trim().toUpperCase().startsWith("UPDATE")) {
						System.out.println(affected+"행이 수정되었어요");
					}
					else if(query.trim().toUpperCase().startsWith("DELETE")) {
						System.out.println(affected+"행이 삭제되었어요");
					}
					else if(query.trim().toUpperCase().startsWith("INSERT")) {
						System.out.println(affected+"행이 입력되었어요");
					}
					
				}
			}
			catch(SQLException e) {
				System.out.println(e.getMessage());
			}
			//while(true)시에는 불필요
			/*
			finally {
				close();
			}*/
		}/////////while
		
		
	}///////execute()
	public static void main(String[] args) throws Exception {
		new ExcecuteSQL().execute();
	}/////////main

}/////////////class
