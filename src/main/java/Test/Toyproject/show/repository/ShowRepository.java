package Test.Toyproject.show.repository;

import Test.Toyproject.show.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
왜 extends를 하는 거임?
 */
@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
    // 최신순 가져오기
    List<Show> findAllByOrderByCreatedAtDesc();

    // 조회수? 예약 많은 순 가져오기
    @Query("""
    select s
    from Show s
    left join Reservation r on r.show = s
    group by s
    order by count(r) desc
    """)
    List<Show> findAllOrderByReservationCountDesc();

    // 공연 임박 순 가져오기
    List<Show> findAllByOrderByStartDateTimeAsc();

    // 나의 예매 공연 정보 가져오기
    List<Show> findByIdIn(List<Long> ids);
}
