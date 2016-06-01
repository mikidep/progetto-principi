package com.depvin.pps.presenter;

import com.depvin.pps.business.SessioneCapoProgetto;
import com.depvin.pps.model.Progetto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by costantino on 31/05/16.
 */
public class ProgettoViewPresenter {
    Progetto progetto;
    JFrame oldView;
    SessioneCapoProgetto sessioneCapoProgetto;

    private JPanel rootPanel;
    private JButton modificaBudgetButton;
    private JButton stampaOrdinePerDipendenteButton;
    private JLabel labelBudget;
    private JButton tornaIndietroButton;
    private JTextField textBudget;
    private JTextField textDate;
    private JList list1;
    private JFrame view;
    private DefaultListModel listModel;

    public ProgettoViewPresenter(final Progetto progetto, final JFrame oldView, final SessioneCapoProgetto sessioneCapoProgetto) {
        this.progetto = progetto;
        this.oldView = oldView;
        this.sessioneCapoProgetto = sessioneCapoProgetto;

        view = new JFrame("Progetto: " + progetto.getNome());
        labelBudget.setText(String.format("%.2f", progetto.getBudget()) + " €");

        listModel = new DefaultListModel();
        // TODO:Estrarre tutti i dipendenti dal progetto
        list1.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list1.setModel(listModel);
        list1.setVisible(true);


        rootPanel.setPreferredSize(new Dimension(600, 300));
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
                    if (newBudget.length() == 0)
                        newBudget = "0.00";
                    newBudget = newBudget.replaceAll(",", ".");
                    if (!newBudget.contains("."))
                        newBudget = newBudget + ".00";
                    float budget = Float.parseFloat(newBudget);
                    sessioneCapoProgetto.modificaBudget(progetto, budget);
                    newBudget = newBudget.replaceAll("\\.", ",");
                    labelBudget.setText(newBudget + " €");
                    labelBudget.setVisible(true);
                }
            }
        });

        stampaOrdinePerDipendenteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (textDate.getText().length() == 0) {
                    showMessageDialog(getView(), "Il campo  \"data\" non può essere lasciato vuoto");
                } else {
                    //TODO: Chiamare il metodo della stampa dell'ordine del dipendente
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
