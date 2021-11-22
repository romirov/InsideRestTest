package com.insideresttest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;

/**
 * 
 * Класс - объект сообщения от пользователя, которых хранится в базе
 * @author hanza
 *
 */
@Entity
@Table(name="message_table")
public class Message {
	@Id
	@Column(name="message_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long message_id;
	@Column(name="message")
	private String message;
	@ManyToOne
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="user_id")
    private User user;

	public Message() {}
	
	public Message(String message, User user) {
		this.message = message;
	}
	
	public Long getId() {
		return message_id;
	}
	
	public void setId(Long message_id) {
		this.message_id = message_id;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\tmessage: [\n");
		stringBuilder.append("\t\tid : " + message_id + ",\n");
		stringBuilder.append("\t\tmessage : " + message + "\n");
		stringBuilder.append("\t]\n");
		return stringBuilder.toString();
	}
}
