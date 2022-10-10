package com.example.stock.facade;

import com.example.stock.service.OptimisticStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptimisticStockFacade {

    private final OptimisticStockService stockService;

    public void decrease(Long stockId, Long quantity) {
        while (true) {
            try {
                stockService.decrease(stockId, quantity);
                break;
            } catch (Exception e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
