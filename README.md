# UserNetworkProject_JAVA
one Server &amp; many Client can network with socket. sending Object to ObjectStream.


Add Client, Server, User, UserFileInputStream,etc.
Client 클래스 : 서버에 연결하는 클라이언트 클래스 
Server 클래스 : 클라이언트에게 채팅 정보를 받아 뿌려주는 클래스
User 클래스 : 유저 정보를 멤버로 가지는 클래스(직렬화 가능)
UserFileInputStream, UserFileOutputStream User 객체를 직렬화해서 텍스트 파일에 입출력하는 클래스
UserSVC 클래스 : 유저 멤버에 접근해 다양한 기능을 하는 클래스
BoardClient 클래스 : 클라이언트가 사용하는 board 클래스. User정보를 HashMap에 저장하고 불러와서 다양한 메뉴창에 보여주고 UserSVC, UserFileStream기능을 수행한다.
