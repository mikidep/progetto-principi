package com.depvin.pps.presenter;

import com.depvin.pps.business.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by costantino on 24/05/16.
 */
public class LoginViewPresenter {
    private JFrame view;
    private JPanel rootPanel;
    private JPasswordField passwordField;
    private JButton submitButton;
    private JTextField usernameTextField;
    private JLabel loadingLabel;

    public LoginViewPresenter() {
        view = new JFrame("Login");
        rootPanel.setPreferredSize(new Dimension(500, 150));
        view.setLocation(450, 300);
        view.setContentPane(rootPanel);

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.pack();

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (usernameTextField.getText().length() == 0 || passwordField.getPassword().length == 0) {
                    showMessageDialog(getView(), "I campi \"username\" e \"password\" non possono essere lasciati vuoti");
                } else {
                    loadingLabel.setVisible(true);
                    submitButton.setEnabled(false);
                    SwingWorker<Sessione, Void> worker = new SwingWorker<Sessione, Void>() {
                        @Override
                        protected Sessione doInBackground() throws UserNotFoundException, UserLoadingException {
                            return Sistema.getInstance().login(usernameTextField.getText(), String.valueOf(passwordField.getPassword()));
                        }

                        @Override
                        protected void done() {
                            try {
                                Sessione s = get();
                                showMessageDialog(getView(), "Login effettuato con successo");
                                if (s instanceof SessioneDipendente) {
                                    SessioneDipendenteViewPresenter p = new SessioneDipendenteViewPresenter((SessioneDipendente) s);
                                    view.setVisible(false);
                                    p.show();

                                } else if (s instanceof SessioneAmministratore) {
                                    SessioneAmministratoreViewPresenter p = new SessioneAmministratoreViewPresenter((SessioneAmministratore) s);
                                    view.setVisible(false);
                                    p.show();

                                } else if (s instanceof SessioneCapoProgetto) {
                                    SessioneCapoProgettoViewPresenter p = new SessioneCapoProgettoViewPresenter((SessioneCapoProgetto) s);
                                    view.setVisible(false);
                                    p.show();
                                } else {
                                    SessioneMagazziniereViewPresenter p = new SessioneMagazziniereViewPresenter((SessioneMagazziniere) s);
                                    view.setVisible(false);
                                    p.show();
                                }
                            } catch (ExecutionException e) {
                                if (e.getCause() instanceof UserNotFoundException) {
                                    try {
                                        Thread.sleep(2000);
                                    } catch (java.lang.InterruptedException ignored) {
                                    }
                                    showMessageDialog(getView(), "Dati dell'account non validi, immettere correttamente le credenziali porcoddio");
                                } else if (e.getCause() instanceof UserLoadingException) {
                                    showMessageDialog(getView(), "Errore nel caricamento della sessione");
                                } else {
                                    e.getCause().printStackTrace();
                                }
                            } catch (InterruptedException ignored) {

                            } finally {
                                loadingLabel.setVisible(false);
                                submitButton.setEnabled(true);
                            }
                        }
                    };
                    worker.execute();
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
