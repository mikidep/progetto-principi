package com.depvin.pps.presenter;

import com.depvin.pps.business.ReportCreationFailedException;
import com.depvin.pps.business.SessioneDipendente;
import com.depvin.pps.model.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;


/**
 * Created by costantino on 24/05/16.
 */
public class SessioneDipendenteViewPresenter {
    SessioneDipendente sessione;

    private JFrame view;
    private JPanel rootPanel;
    private JTabbedPane tabbedPane2;
    private JComboBox categoriaBox;
    private JComboBox prodottoBox;
    private JComboBox magazzinoBox;
    private JComboBox progettiBox;
    private JComboBox progettoOrdineBox;
    private JList articoloCatList;
    private JList ordineCorrenteList;
    private JList ordiniPendentiList;
    private JButton aggiungiArticoloAllOrdineButton;
    private JButton sfogliaCatalogoButton;
    private JButton nuovoOrdineButton;
    private JButton richiediNotificaButton;
    private JButton modificaOrdineButton;
    private JButton chiudiOrdineButton1;
    private JButton modificaQuantitàButton;
    private JButton eliminaArticoloDallOrdineButton;
    private JButton cancellaOrdineButton;
    private JTextField catalogoQuantitaField;
    private JTextField ordineQuantitaField;
    private JTextField nomeField;
    private JLabel ordineNomeLabel;
    private DefaultListModel listModelArticoliCatalogo;
    private DefaultListModel listModelOrdinePendente;
    private DefaultListModel listModelArticoliOrdineCorrente;

