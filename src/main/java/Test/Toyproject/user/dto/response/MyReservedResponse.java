package Test.Toyproject.user.dto.response;

import Test.Toyproject.reservation.entity.Reservation;
import Test.Toyproject.show.entity.Show;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

public record MyReservedResponse (
    String title,
    LocalDateTime startDateTime,
    String location,
    String posterURL
) {
    public static MyReservedResponse from(Show show) {
        return new MyReservedResponse(
                show.getTitle(),
                show.getStartDateTime(),
                show.getLocation(),
                show.getPosterURL()
        );
    }
}
