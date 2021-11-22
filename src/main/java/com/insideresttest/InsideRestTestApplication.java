package com.insideresttest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @author hanza
 * Проект демонстрирует REST API для получения и хранения в БД сообщений от авторизованного пользователя.
 * Перед отправкой сообщения в API пользователь должен пройти аутентификацию и получить jwt токен.
 * Таже авторизованный пользователь может получить историю своих сообщений.
 */
@SpringBootApplication
public class InsideRestTestApplication {
	public static void main(String[] args) {
		SpringApplication.run(InsideRestTestApplication.class, args);
	}

}
