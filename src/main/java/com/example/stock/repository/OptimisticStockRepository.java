package com.example.stock.repository;

import com.example.stock.domain.OptimisticStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface OptimisticStockRepository extends JpaRepository<OptimisticStock, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select s from Stock s where s.id = :id")
    OptimisticStock findStockById(Long id);
}
