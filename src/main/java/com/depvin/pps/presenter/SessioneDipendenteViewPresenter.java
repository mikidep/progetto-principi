package com.depvin.pps.presenter;

import com.depvin.pps.business.SessioneDipendente;
import com.depvin.pps.model.Articolo;
import com.depvin.pps.model.Categoria;
import com.depvin.pps.model.Dipendente;
import com.depvin.pps.model.Prodotto;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by costantino on 24/05/16.
 */
public class SessioneDipendenteViewPresenter {
    SessioneDipendente sessione;

    private JFrame view;
    private JPanel rootPanel;
    private JButton ottieniOrdiniPendentiButton;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton sfogliaCatalogoButton;
    private JComboBox comboBox3;

    public SessioneDipendenteViewPresenter(SessioneDipendente sessione) {
        this.sessione = sessione;
        Dipendente d = sessione.getUtente();
        view = new JFrame("Sessione: " + d.getNome() + " " + d.getCognome());
        rootPanel.setPreferredSize(new Dimension(450, 350));
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
