package Board;
import java.io.Serializable;

public class User implements Serializable{			//ObjectStream을 사용하기 위해선 Serializable 인터페이스를 상속받는다.
	private static final long serialVersionUID = 1L; // 이 값이 서버와 클라이언트 모두에서 같아야 합니다.
	private String id;
	private String passwd;
	private String email;
	private int age;
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String dong) {
		this.email = dong;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "User [id> " + id + ", email> " + email + ", age> " + age + ", name> " + name + "]";
	}
	public User(String id, String passwd, String email, int age, String name) {
		super();
		this.id = id;
		this.passwd = passwd;
		this.email = email;
		this.age = age;
		this.name = name;
	}
}
