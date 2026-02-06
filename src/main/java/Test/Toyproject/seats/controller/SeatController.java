package Test.Toyproject.seats.controller;

import Test.Toyproject.common.dto.ApiResponse;
import Test.Toyproject.seats.dto.SeatStatusResponseDto;
import Test.Toyproject.seats.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;

    @GetMapping("/show/reservations/seats")
    public List<SeatStatusResponseDto> getReservedSeats(@RequestParam Long showId) {
        return seatService.getTakenSeats(showId);
    }


}
