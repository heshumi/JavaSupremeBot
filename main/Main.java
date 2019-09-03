package main;

import gui.AppWindow;

import javax.swing.*;

public class Main {
    public static void main(String args[]){

        JFrame jFrame = new JFrame("KokSupreme bot");
        jFrame.setContentPane(new AppWindow().getPanelMain());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
