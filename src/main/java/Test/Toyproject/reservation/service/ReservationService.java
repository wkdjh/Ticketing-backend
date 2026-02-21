package Test.Toyproject.reservation.service;

import Test.Toyproject.reservation.dto.ReservationRequestDto;
import Test.Toyproject.reservation.entity.Reservation;
import Test.Toyproject.reservation.repository.ReservationRepository;
import Test.Toyproject.seats.entity.SeatStatus;
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

import java.util.List;

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
                .findByShow_IdAndSeatRowAndSeatColumn(
                        requestDto.mid(),
                        row,                    // char 전달
                        requestDto.seatColumn()
                )
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 좌석입니다."));

        // DB seat_status 기준으로 먼저 검사
        if (seat.getSeatStatus() == SeatStatus.TAKEN) {
            throw new IllegalStateException("이미 예약된 좌석입니다.");
        }

        Show show = showRepository.findById(requestDto.mid())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공연입니다."));

        User user = userRepository.findById(uid)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 좌석 상태 TAKEN으로 변경
        seat.take();

        // 예약 저장
        Reservation saved = reservationRepository.save(Reservation.of(show, user, seat));

        return saved.getId();
    }

    @Transactional
    public void cancelReserved(List<Long> reservationIds, Long uid) {

        if (reservationIds == null || reservationIds.isEmpty()) {
            throw new IllegalArgumentException("취소할 예약이 없습니다.");
        }

        List<Reservation> reservations = reservationRepository.findAllByIdInAndUserId(reservationIds, uid);

        // 내 예약 아닌 id가 섞였거나, 이미 삭제된 id가 있으면 걸러짐 → 개수로 검증
        if (reservations.size() != reservationIds.size()) {
            throw new IllegalArgumentException("예약이 없거나 본인 예약이 아닌 항목이 포함되어 있습니다.");
        }

        // 좌석 원복
        for (Reservation r : reservations) {
            Seats seat = r.getSeats();
            seat.release(); // AVAILABLE
            // Seats가 Reservation에 의해 cascade로 저장되지 않는 구조면 seatRepository.save(seat) 해줘야 안전
            // seatRepository.save(seat);
        }

        // 예약 삭제 (한방)
        reservationRepository.deleteAll(reservations);
    }

}
