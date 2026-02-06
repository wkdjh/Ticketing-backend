package Test.Toyproject.seats.service;

import Test.Toyproject.seats.dto.SeatStatusResponseDto;
import Test.Toyproject.seats.entity.SeatStatus;
import Test.Toyproject.seats.entity.Seats;
import Test.Toyproject.seats.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;

    @Transactional(readOnly = true)
    public List<SeatStatusResponseDto> getTakenSeats(Long showId) {
        System.out.println("getTakenSeats showId=" + showId);

        List<Seats> seats = seatRepository.findByShow_IdAndSeatStatus(showId, SeatStatus.TAKEN);
        System.out.println("taken seats size=" + seats.size());

        return seats.stream()
                .map(s -> new SeatStatusResponseDto(String.valueOf(s.getSeatRow()), s.getSeatColumn()))
                .toList();
//        return seatRepository.findByShow_IdAndSeatStatus(showId, SeatStatus.TAKEN)
//                .stream()
//                .map(s -> new SeatStatusResponseDto(String.valueOf(s.getSeatRow()), s.getSeatColumn()))
//                .toList();
    }
}
