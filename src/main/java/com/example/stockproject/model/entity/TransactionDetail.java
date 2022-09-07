package com.example.stockproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hcmio")
@IdClass(TransactionDetailPK.class)
public class TransactionDetail {
    @Id
    @Column(name = "TradeDate")
    private String tradeDate;
    @Id
    @Column(name = "BranchNo")
    private String branchNo;
    @Id
    @Column(name = "CustSeq")
    private String customerSeq;
    @Id
    @Column(name = "DocSeq")
    private String docSeq;
    @Column(name = "Stock")
    private String stock;
    @Column(name = "BsType")
    private String bsType;
    @Column(name = "Price")
    private Double price;
    @Column(name = "Qty")
    private Double qty;
    @Column(name = "Amt")
    private Double amt;
    @Column(name = "Fee")
    private Integer fee;
    @Column(name = "Tax")
    private Double tax;
    @Column(name = "StinTax")
    private Double transferTax;
    @Column(name = "NetAmt")
    private Double netAmt;
    @Column(name = "ModDate")
    private String modDate;
    @Column(name = "ModTime")
    private String modTime;
    @Column(name = "ModUser")
    private String modUser;
}
