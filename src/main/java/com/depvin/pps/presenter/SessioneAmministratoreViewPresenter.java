package com.depvin.pps.presenter;

import com.depvin.pps.business.*;
import com.depvin.pps.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by costantino on 24/05/16.
 */
public class SessioneAmministratoreViewPresenter {
    SessioneAmministratore sessione;
    private JFrame view;
    private JPanel rootPanel;

    private JButton buttonAdmin;
    private JButton buttonDip;
    private JButton buttonMagazziniereVecchiaS;
    private JButton buttonMagazziniereNuovaS;
    private JButton buttonCapo;
    private JButton buttonProg;

    private JTextField nomeUtenteField;
    private JTextField cognomeUtenteField;
    private JTextField usernameUtenteField;
    private JTextField passwordUtenteField;
    private JTextField nomeProgettoField;
    private JTextField budgetField;
    private JTextField nomeMagazzinoField;
    private JTextField nomeSedeField;
    private JTextField nomeNazioneField;
    private JTextField prezzoSpedizioneField;
    private JTextField nuovoMagazzinoNomeField;

    private JRadioButton creaDipendenteRadioButton;
    private JRadioButton creaAmministratoreRadioButton;
    private JRadioButton creaMagazziniereRadioButton;
    private JRadioButton creaProgettoRadioButton;
    private JRadioButton creaCapoProgettoRadioButton;
    private JRadioButton aggiungiMagazziniereRadioButton;

    private JComboBox progettoBox;
    private JComboBox sedeBox;
    private JComboBox capoProgettoBox;

