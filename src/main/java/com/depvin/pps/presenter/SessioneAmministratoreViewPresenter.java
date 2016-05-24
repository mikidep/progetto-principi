package com.depvin.pps.presenter;

import com.depvin.pps.business.SessioneAmministratore;
import com.depvin.pps.model.Amministratore;
import com.depvin.pps.model.Utente;

import javax.swing.*;
import java.awt.*;

/**
 * Created by costantino on 24/05/16.
 */
public class SessioneAmministratoreViewPresenter {
    SessioneAmministratore sessione;

    private JFrame view;
    private JPanel rootPanel;
    private JButton buttonAdmin;
    private JButton buttonDip;
    private JButton buttonMag;
    private JButton buttonCapo;
    private JButton buttonProg;

    public SessioneAmministratoreViewPresenter(SessioneAmministratore sessione) {
        this.sessione = sessione;
        Amministratore a = sessione.getUtente();
        view = new JFrame("Sessione: " + a.getNome() + " " + a.getCognome());
        rootPanel.setPreferredSize(new Dimension(300, 400));
        view.setLocation(550, 100);
        view.setContentPane(rootPanel);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // TODO: negli altri

        view.pack();
    }

    public void show() {
        view.setVisible(true);
    }

    public JFrame getView() {
        return view;
    }

}
