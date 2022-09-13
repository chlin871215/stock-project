package com.example.stockproject.controller;

import com.example.stockproject.controller.request.*;
import com.example.stockproject.controller.response.PaymentResponse;
import com.example.stockproject.controller.response.StockResponse;
import com.example.stockproject.controller.response.SumUnrealProfit;
import com.example.stockproject.controller.response.TransactionResponse;
import com.example.stockproject.model.StockBalanceRepo;
import com.example.stockproject.model.StockInfoRepo;
import com.example.stockproject.model.TransactionRepo;
import com.example.stockproject.model.entity.StockInfo;
import com.example.stockproject.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/unreal")
public class TransactionController {

    @Autowired
    TransactionService transactionService;
    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    StockInfoRepo stockInfoRepo;

    @Autowired
    StockBalanceRepo stockBalanceRepo;

    //交易--------------------------------------------------------------------------------------------------------
    @PostMapping("/add")
    public TransactionResponse transaction(@RequestBody TransactionRequest transactionRequest) {
        return transactionService.transaction(transactionRequest);
    }

    //查詢彙總未實現損益------------------------------------------------------------------------------------------------
    @PostMapping("/sum")
    public SumUnrealProfit sumUnrealizedGainsAndLosses(@RequestBody UnrealProfitRequest unrealProfitRequest) {
        return transactionService.sumUnrealizedGainsAndLosses(unrealProfitRequest);
    }

    //查詢個別未實現損益------------------------------------------------------------------------------------------------
    @PostMapping("/detail")
    public TransactionResponse unrealizedGainsAndLosses(@RequestBody UnrealProfitRequest unrealProfitRequest) {
        return transactionService.unrealProfit(unrealProfitRequest);
    }

    //Update Price
    @PostMapping("/update")
    public StockResponse updatePrice(@RequestBody UpdatePriceRequest updatePriceRequest) {
        return transactionService.updatePrice(updatePriceRequest);
    }

    //today
    @PostMapping("/today")
    public PaymentResponse todayPay(@RequestBody TodayPay todayPay) {
        return transactionService.todayPay(todayPay);
    }

    //caching stockInfo
    @PostMapping("/info")
    public StockResponse cachingStock(@RequestBody StockRequest stock) {
        return transactionService.cachingStock(stock);
    }

}
