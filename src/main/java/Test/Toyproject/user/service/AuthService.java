package Test.Toyproject.user.service;

import Test.Toyproject.jwt.JwtProvider;
import Test.Toyproject.user.dto.request.LoginRequest;
import Test.Toyproject.user.dto.request.SignUpRequest;
import Test.Toyproject.user.dto.response.AuthResponse;
import Test.Toyproject.user.entity.User;
import Test.Toyproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public void signup(SignUpRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 가입된 이메일입니다.");
        }
        if(userRepository.existsByNickName(request.getNickName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 사용중인 닉네임입니다.");
        }

        String encoded = passwordEncoder.encode(request.getPassword());

        User user = new User(
            request.getEmail(),
            encoded,
            request.getNickName()
        );

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일이 올바르지 않습니다."));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 올바르지 않습니다.");
        }

        String access = jwtProvider.createAccessToken(user.getId(), user.getEmail(), user.getNickName());
        String refresh = jwtProvider.createRefreshToken(user.getId());

        return new AuthResponse(access, refresh, user.getId(), user.getEmail(), user.getNickName());
    }
}
