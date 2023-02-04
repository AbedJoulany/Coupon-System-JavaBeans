package com.JHF.DAO;

import com.JHF.Enums.Category;
import com.JHF.javaBeans.Coupon;

import java.util.ArrayList;

public interface CouponsDAO
{
    // method to add coupon to db
    void addCoupon(Coupon coupon);
    // method to update coupon in db
    void updateCoupon(Coupon coupon);
    // method to delete coupon
    void deleteCoupon(int couponID);
    // method to get all coupons from db
    ArrayList<Coupon> getAllCoupons();
    // get all coupons by company id
    ArrayList<Coupon> getAllCouponsByCompanyId(int companyId);
    // get all coupons by customer id
    ArrayList<Coupon> getAllCouponsByCustomerId(int customerId);
    // get all coupons by company id and category
    ArrayList<Coupon> getAllCouponsByCompanyIdAndCategory(int companyId, Category category);
    // get all coupons by customer id and category
    ArrayList<Coupon> getAllCouponsByCustomerIdAndCategory(int customerId, Category category);
    // get all coupons by company id and max price
    ArrayList<Coupon> getAllCouponsByCompanyIdAndMaxPrice(int companyId, double maxPrice);
    // get all coupons by customer id and category
    ArrayList<Coupon> getAllCouponsByCustomerIdAndMaxPrice(int customerId, double maxPrice);
    // get one coupon
    Coupon getOneCoupon(int couponID);
    // purchase a coupon
    void addCouponPurchase(int customerID,int couponID);
    // delete coupon purchase
    void deleteCouponPurchase(int couponID);
}
