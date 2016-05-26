package com.depvin.pps.startup;

import com.depvin.pps.presenter.LoginViewPresenter;

/**
 * Created by Michele De Pascalis on 01/12/15.
 */

public class StartUp {
    public static void main(String args[]) {
        // DBInterface.getInstance().getEntityManager();
        LoginViewPresenter mainPresenter = new LoginViewPresenter();
        mainPresenter.show();
    }
}
