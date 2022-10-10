package com.example.stock.facade;

import com.example.stock.repository.RedisLockRepository;
import com.example.stock.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LettuceLockFacade {

    private final RedisLockRepository lockRepository;

    private final StockService stockService;

    public void decrease(Long id, Long quantity) {
        while (!lockRepository.lock(id)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            stockService.decrease(id, quantity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lockRepository.unLock(id);
        }
    }
}
