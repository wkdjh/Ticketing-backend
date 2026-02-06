package Test.Toyproject.user.dto.response;

import Test.Toyproject.reservation.entity.Reservation;
import Test.Toyproject.seats.entity.Seats;
import Test.Toyproject.show.entity.Show;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public record MyReservedResponse (
    List<Long> reservationIds,
    String title,
    LocalDateTime startDateTime,
    String location,
    String posterURL,
    int peopleCount,
    List<SeatInfoResponse> seats,
    int price
) {
    public static MyReservedResponse from(Show show, List<Long> reservationIds, List<Seats> seatsList) {

        int peopleCount = seatsList.size();
        int totalPrice = show.getPrice() * peopleCount;

        return new MyReservedResponse(
                reservationIds,
                show.getTitle(),
                show.getStartDateTime(),
                show.getLocation(),
                show.getPosterURL(),
                peopleCount,
                seatsList.stream()
                        .map(s -> new SeatInfoResponse(
                                String.valueOf(s.getSeatRow()), // char -> String 바꾸기
                                s.getSeatColumn()
                        ))
                        .toList(),
                totalPrice
        );
    }
}
