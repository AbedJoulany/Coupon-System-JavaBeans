package com.JHF.DAO;

import com.JHF.ClientTypes.Company;

import java.util.ArrayList;

public interface CompaniesDAO {

    // is company exists in db
    boolean isCompanyExists(String email,String password);
    // add company to db
    void addCompany(Company company);
    // update company in db
    void updateCompany(Company company);
    // delete company from db
    void deleteCompany(int companyID);
    // get all companies from db
    ArrayList<Company> getAllCompanies();
    // get one company from db
    Company getOneCompany(int companyID);
    // get one company from db by email for login in
    Company getOneCompany(String email);



}
