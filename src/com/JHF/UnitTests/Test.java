package com.JHF.UnitTests;

import com.JHF.ClientTypes.Company;
import com.JHF.ClientTypes.Customer;
import com.JHF.DailyJob.CouponExpirationDailyJob;
import com.JHF.Enums.Category;
import com.JHF.Enums.ClientType;
import com.JHF.Facade.*;
import com.JHF.Singletones.ConnectionPool;
import com.JHF.Singletones.LoginManager;
import com.JHF.javaBeans.Coupon;

import java.util.Calendar;
import java.util.Date;

public class Test {

    public static void testAll() {

        try {
            LoginManager manager = LoginManager.getInstance();

            CouponExpirationDailyJob dailyJob = new CouponExpirationDailyJob();
            Thread thread = new Thread(dailyJob);
            thread.start();
            // ------- Admin Facade ----------------///////
            // ------- crud methods on company ---- ///////
            AdminFacade adminFacade = (AdminFacade) manager.login("admin@admin.com", "admin", ClientType.Administrator);
            Company company = new Company("coffeMaker", "coffeM@gmail.com", "12345", null);
            adminFacade.addCompany(company);
            company.setPassword("123456");
            adminFacade.updateCompany(company);
            System.out.println(adminFacade.getOneCompany(company.getId()));
            System.out.println(adminFacade.getAllCompanies());

            // ------- crud methods on customer ---- ///////
            Customer customer = new Customer("abed", "jou", "abed@abed.com", "12345678", null);
            adminFacade.addCustomer(customer);
            customer.setLastName("joulany");
            adminFacade.updateCustomer(customer);
            System.out.println(adminFacade.getOneCustomer(customer.getId()));
            System.out.println(adminFacade.getAllCustomers());

            // ------- Company Facade ----------------///////
            // ------- CRUD methods on coupon ----------------///////
            CompanyFacade companyFacade = (CompanyFacade) manager.login(company.getEmail(), company.getPassword(), ClientType.Company);
            Coupon coupon = new Coupon(company.getId(), Category.Restaurant, "freeCoffe", "freeCofeeGift",
                    new Date(2021, Calendar.AUGUST, 15), new Date(2022, Calendar.AUGUST, 15), 70,
                    100, "coffee");
            companyFacade.addCoupon(coupon);
            coupon.setDescription("freeCoffee");
            coupon.setPrice(100);
            companyFacade.updateCoupon(coupon);
            System.out.println(companyFacade.getCompanyCoupons(Category.Restaurant));
            System.out.println(companyFacade.getCompanyCoupons());
            System.out.println(companyFacade.getCompanyCoupons(1000));
            // ------- customer Facade ----------------///////
            // ------- CRUD methods on coupon ----------------///////
            CustomerFacade customerFacade = (CustomerFacade) manager.login("abed@abed.com", "12345678", ClientType.Customer);
            customerFacade.purchaseCoupon(coupon);
            System.out.println(customerFacade.getCustomerCoupons());
            System.out.println(customerFacade.getCustomerCoupons(coupon.getCategory()));
            System.out.println(customerFacade.getCustomerCoupons(300));

            // ------- deleting instances ----------------///////
            adminFacade.deleteCompany(company.getId());
            adminFacade.deleteCustomer(customer.getId());
            companyFacade.deleteCoupon(coupon.getId());
            ConnectionPool.getInstance().closeAllConnections();
            dailyJob.stop();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
