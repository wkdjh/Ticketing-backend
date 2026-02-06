package Test.Toyproject.show.dto;

import Test.Toyproject.show.entity.Show;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GetShowResponseDto {
    private Long id;
    private String title;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String location;
    private String posterURL;
    private int price;
    private LocalDateTime createdAt;

    // entity -> dto 변환
    // 위에 선언한 변수의 개수와 타입과 일치해야 됨
    public static GetShowResponseDto from(Show show) {
        return new GetShowResponseDto(
                show.getId(),
                show.getTitle(),
                show.getStartDateTime(),
                show.getEndDateTime(),
                show.getLocation(),
                show.getPosterURL(),
                show.getPrice(),
                show.getCreatedAt()
        );
    }
}
