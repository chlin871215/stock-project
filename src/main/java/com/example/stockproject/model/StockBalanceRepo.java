package com.example.stockproject.model;

import com.example.stockproject.model.entity.StockBalance;
import com.example.stockproject.model.entity.StockBalancePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockBalanceRepo extends JpaRepository<StockBalance, StockBalancePK> {

    @Query(value = "select * from tcnud where BranchNo = ?1 and CustSeq = ?2 and stock = ?3  ;", nativeQuery = true)
    List<StockBalance> findByBranchNoAndCustSeqAndStock(String branchNo, String custSeq, String stock);

    @Query(value = "select sum(Cost) from tcnud where BranchNo = ?1 and CustSeq = ?2 and TradeDate = ?3  ;", nativeQuery = true)
    Long findTodayBalance(String branchNo, String custSeq, String tradeDate);

    @Query(value = "select sum(Qty) from tcnud where BranchNo= ?1 and CustSeq= ?2 and stock= ?3 ;", nativeQuery = true)
    Double getRemainQty(String branchNo, String custSeq, String stock);

    @Query(value = "select distinct Stock from tcnud where BranchNo = ?1 and CustSeq = ?2 order by Stock  ;", nativeQuery = true)
    List<String> getAllStock(String branchNo, String custSeq);

}
