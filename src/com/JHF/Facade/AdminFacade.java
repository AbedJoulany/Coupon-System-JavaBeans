package com.JHF.Facade;

import com.JHF.ClientTypes.Company;
import com.JHF.javaBeans.Coupon;
import com.JHF.ClientTypes.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminFacade extends ClientFacade {

    @Override
    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    public void addCompany(Company company) throws SQLException {
        if (!companiesDAO.isCompanyExists(company.getEmail(), company.getPassword())
                && isCompanyNameNotExists(company))
        {
            companiesDAO.addCompany(company);
        }
        else
            throw new SQLException("name or email of the company already exists");
    }

    public void updateCompany(Company company) throws SQLException {

        Company co = companiesDAO.getOneCompany(company.getId());
        if (co != null && co.getName().equals(company.getName())) {
            companiesDAO.updateCompany(company);
        }
        else
            throw new SQLException("company_id and company name can`t be updated");
    }

    public void deleteCompany(int companyId) throws SQLException {
        Company company = companiesDAO.getOneCompany(companyId);
        if (company == null)
            throw new SQLException("company isn`t found");

        ArrayList<Coupon> companyCoupons = company.getCoupons();

        for (Coupon coupon : companyCoupons) {
            couponsDAO.deleteCouponPurchase(coupon.getId());
            couponsDAO.deleteCoupon(coupon.getId());
        }
        companiesDAO.deleteCompany(companyId);
    }

    public ArrayList<Company> getAllCompanies() {
        return companiesDAO.getAllCompanies();
    }

    public Company getOneCompany(int companyID) {
        return companiesDAO.getOneCompany(companyID);
    }

    public void addCustomer(Customer customer) throws SQLException {
        if (!customerDAO.isCustomerExists(customer.getEmail(), customer.getPassword())) {
            customerDAO.addCustomer(customer);
        }
        else
            throw new SQLException("email of the customer already exists");
    }

    public void updateCustomer(Customer customer) throws SQLException {
        if (customerDAO.isCustomerExists(customer.getEmail(), customer.getPassword()) &&
                customerDAO.getOneCustomer(customer.getId()) != null) {
            customerDAO.updateCustomer(customer);
        }
        else
            throw new SQLException("customer_id and customer name can`t be updated");
    }

    public void deleteCustomer(int customerID) throws SQLException {
        Customer customer = customerDAO.getOneCustomer(customerID);
        if (customer == null)
            throw new SQLException("customer isn`t found");

        for (Coupon coupon : customer.getCoupons()) {
            couponsDAO.deleteCouponPurchase(coupon.getId());
        }
        customerDAO.deleteCustomer(customerID);
    }

    public ArrayList<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    public Customer getOneCustomer(int customerId) {
        return customerDAO.getOneCustomer(customerId);
    }

    private boolean isCompanyNameNotExists(Company company) {
        for (Company company1 : companiesDAO.getAllCompanies()) {
            if (company1.getName().equals(company.getName())) {
                return false;
            }
        }
        return true;
    }

    private String email = "admin@admin.com";
    private String password = "admin";
}
