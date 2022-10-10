package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
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
class StockServiceTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    void init(){
        Stock stock = new Stock(null , 1L , 100L);
        stockRepository.saveAndFlush(stock);
    }

    @AfterEach
    public void after(){
        stockRepository.deleteAll();
    }

    @Test
    void decrease(){
        stockService.decrease(1L , 1L);

        Stock entity = stockRepository.findById(1L).orElseThrow();

        assertEquals(entity.getQuantity() , 99L);
    }

    @Test
    void decrease_100thread() throws InterruptedException {

        int count = 100;

        ExecutorService executorService =
                Executors.newFixedThreadPool(32);

        CountDownLatch latch = new CountDownLatch(count);

        for (int i = 0 ; i < count ; i++){
            executorService.execute(() ->{
                try{
                stockService.decrease(1L , 1L);
                }finally{
                    latch.countDown();
                }
            });
        }

        latch.await();

        Stock stock = stockRepository.findById(1L).orElseThrow();

        assertEquals(0L , stock.getQuantity());
    }
}