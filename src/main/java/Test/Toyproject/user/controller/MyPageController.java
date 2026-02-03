package Test.Toyproject.user.controller;

import Test.Toyproject.common.dto.ApiResponse;
import Test.Toyproject.user.dto.response.MyReservationDetailResponse;
import Test.Toyproject.user.dto.response.MyReservedResponse;
import Test.Toyproject.user.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/show/myList")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping()
    public ResponseEntity<ApiResponse<List<MyReservedResponse>>> getMyReservations() {
        List<MyReservedResponse> result = myPageService.getMyReserved();
        return ResponseEntity.ok(ApiResponse.ok("예매 내역 조회 성공", result));
    }

    @GetMapping("/{showId}")
    public ResponseEntity<ApiResponse<List<MyReservationDetailResponse>>> myReservationsByShow(
            @PathVariable Long showId
    ) {
        List<MyReservationDetailResponse> result = myPageService.getMyReservationsByShow(showId);
        return ResponseEntity.ok(ApiResponse.ok("예매 내역 조회 성공", result));
    }



}
