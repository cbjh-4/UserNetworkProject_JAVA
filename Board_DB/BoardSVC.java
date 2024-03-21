package Board_DB;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public class BoardSVC {
	DatabaseUtil databaseUtil = new DatabaseUtil();
	Scanner sc = new Scanner(System.in);	
	static int menu;	
	
	public int selectMenu() {		
		System.out.println("========================================");		
		System.out.println("1.게시판 글쓰기");
		System.out.println("2.게시글 수정하기");
		System.out.println("3.게시글 삭제하기");
		System.out.println("4.게시글 목록 보여주기");
		System.out.println("5.게시글 검색하기");
		System.out.println("6.종료");
		System.out.println("========================================");
		System.out.print("메뉴를 입력하세요: ");
		return Integer.parseInt(sc.nextLine());			
	}
	
	public void writeArticle() {
		BoardVO boardVO;
		while(true){
			System.out.println("게시판 등록창입니다.");
			System.out.print("작성자를 입력하세요(20byte 이내로 입력하세요.)>>");
			String writer = sc.nextLine();
			if(checkLength(writer,20)) continue;	
//			String id; //id는 작성할때마다 sql에서 자동으로 1씩 증가해 생성되므로 생성할 땐 필요없다.	
			System.out.print("passwd를 입력하세요(20byte 이내로 입력하세요.)>>");
			String passwd = sc.nextLine();
			if(checkLength(passwd,20)) continue;			
			System.out.print("제목을 입력하세요(50byte 이내로 입력하세요.)>>");
			String subject = sc.nextLine();
			if(checkLength(subject,50)) continue;
			System.out.print("email을 입력하세요(30byte 이내로 입력하세요.)>>");
			String email = sc.nextLine();
			if(checkLength(email,30)) continue;
			boardVO = new BoardVO(writer, subject, email, passwd);		//boardVO객체 생성
			break;		//여기까지 continue하지 않고 실행했다면 반복문 종료
		}		
		databaseUtil.connect();			//Connection 객체 연결
		databaseUtil.insert(boardVO);	//데이터베이스에 게시판 DB 생성		
	}
	
	private boolean checkLength(String inputString, int size) {
		byte[] bytesCheck;
		try {	            
			bytesCheck = inputString.getBytes("UTF-8");	// 문자열을 UTF-8 바이트 배열로 변환
            if(bytesCheck.length > size) {
				System.out.println("\n\n\n"+size+"byte를 초과했습니다. 처음부터 다시 입력하세요.");
				return true;
            }
        } catch (UnsupportedEncodingException e) { e.printStackTrace();} 	// 인코딩이 지원되지 않는 경우의 예외 처리
        return false;		//try-catch문 모두 통과하면 byte초과하지도 않았고 예외도 없으므로 false를 반환. 반복문 재실행 안함
	}
	
	public void printArticles() {	//DB에서 게시글 목록 출력
		databaseUtil.connect();		//Connection 객체 연결
		databaseUtil.select();		//데이터베이스 ResertSet으로 읽어와 sysout으로 출력		
	}
	
	public void removeArticles() {	//DB에 있는 게시글 삭제
		BoardVO boardVO = new BoardVO();
		printArticles();
		databaseUtil.connect();		//sizeOfArticles 실행을 위해 연결
		int size = databaseUtil.sizeOfArticles();
		if(size == 0) {
			System.out.println("게시글이 없으므로 종료합니다.");
			return;
		}
		System.out.println("작성된 게시글의 수: "+size);
		System.out.print("제거할 글의 인덱스를 입력하세요: ");
		int index = Integer.parseInt(sc.nextLine());
		databaseUtil.connect();
		databaseUtil.setArticle(boardVO, index);
		if(boardVO.getPasswd() == null) {		//게시글이 없으면 boardVO 생성자에서 값이 정해지지않아 null값을 반환
			System.out.println("인덱스: "+index+" 게시글이 존재하지 않습니다.");
			return;
		}
		System.out.print("제거할 게시글의 비밀번호를 입력하세요: ");
		System.out.print("비밀번호 : ");
		String passwd = sc.nextLine();
		if(boardVO.getPasswd().equals(passwd)) {
			databaseUtil.connect();				//Connection 객체 연결
			databaseUtil.deleteBoard(index);	//DB에 id와 비교해서 해당 게시글 삭제
		}else {System.out.println("비밀번호가 다릅니다.");}		
	}
	
	public void searchArticle() {
		System.out.println("=====================================");
		System.out.println("게시글 검색 창 입니다.");	
		System.out.print("검색어: ");
		String searchInfo = sc.nextLine();
		databaseUtil.connect();
		databaseUtil.searchBoard(searchInfo);				
	}
	
	
	public void changeArticle() {		
		BoardVO boardVO = new BoardVO();
		printArticles();
		databaseUtil.connect();		//sizeOfArticles 실행을 위해 연결
		int size = databaseUtil.sizeOfArticles();
		if(size == 0) {
			System.out.println("게시글이 없으므로 종료합니다.");
			return;
		}
		System.out.println("작성된 게시글의 수: "+size);
		System.out.print("제거할 글의 인덱스를 입력하세요: ");
		System.out.print("수정할 글의 인덱스를 입력하세요: ");
		int index = Integer.parseInt(sc.nextLine());
		databaseUtil.connect();
		databaseUtil.setArticle(boardVO, index);
		if(boardVO.getPasswd() == null) {		//게시글이 없으면 boardVO 생성자에서 값이 정해지지않아 null값을 반환
			System.out.println("인덱스: "+index+" 게시글이 존재하지 않습니다.");
			return;
		}
		System.out.print("수정할 게시글의 비밀번호를 입력하세요: ");
		System.out.print("비밀번호 : ");
		String passwd = sc.nextLine();		
		if(boardVO.getPasswd().equals(passwd)) {	//인덱스, 비밀번호 일치
			changeMenu(boardVO.getId());			//메뉴창 실행 + util의 update 실행 메서드 불러오기
		}else {System.out.println("비밀번호가 다릅니다.");}	
	}
	
	private void changeMenu(int id) {	//메뉴창 출력 및 DB에 정보 수정
		String boardInfo;
		System.out.println("========================================");	
		System.out.println("게시글 수정 창 입니다.");
		System.out.println("1.작성자 수정하기");
		System.out.println("2.제목 수정하기");
		System.out.println("3.이메일 수정하기");
		System.out.println("4.비밀번호 수정하기");
		System.out.println("5.종료");
		System.out.println("========================================");
		System.out.print("메뉴를 입력하세요: ");
		String menu = sc.nextLine();
		switch(menu) {
		case "1":
			System.out.print("작성자명 입력: ");
			boardInfo = sc.nextLine();
			databaseUtil.connect();
			databaseUtil.updateBoard("writer", boardInfo, id);
			break;
		case "2":
			System.out.print("제목 입력: ");
			boardInfo = sc.nextLine();
			databaseUtil.connect();
			databaseUtil.updateBoard("subject", boardInfo, id);
			break;
		case "3":
			System.out.print("이메일 입력: ");
			boardInfo = sc.nextLine();
			databaseUtil.connect();
			databaseUtil.updateBoard("email", boardInfo, id);
			break;
		case "4":
			System.out.print("비밀번호 입력: ");
			boardInfo = sc.nextLine();
			databaseUtil.connect();
			databaseUtil.updateBoard("passwd", boardInfo, id);
			break;
		case "5":
			System.out.println("게시글 수정 창 종료...");
			return;
	}
	}
}
