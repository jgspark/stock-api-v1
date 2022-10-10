package com.example.stock.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptimisticStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Long quantity;

    // Optimistic Lock Version add
    @Version
    private Long version;

    public void decrease(Long quantity){

        long currentValue = this.quantity - quantity;

        if (currentValue < 0){
            throw new RuntimeException("sale exception");
        }

        this.quantity = currentValue;
    }

}
