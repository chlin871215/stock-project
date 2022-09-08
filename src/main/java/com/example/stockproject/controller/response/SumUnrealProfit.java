package com.example.stockproject.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SumUnrealProfit {//彙總未實現損益
    private List<UnrealProfitResult> resultList;
    private String responseCode;
    private String message;

}
