package com.example.stockproject.model;

import com.example.stockproject.model.entity.StockInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockInfoRepo extends JpaRepository<StockInfo, String> {

    StockInfo findByStock(String stock);
}
