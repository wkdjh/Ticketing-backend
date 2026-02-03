package Test.Toyproject.user.dto.request;

import lombok.Getter;

@Getter
public class SignUpRequest {
    private String email;
    private String password;
    private String nickName;
}
