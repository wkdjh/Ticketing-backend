package Test.Toyproject.reservation.dto;


import Test.Toyproject.seats.entity.Seats;
import Test.Toyproject.show.entity.Show;

public record ReservationRequestDto(
        Long mid,
        String seatRow,
        int seatColumn
) {
    // from은 responsedto에서 dto -> json할 때 사용
//    public static ReservationRequestDto from(Seats seats, Show show) {
//        return new ReservationRequestDto(
//                show.getId(),
//                seats.getId(),
//                seats.getSeat_row(),
//                seats.getSeat_column()
//        );
//    }
}
