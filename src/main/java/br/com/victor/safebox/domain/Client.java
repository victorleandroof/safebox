package br.com.victor.safebox.domain;

import org.hibernate.validator.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Client {

	private String id;

	@NotNull(message = "Name cannot be null!")
	@Size(min = 3, max = 80, message = "Name Me must be between 3 and 80 characters")
	private String name;

	@NotNull(message = "Lastname cannot be null!")
	@Size(min = 3, max = 80, message = "Name Me must be between 3 and 80 characters")
	private String lastname;

	@NotNull(message = "username cannot be null!")
	@Size(min = 3, max = 80, message = "LastName Me must be between 3 and 80 characters")
	@Email
	private String username;

	@NotNull(message = "Password cannot be null!")
	private String password;

	private String publicKey;

	@Size(min = 14 , max = 15, message = "Cellphone Me must be between 14 and 15 characters")
	private String cellphone;

	private LocalDate birthdate;

	private List<Authority> authorities = new ArrayList<Authority>(){
		{
			add(Authority.ROLE_USER);
		}
	};

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}


	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}
}
