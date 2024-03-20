package Board_DB;

public class BoardVO {	//VO : Value, Object 값을 받는 데이터 클래스
	private int id;
	private String writer;
	private String subject;
	private String email;
	private String passwd;	
	
	public BoardVO() {super();}	
	public BoardVO(String writer, String subject, String email, String passwd) {
	super();
	this.id = 9999;	//디버깅을 위한 값. sql에서 자동으로 증가해서 생성되는 시퀀스 사용할 것임.
	this.writer = writer;
	this.subject = subject;
	this.email = email;
	this.passwd = passwd;
}
	@Override
	public String toString() {
		return "id>> " + id + ", writer>> " + writer + ", passwd>> "
				+ passwd  + ", subject>> " + subject + ", email>> " + email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}	
}
