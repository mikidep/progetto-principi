package com.depvin.pps.presenter;

import com.depvin.pps.business.SendOrderException;
import com.depvin.pps.business.ReportCreationFailedException;
import com.depvin.pps.business.SessioneDipendente;
import com.depvin.pps.model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import static javax.swing.JOptionPane.showMessageDialog;
import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    private JComboBox magazzinoNonBox;

    private JList articoloCatList;
    private JList ordineCorrenteList;
    private JList ordiniPendentiList;
    private JList ordineArticoliNuovoList;

    private JButton aggiungiArticoloAllOrdineButton;
    private JButton nuovoOrdineButton;
    private JButton richiediNotificaButton;
    private JButton modificaOrdineButton;
    private JButton inviaOrdine;
    private JButton modificaQuantitàButton;
    private JButton eliminaArticoloDallOrdineButton;
    private JButton cancellaOrdineButton;
    private JButton abilitaNuovoOrdineButton;
    private JButton ottieniInformazioniButton;
    private JButton ottieniImmagineButton;

    private JTextField ricercaPerNomeField;
    private JTextField catalogoQuantitaField;
    private JTextField ordineQuantitaField;
    private JTextField nomeField;
    private JLabel ordineNomeLabel;
    private JLabel prezzoTotaleLabel;
    private JLabel quantitàMagazzinoLabel;
    private JLabel nuovoOrdineLabel;
    private JLabel labelEvadiPrezzo;
    private JList listOrdiniInviati;
    private JList listStatoArticoliInviati;
    private JComboBox richiestaProgettoBox;

    private DefaultListModel listOrdiniInviatiModel;
    private DefaultListModel listStatoArticoliInviatiModel;

    private DefaultListModel listModelArticoliCatalogo;
    private DefaultListModel listModelOrdinePendente;
    private DefaultListModel listModelArticoliOrdineCorrente;
    private DefaultListModel listModelAppoggio;
    private DefaultListModel listOrdineArticoliNuovo;

    public SessioneDipendenteViewPresenter(final SessioneDipendente sessione) {
        this.sessione = sessione;
        final Dipendente d = sessione.getUtente();
        view = new JFrame("Sessione: " + d.getNome() + " " + d.getCognome());
        //final Pattern pattern = Pattern.compile("[a-zA-Z_-|\"£$%&/()=?^'/òçèé+*ù§à°#@{};:.]");
        final Pattern pattern = Pattern.compile("[^0-9]");
        rootPanel.setPreferredSize(new Dimension(1200, 700));
        view.setLocation(80, 0);
        view.setContentPane(rootPanel);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tabbedPane2.setVisible(true);
        modificaOrdineButton.setEnabled(false);
        modificaQuantitàButton.setEnabled(false);
        richiediNotificaButton.setEnabled(false);
        inviaOrdine.setEnabled(false);
        aggiungiArticoloAllOrdineButton.setEnabled(false);
        eliminaArticoloDallOrdineButton.setEnabled(false);
        cancellaOrdineButton.setEnabled(false);
        prodottoBox.setEnabled(false);
        magazzinoBox.setEnabled(false);
        magazzinoNonBox.setEnabled(false);
        catalogoQuantitaField.setEnabled(false);
        abilitaNuovoOrdineButton.setEnabled(false);
        ottieniImmagineButton.setEnabled(false);
        ottieniInformazioniButton.setEnabled(false);
        richiestaProgettoBox.setEnabled(false);

        listOrdineArticoliNuovo = new DefaultListModel();
        listOrdineArticoliNuovo.addElement(null);
        ordineArticoliNuovoList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        ordineArticoliNuovoList.setModel(listOrdineArticoliNuovo);
        ordineArticoliNuovoList.setVisible(true);

        listModelArticoliCatalogo = new DefaultListModel();
        listModelArticoliCatalogo.addElement(null);
        articoloCatList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        articoloCatList.setModel(listModelArticoliCatalogo);
        articoloCatList.setVisible(true);

        listModelArticoliOrdineCorrente = new DefaultListModel();
        listModelOrdinePendente = new DefaultListModel();
        listModelAppoggio = new DefaultListModel();

        listOrdiniInviatiModel = new DefaultListModel();
        listStatoArticoliInviatiModel = new DefaultListModel();
        listOrdiniInviati.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listStatoArticoliInviati.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        List<Ordine> listordine = new ArrayList<Ordine>();
        for (Ordine o : d.getOrdini())
            if (o.isInviato() && !listordine.contains(o)) {
                listordine.add(o);
                listOrdiniInviatiModel.addElement(o.getNome());
            }
        listOrdiniInviati.setModel(listOrdiniInviatiModel);
        listStatoArticoliInviati.setVisible(true);
        listOrdiniInviati.setVisible(true);


        for (Categoria cat : sessione.ottieniListaCategorie())
            categoriaBox.addItem(cat.getNome());
        categoriaBox.setSelectedIndex(-1);

        for (Progetto p : d.getProgetti()) {
            progettoOrdineBox.addItem(p.getNome());
            progettiBox.addItem(p.getNome());
            richiestaProgettoBox.addItem((p.getNome()));
        }

        final List<Categoria> listCategoria = sessione.ottieniListaCategorie();

        progettiBox.setSelectedIndex(-1);
        progettoOrdineBox.setSelectedIndex(-1);
        richiestaProgettoBox.setSelectedIndex(-1);
        view.pack();

        categoriaBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                listModelArticoliCatalogo.clear();
                articoloCatList.setModel(listModelArticoliCatalogo);
                magazzinoBox.setEnabled(false);
                magazzinoBox.removeAllItems();
                magazzinoBox.setSelectedIndex(-1);
                magazzinoNonBox.setEnabled(false);
                magazzinoNonBox.removeAllItems();
                magazzinoNonBox.setSelectedIndex(-1);
                catalogoQuantitaField.setText("");
                aggiungiArticoloAllOrdineButton.setEnabled(false);
                richiestaProgettoBox.setEnabled(false);
                richiediNotificaButton.setEnabled(false);
                if (ricercaPerNomeField.getText().length() == 0) {
                    quantitàMagazzinoLabel.setText("");
                    prodottoBox.removeAllItems();
                    int index = categoriaBox.getSelectedIndex();
                    for (Prodotto prod : listCategoria.get(index).getProdotti()) {
                        prodottoBox.addItem(prod.getNome());
                        listModelArticoliCatalogo.removeAllElements();
                    }
                    catalogoQuantitaField.setEnabled(false);
                    prodottoBox.setEnabled(true);
                    prodottoBox.setSelectedIndex(-1);
                    articoloCatList.setEnabled(false);
                } else {
                    List<Articolo> listA = sessione.ottieniListaArticoliPerRicerca(ricercaPerNomeField.getText());
                    for (Articolo a : listA)
                        listModelArticoliCatalogo.addElement(a.getNome() + "      " + a.getPrezzo() + " €");
                    articoloCatList.setModel(listModelArticoliCatalogo);
                    articoloCatList.setEnabled(true);
                }
            }
        });

        prodottoBox.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {
                quantitàMagazzinoLabel.setText("");
                magazzinoBox.setEnabled(false);
                magazzinoBox.removeAllItems();
                magazzinoBox.setSelectedIndex(-1);
                listModelArticoliCatalogo.clear();
                articoloCatList.setModel(listModelArticoliCatalogo);
                richiediNotificaButton.setEnabled(false);
            }

            public void mousePressed(MouseEvent mouseEvent) {
            }

            public void mouseReleased(MouseEvent mouseEvent) {
            }

            public void mouseEntered(MouseEvent mouseEvent) {
            }

            public void mouseExited(MouseEvent mouseEvent) {
            }
        });

        prodottoBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                listModelArticoliCatalogo.clear();
                articoloCatList.setModel(listModelArticoliCatalogo);
                richiediNotificaButton.setEnabled(false);
                quantitàMagazzinoLabel.setText("");
                catalogoQuantitaField.setText("");
                catalogoQuantitaField.setEnabled(false);
                magazzinoBox.setEnabled(false);
                magazzinoBox.removeAllItems();
                magazzinoBox.setSelectedIndex(-1);
                magazzinoNonBox.setEnabled(false);
                magazzinoNonBox.removeAllItems();
                magazzinoNonBox.setSelectedIndex(-1);
                richiestaProgettoBox.setEnabled(false);

                if (prodottoBox.getSelectedIndex() != -1 && ricercaPerNomeField.getText().length() == 0) {
                    listModelAppoggio.clear();
                    Categoria categoria = listCategoria.get(categoriaBox.getSelectedIndex());
                    Prodotto p = categoria.getProdotti().get(prodottoBox.getSelectedIndex());
                    List<Articolo> listA = sessione.ottieniListaArticoliPerCategoria(categoria);
                    for (Articolo a : listA)
                        if (a.getProdotto().getNome().equals(p.getNome())) {

                            String newBudget = Float.toString(a.getPrezzo());
                            newBudget = newBudget.replaceAll(",", ".");
                            if (!newBudget.contains("."))
                                newBudget = newBudget + ".00";
                            float budget = Float.parseFloat(newBudget);

                            listModelArticoliCatalogo.addElement(a.getNome() + "       " + budget + " €");
                            listModelAppoggio.addElement(a.getNome() + "       " + budget + " €");
                        }
                    articoloCatList.setModel(listModelArticoliCatalogo);
                    articoloCatList.setVisible(true);
                    articoloCatList.setEnabled(true);
                    aggiungiArticoloAllOrdineButton.setEnabled(false);
                } else if (prodottoBox.getSelectedIndex() != -1 && ricercaPerNomeField.getText().length() != 0) {
                    List<Articolo> listA = sessione.ottieniListaArticoliPerRicerca(ricercaPerNomeField.getText());
                    for (Articolo a : listA)
                        listModelArticoliCatalogo.addElement(a.getNome() + "      " + a.getPrezzo() + " €");
                    articoloCatList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                    articoloCatList.setModel(listModelArticoliCatalogo);
                    articoloCatList.setVisible(true);
                    aggiungiArticoloAllOrdineButton.setEnabled(false);
                    articoloCatList.setEnabled(true);
                } else {
                }
            }
        });

        catalogoQuantitaField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent documentEvent) {
                try {
                    Runnable run = new Runnable() {
                        public void run() {
                            Articolo articolo;
                            String nomeBox = (String) magazzinoBox.getSelectedItem();
                            richiediNotificaButton.setEnabled(false);
                            magazzinoBox.removeAllItems();
                            magazzinoNonBox.removeAllItems();
                            richiestaProgettoBox.setEnabled(true);

                            if (ricercaPerNomeField.getText().length() == 0) {
                                Categoria categoria = listCategoria.get(categoriaBox.getSelectedIndex());
                                Prodotto p = categoria.getProdotti().get(prodottoBox.getSelectedIndex());
                                List<Articolo> listA = sessione.ottieniListaArticoliPerCategoria(categoria);
                                List<Articolo> listAAA = new ArrayList<Articolo>();
                                for (Articolo a : listA)
                                    if (a.getProdotto().getNome().equals(p.getNome()))
                                        listAAA.add(a);
                                articolo = listAAA.get(articoloCatList.getSelectedIndex());
                            } else {  //ricercaPerNomeField != 0
                                List<Articolo> listArtcl = sessione.ottieniListaArticoliPerRicerca(ricercaPerNomeField.getText());
                                articolo = listArtcl.get(articoloCatList.getSelectedIndex());
                            }
                            List<ArticoloMagazzino> listAM = articolo.getInMagazzino();
                            Matcher m = pattern.matcher(catalogoQuantitaField.getText());
                            if (!m.find()) {
                                riempiMagazzino(listAM, Integer.parseInt(catalogoQuantitaField.getText()));
                                int j = 0;
                                int index = 0;
                                if (magazzinoBox.getItemCount() != 0) {
                                    for (int x = 0; x < magazzinoBox.getItemCount(); x++) {
                                        if (magazzinoBox.getItemAt(x).equals(nomeBox)) {
                                            j++;
                                            index = x;
                                        }
                                    }
                                }
                                if (j == 0) {
                                    magazzinoBox.setSelectedIndex(-1);
                                    magazzinoBox.setEnabled(true);
                                    quantitàMagazzinoLabel.setText("");
                                    aggiungiArticoloAllOrdineButton.setEnabled(false);
                                } else {
                                    magazzinoBox.setSelectedIndex(index);
                                    magazzinoBox.setEnabled(true);
                                    aggiungiArticoloAllOrdineButton.setEnabled(true);
                                }
                                magazzinoNonBox.setSelectedIndex(-1);
                                magazzinoNonBox.setEnabled(true);
                            } else {
                                magazzinoBox.removeAllItems();
                                magazzinoBox.setEnabled(false);
                                magazzinoNonBox.removeAllItems();
                                magazzinoNonBox.setEnabled(false);
                                catalogoQuantitaField.setText("");
                                catalogoQuantitaField.setEnabled(false);
                                articoloCatList.setSelectedIndex(-1);
                            }
                        }
                    };
                    SwingUtilities.invokeLater(run);
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
            public void removeUpdate(DocumentEvent documentEvent) {

            }
            public void changedUpdate(DocumentEvent documentEvent) {

            }
        });

        richiestaProgettoBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                richiediNotificaButton.setEnabled(true);
            }
        });

        articoloCatList.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {
                try {
                    aggiungiArticoloAllOrdineButton.setEnabled(true);
                    richiediNotificaButton.setEnabled(false);
                    quantitàMagazzinoLabel.setText("");
                    catalogoQuantitaField.setText("");
                    magazzinoBox.removeAllItems();
                    magazzinoNonBox.removeAllItems();
                    richiestaProgettoBox.setEnabled(false);

                    Articolo articolo = null;
                    if (ricercaPerNomeField.getText().length() == 0) {

                        List<Categoria> listC = sessione.ottieniListaCategorie();
                        Categoria categoria = listC.get(categoriaBox.getSelectedIndex());
                        Prodotto p = categoria.getProdotti().get(prodottoBox.getSelectedIndex());
                        List<Articolo> listA = sessione.ottieniListaArticoliPerCategoria(categoria);
                        List<Articolo> listAAA = new ArrayList<Articolo>();
                        for (Articolo a : listA)
                            if (a.getProdotto().getNome().equals(p.getNome()))
                                listAAA.add(a);
                        articolo = listAAA.get(articoloCatList.getSelectedIndex());
                        /*List<ArticoloMagazzino> listAM = articolo.getInMagazzino();
                        riempiMagazzino(listAM, 0);*/
                    } else {
                        List<Articolo> listArtcl = sessione.ottieniListaArticoliPerRicerca(ricercaPerNomeField.getText());
                        articolo = listArtcl.get(articoloCatList.getSelectedIndex());
                    }

                    List<ArticoloMagazzino> listAM = articolo.getInMagazzino();
                    riempiMagazzino(listAM, 0);

                    magazzinoBox.setEnabled(true);
                    magazzinoBox.setSelectedIndex(-1);
                    magazzinoNonBox.setEnabled(true);
                    magazzinoNonBox.setSelectedIndex(-1);
                    catalogoQuantitaField.setText("");
                    catalogoQuantitaField.setEnabled(true);

                    ottieniInformazioniButton.setEnabled(true);
                    ottieniImmagineButton.setEnabled(true);

                } catch (ArrayIndexOutOfBoundsException e) {
                    articoloCatList.setSelectedIndex(-1);
                }
            }

            public void mousePressed(MouseEvent mouseEvent) {

            }

            public void mouseReleased(MouseEvent mouseEvent) {

            }

            public void mouseEntered(MouseEvent mouseEvent) {

            }

            public void mouseExited(MouseEvent mouseEvent) {

            }
        });

        ricercaPerNomeField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent documentEvent) {
                richiediNotificaButton.setEnabled(false);
                listModelArticoliCatalogo.clear();

                articoloCatList.setModel(listModelArticoliCatalogo);

                List<Articolo> listA = sessione.ottieniListaArticoliPerRicerca(ricercaPerNomeField.getText());
                for (Articolo a : listA)
                    listModelArticoliCatalogo.addElement(a.getNome() + "      " + a.getPrezzo() + " €");

                articoloCatList.setModel(listModelArticoliCatalogo);
                articoloCatList.setVisible(true);
                articoloCatList.setSelectedIndex(-1);
                magazzinoBox.setEnabled(false);
                magazzinoBox.removeAllItems();
                magazzinoBox.setSelectedIndex(-1);
                magazzinoNonBox.setEnabled(false);
                magazzinoNonBox.removeAllItems();
                magazzinoNonBox.setSelectedIndex(-1);
                aggiungiArticoloAllOrdineButton.setEnabled(false);
                catalogoQuantitaField.setText("");
                articoloCatList.setEnabled(true);
                categoriaBox.setEnabled(false);
                prodottoBox.setEnabled(false);
            }
            public void removeUpdate(DocumentEvent documentEvent) {
                if (ricercaPerNomeField.getText().length() != 0) {
                    richiediNotificaButton.setEnabled(false);
                    listModelArticoliCatalogo.clear();

                    articoloCatList.setModel(listModelArticoliCatalogo);
                    listModelArticoliCatalogo = new DefaultListModel();

                    List<Articolo> listA = sessione.ottieniListaArticoliPerRicerca(ricercaPerNomeField.getText());
                    for (Articolo a : listA)
                        listModelArticoliCatalogo.addElement(a.getNome() + "      " + a.getPrezzo() + " €");

                    articoloCatList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                    articoloCatList.setModel(listModelArticoliCatalogo);
                    articoloCatList.setVisible(true);

                    magazzinoBox.setEnabled(false);
                    magazzinoBox.removeAllItems();
                    magazzinoBox.setSelectedIndex(-1);
                    magazzinoNonBox.setEnabled(false);
                    magazzinoNonBox.removeAllItems();
                    magazzinoNonBox.setSelectedIndex(-1);
                    aggiungiArticoloAllOrdineButton.setEnabled(false);
                    catalogoQuantitaField.setText("");
                    articoloCatList.setEnabled(true);
                } else {
                    categoriaBox.setEnabled(true);
                    prodottoBox.setEnabled(true);
                    articoloCatList.setModel(listModelAppoggio);
                    articoloCatList.setEnabled(true);
                    richiediNotificaButton.setEnabled(false);
                }
            }
            public void changedUpdate(DocumentEvent documentEvent) {

            }
        });

        magazzinoBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    quantitàMagazzinoLabel.setText("");
                    Articolo articolo = null;
                    if (ricercaPerNomeField.getText().length() == 0) {
                        Categoria categoria = listCategoria.get(categoriaBox.getSelectedIndex());
                        Prodotto p = categoria.getProdotti().get(prodottoBox.getSelectedIndex());
                        List<Articolo> listA = sessione.ottieniListaArticoliPerCategoria(categoria);
                        List<Articolo> listAAA = new ArrayList<Articolo>();
                        for (Articolo a : listA)
                            if (a.getProdotto().getNome().equals(p.getNome()))
                                listAAA.add(a);
                        articolo = listAAA.get(articoloCatList.getSelectedIndex());
                    } else {
                        List<Articolo> listArtcl = sessione.ottieniListaArticoliPerRicerca(ricercaPerNomeField.getText());
                        articolo = listArtcl.get(articoloCatList.getSelectedIndex());
                    }
                    List<ArticoloMagazzino> listAM = articolo.getInMagazzino();
                    int index = magazzinoBox.getSelectedIndex();
                    List<Magazzino> listMa = new ArrayList<Magazzino>();
                    int limite = -1;
                    if (catalogoQuantitaField.getText().length() == 0)
                        limite = 0;
                    else
                        limite = Integer.parseInt(catalogoQuantitaField.getText());
                    for (ArticoloMagazzino am : listAM)
                        if (am.getDisponibilita() >= limite && am.getDisponibilita() != 0)
                            listMa.add(am.getMagazzino());
                    Magazzino magazzino = listMa.get(index);
                    int inr = -1;
                    for (ArticoloMagazzino ams : listAM)
                        if (ams.getMagazzino().getNome().equals(magazzino.getNome()))
                            inr = listAM.indexOf(ams);
                    ArticoloMagazzino articM = listAM.get(inr);
                    int disponibilità = articM.getDisponibilita();
                    quantitàMagazzinoLabel.setText(String.valueOf(disponibilità));
                    quantitàMagazzinoLabel.setVisible(true);
                } catch (NullPointerException e) {

                } catch (ArrayIndexOutOfBoundsException e) {

                } catch (NumberFormatException e) {
                    showMessageDialog(getView(), "Non puoi inserire caratteri che non siano cifre");
                    magazzinoBox.removeAllItems();
                    magazzinoBox.setEnabled(false);
                    magazzinoNonBox.removeAllItems();
                    magazzinoNonBox.setEnabled(false);
                    catalogoQuantitaField.setEnabled(false);
                    articoloCatList.setSelectedIndex(-1);
                }
            }
        });

        progettiBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (progettiBox.getSelectedIndex() != -1) {
                    int index = progettiBox.getSelectedIndex();
                    Progetto prog = d.getProgetti().get(index);
                    listModelOrdinePendente.clear();
                    for (Ordine o : prog.getOrdini())
                        if (o.getDipendente().getNome().equals(d.getNome()) && !o.isInviato())
                            listModelOrdinePendente.addElement(o.getNome());
                    ordiniPendentiList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                    ordiniPendentiList.setModel(listModelOrdinePendente);
                    ordiniPendentiList.setVisible(true);
                }
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
                List<Ordine> listOr = new ArrayList<Ordine>();
                for (Ordine o : d.getProgetti().get(indexProg).getOrdini())
                    if (!o.isInviato())
                        listOr.add(o);
                int indexOrd = ordiniPendentiList.getSelectedIndex();
                sessione.rimuoviOrdine(listOr.get(indexOrd), d.getProgetti().get(indexProg));

                //AGG
                listModelOrdinePendente.clear();
                ordiniPendentiList.setModel(listModelOrdinePendente);

                listOrdiniInviatiModel.clear();
                for (Ordine o : d.getOrdini())
                    if (o.isInviato()) {
                        listOrdiniInviatiModel.addElement(o.getNome());
                    }
                listOrdiniInviati.setModel(listOrdiniInviatiModel);

                listModelOrdinePendente.clear();
                ordiniPendentiList.setModel(listModelOrdinePendente);

                listModelArticoliOrdineCorrente.clear();
                ordineCorrenteList.setModel(listModelArticoliOrdineCorrente);

                listOrdineArticoliNuovo.clear();
                ordineArticoliNuovoList.setModel(listOrdineArticoliNuovo);

                cancellaOrdineButton.setEnabled(false);
                modificaOrdineButton.setEnabled(false);
                modificaQuantitàButton.setEnabled(false);
                eliminaArticoloDallOrdineButton.setEnabled(false);
                inviaOrdine.setEnabled(false);

                magazzinoBox.setEnabled(false);
                magazzinoBox.removeAllItems();
                magazzinoBox.setSelectedIndex(-1);
                magazzinoNonBox.setEnabled(false);
                magazzinoNonBox.removeAllItems();
                magazzinoNonBox.setSelectedIndex(-1);
                catalogoQuantitaField.setText("");
                aggiungiArticoloAllOrdineButton.setEnabled(false);
                richiestaProgettoBox.setEnabled(false);
                richiediNotificaButton.setEnabled(false);

                ottieniInformazioniButton.setEnabled(false);
                ottieniImmagineButton.setEnabled(false);

                listModelArticoliCatalogo.clear();
                labelEvadiPrezzo.setText("");
                ordineNomeLabel.setText("");
                nuovoOrdineLabel.setText("");
                nomeField.setText("");
                prezzoTotaleLabel.setText("");

                progettiBox.setSelectedIndex(-1);
                progettoOrdineBox.setSelectedIndex(-1);
                //AGG


            }
        });

        modificaOrdineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    int indexProg = progettiBox.getSelectedIndex();
                    int indexOrd = ordiniPendentiList.getSelectedIndex();

                    catalogoQuantitaField.setText("");
                    aggiungiArticoloAllOrdineButton.setEnabled(false);
                    listModelArticoliCatalogo.clear();
                    articoloCatList.setModel(listModelArticoliCatalogo);
                    magazzinoBox.setEnabled(false);
                    magazzinoBox.removeAllItems();
                    magazzinoBox.setSelectedIndex(-1);
                    magazzinoNonBox.setEnabled(false);
                    magazzinoNonBox.removeAllItems();
                    magazzinoNonBox.setSelectedIndex(-1);
                    articoloCatList.setEnabled(false);
                    richiestaProgettoBox.setEnabled(false);

                    Progetto prog = d.getProgetti().get(indexProg);

                    //Ordine ord = prog.getOrdini().get(indexOrd);
                    List<Ordine> listO = new ArrayList<Ordine>();
                    for (Ordine o : prog.getOrdini())
                        if (!o.isInviato())
                            listO.add(o);
                    Ordine ord = listO.get(indexOrd);

                    listModelArticoliOrdineCorrente.clear();
                    for (ArticoloOrdine ao : ord.getArticoliOrdine()) {

                        String newBudget = Float.toString(ao.getArticolo().getPrezzo());
                        newBudget = newBudget.replaceAll(",", ".");
                        if (!newBudget.contains("."))
                            newBudget = newBudget + ".00";
                        float budget = Float.parseFloat(newBudget);

                        listModelArticoliOrdineCorrente.addElement(ao.getArticolo().getNome() + "     x " + ao.getQuantita() +
                                "     : " + budget + " €" + "   Mag. : " + ao.getMagazzino().getNome());
                    }
                    ordineCorrenteList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                    ordineArticoliNuovoList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                    ordineCorrenteList.setModel(listModelArticoliOrdineCorrente);
                    ordineArticoliNuovoList.setModel(listModelArticoliOrdineCorrente);
                    ordineCorrenteList.setVisible(true);
                    ordineArticoliNuovoList.setVisible(true);
                    inviaOrdine.setEnabled(true);
                    modificaQuantitàButton.setEnabled(true);
                    nuovoOrdineButton.setEnabled(false);
                    abilitaNuovoOrdineButton.setEnabled(true);
                    ordineNomeLabel.setText(ord.getNome());
                    nuovoOrdineLabel.setText(ord.getNome());
                    float totale = 0;
                    for (ArticoloOrdine aos : ord.getArticoliOrdine())
                        totale = totale + aos.getParziale();
                    prezzoTotaleLabel.setText(Float.toString(totale) + " €");

                    prodottoBox.removeAllItems();
                    prodottoBox.setEnabled(false);

                    catalogoQuantitaField.setEnabled(false);
                    eliminaArticoloDallOrdineButton.setEnabled(false);

                    listModelArticoliCatalogo.clear();
                    articoloCatList.setModel(listModelArticoliCatalogo);

                    labelEvadiPrezzo.setText(Float.toString(totale) + " €");
                    modificaQuantitàButton.setEnabled(false);
                } catch (ArrayIndexOutOfBoundsException e) {

                }
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
                    if (o.getNome().equals(ordineNomeLabel.getText()))
                        index = d.getOrdini().indexOf(o);
                Ordine ordine = d.getOrdini().get(index);
                int indexArt = ordineCorrenteList.getSelectedIndex();
                ArticoloOrdine ao = ordine.getArticoliOrdine().get(indexArt);
                sessione.rimuoviArticoloOrdine(ordine, ao);
                listOrdineArticoliNuovo.clear();
                listModelArticoliOrdineCorrente = new DefaultListModel();
                float app = 0;
                for (ArticoloOrdine aor : ordine.getArticoliOrdine()) {

                    String newBudget = Float.toString(aor.getArticolo().getPrezzo());
                    newBudget = newBudget.replaceAll(",", ".");
                    if (!newBudget.contains("."))
                        newBudget = newBudget + ".00";
                    float budget = Float.parseFloat(newBudget);

                    listModelArticoliOrdineCorrente.addElement(aor.getArticolo().getNome() + "     x " + aor.getQuantita() +
                            "     : " + budget + " €" + "   Mag. : " + aor.getMagazzino().getNome());
                    listOrdineArticoliNuovo.addElement(aor.getArticolo().getNome() + "     x " + aor.getQuantita() +
                            "     : " + budget + " €" + "   Mag. : " + aor.getMagazzino().getNome());
                    app = app + (budget * aor.getQuantita());
                }
                labelEvadiPrezzo.setText(Float.toString(app) + " €");
                prezzoTotaleLabel.setText(Float.toString(app) + " €");

                ordineCorrenteList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                ordineCorrenteList.setModel(listModelArticoliOrdineCorrente);
                ordineArticoliNuovoList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                ordineArticoliNuovoList.setModel(listOrdineArticoliNuovo);
            }
        });

        modificaQuantitàButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                Matcher m = pattern.matcher(catalogoQuantitaField.getText());
                if (ordineQuantitaField.getText().length() == 0)
                    showMessageDialog(getView(), "Il campo \"Inserire nuova quantità\" non può essere lasciato vuoto");
                /*else if (ordineQuantitaField.getText().contains(",") || ordineQuantitaField.getText().contains(".") ||
                        Integer.parseInt(ordineQuantitaField.getText()) < 0)*/
                else if (m.find())
                    showMessageDialog(getView(), "La quantità deve essere un numero intero positivo!");
                else {
                    int index = 0;
                    for (Ordine o : d.getOrdini())
                        if (o.getNome().equals(ordineNomeLabel.getText()))
                            index = d.getOrdini().indexOf(o);
                    Ordine ordine = d.getOrdini().get(index);
                    int indexArt = ordineCorrenteList.getSelectedIndex();
                    ArticoloOrdine ao = ordine.getArticoliOrdine().get(indexArt);
                    //Controllare se sta nelle quantità richieste
                    Magazzino maga = ao.getMagazzino();
                    List<ArticoloMagazzino> listma = maga.getArticoliMagazzino();
                    int indec = -1;
                    for (ArticoloMagazzino aa : listma)
                        if (aa.getArticolo().getNome().equals(ao.getArticolo().getNome()))
                            indec = listma.indexOf(aa);
                    ArticoloMagazzino artic = listma.get(indec);
                    if (artic.getDisponibilita() >= Integer.parseInt(ordineQuantitaField.getText())) {
                        sessione.modificaDisponibilitàArticoloOrdine(ao, Integer.parseInt(ordineQuantitaField.getText()));
                        listModelArticoliOrdineCorrente.clear();
                        listOrdineArticoliNuovo.clear();
                        float app = 0;
                        for (ArticoloOrdine aor : ordine.getArticoliOrdine()) {

                            String newBudget = Float.toString(aor.getArticolo().getPrezzo());
                            newBudget = newBudget.replaceAll(",", ".");
                            if (!newBudget.contains("."))
                                newBudget = newBudget + ".00";
                            float budget = Float.parseFloat(newBudget);

                            listModelArticoliOrdineCorrente.addElement(aor.getArticolo().getNome() + "     x " + aor.getQuantita() +
                                    "     : " + budget + " €" + "   Mag. : " + aor.getMagazzino().getNome());
                            listOrdineArticoliNuovo.addElement(aor.getArticolo().getNome() + "     x " + aor.getQuantita() +
                                    "     : " + budget + " €" + "   Mag. : " + aor.getMagazzino().getNome());
                            app = app + (budget * aor.getQuantita());
                        }
                        labelEvadiPrezzo.setText(Float.toString(app) + " €");
                        prezzoTotaleLabel.setText(Float.toString(app) + " €");
                        ordineCorrenteList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                        ordineArticoliNuovoList.setModel(listOrdineArticoliNuovo);
                        ordineCorrenteList.setModel(listModelArticoliOrdineCorrente);
                        modificaQuantitàButton.setEnabled(false);
                    } else {
                        showMessageDialog(getView(), "Quantità non disponibili nel magazzino");
                    }
                }
            }
        });

        nuovoOrdineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (nomeField.getText().length() == 0 || progettoOrdineBox.getSelectedIndex() == -1 ||
                        progettoOrdineBox.getSelectedIndex() == -1)
                    showMessageDialog(getView(), "I campi \"nome ordine\" e \"progetto\" non possono essere lasciati vuoti");
                else {
                    if (progettoOrdineBox.getSelectedIndex() >= 0) {
                        boolean nomeEsisteP = false;
                        for (Ordine or : d.getProgetti().get(progettoOrdineBox.getSelectedIndex()).getOrdini())
                            if (or.getNome().equals(nomeField.getText()))
                                nomeEsisteP = true;
                        if (!nomeEsisteP) {
                            int index = progettoOrdineBox.getSelectedIndex();
                            sessione.aggiungiOrdineProgetto(nomeField.getText(), d.getProgetti().get(index));
                            aggiungiArticoloAllOrdineButton.setEnabled(true);
                            nuovoOrdineLabel.setText(nomeField.getText());
                            ordineNomeLabel.setText(nomeField.getText());
                            inviaOrdine.setEnabled(true);

                            listOrdineArticoliNuovo.clear();
                            ordineArticoliNuovoList.setModel(listOrdineArticoliNuovo);

                            listModelArticoliOrdineCorrente.clear();
                            ordineCorrenteList.setModel(listModelArticoliOrdineCorrente);
                        } else {
                            showMessageDialog(getView(), "Nome già in uso per un'altro ordine nel progetto, sceglierne un'altro");
                        }
                    } else {
                        showMessageDialog(getView(), "Scegli un progetto");
                    }
                }
            }
        });

        aggiungiArticoloAllOrdineButton.addActionListener(new ActionListener() {
            //Matcher m = pattern.matcher(catalogoQuantitaField.getText());
            public void actionPerformed(ActionEvent actionEvent) {
                if (magazzinoBox.getSelectedIndex() == -1 || catalogoQuantitaField.getText().length() == 0 ||
                        nuovoOrdineLabel.getText().length() == 0)
                    showMessageDialog(getView(), "Non hai scelto il magazzino e/o non hai inserito la quantità " +
                            "adatta o non hai creato nessun ordine");
                /*else if (m.find())
                    showMessageDialog(getView(), "Hai inserito caratteri particolari nella quantità");*/
                else {
                    Articolo articolo;
                    if (ricercaPerNomeField.getText().length() == 0) {
                        Categoria categoria = listCategoria.get(categoriaBox.getSelectedIndex());
                        Prodotto p = categoria.getProdotti().get(prodottoBox.getSelectedIndex());
                        List<Articolo> listA = sessione.ottieniListaArticoliPerCategoria(categoria);
                        List<Articolo> listAAA = new ArrayList<Articolo>();
                        for (Articolo a : listA)
                            if (a.getProdotto().getNome().equals(p.getNome()))
                                listAAA.add(a);
                        articolo = listAAA.get(articoloCatList.getSelectedIndex());
                    } else {
                        List<Articolo> listArtcl = sessione.ottieniListaArticoliPerRicerca(ricercaPerNomeField.getText());
                        articolo = listArtcl.get(articoloCatList.getSelectedIndex());
                        }
                    List<ArticoloMagazzino> listAM = articolo.getInMagazzino();
                    int index = magazzinoBox.getSelectedIndex();
                    List<Magazzino> listMa = new ArrayList<Magazzino>();
                    int limite = -1;
                    if (catalogoQuantitaField.getText().length() == 0)
                        limite = 0;
                    else
                        limite = Integer.parseInt(catalogoQuantitaField.getText());
                    for (ArticoloMagazzino am : listAM)
                        if (am.getDisponibilita() >= limite)
                            listMa.add(am.getMagazzino());
                    Magazzino magazzino = listMa.get(index);
                    int inr = -1;
                    for (ArticoloMagazzino ams : listAM)
                        if (ams.getMagazzino().getNome().equals(magazzino.getNome()))
                            inr = listAM.indexOf(ams);
                    ArticoloMagazzino articM = listAM.get(inr);
                    int innd = -1;
                    for (Ordine o : d.getOrdini())
                        if (o.getNome().equals(nuovoOrdineLabel.getText()))
                            innd = d.getOrdini().indexOf(o);
                    ArticoloOrdine ao = new ArticoloOrdine(d.getOrdini().get(innd), articM.getArticolo(),
                            limite, magazzino);
                    int max = 0;
                    int indexAO = 0;
                    for (ArticoloOrdine aol : d.getOrdini().get(innd).getArticoliOrdine())
                        if (aol.getArticolo().getNome().equals(ao.getArticolo().getNome()) && magazzino.getNome().equals(aol.getMagazzino().getNome())) {
                            max = max + 1;
                            indexAO = d.getOrdini().get(innd).getArticoliOrdine().indexOf(aol);
                        }
                    if (max > 0) {
                        sessione.modificaDisponibilitàArticoloOrdine(d.getOrdini().get(innd).getArticoliOrdine().get(indexAO), Integer.parseInt(catalogoQuantitaField.getText()));
                    } else {
                        sessione.aggiungiArticoloOrdine(ao, ao.getOrdine());
                    }

                    String newBudget = Float.toString(ao.getArticolo().getPrezzo());
                    newBudget = newBudget.replaceAll(",", ".");
                    if (!newBudget.contains("."))
                        newBudget = newBudget + ".00";
                    float budget = Float.parseFloat(newBudget);

                    listOrdineArticoliNuovo.addElement(ao.getArticolo().getNome() + "     x " +
                            limite + "     : " + budget + " €"
                            + "   Mag. : " + ao.getMagazzino().getNome());
                    float totale = 0;
                    for (ArticoloOrdine aos : d.getOrdini().get(innd).getArticoliOrdine())
                        totale = totale + aos.getParziale();

                    prezzoTotaleLabel.setText(Float.toString(totale) + " €");
                    labelEvadiPrezzo.setText(Float.toString(totale) + " €");

                    listModelArticoliOrdineCorrente.clear();
                    listOrdineArticoliNuovo.clear();
                    for (ArticoloOrdine aoos : d.getOrdini().get(innd).getArticoliOrdine()) {

                        String newwBudget = Float.toString(aoos.getArticolo().getPrezzo());
                        newwBudget = newwBudget.replaceAll(",", ".");
                        if (!newwBudget.contains("."))
                            newwBudget = newwBudget + ".00";
                        float buddget = Float.parseFloat(newwBudget);

                        listOrdineArticoliNuovo.addElement(aoos.getArticolo().getNome() + "   x " +
                                aoos.getQuantita() + "     : " + buddget + " €"
                                + "   Mag. : " + aoos.getMagazzino().getNome());
                        listModelArticoliOrdineCorrente.addElement(aoos.getArticolo().getNome() + "   x " +
                                aoos.getQuantita() + "     : " + buddget + " €"
                                + "   Mag. : " + aoos.getMagazzino().getNome());
                    }
                    ordineArticoliNuovoList.setModel(listOrdineArticoliNuovo);
                    ordineCorrenteList.setModel(listModelArticoliOrdineCorrente);
                    }
                }
        });

        ordineCorrenteList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                eliminaArticoloDallOrdineButton.setEnabled(true);
                modificaQuantitàButton.setEnabled(true);
            }
        });

        inviaOrdine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int index = -1;
                for (Ordine o : d.getOrdini())
                    if (o.getNome().equals(ordineNomeLabel.getText()))
                        index = d.getOrdini().indexOf(o);
                Ordine ordine = d.getOrdini().get(index);
                if (!ordine.isInviato()) {
                    try {
                        sessione.inviaOrdine(ordine);
                        showMessageDialog(getView(), "Ordine inviato con successo");
                    } catch (SendOrderException e) {
                        showMessageDialog(getView(), "Non tutti gli elementi ordinati sono disponibili");
                    }

                } else {
                    showMessageDialog(getView(), "Ordine già inviato, attendere per l'evasione");
                }

                try {
                    GregorianCalendar gc = new GregorianCalendar();
                    ByteArrayOutputStream bytes = sessione.stampaOrdine(ordine);
                    //TODO:
                    // A seconda del computer che verrà presentato verrà cambiata la radice in cui salvare la stampa
                    FileOutputStream of = new FileOutputStream("/home/costantino/pdf_progetto/" + d.getNome() + "_" +
                            gc.get(Calendar.DATE) + "_" + gc.get(Calendar.MONTH) + "_" + gc.get(Calendar.YEAR) +
                            "_" + gc.get(Calendar.HOUR) + "_" + gc.get(Calendar.MINUTE) + ".pdf");
                    bytes.writeTo(of);
                    of.close();
                    showMessageDialog(getView(), "Ordine stampato con successo");

                    listModelOrdinePendente.clear();
                    ordiniPendentiList.setModel(listModelOrdinePendente);

                    listOrdiniInviatiModel.clear();
                    for (Ordine o : d.getOrdini())
                        if (o.isInviato()) {
                            listOrdiniInviatiModel.addElement(o.getNome());
                        }
                    listOrdiniInviati.setModel(listOrdiniInviatiModel);

                    listModelOrdinePendente.clear();
                    ordiniPendentiList.setModel(listModelOrdinePendente);

                    listModelArticoliOrdineCorrente.clear();
                    ordineCorrenteList.setModel(listModelArticoliOrdineCorrente);

                    listOrdineArticoliNuovo.clear();
                    ordineArticoliNuovoList.setModel(listOrdineArticoliNuovo);

                    cancellaOrdineButton.setEnabled(false);
                    modificaOrdineButton.setEnabled(false);
                    modificaQuantitàButton.setEnabled(false);
                    eliminaArticoloDallOrdineButton.setEnabled(false);
                    inviaOrdine.setEnabled(false);

                    magazzinoBox.setEnabled(false);
                    magazzinoBox.removeAllItems();
                    magazzinoBox.setSelectedIndex(-1);
                    magazzinoNonBox.setEnabled(false);
                    magazzinoNonBox.removeAllItems();
                    magazzinoNonBox.setSelectedIndex(-1);
                    catalogoQuantitaField.setText("");
                    aggiungiArticoloAllOrdineButton.setEnabled(false);
                    richiestaProgettoBox.setEnabled(false);
                    richiediNotificaButton.setEnabled(false);

                    ottieniInformazioniButton.setEnabled(false);
                    ottieniImmagineButton.setEnabled(false);

                    listModelArticoliCatalogo.clear();
                    labelEvadiPrezzo.setText("");
                    ordineNomeLabel.setText("");
                    nuovoOrdineLabel.setText("");
                    nomeField.setText("");
                    prezzoTotaleLabel.setText("");

                    progettiBox.setSelectedIndex(-1);
                    progettoOrdineBox.setSelectedIndex(-1);

                } catch (ReportCreationFailedException e) {
                    showMessageDialog(getView(), "Errore nella stampa dell'ordine");
                } catch (IOException e) {
                    showMessageDialog(getView(), "IOexception, impossibile stampare");
                }

            }
        });

        abilitaNuovoOrdineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                nuovoOrdineButton.setEnabled(true);
            }
        });

        ottieniImmagineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                Articolo articolo;
                if (ricercaPerNomeField.getText().length() == 0) {
                    Categoria categoria = listCategoria.get(categoriaBox.getSelectedIndex());
                    Prodotto p = categoria.getProdotti().get(prodottoBox.getSelectedIndex());
                    List<Articolo> listA = sessione.ottieniListaArticoliPerCategoria(categoria);
                    List<Articolo> listAAA = new ArrayList<Articolo>();
                    for (Articolo a : listA)
                        if (a.getProdotto().getNome().equals(p.getNome()))
                            listAAA.add(a);
                    articolo = listAAA.get(articoloCatList.getSelectedIndex());
                } else {
                    List<Articolo> listArtcl = sessione.ottieniListaArticoliPerRicerca(ricercaPerNomeField.getText());
                    articolo = listArtcl.get(articoloCatList.getSelectedIndex());
                }

                byte[] bytes = articolo.getImmagine();
                InputStream in = new ByteArrayInputStream(bytes);
                paintImage(in);
            }
        });

        ottieniInformazioniButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                Articolo articolo;
                if (ricercaPerNomeField.getText().length() == 0) {
                    Categoria categoria = listCategoria.get(categoriaBox.getSelectedIndex());
                    Prodotto p = categoria.getProdotti().get(prodottoBox.getSelectedIndex());
                    List<Articolo> listA = sessione.ottieniListaArticoliPerCategoria(categoria);
                    List<Articolo> listAAA = new ArrayList<Articolo>();
                    for (Articolo a : listA)
                        if (a.getProdotto().getNome().equals(p.getNome()))
                            listAAA.add(a);
                    articolo = listAAA.get(articoloCatList.getSelectedIndex());
                } else {
                    List<Articolo> listArtcl = sessione.ottieniListaArticoliPerRicerca(ricercaPerNomeField.getText());
                    articolo = listArtcl.get(articoloCatList.getSelectedIndex());
                }

                String listFoBuff = "";
                String listCatBuff = "";

                int len = articolo.getProdotto().getCategorie().size();
                for (Categoria c : articolo.getProdotto().getCategorie()) {
                    if (articolo.getProdotto().getCategorie().indexOf(c) == len - 1)
                        listCatBuff = listCatBuff + c.getNome();
                    else
                        listCatBuff = listCatBuff + c.getNome() + ", ";
                }
                int len2 = articolo.getFornitori().size();
                for (Fornitore f : articolo.getFornitori()) {
                    if (articolo.getFornitori().indexOf(f) == len2 - 1)
                        listFoBuff = listFoBuff + f.getNome();
                    else
                        listFoBuff = listFoBuff + f.getNome() + ", ";
                }

                showMessageDialog(getView(), "Nome : " + articolo.getNome() + "\n" +
                        "Descrizione : " + articolo.getDescrizione() + "\n" +
                        "Prezzo : " + articolo.getPrezzo() + "\n" +
                        "Categoria : " + listCatBuff + "\n" +
                        "Prodotto : " + articolo.getProdotto().getNome() + "\n" +
                        "Produttore : " + articolo.getProduttore().getNome() + "\n" +
                        "Fornitore : " + listFoBuff + "\n");
            }
        });

        richiediNotificaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (richiestaProgettoBox.getSelectedIndex() == -1)
                    showMessageDialog(getView(), "Scegliere un progetto");
                else {
                    Articolo articolo;
                    if (ricercaPerNomeField.getText().length() == 0) {
                        Categoria categoria = listCategoria.get(categoriaBox.getSelectedIndex());
                        Prodotto p = categoria.getProdotti().get(prodottoBox.getSelectedIndex());
                        List<Articolo> listA = sessione.ottieniListaArticoliPerCategoria(categoria);
                        List<Articolo> listAAA = new ArrayList<Articolo>();
                        for (Articolo a : listA)
                            if (a.getProdotto().getNome().equals(p.getNome()))
                                listAAA.add(a);
                        articolo = listAAA.get(articoloCatList.getSelectedIndex());
                    } else {
                        List<Articolo> listArtcl = sessione.ottieniListaArticoliPerRicerca(ricercaPerNomeField.getText());
                        articolo = listArtcl.get(articoloCatList.getSelectedIndex());
                    }
                    int indexProgetto = richiestaProgettoBox.getSelectedIndex();
                    Progetto progetto = d.getProgetti().get(indexProgetto);

                    List<RichiestaArticolo> listRichieste = sessione.ottieniListaRichiestaArticoliSede(progetto.getSede());
                    boolean richiestaPresente = false;
                    int indice = -1;
                    for (RichiestaArticolo ra : listRichieste)
                        if (ra.getArticolo().getNome().equals(articolo.getNome()) &&
                                ra.getProgetto().getNome().equals(progetto.getNome())) {
                            richiestaPresente = true;
                            indice = listRichieste.indexOf(ra);
                        }

                    if (richiestaPresente) {
                        RichiestaArticolo richiesta = listRichieste.get(indice);
                        int qt = richiesta.getQuantita();
                        int qt2 = Integer.parseInt(catalogoQuantitaField.getText());
                        int totale = qt + qt2;
                        richiesta.setQuantita(totale);
                        sessione.sistemaSave();
                        showMessageDialog(getView(), "Richiesta già presente, quantità aggiornata");
                    } else {
                        sessione.creaNotifica(articolo, progetto, Integer.parseInt(catalogoQuantitaField.getText()));
                        showMessageDialog(getView(), "Richiesta inviata con successo");
                    }
                }
            }
        });

        listOrdiniInviati.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if (listOrdiniInviati.getSelectedIndex() != -1) {
                    listStatoArticoliInviatiModel.clear();
                    int index = listOrdiniInviati.getSelectedIndex();
                    //Ordine o = listordine.get(index);
                    List<Ordine> listor = new ArrayList<Ordine>();
                    for (Ordine o : d.getOrdini())
                        if (o.isInviato() && !listor.contains(o)) {
                            listor.add(o);
                        }
                    Ordine o = listor.get(index);

                    for (ArticoloOrdine ao : o.getArticoliOrdine())
                        if (ao.isEvaso())
                            listStatoArticoliInviatiModel.addElement(ao.getArticolo().getNome() +
                                    "-> stato: evaso");
                        else
                            listStatoArticoliInviatiModel.addElement(ao.getArticolo().getNome() +
                                    "-> stato: in attesa di evasione");
                    listStatoArticoliInviati.setModel(listStatoArticoliInviatiModel);
                }
            }
        });
    }

    public void riempiMagazzino(List<ArticoloMagazzino> articoloMagazzinos, int limite) {
        for (ArticoloMagazzino am : articoloMagazzinos) {
            magazzinoNonBox.setForeground(new Color(0xff0000));
            magazzinoBox.setForeground(new Color(0x00ff00));
            if (am.getDisponibilita() >= limite && am.getDisponibilita() != 0) {
                magazzinoBox.addItem(am.getMagazzino().getNome());
            } else {
                magazzinoNonBox.addItem(am.getMagazzino().getNome());
            }
        }
    }

    public void paintImage(InputStream in) {
        try {
            BufferedImage buffed = ImageIO.read(in);
            ImageIcon icon = new ImageIcon(buffed);
            showMessageDialog(getView(), icon);
        } catch (IOException e) {
        }
    }

    public void show() {
        view.setVisible(true);
    }

    public JFrame getView() {
        return view;
    }
}