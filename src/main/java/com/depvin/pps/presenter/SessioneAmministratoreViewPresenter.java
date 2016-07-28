package com.depvin.pps.presenter;

import com.depvin.pps.business.SessioneAmministratore;
import com.depvin.pps.business.UserExistsException;
import com.depvin.pps.business.UserLoadingException;
import com.depvin.pps.model.Amministratore;
import com.depvin.pps.model.CapoProgetto;
import com.depvin.pps.model.Sede;

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
    private JButton buttonMag;
    private JButton buttonCapo;
    private JButton buttonProg;
    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField progettoField;
    private JComboBox sedeBox;
    private JComboBox capoProgBox;
    private JTextField nomeProgField;
    private JTextField budgetField;
    private JTextField nomeMagazzinoField;
    private JRadioButton creaDipendenteRadioButton;
    private JRadioButton creaAmministratoreRadioButton;
    private JRadioButton creaMagazziniereRadioButton;
    private JRadioButton creaProgettoRadioButton;
    private JRadioButton creaCapoProgettoRadioButton;

    public SessioneAmministratoreViewPresenter(final SessioneAmministratore sessione) {
        this.sessione = sessione;
        final Amministratore a = sessione.getUtente();
        view = new JFrame("Sessione: " + a.getNome() + " " + a.getCognome());
        rootPanel.setPreferredSize(new Dimension(1200, 700));
        view.setLocation(75, 100);
        view.setContentPane(rootPanel);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(creaAmministratoreRadioButton);
        btnGroup.add(creaDipendenteRadioButton);
        btnGroup.add(creaMagazziniereRadioButton);
        btnGroup.add(creaCapoProgettoRadioButton);
        btnGroup.add(creaProgettoRadioButton);
        buttonAdmin.setEnabled(false);
        buttonCapo.setEnabled(false);
        buttonDip.setEnabled(false);
        buttonMag.setEnabled(false);
        buttonProg.setEnabled(false);
        view.pack();

        creaDipendenteRadioButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {

                capoProgBox.removeAllItems();
                List<CapoProgetto> list = sessione.ottieniListaCapoProgetto();
                for (CapoProgetto cp : list)
                    capoProgBox.addItem(cp.getUsername());

                usernameField.setVisible(true);
                passwordField.setVisible(true);
                progettoField.setVisible(true);
                cognomeField.setVisible(true);
                capoProgBox.setVisible(true);
                nomeField.setVisible(true);

                nomeMagazzinoField.setVisible(false);
                nomeProgField.setVisible(false);
                budgetField.setVisible(false);
                sedeBox.setVisible(false);
                buttonAdmin.setEnabled(false);
                buttonCapo.setEnabled(false);
                buttonDip.setEnabled(true);
                buttonMag.setEnabled(false);
                buttonProg.setEnabled(false);
            }
        });

        creaAmministratoreRadioButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {

                usernameField.setVisible(true);
                passwordField.setVisible(true);
                cognomeField.setVisible(true);
                nomeField.setVisible(true);

                nomeMagazzinoField.setVisible(false);
                nomeProgField.setVisible(false);
                progettoField.setVisible(false);
                budgetField.setVisible(false);
                capoProgBox.setVisible(false);
                sedeBox.setVisible(true); //Viene posto prima true e poi false perchè se no non verrebbe aggiornata
                sedeBox.setVisible(false);//la interfaccia grafica se la prima scelta è stata di creare l'amministratore
                buttonAdmin.setEnabled(true);
                buttonCapo.setEnabled(false);
                buttonDip.setEnabled(false);
                buttonMag.setEnabled(false);
                buttonProg.setEnabled(false);
            }
        });

        creaCapoProgettoRadioButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {

                passwordField.setVisible(true);
                usernameField.setVisible(true);
                cognomeField.setVisible(true);
                nomeField.setVisible(true);
                buttonCapo.setEnabled(true);

                nomeMagazzinoField.setVisible(false);
                nomeProgField.setVisible(false);
                progettoField.setVisible(false);
                capoProgBox.setVisible(false);
                budgetField.setVisible(false);
                sedeBox.setVisible(true);
                sedeBox.setVisible(false);
                buttonAdmin.setEnabled(false);
                buttonDip.setEnabled(false);
                buttonMag.setEnabled(false);
                buttonProg.setEnabled(false);
            }
        });

        creaMagazziniereRadioButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {

                sedeBox.removeAllItems();
                List<Sede> list = sessione.ottieniListaSede();
                for (Sede sede : list)
                    sedeBox.addItem(sede.getNome());

                nomeMagazzinoField.setVisible(true);
                usernameField.setVisible(true);
                passwordField.setVisible(true);
                cognomeField.setVisible(true);
                nomeField.setVisible(true);
                sedeBox.setVisible(true);
                buttonMag.setEnabled(true);

                progettoField.setVisible(false);
                nomeProgField.setVisible(false);
                capoProgBox.setVisible(false);
                budgetField.setVisible(false);
                buttonAdmin.setEnabled(false);
                buttonCapo.setEnabled(false);
                buttonDip.setEnabled(false);
                buttonProg.setEnabled(false);
            }
        });

        creaProgettoRadioButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {

                capoProgBox.removeAllItems();
                sedeBox.removeAllItems();
                List<CapoProgetto> listProgetto = sessione.ottieniListaCapoProgetto();
                for (CapoProgetto cp : listProgetto)
                    capoProgBox.addItem(cp.getUsername());
                List<Sede> listSede = sessione.ottieniListaSede();
                for (Sede sede : listSede)
                    sedeBox.addItem(sede.getNome());

                nomeProgField.setVisible(true);
                budgetField.setVisible(true);
                capoProgBox.setVisible(true);
                sedeBox.setVisible(true);
                buttonProg.setEnabled(true);

                nomeField.setVisible(false);
                cognomeField.setVisible(false);
                usernameField.setVisible(false);
                passwordField.setVisible(false);
                nomeMagazzinoField.setVisible(false);
                progettoField.setVisible(false);
                buttonAdmin.setEnabled(false);
                buttonCapo.setEnabled(false);
                buttonDip.setEnabled(false);
                buttonMag.setEnabled(false);
            }
        });

        buttonAdmin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (nomeField.getText().length() == 0 || cognomeField.getText().length() == 0 ||
                        usernameField.getText().length() == 0 || passwordField.getText().length() == 0)
                    showMessageDialog(getView(), "I campi \"nome\", \"cognome\", \"username\" e \"password\" non possono essere lasciati vuoti");
                else {
                    try {
                        sessione.aggiungiAmministratore(nomeField.getText(), cognomeField.getText(), usernameField.getText(), passwordField.getText());
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
                if (nomeField.getText().length() == 0 || cognomeField.getText().length() == 0 ||
                        usernameField.getText().length() == 0 || passwordField.getText().length() == 0 ||
                        progettoField.getText().length() == 0 || capoProgBox.getSelectedItem().equals(null))
                    showMessageDialog(getView(), "I campi \"nome\", \"cognome\", \"username\", \"password\", \"capo progetto\" e \"progetto\"non possono essere lasciati vuoti");
                else {
                    try {
                        int index = 0;
                        List<CapoProgetto> listcp = sessione.ottieniListaCapoProgetto();
                        for (CapoProgetto cp : listcp)
                            if ((cp.getUsername()).equals(capoProgBox.getSelectedItem()))
                                index = listcp.indexOf(cp);
                        sessione.aggiungiDipendente(nomeField.getText(), cognomeField.getText(), usernameField.getText(), passwordField.getText(), listcp.get(index), progettoField.getText());
                    } catch (UserExistsException e) {
                        showMessageDialog(getView(), "Utente già esistente");
                    } catch (UserLoadingException e) {
                        showMessageDialog(getView(), "Errore nel caricamento");
                    }
                }
            }
        });

        buttonMag.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (nomeField.getText().length() == 0 || cognomeField.getText().length() == 0 ||
                        usernameField.getText().length() == 0 || passwordField.getText().length() == 0 ||
                        nomeMagazzinoField.getText().length() == 0 || sedeBox.getSelectedItem().equals(null))
                    showMessageDialog(getView(), "I campi \"nome\", \"cognome\", \"username\", \"password\", \"sede\" e \"magazzino\" non possono essere lasciati vuoti");
                else {
                    try {
                        int index = 0;
                        List<Sede> lists = sessione.ottieniListaSede();
                        for (Sede s : lists)
                            if ((s.getNome()).equals(sedeBox.getSelectedItem()))
                                index = lists.indexOf(s);
                        sessione.aggiungiMagazziniere(nomeField.getText(), cognomeField.getText(), usernameField.getText(), passwordField.getText(), lists.get(index), nomeMagazzinoField.getText());
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
                if (nomeField.getText().length() == 0 || cognomeField.getText().length() == 0 ||
                        usernameField.getText().length() == 0 || passwordField.getText().length() == 0)
                    showMessageDialog(getView(), "I campi \"nome\", \"cognome\", \"username\" e \"password\" non possono essere lasciati vuoti");
                else {
                    try {
                        sessione.aggiungiCapoProgetto(nomeField.getText(), cognomeField.getText(), usernameField.getText(), passwordField.getText());
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
                if (nomeProgField.getText().length() == 0 || sedeBox.getSelectedItem().equals(null) ||
                        capoProgBox.getSelectedItem().equals(null) || budgetField.getText().length() == 0)
                    showMessageDialog(getView(), "I campi \"nome progetto\", \"sede\", \"budget\" e \"capo progetto\" non possono essere lasciati vuoti");
                else {
                    int index = 0;
                    int indecs = 0;
                    List<Sede> lists = sessione.ottieniListaSede();
                    for (Sede s : lists)
                        if ((s.getNome()).equals(sedeBox.getSelectedItem()))
                            indecs = lists.indexOf(s);
                    List<CapoProgetto> listcp = sessione.ottieniListaCapoProgetto();
                    for (CapoProgetto cp : listcp)
                        if ((cp.getUsername()).equals(capoProgBox.getSelectedItem()))
                            index = listcp.indexOf(cp);
                    String newBudget = budgetField.getText();
                    newBudget = newBudget.replaceAll(",", ".");
                    if (!newBudget.contains("."))
                        newBudget = newBudget + ".00";
                    float budget = Float.parseFloat(newBudget);
                    sessione.aggiungiProgetto(nomeProgField.getText(), lists.get(indecs), budget, listcp.get(index));
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