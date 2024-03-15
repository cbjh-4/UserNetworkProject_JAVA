package Board;

import java.util.Scanner;

public class BoardClient {
	int menu;
	public UserSVC userSVC;
	
	public BoardClient() {
		userSVC = new UserSVC();
	}

	public void boardRun(Scanner sc) {
				
		UserFileInputStream ufis = new UserFileInputStream();		
		System.out.println("메뉴를 입력하세요.");
		System.out.println("1.회원 등록하기");
		System.out.println("2.등록된 회원 확인하기");
		System.out.println("3.회원 검색하기");
		System.out.println("4.회원 정보 수정하기");
		System.out.println("5.나가기");
		while(true) {
			try {
				System.out.print("메뉴 번호 입력: ");
				menu = sc.nextInt();
				sc.nextLine();	//stream 비우기
				break;
			}catch(Exception e) {
				System.out.println("정수를 입력하세요!");
				System.out.println("------------------------------------");
				sc.nextLine();	//stream 비우기
				break;
			}
		}						
		switch(menu) {
		case 1:
			userSVC.createUser(sc);		//유저 등록(닉네임, 비밀번호, 동, 나이, 이름)								
			break;
		case 2:
			ufis.userFileRead();
			break;
		case 3:
			ufis.SearchFile(sc);
			break;
		case 4:
			userSVC.changeUserFile(sc);
			break;
		default:
			break;
		}
	}
}
