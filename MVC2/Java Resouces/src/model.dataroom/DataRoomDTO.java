package model.dataroom;

public class DataRoomDTO {
	private String no;
	private String name;
	private String title;
	private String password;
	private String content;
	private java.sql.Date postDate;
	private String attachedFile;
	private String downCount;
	
	
	//게터/세터
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public java.sql.Date getPostDate() {
		return postDate;
	}
	public void setPostDate(java.sql.Date postDate) {
		this.postDate = postDate;
	}
	public String getAttachedFile() {
		return attachedFile;
	}
	public void setAttachedFile(String attachedFile) {
		this.attachedFile = attachedFile;
	}
	public String getDownCount() {
		return downCount;
	}
	public void setDownCount(String downCount) {
		this.downCount = downCount;
	}
	
}
