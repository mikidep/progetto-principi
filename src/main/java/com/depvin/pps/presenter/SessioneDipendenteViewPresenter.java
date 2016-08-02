package com.depvin.pps.presenter;

import com.depvin.pps.business.SessioneDipendente;
import com.depvin.pps.model.*;

import javax.swing.*;
import java.awt.*;


/**
 * Created by costantino on 24/05/16.
 */
public class SessioneDipendenteViewPresenter {
    SessioneDipendente sessione;

    private JFrame view;
    private JPanel rootPanel;
    private JTabbedPane tabbedPane2;
    private JComboBox catalogoBox;
    private JComboBox ProdottoBox;
    private JButton sfogliaCatalogoButton;
    private JComboBox magazzinoBox;
    private JList articoloCatList;
    private JButton aggiungiArticoloAllOrdineButton;
    private JList ordiniPendentiList;
    private JComboBox ordiniPendentiBox;
    private JButton nuovoOrdineButton;
    private JComboBox progettiBox;
    private JButton richiediNotificaButton;
    private JButton modificaOrdineButton;
    private JButton chiudiOrdineButton;
    private JList ordineCorrenteList;
    private JButton modificaQuantitàButton;
    private JButton eliminaArticoloDallOrdineButton;
    private JComboBox progettoOrdineBox;
    private JButton chiudiOrdineButton1;

    public SessioneDipendenteViewPresenter(SessioneDipendente sessione) {
        this.sessione = sessione;
        Dipendente d = sessione.getUtente();
        view = new JFrame("Sessione: " + d.getNome() + " " + d.getCognome());
        rootPanel.setPreferredSize(new Dimension(450, 350));
        view.setLocation(550, 100);
        view.setContentPane(rootPanel);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tabbedPane2.setVisible(true);
        view.pack();
    }


    public void show() {
        view.setVisible(true);
    }

    public JFrame getView() {
        return view;
    }

}
