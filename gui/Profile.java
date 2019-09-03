package gui;

import user.UserProfile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Profile {
    private JPanel panelMain;
    private JTextField fullNameTextField;
    private JTextField emailTextField;
    private JTextField telTextField;
    private JTextField addressTextField;
    private JTextField address2TextField;
    private JTextField address3TextField;
    private JTextField cityTextField;
    private JTextField postcodeTextField;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JTextField numberTextField;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JTextField CVVTextField;
    private JButton сохранитьButton;

    public Profile(UserProfile userProfile) {
        сохранитьButton.addActionListener(actionEvent -> {
            userProfile.setFullName(fullNameTextField.getText());
            userProfile.setEmail(emailTextField.getText());
            userProfile.setTel(telTextField.getText());
            userProfile.setAddress(addressTextField.getText());
            userProfile.setAddress2(address2TextField.getText());
            userProfile.setAddress3(address3TextField.getText());
            userProfile.setCity(cityTextField.getText());
            userProfile.setPostcode(postcodeTextField.getText());
            userProfile.setCountry((String) comboBox1.getSelectedItem());
            userProfile.setCard((String)comboBox2.getSelectedItem());
            userProfile.setCardNumber(numberTextField.getText());
            userProfile.setDay((String) comboBox3.getSelectedItem());
            userProfile.setYear((String)comboBox4.getSelectedItem());
            userProfile.setCvv(CVVTextField.getText());

        });
    }

    public Container getPanelMain(){
        return panelMain;
    }
}
