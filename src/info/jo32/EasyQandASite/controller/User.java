package info.jo32.EasyQandASite.controller;

@SuppressWarnings("serial")
public class User extends Entity {

	long id;
	String email;
	String name;
	String password;
	String role;

	public String getRight() {
		return role;
	}

	public void setRight(String right) {
		this.role = right;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
