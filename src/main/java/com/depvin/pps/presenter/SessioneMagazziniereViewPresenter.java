package com.depvin.pps.presenter;

import com.depvin.pps.business.SessioneMagazziniere;
import com.depvin.pps.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by costantino on 24/05/16.
 */
public class SessioneMagazziniereViewPresenter {
    SessioneMagazziniere sessione;

    private JFrame view;
    private JPanel rootPanel;
    private JList listaArticoliOrdine;
    private JList listaArticoliM;
    private JTextField nomeField;
    private JTextField descrizioneField;
    private JTextField prezzoField;
    private JTextField prodottoField;
    private JTextField produttoreField;
    private JTextField fornitoreField;
    private JTextField disponibilitàField;
    private JTextField categoriaField;
    private JButton aggiungiArticoloButton;
    private JButton rimuoviArticoloButton;
    private JButton stampaOrdineButton;
    private JButton modificaDisponibilitàButton;
    private JButton pulisciTuttiICampiButton;
    private JButton confermaModificaButton;
    private JButton aggiungiProdottoButton;
    private JButton modificaProduttoreButton;
    private JButton aggiungiFornitoreButton;
    private JButton aggiungiCategoriaButton;
    private JTabbedPane tabbedPane1;
    private JRadioButton aggiungiArticoloRadioButton;
    private JRadioButton modificaDisponibilitàRadioButton;
    private JRadioButton rimuoviArticoloRadioButton;
    private JRadioButton modificaArticoloRadioButton;
    private JComboBox fornitoreBox;
    private JComboBox produttoreBox;
    private JComboBox prodottoBox;
    private JComboBox categoriaBox;
    private DefaultListModel listModelArticoliOrdini;
    private DefaultListModel listModelArticoli;

