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
    @Autowired
    CalService calService;

    //todayPay
    public PaymentResponse todayPay(TodayPay todayPay) {
        //check
        if (null != calService.check(todayPay)) return new PaymentResponse(calService.check(todayPay), 0l);
        //process
        Calendar theDay = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if (null != holidayRepo.findByHoliday(sdf.format(theDay.getTime())) || theDay.get(Calendar.DAY_OF_WEEK) == 1 || theDay.get(Calendar.DAY_OF_WEEK) == 7) {
            return new PaymentResponse("Today's payment is 0", 0l);
        }
        int count = 0;
        while (count < 2) {
            theDay.add(Calendar.DATE, -1);
            if (null == holidayRepo.findByHoliday(sdf.format(theDay.getTime())) && theDay.get(Calendar.DAY_OF_WEEK) != 1 && theDay.get(Calendar.DAY_OF_WEEK) != 7) {
                count++;
            }
        }
        if (null == stockBalanceRepo.findTodayBalance(todayPay.getBranchNo(), todayPay.getCustSeq(), sdf.format(theDay.getTime()))) {
            return new PaymentResponse("Today's payment is 0", 0l);
        }
        return new PaymentResponse("", stockBalanceRepo.findTodayBalance(todayPay.getBranchNo(), todayPay.getCustSeq(), sdf.format(theDay.getTime())));
    }

}
