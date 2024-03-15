package Board;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import java.io.*;

public class Client {
	
	public static void main(String[] args) {
		BoardClient boardClient = new BoardClient();
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		Socket socket = null;
		Scanner sc = null;
		try {
			sc = new Scanner(System.in);
			socket = new Socket("localhost", 9999);	//클라이언트 소켓 생성, 서버에 연결 자신의 IP주소: "localhost"//			
            oos = new ObjectOutputStream(socket.getOutputStream());	// ObjectOutputStream 생성
            oos.flush();
            ois = new ObjectInputStream(socket.getInputStream());
			while(true) {
				System.out.print("서버로 보내기>>");	//프롬프트
				String outputMessage = sc.nextLine();	//키보드에서 한 행 읽기
				if(outputMessage.equals("bye")) {	//키보드에서 bye를 입력 받았다면
					oos.writeObject(outputMessage);	//bye를 서버에 보낸다.
					oos.flush();					//아웃 스트림을 비운다.
					break;		//"bye"를 입력하면 서버로 전송 후 실행 종료
				}else if(outputMessage.equals("board")) {	//else if문 : board를 입력하면 userMap객체를 아웃스트림으로 전송
					boardClient.boardRun(sc);				//board 실행
					Map<String, User> userMap = boardClient.userSVC.userMap;
					oos.writeObject(userMap);		//userMap객체를 UserFileInputStream에서 받아와 서버로 전송
					oos.flush();					//아웃 스트림 버퍼 에 있는 모든 데이터 전송					
				}else {
					oos.writeObject(outputMessage);	//키보드에서 읽은 값이 그냥 문자열일 경우 메세지 전송
					oos.flush();					//아웃 스트림 버퍼 에 있는 모든 데이터 전송 
				}
				String inputMessage = (String)ois.readObject();	//서버에서 읽어올때까지 대기
				System.out.println("서버: "+inputMessage);		//서버에서 읽어온 문자열 화면에 출력
			}
		}catch(IOException e) {System.out.println(e.getMessage());
		}catch(ClassNotFoundException e) {System.out.println(e.getMessage());
		}finally {
			try {
				sc.close();
				if(socket != null) socket.close(); //클라이언트 소켓 닫기				
			}catch(IOException e) {System.out.println("서버와 채팅 중 오류가 발생했습니다."); e.printStackTrace();}
		}
	}
}
