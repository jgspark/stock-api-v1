package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    @Transactional
    public synchronized Stock decrease(Long stockId , Long quantity){
        Stock stock = stockRepository.findById(stockId).orElseThrow();
        stock.decrease(quantity);
        return stock;
    }
}
