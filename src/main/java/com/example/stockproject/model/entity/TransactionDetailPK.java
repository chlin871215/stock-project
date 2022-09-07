package com.example.stockproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TransactionDetailPK implements Serializable {

    private String tradeDate;
    private String branchNo;
    private String customerSeq;
    private String docSeq;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDetailPK that = (TransactionDetailPK) o;
        return Objects.equals(tradeDate, that.tradeDate) && Objects.equals(branchNo, that.branchNo) && Objects.equals(customerSeq, that.customerSeq) && Objects.equals(docSeq, that.docSeq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradeDate, branchNo, customerSeq, docSeq);
    }


}
