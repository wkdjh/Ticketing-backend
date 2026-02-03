package Test.Toyproject.user.service;

import Test.Toyproject.user.entity.User;
import Test.Toyproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetails loadUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return CustomUserDetails.from(user);
    }

    public static class CustomUserDetails implements UserDetails {

        private final Long id;
        private final String email;
        private final String password;
        private final String nickName;

        private CustomUserDetails(Long id, String email, String password, String nickName) {
            this.id = id;
            this.email = email;
            this.password = password;
            this.nickName = nickName;
        }

        public static CustomUserDetails from(User user) {
            return new CustomUserDetails(
                    user.getId(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getNickName()
            );
        }

        public Long getId() { return id; }
        public String getNickName() { return nickName; }

        @Override
        public List<SimpleGrantedAuthority> getAuthorities() {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }

        @Override public String getPassword() { return password; }
        @Override public String getUsername() { return email; }

        @Override public boolean isAccountNonExpired() { return true; }
        @Override public boolean isAccountNonLocked() { return true; }
        @Override public boolean isCredentialsNonExpired() { return true; }
        @Override public boolean isEnabled() { return true; }
    }



}
