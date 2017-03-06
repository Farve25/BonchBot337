package Bot.BonchBot;

public class Person {
	private String firstName;
	private String lastName;
	private String dept;
	private String mail;
	private String place;
	
	Person(String firstName, String lastName, String dept, String mail, String place){
		this.firstName = firstName;
		this.lastName = lastName;
		this.dept = dept;
		this.mail = mail;
		this.place = place;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getDept(){
		return dept;
	}
	
	public String getmail(){
		return mail;
	}
	
	public String getplace(){
		return place;
	}
}
