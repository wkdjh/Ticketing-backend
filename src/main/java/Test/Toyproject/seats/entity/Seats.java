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

    @Column(nullable = false)
    private char seat_row;

    @Column(nullable = false)
    private int seat_column;

    // Show의 id랑 fk
    /*

     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mid", nullable = false)
    private Show show;
}
