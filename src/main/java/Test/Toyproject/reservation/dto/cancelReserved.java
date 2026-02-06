package Test.Toyproject.reservation.dto;

import java.util.List;

public record cancelReserved (
        List<Long> reservationIds
) {}

