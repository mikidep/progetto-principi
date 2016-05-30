package com.depvin.pps.presenter;

import com.depvin.pps.business.SessioneMagazziniere;
import com.depvin.pps.model.Magazziniere;

import javax.swing.*;
import java.awt.*;

/**
 * Created by costantino on 24/05/16.
 */
public class SessioneMagazziniereViewPresenter {
    SessioneMagazziniere sessione;

    private JFrame view;
    private JPanel rootPanel;
    private JButton rifornisciMagazzinoButton;
    private JButton ottieniListaOrdiniButton;
    private JList list1;

    public SessioneMagazziniereViewPresenter(SessioneMagazziniere sessione) {
        this.sessione = sessione;
        Magazziniere m = sessione.getUtente();
        view = new JFrame("Sessione: " + m.getNome() + " " + m.getCognome());
        rootPanel.setPreferredSize(new Dimension(500, 250));
        view.setLocation(300, 300);

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
