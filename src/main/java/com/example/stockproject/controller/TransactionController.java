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
        try {
            return transactionService.transaction(transactionRequest);
        } catch (Exception e) {
            return new TransactionResponse(null, "005", "連線逾時");
        }
    }

    //查詢彙總未實現損益------------------------------------------------------------------------------------------------
    @PostMapping("/sum")
    public SumUnrealProfit sumUnrealizedGainsAndLosses(@RequestBody UnrealProfitRequest unrealProfitRequest) {
        try {
            return unrealService.sumUnrealizedGainsAndLosses(unrealProfitRequest);
        } catch (Exception e) {
            return new SumUnrealProfit(null, "005", "連線逾時");
        }
    }

    //查詢個別未實現損益------------------------------------------------------------------------------------------------
    @PostMapping("/detail")
    public TransactionResponse unrealizedGainsAndLosses(@RequestBody UnrealProfitRequest unrealProfitRequest) {
        try {
            return unrealService.unrealProfit(unrealProfitRequest);
        } catch (Exception e) {
            return new TransactionResponse(null, "005", "連線逾時");
        }
    }

    //today's payment----------------------------------------------------------------------------------------------
    @PostMapping("/today")
    public PaymentResponse todayPay(@RequestBody TodayPay todayPay) {
        try {
            return paymentService.todayPay(todayPay);
        } catch (Exception e) {
            return new PaymentResponse("連線逾時", null);
        }
    }


    //caching stockInfo---------------------------------------------------------------------------------------------
    @PostMapping("/info")
    public StockResponse cachingStock(@RequestBody StockRequest stock) {
        try {
            return updateStockService.cachingStock(stock);
        } catch (Exception e) {
            return new StockResponse(null, "連線逾時");
        }
    }

    //Update Price------------------------------------------------------------------------------------------------
    @PostMapping("/update")
    public StockResponse updatePrice(@RequestBody UpdatePriceRequest updatePriceRequest) {
        try {
            return updateStockService.updatePrice(updatePriceRequest);
        } catch (Exception e) {
            return new StockResponse(null, "連線逾時");
        }
    }

}
