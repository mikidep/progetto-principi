package com.depvin.pps.presenter;

import com.depvin.pps.business.EvasionException;
import com.depvin.pps.business.ReportCreationFailedException;
import com.depvin.pps.business.SessioneMagazziniere;
import com.depvin.pps.model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by costantino on 24/05/16.
 */
public class SessioneMagazziniereViewPresenter {

    SessioneMagazziniere sessione;
    private JFrame view;
    private JPanel rootPanel;
    private JTabbedPane tabbedPane;

    private JList listModificaArticoloMagazzino;
    private JList listArticoliOrdinati;
    private JList listAggiungiArticoloMagazzino;
    private JList listModificaDisponibilitàArticoloMagazzino;
    private JList listRichiesteArticoli;
    private JList listOrdini;
    private JList listArticoliDisponibiliOrdine;

    private JLabel labelquantitàDisponibile;
    private JTextField nomeField;
    private JTextField descrizioneField;
    private JTextField quantitàField;
    private JTextField prezzoField;
    private JTextField categoriaField;
    private JTextField prodottoField;
    private JTextField produttoreField;
    private JTextField fornitoreField;
    private JTextField quantitàFieldMod;
    private JTextField nomeModificaField;
    private JTextField descrizioneModificaField;
    private JTextField prezzoModificaField;
    private JTextField categoriaAggiungiField;
    private JTextField prodottoModificaField;
    private JTextField produttoreModificaField;
    private JTextField fornitoreModificaField;
    private JTextField nuovoFornitorefield;
    private JTextField categoriaModificaField;
    private JTextField vecchioFornitoreField;
    private JTextField immagineModificaField;
    private JTextField immagineField;

    private JButton aggiungiArticoloNelDatabaseButton;
    private JButton pulisciTuttiICampiButton;
    private JButton confermaModificaButton;
    private JButton ottieniInformazioniButton;
    private JButton confermaModificheButton;
    private JButton stampaArticoliOrdineButton;
    private JButton pulisciTuttiICampiButton1;
    private JButton modificaFornitoreButton;
    private JButton ottieniImmagineButton;
    private JButton modificaCategoriaButton;
    private JButton clearButton;
    private JButton evadiOrdineButton;
    private JButton scegliImmagineButton;
    private JButton scegliImmagineButton1;
    private JComboBox categoriaBox;
    private JComboBox prodottoBox;

    private DefaultListModel listArticoliDisponibiliOrdineModel;
    private DefaultListModel listOrdiniModel;
    private DefaultListModel listRichiesteArticoliModel;
    private DefaultListModel listArticoliOrdinatiModel;
    private DefaultListModel listArticoliMagazzinoModel;

