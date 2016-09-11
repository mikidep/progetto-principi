package com.depvin.pps.presenter;

import com.depvin.pps.dao.FornitoreDAO;
import com.depvin.pps.model.Fornitore;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

public class ModificaFornitoriViewPresenter {
    private List<Fornitore> fornitori;
    private List<Fornitore> fornitoriDisponibili;

    private JFrame view;
    private JPanel rootPanel;
    private JList fornitoriJList;
    private JComboBox fornitoriComboBox;
    private JButton aggiungiButton;
    private JButton rimuoviSelezioneButton;

    public ModificaFornitoriViewPresenter(List<Fornitore> _fornitori) {
        this.fornitori = _fornitori;
        view = new JFrame("Seleziona Fornitori...");
        rootPanel.setPreferredSize(new Dimension(500, 400));
        view.setLocation(250, 0);
        view.setContentPane(rootPanel);
        view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        view.pack();

        rimuoviSelezioneButton.setEnabled(false);

        fornitoriComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selected = ((String) fornitoriComboBox.getSelectedItem()).trim();
                if (selected.equals("")) {
                    return;
                }

                aggiungiButton.setEnabled(true);
                checkAggiungiEnabled();

                for (Fornitore af : fornitoriDisponibili) {
                    if (af.getNome().equals(selected)) {
                        return;
                    }
                }
                // Se il for sopra passa indisturbato, il nome è nuovo
                FornitoreDAO.addFornitoreWithNome(selected);
                updateComboBox();
                fornitoriComboBox.setSelectedItem(selected);
                checkAggiungiEnabled();
            }
        });

        rimuoviSelezioneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fornitori.remove(fornitoriJList.getSelectedIndex());
                updateJList();
                checkAggiungiEnabled();
            }
        });

        aggiungiButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fornitori.add(fornitoriDisponibili.get(fornitoriComboBox.getSelectedIndex()));
                updateJList();
                checkAggiungiEnabled();
            }
        });

        fornitoriJList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                rimuoviSelezioneButton.setEnabled(true);
            }
        });

        updateComboBox();
        updateJList();
        checkAggiungiEnabled();
    }

    public void show() {
        view.setVisible(true);
    }

    private void updateJList() {
        Vector<String> nomi = new Vector<String>();
        for (Fornitore f : fornitori) {
            nomi.add(f.getNome());
        }
        fornitoriJList.setListData(nomi);
        rimuoviSelezioneButton.setEnabled(false);
    }

    private void updateComboBox() {
        fornitoriDisponibili = FornitoreDAO.getAllFornitori();
        Vector<String> nomi = new Vector<String>();
        for (Fornitore f : fornitoriDisponibili) {
            nomi.add(f.getNome());
        }
        fornitoriComboBox.setModel(new DefaultComboBoxModel(nomi));
    }

    private void checkAggiungiEnabled() {
        if (fornitoriComboBox.getModel().getSize() == 0) {
            aggiungiButton.setEnabled(false);
        }

        // Non è che il fornitore selezionato è già in lista?
        String selected = ((String) fornitoriComboBox.getSelectedItem()).trim();
        for (Fornitore f : fornitori) {
            if (f.getNome().equals(selected)) {
                aggiungiButton.setEnabled(false);
            }
        }
    }

    public JFrame getView() {
        return view;
    }
}
