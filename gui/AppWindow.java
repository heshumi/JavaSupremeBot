package gui;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import supreme.SupremeWebService;
import user.UserPreferences;
import user.UserProfile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class AppWindow{
    private SupremeWebService supremeWebService;
    private UserPreferences userPrefs;
    private UserProfile userProfile;

    private JTextField textField1;
    private JButton обновитьButton;
    private JButton runButton;
    private JPanel myPanel;
    private JTextField textField2;
    private JButton обновитьButton1;
    private JTextField textField3;
    private JTextArea textArea1;
    private JButton авторизоватьсяButton;
    private JLabel authInfo;
    private JComboBox comboBox1;
    private JTextField textField4;
    private JLabel label1;
    private JLabel label2;
    private JTextField textField5;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JButton заполнитьПрофильButton;
    private JButton указатьПутьКChromeDriverButton;
    private JTextField textField6;
    private JComboBox comboBox4;
    private JCheckBox checkBox1;
    private JLabel auth2;
    private JCheckBox checkBox2;
    private JTextField textField7;
    private JTextField textField8;

    public AppWindow(){
        userPrefs= new UserPreferences();
        userProfile= new UserProfile();
        supremeWebService = new SupremeWebService(userProfile, userPrefs);

        runButton.addActionListener(actionEvent -> {
            userPrefs.setUrl(textField1.getText());
            userPrefs.setCodeWord(textField2.getText());
            userPrefs.setColors(textField3.getText());
            userPrefs.setSizes(textField5.getText());
            int reload=comboBox1.getSelectedIndex();
            switch (reload){
                case 0:
                    userPrefs.setReload(true);
                    userPrefs.setReloadTime(Integer.parseInt(textField4.getText()));
                break;
                case 1: userPrefs.setReload(false);
                break;
                case -1: System.out.println("Ошибка. у reload индекс равен -1");
            }
            switch (comboBox2.getSelectedIndex()){
                case 0:userPrefs.setAnyColor(true);
                    break;
                case 1:userPrefs.setAnyColor(false);
            }

            switch (comboBox3.getSelectedIndex()){
                case 0:userPrefs.setAnySize(true);
                    break;
                case 1:userPrefs.setAnySize(false);
            }

            if (textField2.getText().equals("") || !userPrefs.isAnyColor() && userPrefs.getColors().size()==0)
                userPrefs.setCodeWord("123456"); //Делаем так, чтобы программа ничего не выбирала и ждала выбора пользователя
//            userPrefs.setUserProfileName(textField6.getText());
            userPrefs.setCheckoutDelay(textField6.getText());
            userPrefs.setTickAndPress(checkBox1.isSelected());
            userPrefs.setFillInfo(checkBox2.isSelected());

            supremeWebService.run();
        });


        авторизоватьсяButton.addActionListener(actionEvent -> {

            Document doc = null;
            try {
                doc = Jsoup.connect("http://worldclockapi.com/api/jsonp/cet/now?callback=mycallback").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String json= doc.body().text();

            int start=json.indexOf("currentDateTime")+18;
            int finish=json.indexOf("utcOffset")-15;

            String date = json.substring(start,finish);
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
            DateTime now=formatter.parseDateTime(date);


            //Если до 6 декабря, то показываем кнопку run
            DateTime december7= new DateTime(2018,12,7,0,0,0);

            if(now.isBefore(december7)) {
                runButton.setEnabled(true);
                заполнитьПрофильButton.setEnabled(true);
                auth2.setText("Успешная авторизация");
            }
            else auth2.setText("Не удалось авторизоваться");
            textField4.setText("100");
            textField6.setText("0");
            checkBox1.setSelected(true);
            checkBox2.setSelected(true);
            getPanelMain().remove(textArea1);
            //getPanelMain().remove(textField6);

        });

        comboBox1.addActionListener(actionEvent -> {
            if (comboBox1.getSelectedIndex()==1){
                label1.setEnabled(false);
                label2.setEnabled(false);
                textField4.setEnabled(false);
            } else {
                label1.setEnabled(true);
                label2.setEnabled(true);
                textField4.setEnabled(true);
            }
        });
        заполнитьПрофильButton.addActionListener(actionEvent -> {
            JFrame jFrame = new JFrame("Profile");
            jFrame.setContentPane(new Profile(userProfile).getPanelMain());
            jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            jFrame.pack();
            jFrame.setVisible(true);

        });
        указатьПутьКChromeDriverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser= new JFileChooser();
                fileChooser.showOpenDialog(null);
                File f = fileChooser.getSelectedFile();
                String filename= f.getAbsolutePath();
                userPrefs.setChromeDriverPath(filename);
            }
        });
    }
    public Container getPanelMain() {
        return myPanel;
    }

}
