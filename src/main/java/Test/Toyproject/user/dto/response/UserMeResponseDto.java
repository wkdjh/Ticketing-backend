package Test.Toyproject.user.dto.response;

public record UserMeResponseDto (
    Long userId,
    String email,
    String nickName
) {}
