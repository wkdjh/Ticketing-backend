package Test.Toyproject.reservation.repository;

import Test.Toyproject.reservation.entity.Reservation;
import Test.Toyproject.seats.entity.Seats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // 예매하기, save는 그냥 주어지는구나?

    // 이미 예약이 되어있는지 확인
    boolean existsByShowIdAndSeatsId(Long mid, Long sid);

    // 예매 취소하기 위한 예약 목록 찾기
    Optional<Reservation> findByIdAndUserId(Long reservationId, Long userId);

    // 내 예매 내역 조회
    @Query("""
        select r from Reservation r
        join fetch r.show
        join fetch r.seats
        where r.user.id = :userId
    """)
    List<Reservation> findByUserIdWithShowAndSeats(@Param("userId") Long userId);

    // 내가 예매한 공연의 모든 예약,좌석 찾기
    @Query("""
        select r from Reservation r
        join fetch r.show
        join fetch r.user
        join fetch r.seats
        where r.id = :reservationId
    """)
    Optional<Reservation> findOneWithAll(@Param("reservationId") Long reservationId);

    // 같은 유저 + 같은 공연의 예약 전부 삭제(한 번에)
    long deleteByUser_IdAndShow_Id(Long userId, Long showId);
}