    public SessioneAmministratoreViewPresenter(final SessioneAmministratore sessione) {

        this.sessione = sessione;
        final Amministratore a = sessione.getUtente();
        view = new JFrame("Sessione: " + a.getNome() + " " + a.getCognome());
        rootPanel.setPreferredSize(new Dimension(1400, 800));
        view.setLocation(0, 0);
        view.setContentPane(rootPanel);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(creaAmministratoreRadioButton);
        btnGroup.add(creaDipendenteRadioButton);
        btnGroup.add(creaMagazziniereRadioButton);
        btnGroup.add(creaCapoProgettoRadioButton);
        btnGroup.add(creaProgettoRadioButton);
        btnGroup.add(aggiungiMagazziniereRadioButton);

        buttonAdmin.setEnabled(false);
        buttonCapo.setEnabled(false);
        buttonDip.setEnabled(false);
        buttonProg.setEnabled(false);
        buttonMagazziniereNuovaS.setEnabled(false);
        buttonMagazziniereVecchiaS.setEnabled(false);
        view.pack();

        creaDipendenteRadioButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {

                usernameUtenteField.removeAll();
                passwordUtenteField.removeAll();
                cognomeUtenteField.removeAll();
                nomeUtenteField.removeAll();
                capoProgettoBox.removeAllItems();

                List<CapoProgetto> listCapoProgetto = sessione.ottieniListaCapoProgetto();
                for (CapoProgetto cp : listCapoProgetto)
                    capoProgettoBox.addItem(cp.getUsername());

                progettoBox.removeAllItems();
                progettoBox.setEnabled(true);
                progettoBox.setVisible(true);
                int index = capoProgettoBox.getSelectedIndex();
                List<Progetto> list = sessione.ottieniListaCapoProgetto().get(index).getProgetti();
                for (Progetto prog : list)
                    progettoBox.addItem(prog.getNome());

                usernameUtenteField.setVisible(true);
                passwordUtenteField.setVisible(true);
                cognomeUtenteField.setVisible(true);
                capoProgettoBox.setVisible(true);
                nomeUtenteField.setVisible(true);
                buttonDip.setEnabled(true);

                nuovoMagazzinoNomeField.setVisible(false);
                nomeMagazzinoField.setVisible(false);
                nomeProgettoField.setVisible(false);
                budgetField.setVisible(false);
                sedeBox.setVisible(false);
                nomeSedeField.setVisible(false);
                nomeNazioneField.setVisible(false);
                prezzoSpedizioneField.setVisible(false);
                buttonAdmin.setEnabled(false);
                buttonCapo.setEnabled(false);
                buttonMagazziniereVecchiaS.setEnabled(false);
                buttonProg.setEnabled(false);
                buttonMagazziniereNuovaS.setEnabled(false);
            }
        });

        capoProgettoBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                progettoBox.removeAllItems();
                progettoBox.setEnabled(true);
                progettoBox.setVisible(true);
                int index = capoProgettoBox.getSelectedIndex();
                List<Progetto> list = sessione.ottieniListaCapoProgetto().get(index).getProgetti();
                for (Progetto prog : list)
                    progettoBox.addItem(prog.getNome());
            }
        });

        /*capoProgettoBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                    progettoBox.removeAllItems();
                    progettoBox.setEnabled(true);
                    progettoBox.setVisible(true);
                    int index = capoProgettoBox.getSelectedIndex();
                    List<Progetto> list = sessione.ottieniListaCapoProgetto().get(index).getProgetti();
                    for (Progetto prog : list)
                        progettoBox.addItem(prog.getNome());
            }
        });*/

        creaAmministratoreRadioButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {

                usernameUtenteField.removeAll();
                passwordUtenteField.removeAll();
                cognomeUtenteField.removeAll();
                nomeUtenteField.removeAll();

                usernameUtenteField.setVisible(true);
                passwordUtenteField.setVisible(true);
                cognomeUtenteField.setVisible(true);
                nomeUtenteField.setVisible(true);
                buttonAdmin.setEnabled(true);

                nuovoMagazzinoNomeField.setVisible(false);
                progettoBox.setVisible(false);
                progettoBox.setEnabled(false);
                capoProgettoBox.setVisible(false);
                sedeBox.setVisible(true); //Viene posto prima true e poi false perchè se no non verrebbe aggiornata
                sedeBox.setVisible(false);//l'interfaccia grafica se la prima scelta è stata di creare l'amministratore
                buttonCapo.setEnabled(false);
                buttonDip.setEnabled(false);
                buttonMagazziniereVecchiaS.setEnabled(false);
                buttonProg.setEnabled(false);
                buttonMagazziniereNuovaS.setEnabled(false);
                nomeMagazzinoField.setVisible(false);
                nomeProgettoField.setVisible(false);
                budgetField.setVisible(false);
                nomeSedeField.setVisible(false);
                nomeNazioneField.setVisible(false);
                prezzoSpedizioneField.setVisible(false);
            }
        });

        creaCapoProgettoRadioButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {

                passwordUtenteField.removeAll();
                usernameUtenteField.removeAll();
                cognomeUtenteField.removeAll();
                nomeUtenteField.removeAll();

                passwordUtenteField.setVisible(true);
                usernameUtenteField.setVisible(true);
                cognomeUtenteField.setVisible(true);
                nomeUtenteField.setVisible(true);
                buttonCapo.setEnabled(true);

                nuovoMagazzinoNomeField.setVisible(false);
                capoProgettoBox.setVisible(false);
                sedeBox.setVisible(true);
                progettoBox.setVisible(false);
                progettoBox.setEnabled(false);
                sedeBox.setVisible(false);
                buttonAdmin.setEnabled(false);
                buttonDip.setEnabled(false);
                buttonMagazziniereVecchiaS.setEnabled(false);
                buttonProg.setEnabled(false);
                buttonMagazziniereNuovaS.setEnabled(false);
                nomeMagazzinoField.setVisible(false);
                nomeProgettoField.setVisible(false);
                budgetField.setVisible(false);
                nomeSedeField.setVisible(false);
                nomeNazioneField.setVisible(false);
                prezzoSpedizioneField.setVisible(false);
            }
        });

        creaMagazziniereRadioButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {

                nomeMagazzinoField.removeAll();
                usernameUtenteField.removeAll();
                passwordUtenteField.removeAll();
                cognomeUtenteField.removeAll();
                nomeUtenteField.removeAll();
                sedeBox.removeAllItems();
                List<Sede> list = sessione.ottieniListaSede();
                for (Sede sede : list)
                    sedeBox.addItem(sede.getNome());

                nomeMagazzinoField.setVisible(true);
                usernameUtenteField.setVisible(true);
                passwordUtenteField.setVisible(true);
                cognomeUtenteField.setVisible(true);
                nomeUtenteField.setVisible(true);
                sedeBox.setVisible(true);
                buttonMagazziniereVecchiaS.setEnabled(true);

                nuovoMagazzinoNomeField.setVisible(false);
                nomeProgettoField.setVisible(false);
                progettoBox.setVisible(false);
                progettoBox.setEnabled(false);
                capoProgettoBox.setVisible(false);
                budgetField.setVisible(false);
                buttonAdmin.setEnabled(false);
                buttonCapo.setEnabled(false);
                buttonDip.setEnabled(false);
                buttonProg.setEnabled(false);
                buttonMagazziniereNuovaS.setEnabled(false);
                nomeSedeField.setVisible(false);
                nomeNazioneField.setVisible(false);
                prezzoSpedizioneField.setVisible(false);
            }
        });

        creaProgettoRadioButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {

                nomeProgettoField.removeAll();
                budgetField.removeAll();
                capoProgettoBox.removeAllItems();
                sedeBox.removeAllItems();
                List<CapoProgetto> listProgetto = sessione.ottieniListaCapoProgetto();
                for (CapoProgetto cp : listProgetto)
                    capoProgettoBox.addItem(cp.getUsername());
                List<Sede> listSede = sessione.ottieniListaSede();
                for (Sede sede : listSede)
                    sedeBox.addItem(sede.getNome());

                nomeProgettoField.setVisible(true);
                budgetField.setVisible(true);
                capoProgettoBox.setVisible(true);
                sedeBox.setVisible(true);
                buttonProg.setEnabled(true);

                nuovoMagazzinoNomeField.setVisible(false);
                progettoBox.setVisible(false);
                progettoBox.setEnabled(false);
                nomeUtenteField.setVisible(false);
                cognomeUtenteField.setVisible(false);
                usernameUtenteField.setVisible(false);
                passwordUtenteField.setVisible(false);
                nomeMagazzinoField.setVisible(false);
                buttonAdmin.setEnabled(false);
                buttonCapo.setEnabled(false);
                buttonDip.setEnabled(false);
                buttonMagazziniereVecchiaS.setEnabled(false);
                buttonMagazziniereNuovaS.setEnabled(false);
                nomeSedeField.setVisible(false);
                nomeNazioneField.setVisible(false);
                prezzoSpedizioneField.setVisible(false);
            }
        });

        aggiungiMagazziniereRadioButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {

                usernameUtenteField.removeAll();
                passwordUtenteField.removeAll();
                cognomeUtenteField.removeAll();
                nomeUtenteField.removeAll();
                prezzoSpedizioneField.removeAll();
                nomeNazioneField.removeAll();
                nomeSedeField.removeAll();
                nuovoMagazzinoNomeField.removeAll();

                nomeSedeField.setVisible(true);
                nomeNazioneField.setVisible(true);
                prezzoSpedizioneField.setVisible(true);
                usernameUtenteField.setVisible(true);
                passwordUtenteField.setVisible(true);
                cognomeUtenteField.setVisible(true);
                nomeUtenteField.setVisible(true);
                nuovoMagazzinoNomeField.setVisible(true);
                buttonMagazziniereNuovaS.setEnabled(true);

                sedeBox.setVisible(true);
                sedeBox.setVisible(false);
                buttonMagazziniereVecchiaS.setEnabled(false);
                nomeProgettoField.setVisible(false);
                progettoBox.setVisible(false);
                progettoBox.setEnabled(false);
                capoProgettoBox.setVisible(false);
                budgetField.setVisible(false);
                buttonAdmin.setEnabled(false);
                buttonCapo.setEnabled(false);
                buttonDip.setEnabled(false);
                buttonProg.setEnabled(false);
                nomeMagazzinoField.setVisible(false);
            }
        });

        buttonAdmin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (nomeUtenteField.getText().length() == 0 || cognomeUtenteField.getText().length() == 0 ||
                        usernameUtenteField.getText().length() == 0 || passwordUtenteField.getText().length() == 0)
                    showMessageDialog(getView(), "I campi \"Nome\", \"Cognome\", \"Username\" e" +
                            " \"Password\" non possono essere lasciati vuoti");
                else {
                    try {
                        sessione.aggiungiAmministratore(nomeUtenteField.getText(), cognomeUtenteField.getText(), usernameUtenteField.getText(), passwordUtenteField.getText());
                    } catch (UserExistsException e) {
                        showMessageDialog(getView(), "Utente già esistente");
                    } catch (UserLoadingException e) {
                        showMessageDialog(getView(), "Errore nel caricamento");
                    }
                    showMessageDialog(getView(), "Amministratore aggiunto con successo");
                }
            }
        });

        buttonDip.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (nomeUtenteField.getText().length() == 0 || cognomeUtenteField.getText().length() == 0 ||
                        usernameUtenteField.getText().length() == 0 || passwordUtenteField.getText().length() == 0 ||
                        progettoBox.getSelectedItem().equals(null) || capoProgettoBox.getSelectedItem().equals(null))
                    showMessageDialog(getView(), "I campi \"Nome\", \"Cognome\", \"Username\", \"Password\", " +
                            "\"Scegliere il Capo Progetto\" e \"Scegliere il Progetto\"non possono essere lasciati vuoti");
                else {
                    try {
                        int indexCapoProgetto = 0;
                        List<CapoProgetto> listCP = sessione.ottieniListaCapoProgetto();
                        for (CapoProgetto cp : listCP)
                            if ((cp.getUsername()).equals(capoProgettoBox.getSelectedItem()))
                                indexCapoProgetto = listCP.indexOf(cp);
                        int indexCapoProgetto2 = capoProgettoBox.getSelectedIndex();
                        int indexProgetto = progettoBox.getSelectedIndex();
                        List<Progetto> listP = sessione.ottieniListaCapoProgetto().get(indexCapoProgetto2).getProgetti();
                        for (Progetto p : listP)
                            if (p.getNome().equals(progettoBox.getSelectedItem()))
                                indexProgetto = listP.indexOf(p);
                        sessione.aggiungiDipendente(nomeUtenteField.getText(), cognomeUtenteField.getText(),
                                usernameUtenteField.getText(), passwordUtenteField.getText());
                        listCP.get(indexCapoProgetto).getProgetti().get(indexProgetto).getDipendenti().
                                add((Dipendente) sessione.ottieniUtente(usernameUtenteField.getText(), passwordUtenteField.getText()));
                    } catch (UserExistsException e) {
                        showMessageDialog(getView(), "Utente già esistente");
                    } catch (UserLoadingException e) {
                        showMessageDialog(getView(), "Errore nel caricamento");
                    } catch (UserNotFoundException e) {
                        showMessageDialog(getView(), "Utente non trovato");
                    }
                    showMessageDialog(getView(), "Dipendente aggiunto con successo");
                }
            }
        });

        buttonMagazziniereVecchiaS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (nomeUtenteField.getText().length() == 0 || cognomeUtenteField.getText().length() == 0 ||
                        usernameUtenteField.getText().length() == 0 || passwordUtenteField.getText().length() == 0 ||
                        nomeMagazzinoField.getText().length() == 0 || sedeBox.getSelectedItem().equals(null))
                    showMessageDialog(getView(), "I campi \"Nome\", \"Cognome\", \"Username\", \"Password\"," +
                            " \"Scegliere la Sede\" e \"Magazzino\" non possono essere lasciati vuoti");
                else {
                    try {
                        int index = 0;
                        List<Sede> lists = sessione.ottieniListaSede();
                        for (Sede s : lists)
                            if ((s.getNome()).equals(sedeBox.getSelectedItem()))
                                index = lists.indexOf(s);
                        sessione.aggiungiMagazziniere(nomeUtenteField.getText(), cognomeUtenteField.getText(), usernameUtenteField.getText(), passwordUtenteField.getText(), lists.get(index), nomeMagazzinoField.getText());
                    } catch (UserExistsException e) {
                        showMessageDialog(getView(), "Utente già esistente");
                    } catch (UserLoadingException e) {
                        showMessageDialog(getView(), "Errore nel caricamento");
                    }
                    showMessageDialog(getView(), "Magazziniere aggiunto con successo");
                }
            }
        });

        buttonCapo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (nomeUtenteField.getText().length() == 0 || cognomeUtenteField.getText().length() == 0 ||
                        usernameUtenteField.getText().length() == 0 || passwordUtenteField.getText().length() == 0)
                    showMessageDialog(getView(), "I campi \"Nome\", \"Cognome\", \"Username\" e \"Password\" " +
                            "non possono essere lasciati vuoti");
                else {
                    try {
                        sessione.aggiungiCapoProgetto(nomeUtenteField.getText(), cognomeUtenteField.getText(), usernameUtenteField.getText(), passwordUtenteField.getText());
                    } catch (UserExistsException e) {
                        showMessageDialog(getView(), "Utente già esistente");
                    } catch (UserLoadingException e) {
                        showMessageDialog(getView(), "Errore nel caricamento");
                    }
                    showMessageDialog(getView(), "Capo Progetto aggiunto con successo");
                }
            }
        });


        buttonProg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (nomeProgettoField.getText().length() == 0 || sedeBox.getSelectedItem().equals(null) ||
                        capoProgettoBox.getSelectedItem().equals(null) || budgetField.getText().length() == 0)
                    showMessageDialog(getView(), "I campi \"Nuovo Progetto\", \"Scegliere la Sede\", \"Budget\" e" +
                            " \"Scegliere il Capo Progetto\" non possono essere lasciati vuoti");
                else {
                    int index = 0;
                    int indecs = 0;
                    List<Sede> lists = sessione.ottieniListaSede();
                    for (Sede s : lists)
                        if ((s.getNome()).equals(sedeBox.getSelectedItem()))
                            indecs = lists.indexOf(s);
                    List<CapoProgetto> listcp = sessione.ottieniListaCapoProgetto();
                    for (CapoProgetto cp : listcp)
                        if ((cp.getUsername()).equals(capoProgettoBox.getSelectedItem()))
                            index = listcp.indexOf(cp);
                    String newBudget = budgetField.getText();
                    newBudget = newBudget.replaceAll(",", ".");
                    if (!newBudget.contains("."))
                        newBudget = newBudget + ".00";
                    float budget = Float.parseFloat(newBudget);
                    sessione.aggiungiProgetto(nomeProgettoField.getText(), lists.get(indecs), budget, listcp.get(index));
                    showMessageDialog(getView(), "Progetto aggiunto con successo");
                }
            }
        });

        buttonMagazziniereNuovaS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (nomeSedeField.getText().length() == 0 || nomeNazioneField.getText().length() == 0 ||
                        prezzoSpedizioneField.getText().length() == 0 || nomeUtenteField.getText().length() == 0 ||
                        cognomeUtenteField.getText().length() == 0 || usernameUtenteField.getText().length() == 0 ||
                        passwordUtenteField.getText().length() == 0 || nuovoMagazzinoNomeField.getText().length() == 0)
                    showMessageDialog(getView(), "I campi \"Nome\", \"Cognome\", \"Username\", \"Password\"," +
                            " \"Nome Sede\", \"Nome Nazione\" e \"Costi Spedizione\" non possono essere lasciati vuoti");
                else {
                    String prezzoSpedizione = prezzoSpedizioneField.getText();
                    prezzoSpedizione = prezzoSpedizione.replaceAll(",", ".");
                    if (!prezzoSpedizione.contains("."))
                        prezzoSpedizione = prezzoSpedizione + ".00";
                    float spedizione = Float.parseFloat(prezzoSpedizione);
                    Nazione nazione = new Nazione(nomeNazioneField.getText(), spedizione);
                    Sede sede = new Sede(nomeSedeField.getToolTipText(), nazione);
                    try {
                        sessione.aggiungiMagazziniere(nomeUtenteField.getText(), cognomeUtenteField.getText(),
                                usernameUtenteField.getText(), passwordUtenteField.getText(), sede,
                                nuovoMagazzinoNomeField.getText());
                    } catch (UserExistsException e) {
                        showMessageDialog(getView(), "Utente già esistente");
                    } catch (UserLoadingException e) {
                        showMessageDialog(getView(), "Errore nel caricamento");
                    }
                    showMessageDialog(getView(), "Magazziniere aggiunto con successo");
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