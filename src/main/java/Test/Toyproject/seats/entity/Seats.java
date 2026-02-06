package Test.Toyproject.seats.entity;

import Test.Toyproject.show.entity.Show;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Seats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "seat_row")
    private char seatRow;

    @Column(nullable = false, name = "seat_column")
    private int seatColumn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus seatStatus;

    // Show의 id랑 fk
    /*

     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mid", nullable = false)
    private Show show;

    public void take() {
        this.seatStatus = SeatStatus.TAKEN;
    }

    public void release() {
        this.seatStatus = SeatStatus.AVAILABLE;
    }
}
