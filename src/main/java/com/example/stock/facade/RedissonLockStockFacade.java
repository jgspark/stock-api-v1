package com.example.stock.facade;

import com.example.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedissonLockStockFacade {

    private final RedissonClient redissonClient;

    private final StockService stockService;

    public void decrease(Long stockId, Long quantity) {
        RLock lock = redissonClient.getLock(stockId.toString());

        try {

            // 5s
            boolean isLockAble = lock.tryLock(5, 1, TimeUnit.SECONDS);

            if (isNotLockAble(isLockAble)) {
                log.info("lock is fail");
                return;
            }

            stockService.decrease(stockId, quantity);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    private boolean isNotLockAble(boolean isLock) {
        return !isLock;
    }
}
