package com.JHF.Facade;

import com.JHF.Enums.Category;
import com.JHF.javaBeans.Coupon;
import com.JHF.ClientTypes.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class CustomerFacade extends ClientFacade {

    public CustomerFacade() {
    }

    @Override
    public boolean login(String email, String password) {
        if(customerDAO.isCustomerExists(email, password)){
            this.customerId = customerDAO.getOneCustomer(email).getId();
            return true;
        }
        return false;
    }
    public void purchaseCoupon(Coupon coupon) throws SQLException {
        Date date = new Date();
            if (!getCustomerDetails().getCoupons().contains(coupon) &&
                    couponsDAO.getOneCoupon(coupon.getId()).getAmount() > 0 &&
                    coupon.getEndDate().after(date))
            {
                couponsDAO.addCouponPurchase(customerId, coupon.getId());
                ArrayList<Coupon> coupons = customerDAO.getOneCustomer(customerId).getCoupons();
                coupons.add(coupon);
                Customer customer = customerDAO.getOneCustomer(customerId);
                customer.setCoupons(coupons);
                customerDAO.updateCustomer(customer);
            }
            else
                throw new SQLException("purchase coupon failed, \n" +
                        "coupon can`t be purchased more than once\n" +
                        "or coupons are out\n" +
                        "or coupon has expired");
    }

    public ArrayList<Coupon> getCustomerCoupons() {
        return getCustomerDetails().getCoupons();
    }

    public ArrayList<Coupon> getCustomerCoupons(Category category) {
        return couponsDAO.getAllCouponsByCustomerIdAndCategory(customerId,category);
    }

    public ArrayList<Coupon> getCustomerCoupons(double maxPrice) {
        return couponsDAO.getAllCouponsByCustomerIdAndMaxPrice(customerId,maxPrice);
    }

    public Customer getCustomerDetails() {
        return customerDAO.getOneCustomer(customerId);
    }
    private int customerId;
}
