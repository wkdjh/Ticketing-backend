package Test.Toyproject.reservation.service;

import Test.Toyproject.show.repository.ShowRepository;
import Test.Toyproject.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import Test.Toyproject.reservation.dto.ReservationRequestDto;
import Test.Toyproject.seats.entity.SeatStatus;
import Test.Toyproject.seats.entity.Seats;
import Test.Toyproject.seats.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
//  동시성 테스트 용, 같은 공연 좌석을 여러 사람이 동시에 예매하는 경우
@SpringBootTest
public class ReservationConcurrencyTest {
    @Autowired ReservationService reservationService;
    @Autowired SeatRepository seatRepository;
    @Autowired UserRepository userRepository;
    @Autowired ShowRepository showRepository;
    @Autowired
    PlatformTransactionManager txManager;


    @Test
    void should_allow_only_one_reservation_for_same_seat_uid9_uid10_show1() throws Exception {
        Long mid = 1L;
        Long uid1 = 9L;
        Long uid2 = 10L;
        char row = 'A';
        int col = 1;

        TransactionTemplate tt = new TransactionTemplate(txManager);

        // 초기화는 여기서 "짧게" 트랜잭션 커밋하고 끝낸다
        tt.execute(status -> {
            Seats seat = seatRepository.findByShow_IdAndSeatRowAndSeatColumn(mid, row, col)
                    .orElseThrow(() -> new IllegalArgumentException("좌석 없음"));
            seat.makeAvailable();
            seatRepository.saveAndFlush(seat);
            return null;
        });

        ReservationRequestDto req = new ReservationRequestDto(mid, "A", col);

        int threadCount = 2;
        ExecutorService pool = Executors.newFixedThreadPool(threadCount);

        CountDownLatch ready = new CountDownLatch(threadCount);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done  = new CountDownLatch(threadCount);

        AtomicInteger success = new AtomicInteger(0);
        AtomicInteger fail = new AtomicInteger(0);

        Runnable t1 = () -> {
            ready.countDown();
            try { start.await(); } catch (InterruptedException ignored) {}
            try {
                reservationService.reservedShow(req, uid1);
                success.incrementAndGet();
            } catch (Exception e) {
                e.printStackTrace();
                fail.incrementAndGet();
            } finally {
                done.countDown();
            }
        };

        Runnable t2 = () -> {
            ready.countDown();
            try { start.await(); } catch (InterruptedException ignored) {}
            try {
                reservationService.reservedShow(req, uid2);
                success.incrementAndGet();
            } catch (Exception e) {
                e.printStackTrace();
                fail.incrementAndGet();
            } finally {
                done.countDown();
            }
        };

        pool.submit(t1);
        pool.submit(t2);

        ready.await();
        start.countDown();
        done.await();
        pool.shutdown();

        System.out.println("success=" + success.get() + ", fail=" + fail.get());

        assertThat(success.get()).isEqualTo(1);
        assertThat(fail.get()).isEqualTo(1);
    }
}
