package com.example.stockproject.service;

import com.example.stockproject.controller.request.TransactionRequest;
import com.example.stockproject.controller.response.TransactionResponse;
import com.example.stockproject.controller.response.UnrealResult;
import com.example.stockproject.model.StockBalanceRepo;
import com.example.stockproject.model.TransactionRepo;
import com.example.stockproject.model.entity.StockBalance;
import com.example.stockproject.model.entity.Symbol;
import com.example.stockproject.model.entity.TransactionDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//交易
@Service
public class TransactionService {
    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    StockBalanceRepo stockBalanceRepo;
    @Autowired
    CalService calService;

    public TransactionResponse transaction(TransactionRequest transactionRequest) {
        //check:request資訊是否正確、股票餘額是否足夠
        if (null != calService.check(transactionRequest))
            return new TransactionResponse(null, "002", calService.check(transactionRequest));
        //創建明細--------------------------------------------------------------------------------------------------
        TransactionDetail transactionDetail = new TransactionDetail();
        {
            transactionDetail.setTradeDate(transactionRequest.getTradeDate());//tradeDate
            transactionDetail.setBranchNo(transactionRequest.getBranchNo());//branchNo
            transactionDetail.setCustomerSeq(transactionRequest.getCustSeq());//customerSeq
            transactionDetail.setDocSeq(transactionRequest.getDocSeq());//docSeq
            transactionDetail.setStock(transactionRequest.getStock());//stock
            transactionDetail.setBsType("B");//bsType
            transactionDetail.setPrice(transactionRequest.getPrice());//price
            transactionDetail.setQty(transactionRequest.getQty());//qty
            transactionDetail.setAmt(calService.getAmt(transactionDetail.getPrice(), transactionDetail.getQty()));//單價*股數=amt
            transactionDetail.setFee(calService.getFee(transactionDetail.getAmt()));//fee
            transactionDetail.setTax(calService.getTax(transactionDetail.getAmt(), transactionDetail.getBsType()));//根據bsType決定tax
            transactionDetail.setTransferTax(0.0);//交易稅目前為0
            transactionDetail.setNetAmt(calService.getNetAmt(transactionDetail.getAmt(), transactionDetail.getFee(), transactionDetail.getTax(), transactionDetail.getBsType()));//根據四項數據得到淨收付
            transactionDetail.setModDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));//modDate
            transactionDetail.setModTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss")));//modTime
            transactionDetail.setModUser("Berlin");//modUser
            transactionRepo.save(transactionDetail);//存進sql
        }
        //更新餘額--------------------------------------------------------------------------------------------------
        {
            StockBalance newStockBalance = new StockBalance();
            newStockBalance.setTradeDate(transactionDetail.getTradeDate());
            newStockBalance.setBranchNo(transactionDetail.getBranchNo());
            newStockBalance.setCustomerSeq(transactionDetail.getCustomerSeq());
            newStockBalance.setDocSeq(transactionDetail.getDocSeq());
            newStockBalance.setStock(transactionDetail.getStock());
            newStockBalance.setPrice(calService.getBalancePrice(0.0, 0.0, transactionDetail.getPrice(), transactionDetail.getQty()));
            newStockBalance.setQty(calService.getBalanceQty(0.0, transactionDetail.getQty(), transactionDetail.getBsType()));
            newStockBalance.setRemainQty(calService.getBalanceQty(0.0, transactionDetail.getQty(), transactionDetail.getBsType()));
            newStockBalance.setFee(transactionDetail.getFee());
            newStockBalance.setCost(calService.getBalanceCost(0.0, transactionDetail.getNetAmt(), transactionDetail.getBsType()));
            newStockBalance.setModDate(transactionDetail.getModDate());
            newStockBalance.setModTime(transactionDetail.getModTime());
            newStockBalance.setModUser(transactionDetail.getModUser());
            stockBalanceRepo.save(newStockBalance);
        }
        List<UnrealResult> unrealResults = new ArrayList<>();
        {
            Symbol symbol = calService.getSymbol(transactionRequest.getStock());
            UnrealResult unrealResult = new UnrealResult();
            unrealResult.setTradeDate(transactionDetail.getTradeDate());
            unrealResult.setDocSeq(transactionDetail.getDocSeq());
            unrealResult.setStock(transactionDetail.getStock());
            unrealResult.setStockName(symbol.getShortname());
            unrealResult.setBuyPrice(transactionDetail.getPrice());
            unrealResult.setNowPrice(Double.parseDouble(symbol.getDealprice()));
            unrealResult.setQty(transactionDetail.getQty());
            unrealResult.setRemainQty(stockBalanceRepo.getRemainQty(transactionRequest.getBranchNo(), transactionRequest.getCustSeq(), transactionRequest.getStock()));
            unrealResult.setFee(transactionDetail.getFee());
            unrealResult.setCost(Math.abs(transactionDetail.getNetAmt()));
            unrealResult.setMarketValue(Math.round(Double.parseDouble(symbol.getDealprice()) * transactionDetail.getQty()));
            unrealResult.setUnrealProfit(calService.getUnreal(transactionDetail.getStock(), Math.abs(transactionDetail.getNetAmt()), transactionDetail.getQty()));
            unrealResult.setMargin(String.format("%.2f", (calService.getRoundTwo(calService.getUnreal(transactionDetail.getStock(), Math.abs(transactionDetail.getNetAmt()), transactionDetail.getQty()) / Math.abs(transactionDetail.getNetAmt()) * 100))) + "%");
            unrealResults.add(unrealResult);
        }
        return new TransactionResponse(
                unrealResults,
                "000",
                "");
    }

}
