package com.insideresttest.service;

import java.security.Key;
import java.sql.Date;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.insideresttest.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Класс для генерации jwt токена и его проверки
 * @author hanza
 *
 */
@Service
public class JwtService {
	private String jwtSecret; //секрет для генерации токена, берется из application.properties
	private static final Logger log = LoggerFactory.getLogger(JwtService.class);
	
	private JwtService() {}
	
	@Autowired
	public JwtService(@Value("${jwt.secret}") String jwtSecret) {
		this.jwtSecret = jwtSecret;
	}
	
	/**
	 * Метод, генерирующий jwt токен
	 * @param user - объект пользователя, для которого генерируется токен
	 * @return возвращается сгенерированный токен
	 */
	public String generateToken(User user) {
		log.info("Generate token...");
		log.info("Secret :  " + jwtSecret);
		Date date = Date.valueOf(LocalDate.now().plusDays(15));
		String clientName = user.getName();
		return Jwts.builder().setSubject(clientName).setExpiration(date).signWith(getKeyFromSecret()).compact();
	}
	
	/**
	 * Метод, получающий имя пользователя из токена
	 * @param token - строка с токеном
	 * @return возвращает имя пользователя
	 */
	public String getClientNameFromToken (String token) {
		return Jwts.parserBuilder().setSigningKey(getKeyFromSecret()).build().parseClaimsJws(token).getBody().getSubject();
	}
	
	/**
	 * Метод проверяет токен на валидность
	 * @param token - строка с токеном
	 * @return возвращает статус проверки и ошибку при ее возникновении
	 */
	public boolean validateToken (String token) {
		try {
			Jwts.parserBuilder().setSigningKey(getKeyFromSecret()).build().parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException expEx) {
            log.error("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt");
        } catch (Exception e) {
            log.error("invalid token");
        }
        return false;
	}
	
	/**
	 * Метод получет ключ для подписи токена из секрета, хранящегося в application.properties
	 * @return возвращает ключ для подписи токена
	 */
	private Key getKeyFromSecret() {
		return Keys.hmacShaKeyFor(jwtSecret.getBytes());
	}
}
