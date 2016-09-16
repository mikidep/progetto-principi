package com.depvin.pps.startup;

import com.depvin.pps.presenter.LoginViewPresenter;


public class StartUp {
    public static void main(String args[]) {
        // DBInterface.getInstance().getEntityManager();
        LoginViewPresenter mainPresenter = new LoginViewPresenter();
        mainPresenter.show();
    }
}
