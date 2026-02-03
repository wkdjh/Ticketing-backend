package Test.Toyproject.user.service;

import Test.Toyproject.reservation.entity.Reservation;
import Test.Toyproject.show.entity.Show;
import Test.Toyproject.show.repository.ShowRepository;
import Test.Toyproject.user.dto.response.MyReservationDetailResponse;
import Test.Toyproject.user.dto.response.MyReservedResponse;
import Test.Toyproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final ShowRepository showRepository;

    // 내 예약내역을 불러오기 위한 responseDto가 따로 필요한가? -> 일단 만듬
    // 예매 내역이 없을 수도 있으니 repo를 Optional로 했는데.. optional 하면 안 되네

    // 내가 예약한 공연 목록
    public List<MyReservedResponse> getMyReserved() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var userDetails = (CustomUserDetailsService.CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getId();

        List<Show> shows = userRepository.findShowsOrderedByLatestReservation(userId);

        return shows.stream()
                .map(MyReservedResponse::from)
                .toList();
    }

    // 내가 예약한 공연 누르면 예약 정보(좌석) 확인
    @Transactional(readOnly = true)
    public List<MyReservationDetailResponse> getMyReservationsByShow(Long showId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var userDetails = (CustomUserDetailsService.CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getId();

        List<Reservation> reservations =
                userRepository.findMyReservationsByShowId(userId, showId);

        return reservations.stream()
                .map(r -> MyReservationDetailResponse.from(r.getShow(), r.getSeats()))
                .toList();
    }

}
