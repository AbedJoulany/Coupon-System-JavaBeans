package com.JHF.Facade;

import com.JHF.Enums.Category;
import com.JHF.ClientTypes.Company;
import com.JHF.javaBeans.Coupon;

import java.sql.SQLException;
import java.util.ArrayList;

public class CompanyFacade  extends ClientFacade{
    public CompanyFacade() {
    }

    public boolean login(String email, String password) {
        if(companiesDAO.isCompanyExists(email, password)){
            this.companyId = companiesDAO.getOneCompany(email).getId();
            return true;
        }
        return false;
    }

    public void addCoupon(Coupon coupon) throws SQLException {
        for (Coupon coupon1 : couponsDAO.getAllCoupons()) {
            if (coupon.getTitle().equals(coupon1.getTitle()) &&
                    coupon1.getCompanyID() == coupon.getCompanyID())
                    throw new SQLException("coupon with same name and same company already exists");
        }

        couponsDAO.addCoupon(coupon);
    }

    public void updateCoupon(Coupon coupon) throws SQLException {
        Coupon coupon1 = couponsDAO.getOneCoupon(coupon.getId());
        if (coupon1 != null &&
                coupon1.getCompanyID() == coupon.getCompanyID()) {
            couponsDAO.updateCoupon(coupon);
        }
        else
            throw new SQLException("coupon_id and company_id can`t be updated");
    }

    public void deleteCoupon(int couponID) throws SQLException {
        if(couponsDAO.getOneCoupon(couponID) == null)
            throw new SQLException("coupon not found");
        couponsDAO.deleteCouponPurchase(couponID);
        couponsDAO.deleteCoupon(couponID);
    }

    public ArrayList<Coupon> getCompanyCoupons() {
       return getCompanyDetails().getCoupons();
    }

    public ArrayList<Coupon> getCompanyCoupons(Category category) {
        return couponsDAO.getAllCouponsByCompanyIdAndCategory(companyId,category);
    }

    public ArrayList<Coupon> getCompanyCoupons(double maxPrice) {
            return couponsDAO.getAllCouponsByCompanyIdAndMaxPrice(companyId,maxPrice);
    }

    public Company getCompanyDetails() {
        return companiesDAO.getOneCompany(companyId);
    }

    private int companyId;
}
