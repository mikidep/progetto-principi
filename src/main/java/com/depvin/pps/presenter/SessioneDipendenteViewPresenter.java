package com.depvin.pps.presenter;

import com.depvin.pps.business.SessioneDipendente;
import com.depvin.pps.model.Dipendente;

import javax.swing.*;
import java.awt.*;

/**
 * Created by costantino on 24/05/16.
 */
public class SessioneDipendenteViewPresenter {
    SessioneDipendente sessione;

    private JFrame view;
    private JPanel rootPanel;

    public SessioneDipendenteViewPresenter(SessioneDipendente sessione) {
        this.sessione = sessione;
        Dipendente d = sessione.getUtente();
        view = new JFrame("Sessione: " + d.getNome() + " " + d.getCognome());
        rootPanel.setPreferredSize(new Dimension(300, 400));
        view.setLocation(550, 100);
        view.setContentPane(rootPanel);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        view.pack();
    }


    public void show() {
        view.setVisible(true);
    }

    public JFrame getView() {
        return view;
    }

}
