package Board_DB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DatabaseUtil {	
	Connection con;			
	static {		//static 초기화 블록을 시작합니다. 이 블록 내의 코드는 클래스가 처음 로딩될 때 단 한 번만 실행됩니다.
		try {		//해당 드라이버가 있는지 확인
			Class.forName("oracle.jdbc.driver.OracleDriver");		//Class.forName 메소드를 호출하여 클래스를 메모리에 로드. JDBC 드라이버를 초기화하는 표준 방법 		
		}catch(ClassNotFoundException e) {		//해당 클래스를 찾을 수 없으면 예외처리
			e.printStackTrace();
		}
	}	
		
	public void insert(BoardVO boardVO) {
		PreparedStatement pstmt = null;
		try {
			System.out.println(boardVO);
			String sql ="INSERT INTO BOARD VALUES(board_seq.nextval,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, boardVO.getWriter());
			pstmt.setString(2, boardVO.getSubject());
			pstmt.setString(3, boardVO.getEmail());
			pstmt.setString(4, boardVO.getPasswd());
			
			int count = pstmt.executeUpdate();	//insert가 성공하면 0이 아닌 정수 값을 반환한다.
			if(count > 0){
				System.out.println("insert success..");
			}else {
				System.out.println("insert fail");
			}
		}catch(SQLException e) {
			System.out.println("insert fail...");
			e.printStackTrace();
		}finally{
			disconnect(con);
			disconnect(pstmt);
		}
	}
	
	public void select() {
		ResultSet rs = null;
		Statement stmt = null;
		try {
			String sql = "SELECT * FROM BOARD";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
//			if(!rs.next()) System.out.println("작성한 게시글이 없습니다!");			//게시글 있는지 체크
			while(rs.next()){	//ResultSet.nest(); 데이터를 한 줄 가져온다. 데이터가 없으면 false 반환
				System.out.println("인덱스: "+rs.getInt("id")+", 작성자: "+rs.getString("writer")+", 제목: "+rs.getString("subject")	//.getString() 매개변수에 몇번째 컬럼인지 인덱스를 적거나 id를 쌍따옴표 안에 적으면 된다.
				+", 이메일: "+rs.getString("email")+", 비밀번호: "+rs.getString("passwd")+"\n");
			}
		}catch(SQLException e) {e.printStackTrace();
		}finally {
			disconnect(con);
			disconnect(stmt);
			disconnect(rs);
	}}
	
	public void setArticle(BoardVO boardVO, int id) {
		ResultSet rs = null;
		Statement stmt = null;
		try {
			String sql = "SELECT * FROM BOARD";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {	//ResultSet.nest(); 데이터를 한 줄 가져온다. 데이터가 없으면 false 반환
				int TempId = rs.getInt("id");
				if(TempId == id) {	//아이디가 같은 게시글을 찾아 boardVO의 멤버를 수정한다.
					boardVO.setId(rs.getInt("id"));
					boardVO.setWriter(rs.getString("writer"));
					boardVO.setSubject(rs.getString("subject"));
					boardVO.setEmail(rs.getString("email"));
					boardVO.setPasswd(rs.getString("passwd"));
				}
			}			
		}catch(SQLException e) {e.printStackTrace();
		}finally {
			disconnect(con);
			disconnect(stmt);
			disconnect(rs);
		}				
	}
	
	public void deleteBoard(int id) {
		PreparedStatement pstmt = null;
		try {
			String sql = "DELETE FROM BOARD where id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			int count = pstmt.executeUpdate();
			if(count>0) {System.out.println("DELETE 성공!");
			}else {System.out.println("DELETE 실패...");}			
		}catch(SQLException e) {e.printStackTrace();
	}}	
	
	public void searchBoard(String searchInfo) {
		Statement stmt = null;
		ResultSet rs = null;
		BoardVO boardVO;
		try {
			String sql = "SELECT * FROM BOARD";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {	//ResultSet.nest(); 데이터를 한 줄 가져온다. 데이터가 없으면 false 반환
				String writer = rs.getString("writer");
				String subject = rs.getString("subject");
				String email = rs.getString("email");				
				if(searchInfo.equals(writer) || searchInfo.equals(subject) || searchInfo.equals(email)) {
					System.out.print(searchInfo+"로 검색한 결과값>> ");
					System.out.println("인덱스: "+rs.getInt("id")+", 작성자: "+rs.getString("writer")+", 제목: "+rs.getString("subject")	//.getString() 매개변수에 몇번째 컬럼인지 인덱스를 적거나 id를 쌍따옴표 안에 적으면 된다.
					+", 이메일: "+rs.getString("email")+", 비밀번호: "+rs.getString("passwd"));
				}
			}
		}catch(SQLException e) {e.printStackTrace();
		}finally {
			disconnect(con);
			disconnect(stmt);
			disconnect(rs);
		}
	}
	
	public void updateBoard(String column, String boardInfo, int id) {
		PreparedStatement pstmt = null;				
		try {
			String sql = "UPDATE BOARD SET " + column + " = ? WHERE id = ?" ;	//SQL 문에서 UPDATE 구문을 사용할 때는 FROM 키워드를 사용하지 않는다.
			pstmt = con.prepareStatement(sql);									//UPDATE 구문에서는 컬럼 이름을 매개변수화해서 바인딩할 수 없다.
			pstmt.setString(1, boardInfo);
			pstmt.setInt(2, id);			
			int count = pstmt.executeUpdate();
			if(count>0) {System.out.println("UPDATE 성공!");
			}else {System.out.println("UPDATE 실패...");}			
		}catch(SQLException e) {e.printStackTrace();
		}finally {
			try {
				disconnect(con);
				disconnect(pstmt);							
			}catch(Exception e) {e.printStackTrace();}
	}}		
	
	public void connect() {		
		con = null;
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",	//오라클 드라이버 이름, 오라클 계정명, 오라클 비밀번호
					"c##test1", "1234");												//Oracle 데이터베이스에 c##test1유저로 연결을 시도
		}catch(SQLException e) {System.out.println("Connection 실패"); e.printStackTrace();}		
	}
	
	public void disconnect(Statement stmt) {        // Statement 자원 해제 메서드
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) {System.out.println("Statement가 연결 해제 되지 않음"); e.printStackTrace();}
    }	
	public void disconnect(PreparedStatement pstmt) {        // PreparedStatement 자원 해제 메서드 오버로딩
        try {
            if (pstmt != null) pstmt.close();
        } catch (SQLException e) {System.out.println("PreparedStatement가 연결 해제 되지 않음"); e.printStackTrace();}
    }
	public void disconnect(Connection con) {        // PreparedStatement 자원 해제 메서드 오버로딩
        try {
            if (con != null) con.close();
        } catch (SQLException e) {System.out.println("Connection이 연결 해제 되지 않음"); e.printStackTrace();}
    }
	public void disconnect(ResultSet rs) {        // ResultSet 자원 해제 메서드 오버로딩
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {System.out.println("ResultSet이 연결 해제 되지 않음"); e.printStackTrace();}
    }
}
