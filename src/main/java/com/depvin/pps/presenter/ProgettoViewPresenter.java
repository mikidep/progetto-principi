package com.depvin.pps.presenter;

import com.depvin.pps.model.Progetto;

import javax.swing.*;
import java.awt.*;

/**
 * Created by costantino on 31/05/16.
 */
public class ProgettoViewPresenter {
    Progetto progetto;

    private JPanel rootPanel;
    private JButton modificaBudgetButton;
    private JButton stampaOrdinePerDipendenteButton;
    private JLabel labelBudget;
    private JButton tornaIndietroButton;
    private JFrame view;

    public ProgettoViewPresenter(Progetto progetto) {
        this.progetto = progetto;
        view = new JFrame("Progetto: " + progetto.getNome());
        labelBudget.setText(String.format("%.2f", progetto.getBudget()) + " €");

        rootPanel.setPreferredSize(new Dimension(500, 300));
        view.setLocation(300, 300);

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

    public void hide() {
        view.setVisible(false);
    }

}
