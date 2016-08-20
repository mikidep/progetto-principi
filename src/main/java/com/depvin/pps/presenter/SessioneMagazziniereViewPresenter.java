package com.depvin.pps.presenter;

import com.depvin.pps.business.ReportCreationFailedException;
import com.depvin.pps.business.SessioneMagazziniere;
import com.depvin.pps.model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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

    private JButton aggiungiArticoloNelMagazzinoButton;
    private JButton pulisciTuttiICampiButton;
    private JButton confermaModificaButton;
    private JButton ottieniInformazioniButton;
    private JButton confermaModificheButton;
    private JButton stampaArticoliOrdinatiButton;
    private JTextField immagineModificaField;
    private JTextField immagineField;
    private JButton pulisciTuttiICampiButton1;
    private JButton modificaFornitoreButton;
    private JTextField vecchioFornitoreField;
    private JButton ottieniImmagineButton;
    private JTextField categoriaModificaField;
    private JButton modificaCategoriaButton;


    private DefaultListModel listArticoliOrdinatiModel;
    private DefaultListModel listArticoliMagazzinoModel;

    public SessioneMagazziniereViewPresenter(final SessioneMagazziniere sessione) {

        this.sessione = sessione;
        final Magazziniere m = sessione.getUtente();
        view = new JFrame("Sessione: " + m.getNome() + " " + m.getCognome());
        rootPanel.setPreferredSize(new Dimension(1000, 750));
        view.setLocation(200, 200);
        view.setContentPane(rootPanel);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        listArticoliOrdinatiModel = new DefaultListModel();
        for (ArticoloOrdine ao : sessione.ottieniListaArticoliOrdine(m.getMagazzino()))
            listArticoliOrdinatiModel.addElement(ao.getArticolo().getNome());
        listArticoliOrdinati.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listArticoliOrdinati.setModel(listArticoliOrdinatiModel);
        listArticoliOrdinati.setVisible(true);
        listArticoliMagazzinoModel = new DefaultListModel();
        for (ArticoloMagazzino am : m.getMagazzino().getArticoliMagazzino())
            listArticoliMagazzinoModel.addElement(am.getArticolo().getNome());
        listAggiungiArticoloMagazzino.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listModificaDisponibilitàArticoloMagazzino.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listModificaArticoloMagazzino.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listAggiungiArticoloMagazzino.setModel(listArticoliMagazzinoModel);
        listModificaArticoloMagazzino.setModel(listArticoliMagazzinoModel);
        listModificaDisponibilitàArticoloMagazzino.setModel(listArticoliMagazzinoModel);
        listAggiungiArticoloMagazzino.setVisible(true);
        listModificaArticoloMagazzino.setVisible(true);
        listModificaDisponibilitàArticoloMagazzino.setVisible(true);
        tabbedPane.setVisible(true);
        view.pack();

        stampaArticoliOrdinatiButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                GregorianCalendar gc = new GregorianCalendar();
                try {
                    sessione.stampaArticoliOrdine(m.getNome() + " " + gc.get(Calendar.DATE) + gc.get(Calendar.MONTH) +
                                    gc.get(Calendar.YEAR) + gc.get(Calendar.HOUR) + gc.get(Calendar.MINUTE),
                            sessione.ottieniListaArticoliOrdine(m.getMagazzino()));
                } catch (ReportCreationFailedException e) {
                    showMessageDialog(getView(), "Errore nella stampa dell'ordine");
                }
            }
        });

        aggiungiArticoloNelMagazzinoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (nomeField.getText().length() == 0 || descrizioneField.getText().length() == 0 ||
                        prezzoField.getText().length() == 0 || quantitàField.getText().length() == 0 ||
                        prodottoField.getText().length() == 0 || produttoreField.getText().length() == 0 ||
                        fornitoreField.getText().length() == 0 || immagineField.getText().length() == 0)
                    showMessageDialog(getView(), "Riempire tutti i campi");
                else {
                    String newBudget = prezzoField.getText();
                    newBudget = newBudget.replaceAll(",", ".");
                    if (!newBudget.contains("."))
                        newBudget = newBudget + ".00";
                    float budget = Float.parseFloat(newBudget);
                    Categoria categoria = new Categoria(categoriaField.getText());
                    List<Categoria> listC = new ArrayList<Categoria>();
                    List<Categoria> listCAT = sessione.ottieniListaCategoria();
                    for (Categoria cat : listCAT) {
                        if (listC.isEmpty())
                            if (cat.getNome().equals(categoria.getNome()))
                                listC.add(cat);
                    }
                    if (listC.isEmpty())
                        listC.add(categoria);
                    Fornitore fornitore = new Fornitore(fornitoreField.getText());
                    List<Fornitore> listF = new ArrayList<Fornitore>();
                    listF.add(fornitore);
                    Produttore produttore = new Produttore(produttoreField.getText());
                    Prodotto prodotto = new Prodotto(prodottoField.getText(), listC);
                    Articolo articolo = new Articolo(nomeField.getText(), descrizioneField.getText(),
                            budget, prodotto, produttore, listF);
                    try {
                        BufferedImage img = ImageIO.read(new File("/home/costantino/Scaricati/Progetto_Softwar_Immagini/" +
                                immagineField.getText()));
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(img, "jpg", baos);
                        baos.flush();
                        byte[] imageInByte = baos.toByteArray();
                        sessione.aggiungiImmagineArticolo(articolo, imageInByte);
                        baos.close();
                    } catch (IOException e) {
                    }
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
                        listArticoliMagazzinoModel.removeAllElements();
                        for (ArticoloMagazzino am : m.getMagazzino().getArticoliMagazzino())
                            listArticoliMagazzinoModel.addElement(am.getArticolo().getNome());
                        listAggiungiArticoloMagazzino.setModel(listArticoliMagazzinoModel);
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

        listModificaDisponibilitàArticoloMagazzino.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                int index = listModificaDisponibilitàArticoloMagazzino.getSelectedIndex();
                ArticoloMagazzino am = m.getMagazzino().getArticoliMagazzino().get(index);
                labelquantitàDisponibile.setText(String.valueOf(am.getDisponibilita()));
                labelquantitàDisponibile.setVisible(true);
            }
        });

        confermaModificaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int index = listModificaDisponibilitàArticoloMagazzino.getSelectedIndex();
                ArticoloMagazzino am = m.getMagazzino().getArticoliMagazzino().get(index);
                if (quantitàFieldMod.getText().length() != 0) {
                    sessione.modificaQuantitàArticolo(am, Integer.parseInt(quantitàFieldMod.getText()));
                }
                labelquantitàDisponibile.setText(String.valueOf(am.getDisponibilita()));
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
            }
        });

        confermaModificheButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int index = listModificaArticoloMagazzino.getSelectedIndex();
                ArticoloMagazzino am = m.getMagazzino().getArticoliMagazzino().get(index);
                if (categoriaAggiungiField.getText().length() != 0)
                    sessione.aggiungiCategoriaArticolo(am, categoriaAggiungiField.getText());
                if (prodottoModificaField.getText().length() != 0 && categoriaAggiungiField.getText().length() != 0)
                    sessione.modificaProdottoCategoriaArticolo(am, prodottoModificaField.getText(),
                            new Categoria(categoriaAggiungiField.getText()));
                if (produttoreModificaField.getText().length() != 0)
                    sessione.modificaProduttoreArticolo(am, produttoreModificaField.getText());
                if (nomeModificaField.getText().length() != 0)
                    sessione.modificaNomeArticolo(am, nomeModificaField.getText());
                if (prezzoModificaField.getText().length() != 0)
                    sessione.modificaPrezzoArticolo(am, Float.parseFloat(prezzoModificaField.getText()));
                if (fornitoreModificaField.getText().length() != 0)
                    sessione.aggiungiFornitoreArticolo(am, fornitoreModificaField.getText());
                if (descrizioneModificaField.getText().length() != 0)
                    sessione.modificaDescrizioneArticolo(am, descrizioneModificaField.getText());
                if (immagineModificaField.getText().length() != 0) {
                    try {
                        BufferedImage img = ImageIO.read(new File("/home/costantino/Scaricati/Progetto_Softwar_Immagini/" +
                                immagineModificaField.getText()));
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(img, "jpg", baos);
                        baos.flush();
                        byte[] imageInByte = baos.toByteArray();
                        baos.close();
                        sessione.modificaImmagineArticolo(am, imageInByte);
                    } catch (IOException e) {
                    }
                }
            }
        });

        modificaFornitoreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int index = listModificaArticoloMagazzino.getSelectedIndex();
                ArticoloMagazzino am = m.getMagazzino().getArticoliMagazzino().get(index);
                sessione.modificaFornitoreArticolo(am, fornitoreModificaField.getText(), vecchioFornitoreField.getText());
            }
        });

        modificaCategoriaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int index = listModificaArticoloMagazzino.getSelectedIndex();
                ArticoloMagazzino am = m.getMagazzino().getArticoliMagazzino().get(index);
                sessione.modificaCategoriaArticolo(am, categoriaModificaField.getText());
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