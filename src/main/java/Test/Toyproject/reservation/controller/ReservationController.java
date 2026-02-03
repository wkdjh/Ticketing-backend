package Test.Toyproject.reservation.controller;

import Test.Toyproject.reservation.dto.ReservationRequestDto;
import Test.Toyproject.reservation.service.ReservationService;
import Test.Toyproject.user.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/show/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponseDto> reserve(
            @RequestBody ReservationRequestDto req,
            @AuthenticationPrincipal CustomUserDetailsService.CustomUserDetails userDetails
    ) {

        Long uid = userDetails.getId();

        Long reservationId = reservationService.reservedShow(req, uid);
        return ResponseEntity.ok(new ReservationResponseDto(reservationId));
    }

    public record ReservationResponseDto(Long reservationId) {}

}
