package com.example.stockproject.service;

import com.example.stockproject.controller.request.UnrealProfitRequest;
import com.example.stockproject.controller.response.SumUnrealProfit;
import com.example.stockproject.controller.response.TransactionResponse;
import com.example.stockproject.controller.response.UnrealProfitResult;
import com.example.stockproject.controller.response.UnrealResult;
import com.example.stockproject.model.StockBalanceRepo;
import com.example.stockproject.model.entity.StockBalance;
import com.example.stockproject.model.entity.Symbol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UnrealService {

    @Autowired
    CalService calService;
    @Autowired
    StockBalanceRepo stockBalanceRepo;

    //查詢未實現損益------------------------------------------------------------------------------------------------
    public SumUnrealProfit sumUnrealizedGainsAndLosses(UnrealProfitRequest unrealProfitRequest) {
        //check
        if ("001".equals(calService.check(unrealProfitRequest)))
            return new SumUnrealProfit(null, "001", "查無符合資料");
        if (null != calService.check(unrealProfitRequest))
            return new SumUnrealProfit(null, "002", calService.check(unrealProfitRequest));
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
            UnrealProfitResult unrealProfitResult = new UnrealProfitResult();
            unrealProfitResult.setDetailList(getResultList(new UnrealProfitRequest(unrealProfitRequest.getBranchNo(), unrealProfitRequest.getCustSeq(), stock, unrealProfitRequest.getUpperLimit(), unrealProfitRequest.getLowerLimit())));
            unrealProfitResult.setStock(stock);
            Symbol symbol = calService.getSymbol(unrealProfitRequest.getStock());
            String stockName = symbol.getShortname();
            Double curPrice = Double.parseDouble(symbol.getDealprice());
            unrealProfitResult.setStockName(stockName);
            unrealProfitResult.setNowPrice(curPrice);
            for (UnrealResult unrealResult : unrealProfitResult.getDetailList()) {
                unrealProfitResult.setSumRemainQty((null == unrealProfitResult.getSumRemainQty()) ? unrealResult.getRemainQty() : unrealProfitResult.getSumRemainQty() + unrealResult.getQty());
                unrealProfitResult.setSumFee((null == unrealProfitResult.getSumFee()) ? unrealResult.getFee() : unrealProfitResult.getSumFee() + unrealResult.getFee());
                unrealProfitResult.setSumCost((null == unrealProfitResult.getSumCost()) ? unrealResult.getCost() : unrealProfitResult.getSumCost() + unrealResult.getCost());
                unrealProfitResult.setSumUnrealProfit((null == unrealProfitResult.getSumUnrealProfit()) ? unrealResult.getUnrealProfit() : unrealProfitResult.getSumUnrealProfit() + unrealResult.getUnrealProfit());
                unrealProfitResult.setSumMarketValue(calService.getAmt(unrealProfitResult.getNowPrice(), unrealProfitResult.getSumRemainQty()) - calService.getFee(calService.getAmt(unrealProfitResult.getNowPrice(), unrealProfitResult.getSumRemainQty())) - calService.getTax(calService.getAmt(unrealProfitResult.getNowPrice(), unrealProfitResult.getSumRemainQty()), "S"));
                unrealProfitResult.setSumMargin(String.format("%.2f", calService.getRoundTwo(unrealProfitResult.getSumUnrealProfit() / unrealProfitResult.getSumCost() * 100)) + "%");
            }
            if (null != unrealProfitResult.getSumUnrealProfit()) {
                unrealProfitResults.add(unrealProfitResult);
            }
        }

        if (0 == unrealProfitResults.size()) return new SumUnrealProfit(null, "001", "查無符合資料");

        return new SumUnrealProfit(
                unrealProfitResults,
                "000",
                ""

        );
    }

    public TransactionResponse unrealProfit(UnrealProfitRequest unrealProfitRequest) {
        //check
        if ("001".equals(calService.check(unrealProfitRequest)))
            return new TransactionResponse(null, "001", "查無符合資料");
        if (null != calService.check(unrealProfitRequest))
            return new TransactionResponse(null, "002", calService.check(unrealProfitRequest));
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
        Symbol symbol = calService.getSymbol(unrealProfitRequest.getStock());
        String stockName = symbol.getShortname();
        Double curPrice = Double.parseDouble(symbol.getDealprice());
        for (StockBalance stockBalance : stockBalances) {
            if (unrealProfitRequest.getUpperLimit().compareTo(unrealProfitRequest.getLowerLimit()) == 0) {
                UnrealResult unrealResult = new UnrealResult();
                unrealResult.setTradeDate(stockBalance.getTradeDate());
                unrealResult.setDocSeq(stockBalance.getDocSeq());
                unrealResult.setStock(stockBalance.getStock());
                unrealResult.setStockName(stockName);
                unrealResult.setBuyPrice(stockBalance.getPrice());
                unrealResult.setNowPrice(curPrice);
                unrealResult.setQty(stockBalance.getQty());
                unrealResult.setRemainQty(stockBalance.getRemainQty());
                unrealResult.setFee(stockBalance.getFee());
                unrealResult.setCost(Math.abs(stockBalance.getCost()));
                unrealResult.setMarketValue(Math.round(curPrice * stockBalance.getQty()));
                unrealResult.setUnrealProfit(calService.getUnreal(stockBalance.getStock(), Math.abs(stockBalance.getCost()), stockBalance.getQty()));
                unrealResult.setMargin(String.format("%.2f", calService.getRoundTwo(calService.getUnreal(stockBalance.getStock(), stockBalance.getCost(), stockBalance.getQty()) / stockBalance.getCost() * 100)) + "%");
                unrealResults.add(unrealResult);
            } else if (calService.getRoundTwo(calService.getUnreal(stockBalance.getStock(), stockBalance.getCost(), stockBalance.getQty()) / stockBalance.getCost() * 100) < unrealProfitRequest.getUpperLimit() && calService.getRoundTwo(calService.getUnreal(stockBalance.getStock(), stockBalance.getCost(), stockBalance.getQty()) / stockBalance.getCost() * 100) > unrealProfitRequest.getLowerLimit()) {
                UnrealResult unrealResult = new UnrealResult();
                unrealResult.setTradeDate(stockBalance.getTradeDate());
                unrealResult.setDocSeq(stockBalance.getDocSeq());
                unrealResult.setStock(stockBalance.getStock());
                unrealResult.setStockName(stockName);
                unrealResult.setBuyPrice(stockBalance.getPrice());
                unrealResult.setNowPrice(curPrice);
                unrealResult.setQty(stockBalance.getQty());
                unrealResult.setRemainQty(stockBalance.getRemainQty());
                unrealResult.setFee(stockBalance.getFee());
                unrealResult.setCost(Math.abs(stockBalance.getCost()));
                unrealResult.setMarketValue(Math.round(curPrice * stockBalance.getQty()));
                unrealResult.setUnrealProfit(calService.getUnreal(stockBalance.getStock(), Math.abs(stockBalance.getCost()), stockBalance.getQty()));
                unrealResult.setMargin(String.format("%.2f", calService.getRoundTwo(calService.getUnreal(stockBalance.getStock(), stockBalance.getCost(), stockBalance.getQty()) / stockBalance.getCost() * 100)) + "%");
                unrealResults.add(unrealResult);
            }
        }
        return unrealResults;
    }


}
