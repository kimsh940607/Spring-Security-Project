package hello.login.web.login;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class LoginForm {

	@NotNull
	private String loginId;
	
	@NotNull
	private String password;
}
