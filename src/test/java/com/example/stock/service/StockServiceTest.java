package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    private StockService stockService;

    @Mock
    private StockRepository stockRepository;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        stockService = new StockService(stockRepository);
    }

    @Test
    void decrease(){

        Optional<Stock> mock = getMock();

        given(stockRepository.findById(any())).willReturn(mock);

        Stock entity = stockService.decrease(mock.get().getId(), 100L);

        assertEquals(900L , entity.getQuantity());
    }

    private Optional<Stock> getMock(){
        return Optional.of(new Stock(1L , 1L , 1000L));
    }
}