package br.com.victor.safebox.gateway.mongo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import java.time.LocalDate;
import java.util.List;

@Document(collection="client")
public class ClientEntity {

	@Id
	private String id;
	@Field("name")
	private String name;
	@Field("lastname")
	private String lastname;
	@Field("username")
	@Indexed(unique = true)
	private String username;
	@Field("password")
	private String password;
	@Field("publickey")
	@Indexed(unique = true)
	private String publicKey;
	@Field("cellphone")
	@Indexed(unique = true)
	private String cellphone;
	@Field("birthdate")
	private LocalDate birthdate;
	@Field("passwords")
	@DBRef
	private List<PasswordEntity> listPasswords;

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

	public List<PasswordEntity> getListPasswords() {
		return listPasswords;
	}

	public void setListPasswords(List<PasswordEntity> listPasswords) {
		this.listPasswords = listPasswords;
	}
}
