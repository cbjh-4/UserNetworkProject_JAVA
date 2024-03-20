package Board_DB;
public class BoardMain {	

	public static void main(String[] args){
		int menu;
		boolean stop = false;		
		BoardSVC boardSVC = new BoardSVC();
		do {
			menu = boardSVC.selectMenu();
			switch(menu) {
			case 1:	//등록			
				boardSVC.writeArticle();				
				break;
			case 2:	//수정
				boardSVC.changeArticle();
				break;
			case 3:	//삭제
				boardSVC.removeArticles();	
				break;
			case 4:	//목록
				boardSVC.printArticles();
				break;
			case 5:	//검색
				boardSVC.searchArticle();		//유저 등록(닉네임, 비밀번호, 동, 나이, 이름)								
				break;
			case 6:	//종료
				stop = true;				
			}
		}while(!stop);
	}
}
