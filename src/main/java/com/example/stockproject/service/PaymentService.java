package com.example.stockproject.service;

import com.example.stockproject.controller.request.TodayPay;
import com.example.stockproject.controller.response.PaymentResponse;
import com.example.stockproject.model.HolidayRepo;
import com.example.stockproject.model.StockBalanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class PaymentService {
    @Autowired
    HolidayRepo holidayRepo;
    @Autowired
    StockBalanceRepo stockBalanceRepo;

    //todayPay
    public PaymentResponse todayPay(TodayPay todayPay) {
        //check
        if (null != check(todayPay)) return new PaymentResponse(check(todayPay), 0l);
        //process
        Calendar today = Calendar.getInstance();
        Calendar target = Calendar.getInstance();
        target.add(Calendar.DATE, -2);//預期目標日為今日回推兩天
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        while (0 != today.compareTo(target)) {//當目前日期不等於目標日期則執行迴圈
            today.add(Calendar.DATE, -1);//回到前一天
            //前一天是假日則使預期目標日回推一天
            if (null != holidayRepo.findByHoliday(sdf.format(today.getTime())) || today.get(Calendar.DAY_OF_WEEK) == 1 || today.get(Calendar.DAY_OF_WEEK) == 7) {
                target.add(Calendar.DATE, -1);
            }
        }
        if (null == stockBalanceRepo.findTodayBalance(todayPay.getBranchNo(), todayPay.getCustSeq(), sdf.format(target.getTime()))) {
            return new PaymentResponse("Today's payment is 0", 0l);
        }
        return new PaymentResponse("", stockBalanceRepo.findTodayBalance(todayPay.getBranchNo(), todayPay.getCustSeq(), sdf.format(target.getTime())));
    }


    private String check(TodayPay todayPay) {
        if (todayPay.getBranchNo().isBlank() || todayPay.getBranchNo().length() > 4) return "BranchNo data wrong";
        if (todayPay.getCustSeq().isBlank() || todayPay.getCustSeq().length() > 7) return "CustSeq data wrong";
        return null;
    }

}
