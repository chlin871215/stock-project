package com.example.stockproject.controller;

import com.example.stockproject.controller.request.TransactionRequest;
import com.example.stockproject.controller.request.UnrealProfitRequest;
import com.example.stockproject.controller.response.SumUnrealProfit;
import com.example.stockproject.controller.response.TransactionResponse;
import com.example.stockproject.controller.response.UnrealResult;
import com.example.stockproject.model.StockBalanceRepo;
import com.example.stockproject.model.StockInfoRepo;
import com.example.stockproject.model.TransactionRepo;
import com.example.stockproject.model.entity.TransactionDetail;
import com.example.stockproject.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
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
    @PostMapping()
    public TransactionResponse transaction(@RequestBody TransactionRequest transactionRequest) {
        String response = transactionService.transaction(transactionRequest);
        if (response.equals("Transaction successful") || response.equals("Transaction complete,qty is 0")) {
            TransactionDetail transactionDetail=transactionRepo.findByDocSeqAndTradeDate(transactionRequest.getDocSeq(), transactionRequest.getTradeDate());
            return new TransactionResponse(new UnrealResult(transactionDetail.getTradeDate(), transactionDetail.getDocSeq(), transactionDetail.getStock(),stockInfoRepo.findByStock(transactionRequest.getStock()).getStockName(), transactionRequest.getPrice(),stockInfoRepo.findByStock(transactionRequest.getStock()).getCurPrice(), transactionRequest.getQty(), stockBalanceRepo.getRemainQty(transactionRequest.getBranchNo(), transactionRequest.getCustSeq(), transactionRequest.getStock()), transactionDetail.getFee(), transactionDetail.getNetAmt(),null,null),"000","");
        }
        return new TransactionResponse(null, "002", response);
    }


    //查詢未實現損益------------------------------------------------------------------------------------------------
    @PostMapping("/unreal")
    public SumUnrealProfit unrealizedGainsAndLosses(@RequestBody UnrealProfitRequest unrealProfitRequest) {
        return new SumUnrealProfit(null, null, null);
    }
}
