package com.insideresttest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.insideresttest.model.User;

/**
 * Интерфейс для работы с базой
 * @author hanza
 *
 */
@Repository
public interface UserDAO extends JpaRepository<User, Long>{
	public User findByToken(String token);
}
