package com.insideresttest.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.insideresttest.model.User;
import com.insideresttest.model.Message;
import com.insideresttest.service.UserService;
import com.insideresttest.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author hanza
 * Класс, реализующий работу REST контроллера
 * 
 */

@RestController
public class Controller {
	private UserService userService; //реализует соединение и CRUD операции с базой данных
	private JwtService jwtService; //реализует создание и проверку jwt token
	private static final Logger log = LoggerFactory.getLogger(Controller.class);
	
	private void Controller() {}
	
	@Autowired
	public void Controller(UserService userService, JwtService jwtService) {
		this.userService = userService;
		this.jwtService = jwtService;
	}
	
	/**
	 * Метод, реализует аутентификации пользователей
	 * @param name - имя пользователя
	 * @param password - пароль пользователя
	 * @return возращает или токен, если пользователь есть в базе, или статус ошибки
	 */
	@PostMapping("/auth")
	@ResponseBody
	public ResponseEntity<?> authenticate(
			@RequestParam(value="name", required=true) String name,
			@RequestParam(value="password", required=true) String password) {
		
		log.info("User name: " + name);
		log.info("User password: " + password);
		
		List<User> users = userService.searchAll();
		String token = null;
		if(users != null) {
			for(User user : users) {
				if(user.getName().equals(name) && user.getPassword().equals(password)) {
					token = jwtService.generateToken(user);
					user.setToken(token);
					userService.update(user);
					return new ResponseEntity<>("{\n\ttoken: " + token + "\n}", HttpStatus.CREATED);
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Метод обрабатывает сообщения от пользователей.
	 * Если сообщение не содержит history, то оно сохраняется в базе.
	 * Иначе выводится история сообщений.
	 * @param name - имя пользователя
	 * @param message - сообщение от пользователя
	 * @param authHeader - заголовок с токеном
	 * @return возвращает статус обработки сообщений от пользователей: успешно обработано или ошибку
	 */
	@PostMapping("/message")
	@ResponseBody
	public ResponseEntity<?> messageHandling(
			@RequestParam(name="name", required=true) String name, 
			@RequestParam(name="message", required=false, defaultValue="" ) String message,
			@RequestHeader(name = "Authorization", required=true) String authHeader) {

		log.info("User name: " + name);
		log.info("User message: " + message);
		log.info("User authorization header: " + authHeader);
		
		User user = validateAuthHeader(authHeader);
		
		if(user != null) {
			if(!detectHistoryMessage(message)) {
				Message msg = new Message();
				msg.setMessage(message);
				user.addMessage(msg);
				userService.update(user);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				int messageCount = Integer.valueOf(message.substring("history ".length(), message.length()));
				List<Message> messages = user.getMessages();
				if(messages != null) {
					if(messages.size() > messageCount)
						messages = messages.subList(messages.size() - messageCount, messages.size());
					StringBuilder stringBuilder = new StringBuilder();
					stringBuilder.append("{\n");
					messages.forEach(msg -> stringBuilder.append("\tmessage: " + msg.getMessage() + ",\n"));
					stringBuilder.append("}");
					return new ResponseEntity<>(stringBuilder.toString(), HttpStatus.OK);
				}else {
					return new ResponseEntity<>("Messages not found", HttpStatus.NOT_FOUND);
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	/**
	 * Метод проверяет наличие заголовка с токеном в сообщениях от пользователей
	 * @param authHeader - строка заголовка
	 * @return - возвращает объект пользователя, если токен найден в базе или null
	 */
	private User validateAuthHeader (String authHeader) {
		if(!authHeader.isBlank() && !authHeader.isEmpty() && authHeader.matches("Bearer [\\p{Alnum}\\p{Punct}]+")) {
			String token = authHeader.substring("Bearer ".length(), authHeader.length());
			User User = userService.search(token);
			return User;
		}
		return null;
	}
	
	/**
	 * Метод проверяет наличие слова history в сообщениях от пользователей
	 * @param message - строка сообщения от пользователя
	 * @return возвращает статус проверки сообщения
	 */
	private boolean detectHistoryMessage (String message) {
		if(message.matches("history [\\p{Digit}]+")) {
			return true;
		}
		return false;
	}
}
