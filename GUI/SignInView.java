package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Controller.Controller;
import Client.Client;

public class SignInView extends InterfaceView {
    private JPanel contentPane;
    private JButton buttonReturn;
    private JTextField emailField;
    private JTextField passField;
    private JButton signInButton;

    private Client user;
    private Controller controller;
    private String email=null;
    private String password=null;


    public SignInView(Controller controller) {
        super(controller);
        this.controller = controller;
        setContentPane(contentPane);

        //getRootPane().setDefaultButton(buttonReturn);   
        this.pack();
        setModal(true);

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSignIn();
            }
        });
        buttonReturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onReturn();
            }
        });
    }

    public void onSignIn(){
        email = emailField.getText();
        password = passField.getText();
        user.signInUser();
    }

    public void onReturn(){
        this.dispose();
        new Menu(controller).setVisible(true);
    }
}
