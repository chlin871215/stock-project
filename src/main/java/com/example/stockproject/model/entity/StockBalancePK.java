package com.example.stockproject.model.entity;

import java.io.Serializable;
import java.util.Objects;

public class StockBalancePK implements Serializable {

    private String tradeDate;
    private String branchNo;
    private String customerSeq;
    private String docSeq;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockBalancePK that = (StockBalancePK) o;
        return Objects.equals(tradeDate, that.tradeDate) && Objects.equals(branchNo, that.branchNo) && Objects.equals(customerSeq, that.customerSeq) && Objects.equals(docSeq, that.docSeq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradeDate, branchNo, customerSeq, docSeq);
    }

}
