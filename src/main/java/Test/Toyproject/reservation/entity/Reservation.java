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

    protected Reservation() {} // JPA 기본 생성자

    public Reservation(Show show, User user, Seats seats) {
        this.show = show;
        this.user = user;
        this.seats = seats;
    }

    // 취향: 더 명확하게 하고 싶으면 factory로
    public static Reservation of(Show show, User user, Seats seats) {
        return new Reservation(show, user, seats);
    }
}
