package Test.Toyproject.seats.repository;

import Test.Toyproject.seats.entity.SeatStatus;
import Test.Toyproject.seats.entity.Seats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seats, Long> {

    // mid, seatRow, seatColumn으로 sid 찾기
    Optional<Seats> findByShow_IdAndSeatRowAndSeatColumn(Long mid, char seatRow, int seatColumn);

    // 현재 taken인 좌석 정보 불러오기
    List<Seats> findByShow_IdAndSeatStatus(Long showId, SeatStatus seatStatus);


}
