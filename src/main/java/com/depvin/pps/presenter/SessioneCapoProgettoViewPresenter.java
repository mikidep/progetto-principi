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
    private JList list1;
    private JButton confermaButton;
    private JButton stampaOrdinePerProgettoButton;
    private DefaultListModel listModel;


    public SessioneCapoProgettoViewPresenter(final SessioneCapoProgetto sessione) {
        this.sessione = sessione;
        final CapoProgetto cp = sessione.getUtente();
        view = new JFrame("Sessione: " + cp.getNome() + " " + cp.getCognome());
        rootPanel.setPreferredSize(new Dimension(300, 300));
        view.setLocation(300, 300);

        listModel = new DefaultListModel();
        final List<Progetto> progs = cp.getProgetti();
        for (Progetto p : progs) {
            listModel.addElement(p.getNome());
        }
        list1.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list1.setModel(listModel);
        list1.setVisible(true);

        view.setContentPane(rootPanel);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        view.pack();

        confermaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (actionEvent.getSource() == confermaButton) {
                    //TODO:To Fix ArrayIndexOutOfBoundsException
                    int index = list1.getSelectedIndex();
                    System.out.println(index);
                    Progetto prog = cp.getProgetti().get(index);
                    ProgettoViewPresenter proge = new ProgettoViewPresenter(prog, view, sessione);
                    view.setVisible(false);
                    proge.show();
                }
            }
        });
        stampaOrdinePerProgettoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (actionEvent.getSource() == stampaOrdinePerProgettoButton) {
                    //TODO:To Fix ArrayIndexOutOfBoundsException
                    int index = list1.getSelectedIndex();
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


    }

    public void show() {
        view.setVisible(true);
    }

    public JFrame getView() {
        return view;
    }
}
