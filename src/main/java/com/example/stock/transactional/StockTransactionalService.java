package com.example.stock.transactional;

import com.example.stock.service.StockService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StockTransactionalService {

    private final StockService stockService;

    public void decrease(Long stockId , Long quantity){
        startedTransactional();
        stockService.decrease(stockId, quantity);
        endedTransactional();
    }

    private void startedTransactional(){

    }

    private void endedTransactional(){

    }
}
