package Board;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Scanner;
import java.io.EOFException;

public class UserFileInputStream {
	private FileInputStream fis = null;
	private ObjectInputStream ois = null;
	private Map<String, User> userMap = null;

	public void userFileRead(){	//파일에서 User 오브젝트를 읽어와 유저 정보 출력하는 메서드		
		try {
			fis = new FileInputStream("c:\\data\\clientInfo.txt");
			ois = new ObjectInputStream(fis);
			userMap = (Map<String, User>)ois.readObject();	//userMap에 인풋스트림에서 받아온 HashMap 오브젝트를 반환받음.
			for (User user : userMap.values()) {
				System.out.println(user);
			}		
		}catch(Exception e) {System.out.println("파일에서 유저 정보를 못 불러왔습니다.");e.printStackTrace();
		}finally {
			try {
				if(fis!=null) fis.close();
				if(ois!=null) ois.close();
			}catch(IOException e) {System.out.println("오류로 입력 스트림을 닫지 못함");System.out.println(e.getMessage());}
	}}
	
	public void SearchFile(Scanner sc) {	//파일에 저장된 User 오브젝트를 비교해서 검색된 유저정보 화면에 출력하는 메서드
		boolean found = false;
		System.out.println("유저 검색창 입니다.");
		System.out.println("검색할 정보 입력(유저명 or 이메일 or 나이 or 이름): ");
		String userInput = sc.nextLine();
		try {
			fis = new FileInputStream("c:\\data\\clientInfo.txt");
			ois = new ObjectInputStream(fis);
			userMap = (Map<String, User>)ois.readObject();
			Collection<User> userColletion = userMap.values();
			for(User user : userColletion) {
				String userAge = String.valueOf(user.getAge()); //userInput은 문자열인데 int 자료형은 user.getAge()와 비교하기 위해 문자열로 변환.
				if(userAge.equals(userInput) || user.getEmail().equals(userInput) || user.getId().equals(userInput) || user.getName().equals(userInput)) { 
					System.out.println("검색된 유저 정보:" + user);
					System.out.println("------------------------------------------------------");
					found = true;
					break;
				}
			}					
		}catch(EOFException e){System.out.println("파일 끝에 도달했음."); 
		}catch (Exception e) {	e.printStackTrace();
		} finally {
			try {
				if (fis != null) fis.close();
				if (ois != null) ois.close();
			} catch (IOException e) {System.out.println(e.getMessage());}
		}
		if (!found) {
			System.out.println("검색된 정보가 없습니다.");
			System.out.println("------------------------------------------------------");
		}
	}
}