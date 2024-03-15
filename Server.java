package Board;
import java.io.*;
import java.util.*;
import java.net.*;

public class Server {
	static BoardMain boardMain = new BoardMain();
	
	public static void main(String[] args) {		
		ServerSocket listener = null;
		ObjectInputStream ois = null;	//클라이언트로부터 오브젝트 받는 스트림
		FileOutputStream fos = null;	//파일에 저장하는 스트림
		ObjectOutputStream foos = null;	//오브젝트를 파일에 저장하는 스트림
		ObjectOutputStream oos = null;	//문자열 보내는 아웃스트림
		Socket socket = null;
		Scanner sc = new Scanner(System.in);
		Map<String, User> userMap = null;
		try {
			listener = new ServerSocket(9999);	//서버 소켓 생성 : 네트워크 연결 받는 객체
			System.out.println("연결을 기다리고 있습니다.");
			socket = listener.accept();	//클라이언트로부터 연결 요청 대기
			System.out.println("연결되었습니다.");
			ois = new ObjectInputStream(socket.getInputStream());		//클라이언트로부터 오브젝트 입력 스트림 생성
			fos = new FileOutputStream("c:\\data\\ServerInfo.txt");		//파일에 저장하는 스트림 생성
			foos = new ObjectOutputStream(fos);							//오브젝트 출력 스트림 생성
			oos = new ObjectOutputStream(socket.getOutputStream()); 
			foos.flush();
			oos.flush();
			while(true) {
				Object inputObject = ois.readObject(); // 직접 Object 타입으로 받음, 받을 때까지 대기함.				
				if(inputObject instanceof String) { // 받은 객체가 String인 경우
					String inputMessage = (String)inputObject;
					System.out.println("클라이언트: "+inputMessage);
					if(inputMessage.equalsIgnoreCase("bye")) {	//equalsIgnoreCase() 문자열을 대소문자 구분 없이 비교하는 메서드
						System.out.println("클라이언트에서 bye로 연결을 종료하였음.");
						break;	//"bye"를 받으면 연결 종료
					}
					System.out.print("클라이언트로 보내기>>");	//프롬프트
					String outputMessage = sc.nextLine();	//키보드에서 한 행 읽기
					oos.writeObject(outputMessage);
					oos.flush();
				}else if(inputObject instanceof Map) { // 받은 객체가 Map인 경우
					userMap = (Map<String, User>)inputObject;//ObjectStream으로 받은 객체 userMap에 저장					
					foos.writeObject(userMap);//userMap 오브젝트 파일로 write
					System.out.println("ServerInfo.txt를 생성함!");					
					oos.writeObject("userMap을 받음");
				}				
			}
		}catch(IOException e) {System.out.println(e.getMessage());
		}catch(ClassNotFoundException e) {System.out.println(e.getMessage());	//입력받은 오브젝트가 다른 클래스일수도 있으므로 예외처리해야됨
		}finally {
			try {
				sc.close();			//scanner 닫기
				socket.close();		//통신용 소켓 닫기
				listener.close();	//서버 소켓 닫기
			}catch(IOException e) {System.out.println("클라이언트와 채팅 중 오류가 발생했습니다."); e.printStackTrace();}
		}
	}
}
