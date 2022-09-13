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

}
