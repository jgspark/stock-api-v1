package com.example.stock.facade;

import com.example.stock.domain.OptimisticStock;
import com.example.stock.repository.OptimisticStockRepository;
import com.example.stock.service.OptimisticStockService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class OptimisticStockFacadeTest {

    @Autowired
    private OptimisticStockFacade facade;

    @Autowired
    private OptimisticStockService stockService;

    @Autowired
    private OptimisticStockRepository stockRepository;

    @BeforeEach
    void init() {
        OptimisticStock stock = new OptimisticStock(null, 1L, 100L, 0L);
        stockRepository.saveAndFlush(stock);
    }

    @AfterEach
    public void after() {
        stockRepository.deleteAll();
    }

    @Test
    void decrease_100thread() throws InterruptedException {

        int count = 100;

        ExecutorService executorService =
                Executors.newFixedThreadPool(32);

        CountDownLatch latch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            executorService.execute(() -> {
                try {
                    facade.decrease(1L, 1L);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        OptimisticStock stock = stockRepository.findById(1L).orElseThrow();

        assertEquals(0L, stock.getQuantity());
    }
}