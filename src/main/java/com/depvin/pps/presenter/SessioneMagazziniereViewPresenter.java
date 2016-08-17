package com.depvin.pps.presenter;

import com.depvin.pps.business.ReportCreationFailedException;
import com.depvin.pps.business.SessioneMagazziniere;
import com.depvin.pps.model.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JList listArticoliOrdinati;
    private JButton stampaArticoliOrdinatiButton;
    private JList listArticoliMagazzino;
    private JButton eliminaArticoloButton;
    private JList listArticoliMagazzinoAdd;
    private JTextField nomeField;
    private JTextField descrizioneField;
    private JTextField quantitàField;
    private JTextField prezzoField;
    private JTextField categoriaField;
    private JTextField prodottoField;
    private JTextField produttoreField;
    private JTextField fornitoreField;
    private JButton aggiungiArticoloNelMagazzinoButton;
    private JButton pulisciTuttiICampiButton;
    private JButton confermaModificaButton;
    private JList listArticoliMagazzinoMod;
    private JTextField quantitàFieldMod;
    private JLabel labelquantitàDisponibile;
    private DefaultListModel getListArticoliMagazzinoModModel;
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
        tabbedPane.setVisible(true);
        listArticoliOrdinati.setVisible(true);
        listArticoliMagazzinoModel = new DefaultListModel();
        for (ArticoloMagazzino am : m.getMagazzino().getArticoliMagazzino())
            listArticoliMagazzinoModel.addElement(am.getArticolo().getNome());
        listArticoliMagazzino.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listArticoliMagazzinoAdd.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listArticoliMagazzino.setModel(listArticoliMagazzinoModel);
        listArticoliMagazzinoAdd.setModel(listArticoliMagazzinoModel);
        listArticoliMagazzino.setVisible(true);
        listArticoliMagazzinoAdd.setVisible(true);
        getListArticoliMagazzinoModModel = new DefaultListModel();
        for (ArticoloMagazzino am : m.getMagazzino().getArticoliMagazzino())
            getListArticoliMagazzinoModModel.addElement(am.getArticolo().getNome() + " " + am.getArticolo().getDescrizione()
                    + " " + am.getDisponibilita() + " " + am.getArticolo().getPrezzo());
        listArticoliMagazzinoMod.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listArticoliMagazzinoMod.setModel(listArticoliMagazzinoModel);
        listArticoliMagazzinoMod.setVisible(true);
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

        /*eliminaArticoloButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int index = listArticoliMagazzino.getSelectedIndex();
                System.out.println(index);
                ArticoloMagazzino am = m.getMagazzino().getArticoliMagazzino().get(index);
                System.out.println(am.getArticolo().getNome());
                sessione.eliminaArticoloMagazzino(am);
            }
        });//Eliminerà l'articolo solo se non sarà più disponibile in quel magazzino */

        aggiungiArticoloNelMagazzinoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (nomeField.getText().length() == 0 || descrizioneField.getText().length() == 0 ||
                        prezzoField.getText().length() == 0 || quantitàField.getText().length() == 0 ||
                        prodottoField.getText().length() == 0 || produttoreField.getText().length() == 0 ||
                        fornitoreField.getText().length() == 0)
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
                        List<Prodotto> listP = cat.getProdotti();
                        for (Prodotto p : listP)
                            if (p.getNome().equals(prodottoField))
                                listC.add(cat);
                    }
                    if (listC.contains(null))
                        listC.add(categoria);
                    Fornitore fornitore = new Fornitore(fornitoreField.getText());
                    List<Fornitore> listF = new ArrayList<Fornitore>();
                    listF.add(fornitore);
                    Produttore produttore = new Produttore(produttoreField.getText());
                    Prodotto prodotto = new Prodotto(prodottoField.getText(), listC);
                    Articolo articolo = new Articolo(nomeField.getText(), descrizioneField.getText(), budget, prodotto, produttore, listF);
                    ArticoloMagazzino amg = new ArticoloMagazzino(m.getMagazzino(), articolo, Integer.parseInt(quantitàField.getText()));
                    if (m.getMagazzino().getArticoliMagazzino().contains(amg))
                        showMessageDialog(getView(), "Impossibile aggiungere l'articolo nel magazzino. Articolo già presente");
                    else {
                        sessione.aggiungiArticoloMagazzino(articolo, Integer.parseInt(quantitàField.getText()));
                        listArticoliMagazzinoModel.removeAllElements();
                        for (ArticoloMagazzino am : m.getMagazzino().getArticoliMagazzino())
                            listArticoliMagazzinoModel.addElement(am.getArticolo().getNome());
                        listArticoliMagazzino.setModel(listArticoliMagazzinoModel);
                        listArticoliMagazzinoAdd.setModel(listArticoliMagazzinoModel);
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
            }
        });

        listArticoliMagazzinoMod.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                int index = listArticoliMagazzinoMod.getSelectedIndex();
                ArticoloMagazzino am = m.getMagazzino().getArticoliMagazzino().get(index);
                labelquantitàDisponibile.setText(String.valueOf(am.getDisponibilita()));
                labelquantitàDisponibile.setVisible(true);
            }
        });

        confermaModificaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int index = listArticoliMagazzinoMod.getSelectedIndex();
                ArticoloMagazzino am = m.getMagazzino().getArticoliMagazzino().get(index);
                if (quantitàFieldMod.getText().length() != 0) {
                    sessione.modificaQuantitàArticolo(am, Integer.parseInt(quantitàFieldMod.getText()));
                }
                labelquantitàDisponibile.setText(String.valueOf(am.getDisponibilita()));

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