package com.depvin.pps.presenter;

import com.depvin.pps.business.ReportCreationFailedException;
import com.depvin.pps.business.SessioneDipendente;
import com.depvin.pps.model.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
    private JList ordineArticoliNuovoList;


    private JButton aggiungiArticoloAllOrdineButton;
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
    private JLabel prezzoTotaleLabel;
    private JTextField ricercaPerNomeField;

    private DefaultListModel listModelArticoliCatalogo;
    private DefaultListModel listModelOrdinePendente;
    private DefaultListModel listModelArticoliOrdineCorrente;
    private DefaultListModel listModelAppoggio;

    public SessioneDipendenteViewPresenter(final SessioneDipendente sessione) {
        this.sessione = sessione;
        final Dipendente d = sessione.getUtente();
        view = new JFrame("Sessione: " + d.getNome() + " " + d.getCognome());
        rootPanel.setPreferredSize(new Dimension(1200, 700));
        view.setLocation(50, 100);
        view.setContentPane(rootPanel);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tabbedPane2.setVisible(true);
        modificaOrdineButton.setEnabled(false);
        modificaQuantitàButton.setEnabled(false);
        richiediNotificaButton.setEnabled(false);
        chiudiOrdineButton1.setEnabled(false);
        aggiungiArticoloAllOrdineButton.setEnabled(false);
        eliminaArticoloDallOrdineButton.setEnabled(false);
        cancellaOrdineButton.setEnabled(false);
        prodottoBox.setEnabled(false);
        magazzinoBox.setEnabled(false);

        listModelArticoliCatalogo = new DefaultListModel();
        listModelArticoliCatalogo.addElement(null);
        articoloCatList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        articoloCatList.setModel(listModelArticoliCatalogo);
        articoloCatList.setVisible(true);


        for (Categoria cat : sessione.ottieniListaCategorie())
            categoriaBox.addItem(cat.getNome());
        categoriaBox.setSelectedIndex(-1);

        for (Progetto p : d.getProgetti()) {
            progettoOrdineBox.addItem(p.getNome());
            progettiBox.addItem(p.getNome());
        }

        view.pack();

        categoriaBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                prodottoBox.removeAllItems();
                int index = categoriaBox.getSelectedIndex();
                for (Prodotto prod : sessione.ottieniListaCategorie().get(index).getProdotti()) {
                    prodottoBox.addItem(prod.getNome());
                    listModelArticoliCatalogo.removeAllElements();
                }
                prodottoBox.setEnabled(true);
                prodottoBox.setSelectedIndex(-1);
            }
        });

        prodottoBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (prodottoBox.getSelectedIndex() != -1) {
                    listModelArticoliCatalogo = new DefaultListModel();
                    listModelAppoggio = new DefaultListModel();
                    List<Categoria> listC = sessione.ottieniListaCategorie();
                    Categoria categoria = listC.get(categoriaBox.getSelectedIndex());
                    Prodotto p = categoria.getProdotti().get(prodottoBox.getSelectedIndex());
                    List<Articolo> listA = sessione.ottieniListaArticoliPerCategoria(categoria);
                    for (Articolo a : listA)
                        if (a.getProdotto().getNome().equals(p.getNome())) {
                            listModelArticoliCatalogo.addElement(a.getNome());
                            listModelAppoggio.addElement(a.getNome());
                        }
                    articoloCatList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                    articoloCatList.setModel(listModelArticoliCatalogo);
                    articoloCatList.setVisible(true);
                } else {
                }
            }
        });

        articoloCatList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                richiediNotificaButton.setEnabled(true);
                aggiungiArticoloAllOrdineButton.setEnabled(true);
                magazzinoBox.setEnabled(true);
            }
        });

        magazzinoBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int index = articoloCatList.getSelectedIndex();
                if (ricercaPerNomeField.getText().length() != 0) {
                    List<Articolo> listA = sessione.ottieniListaArticoliPerRicerca(ricercaPerNomeField.getText());
                    Articolo a = listA.get(index);
                    //TODO:Estrarre i magazzini in cui è presente
                } else {
                    List<Categoria> listC = sessione.ottieniListaCategorie();
                    Categoria c = listC.get(categoriaBox.getSelectedIndex());
                    List<Articolo> listA = sessione.ottieniListaArticoliPerCategoria(c);
                    Prodotto p = c.getProdotti().get(prodottoBox.getSelectedIndex());
                    List<Articolo> nListA = new ArrayList<Articolo>();
                    for (Articolo a : listA)
                        if (a.getProdotto().getNome().equals(p.getNome()))
                            nListA.add(a);
                    Articolo a = nListA.get(index);
                    //TODO:Estrarre i magazzini in cui è presente
                }
            }
        });

        ricercaPerNomeField.getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent documentEvent) {
                listModelArticoliCatalogo = new DefaultListModel();
                listModelArticoliCatalogo.clear();
                articoloCatList.removeAll();
                List<Articolo> listA = sessione.ottieniListaArticoliPerRicerca(ricercaPerNomeField.getText());
                for (Articolo a : listA)
                    listModelArticoliCatalogo.addElement(a.getNome());
                articoloCatList.setModel(listModelArticoliCatalogo);
            }

            public void removeUpdate(DocumentEvent documentEvent) {
                if (ricercaPerNomeField.getText().length() != 0) {
                    listModelArticoliCatalogo.clear();
                    articoloCatList.removeAll();
                    List<Articolo> listA = sessione.ottieniListaArticoliPerRicerca(ricercaPerNomeField.getText());
                    for (Articolo a : listA)
                        listModelArticoliCatalogo.addElement(a.getNome());
                    articoloCatList.setModel(listModelArticoliCatalogo);
                } else {
                    articoloCatList.setModel(listModelAppoggio);
                }
            }

            public void changedUpdate(DocumentEvent documentEvent) {

            }

        });

        progettiBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                int index = progettiBox.getSelectedIndex();
                Progetto prog = d.getProgetti().get(index);
                listModelOrdinePendente = new DefaultListModel();
                for (Ordine o : prog.getOrdini())
                    if (o.getDipendente().getNome().equals(d.getNome()))
                        listModelOrdinePendente.addElement(o.getNome());
                ordiniPendentiList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                ordiniPendentiList.setModel(listModelOrdinePendente);
                ordiniPendentiList.setVisible(true);
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
                if (ordineQuantitaField.getText().length() == 0)
                    showMessageDialog(getView(), "Il campo \"Inserire nuopva quantità\" non può essere lasciato vuoto");
                else if (ordineQuantitaField.getText().contains(",") || ordineQuantitaField.getText().contains(".") || Integer.parseInt(ordineQuantitaField.getText()) < 0)
                    showMessageDialog(getView(), "La quantità deve essere un numero intero positivo!");
                else {
                    int index = 0;
                    for (Ordine o : d.getOrdini())
                        if (o.getNome().equals(ordineNomeLabel))
                            index = d.getOrdini().indexOf(o);
                    Ordine ordine = d.getOrdini().get(index);
                    int indexArt = ordineCorrenteList.getSelectedIndex();
                    ArticoloOrdine ao = ordine.getArticoliOrdine().get(indexArt);
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

        nuovoOrdineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (nomeField.getText().length() == 0 || progettoOrdineBox.getSelectedIndex() == -1)
                    showMessageDialog(getView(), "I campi \"nome ordine\" e \"progetto\" non possono essere lasciati vuoti");
                else {
                    int index = progettoOrdineBox.getSelectedIndex();
                    sessione.aggiungiOrdineProgetto(nomeField.getText(), d.getProgetti().get(index));
                }
            }
        });


        aggiungiArticoloAllOrdineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int indexA = articoloCatList.getSelectedIndex();
                // TODO: Da continuare quando si avranno gli articoli
            }
        });

        ordineCorrenteList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                chiudiOrdineButton1.setEnabled(true);
                eliminaArticoloDallOrdineButton.setEnabled(true);
                modificaQuantitàButton.setEnabled(true);
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
                    showMessageDialog(getView(), "Ordine stampato con successo");
                } catch (ReportCreationFailedException e) {
                    showMessageDialog(getView(), "Errore nella stampa dell'ordine");
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