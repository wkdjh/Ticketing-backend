package Test.Toyproject.user.repository;

import Test.Toyproject.reservation.entity.Reservation;
import Test.Toyproject.show.entity.Show;
import Test.Toyproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 이메일 중복 체크(회원가입)
    boolean existsByEmail(String email);

    // 이메일 확인(로그인)
    Optional<User> findByEmail(String email);

    // 닉네임 중복 체크
    boolean existsByNickName(String nickName);

    // 나의 예매 내역 가져오기(최근 예약 순)
    @Query("""
    select r.show
    from Reservation r
    where r.user.id = :userId
    order by r.id desc
    """)
    List<Show> findShowsOrderedByLatestReservation(@Param("userId") Long userId);

    // 디테일한 예매 정보 가져오기
    @Query("""
        select r
        from Reservation r
        join fetch r.seats s
        join fetch r.show sh
        where r.user.id = :userId
          and sh.id = :showId
        order by r.id desc
    """)
    List<Reservation> findMyReservationsByShowId(
            @Param("userId") Long userId,
            @Param("showId") Long showId
    );



    // 이건 필요없나?
//    Optional<User> findByNickName(String nickName);

}
