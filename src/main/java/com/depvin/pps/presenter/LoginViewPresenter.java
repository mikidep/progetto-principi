package com.depvin.pps.presenter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;

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
