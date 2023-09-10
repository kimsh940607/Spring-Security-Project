package hello.login.web.login;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.LoginConst;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;
	private final SessionManager sessionManager;
	
	@GetMapping("/login")
	public String login(@ModelAttribute("loginForm") LoginForm loginForm) {
		return "login/loginForm";
	}
	
//	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, HttpServletResponse response) {
		if(bindingResult.hasErrors()) {
			return "login/loginForm";
		}
		Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
		if(loginMember == null) {
			bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다");
			return "login/loginForm";
		}
		
		// 로그인 성공 처리 TODO
		Cookie isCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
		// 세션 쿠키 response에 add
		response.addCookie(isCookie);
		
		return "redirect:/";
	}
	
//	@PostMapping("/login")
	public String loginV2(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, HttpServletResponse response) {
		if(bindingResult.hasErrors()) {
			return "login/loginForm";
		}
		Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
		if(loginMember == null) {
			bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다");
			return "login/loginForm";
		}
		
		// 로그인 성공 처리 TODO
		sessionManager.createSession(loginMember, response);

		return "redirect:/";
	}

	@PostMapping("/login")
	public String loginV3(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {
		if(bindingResult.hasErrors()) {
			return "login/loginForm";
		}
		Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
		if(loginMember == null) {
			bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다");
			return "login/loginForm";
		}
		
		// 로그인 성공 처리 TODO
//		sessionManager.createSession(loginMember, response);
		HttpSession session = request.getSession(true);
		session.setAttribute(LoginConst.SESSION_NAME, loginMember);

		return "redirect:/";
	}
	
//	@PostMapping("/logout")
	public String logout(HttpServletResponse response) {
		this.expiredCookie(response, "memberId");
		return "redirect:/";
	}

//	@PostMapping("/logout")
	public String logoutV2(HttpServletRequest request) {
		sessionManager.expire(request);
		return "redirect:/";
	}

	@PostMapping("/logout")
	public String logoutV3(HttpServletRequest request) {
//		sessionManager.expire(request);
		HttpSession session = request.getSession(false);
		if(session != null) {
			session.invalidate();
		}
		return "redirect:/";
	}
	
	private void expiredCookie(HttpServletResponse response, String cookieName) {
		Cookie cookie = new Cookie(cookieName, null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
}
