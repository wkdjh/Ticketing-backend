package Test.Toyproject.reservation.entity;

import Test.Toyproject.seats.entity.Seats;
import Test.Toyproject.show.entity.Show;
import Test.Toyproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "Reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mid", nullable = false)
    private Show show;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "uid", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sid", nullable = false)
    private Seats seats;

}
