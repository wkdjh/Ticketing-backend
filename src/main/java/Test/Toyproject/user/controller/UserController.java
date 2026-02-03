package Test.Toyproject.user.controller;

import Test.Toyproject.common.dto.ApiResponse;
import Test.Toyproject.user.dto.request.LoginRequest;
import Test.Toyproject.user.dto.request.SignUpRequest;
import Test.Toyproject.user.dto.response.AuthResponse;
import Test.Toyproject.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/show/auth")
public class UserController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody SignUpRequest req) {
        authService.signup(req);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
        AuthResponse auth = authService.login(request);

        // auth -> ApiResponse의 data 부분
        return ResponseEntity.ok(ApiResponse.ok("로그인 성공", auth));
    }
}
