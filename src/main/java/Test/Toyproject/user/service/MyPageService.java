package Test.Toyproject.user.service;

import Test.Toyproject.reservation.entity.Reservation;
import Test.Toyproject.reservation.repository.ReservationRepository;
import Test.Toyproject.seats.entity.Seats;
import Test.Toyproject.seats.repository.SeatRepository;
import Test.Toyproject.show.entity.Show;
import Test.Toyproject.show.repository.ShowRepository;
import Test.Toyproject.user.dto.response.MyReservedResponse;
import Test.Toyproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;

    // 내 예약내역을 불러오기 위한 responseDto가 따로 필요한가? -> 일단 만듬
    // 예매 내역이 없을 수도 있으니 repo를 Optional로 했는데.. optional 하면 안 되네

    // 내가 예약한 공연 목록
    public List<MyReservedResponse> getMyReserved() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var userDetails = (CustomUserDetailsService.CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getId();

        // 1) 최신 예약 순으로 show 목록 (너가 이미 가지고 있는 쿼리)
        List<Show> showsOrdered = userRepository.findShowsOrderedByLatestReservation(userId);

        // 2) 내 예약 전체 조회 (show + seats 까지 한 번에 가져오면 더 좋음)
        List<Reservation> reservations = reservationRepository.findByUserIdWithShowAndSeats(userId);

        // 3) showId 기준으로 seats 묶기
        Map<Long, List<Reservation>> reservationsByShowId = reservations.stream()
                .collect(Collectors.groupingBy(r -> r.getShow().getId()));

        // dto 변환
        return showsOrdered.stream()
                .filter(show -> reservationsByShowId.containsKey(show.getId()))
                .map(show -> MyReservedResponse.from(show, reservationsByShowId.get(show.getId())))
                .toList();
    }

}
