package com.example.stockproject.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SumUnrealProfit {//彙總未實現損益
    private UnrealProfitResult unrealProfitResult;
    private String responseCode;
    private String message;

}
