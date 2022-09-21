package com.example.stockproject.service;

import com.example.stockproject.controller.request.TodayPay;
import com.example.stockproject.controller.request.TransactionRequest;
import com.example.stockproject.controller.request.UnrealProfitRequest;
import com.example.stockproject.controller.request.UpdatePriceRequest;
import com.example.stockproject.model.StockBalanceRepo;
import com.example.stockproject.model.StockInfoRepo;
import com.example.stockproject.model.TransactionRepo;
import com.example.stockproject.model.entity.StockInfo;
import com.example.stockproject.model.entity.Symbol;
import com.example.stockproject.model.entity.Symbols;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalService {

    @Autowired
    StockInfoRepo stockInfoRepo;
    @Autowired
    StockBalanceRepo stockBalanceRepo;
    @Autowired
    TransactionRepo transactionRepo;

    public String check(UnrealProfitRequest unrealProfitRequest) {
        if (unrealProfitRequest.getBranchNo().isBlank() || unrealProfitRequest.getBranchNo().length() > 4)
            return "BranchNo data wrong";
        if (unrealProfitRequest.getCustSeq().isBlank() || unrealProfitRequest.getCustSeq().length() > 7)
            return "CustSeq data wrong";
        if (null == unrealProfitRequest.getLowerLimit() || null == unrealProfitRequest.getUpperLimit() || unrealProfitRequest.getUpperLimit() < unrealProfitRequest.getLowerLimit())
            return "Limit data wrong";
        if (unrealProfitRequest.getStock().isBlank()) {
            return null;
        } else if (null == stockInfoRepo.findByStock(unrealProfitRequest.getStock())) return "Stock doesn't exist";
        if (null == stockBalanceRepo.findByBranchNoAndCustSeqAndStock(unrealProfitRequest.getBranchNo(), unrealProfitRequest.getBranchNo(), unrealProfitRequest.getStock()))
            return "001";
        return null;
    }

    public String check(TodayPay todayPay) {
        if (todayPay.getBranchNo().isBlank() || todayPay.getBranchNo().length() > 4) return "BranchNo data wrong";
        if (todayPay.getCustSeq().isBlank() || todayPay.getCustSeq().length() > 7) return "CustSeq data wrong";
        return null;
    }

    public String check(UpdatePriceRequest updatePriceRequest) {
        if (updatePriceRequest.getStock().isBlank()) return "Stock data wrong";
        if (null == stockInfoRepo.findByStock(updatePriceRequest.getStock())) return "Stock doesn't exist";
        if (null == updatePriceRequest.getPrice()) return "Price data wrong";
        if (updatePriceRequest.getPrice() < 10.0 || updatePriceRequest.getPrice() * 100 % 1 != 0 || updatePriceRequest.getPrice() >= 1_000_000)
            return "Price data wrong";
        return null;
    }

    public String check(TransactionRequest transactionRequest) {
        //check:request資訊是否正確、股票餘額是否足夠
        //check:tradeDate
        if (transactionRequest.getTradeDate().isBlank() || transactionRequest.getTradeDate().length() > 8)
            return "TradeDate data wrong";
        //check:branchNo
        if (transactionRequest.getBranchNo().isBlank() || transactionRequest.getBranchNo().length() > 4)
            return "BranchNo data wrong";
        //check:custSeq
        if (transactionRequest.getCustSeq().isBlank() || transactionRequest.getCustSeq().length() > 7)
            return "CustSeq data wrong";
        //check:docSeq
        if (transactionRequest.getDocSeq().isBlank() || transactionRequest.getDocSeq().length() > 5)
            return "DocSeq data wrong";
        //check:stock
        if (transactionRequest.getStock().isBlank()) return "Stock data wrong";
        //check:price
        if (null == transactionRequest.getPrice() || transactionRequest.getPrice() <= 0 || transactionRequest.getPrice() >= 1_000_000)
            return "Price data wrong";
        //check:docSeq是否存在
        if (null != transactionRepo.findByDocSeqAndTradeDate(transactionRequest.getDocSeq(), transactionRequest.getTradeDate()))
            return "This DocSeq already exist";
        //check:stock是否存在
        if (null == stockInfoRepo.findByStock(transactionRequest.getStock())) return "This Stock doesn't exist";
        //check:qty不得為空或小於等於0或含有小數
        if (null == transactionRequest.getQty() || transactionRequest.getQty() <= 0 || null == transactionRequest.getQty() || transactionRequest.getQty() % 1 != 0)
            return "Qty data wrong";
        //qty不得超過9位數
        if (transactionRequest.getQty() >= 1_000_000_000) return "Qty too much";
        if (null != stockBalanceRepo.getRemainQty(
                transactionRequest.getBranchNo(),
                transactionRequest.getCustSeq(),
                transactionRequest.getStock()
        ) && transactionRequest.getQty() +
                stockBalanceRepo.getRemainQty(
                        transactionRequest.getBranchNo(),
                        transactionRequest.getCustSeq(),
                        transactionRequest.getStock()) >= 1_000_000_000) {//remainQty不得超過9位數
            return "RemainQty too much";
        }

        return null;
    }

    public Double getUnreal(String stock, Double cost, Double qty) {
        Double curPrice = Double.parseDouble(getSymbol(stock).getDealprice());
        return (double) Math.round((curPrice * qty) - cost - getFee(getAmt(curPrice, qty)) - getTax(getAmt(curPrice, qty), "S"));
    }

    public Symbol getSymbol(String stock) {
        String urlString = "http://systexdemo.ddns.net:443/Quote/Stock.jsp?stock=" + stock;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(urlString, Symbols.class).getSymbolList().get(0);
    }

    public Double getAmt(Double price, Double qty) {
        return price * qty;
    }

    public Integer getFee(Double amt) {
        return (int) Math.round(amt * 0.001425);
    }

    public Integer getTax(Double amt, String bsType) {
        return (bsType.equals("S")) ? (int) Math.round(amt * 0.003) : 0;
    }

    public Double getRoundTwo(Double number) {
        return Math.round(number * 100) / 100.0;
    }

    public Double getNetAmt(Double amt, Integer fee, Integer tax, String bsType) {
        return (bsType.equals("B")) ? (-(amt + fee)) : (amt - fee - tax);
    }

    public Double getBalancePrice(Double oldPrice, Double oldQty, Double newPrice, Double newQty) {//依比例計算價格
        return (oldPrice * oldQty + newPrice * newQty) / (oldQty + newQty);
    }

    public Double getBalanceCost(Double oldNetAmt, Double newNetAmt, String bsType) {
        return (bsType.equals("B")) ? (Math.abs(oldNetAmt) + Math.abs(newNetAmt)) : (Math.abs(oldNetAmt - newNetAmt));
    }

    public Double getBalanceQty(Double oldQty, Double newQty, String bsType) {
        return (bsType.equals("B")) ? (oldQty + newQty) : (oldQty - newQty);
    }

    public void getRandomPrice(String stock) {
        StockInfo stockInfo = stockInfoRepo.findByStock(stock);
        Double oldPrice = stockInfo.getCurPrice();
        Double newPrice, r;
        do {
            r = Math.random() / 10;//0~9.99，最高漲跌幅9.99％
            newPrice = ((Math.random() * 10) < 5) ? (oldPrice * (1 + r)) : (oldPrice * (1 - r));//取二分之一機率
        } while (newPrice < 10);//股票最低價10
        stockInfo.setCurPrice(getRoundTwo(newPrice));//取小數點後第四位四捨五入
        stockInfoRepo.save(stockInfo);
    }

    public String getNewDocSeq(String tradeDate) {//流水單號
        String lastDocSeqEng = "AA";
        int lastDocSeqInt = 0;
        if (null != transactionRepo.getNewDocSeq(tradeDate)) {
            lastDocSeqEng = transactionRepo.getNewDocSeq(tradeDate).substring(0, 2);//取英文0~1
            lastDocSeqInt = Integer.parseInt(transactionRepo.getNewDocSeq(tradeDate).substring(2, 5));//取數字2~4
        }
        List<Integer> engToAscii = lastDocSeqEng.chars().boxed().collect(Collectors.toList());//英文轉ascii,box()之作用為將int轉為INTEGER
        //數字+1
        lastDocSeqInt++;
        //進位處理--------------------------------------------------------------------------------------------------
        {
            if (lastDocSeqInt > 999) {//如果超過999則歸1且英文進位
                lastDocSeqInt = 1;//歸1
                engToAscii.set(1, engToAscii.get(1) + 1);//英文進位
                if (engToAscii.get(1) > 90) {//如果超過Z
                    engToAscii.set(1, 65);//歸A
                    engToAscii.set(0, engToAscii.get(0) + 1);//進位
                    if (engToAscii.get(0) > 90 && engToAscii.get(0) < 97) {//如果超過Z
                        engToAscii.set(0, 97);//超過預設數據最大量，若超過給臨時數據庫aA001~zA999
                    }
                }
            }
        }
        //數值轉字串之檢查---------------------------------------------------------------------------------------------
        {
            String newDocSeqInt = String.format("%03d", lastDocSeqInt);//數值轉字串，%03d：表示補0到第3位
            String newDocSeqEng = "";
            for (int ascii : engToAscii) {
                newDocSeqEng += Character.toString(ascii);//list英文ascii轉字串
            }
            return newDocSeqEng + newDocSeqInt;
        }
    }


}
