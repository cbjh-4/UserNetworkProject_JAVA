package Board;
import java.io.*;
import java.util.*;

public class UserFileOutputStream {	
	private FileOutputStream fos = null;
	private ObjectOutputStream oos = null;
	
	public void userFileSave(Map<String, User> usermap){		
		try {
			fos = new FileOutputStream("c:\\data\\clientInfo.txt");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(usermap);					
		}catch(IOException e) {System.out.println(e.getMessage());e.printStackTrace();
		}finally {
			try {
				if(fos!=null)fos.close();
				if(oos!=null)oos.close();
			}catch(IOException e) {System.out.println("오류로, 유저 정보가 userinfo.txt에 저장되지 않았습니다.");e.printStackTrace();}
		}}
	
	

}