    public SessioneMagazziniereViewPresenter(final SessioneMagazziniere sessione) {
        this.sessione = sessione;
        final Magazziniere m = sessione.getUtente();
        view = new JFrame("Sessione: " + m.getNome() + " " + m.getCognome());
        rootPanel.setPreferredSize(new Dimension(1000, 750));
        view.setLocation(200, 200);
        view.setContentPane(rootPanel);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(modificaDisponibilitàRadioButton);
        btnGroup.add(aggiungiArticoloRadioButton);
        btnGroup.add(rimuoviArticoloRadioButton);
        btnGroup.add(modificaArticoloRadioButton);
        pulisciTuttiICampiButton.setEnabled(false);
        modificaDisponibilitàButton.setEnabled(false);
        aggiungiArticoloButton.setEnabled(false);
        rimuoviArticoloButton.setEnabled(false);
        stampaOrdineButton.setEnabled(false);
        confermaModificaButton.setEnabled(false);
        aggiungiProdottoButton.setEnabled(false);
        aggiungiFornitoreButton.setEnabled(false);
        modificaProduttoreButton.setEnabled(false);
        aggiungiCategoriaButton.setEnabled(false);

        listModelArticoli = new DefaultListModel();
        final List<ArticoloMagazzino> articoloMagazzinos = m.getMagazzino().getArticoliMagazzino();
        for (ArticoloMagazzino am : articoloMagazzinos) {
            listModelArticoli.addElement(am.getArticolo().getNome());
        }
        listaArticoliM.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listaArticoliM.setModel(listModelArticoli);
        listaArticoliM.setVisible(true);

        listModelArticoliOrdini = new DefaultListModel();
        for (ArticoloMagazzino ams : articoloMagazzinos) {
            //ams.getArticolo()
        }
        //TODO: inserire gli articoli dell'ordine nella corrispondente lista

        tabbedPane1.setVisible(true);
        List<Categoria> listcat = sessione.ottieniListaCategorie();
        for (Categoria cat : listcat)
            categoriaBox.addItem(cat.getNome());

        view.pack();

        categoriaBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                int index = categoriaBox.getSelectedIndex();
                Categoria cat = sessione.ottieniListaCategorie().get(index);
                List<Prodotto> listprod = cat.getProdotti();
                for (Prodotto prod : listprod)
                    prodottoBox.addItem(prod.getNome());
            }
        });

        stampaOrdineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                //TODO: collegarlo ai relativi articoli dell'ordine
            }
        });

        pulisciTuttiICampiButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                nomeField.removeAll();
                descrizioneField.removeAll();
                prezzoField.removeAll();
                prodottoField.removeAll();
                produttoreField.removeAll();
                fornitoreField.removeAll();
                disponibilitàField.removeAll();
                categoriaField.removeAll();
            }
        });

        aggiungiCategoriaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (categoriaField.getText().length() == 0)
                    showMessageDialog(getView(), "Il campo categoria non può rimanere vuoto");
                else {
                    Categoria cat = new Categoria(categoriaField.getText());
                    sessione.ottieniListaCategorie().add(cat);
                    showMessageDialog(getView(), "Categoria aggiunta con successo");
                }
            }
        });

        aggiungiProdottoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (prodottoField.getText().length() == 0)
                    showMessageDialog(getView(), "Il campo prodotto non può rimanere vuoto");
                else {
                    int index = categoriaBox.getSelectedIndex();
                    Categoria cat = sessione.ottieniListaCategorie().get(index);
                    List<Prodotto> listprod = cat.getProdotti();
                    List<Categoria> listcat = new ArrayList<Categoria>();
                    listcat.add(cat);
                    Prodotto prod = new Prodotto(prodottoField.getText(), listcat);
                    listprod.add(prod);
                    showMessageDialog(getView(), "Prodotto aggiunto con successo");
                }
            }
        });

        modificaProduttoreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (produttoreField.getText().length() == 0)
                    showMessageDialog(getView(), "Il campo produttore non può rimanere vuoto");
                else {
                    Produttore produ = new Produttore(produttoreField.getText());
                    int index = listaArticoliM.getSelectedIndex();
                    ArticoloMagazzino am = m.getMagazzino().getArticoliMagazzino().get(index);
                    am.getArticolo().setProduttore(produ);
                    showMessageDialog(getView(), "Produttore modificato con successo");
                }
            }
        });

        aggiungiFornitoreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (fornitoreField.getText().length() == 0)
                    showMessageDialog(getView(), "Il campo fornitore non può rimanere vuoto");
                else {
                    Fornitore fo = new Fornitore(fornitoreField.getText());
                    int index = listaArticoliM.getSelectedIndex();
                    ArticoloMagazzino am = m.getMagazzino().getArticoliMagazzino().get(index);
                    List<Fornitore> listfo = am.getArticolo().getFornitori();
                    listfo.add(fo);
                    showMessageDialog(getView(), "Fornitore aggiunto con successo");
                }
            }
        });

        aggiungiArticoloRadioButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                pulisciTuttiICampiButton.setEnabled(true);
                modificaDisponibilitàButton.setEnabled(false);
                aggiungiArticoloButton.setEnabled(true);
                rimuoviArticoloButton.setEnabled(false);
                stampaOrdineButton.setEnabled(false);
                confermaModificaButton.setEnabled(false);
                aggiungiProdottoButton.setEnabled(true);
                modificaProduttoreButton.setEnabled(true);
                aggiungiFornitoreButton.setEnabled(true);
                aggiungiCategoriaButton.setEnabled(true);

                nomeField.setVisible(true);
                descrizioneField.setVisible(true);
                prezzoField.setVisible(true);
                prodottoField.setVisible(true);
                produttoreField.setVisible(true);
                fornitoreField.setVisible(true);
                disponibilitàField.setVisible(true);
                categoriaBox.setVisible(true);
                fornitoreBox.setVisible(true);
                prodottoBox.setVisible(true);
                produttoreBox.setVisible(true);
            }
        });

        aggiungiArticoloButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (nomeField.getText().length() == 0 || descrizioneField.getText().length() == 0 ||
                        prezzoField.getText().length() == 0 || prodottoBox.getSelectedIndex() == -1 ||
                        produttoreBox.getSelectedIndex() == -1 || fornitoreBox.getSelectedIndex() == -1 ||
                        disponibilitàField.getText().length() == 0)
                    showMessageDialog(getView(), "I campi \"nome prodotto\", \"descrizione\",\"prezzo\", \"prodotto\"," +
                            " \"produttori\", \"fornitori\" e \"disponibilità\" non possono essere lasciati vuoti");
                else {
                    String prezzo = prezzoField.getText();
                    prezzo = prezzo.replaceAll(",", ".");
                    if (!prezzo.contains("."))
                        prezzo = prezzo + ".00";
                    int indexcat = categoriaBox.getSelectedIndex();
                    int indexprod = prodottoBox.getSelectedIndex();
                    Prodotto prod = sessione.ottieniListaCategorie().get(indexcat).getProdotti().get(indexprod);
                    Produttore produttore = new Produttore(prodottoField.getText());
                    Fornitore fo = new Fornitore(fornitoreField.getText());
                    List<Fornitore> lifo = new ArrayList<Fornitore>();
                    lifo.add(fo);
                    Articolo a = new Articolo(nomeField.getText(), descrizioneField.getText(), Float.parseFloat(prezzo),
                            prod, produttore, lifo);
                    sessione.aggiungiArticoloMagazzino(a, Integer.parseInt(disponibilitàField.getText()));
                    showMessageDialog(getView(), "Articolo aggiunto con successo");
                }
            }
        });

        rimuoviArticoloRadioButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                pulisciTuttiICampiButton.setEnabled(false);
                modificaDisponibilitàButton.setEnabled(false);
                aggiungiArticoloButton.setEnabled(false);
                rimuoviArticoloButton.setEnabled(true);
                stampaOrdineButton.setEnabled(false);
                confermaModificaButton.setEnabled(false);
                aggiungiProdottoButton.setEnabled(false);
                modificaProduttoreButton.setEnabled(false);
                aggiungiFornitoreButton.setEnabled(false);
                aggiungiCategoriaButton.setEnabled(false);

                nomeField.setVisible(false);
                descrizioneField.setVisible(false);
                prezzoField.setVisible(false);
                prodottoField.setVisible(false);
                produttoreField.setVisible(false);
                fornitoreField.setVisible(false);
                disponibilitàField.setVisible(false);
                categoriaBox.setVisible(false);
                fornitoreBox.setVisible(false);
                prodottoBox.setVisible(false);
                produttoreBox.setVisible(false);
            }
        });

        rimuoviArticoloButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int index = listaArticoliM.getSelectedIndex();
                ArticoloMagazzino am = m.getMagazzino().getArticoliMagazzino().get(index);
                sessione.eliminaArticoloMagazzino(am);
                showMessageDialog(getView(), "Articolo rimosso con successo");
            }
        });

        modificaDisponibilitàRadioButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                pulisciTuttiICampiButton.setEnabled(true);
                modificaDisponibilitàButton.setEnabled(true);
                aggiungiArticoloButton.setEnabled(false);
                rimuoviArticoloButton.setEnabled(false);
                stampaOrdineButton.setEnabled(false);
                confermaModificaButton.setEnabled(false);
                aggiungiProdottoButton.setEnabled(false);
                modificaProduttoreButton.setEnabled(false);
                aggiungiFornitoreButton.setEnabled(false);
                aggiungiCategoriaButton.setEnabled(false);

                nomeField.setVisible(false);
                descrizioneField.setVisible(false);
                prezzoField.setVisible(false);
                prodottoField.setVisible(false);
                produttoreField.setVisible(false);
                fornitoreField.setVisible(false);
                disponibilitàField.setVisible(true);
                categoriaBox.setVisible(false);
                fornitoreBox.setVisible(false);
                prodottoBox.setVisible(false);
                produttoreBox.setVisible(false);
            }
        });

        modificaDisponibilitàButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (disponibilitàField.getText().length() == 0 && listaArticoliM.getSelectedIndex() == 0)
                    showMessageDialog(getView(), "Il campo \"disponibilità\" non può essere lasciato vuoto e bisogna" +
                            " aver dovuto selezionare il prodotto desiderato");
                else {
                    int index = listaArticoliM.getSelectedIndex();
                    ArticoloMagazzino am = m.getMagazzino().getArticoliMagazzino().get(index);
                    sessione.modificaQuantitàArticolo(am, Integer.parseInt(disponibilitàField.getText()));
                    showMessageDialog(getView(), "Modifica avvenuta con successo");
                }
            }
        });

        modificaArticoloRadioButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                pulisciTuttiICampiButton.setEnabled(true);
                modificaDisponibilitàButton.setEnabled(false);
                aggiungiArticoloButton.setEnabled(false);
                rimuoviArticoloButton.setEnabled(false);
                stampaOrdineButton.setEnabled(false);
                confermaModificaButton.setEnabled(true);
                aggiungiProdottoButton.setEnabled(true);
                modificaProduttoreButton.setEnabled(true);
                aggiungiFornitoreButton.setEnabled(true);
                aggiungiCategoriaButton.setEnabled(true);

                nomeField.setVisible(true);
                descrizioneField.setVisible(true);
                prezzoField.setVisible(true);
                prodottoField.setVisible(true);
                produttoreField.setVisible(true);
                fornitoreField.setVisible(true);
                disponibilitàField.setVisible(true);
                categoriaBox.setVisible(true);
                fornitoreBox.setVisible(true);
                prodottoBox.setVisible(true);
                produttoreBox.setVisible(true);
            }
        });

        confermaModificaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (nomeField.getText().length() == 0 && descrizioneField.getText().length() == 0 &&
                        prezzoField.getText().length() == 0)
                    showMessageDialog(getView(), "I campi \"nome prodotto\", \"descrizione\" e \"prezzo\"" +
                            " non possono essere lasciati contemporaneamente vuoti");
                else {
                    int index = listaArticoliM.getSelectedIndex();
                    Articolo a = m.getMagazzino().getArticoliMagazzino().get(index).getArticolo();
                    if (nomeField.getText().length() != 0)
                        a.setNome(nomeField.getText());
                    if (descrizioneField.getText().length() != 0)
                        a.setDescrizione(descrizioneField.getText());
                    if (prezzoField.getText().length() != 0) {
                        String prezzo = prezzoField.getText();
                        prezzo = prezzo.replaceAll(",", ".");
                        if (!prezzo.contains("."))
                            prezzo = prezzo + ".00";
                        a.setPrezzo(Float.parseFloat(prezzo));
                    }
                    showMessageDialog(getView(), "Modifica avvenuta con successo");
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