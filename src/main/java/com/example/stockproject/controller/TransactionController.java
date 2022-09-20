package com.example.stockproject.controller;

import com.example.stockproject.controller.request.*;
import com.example.stockproject.controller.response.PaymentResponse;
import com.example.stockproject.controller.response.StockResponse;
import com.example.stockproject.controller.response.SumUnrealProfit;
import com.example.stockproject.controller.response.TransactionResponse;
import com.example.stockproject.service.PaymentService;
import com.example.stockproject.service.TransactionService;
import com.example.stockproject.service.UnrealService;
import com.example.stockproject.service.UpdateStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/unreal")
public class TransactionController {

    @Autowired
    TransactionService transactionService;
    @Autowired
    UpdateStockService updateStockService;
    @Autowired
    PaymentService paymentService;
    @Autowired
    UnrealService unrealService;

    //交易--------------------------------------------------------------------------------------------------------
    @PostMapping("/add")
    public TransactionResponse transaction(@RequestBody TransactionRequest transactionRequest) {
        return transactionService.transaction(transactionRequest);
    }

    //查詢彙總未實現損益------------------------------------------------------------------------------------------------
    @PostMapping("/sum")
    public SumUnrealProfit sumUnrealizedGainsAndLosses(@RequestBody UnrealProfitRequest unrealProfitRequest) {
        return unrealService.sumUnrealizedGainsAndLosses(unrealProfitRequest);
    }

    //查詢個別未實現損益------------------------------------------------------------------------------------------------
    @PostMapping("/detail")
    public TransactionResponse unrealizedGainsAndLosses(@RequestBody UnrealProfitRequest unrealProfitRequest) {
        return unrealService.unrealProfit(unrealProfitRequest);
    }

    //today's payment----------------------------------------------------------------------------------------------
    @PostMapping("/today")
    public PaymentResponse todayPay(@RequestBody TodayPay todayPay) {
        return paymentService.todayPay(todayPay);
    }


    //caching stockInfo---------------------------------------------------------------------------------------------
    @PostMapping("/info")
    public StockResponse cachingStock(@RequestBody StockRequest stock) {
        return updateStockService.cachingStock(stock);
    }

    //Update Price------------------------------------------------------------------------------------------------
    @PostMapping("/update")
    public StockResponse updatePrice(@RequestBody UpdatePriceRequest updatePriceRequest) {
        return updateStockService.updatePrice(updatePriceRequest);
    }

}
