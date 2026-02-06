package Test.Toyproject.seats.controller;

import Test.Toyproject.common.dto.ApiResponse;
import Test.Toyproject.seats.dto.SeatStatusResponseDto;
import Test.Toyproject.seats.service.SeatService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;

    // 공연 당 좌석 정보 조회
    @GetMapping("/show/reservations/seats")
    public ApiResponse<List<SeatStatusResponseDto>> getReservedSeats(@RequestParam Long showId) {
        List<SeatStatusResponseDto> data = seatService.getTakenSeats(showId);
        return ApiResponse.ok("예약된 좌석 조회 성공", data);
    }


}
