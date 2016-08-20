package com.depvin.pps.presenter;

import com.depvin.pps.business.ReportCreationFailedException;
import com.depvin.pps.business.SessioneCapoProgetto;
import com.depvin.pps.model.CapoProgetto;
import com.depvin.pps.model.Dipendente;
import com.depvin.pps.model.Progetto;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
    private JList listDipendenti;
    private JButton stampaOrdinePerProgettoButton;
    private JButton stampaOrdinePerDipendenteButton;
    private JButton modificaBudgetButton;
    private JTextField textBudget;
    private JLabel labelBudget;
    private JLabel labelMsgBudget;
    private JLabel labelShowMsg;
    private DefaultListModel listModelProg;
    private DefaultListModel listModelDip;

    public SessioneCapoProgettoViewPresenter(final SessioneCapoProgetto sessione) {
        this.sessione = sessione;
        final CapoProgetto cp = sessione.getUtente();

        view = new JFrame("Sessione: " + cp.getNome() + " " + cp.getCognome());
        rootPanel.setPreferredSize(new Dimension(800, 600));
        view.setLocation(275, 50);
        listModelProg = new DefaultListModel();
        final List<Progetto> progs = cp.getProgetti();
        for (Progetto p : progs) {
            listModelProg.addElement(p.getNome());
        }
        listProgetti.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        modificaBudgetButton.setEnabled(false);
        stampaOrdinePerProgettoButton.setEnabled(false);
        stampaOrdinePerDipendenteButton.setEnabled(false);
        listProgetti.setModel(listModelProg);
        listProgetti.setVisible(true);
        view.setContentPane(rootPanel);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.pack();

        listProgetti.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                listModelDip = new DefaultListModel();
                int index = listProgetti.getSelectedIndex();
                //System.out.println(index); //Verrà stampato due volte perchè, ogni volta viene riaggiornata la visibilità delle label
                Progetto prog = cp.getProgetti().get(index);
                List<Dipendente> dip = prog.getDipendenti();
                for (Dipendente d : dip) {
                    listModelDip.addElement(d.getUsername());
                }
                listDipendenti.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                listDipendenti.setModel(listModelDip);
                labelBudget.setText(String.format("%.2f", prog.getBudget()) + " €");
                listDipendenti.setVisible(true);
                textBudget.setVisible(true);
                labelMsgBudget.setVisible(true);
                labelShowMsg.setVisible(true);
                labelBudget.setVisible(true);
                modificaBudgetButton.setEnabled(true);
                stampaOrdinePerProgettoButton.setEnabled(true);
                stampaOrdinePerDipendenteButton.setEnabled(false);
            }
        });

        listDipendenti.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                stampaOrdinePerDipendenteButton.setEnabled(true);
            }
        });

        modificaBudgetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
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
        });

        stampaOrdinePerProgettoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int index = listProgetti.getSelectedIndex();
                System.out.println(index);
                Progetto prog = cp.getProgetti().get(index);
                if (prog.getOrdini().isEmpty())
                    showMessageDialog(getView(), "Il seguente progetto non ha nessun ordine a suo carico");
                else {
                    try {
                        sessione.stampaOrdineProgetto(prog);
                        showMessageDialog(getView(), "Stampa svolta con successo");

                    } catch (ReportCreationFailedException e) {
                        showMessageDialog(getView(), "Errore nella stampa degli ordini");
                    }
                }
            }
        });

        stampaOrdinePerDipendenteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int indexProg = listProgetti.getSelectedIndex();
                int indexDip = listDipendenti.getSelectedIndex();
                System.out.println(indexDip);
                System.out.println(indexProg);
                Progetto prog = cp.getProgetti().get(indexProg);
                Dipendente dip = prog.getDipendenti().get(indexDip);
                if (prog.getOrdini().isEmpty())
                    showMessageDialog(getView(), "Il dipendente in questione non ha effettuato nessun ordine");
                else {
                    try {
                        sessione.stampaOrdineDipendente(dip, prog);
                        showMessageDialog(getView(), "Stampa svolta con successo");
                    } catch (ReportCreationFailedException e) {
                        showMessageDialog(getView(), "Errore nella stampa degli ordini");
                    }
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