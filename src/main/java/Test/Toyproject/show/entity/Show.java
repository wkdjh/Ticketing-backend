package Test.Toyproject.show.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.lang.annotation.Target;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "shows")
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String posterURL;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Show(Long id, String title, String posterURL, String location, LocalDateTime startDateTime, LocalDateTime endDateTime, int price, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.posterURL = posterURL;
        this.location = location;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.price = price;
        this.createdAt = createdAt;
    }
}
