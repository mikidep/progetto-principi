package com.depvin.pps.presenter;

import com.depvin.pps.dao.CategoriaDAO;
import com.depvin.pps.model.Categoria;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

public class ModificaCategorieViewPresenter {
    private List<Categoria> categorie;
    private List<Categoria> categorieDisponibili;

    private JFrame view;
    private JPanel rootPanel;
    private JList categorieJList;
    private JComboBox categorieComboBox;
    private JButton aggiungiButton;
    private JButton rimuoviSelezioneButton;

    public ModificaCategorieViewPresenter(List<Categoria> _categorie) {
        this.categorie = _categorie;
        view = new JFrame("SelegetAllCategorie()...");
        rootPanel.setPreferredSize(new Dimension(500, 400));
        view.setLocation(250, 0);
        view.setContentPane(rootPanel);
        view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        view.pack();

        rimuoviSelezioneButton.setEnabled(false);

        categorieComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selected = ((String) categorieComboBox.getSelectedItem()).trim();
                if (selected.equals("")) {
                    return;
                }

                aggiungiButton.setEnabled(true);
                checkAggiungiEnabled();

                for (Categoria af : categorieDisponibili) {
                    if (af.getNome().equals(selected)) {
                        return;
                    }
                }
                // Se il for sopra passa indisturbato, il nome è nuovo
                CategoriaDAO.addCategoriaWithNome(selected);
                updateComboBox();
                categorieComboBox.setSelectedItem(selected);
                checkAggiungiEnabled();
            }
        });

        rimuoviSelezioneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                categorie.remove(categorieJList.getSelectedIndex());
                updateJList();
                checkAggiungiEnabled();
            }
        });

        aggiungiButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                categorie.add(categorieDisponibili.get(categorieComboBox.getSelectedIndex()));
                updateJList();
                checkAggiungiEnabled();
            }
        });

        categorieJList.addListSelectionListener(new ListSelectionListener() {
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
        for (Categoria f : categorie) {
            nomi.add(f.getNome());
        }
        categorieJList.setListData(nomi);
        rimuoviSelezioneButton.setEnabled(false);
    }

    private void updateComboBox() {
        categorieDisponibili = CategoriaDAO.getAllCategorie();
        Vector<String> nomi = new Vector<String>();
        for (Categoria f : categorieDisponibili) {
            nomi.add(f.getNome());
        }
        categorieComboBox.setModel(new DefaultComboBoxModel(nomi));
    }

    private void checkAggiungiEnabled() {
        if (categorieComboBox.getModel().getSize() == 0) {
            aggiungiButton.setEnabled(false);
        }

        // Non è che il fornitore selezionato è già in lista?
        String selected = ((String) categorieComboBox.getSelectedItem()).trim();
        for (Categoria f : categorie) {
            if (f.getNome().equals(selected)) {
                aggiungiButton.setEnabled(false);
            }
        }
    }

    public JFrame getView() {
        return view;
    }
}
