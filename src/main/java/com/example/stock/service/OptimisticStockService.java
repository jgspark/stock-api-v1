package com.example.stock.service;

import com.example.stock.domain.OptimisticStock;
import com.example.stock.repository.OptimisticStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OptimisticStockService {

    private final OptimisticStockRepository stockRepository;

    @Transactional
    public void decrease(Long stockId, Long quantity) {
        OptimisticStock stock = stockRepository.findStockById(stockId);
        stock.decrease(quantity);
        stockRepository.saveAndFlush(stock);
    }
}
