package com.depvin.pps.presenter;

import com.depvin.pps.business.Sessione;
import com.depvin.pps.business.Sistema;
import com.depvin.pps.business.UserLoadingException;
import com.depvin.pps.business.UserNotFoundException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by costantino on 24/05/16.
 */
public class LoginViewPresenter {
    private JFrame view;
    private JPanel rootPanel;
    private JPasswordField passwordField;
    private JButton submitButton;
    private JTextField usernameTextField;

    public LoginViewPresenter() {
        view = new JFrame("Login");
        view.setContentPane(rootPanel);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.pack();

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (usernameTextField.getText().length() == 0 || passwordField.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(getView(), "I campi \"username\" e \"password\" non possono essere lasciati vuoti");
                } else {
                    try {
                        Sessione s = Sistema.getInstance().login(usernameTextField.getText(), String.valueOf(passwordField.getPassword()));

                    } catch (UserNotFoundException e) {
                        try {
                            Thread.sleep(2000);
                        } catch (java.lang.InterruptedException es) {
                        }
                        JOptionPane.showMessageDialog(getView(), "Dati dell'account non validi, immettere correttamente le credenziali porcoddio");
                    } catch (UserLoadingException e) {
                        JOptionPane.showMessageDialog(getView(), "Errore nel caricamento della sessione");
                    }
                }
            }
        });
    }

    public void main() {
        view.setVisible(true);
    }

    public JFrame getView() {
        return view;
    }
}
