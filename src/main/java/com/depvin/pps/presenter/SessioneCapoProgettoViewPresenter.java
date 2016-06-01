package com.depvin.pps.presenter;

import com.depvin.pps.business.ReportCreationFailedException;
import com.depvin.pps.business.SessioneCapoProgetto;
import com.depvin.pps.model.CapoProgetto;
import com.depvin.pps.model.Progetto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by costantino on 24/05/16.
 */
public class SessioneCapoProgettoViewPresenter {
    SessioneCapoProgetto sessione;

    private JFrame view;
    private JPanel rootPanel;
    private JList listProgetti;
    private JButton stampaOrdinePerProgettoButton;
    private JList listDipendenti;
    private JButton stampaOrdinePerDipendenteButton;
    private JTextField textDate;
    private JLabel labelDipendenti;
    private JLabel labelBudget;
    private JButton modificaBudgetButton;
    private JTextField textBudget;
    private JLabel labelMsgBudget;
    private JButton confermaProgettoButton;
    private JButton confermaDipendenteButton;
    private JLabel labelShowMsg;
    private JLabel labelProg;
    private DefaultListModel listModel;


    public SessioneCapoProgettoViewPresenter(final SessioneCapoProgetto sessione) {
        this.sessione = sessione;
        final CapoProgetto cp = sessione.getUtente();

        view = new JFrame("Sessione: " + cp.getNome() + " " + cp.getCognome());
        rootPanel.setPreferredSize(new Dimension(1500, 800));
        view.setLocation(0, 0);

        listModel = new DefaultListModel();
        final List<Progetto> progs = cp.getProgetti();
        for (Progetto p : progs) {
            listModel.addElement(p.getNome());
        }

        listProgetti.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        modificaBudgetButton.setEnabled(false);
        stampaOrdinePerProgettoButton.setEnabled(false);
        stampaOrdinePerDipendenteButton.setEnabled(false);
        listProgetti.setModel(listModel);
        listProgetti.setVisible(true);
        view.setContentPane(rootPanel);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.pack();


        confermaProgettoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (actionEvent.getSource() == confermaProgettoButton) {
                    // TODO:To Fix ArrayIndexOutOfBoundsException
                    // and remove the conferma button and add a listener whene a project is selected
                    int index = listProgetti.getSelectedIndex();
                    System.out.println(index);
                    Progetto prog = cp.getProgetti().get(index);
                    labelProg.setText("Progetto correntemente aperto: " + prog.getNome());
                    // TODO:Estrarre tutti i dipendenti dal progetto
                    labelBudget.setText(String.format("%.2f", prog.getBudget()) + " €");
                    labelProg.setVisible(true);
                    textBudget.setVisible(true);
                    labelMsgBudget.setVisible(true);
                    labelShowMsg.setVisible(true);
                    labelBudget.setVisible(true);
                    modificaBudgetButton.setEnabled(true);
                }
            }
        });

        stampaOrdinePerProgettoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (actionEvent.getSource() == stampaOrdinePerProgettoButton) {
                    //TODO:To Fix ArrayIndexOutOfBoundsException
                    int index = listProgetti.getSelectedIndex();
                    System.out.println(index);
                    Progetto prog = cp.getProgetti().get(index);
                    try {
                        sessione.stampaOrdineProgetto(prog);
                    } catch (ReportCreationFailedException e) {
                        showMessageDialog(getView(), "Errore nella stampa degli ordini");
                    }
                }
            }
        });

        modificaBudgetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (actionEvent.getSource() == modificaBudgetButton) {
                    if (textBudget.getText().length() == 0) {
                        showMessageDialog(getView(), "Il campo Budget non può essere lasciato vuoto");
                    } else {
                        int index = listProgetti.getSelectedIndex();
                        Progetto prog = cp.getProgetti().get(index);
                        String newBudget = textBudget.getText();
                        newBudget = newBudget.replaceAll(",", ".");
                        if (!newBudget.contains("."))
                            newBudget = newBudget + ".00";
                        float budget = Float.parseFloat(newBudget);
                        sessione.modificaBudget(prog, budget);
                        labelBudget.setText(String.format("%.2f", prog.getBudget()) + " €");
                        labelBudget.setVisible(true);
                    }
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
}
