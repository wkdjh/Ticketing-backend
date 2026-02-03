package Test.Toyproject.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {
    String accessToken;
    String refreshToken;
    Long userId;
    String email;
    String nickName;
}
