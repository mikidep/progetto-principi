package com.depvin.pps.presenter;

import com.depvin.pps.business.SessioneCapoProgetto;
import com.depvin.pps.business.Sistema;
import com.depvin.pps.model.CapoProgetto;
import com.depvin.pps.model.Progetto;
import sun.org.mozilla.javascript.v8dtoa.CachedPowers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by costantino on 31/05/16.
 */
public class ProgettoViewPresenter {
    Progetto progetto;
    JFrame oldView;
    SessioneCapoProgetto scp;

    private JPanel rootPanel;
    private JButton modificaBudgetButton;
    private JButton stampaOrdinePerDipendenteButton;
    private JLabel labelBudget;
    private JButton tornaIndietroButton;
    private JTextField textBudget;
    private JTextField textNameSurname;
    private JTextField textDate;
    private JFrame view;

    public ProgettoViewPresenter(final Progetto progetto, final JFrame oldView, final SessioneCapoProgetto scp) {
        this.progetto = progetto;
        this.oldView = oldView;
        this.scp = scp;

        view = new JFrame("Progetto: " + progetto.getNome());
        labelBudget.setText(String.format("%.2f", progetto.getBudget()) + " €");

        rootPanel.setPreferredSize(new Dimension(500, 300));
        view.setLocation(300, 300);

        view.setContentPane(rootPanel);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        view.pack();

        tornaIndietroButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (actionEvent.getSource() == tornaIndietroButton) {
                    view.setVisible(false);
                    oldView.setVisible(true);
                }
            }
        });
        modificaBudgetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (actionEvent.getSource() == modificaBudgetButton) {
                    String newBudget = textBudget.getText();
                    newBudget = newBudget.replaceAll(",", ".");
                    float budget = Float.parseFloat(newBudget);
                    scp.modificaBudget(progetto, budget);
                    newBudget = newBudget.replaceAll("\\.", ",");
                    labelBudget.setText(newBudget + " €");
                    labelBudget.setVisible(true);
                }
            }
        });

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
