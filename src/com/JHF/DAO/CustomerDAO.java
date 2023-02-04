package com.JHF.DAO;

import com.JHF.ClientTypes.Customer;

import java.util.ArrayList;

public interface CustomerDAO {
    // check if the customer exists in the db
    boolean isCustomerExists(String email,String password);
    // adding the customer to the db
    void addCustomer(Customer customer);
    // updating the customer in the db
    void updateCustomer(Customer customer);
    // deleting customer from db
    void deleteCustomer(int customerID);
    // getting all customers from db
    ArrayList<Customer> getAllCustomers();
    // getting customer from db by customer id
    Customer getOneCustomer(int customerID);
    // getting customer by email for login in
    Customer getOneCustomer(String email);

}
