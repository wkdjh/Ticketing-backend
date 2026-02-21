package Test.Toyproject.seats.repository;

import Test.Toyproject.seats.entity.SeatStatus;
import Test.Toyproject.seats.entity.Seats;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seats, Long> {

    // mid, seatRow, seatColumn으로 sid 찾기
    @Lock(LockModeType.PESSIMISTIC_WRITE) // 배타 락
    Optional<Seats> findByShow_IdAndSeatRowAndSeatColumn(Long mid, char seatRow, int seatColumn);

    // 현재 taken인 좌석 정보 불러오기
    List<Seats> findByShow_IdAndSeatStatus(Long showId, SeatStatus seatStatus);


}
