package com.JHF.Singletones;

import com.JHF.Enums.ClientType;
import com.JHF.Facade.AdminFacade;
import com.JHF.Facade.ClientFacade;
import com.JHF.Facade.CompanyFacade;
import com.JHF.Facade.CustomerFacade;

public class LoginManager {
    private static LoginManager loginManager;

    private LoginManager(){

    }

    public static LoginManager getInstance() {
        if (loginManager == null) {
            loginManager = new LoginManager();
        }
        return loginManager;
    }

    public ClientFacade login(String email, String password, ClientType clientType){
        ClientFacade facade = null;
        switch (clientType){
            case Administrator:
                facade = new AdminFacade();
                break;
            case Company:
                facade = new CompanyFacade();
                break;
            case Customer:
                facade = new CustomerFacade();
                break;
            default:
                return null;
        }
        if(facade.login(email, password))
            return facade;
        return null;
    }
}
