package com.depvin.pps.presenter;

import com.depvin.pps.business.ReportCreationFailedException;
import com.depvin.pps.business.SessioneCapoProgetto;
import com.depvin.pps.business.UserLoadingException;
import com.depvin.pps.business.UserNotFoundException;
import com.depvin.pps.model.CapoProgetto;
import com.depvin.pps.model.Dipendente;
import com.depvin.pps.model.Ordine;
import com.depvin.pps.model.Progetto;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
    private JTabbedPane tabbedPane1;
    private JComboBox progettoAddBox;
    private JTextField pwdTextField;
    private JTextField usernameTextField;
    private JButton aggiungiDipendenteAlProgettoButton;
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
            progettoAddBox.addItem(p.getNome());
        }
        progettoAddBox.setSelectedIndex(-1);
        listProgetti.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        modificaBudgetButton.setEnabled(false);
        stampaOrdinePerProgettoButton.setEnabled(false);
        stampaOrdinePerDipendenteButton.setEnabled(false);
        listProgetti.setModel(listModelProg);
        listProgetti.setVisible(true);
        view.setContentPane(rootPanel);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tabbedPane1.setVisible(true);
        view.pack();

        listProgetti.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                listModelDip = new DefaultListModel();
                int index = listProgetti.getSelectedIndex();
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
                Progetto prog = cp.getProgetti().get(index);
                if (prog.getOrdini().isEmpty())
                    showMessageDialog(getView(), "Il seguente progetto non ha nessun ordine a suo carico");
                else {
                    //TODO:
                    // A seconda del computer che verrà presentato verrà cambiata la radice in cui salvare la stampa
                    try {
                        GregorianCalendar gc = new GregorianCalendar();
                        ByteArrayOutputStream bytes = sessione.stampaOrdineProgetto(prog);
                        FileOutputStream of = new FileOutputStream("/home/costantino/pdf_progetto/" + cp.getNome() + "_" +
                                prog.getNome() + "_" + gc.get(Calendar.DATE) + "_" + gc.get(Calendar.MONTH) + "_" +
                                gc.get(Calendar.YEAR) + "_" + gc.get(Calendar.HOUR) + "_" + gc.get(Calendar.MINUTE) +
                                ".pdf");
                        bytes.writeTo(of);
                        of.close();

                        showMessageDialog(getView(), "Stampa svolta con successo");

                    } catch (ReportCreationFailedException e) {
                        showMessageDialog(getView(), "Errore nella stampa degli ordini");
                    } catch (IOException e) {
                        showMessageDialog(getView(), "IOexception, impossibile stampare");
                    }
                }
            }
        });

        stampaOrdinePerDipendenteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int indexProg = listProgetti.getSelectedIndex();
                int indexDip = listDipendenti.getSelectedIndex();
                Progetto prog = cp.getProgetti().get(indexProg);
                Dipendente dip = prog.getDipendenti().get(indexDip);
                boolean ordinePresente = false;
                for (Ordine o : dip.getOrdini())
                    if (o.getDipendente().getNome().equals(dip.getNome()))
                        ordinePresente = true;
                if (prog.getOrdini().isEmpty() || !ordinePresente)
                    showMessageDialog(getView(), "Il dipendente in questione non ha effettuato nessun ordine");
                else {
                    //TODO:
                    // A seconda del computer che verrà presentato verrà cambiata la radice in cui salvare la stampa
                    try {
                        GregorianCalendar gc = new GregorianCalendar();
                        ByteArrayOutputStream bytes = sessione.stampaOrdineDipendente(dip, prog);
                        FileOutputStream of = new FileOutputStream("/home/costantino/pdf_progetto/" + cp.getNome() + "_" +
                                prog.getDipendenti().get(indexDip).getNome() + "_" + prog.getNome() + "_" +
                                gc.get(Calendar.DATE) + "_" + gc.get(Calendar.MONTH) + "_" + gc.get(Calendar.YEAR) +
                                "_" + gc.get(Calendar.HOUR) + "_" + gc.get(Calendar.MINUTE) + ".pdf");
                        bytes.writeTo(of);
                        of.close();

                        showMessageDialog(getView(), "Stampa svolta con successo");
                    } catch (ReportCreationFailedException e) {
                        showMessageDialog(getView(), "Errore nella stampa degli ordini");
                    } catch (IOException e) {
                        showMessageDialog(getView(), "IOexception, impossibile stampare");
                    }
                }
            }
        });

        aggiungiDipendenteAlProgettoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (usernameTextField.getText().length() == 0 || pwdTextField.getText().length() == 0 ||
                        progettoAddBox.getSelectedIndex() == -1)
                    showMessageDialog(getView(), "I campi \"username dipendente\", \"password dipendente\" e" +
                            " \"Scegli il Progetto\" non possono essere lasciati vuoti");
                else {
                    try {
                        Dipendente dipendente = (Dipendente) sessione.ottieniUtente(usernameTextField.getText(),
                                pwdTextField.getText());
                        int indexProgetto = progettoAddBox.getSelectedIndex();
                        Progetto progetto = cp.getProgetti().get(indexProgetto);
                        sessione.aggiungiDipendenteProgetto(progetto, dipendente);
                        showMessageDialog(getView(), "Dipendente aggiunto al Progetto con successo");
                    } catch (UserNotFoundException e) {
                        showMessageDialog(getView(), "Utente già inesistente");
                    } catch (UserLoadingException e) {
                        showMessageDialog(getView(), "Errore nel caricamento");
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