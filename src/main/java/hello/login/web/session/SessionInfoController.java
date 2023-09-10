package hello.login.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class SessionInfoController {

	@GetMapping("/session-info")
	public String sesionInfo(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session == null) {
			return "세션 없음";
		}
		session.getAttributeNames()
				.asIterator()
				.forEachRemaining(
					m -> log.info("session name = {}, value = {}", m, session.getAttribute(m)));
	
		log.info("sessionId = {}", session.getId());
		log.info("accessedTime = {}", session.getLastAccessedTime());
		log.info("maxInactiveInterval = {}", session.getMaxInactiveInterval());
		log.info("createTime = {}", session.getCreationTime());
		log.info("isNew = {}", session.isNew());
		
		return "세션 출력";
	}
}
