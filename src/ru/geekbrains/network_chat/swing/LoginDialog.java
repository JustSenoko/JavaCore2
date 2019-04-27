package ru.geekbrains.network_chat.swing;

import ru.geekbrains.network_chat.Network;
import ru.geekbrains.network_chat.authorization.AuthException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;

class LoginDialog extends JDialog {
    private final JButton btnRegistration;
    private Network network;
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JButton btnLogin;
    private JButton btnCancel;

    private boolean connected;

    LoginDialog(Frame parent, Network network) {
        super(parent, "Логин", true);
        this.network = network;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        lbUsername = new JLabel("Имя пользователя: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbUsername, cs);

        tfUsername = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(tfUsername, cs);

        lbPassword = new JLabel("Пароль: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbPassword, cs);

        pfPassword = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(pfPassword, cs);
        panel.setBorder(new LineBorder(Color.GRAY));

        btnLogin = new JButton("Войти");
        btnRegistration = new JButton("Регистрация");
        btnCancel = new JButton("Отмена");

        JPanel bp = new JPanel();
        bp.add(btnLogin);

        btnLogin.addActionListener(e -> authorise());

        bp.add(btnRegistration);
        btnRegistration.addActionListener(e -> {
            RegistrationDialog registrationDialog = new RegistrationDialog(this, network);
            registrationDialog.setVisible(true);
            if (registrationDialog.isRegistered()) {
                tfUsername.setText(registrationDialog.getLogin());
                pfPassword.setText(registrationDialog.getPassword());
            }
        });

        bp.add(btnCancel);
        btnCancel.addActionListener(e -> {
            connected = false;
            dispose();
        });

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    private void authorise() {
        try {
            network.authorize(tfUsername.getText(), String.valueOf(pfPassword.getPassword()));
            connected = true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(LoginDialog.this,
                    "Ошибка сети",
                    "Авторизация",
                    JOptionPane.ERROR_MESSAGE);
            return;
        } catch (AuthException ex) {
            JOptionPane.showMessageDialog(LoginDialog.this,
                    "Ошибка авторизации",
                    "Авторизация",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        dispose();
    }

    boolean isConnected() {
        return connected;
    }
}