    public SessioneDipendenteViewPresenter(final SessioneDipendente sessione) {
        this.sessione = sessione;
        final Dipendente d = sessione.getUtente();
        view = new JFrame("Sessione: " + d.getNome() + " " + d.getCognome());
        rootPanel.setPreferredSize(new Dimension(450, 350));
        view.setLocation(550, 100);
        view.setContentPane(rootPanel);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tabbedPane2.setVisible(true);
        modificaOrdineButton.setEnabled(false);
        modificaQuantitàButton.setEnabled(false);
        richiediNotificaButton.setEnabled(false);
        chiudiOrdineButton1.setEnabled(false);
        sfogliaCatalogoButton.setEnabled(false);
        aggiungiArticoloAllOrdineButton.setEnabled(false);
        eliminaArticoloDallOrdineButton.setEnabled(false);
        cancellaOrdineButton.setEnabled(false);
        prodottoBox.setEnabled(false);

        for (Progetto prog : d.getProgetti()) {
            progettoOrdineBox.addItem(prog.getNome());
            progettiBox.addItem(prog.getNome());
        }
        for (Categoria cat : sessione.ottieniListaCategorie())
            categoriaBox.addItem(cat.getNome());
        //TODO: Aggiungere la lista di magazzini nel relativo comboBox


        view.pack();

        nuovoOrdineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (nomeField.getText().length() == 0 || progettoOrdineBox.getSelectedIndex() == -1)
                    showMessageDialog(getView(), "I campi \"nome progetto \" e \"progetto\" non possono essere lasciati vuoti");
                else {
                    int index = progettoOrdineBox.getSelectedIndex();
                    sessione.aggiungiOrdineProgetto(nomeField.getText(), d.getProgetti().get(index));
                }
            }
        });

        categoriaBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int index = categoriaBox.getSelectedIndex();
                for (Prodotto prod : sessione.ottieniListaCategorie().get(index).getProdotti())
                    prodottoBox.addItem(prod.getNome());
            }
        });

        progettiBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                int index = progettiBox.getSelectedIndex();
                Progetto prog = d.getProgetti().get(index);
                listModelOrdinePendente = new DefaultListModel();
                for (Ordine o : prog.getOrdini())
                    listModelOrdinePendente.addElement(o.getNome());
                ordiniPendentiList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                ordiniPendentiList.setModel(listModelOrdinePendente);
            }
        });

        ordiniPendentiList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                modificaOrdineButton.setEnabled(true);
                cancellaOrdineButton.setEnabled(true);
            }
        });

        cancellaOrdineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int indexProg = progettiBox.getSelectedIndex();
                int indexOrd = ordiniPendentiList.getSelectedIndex();
                sessione.rimuoviOrdine(d.getProgetti().get(indexProg).getOrdini().get(indexOrd));
            }
        });

        modificaOrdineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int indexProg = progettiBox.getSelectedIndex();
                int indexOrd = ordiniPendentiList.getSelectedIndex();
                Progetto prog = d.getProgetti().get(indexProg);
                Ordine ord = prog.getOrdini().get(indexOrd);
                listModelArticoliOrdineCorrente = new DefaultListModel();
                for (ArticoloOrdine ao : ord.getArticoliOrdine()) {
                    listModelArticoliOrdineCorrente.addElement(ao.getArticolo().getNome() + " " + ao.getQuantita() +
                            " " + ao.getParziale());
                }
                ordineCorrenteList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                ordineCorrenteList.setModel(listModelArticoliOrdineCorrente);
                chiudiOrdineButton1.setEnabled(true);
                modificaQuantitàButton.setEnabled(true);
                ordineNomeLabel.setText(ord.getNome());
            }
        });

        ordineCorrenteList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                eliminaArticoloDallOrdineButton.setEnabled(true);
            }
        });

        chiudiOrdineButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int index = -1;
                for (Ordine o : d.getOrdini())
                    if (o.getNome().equals(ordineNomeLabel))
                        index = d.getOrdini().indexOf(o);
                Ordine ordine = d.getOrdini().get(index);
                sessione.confermaOrdine(ordine);
                try {
                    sessione.stampaOrdine(ordine);
                } catch (ReportCreationFailedException e) {
                    showMessageDialog(getView(), "Errore nella stampa dell'ordine");
                }
            }
        });

        eliminaArticoloDallOrdineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int index = -1;
                for (Ordine o : d.getOrdini())
                    if (o.getNome().equals(ordineNomeLabel))
                        index = d.getOrdini().indexOf(o);
                Ordine ordine = d.getOrdini().get(index);
                int indexArt = ordineCorrenteList.getSelectedIndex();
                ArticoloOrdine ao = ordine.getArticoliOrdine().get(indexArt);
                sessione.rimuoviArticoloOrdine(ordine, ao);
            }
        });

        modificaQuantitàButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int index = -1;
                for (Ordine o : d.getOrdini())
                    if (o.getNome().equals(ordineNomeLabel))
                        index = d.getOrdini().indexOf(o);
                Ordine ordine = d.getOrdini().get(index);
                int indexArt = ordineCorrenteList.getSelectedIndex();
                ArticoloOrdine ao = ordine.getArticoliOrdine().get(indexArt);
                if (ordineQuantitaField.getText().length() == 0)
                    showMessageDialog(getView(), "Il campo \"Inserire nuopva quantità\" non può essere lasciato vuoto");
                else if (ordineQuantitaField.getText().contains(",") || ordineQuantitaField.getText().contains("."))
                    showMessageDialog(getView(), "La quantità deve essere un numero intero!");
                else if (ao.getQuantita() >= Integer.parseInt(ordineQuantitaField.getText())) {
                    ao.setQuantita(Integer.parseInt(ordineQuantitaField.getText()));
                    listModelArticoliOrdineCorrente = new DefaultListModel();
                    for (ArticoloOrdine aor : ordine.getArticoliOrdine()) {
                        listModelArticoliOrdineCorrente.addElement(aor.getArticolo().getNome() + " " + aor.getQuantita() +
                                " " + aor.getParziale());
                    }
                    ordineCorrenteList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                    ordineCorrenteList.setModel(listModelArticoliOrdineCorrente);
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