package com.example.stockproject.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UnrealProfitResult {

    private String stock;
    private String stockName;
    private Double nowPrice;
    private Double sumRemainQty;
    private Integer sumFee;
    private Double sumCost;
    private Double sumMarketValue;
    private Double sumUnrealProfit;
    private List<UnrealResult> detailList;

}
