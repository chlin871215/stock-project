package com.example.stockproject.model;

import com.example.stockproject.model.entity.TransactionDetail;
import com.example.stockproject.model.entity.TransactionDetailPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository<TransactionDetail, TransactionDetailPK> {

    TransactionDetail findByDocSeqAndTradeDate(String docSeq, String tradeDate);

    @Query(value = "SELECT DocSeq FROM hcmio where TradeDate=?1 ORDER BY DocSeq DESC LIMIT 1", nativeQuery = true)
    String getNewDocSeq(String tradeDate);

}
