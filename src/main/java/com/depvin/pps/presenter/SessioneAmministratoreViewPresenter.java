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
    private JButton buttonDipProg;

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

    private JComboBox progettoBox;
    private JComboBox sedeBox;
    private JComboBox capoProgettoBox;
    private JComboBox selezioneOpzioneBox;

    private JLabel nomeLabel;
    private JLabel cognomeLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel capoProgettoBoxLabel;
    private JLabel progettoBoxLabel;
    private JLabel sedeBoxLabel;
    private JLabel sedeLabel;
    private JLabel nazioneLabel;
    private JLabel nuovoMagazzinoLabel;
    private JLabel spedizioneLabel;
    private JLabel magazzinoLabel;
    private JLabel nuovoProgettoLabel;
    private JLabel budgetLabel;

    public SessioneAmministratoreViewPresenter(final SessioneAmministratore sessione) {

        this.sessione = sessione;
        final Amministratore a = sessione.getUtente();
        view = new JFrame("Sessione: " + a.getNome() + " " + a.getCognome());
        rootPanel.setPreferredSize(new Dimension(850, 850));
        view.setLocation(250, 0);
        view.setContentPane(rootPanel);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.pack();

        selezioneOpzioneBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                if (selezioneOpzioneBox.getSelectedIndex() == 1) {
                    nomeUtenteField.setText("");
                    cognomeUtenteField.setText("");
                    usernameUtenteField.setText("");
                    passwordUtenteField.setText("");
                    capoProgettoBox.removeAllItems();

                    List<CapoProgetto> listCapoProgetto = sessione.ottieniListaCapoProgetto();
                    for (CapoProgetto cp : listCapoProgetto)
                        capoProgettoBox.addItem(cp.getUsername());

                    progettoBox.removeAllItems();
                    progettoBox.setEnabled(false);
                    progettoBox.setVisible(true);
                    int index = capoProgettoBox.getSelectedIndex();
                    List<Progetto> list = sessione.ottieniListaCapoProgetto().get(index).getProgetti();
                    for (Progetto prog : list)
                        progettoBox.addItem(prog.getNome());
                    progettoBox.setSelectedIndex(-1);
                    capoProgettoBox.setSelectedIndex(-1);

                    nomeUtenteField.setVisible(true);
                    cognomeUtenteField.setVisible(true);
                    usernameUtenteField.setVisible(true);
                    passwordUtenteField.setVisible(true);
                    capoProgettoBox.setVisible(true);
                    buttonDip.setVisible(true);

                    nuovoMagazzinoNomeField.setVisible(false);
                    nomeMagazzinoField.setVisible(false);
                    nomeProgettoField.setVisible(false);
                    budgetField.setVisible(false);
                    sedeBox.setVisible(false);
                    nomeSedeField.setVisible(false);
                    nomeNazioneField.setVisible(false);
                    prezzoSpedizioneField.setVisible(false);

                    buttonAdmin.setVisible(false);
                    buttonMagazziniereVecchiaS.setVisible(false);
                    buttonMagazziniereNuovaS.setVisible(false);
                    buttonCapo.setVisible(false);
                    buttonProg.setVisible(false);
                    buttonDipProg.setVisible(false);

                    nomeLabel.setVisible(true);
                    cognomeLabel.setVisible(true);
                    usernameLabel.setVisible(true);
                    passwordLabel.setVisible(true);
                    capoProgettoBoxLabel.setVisible(true);
                    progettoBoxLabel.setVisible(true);

                    sedeBoxLabel.setVisible(false);
                    sedeLabel.setVisible(false);
                    nazioneLabel.setVisible(false);
                    nuovoMagazzinoLabel.setVisible(false);
                    spedizioneLabel.setVisible(false);
                    magazzinoLabel.setVisible(false);
                    nuovoProgettoLabel.setVisible(false);
                    budgetLabel.setVisible(false);
                } else if (selezioneOpzioneBox.getSelectedIndex() == 2) {
                    nomeUtenteField.setText("");
                    cognomeUtenteField.setText("");
                    usernameUtenteField.setText("");
                    passwordUtenteField.setText("");

                    nomeUtenteField.setVisible(true);
                    cognomeUtenteField.setVisible(true);
                    usernameUtenteField.setVisible(true);
                    passwordUtenteField.setVisible(true);
                    buttonAdmin.setVisible(true);

                    nuovoMagazzinoNomeField.setVisible(false);
                    progettoBox.setVisible(false);
                    progettoBox.setEnabled(false);
                    capoProgettoBox.setVisible(false);
                    sedeBox.setVisible(true); //Viene posto prima true e poi false perchè se no non verrebbe aggiornata
                    sedeBox.setVisible(false);//l'interfaccia grafica se la prima scelta è stata di creare l'amministratore

                    buttonDip.setVisible(false);
                    buttonMagazziniereVecchiaS.setVisible(false);
                    buttonMagazziniereNuovaS.setVisible(false);
                    buttonCapo.setVisible(false);
                    buttonProg.setVisible(false);
                    buttonDipProg.setVisible(false);

                    nomeMagazzinoField.setVisible(false);
                    nomeProgettoField.setVisible(false);
                    budgetField.setVisible(false);
                    nomeSedeField.setVisible(false);
                    nomeNazioneField.setVisible(false);
                    prezzoSpedizioneField.setVisible(false);

                    nomeLabel.setVisible(true);
                    cognomeLabel.setVisible(true);
                    usernameLabel.setVisible(true);
                    passwordLabel.setVisible(true);

                    capoProgettoBoxLabel.setVisible(false);
                    progettoBoxLabel.setVisible(false);
                    sedeBoxLabel.setVisible(false);
                    sedeLabel.setVisible(false);
                    nazioneLabel.setVisible(false);
                    nuovoMagazzinoLabel.setVisible(false);
                    spedizioneLabel.setVisible(false);
                    magazzinoLabel.setVisible(false);
                    nuovoProgettoLabel.setVisible(false);
                    budgetLabel.setVisible(false);
                } else if (selezioneOpzioneBox.getSelectedIndex() == 3) {
                    nomeUtenteField.setText("");
                    cognomeUtenteField.setText("");
                    usernameUtenteField.setText("");
                    passwordUtenteField.setText("");
                    sedeBox.removeAllItems();
                    nomeMagazzinoField.setText("");

                    List<Sede> list = sessione.ottieniListaSede();
                    for (Sede sede : list)
                        sedeBox.addItem(sede.getNome());
                    sedeBox.setSelectedIndex(-1);

                    nomeUtenteField.setVisible(true);
                    cognomeUtenteField.setVisible(true);
                    usernameUtenteField.setVisible(true);
                    passwordUtenteField.setVisible(true);
                    sedeBox.setVisible(true);
                    nomeMagazzinoField.setVisible(true);
                    buttonMagazziniereVecchiaS.setVisible(true);

                    nuovoMagazzinoNomeField.setVisible(false);
                    nomeProgettoField.setVisible(false);
                    progettoBox.setVisible(false);
                    progettoBox.setEnabled(false);
                    capoProgettoBox.setVisible(false);
                    budgetField.setVisible(false);

                    buttonAdmin.setVisible(false);
                    buttonCapo.setVisible(false);
                    buttonDip.setVisible(false);
                    buttonProg.setVisible(false);
                    buttonMagazziniereNuovaS.setVisible(false);
                    buttonDipProg.setVisible(false);
                    nomeSedeField.setVisible(false);
                    nomeNazioneField.setVisible(false);
                    prezzoSpedizioneField.setVisible(false);

                    nomeLabel.setVisible(true);
                    cognomeLabel.setVisible(true);
                    usernameLabel.setVisible(true);
                    passwordLabel.setVisible(true);
                    sedeBoxLabel.setVisible(true);
                    magazzinoLabel.setVisible(true);

                    capoProgettoBoxLabel.setVisible(false);
                    progettoBoxLabel.setVisible(false);
                    sedeLabel.setVisible(false);
                    nazioneLabel.setVisible(false);
                    nuovoMagazzinoLabel.setVisible(false);
                    spedizioneLabel.setVisible(false);
                    nuovoProgettoLabel.setVisible(false);
                    budgetLabel.setVisible(false);
                } else if (selezioneOpzioneBox.getSelectedIndex() == 4) {
                    nomeUtenteField.setText("");
                    cognomeUtenteField.setText("");
                    usernameUtenteField.setText("");
                    passwordUtenteField.setText("");
                    prezzoSpedizioneField.setText("");
                    nomeNazioneField.setText("");
                    nomeSedeField.setText("");
                    nuovoMagazzinoNomeField.setText("");

                    nomeSedeField.setVisible(true);
                    nomeNazioneField.setVisible(true);
                    prezzoSpedizioneField.setVisible(true);
                    usernameUtenteField.setVisible(true);
                    passwordUtenteField.setVisible(true);
                    cognomeUtenteField.setVisible(true);
                    nomeUtenteField.setVisible(true);
                    nuovoMagazzinoNomeField.setVisible(true);
                    buttonMagazziniereNuovaS.setVisible(true);

                    sedeBox.setVisible(false);
                    buttonMagazziniereVecchiaS.setVisible(false);
                    nomeProgettoField.setVisible(false);
                    progettoBox.setVisible(false);
                    progettoBox.setEnabled(false);
                    capoProgettoBox.setVisible(false);
                    budgetField.setVisible(false);
                    buttonAdmin.setVisible(false);
                    buttonCapo.setVisible(false);
                    buttonDip.setVisible(false);
                    buttonProg.setVisible(false);
                    buttonDipProg.setVisible(false);
                    nomeMagazzinoField.setVisible(false);

                    nomeLabel.setVisible(true);
                    cognomeLabel.setVisible(true);
                    usernameLabel.setVisible(true);
                    passwordLabel.setVisible(true);
                    nuovoMagazzinoLabel.setVisible(true);
                    spedizioneLabel.setVisible(true);
                    sedeLabel.setVisible(true);
                    nazioneLabel.setVisible(true);

                    capoProgettoBoxLabel.setVisible(false);
                    progettoBoxLabel.setVisible(false);
                    sedeBoxLabel.setVisible(false);
                    magazzinoLabel.setVisible(false);
                    nuovoProgettoLabel.setVisible(false);
                    budgetLabel.setVisible(false);
                } else if (selezioneOpzioneBox.getSelectedIndex() == 5) {
                    nomeUtenteField.setText("");
                    cognomeUtenteField.setText("");
                    usernameUtenteField.setText("");
                    passwordUtenteField.setText("");

                    nomeUtenteField.setVisible(true);
                    cognomeUtenteField.setVisible(true);
                    usernameUtenteField.setVisible(true);
                    passwordUtenteField.setVisible(true);
                    buttonCapo.setVisible(true);

                    nuovoMagazzinoNomeField.setVisible(false);
                    capoProgettoBox.setVisible(false);
                    sedeBox.setVisible(false);
                    progettoBox.setVisible(false);
                    progettoBox.setEnabled(false);
                    sedeBox.setVisible(false);
                    buttonAdmin.setVisible(false);
                    buttonDip.setVisible(false);
                    buttonMagazziniereVecchiaS.setVisible(false);
                    buttonProg.setVisible(false);
                    buttonMagazziniereNuovaS.setVisible(false);
                    buttonDipProg.setVisible(false);

                    nomeMagazzinoField.setVisible(false);
                    nomeProgettoField.setVisible(false);
                    budgetField.setVisible(false);
                    nomeSedeField.setVisible(false);
                    nomeNazioneField.setVisible(false);
                    prezzoSpedizioneField.setVisible(false);

                    nomeLabel.setVisible(true);
                    cognomeLabel.setVisible(true);
                    usernameLabel.setVisible(true);
                    passwordLabel.setVisible(true);

                    capoProgettoBoxLabel.setVisible(false);
                    progettoBoxLabel.setVisible(false);
                    sedeBoxLabel.setVisible(false);
                    sedeLabel.setVisible(false);
                    nazioneLabel.setVisible(false);
                    nuovoMagazzinoLabel.setVisible(false);
                    spedizioneLabel.setVisible(false);
                    magazzinoLabel.setVisible(false);
                    nuovoProgettoLabel.setVisible(false);
                    budgetLabel.setVisible(false);
                } else if (selezioneOpzioneBox.getSelectedIndex() == 6) {
                    nomeProgettoField.setText("");
                    budgetField.setText("");
                    capoProgettoBox.removeAllItems();
                    sedeBox.removeAllItems();
                    List<CapoProgetto> listProgetto = sessione.ottieniListaCapoProgetto();
                    for (CapoProgetto cp : listProgetto)
                        capoProgettoBox.addItem(cp.getUsername());
                    capoProgettoBox.setSelectedIndex(-1);
                    List<Sede> listSede = sessione.ottieniListaSede();
                    for (Sede sede : listSede)
                        sedeBox.addItem(sede.getNome());
                    sedeBox.setSelectedIndex(-1);


                    nomeProgettoField.setVisible(true);
                    budgetField.setVisible(true);
                    capoProgettoBox.setVisible(true);
                    sedeBox.setVisible(true);
                    buttonProg.setVisible(true);

                    nuovoMagazzinoNomeField.setVisible(false);
                    progettoBox.setVisible(false);
                    progettoBox.setEnabled(false);
                    nomeUtenteField.setVisible(false);
                    cognomeUtenteField.setVisible(false);
                    usernameUtenteField.setVisible(false);
                    passwordUtenteField.setVisible(false);
                    nomeMagazzinoField.setVisible(false);
                    buttonAdmin.setVisible(false);
                    buttonCapo.setVisible(false);
                    buttonDip.setVisible(false);
                    buttonMagazziniereVecchiaS.setVisible(false);
                    buttonMagazziniereNuovaS.setVisible(false);
                    buttonDipProg.setVisible(false);
                    nomeSedeField.setVisible(false);
                    nomeNazioneField.setVisible(false);
                    prezzoSpedizioneField.setVisible(false);

                    nuovoProgettoLabel.setVisible(true);
                    budgetLabel.setVisible(true);
                    capoProgettoBoxLabel.setVisible(true);
                    sedeBoxLabel.setVisible(true);

                    nomeLabel.setVisible(false);
                    cognomeLabel.setVisible(false);
                    usernameLabel.setVisible(false);
                    passwordLabel.setVisible(false);
                    progettoBoxLabel.setVisible(false);
                    sedeLabel.setVisible(false);
                    nazioneLabel.setVisible(false);
                    nuovoMagazzinoLabel.setVisible(false);
                    spedizioneLabel.setVisible(false);
                    magazzinoLabel.setVisible(false);
                }
            }
        });

        capoProgettoBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                try {
                    progettoBox.removeAllItems();
                    progettoBox.setEnabled(false);
                    progettoBox.setVisible(false);
                    if (selezioneOpzioneBox.getSelectedIndex() == 1 || selezioneOpzioneBox.getSelectedIndex() == 7) {
                        progettoBox.setEnabled(true);
                        progettoBox.setVisible(true);
                    }
                    int index = capoProgettoBox.getSelectedIndex();
                    List<Progetto> list = sessione.ottieniListaCapoProgetto().get(index).getProgetti();
                    for (Progetto prog : list)
                        progettoBox.addItem(prog.getNome());
                    progettoBox.setSelectedIndex(-1);
                } catch (Exception e) {
                    /*"AWT-EventQueue-0" java.lang.ArrayIndexOutOfBoundsException: -1*/
                }
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
                        sessione.aggiungiAmministratore(nomeUtenteField.getText(), cognomeUtenteField.getText(),
                                usernameUtenteField.getText(), passwordUtenteField.getText());
                        showMessageDialog(getView(), "Amministratore aggiunto con successo");
                    } catch (UserExistsException e) {
                        showMessageDialog(getView(), "Utente già esistente");
                    } catch (UserLoadingException e) {
                        showMessageDialog(getView(), "Errore nel caricamento");
                    }
                }
            }
        });

        buttonDip.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (nomeUtenteField.getText().length() == 0 || cognomeUtenteField.getText().length() == 0 ||
                        usernameUtenteField.getText().length() == 0 || passwordUtenteField.getText().length() == 0 ||
                        progettoBox.getSelectedIndex() == -1 || capoProgettoBox.getSelectedIndex() == -1)
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
                        showMessageDialog(getView(), "Dipendente aggiunto con successo");
                    } catch (UserExistsException e) {
                        showMessageDialog(getView(), "Utente già esistente");
                    } catch (UserLoadingException e) {
                        showMessageDialog(getView(), "Errore nel caricamento");
                    } catch (UserNotFoundException e) {
                        showMessageDialog(getView(), "Utente non trovato");
                    }
                }
            }
        });

        buttonMagazziniereVecchiaS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (nomeUtenteField.getText().length() == 0 || cognomeUtenteField.getText().length() == 0 ||
                        usernameUtenteField.getText().length() == 0 || passwordUtenteField.getText().length() == 0 ||
                        nomeMagazzinoField.getText().length() == 0 || sedeBox.getSelectedIndex() == -1)
                    showMessageDialog(getView(), "I campi \"Nome\", \"Cognome\", \"Username\", \"Password\"," +
                            " \"Scegliere la Sede\" e \"Magazzino\" non possono essere lasciati vuoti");
                else {
                    try {
                        int index = 0;
                        List<Sede> lists = sessione.ottieniListaSede();
                        for (Sede s : lists)
                            if ((s.getNome()).equals(sedeBox.getSelectedItem()))
                                index = lists.indexOf(s);
                        sessione.aggiungiMagazziniere(nomeUtenteField.getText(), cognomeUtenteField.getText(),
                                usernameUtenteField.getText(), passwordUtenteField.getText(), lists.get(index),
                                nomeMagazzinoField.getText());
                        showMessageDialog(getView(), "Magazziniere aggiunto con successo");
                    } catch (UserExistsException e) {
                        showMessageDialog(getView(), "Utente già esistente");
                    } catch (UserLoadingException e) {
                        showMessageDialog(getView(), "Errore nel caricamento");
                    }
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
                        sessione.aggiungiCapoProgetto(nomeUtenteField.getText(), cognomeUtenteField.getText(),
                                usernameUtenteField.getText(), passwordUtenteField.getText());
                        showMessageDialog(getView(), "Capo Progetto aggiunto con successo");
                    } catch (UserExistsException e) {
                        showMessageDialog(getView(), "Utente già esistente");
                    } catch (UserLoadingException e) {
                        showMessageDialog(getView(), "Errore nel caricamento");
                    }
                }
            }
        });


        buttonProg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (nomeProgettoField.getText().length() == 0 || sedeBox.getSelectedIndex() == -1 ||
                        capoProgettoBox.getSelectedIndex() == -1 || budgetField.getText().length() == 0)
                    showMessageDialog(getView(), "I campi \"Scegliere il Capo Progetto\", \"Scegliere la Sede\"," +
                            " \"Nuovo Progetto\" e \"Budget\" non possono essere lasciati vuoti");
                else {
                    int index = 0;
                    int indecs = 0;
                    List<Sede> lists = sessione.ottieniListaSede();
                    for (Sede s : lists)
                        if (s.equals(sedeBox.getSelectedItem()))
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
                            " \"Nome Sede\", \"Nome Nazione\", \"Nuovo Magazzino\" e \"Costi Spedizione\"" +
                            " non possono essere lasciati vuoti");
                else {
                    String prezzoSpedizione = prezzoSpedizioneField.getText();
                    prezzoSpedizione = prezzoSpedizione.replaceAll(",", ".");
                    if (!prezzoSpedizione.contains("."))
                        prezzoSpedizione = prezzoSpedizione + ".00";
                    float spedizione = Float.parseFloat(prezzoSpedizione);
                    Nazione nazione = new Nazione(nomeNazioneField.getText(), spedizione);
                    Sede sede = new Sede(nomeSedeField.getText(), nazione);
                    try {
                        sessione.aggiungiMagazziniere(nomeUtenteField.getText(), cognomeUtenteField.getText(),
                                usernameUtenteField.getText(), passwordUtenteField.getText(), sede,
                                nuovoMagazzinoNomeField.getText());
                        showMessageDialog(getView(), "Magazziniere aggiunto con successo");
                    } catch (UserExistsException e) {
                        showMessageDialog(getView(), "Utente già esistente");
                    } catch (UserLoadingException e) {
                        showMessageDialog(getView(), "Errore nel caricamento");
                    }
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