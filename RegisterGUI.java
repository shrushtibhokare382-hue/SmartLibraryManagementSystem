import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.io.FileWriter;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegisterGUI extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;
    JComboBox<String> roleBox;

    public RegisterGUI() {

        setTitle("Create Account");
        setSize(520,420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Gradient Background
        JPanel background = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0,0,new Color(102,126,234),
                        0,getHeight(),new Color(118,75,162));
                g2.setPaint(gp);
                g2.fillRect(0,0,getWidth(),getHeight());
            }
        };

        background.setLayout(new GridBagLayout());
        add(background);

        // Glass Card
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(340,280));
        card.setBackground(new Color(255,255,255,220));
        card.setLayout(new BoxLayout(card,BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(20,30,20,30));

        JLabel title = new JLabel("📚 Register Account");
        title.setFont(new Font("Segoe UI",Font.BOLD,20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(title);
        card.add(Box.createVerticalStrut(15));

        roleBox = new JComboBox<>(new String[]{"User","Admin"});
        roleBox.setMaximumSize(new Dimension(260,35));

        usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(260,35));

        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(260,35));

        JCheckBox showPass = new JCheckBox("Show Password");
        showPass.setBackground(new Color(0,0,0,0));
        showPass.addActionListener(e -> {
            if(showPass.isSelected())
                passwordField.setEchoChar((char)0);
            else
                passwordField.setEchoChar('•');
        });

        card.add(new JLabel("Role"));
        card.add(roleBox);
        card.add(Box.createVerticalStrut(8));

        card.add(new JLabel("Username"));
        card.add(usernameField);
        card.add(Box.createVerticalStrut(8));

        card.add(new JLabel("Password"));
        card.add(passwordField);

        card.add(showPass);
        card.add(Box.createVerticalStrut(10));

        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(new Color(46,204,113));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton backBtn = new JButton("Back to Login");
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(registerBtn);
        card.add(Box.createVerticalStrut(8));
        card.add(backBtn);

        background.add(card);

        // REGISTER BUTTON
        registerBtn.addActionListener(e -> registerUser());

        // BACK BUTTON
        backBtn.addActionListener(e -> {
            new LoginGUI().setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private void registerUser(){

        String role = (String) roleBox.getSelectedItem();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if(username.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please fill all fields");
            return;
        }

        try{

            FileWriter writer = new FileWriter("users.txt",true);
            writer.write(role + "," + username + "," + password + "\n");
            writer.close();

            JOptionPane.showMessageDialog(this,"Registration Successful 🎉");

            new LoginGUI().setVisible(true);
            dispose();

        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Error saving user");
        }
    }

    public static void main(String[] args){
        new RegisterGUI();
    }
}