package hello.login.web.session;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import hello.login.domain.member.Member;

class SessionManagerTest {

	SessionManager sessionManager = new SessionManager();

	@Test
	void sessionTest() {
		// 세션 생성
		MockHttpServletResponse response = new MockHttpServletResponse();
		Member member = new Member();
		sessionManager.createSession(member, response);
		
		// 요청에 응답 쿠키 저장 -> setCookie
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setCookies(response.getCookies());
		
		// 세션 조회
		Object findSession = sessionManager.getSession(request);
	
		Assertions.assertThat(findSession).isEqualTo(member);
		
		// 세션 만료
		sessionManager.expire(request);
		// 만료 후 세션 조회
		Object expireSession = sessionManager.getSession(request);
		
		Assertions.assertThat(expireSession).isNull();
	}
}
