package Test.Toyproject.user.repository;

import Test.Toyproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 이메일 중복 체크(회원가입)
    boolean existsByEmail(String email);

    // 이메일 확인(로그인)
    Optional<User> findByEmail(String email);

    // 닉네임 중복 체크
    boolean existsByNickName(String nickName);

    // 이건 필요없나?
    Optional<User> findByNickName(String nickName);

}
