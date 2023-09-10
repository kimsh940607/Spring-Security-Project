package hello.login.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

	private final MemberRepository memberRepository;
	private final SessionManager sessionManager;
	
    // @GetMapping("/")
    public String home() {
        return "home";
    }
    
//    @GetMapping("/")
    public String homeLogin(HttpServletRequest request, Model model) {
    	Member memeber = (Member) sessionManager.getSession(request);
        if(memeber == null) { // memberId가 없다는건 addCookie가 되지 않음
        	return "home";
        }
        model.addAttribute("member", memeber);
        return "loginHome";
    }

    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {
    	HttpSession session = request.getSession(false);
    	if(session == null) { // memberId가 없다는건 addCookie가 되지 않음
    		return "home";
    	}
    	Member member = (Member) session.getAttribute(LoginConst.SESSION_NAME);
    	model.addAttribute("member", member);
    	return "loginHome";
    }
}