package Test.Toyproject.seats.repository;

import Test.Toyproject.seats.entity.Seats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seats, Long> {

    // mid, seatRow, seatColumn으로 sid 찾기
    Optional<Seats> findByShowIdAndSeatRowAndSeatColumn(Long mid, char seatRow, int seatColumn);
}
