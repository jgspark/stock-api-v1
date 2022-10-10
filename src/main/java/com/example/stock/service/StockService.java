package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    //@Transactional
    public synchronized Stock decrease(Long stockId , Long quantity){
        Stock stock = stockRepository.findById(stockId).orElseThrow();
        stock.decrease(quantity);
        stockRepository.saveAndFlush(stock);
        return stock;
    }
}
