package com.JHF.Facade;

import com.JHF.DAO.*;

public abstract class ClientFacade {

    public ClientFacade() {
        companiesDAO = new CompaniesDBDAO();
        couponsDAO = new CouponsDBDAO();
        customerDAO = new CustomerDBAO();
    }


    public abstract boolean login(String email, String password);

    protected CouponsDAO couponsDAO;
    protected CompaniesDAO companiesDAO;
    protected CustomerDAO customerDAO;
}
