package com.example.stockproject.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnrealResult {

    private String tradeDate;
    private String docSeq;
    private String stock;
    private String stockName;
    private Double buyPrice;
    private Double nowPrice;
    private Double qty;
    private Double remainQty;
    private Integer fee;
    private Double cost;
    private Long marketValue;
    private Double unrealProfit;
    private String margin;

    /*
        tradeDate - 交易日期
        docSeq - 委託書號
        stock - 股票代號
        stockName - 股票中文名稱
        buyPrice - 買進價格（一律顯示小數點後兩位）
        nowPrice - 現價（一律顯示小數點後兩位）
        qty - 買進股數
        remainQty - 剩餘股數
        fee - 買進手續費（顯示整數）
        cost - 買進成本（顯示整數）
        marketValue - 市值（顯示整數）
        unrealProfit - 未實現損益（顯示整數）
     */
}
