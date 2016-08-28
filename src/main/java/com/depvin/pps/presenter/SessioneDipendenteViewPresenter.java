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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;
import static javax.swing.JOptionPane.showMessageDialog;
import java.awt.Color;

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

    private DefaultListModel listModelArticoliCatalogo;
    private DefaultListModel listModelOrdinePendente;
    private DefaultListModel listModelArticoliOrdineCorrente;
    private DefaultListModel listModelAppoggio;
    private DefaultListModel listOrdineArticoliNuovo;

    public SessioneDipendenteViewPresenter(final SessioneDipendente sessione) {
        this.sessione = sessione;
        final Dipendente d = sessione.getUtente();
        view = new JFrame("Sessione: " + d.getNome() + " " + d.getCognome());
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

        for (Categoria cat : sessione.ottieniListaCategorie())
            categoriaBox.addItem(cat.getNome());
        categoriaBox.setSelectedIndex(-1);

        for (Progetto p : d.getProgetti()) {
            progettoOrdineBox.addItem(p.getNome());
            progettiBox.addItem(p.getNome());
        }

        final List<Categoria> listCategoria = sessione.ottieniListaCategorie();

        progettiBox.setSelectedIndex(-1);
        progettoOrdineBox.setSelectedIndex(-1);
        view.pack();

        categoriaBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (ricercaPerNomeField.getText().length() == 0) {

                    listModelArticoliCatalogo.clear();
                    articoloCatList.setModel(listModelArticoliCatalogo);

                    richiediNotificaButton.setEnabled(false);
                    aggiungiArticoloAllOrdineButton.setEnabled(false);
                    quantitàMagazzinoLabel.setText("");

                    magazzinoBox.setEnabled(false);
                    magazzinoBox.removeAllItems();
                    magazzinoBox.setSelectedIndex(-1);
                    magazzinoNonBox.setEnabled(false);
                    magazzinoNonBox.removeAllItems();
                    magazzinoNonBox.setSelectedIndex(-1);

                    prodottoBox.removeAllItems();
                    int index = categoriaBox.getSelectedIndex();
                    for (Prodotto prod : listCategoria.get(index).getProdotti()) {
                        prodottoBox.addItem(prod.getNome());
                        listModelArticoliCatalogo.removeAllElements();
                    }
                    catalogoQuantitaField.setText("");
                    catalogoQuantitaField.setEnabled(false);
                    prodottoBox.setEnabled(true);
                    prodottoBox.setSelectedIndex(-1);
                    articoloCatList.setEnabled(false);
                } else {
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
                if (prodottoBox.getSelectedIndex() != -1 && ricercaPerNomeField.getText().length() == 0) {

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

                    listModelArticoliCatalogo.clear();
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
                    articoloCatList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                    articoloCatList.setModel(listModelArticoliCatalogo);
                    articoloCatList.setVisible(true);
                    articoloCatList.setEnabled(true);
                    aggiungiArticoloAllOrdineButton.setEnabled(false);

                } else if (prodottoBox.getSelectedIndex() != -1 && ricercaPerNomeField.getText().length() != 0) {

                    listModelArticoliCatalogo.clear();
                    listModelArticoliCatalogo = new DefaultListModel();
                    articoloCatList.setModel(listModelArticoliCatalogo);
                    richiediNotificaButton.setEnabled(false);
                    quantitàMagazzinoLabel.setText("");
                    catalogoQuantitaField.setEnabled(false);
                    articoloCatList.removeAll();

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
                }
            }
        });

        catalogoQuantitaField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent documentEvent) {
                try {
                    Runnable run = new Runnable() {
                        public void run() {
                            Articolo articolo = null;
                            String nomeBox = (String) magazzinoBox.getSelectedItem();
                            magazzinoBox.removeAllItems();
                            magazzinoNonBox.removeAllItems();
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
                            riempiMagazzino(listAM, Integer.parseInt(catalogoQuantitaField.getText()));
                            int j = 0;
                            int index = 0;
                            for (int x = 0; x < magazzinoBox.getItemCount(); x++) {
                                if (magazzinoBox.getItemAt(x).equals(nomeBox)) {
                                    j++;
                                    index = magazzinoBox.getSelectedIndex();
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

        articoloCatList.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {
                try {
                    aggiungiArticoloAllOrdineButton.setEnabled(true);
                    richiediNotificaButton.setEnabled(true);
                    quantitàMagazzinoLabel.setText("");
                    magazzinoBox.removeAllItems();
                    magazzinoNonBox.removeAllItems();
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
                    catalogoQuantitaField.setEnabled(true);
                    catalogoQuantitaField.setText("");

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
                        if (am.getDisponibilita() > limite)
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

                }
            }
        });

        progettiBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int index = progettiBox.getSelectedIndex();
                Progetto prog = d.getProgetti().get(index);
                listModelOrdinePendente.clear();
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

                Progetto prog = d.getProgetti().get(indexProg);
                Ordine ord = prog.getOrdini().get(indexOrd);
                listModelArticoliOrdineCorrente.clear();
                for (ArticoloOrdine ao : ord.getArticoliOrdine()) {

                    String newBudget = Float.toString(ao.getArticolo().getPrezzo());
                    newBudget = newBudget.replaceAll(",", ".");
                    if (!newBudget.contains("."))
                        newBudget = newBudget + ".00";
                    float budget = Float.parseFloat(newBudget);

                    listModelArticoliOrdineCorrente.addElement(ao.getArticolo().getNome() + "     x " + ao.getQuantita() +
                            "     : " + budget + " €");
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
                prezzoTotaleLabel.setText(Float.toString(totale));

                prodottoBox.removeAllItems();
                prodottoBox.setEnabled(false);

                catalogoQuantitaField.setEnabled(false);
                eliminaArticoloDallOrdineButton.setEnabled(false);

                listModelArticoliCatalogo.clear();
                articoloCatList.setModel(listModelArticoliCatalogo);

                labelEvadiPrezzo.setText(Float.toString(totale) + " €");
                modificaQuantitàButton.setEnabled(false);
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
                            "     : " + budget + " €");
                    listOrdineArticoliNuovo.addElement(aor.getArticolo().getNome() + "     x " + aor.getQuantita() +
                            "     : " + budget + " €");
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
                if (ordineQuantitaField.getText().length() == 0)
                    showMessageDialog(getView(), "Il campo \"Inserire nuova quantità\" non può essere lasciato vuoto");
                else if (ordineQuantitaField.getText().contains(",") || ordineQuantitaField.getText().contains(".") || Integer.parseInt(ordineQuantitaField.getText()) < 0)
                    showMessageDialog(getView(), "La quantità deve essere un numero intero positivo!");
                else {
                    int index = 0;
                    for (Ordine o : d.getOrdini())
                        if (o.getNome().equals(ordineNomeLabel.getText()))
                            index = d.getOrdini().indexOf(o);
                    Ordine ordine = d.getOrdini().get(index);
                    int indexArt = ordineCorrenteList.getSelectedIndex();
                    ArticoloOrdine ao = ordine.getArticoliOrdine().get(indexArt);
                    sessione.modificaDisponibilitàArticoloOrdine(ao, Integer.parseInt(ordineQuantitaField.getText()));
                    //listModelArticoliOrdineCorrente = new DefaultListModel();
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
                                "     : " + budget + " €");
                        listOrdineArticoliNuovo.addElement(aor.getArticolo().getNome() + "     x " + aor.getQuantita() +
                                "     : " + budget + " €");
                        app = app + (budget * aor.getQuantita());
                    }
                    labelEvadiPrezzo.setText(Float.toString(app) + " €");
                    prezzoTotaleLabel.setText(Float.toString(app) + " €");
                    ordineCorrenteList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                    ordineArticoliNuovoList.setModel(listOrdineArticoliNuovo);
                    ordineCorrenteList.setModel(listModelArticoliOrdineCorrente);
                    modificaQuantitàButton.setEnabled(false);
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
                    aggiungiArticoloAllOrdineButton.setEnabled(true);
                    nuovoOrdineLabel.setText(nomeField.getText());
                }
            }
        });

        aggiungiArticoloAllOrdineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (magazzinoBox.getSelectedIndex() == -1 || catalogoQuantitaField.getText().length() == 0)
                    showMessageDialog(getView(), "Non hai scelto il magazzino e/o non hai inserito la quantità adatta");
                else {
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
                        if (am.getDisponibilita() > limite)
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
                            limite + "     : " + budget + " €");
                    float totale = 0;
                    for (ArticoloOrdine aos : d.getOrdini().get(innd).getArticoliOrdine())
                        totale = totale + aos.getParziale();

                    prezzoTotaleLabel.setText(Float.toString(totale));
                    labelEvadiPrezzo.setText(Float.toString(totale));

                    listModelArticoliOrdineCorrente.clear();
                    listOrdineArticoliNuovo.clear();
                    for (ArticoloOrdine aoos : d.getOrdini().get(innd).getArticoliOrdine()) {

                        String newwBudget = Float.toString(aoos.getArticolo().getPrezzo());
                        newwBudget = newwBudget.replaceAll(",", ".");
                        if (!newwBudget.contains("."))
                            newwBudget = newwBudget + ".00";
                        float buddget = Float.parseFloat(newwBudget);

                        listOrdineArticoliNuovo.addElement(aoos.getArticolo().getNome() + "   x " +
                                aoos.getQuantita() + "     : " + buddget + " €");
                        listModelArticoliOrdineCorrente.addElement(aoos.getArticolo().getNome() + "   x " +
                                aoos.getQuantita() + "     : " + buddget + " €");
                    }
                    ordineArticoliNuovoList.setModel(listOrdineArticoliNuovo);
                    ordineCorrenteList.setModel(listModelArticoliOrdineCorrente);
                }
            }
        });

        ordineCorrenteList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                inviaOrdine.setEnabled(true);
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
                    try {
                        sessione.stampaOrdine(ordine);
                        showMessageDialog(getView(), "Ordine stampato con successo");
                    } catch (ReportCreationFailedException e) {
                        showMessageDialog(getView(), "Errore nella stampa dell'ordine");
                    }
                } else {
                    showMessageDialog(getView(), "Ordine già inviato, attendere per l'evasione");
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

                byte[] bytes = articolo.getImmagine();
                InputStream in = new ByteArrayInputStream(bytes);
                paintImage(in);
            }
        });

        ottieniInformazioniButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
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
                int indexProgetto = progettoOrdineBox.getSelectedIndex();
                Progetto progetto = d.getProgetti().get(indexProgetto);
                sessione.creaNotifica(articolo, progetto, Integer.parseInt(catalogoQuantitaField.getText()));
                showMessageDialog(getView(), "Richiesta inviata con successo");
            }
        });

    }

    public void riempiMagazzino(List<ArticoloMagazzino> articoloMagazzinos, int limite) {
        for (ArticoloMagazzino am : articoloMagazzinos) {
            magazzinoNonBox.setForeground(new Color(0xff0000));
            magazzinoBox.setForeground(new Color(0x00ff00));
            if (am.getDisponibilita() > limite) {
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