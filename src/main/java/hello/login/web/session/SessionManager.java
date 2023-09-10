package hello.login.web.session;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * 세션 관리
 */
@Component
public class SessionManager {
	
	public static final String SESSION_COOKIE_NAME = "mySessionid";
	
	private Map<String, Object> store = new ConcurrentHashMap<>();
	
	/*
	 * 세션 생성 
	 */
	public void createSession(Object value, HttpServletResponse response) {
		// 세션 id를 생성하고, 값을 세션에 저장
		String sessionId = UUID.randomUUID().toString();
		store.put(sessionId, value);
		
		// 쿠키 생성
		Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
		response.addCookie(cookie);
	}
	/*
	 * 세션 조회
	 */
	public Object getSession(HttpServletRequest request) {
		Cookie cookie = this.findCookie(request, SESSION_COOKIE_NAME);
		if(cookie == null) {
			return null;
		}
		return store.get(cookie.getValue());
	}
	
	/*
	 * 세션 만료
	 */
	public void expire(HttpServletRequest request) {
		Cookie cookie = this.findCookie(request, SESSION_COOKIE_NAME);
		if(cookie != null) {
			store.remove(cookie.getValue());
		}
	}
	
	private Cookie findCookie(HttpServletRequest request, String cookieName) {
		Cookie [] cookies = request.getCookies();
		if(cookies == null) {
			return null;
		}
		return Arrays.stream(cookies).filter(m -> m.getName().equals(cookieName)).findFirst().orElse(null);
	}
}
