package Test.Toyproject.reservation.controller;

import Test.Toyproject.reservation.dto.ReservationRequestDto;
import Test.Toyproject.reservation.dto.ReservationResponseDto;
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

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> cancel(
            @PathVariable Long reservationId,
            @AuthenticationPrincipal CustomUserDetailsService.CustomUserDetails userDetails
    ) {
        Long uid = userDetails.getId();
        reservationService.cancelReserved(reservationId, uid);
        return ResponseEntity.noContent().build(); // ✅ 204 No Content
    }

}
