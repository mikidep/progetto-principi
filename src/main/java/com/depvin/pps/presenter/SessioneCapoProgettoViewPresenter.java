package com.depvin.pps.presenter;

import com.depvin.pps.business.SessioneCapoProgetto;
import com.depvin.pps.model.CapoProgetto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by costantino on 24/05/16.
 */
public class SessioneCapoProgettoViewPresenter {
    SessioneCapoProgetto sessione;

    private JFrame view;
    private JPanel rootPanel;
    private JComboBox comboBox1;

    public SessioneCapoProgettoViewPresenter(SessioneCapoProgetto sessione) {
        this.sessione = sessione;
        CapoProgetto cp = sessione.getUtente();
        view = new JFrame("Sessione: " + cp.getNome() + " " + cp.getCognome());
        rootPanel.setPreferredSize(new Dimension(300, 300));
        view.setLocation(300, 300);
        List prog = cp.getProgetti();
        comboBox1.addItem(prog);

        view.setContentPane(rootPanel);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // TODO: Collegare gli altri listener

        view.pack();
    }

    public void show() {
        view.setVisible(true);
    }

    public JFrame getView() {
        return view;
    }

}
