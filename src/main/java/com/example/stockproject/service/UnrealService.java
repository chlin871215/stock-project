package com.example.stockproject.service;

import com.example.stockproject.controller.request.UnrealProfitRequest;
import com.example.stockproject.controller.response.SumUnrealProfit;
import com.example.stockproject.controller.response.TransactionResponse;
import com.example.stockproject.controller.response.UnrealProfitResult;
import com.example.stockproject.controller.response.UnrealResult;
import com.example.stockproject.model.StockBalanceRepo;
import com.example.stockproject.model.StockInfoRepo;
import com.example.stockproject.model.TransactionRepo;
import com.example.stockproject.model.entity.StockBalance;
import com.example.stockproject.model.entity.StockInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UnrealService {

    @Autowired
    StockInfoRepo stockInfoRepo;
    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    StockBalanceRepo stockBalanceRepo;


    //查詢未實現損益------------------------------------------------------------------------------------------------
    public SumUnrealProfit sumUnrealizedGainsAndLosses(UnrealProfitRequest unrealProfitRequest) {
        //check
        if ("001".equals(check(unrealProfitRequest))) return new SumUnrealProfit(null, "001", "查無符合資料");
        if (null != check(unrealProfitRequest)) return new SumUnrealProfit(null, "002", check(unrealProfitRequest));
        //process

        List<String> stockList;
        if (unrealProfitRequest.getStock().isBlank()) {
            stockList = stockBalanceRepo.getAllStock(unrealProfitRequest.getBranchNo(), unrealProfitRequest.getCustSeq());
        } else {
            stockList = new ArrayList<>();
            stockList.add(unrealProfitRequest.getStock());
        }

        List<UnrealProfitResult> unrealProfitResults = new ArrayList<>();

        for (String stock : stockList) {
            StockInfo stockInfo = stockInfoRepo.findByStock(stock);
            UnrealProfitResult unrealProfitResult = new UnrealProfitResult();
            unrealProfitResult.setDetailList(getResultList(new UnrealProfitRequest(unrealProfitRequest.getBranchNo(), unrealProfitRequest.getCustSeq(), stock, unrealProfitRequest.getUpperLimit(), unrealProfitRequest.getLowerLimit())));
            unrealProfitResult.setStock(stock);
            unrealProfitResult.setStockName(stockInfo.getStockName());
            unrealProfitResult.setNowPrice(stockInfo.getCurPrice());
            for (UnrealResult unrealResult : unrealProfitResult.getDetailList()) {
                unrealProfitResult.setSumRemainQty((null == unrealProfitResult.getSumRemainQty()) ? unrealResult.getRemainQty() : unrealProfitResult.getSumRemainQty() + unrealResult.getQty());
                unrealProfitResult.setSumFee((null == unrealProfitResult.getSumFee()) ? unrealResult.getFee() : unrealProfitResult.getSumFee() + unrealResult.getFee());
                unrealProfitResult.setSumCost((null == unrealProfitResult.getSumCost()) ? unrealResult.getCost() : unrealProfitResult.getSumCost() + unrealResult.getCost());
                unrealProfitResult.setSumUnrealProfit((null == unrealProfitResult.getSumUnrealProfit()) ? unrealResult.getUnrealProfit() : unrealProfitResult.getSumUnrealProfit() + unrealResult.getUnrealProfit());
                unrealProfitResult.setSumMarketValue(getAmt(unrealProfitResult.getNowPrice(), unrealProfitResult.getSumRemainQty()) - getFee(getAmt(unrealProfitResult.getNowPrice(), unrealProfitResult.getSumRemainQty())) - getTax(getAmt(unrealProfitResult.getNowPrice(), unrealProfitResult.getSumRemainQty()), "S"));
                unrealProfitResult.setSumMargin(String.format("%.2f", getRoundTwo(unrealProfitResult.getSumUnrealProfit() / unrealProfitResult.getSumCost() * 100)) + "%");
            }
            unrealProfitResults.add(unrealProfitResult);
        }

        return new SumUnrealProfit(
                unrealProfitResults,
                "000",
                ""

        );
    }

    public TransactionResponse unrealProfit(UnrealProfitRequest unrealProfitRequest) {
        //check
        if ("001".equals(check(unrealProfitRequest))) return new TransactionResponse(null, "001", "查無符合資料");
        if (null != check(unrealProfitRequest)) return new TransactionResponse(null, "002", check(unrealProfitRequest));
        //process
        TransactionResponse transactionResponse = new TransactionResponse();

        List<String> stockList;
        if (unrealProfitRequest.getStock().isBlank()) {
            stockList = stockBalanceRepo.getAllStock(unrealProfitRequest.getBranchNo(), unrealProfitRequest.getCustSeq());
        } else {
            stockList = new ArrayList<>();
            stockList.add(unrealProfitRequest.getStock());
        }
        List<UnrealResult> resultList = new ArrayList<>();
        for (String stock : stockList) {
            for (UnrealResult unrealResult : getResultList(new UnrealProfitRequest(unrealProfitRequest.getBranchNo(), unrealProfitRequest.getCustSeq(), stock, unrealProfitRequest.getUpperLimit(), unrealProfitRequest.getLowerLimit()))) {
                resultList.add(unrealResult);
            }
        }

        transactionResponse.setResultList(resultList);
        transactionResponse.setResponseCode("000");
        transactionResponse.setMessage("");
        return transactionResponse;
    }


    private List<UnrealResult> getResultList(UnrealProfitRequest unrealProfitRequest) {
        List<StockBalance> stockBalances = stockBalanceRepo.findByBranchNoAndCustSeqAndStock(unrealProfitRequest.getBranchNo(), unrealProfitRequest.getCustSeq(), unrealProfitRequest.getStock());
        List<UnrealResult> unrealResults = new ArrayList<>();
        StockInfo stockInfo = stockInfoRepo.findByStock(unrealProfitRequest.getStock());
        String stockName = stockInfo.getStockName();
        Double curPrice = stockInfo.getCurPrice();
        for (StockBalance stockBalance : stockBalances) {
            if (unrealProfitRequest.getUpperLimit().compareTo(unrealProfitRequest.getLowerLimit())==0) {
                unrealResults.add(new UnrealResult(
                        stockBalance.getTradeDate(),
                        stockBalance.getDocSeq(),
                        stockBalance.getStock(),
                        stockName,
                        stockBalance.getPrice(),
                        curPrice,
                        stockBalance.getQty(),
                        stockBalance.getRemainQty(),
                        stockBalance.getFee(),
                        stockBalance.getCost(),
                        Math.round(stockInfo.getCurPrice() * stockBalance.getQty()),
                        getUnreal(stockBalance.getStock(), stockBalance.getCost(), stockBalance.getQty()),
                        String.format("%.2f", getRoundTwo(getUnreal(stockBalance.getStock(), stockBalance.getCost(), stockBalance.getQty()) / stockBalance.getCost() * 100)) + "%"
                ));
            } else if (getRoundTwo(getUnreal(stockBalance.getStock(), stockBalance.getCost(), stockBalance.getQty()) / stockBalance.getCost() * 100) < unrealProfitRequest.getUpperLimit() && getRoundTwo(getUnreal(stockBalance.getStock(), stockBalance.getCost(), stockBalance.getQty()) / stockBalance.getCost() * 100) > unrealProfitRequest.getLowerLimit()) {
                unrealResults.add(new UnrealResult(
                        stockBalance.getTradeDate(),
                        stockBalance.getDocSeq(),
                        stockBalance.getStock(),
                        stockName,
                        stockBalance.getPrice(),
                        curPrice,
                        stockBalance.getQty(),
                        stockBalance.getRemainQty(),
                        stockBalance.getFee(),
                        stockBalance.getCost(),
                        Math.round(stockInfo.getCurPrice() * stockBalance.getQty()),
                        getUnreal(stockBalance.getStock(), stockBalance.getCost(), stockBalance.getQty()),
                        String.format("%.2f", getRoundTwo(getUnreal(stockBalance.getStock(), stockBalance.getCost(), stockBalance.getQty()) / stockBalance.getCost() * 100)) + "%"
                ));
            }
        }
        return unrealResults;
    }

    private String check(UnrealProfitRequest unrealProfitRequest) {
        if (unrealProfitRequest.getBranchNo().isBlank() || unrealProfitRequest.getBranchNo().length() > 4)
            return "BranchNo data wrong";
        if (unrealProfitRequest.getCustSeq().isBlank() || unrealProfitRequest.getCustSeq().length() > 7)
            return "CustSeq data wrong";
        if (unrealProfitRequest.getUpperLimit() < unrealProfitRequest.getLowerLimit()) return "Limit data wrong";
        if (unrealProfitRequest.getStock().isBlank()) {
            return null;
        } else if (null == stockInfoRepo.findByStock(unrealProfitRequest.getStock())) return "Stock doesn't exist";
        if (null == stockBalanceRepo.findByBranchNoAndCustSeqAndStock(unrealProfitRequest.getBranchNo(), unrealProfitRequest.getBranchNo(), unrealProfitRequest.getStock()))
            return "001";
        return null;
    }

    private Double getUnreal(String stock, Double cost, Double qty) {
        Double curPrice = stockInfoRepo.findByStock(stock).getCurPrice();
        return (double) Math.round((curPrice * qty) - cost - getFee(getAmt(curPrice, qty)) - getTax(getAmt(curPrice, qty), "S"));
    }

    private Double getAmt(Double price, Double qty) {
        return price * qty;
    }

    private Integer getFee(Double amt) {
        return (int) Math.round(amt * 0.001425);
    }

    private Integer getTax(Double amt, String bsType) {
        return (bsType.equals("S")) ? (int) Math.round(amt * 0.003) : 0;
    }

    private Double getRoundTwo(Double number) {
        return Math.round(number * 100) / 100.0;
    }


}