    public SessioneMagazziniereViewPresenter(final SessioneMagazziniere sessione) {

        this.sessione = sessione;
        final Magazziniere m = sessione.getUtente();
        view = new JFrame("Sessione: " + m.getNome() + " " + m.getCognome());
        rootPanel.setPreferredSize(new Dimension(1000, 750));
        view.setLocation(200, 0);
        view.setContentPane(rootPanel);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final List<ArticoloMagazzino> listAM = m.getMagazzino().getArticoliMagazzino();
        final List<Articolo> listAAA = new ArrayList<Articolo>();
        for (ArticoloMagazzino ammm : listAM)
            listAAA.add(ammm.getArticolo());

        for (Articolo a : sessione.ottieniListaArticoli()) {
            if (!listAAA.contains(a))
                sessione.aggiungiArticoloMagazzino(a, 0);
        }
        for (Categoria c : sessione.ottieniListaCategoria()) {
            categoriaBox.addItem(c.getNome());
            for (Prodotto p : sessione.ottieniListaProdotto(c)) {
                prodottoBox.addItem(p.getNome());
            }
        }
        prodottoBox.addItem("Nuovo");
        categoriaBox.addItem("Nuovo");
        prodottoBox.setSelectedIndex(-1);
        categoriaBox.setSelectedIndex(-1);
        categoriaField.setEnabled(false);
        prodottoField.setEnabled(false);

        listOrdiniModel = new DefaultListModel();
        listRichiesteArticoliModel = new DefaultListModel();
        listArticoliDisponibiliOrdineModel = new DefaultListModel();
        listArticoliMagazzinoModel = new DefaultListModel();
        listArticoliOrdinatiModel = new DefaultListModel();

        listOrdini.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listOrdini.setVisible(true);

        listRichiesteArticoli.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listRichiesteArticoli.setVisible(true);

        listArticoliDisponibiliOrdine.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listArticoliDisponibiliOrdine.setVisible(true);

        listArticoliOrdinati.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listArticoliOrdinati.setVisible(true);

        listAggiungiArticoloMagazzino.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listAggiungiArticoloMagazzino.setVisible(true);

        listModificaDisponibilitàArticoloMagazzino.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listModificaDisponibilitàArticoloMagazzino.setVisible(true);

        listModificaArticoloMagazzino.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listModificaArticoloMagazzino.setVisible(true);


        for (ArticoloMagazzino am : m.getMagazzino().getArticoliMagazzino())
            listArticoliMagazzinoModel.addElement(am.getArticolo().getNome());

        listAggiungiArticoloMagazzino.setModel(listArticoliMagazzinoModel);
        listModificaDisponibilitàArticoloMagazzino.setModel(listArticoliMagazzinoModel);
        listModificaArticoloMagazzino.setModel(listArticoliMagazzinoModel);

        List<Ordine> listaOrdine = new ArrayList<Ordine>();
        for (ArticoloOrdine ao : sessione.ottieniListaArticoliOrdine(m.getMagazzino()))
            if (!listaOrdine.contains(ao.getOrdine()) && ao.getOrdine().isInviato())
                if (!ao.isEvaso()) {
                    listaOrdine.add(ao.getOrdine());
                    listOrdiniModel.addElement(ao.getOrdine().getNome());
                }

        listOrdini.setModel(listOrdiniModel);

        List<RichiestaArticolo> listRA = sessione.ottieniListaRichiestaArticoliSede(m.getMagazzino().getSede());
        for (RichiestaArticolo ra : listRA)
            listRichiesteArticoliModel.addElement(ra.getArticolo().getNome() + "   x " + ra.getQuantita() +
                    "  progetto : " + ra.getProgetto().getNome());
        listRichiesteArticoli.setModel(listRichiesteArticoliModel);

        tabbedPane.setVisible(true);
        ottieniImmagineButton.setEnabled(false);
        ottieniInformazioniButton.setEnabled(false);
        confermaModificheButton.setEnabled(false);
        modificaCategoriaButton.setEnabled(false);
        modificaFornitoreButton.setEnabled(false);
        confermaModificaButton.setEnabled(false);
        evadiOrdineButton.setEnabled(false);
        stampaArticoliOrdineButton.setEnabled(false);

        view.pack();

        listModificaDisponibilitàArticoloMagazzino.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                int index = listModificaDisponibilitàArticoloMagazzino.getSelectedIndex();
                ArticoloMagazzino am = m.getMagazzino().getArticoliMagazzino().get(index);
                labelquantitàDisponibile.setText(String.valueOf(am.getDisponibilita()));
                labelquantitàDisponibile.setVisible(true);
                confermaModificaButton.setEnabled(true);
            }
        });

        listModificaArticoloMagazzino.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                ottieniImmagineButton.setEnabled(true);
                ottieniInformazioniButton.setEnabled(true);
                confermaModificheButton.setEnabled(true);
                modificaCategoriaButton.setEnabled(true);
                modificaFornitoreButton.setEnabled(true);
            }
        });

        stampaArticoliOrdineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {

                List<Ordine> listOrdine = new ArrayList<Ordine>();
                for (ArticoloOrdine ao : sessione.ottieniListaArticoliOrdine(m.getMagazzino()))
                    if (!listOrdine.contains(ao.getOrdine()) && ao.getOrdine().isInviato())
                        if (!ao.isEvaso())
                            listOrdine.add(ao.getOrdine());

                int indexO = listOrdini.getSelectedIndex();
                Ordine o = listOrdine.get(indexO);
                List<ArticoloOrdine> listAO = o.getArticoliOrdine();
                GregorianCalendar gc = new GregorianCalendar();

                //TODO:
                // A seconda del computer che verrà presentato verrà cambiata la radice in cui salvare la stampa
                try {
                    ByteArrayOutputStream bytes = sessione.stampaArticoliOrdine(m.getNome() + " " + gc.get(Calendar.DATE) + gc.get(Calendar.MONTH) +
                            gc.get(Calendar.YEAR) + gc.get(Calendar.HOUR) + gc.get(Calendar.MINUTE), listAO);

                    FileOutputStream of = new FileOutputStream("/home/costantino/pdf_progetto/" + m.getNome() + "_" +
                            gc.get(Calendar.DATE) + "_" + gc.get(Calendar.MONTH) + "_" + gc.get(Calendar.YEAR) +
                            "_" + gc.get(Calendar.HOUR) + "_" + gc.get(Calendar.MINUTE) + ".pdf");
                    bytes.writeTo(of);
                    of.close();

                    showMessageDialog(getView(), "Stampa avvenuta con successo");
                } catch (ReportCreationFailedException e) {
                    showMessageDialog(getView(), "Errore nella stampa dell'ordine");
                } catch (IOException e) {
                    showMessageDialog(getView(), "IOexception, impossibile stampare");
                }
            }
        });

        aggiungiArticoloNelDatabaseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (nomeField.getText().length() == 0 || descrizioneField.getText().length() == 0 ||
                        prezzoField.getText().length() == 0 || quantitàField.getText().length() == 0 ||
                        (prodottoField.getText().length() == 0 && prodottoBox.getSelectedIndex() == -1) ||
                        produttoreField.getText().length() == 0 || fornitoreField.getText().length() == 0 ||
                        immagineField.getText().length() == 0)
                    showMessageDialog(getView(), "Riempire tutti i campi");
                else {
                    String newBudget = prezzoField.getText();
                    newBudget = newBudget.replaceAll(",", ".");
                    if (!newBudget.contains("."))
                        newBudget = newBudget + ".00";
                    float budget = Float.parseFloat(newBudget);

                    List<Categoria> listC = new ArrayList<Categoria>();

                    if (categoriaBox.getSelectedItem().equals("Nuovo")) {
                        Categoria categoria = new Categoria(categoriaField.getText());
                        List<Categoria> listCAT = sessione.ottieniListaCategoria();
                        for (Categoria cat : listCAT) {
                            if (listC.isEmpty())
                                if (cat.getNome().equals(categoria.getNome()))
                                    listC.add(cat);
                        }
                        if (listC.isEmpty())
                            listC.add(categoria);
                    } else {
                        Categoria cat = sessione.ottieniListaCategoria().get(categoriaBox.getSelectedIndex());
                        listC.add(cat);
                    }

                    Fornitore fornitore = new Fornitore(fornitoreField.getText());
                    List<Fornitore> listF = new ArrayList<Fornitore>();
                    listF.add(fornitore);
                    Produttore produttore = new Produttore(produttoreField.getText());
                    Prodotto prodotto = new Prodotto("");
                    if (prodottoBox.getSelectedItem().equals("Nuovo")) {
                        int k = 0;
                        int indexx = -1;
                        List<Prodotto> lp = new ArrayList<Prodotto>();
                        for (Categoria c : sessione.ottieniListaCategoria())
                            for (Prodotto p : sessione.ottieniListaProdotto(c))
                                lp.add(p);
                        for (Prodotto p : lp)
                            if (p.getNome().equals(prodottoField.getText())) {
                                indexx = lp.indexOf(p);
                                k++;
                            }
                        if (k > 0)
                            prodotto = lp.get(indexx);
                        else
                            prodotto = new Prodotto(prodottoField.getText(), listC);
                    } else {
                        List<Prodotto> lp = new ArrayList<Prodotto>();
                        for (Categoria c : sessione.ottieniListaCategoria())
                            for (Prodotto p : sessione.ottieniListaProdotto(c))
                                lp.add(p);
                        prodotto = lp.get(prodottoBox.getSelectedIndex());
                    }
                    Articolo articolo = new Articolo(nomeField.getText(), descrizioneField.getText(),
                            budget, prodotto, produttore, listF);
                    try {
                        BufferedImage img = ImageIO.read(new File(immagineField.getText()));
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(img, "jpg", baos);
                        baos.flush();
                        byte[] imageInByte = baos.toByteArray();
                        sessione.aggiungiImmagineArticolo(articolo, imageInByte);
                        baos.close();

                    ArticoloMagazzino amg = new ArticoloMagazzino(m.getMagazzino(), articolo,
                            Integer.parseInt(quantitàField.getText()));
                    List<ArticoloMagazzino> listAM = m.getMagazzino().getArticoliMagazzino();
                    int index = 0;
                    for (ArticoloMagazzino ao : listAM)
                        if (ao.getArticolo().getNome().equals(amg.getArticolo().getNome()))
                            index += 1;
                    if (index > 0)
                        showMessageDialog(getView(),
                                "Impossibile aggiungere l'articolo nel magazzino. Articolo già presente");
                    else {
                        sessione.aggiungiArticoloMagazzino(articolo, Integer.parseInt(quantitàField.getText()));
                        showMessageDialog(getView(), "Articolo aggiunto con successo");

                        listArticoliMagazzinoModel.clear();
                        for (ArticoloMagazzino am : m.getMagazzino().getArticoliMagazzino())
                            listArticoliMagazzinoModel.addElement(am.getArticolo().getNome());
                        listAggiungiArticoloMagazzino.setModel(listArticoliMagazzinoModel);
                        categoriaBox.removeAllItems();
                        prodottoBox.removeAllItems();
                        for (Categoria c : sessione.ottieniListaCategoria()) {
                            categoriaBox.addItem(c.getNome());
                            for (Prodotto p : sessione.ottieniListaProdotto(c)) {
                                prodottoBox.addItem(p.getNome());
                            }
                        }
                        prodottoBox.addItem("Nuovo");
                        categoriaBox.addItem("Nuovo");
                        categoriaField.setEnabled(false);
                        prodottoField.setEnabled(false);
                        prodottoBox.setSelectedIndex(-1);
                        categoriaBox.setSelectedIndex(-1);
                    }
                    } catch (IOException e) {
                    }
                }
            }
        });

        pulisciTuttiICampiButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                nomeField.setText("");
                descrizioneField.setText("");
                prezzoField.setText("");
                quantitàField.setText("");
                prodottoField.setText("");
                produttoreField.setText("");
                fornitoreField.setText("");
                categoriaField.setText("");
                immagineField.setText("");
            }
        });

        confermaModificaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int index = listModificaDisponibilitàArticoloMagazzino.getSelectedIndex();
                ArticoloMagazzino am = m.getMagazzino().getArticoliMagazzino().get(index);
                if (quantitàFieldMod.getText().length() != 0) {
                    sessione.modificaQuantitàArticolo(am, Integer.parseInt(quantitàFieldMod.getText()), m.getMagazzino());
                } else
                    showMessageDialog(getView(), "Il campo \"modifica disponibilità\" non può rimanere vuoto");
                labelquantitàDisponibile.setText(String.valueOf(am.getDisponibilita()));

                listRichiesteArticoliModel.clear();
                List<RichiestaArticolo> listRA = sessione.ottieniListaRichiestaArticoliSede(m.getMagazzino().getSede());
                for (RichiestaArticolo ra : listRA)
                    listRichiesteArticoliModel.addElement(ra.getArticolo().getNome() + "   x " + ra.getQuantita() +
                            "  progetto : " + ra.getProgetto().getNome());
                listRichiesteArticoli.setModel(listRichiesteArticoliModel);
            }
        });

        ottieniInformazioniButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int index = listModificaArticoloMagazzino.getSelectedIndex();
                ArticoloMagazzino am = m.getMagazzino().getArticoliMagazzino().get(index);
                String listFoBuff = "";
                String listCatBuff = "";
                int len = am.getArticolo().getProdotto().getCategorie().size();
                for (Categoria c : am.getArticolo().getProdotto().getCategorie()) {
                    if (am.getArticolo().getProdotto().getCategorie().indexOf(c) == len - 1)
                        listCatBuff = listCatBuff + c.getNome();
                    else
                        listCatBuff = listCatBuff + c.getNome() + ", ";
                }
                int len2 = am.getArticolo().getFornitori().size();
                for (Fornitore f : am.getArticolo().getFornitori()) {
                    if (am.getArticolo().getFornitori().indexOf(f) == len2 - 1)
                        listFoBuff = listFoBuff + f.getNome();
                    else
                        listFoBuff = listFoBuff + f.getNome() + ", ";
                }
                showMessageDialog(getView(), "Nome : " + am.getArticolo().getNome() + "\n" +
                        "Descrizione : " + am.getArticolo().getDescrizione() + "\n" +
                        "Prezzo : " + am.getArticolo().getPrezzo() + "\n" +
                        "Categoria : " + listCatBuff + "\n" +
                        "Prodotto : " + am.getArticolo().getProdotto().getNome() + "\n" +
                        "Produttore : " + am.getArticolo().getProduttore().getNome() + "\n" +
                        "Fornitore : " + listFoBuff + "\n");
            }
        });

        ottieniImmagineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int index = listModificaArticoloMagazzino.getSelectedIndex();
                ArticoloMagazzino am = m.getMagazzino().getArticoliMagazzino().get(index);
                byte[] bytes = am.getArticolo().getImmagine();
                InputStream in = new ByteArrayInputStream(bytes);
                paintImage(in);
            }
        });

        pulisciTuttiICampiButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                nomeModificaField.setText("");
                descrizioneModificaField.setText("");
                prezzoModificaField.setText("");
                prodottoModificaField.setText("");
                produttoreModificaField.setText("");
                fornitoreModificaField.setText("");
                categoriaAggiungiField.setText("");
                immagineModificaField.setText("");
                vecchioFornitoreField.setText("");
                categoriaModificaField.setText("");
                nuovoFornitorefield.setText("");
            }
        });

        confermaModificheButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (categoriaAggiungiField.getText().length() == 0 && prodottoModificaField.getText().length() == 0 &&
                        produttoreModificaField.getText().length() == 0 && nomeModificaField.getText().length() == 0 &&
                        prezzoModificaField.getText().length() == 0 && fornitoreModificaField.getText().length() == 0 &&
                        descrizioneModificaField.getText().length() == 0 && immagineModificaField.getText().length() == 0) {
                    showMessageDialog(getView(), "Nessuna modifica compiuta, tutti i campi sono vuoti");
                } else {
                    int index = listModificaArticoloMagazzino.getSelectedIndex();
                    ArticoloMagazzino am = m.getMagazzino().getArticoliMagazzino().get(index);
                    if (categoriaAggiungiField.getText().length() != 0) {
                        List<Categoria> listc = sessione.ottieniListaCategoria();
                        int ind = 0;
                        for (Categoria cat : listc)
                            if (cat.getNome().equals(categoriaAggiungiField.getText()))
                                ind = 10;
                        if (ind == 0) {
                            sessione.aggiungiCategoriaArticolo(am, categoriaAggiungiField.getText());
                            showMessageDialog(getView(), "Modica avvenuta con successo");
                        } else
                            showMessageDialog(getView(), "Categoria già presente");
                    }
                    if (prodottoModificaField.getText().length() != 0) {
                        sessione.modificaProdottoArticolo(am, prodottoModificaField.getText());
                        showMessageDialog(getView(), "Modica avvenuta con successo");

                    }
                    if (produttoreModificaField.getText().length() != 0) {
                        sessione.modificaProduttoreArticolo(am, produttoreModificaField.getText());
                        showMessageDialog(getView(), "Modica avvenuta con successo");
                    }
                    if (nomeModificaField.getText().length() != 0) {
                        sessione.modificaNomeArticolo(am, nomeModificaField.getText());
                        showMessageDialog(getView(), "Modica avvenuta con successo");
                    }
                    if (prezzoModificaField.getText().length() != 0) {
                        sessione.modificaPrezzoArticolo(am, Float.parseFloat(prezzoModificaField.getText()));
                        showMessageDialog(getView(), "Modica avvenuta con successo");
                    }
                    if (fornitoreModificaField.getText().length() != 0) {
                        sessione.aggiungiFornitoreArticolo(am, fornitoreModificaField.getText());
                        showMessageDialog(getView(), "Modica avvenuta con successo");
                    }
                    if (descrizioneModificaField.getText().length() != 0) {
                        sessione.modificaDescrizioneArticolo(am, descrizioneModificaField.getText());
                        showMessageDialog(getView(), "Modica avvenuta con successo");
                    }
                    if (immagineModificaField.getText().length() != 0) {
                        try {
                            BufferedImage img = ImageIO.read(new File(immagineModificaField.getText()));
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            ImageIO.write(img, "jpg", baos);
                            baos.flush();
                            byte[] imageInByte = baos.toByteArray();
                            baos.close();
                            sessione.modificaImmagineArticolo(am, imageInByte);
                            showMessageDialog(getView(), "Modica avvenuta con successo");
                        } catch (IOException e) {
                        }
                    }
                    listArticoliMagazzinoModel.clear();
                    for (ArticoloMagazzino artm : m.getMagazzino().getArticoliMagazzino())
                        listArticoliMagazzinoModel.addElement(artm.getArticolo().getNome());
                    listAggiungiArticoloMagazzino.setModel(listArticoliMagazzinoModel);
                }
            }
        });

        modificaFornitoreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (nuovoFornitorefield.getText().length() != 0 && vecchioFornitoreField.getText().length() != 0) {
                    int index = listModificaArticoloMagazzino.getSelectedIndex();
                    ArticoloMagazzino am = m.getMagazzino().getArticoliMagazzino().get(index);
                    sessione.modificaFornitoreArticolo(am, nuovoFornitorefield.getText(), vecchioFornitoreField.getText());
                    listArticoliMagazzinoModel.clear();
                    for (ArticoloMagazzino ammm : m.getMagazzino().getArticoliMagazzino())
                        listArticoliMagazzinoModel.addElement(ammm.getArticolo().getNome());
                    listAggiungiArticoloMagazzino.setModel(listArticoliMagazzinoModel);
                    listModificaArticoloMagazzino.setModel(listArticoliMagazzinoModel);
                    listModificaDisponibilitàArticoloMagazzino.setModel(listArticoliMagazzinoModel);
                } else
                    showMessageDialog(getView(), "I campi \"Vecchio fornitore\" e \"Nuovo fornitore\" non possono" +
                            " rimanere vuoti");
            }
        });

        modificaCategoriaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (categoriaModificaField.getText().length() != 0) {
                    int index = listModificaArticoloMagazzino.getSelectedIndex();
                    ArticoloMagazzino am = m.getMagazzino().getArticoliMagazzino().get(index);
                    sessione.modificaCategoriaArticolo(am, categoriaModificaField.getText());
                    listArticoliMagazzinoModel.clear();
                    for (ArticoloMagazzino ammm : m.getMagazzino().getArticoliMagazzino())
                        listArticoliMagazzinoModel.addElement(ammm.getArticolo().getNome());
                    listAggiungiArticoloMagazzino.setModel(listArticoliMagazzinoModel);
                    listModificaArticoloMagazzino.setModel(listArticoliMagazzinoModel);
                    listModificaDisponibilitàArticoloMagazzino.setModel(listArticoliMagazzinoModel);
                } else
                    showMessageDialog(getView(), "Il campo \"Modifica Categoria\" non può rimanere vuoto");
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                quantitàFieldMod.setText("");
            }
        });

        listOrdini.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {

                stampaArticoliOrdineButton.setEnabled(true);

                List<Ordine> listOrdine = new ArrayList<Ordine>();
                for (ArticoloOrdine ao : sessione.ottieniListaArticoliOrdine(m.getMagazzino()))
                    if (!listOrdine.contains(ao.getOrdine()) && ao.getOrdine().isInviato())
                        if (!ao.isEvaso())
                            listOrdine.add(ao.getOrdine());

                evadiOrdineButton.setEnabled(true);
                int indexO = listOrdini.getSelectedIndex();
                Ordine ordine = listOrdine.get(indexO);

                List<Articolo> listA = new ArrayList<Articolo>();

                listArticoliOrdinatiModel.clear();
                for (ArticoloOrdine ao : ordine.getArticoliOrdine())
                    if (ao.getMagazzino().getNome().equals(m.getMagazzino().getNome()) && !ao.isEvaso()) {
                        listArticoliOrdinatiModel.addElement(ao.getArticolo().getNome() + "    x " + ao.getQuantita());
                        listA.add(ao.getArticolo());
                    }
                listArticoliOrdinati.setModel(listArticoliOrdinatiModel);

                listArticoliDisponibiliOrdineModel.clear();

                List<ArticoloMagazzino> listAAM = new ArrayList<ArticoloMagazzino>();
                for (ArticoloMagazzino am : listAM)
                    if (listA.contains(am.getArticolo()))
                        listAAM.add(am);

                for (ArticoloOrdine ao : ordine.getArticoliOrdine())
                    for (ArticoloMagazzino am : listAAM)
                        if (am.getArticolo().getNome().equals(ao.getArticolo().getNome()) &&
                                am.getDisponibilita() >= ao.getQuantita())
                            listArticoliDisponibiliOrdineModel.addElement(am.getArticolo().getNome() + "   x "
                                    + am.getDisponibilita());

                listArticoliDisponibiliOrdine.setModel(listArticoliDisponibiliOrdineModel);

            }
        });

        evadiOrdineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {

                List<Ordine> listOrdine = new ArrayList<Ordine>();
                for (ArticoloOrdine ao : sessione.ottieniListaArticoliOrdine(m.getMagazzino()))
                    if (!listOrdine.contains(ao.getOrdine()) && ao.getOrdine().isInviato())
                        if (!ao.isEvaso())
                            listOrdine.add(ao.getOrdine());

                int indexO = listOrdini.getSelectedIndex();
                Ordine ordine = listOrdine.get(indexO);

                int limite = 0;
                for (ArticoloOrdine ao : ordine.getArticoliOrdine()) {
                    if (ao.getMagazzino().getNome().equals(m.getMagazzino().getNome()))
                        if (!ao.isDisponibile())
                            limite++;
                }
                if (limite > 0)
                    showMessageDialog(getView(), "Gli articoli dell'ordine non sono disponibili nelle quantità richieste," +
                            " impossibile evadere l'ordine");
                else {
                    try {
                        sessione.evadiOrdine(ordine, m.getMagazzino());

                        listOrdini.setSelectedIndex(-1);
                        listArticoliDisponibiliOrdineModel.clear();
                        listArticoliOrdinatiModel.clear();
                        listArticoliDisponibiliOrdine.setModel(listArticoliDisponibiliOrdineModel);
                        listArticoliOrdinati.setModel(listArticoliOrdinatiModel);
                        evadiOrdineButton.setEnabled(false);

                        List<ArticoloOrdine> listAO = ordine.getArticoliOrdine();
                        GregorianCalendar gc = new GregorianCalendar();

                        //TODO:
                        // A seconda del computer che verrà presentato verrà cambiata la radice in cui salvare la stampa
                        try {
                            ByteArrayOutputStream bytes = sessione.stampaArticoliOrdine(m.getNome() + " " + gc.get(Calendar.DATE) + gc.get(Calendar.MONTH) +
                                    gc.get(Calendar.YEAR) + gc.get(Calendar.HOUR) + gc.get(Calendar.MINUTE), listAO);

                            FileOutputStream of = new FileOutputStream("/home/costantino/pdf_progetto/" + m.getNome() + "_" +
                                    gc.get(Calendar.DATE) + "_" + gc.get(Calendar.MONTH) + "_" + gc.get(Calendar.YEAR) +
                                    "_" + gc.get(Calendar.HOUR) + "_" + gc.get(Calendar.MINUTE) + ".pdf");
                            bytes.writeTo(of);
                            of.close();

                            showMessageDialog(getView(), "Articoli evasi con successo e ricevuta stampata con successo");

                        } catch (ReportCreationFailedException e) {
                            showMessageDialog(getView(), "Errore nella stampa dell'ordine");
                        } catch (IOException e) {
                            showMessageDialog(getView(), "IOexception, impossibile stampare");
                        }

                    } catch (EvasionException e) {
                        showMessageDialog(getView(), "Impossibile evadere l'ordine, il budget del progetto insufficiente");
                    }
                }
            }
        });

        scegliImmagineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "JPG & JPEG Images", "jpg", "jpeg");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(getView());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    immagineModificaField.setText(chooser.getSelectedFile().getAbsolutePath());
                } else {
                    showMessageDialog(getView(), "Inserire un'immagine di tipo jpg o jpeg");
                }
            }
        });

        scegliImmagineButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "JPG & JPEG Images", "jpg", "jpeg");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(getView());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    immagineField.setText(chooser.getSelectedFile().getAbsolutePath());
                } else {
                    showMessageDialog(getView(), "Inserire un'immagine di tipo jpg o jpeg");
                }
            }
        });

        categoriaBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (categoriaBox.getSelectedItem().equals("Nuovo"))
                        categoriaField.setEnabled(true);
                    else {
                        categoriaField.setEnabled(false);
                    }
                } catch (NullPointerException e) {
                    //ignored
                }
            }
        });

        prodottoBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (prodottoBox.getSelectedItem().equals("Nuovo")) {
                        prodottoField.setEnabled(true);
                    } else {
                        prodottoField.setEnabled(false);
                    }
                } catch (NullPointerException e) {
                    //ignored
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

    public void paintImage(InputStream in) {
        try {
            BufferedImage buffed = ImageIO.read(in);
            ImageIcon icon = new ImageIcon(buffed);
            showMessageDialog(getView(), icon);
        } catch (IOException e) {
        }
    }
}