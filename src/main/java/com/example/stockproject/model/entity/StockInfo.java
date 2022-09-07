package com.example.stockproject.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mstmb")
@Entity
public class StockInfo {
    @Id
    @Column(name = "Stock")
    private String stock;
    @Column(name = "StockName")
    private String stockName;
    @Column(name = "MarketType")
    private String marketType;
    @Column(name = "CurPrice")
    private Double curPrice;
    @Column(name = "RefPrice")
    private Double refPrice;
    @Column(name = "Currency")
    private String currency;
    @Column(name = "ModDate")
    private String modDate;
    @Column(name = "ModTime")
    private String modTime;
    @Column(name = "ModUser")
    private String modUser;

}
