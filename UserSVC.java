package Board;
import java.util.Collection;
import java.util.HashMap;	//key값으로 구분할 이름 String넣고 value값으로 User클래스 받을 예정이다.
import java.util.Map;
import java.util.Scanner;

public class UserSVC {
	public Map<String, User> userMap;
	private UserFileOutputStream ufos = new UserFileOutputStream();
	static int menu;

	public UserSVC() {
		userMap = new HashMap<>();
		initUser();
	}

	private void initUser() {		
		userMap.put("user1", new User("user1", "pass1", "동1", 30, "홍길동"));
		userMap.put("user2", new User("user2", "pass2", "동2", 40, "홍길자"));
		userMap.put("user3", new User("user3", "pass3", "동3", 50, "홍길순"));
		userMap.put("user4", new User("user4", "pass4", "동4", 10, "홍길직"));
		userMap.put("user5", new User("user5", "pass5", "동5", 20, "홍길표"));
		ufos.userFileSave(userMap);
	}

	public boolean authUser(String id, String passwd) {
		if(userMap.containsKey(id)) {
			User user = userMap.get(id);
			return user.getPasswd().equals(passwd);
		}
		return false;
	}

	public void createUser(Scanner sc) {
		System.out.println("유저를 등록합니다.");
		System.out.print("유저명(작성자명): ");
		String id = sc.nextLine();
		if(userMap.containsKey(id)) {
			System.out.println("동일한 유저명이 존재합니다.");
			return;
		}
		System.out.print("비밀번호: ");
		String passwd = sc.nextLine();
		System.out.print("이메일주소: ");
		String email = sc.nextLine();
		System.out.print("나이: ");
		int age = sc.nextInt();
		sc.nextLine();	//stream 비우기
		System.out.print("이름: ");
		String name = sc.nextLine();		
		userMap.put(id, new User(id, passwd, email, age, name));
		ufos.userFileSave(userMap);
	}

	public User login(Scanner sc) {
		while(true) {
			System.out.println("유저명(작성자명): ");
			String id = sc.nextLine();
			System.out.println("비밀번호: ");
			String passwd = sc.nextLine();
			if(userMap.containsKey(id)) {
				User user = userMap.get(id);
				if(user.getPasswd().equals(passwd)) {
					return user;
				}					
			}System.out.println("유저명(작성자명) 또는 비밀번호가 다릅니다. 다시 입력해주세요.");
		}		
	}

	public void listUser() {
		for (User user : userMap.values()) {
			System.out.println(user);
		}
	}

	public void searchUser(Scanner sc) {	//02.29 NEW. 회원검색 기능 
		boolean found = false;
		System.out.println("유저 검색창 입니다.");
		System.out.println("검색할 정보 입력(유저명 or 이메일 or 나이 or 이름): ");
		String userInput = sc.nextLine();
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
		if(!found) {
			System.out.println("검색된 정보가 없습니다.");	
			System.out.println("------------------------------------------------------");}
	}
	
	public void changeUserFile(Scanner sc) {
		System.out.println("유저 정보 수정창 입니다.");
		System.out.print("로그인해주세요>>");
		User user = login(sc);
		System.out.print("수정 할 정보를 알려주세요. 1.유저명 2.이메일 3.나이 4.이름 5.패쓰워드");
		int menu = Integer.parseInt(sc.nextLine());
		switch(menu) {
		case 1:
			System.out.println("현재 유저명: "+user.getId());
			System.out.print("수정할 유저명: ");
			String tempId = sc.nextLine();
			user.setId(tempId);
			System.out.println("수정했습니다.");
			break;
		case 2:
			System.out.println("현재 이메일주소: "+user.getEmail());
			System.out.print("수정할 이메일주소: ");
			String tempEmail = sc.nextLine();
			user.setEmail(tempEmail);
			System.out.println("수정했습니다.");
			break;
		case 3:
			System.out.println("현재 나이: "+user.getAge());
			System.out.print("수정할 나이: ");
			String tempAge = sc.nextLine();
			user.setAge(Integer.parseInt(tempAge));
			break;
		case 4:
			System.out.println("현재 이름: "+user.getName());
			System.out.print("수정할 이름: ");
			String tempName = sc.nextLine();
			user.setEmail(tempName);
			System.out.println("수정했습니다.");
			break;
		case 5:
			System.out.println("현재 비밀번호: "+user.getPasswd());
			System.out.print("수정할 비밀번호: ");
			String tempPasswd = sc.nextLine();
			user.setEmail(tempPasswd);
			System.out.println("수정했습니다.");
			break;
		default:
			System.out.println("메뉴는 숫자로 1~5 사이 값을 입력하세요.");
		}
		ufos.userFileSave(userMap);
	}
	
//	public Map<String, User> getUserMap(){return userMap;	}
}
