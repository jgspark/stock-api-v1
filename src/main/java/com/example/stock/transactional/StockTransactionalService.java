package com.example.stock.transactional;

import com.example.stock.domain.Stock;
import com.example.stock.service.StockService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StockTransactionalService {

    private final StockService stockService;

    public Stock decrease(Long stockId , Long quantity){
        startedTransactional();
        Stock stock = stockService.decrease(stockId, quantity);
        endedTransactional();
        return stock;
    }

    private void startedTransactional(){

    }

    private void endedTransactional(){

    }
}
