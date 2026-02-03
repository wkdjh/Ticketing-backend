package Test.Toyproject.user.dto.response;

import Test.Toyproject.reservation.entity.Reservation;
import Test.Toyproject.seats.entity.Seats;
import Test.Toyproject.show.entity.Show;

public record MyReservationDetailResponse (
        char seat_row,
        int seat_column,
        int price
) {
    public static MyReservationDetailResponse from(Show show, Seats seats) {
        return new MyReservationDetailResponse(
                seats.getSeat_row(),
                seats.getSeat_column(),
                show.getPrice()
        );
    }
}

