package com.example.stockproject.model;

import com.example.stockproject.model.entity.StockBalance;
import com.example.stockproject.model.entity.StockBalancePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockBalanceRepo extends JpaRepository<StockBalance, StockBalancePK> {

    @Query(value = "select * from tcnud where BranchNo = ?1 and CustSeq = ?2 and stock = ?3 order by DocSeq desc limit 1 ;",nativeQuery = true)
    StockBalance findByBranchNoAndCustSeqAndStock(String branchNo, String custSeq, String stock);

    @Query(value = "select RemainQty from tcnud where BranchNo = ?1 and CustSeq = ?2 and stock = ?3 order by ModDate desc,ModTime desc limit 1 ;",nativeQuery = true)
    Double getRemainQty(String branchNo, String custSeq, String stock);


}
