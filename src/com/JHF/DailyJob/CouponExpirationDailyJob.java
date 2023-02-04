package com.JHF.DailyJob;

import com.JHF.javaBeans.Coupon;
import com.JHF.DAO.CouponsDAO;
import com.JHF.DAO.CouponsDBDAO;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class CouponExpirationDailyJob implements Runnable {
    private CouponsDAO couponsDAO;
    private boolean quit;
    private long deltaTime;
    final static int MILLIS_TO_DAY = 24 * 60 * 60 * 100;

    public CouponExpirationDailyJob() {
        couponsDAO = new CouponsDBDAO();
        deltaTime =0;
        quit = false;
    }

    @Override
    public void run() {
        while (!quit)
        {
            if ((System.currentTimeMillis() - deltaTime >= MILLIS_TO_DAY)) {
                for (Coupon coupon : couponsDAO.getAllCoupons()) {
                    if (coupon.getEndDate().after(new Date())) {
                        couponsDAO.deleteCouponPurchase(coupon.getId());
                        couponsDAO.deleteCoupon(coupon.getId());
                    }
                }
                deltaTime = System.currentTimeMillis();
            }
        }
        System.err.println("out of loop");
    }
    public void stop() {
        quit = true;
        //System.exit(0);
    }
}
