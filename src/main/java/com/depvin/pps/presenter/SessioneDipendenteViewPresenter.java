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
import java.awt.event.*;
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
    private JButton abilitaNuovoOrdineButton;

    private JTextField catalogoQuantitaField;
    private JTextField ordineQuantitaField;
    private JTextField nomeField;
    private JLabel ordineNomeLabel;
    private JLabel prezzoTotaleLabel;
    private JTextField ricercaPerNomeField;
    private JComboBox magazzinoNonBox;
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
        chiudiOrdineButton1.setEnabled(false);
        aggiungiArticoloAllOrdineButton.setEnabled(false);
        eliminaArticoloDallOrdineButton.setEnabled(false);
        cancellaOrdineButton.setEnabled(false);
        prodottoBox.setEnabled(false);
        magazzinoBox.setEnabled(false);
        catalogoQuantitaField.setEnabled(false);
        abilitaNuovoOrdineButton.setEnabled(false);

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

        listModelArticoliCatalogo = new DefaultListModel();
        listModelAppoggio = new DefaultListModel();

        for (Categoria cat : sessione.ottieniListaCategorie())
            categoriaBox.addItem(cat.getNome());
        categoriaBox.setSelectedIndex(-1);

        for (Progetto p : d.getProgetti()) {
            progettoOrdineBox.addItem(p.getNome());
            progettiBox.addItem(p.getNome());
        }
        progettiBox.setSelectedIndex(-1);
        progettoOrdineBox.setSelectedIndex(-1);
        view.pack();

        categoriaBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {

                richiediNotificaButton.setEnabled(false);
                quantitàMagazzinoLabel.setText("");

                prodottoBox.removeAllItems();
                magazzinoBox.setEnabled(false);
                magazzinoBox.removeAllItems();
                magazzinoBox.setSelectedIndex(-1);
                magazzinoNonBox.setEnabled(false);
                magazzinoNonBox.removeAllItems();
                magazzinoNonBox.setSelectedIndex(-1);
                int index = categoriaBox.getSelectedIndex();
                for (Prodotto prod : sessione.ottieniListaCategorie().get(index).getProdotti()) {
                    prodottoBox.addItem(prod.getNome());
                    listModelArticoliCatalogo.removeAllElements();
                }
                catalogoQuantitaField.setEnabled(false);
                prodottoBox.setEnabled(true);
                prodottoBox.setSelectedIndex(-1);
                articoloCatList.setEnabled(false);
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
                if (prodottoBox.getSelectedIndex() != -1) {

                    listModelArticoliCatalogo.clear();
                    articoloCatList.setModel(listModelArticoliCatalogo);

                    richiediNotificaButton.setEnabled(false);
                    quantitàMagazzinoLabel.setText("");

                    catalogoQuantitaField.setEnabled(false);
                    magazzinoBox.setEnabled(false);
                    magazzinoBox.removeAllItems();
                    magazzinoBox.setSelectedIndex(-1);
                    magazzinoNonBox.setEnabled(false);
                    magazzinoNonBox.removeAllItems();
                    magazzinoNonBox.setSelectedIndex(-1);

                    listModelArticoliCatalogo.clear();
                    listModelAppoggio.clear();

                    List<Categoria> listC = sessione.ottieniListaCategorie();
                    Categoria categoria = listC.get(categoriaBox.getSelectedIndex());
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
                } else {
                }
            }
        });

        catalogoQuantitaField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    quantitàMagazzinoLabel.setText("");
                    magazzinoBox.removeAllItems();
                    magazzinoNonBox.removeAllItems();
                    List<Categoria> listC = sessione.ottieniListaCategorie();
                    Categoria categoria = listC.get(categoriaBox.getSelectedIndex());
                    Prodotto p = categoria.getProdotti().get(prodottoBox.getSelectedIndex());
                    List<Articolo> listA = sessione.ottieniListaArticoliPerCategoria(categoria);
                    List<Articolo> listAAA = new ArrayList<Articolo>();
                    for (Articolo a : listA)
                        if (a.getProdotto().getNome().equals(p.getNome()))
                            listAAA.add(a);
                    Articolo articolo = listAAA.get(articoloCatList.getSelectedIndex());
                    List<ArticoloMagazzino> listAM = articolo.getInMagazzino();
                    riempiMagazzino(listAM, Integer.parseInt(catalogoQuantitaField.getText()));
                    magazzinoBox.setEnabled(true);
                    magazzinoBox.setSelectedIndex(-1);
                    magazzinoNonBox.setEnabled(true);
                    magazzinoNonBox.setSelectedIndex(-1);
                    catalogoQuantitaField.setEnabled(true);
                } catch (ArrayIndexOutOfBoundsException e) {
                }
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
                    List<Categoria> listC = sessione.ottieniListaCategorie();
                    Categoria categoria = listC.get(categoriaBox.getSelectedIndex());
                    Prodotto p = categoria.getProdotti().get(prodottoBox.getSelectedIndex());
                    List<Articolo> listA = sessione.ottieniListaArticoliPerCategoria(categoria);
                    List<Articolo> listAAA = new ArrayList<Articolo>();
                    for (Articolo a : listA)
                        if (a.getProdotto().getNome().equals(p.getNome()))
                            listAAA.add(a);
                    Articolo articolo = listAAA.get(articoloCatList.getSelectedIndex());
                    List<ArticoloMagazzino> listAM = articolo.getInMagazzino();
                    riempiMagazzino(listAM, 0);
                    magazzinoBox.setEnabled(true);
                    magazzinoBox.setSelectedIndex(-1);
                    magazzinoNonBox.setEnabled(true);
                    magazzinoNonBox.setSelectedIndex(-1);
                    catalogoQuantitaField.setEnabled(true);
                    catalogoQuantitaField.setText("");
                } catch (ArrayIndexOutOfBoundsException e) {
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
                    richiediNotificaButton.setEnabled(false);
                    listModelArticoliCatalogo.clear();
                    articoloCatList.removeAll();
                    List<Articolo> listA = sessione.ottieniListaArticoliPerRicerca(ricercaPerNomeField.getText());
                    for (Articolo a : listA)
                        listModelArticoliCatalogo.addElement(a.getNome());
                    articoloCatList.setModel(listModelArticoliCatalogo);
                } else {
                    articoloCatList.setModel(listModelAppoggio);
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
                    List<Categoria> listC = sessione.ottieniListaCategorie();
                    Categoria categoria = listC.get(categoriaBox.getSelectedIndex());
                    Prodotto p = categoria.getProdotti().get(prodottoBox.getSelectedIndex());
                    List<Articolo> listA = sessione.ottieniListaArticoliPerCategoria(categoria);
                    List<Articolo> listAAA = new ArrayList<Articolo>();
                    for (Articolo a : listA)
                        if (a.getProdotto().getNome().equals(p.getNome()))
                            listAAA.add(a);
                    Articolo articolo = listAAA.get(articoloCatList.getSelectedIndex());
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
                listModelArticoliOrdineCorrente = new DefaultListModel();
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
                chiudiOrdineButton1.setEnabled(true);
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
                prodottoBox.setSelectedIndex(-1);

                labelEvadiPrezzo.setText(Float.toString(totale) + " €");
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
                //TODO: Rimuovere per bene gli articoli dall'ordine
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
                    showMessageDialog(getView(), "Il campo \"Inserire nuoova quantità\" non può essere lasciato vuoto");
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
                    listModelArticoliOrdineCorrente = new DefaultListModel();
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

        //TODO: Aggiungere il fatto che, se già presente, deve aumentare solo la quantità
        aggiungiArticoloAllOrdineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (ricercaPerNomeField.getText().length() == 0) {
                    List<Categoria> listC = sessione.ottieniListaCategorie();
                    Categoria categoria = listC.get(categoriaBox.getSelectedIndex());
                    Prodotto p = categoria.getProdotti().get(prodottoBox.getSelectedIndex());
                    List<Articolo> listA = sessione.ottieniListaArticoliPerCategoria(categoria);
                    List<Articolo> listAAA = new ArrayList<Articolo>();
                    for (Articolo a : listA)
                        if (a.getProdotto().getNome().equals(p.getNome()))
                            listAAA.add(a);
                    Articolo articolo = listAAA.get(articoloCatList.getSelectedIndex());
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
                    sessione.aggiungiArticoloOrdine(ao, ao.getOrdine());

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

                    listOrdineArticoliNuovo.clear();
                    for (ArticoloOrdine aoos : d.getOrdini().get(innd).getArticoliOrdine()) {

                        String newwBudget = Float.toString(aoos.getArticolo().getPrezzo());
                        newwBudget = newwBudget.replaceAll(",", ".");
                        if (!newwBudget.contains("."))
                            newwBudget = newwBudget + ".00";
                        float buddget = Float.parseFloat(newwBudget);

                        listOrdineArticoliNuovo.addElement(aoos.getArticolo().getNome() + "   x " +
                                aoos.getQuantita() + "     : " + buddget + " €");
                    }
                    ordineArticoliNuovoList.setModel(listOrdineArticoliNuovo);
                }
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

        abilitaNuovoOrdineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                nuovoOrdineButton.setEnabled(true);
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

    public void show() {
        view.setVisible(true);
    }

    public JFrame getView() {
        return view;
    }
}