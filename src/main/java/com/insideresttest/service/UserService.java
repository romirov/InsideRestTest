package com.insideresttest.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.insideresttest.model.User;
import com.insideresttest.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс реализует CRUD операции с базой данных
 * @author hanza
 *
 */
@Service
public class UserService {
	private UserDAO userDAO;
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	private UserService() {}
	
	@Autowired
	public UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Transactional
	public void save(User user) {
		log.info("Save user " + user.toString() + " to database.");
		userDAO.saveAndFlush(user);
	}
	
	@Transactional
	public User search(Long id) {
		log.info("Looking for a user with user id " + id);
		return userDAO.getById(id);
	}
	
	@Transactional
	public User search(String token) {
		log.info("Looking for a user with token " + token);
		return userDAO.findByToken(token);
	}
	
	@Transactional
	public List<User> searchAll() {
		log.info("Looking for an all users in a database");
		return userDAO.findAll();
	}
	
	@Transactional
	public void update(User user) {
		log.info("Updating user data in the database " + user.toString());
		userDAO.save(user);
	}

	@Transactional
	public void delete(Long id) {
		log.info("Delete user with id " + id);
		userDAO.deleteById(id);
	}
}
