package Test.Toyproject.reservation.service;

import Test.Toyproject.reservation.dto.ReservationRequestDto;
import Test.Toyproject.reservation.entity.Reservation;
import Test.Toyproject.reservation.repository.ReservationRepository;
import Test.Toyproject.seats.entity.Seats;
import Test.Toyproject.seats.repository.SeatRepository;
import Test.Toyproject.show.entity.Show;
import Test.Toyproject.show.repository.ShowRepository;
import Test.Toyproject.user.entity.User;
import Test.Toyproject.user.repository.UserRepository;
import Test.Toyproject.user.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public Long reservedShow(ReservationRequestDto requestDto, Long uid) {
        // reservation에 mid랑 uid, sid가 저장 되어야 됨.

        char row = requestDto.seatRow().charAt(0);

        Seats seat = seatRepository
                .findByShowIdAndSeatRowAndSeatColumn(
                        requestDto.mid(),
                        row,                    // ✅ char 전달
                        requestDto.seatColumn()
                )
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 좌석입니다."));

        Show show = showRepository.findById(requestDto.mid())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공연입니다."));

        User user = userRepository.findById(uid)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (reservationRepository.existsByShowIdAndSeatsId(show.getId(), seat.getId())) {
            throw new IllegalStateException("이미 예약된 좌석입니다.");
        }

        try {
            Reservation saved = reservationRepository.save(Reservation.of(show, user, seat));
            return saved.getId();
        } catch (DataIntegrityViolationException e) {
            // 동시에 들어온 경우(유니크 제약 필요)
            throw new IllegalStateException("이미 예약된 좌석입니다.");
        }
    }

}
