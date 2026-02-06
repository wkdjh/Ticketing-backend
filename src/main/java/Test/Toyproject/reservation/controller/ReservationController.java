package Test.Toyproject.reservation.controller;

import Test.Toyproject.reservation.dto.ReservationRequestDto;
import Test.Toyproject.reservation.dto.ReservationResponseDto;
import Test.Toyproject.reservation.dto.cancelReserved;
import Test.Toyproject.reservation.service.ReservationService;
import Test.Toyproject.user.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/show/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    // 공연 예매
    @PostMapping
    public ResponseEntity<ReservationResponseDto> reserve(
            @RequestBody ReservationRequestDto req,
            @AuthenticationPrincipal CustomUserDetailsService.CustomUserDetails userDetails
    ) {

        Long uid = userDetails.getId();

        Long reservationId = reservationService.reservedShow(req, uid);
        return ResponseEntity.ok(new ReservationResponseDto(reservationId));
    }

    @DeleteMapping
    public ResponseEntity<Void> cancelAll(
            @RequestBody cancelReserved req,
            @AuthenticationPrincipal CustomUserDetailsService.CustomUserDetails userDetails
    ) {
        reservationService.cancelReserved(req.reservationIds(), userDetails.getId());
        return ResponseEntity.noContent().build();
    }

}
