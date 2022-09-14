package com.example.stockproject.service;

import com.example.stockproject.controller.request.StockRequest;
import com.example.stockproject.controller.request.UpdatePriceRequest;
import com.example.stockproject.controller.response.StockResponse;
import com.example.stockproject.model.StockInfoRepo;
import com.example.stockproject.model.entity.StockInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UpdateStockService {

    @Autowired
    StockInfoRepo stockInfoRepo;

    @Cacheable(cacheNames = "stockInfo_cache", key = "#stock.getStock()")
    public StockResponse cachingStock(StockRequest stock) {
        //check
        if (null == stockInfoRepo.findByStock(stock.getStock()))
            return new StockResponse(null, "Stock data wrong");
        StockInfo stockInfo = stockInfoRepo.findByStock(stock.getStock());
        return new StockResponse(stockInfo, "");
    }

    //updatePrice
    @CachePut(cacheNames = "stockInfo_cache", key = "#updatePriceRequest.getStock()")
    public StockResponse updatePrice(UpdatePriceRequest updatePriceRequest) {
        if (null != check(updatePriceRequest)) return new StockResponse(null, check(updatePriceRequest));
        StockInfo stockInfo = stockInfoRepo.findByStock(updatePriceRequest.getStock());
        stockInfo.setCurPrice(updatePriceRequest.getPrice());
        stockInfoRepo.save(stockInfo);
        return new StockResponse(stockInfo, "");
    }

    private String check(UpdatePriceRequest updatePriceRequest) {
        if (updatePriceRequest.getStock().isBlank()) return "Stock data wrong";
        if (null == stockInfoRepo.findByStock(updatePriceRequest.getStock())) return "Stock doesn't exist";
        if (null==updatePriceRequest.getPrice())return "Price data wrong";
        if (updatePriceRequest.getPrice() < 10.0 || updatePriceRequest.getPrice() * 100 % 1 != 0 || updatePriceRequest.getPrice() >= 1_000_000)
            return "Price data wrong";
        return null;
    }

}
