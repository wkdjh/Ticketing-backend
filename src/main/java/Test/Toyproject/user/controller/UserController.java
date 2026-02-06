package Test.Toyproject.user.controller;

import Test.Toyproject.common.dto.ApiResponse;
import Test.Toyproject.user.dto.request.LoginRequest;
import Test.Toyproject.user.dto.request.SignUpRequest;
import Test.Toyproject.user.dto.response.AuthResponse;
import Test.Toyproject.user.dto.response.UserMeResponseDto;
import Test.Toyproject.user.repository.UserRepository;
import Test.Toyproject.user.service.AuthService;
import Test.Toyproject.user.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/show/auth")
public class UserController {
    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody SignUpRequest req) {

//        // 이메일 중복 확인
//        if (userRepository.existsByEmail(req.getEmail())) {
//            throw new EmailAlreadyExistException();
//        }
//
//        // 닉네임 중복 확인
//        if (userRepository.existsByNickname(req.getNickName())) {
//            throw new NicknameAlreadyExistException();
//        }
        authService.signup(req);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
        AuthResponse auth = authService.login(request);

        // auth -> ApiResponse의 data 부분
        return ResponseEntity.ok(ApiResponse.ok("로그인 성공", auth));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserMeResponseDto>> me(
            @AuthenticationPrincipal CustomUserDetailsService.CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getId();
        UserMeResponseDto dto = authService.me(userId);
        return ResponseEntity.ok(ApiResponse.ok("내 정보 조회 성공", dto));
    }

}
