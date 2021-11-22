package com.insideresttest.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * Класс - объект сообщения от пользователя, которых хранится в базе
 * @author hanza
 *
 */
@Entity
@Table(name = "user_table")
public class User {
	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long user_id;
	@Column(name="name")
	private String name;
	@Column(name="password")
	private String password;
	@Column(name="token")
	private String token;
	/*@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name = "user_id")*/
	@OneToMany( cascade=CascadeType.ALL, orphanRemoval=true)//, fetch=FetchType.LAZY)
	
	private List<Message> messages;

	public User() {}
	
	public User(String name, String password, String token) {
		this.name = name;
		this.password = password;
		this.token = token;
	}
	
	public Long getId() {
		return user_id;
	}
	
	public void setId(Long user_id) {
		this.user_id = user_id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public void addMessage(Message message) {
        messages.add(message);
    }
	
	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("user:[\n");
		stringBuilder.append("\tid : " + user_id + ",\n");
		stringBuilder.append("\tname : " + name + ",\n");
		stringBuilder.append("\tpassword : " + password + ",\n");
		stringBuilder.append("\ttoken : " + token + ",\n");
		messages.forEach(msg -> stringBuilder.append("\t" + msg.toString()));
		stringBuilder.append("]");
		return stringBuilder.toString();
	}
}
