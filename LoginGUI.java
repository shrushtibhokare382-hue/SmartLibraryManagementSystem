import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginGUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;
    private JComboBox<String> roleBox;

    public LoginGUI() {

        setTitle("Smart Library System");
        setSize(540,460);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Gradient background
        JPanel background = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                Graphics2D g2=(Graphics2D)g;
                GradientPaint gradient = new GradientPaint(
                        0,0,new Color(58,123,213),
                        0,getHeight(),new Color(58,96,115));
                g2.setPaint(gradient);
                g2.fillRect(0,0,getWidth(),getHeight());
            }
        };

        background.setLayout(new GridBagLayout());
        add(background);

        // Card
        JPanel card=new JPanel();
        card.setPreferredSize(new Dimension(360,320));
        card.setBackground(new Color(255,255,255,220));
        card.setLayout(new BoxLayout(card,BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(25,30,25,30));

        JLabel logo=new JLabel("📚");
        logo.setFont(new Font("Segoe UI Emoji",Font.PLAIN,42));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title=new JLabel("Library Login");
        title.setFont(new Font("Segoe UI",Font.BOLD,22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(logo);
        card.add(title);
        card.add(Box.createVerticalStrut(15));

        roleBox=new JComboBox<>(new String[]{"Admin","User"});
        roleBox.setMaximumSize(new Dimension(280,35));

        card.add(new JLabel("Login As"));
        card.add(roleBox);
        card.add(Box.createVerticalStrut(10));

        usernameField=new JTextField();
        passwordField=new JPasswordField();

        usernameField.setMaximumSize(new Dimension(280,35));
        passwordField.setMaximumSize(new Dimension(280,35));

        card.add(new JLabel("Username"));
        card.add(usernameField);
        card.add(Box.createVerticalStrut(10));

        card.add(new JLabel("Password"));
        card.add(passwordField);

        JCheckBox showPassword=new JCheckBox("Show Password");
        showPassword.setBackground(new Color(255,255,255,0));
        showPassword.addActionListener(e->{
            if(showPassword.isSelected())
                passwordField.setEchoChar((char)0);
            else
                passwordField.setEchoChar('•');
        });

        card.add(showPassword);

        messageLabel=new JLabel(" ");
        messageLabel.setForeground(Color.RED);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(messageLabel);

        // LOGIN BUTTON
        JButton loginBtn=new JButton("Login");
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setBackground(new Color(52,152,219));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);

        // REGISTER BUTTON
        JButton registerBtn=new JButton("Create Account");
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        registerBtn.addActionListener(e->{
            new RegisterGUI().setVisible(true);
            dispose();
        });

        loginBtn.addActionListener(e->login());

        card.add(Box.createVerticalStrut(10));
        card.add(loginBtn);
        card.add(Box.createVerticalStrut(10));
        card.add(registerBtn);

        background.add(card);
    }

    private void login(){

        String role=(String)roleBox.getSelectedItem();
        String username=usernameField.getText().trim();
        String password=new String(passwordField.getPassword()).trim();

        try{

            BufferedReader reader=new BufferedReader(new FileReader("users.txt"));
            String line;

            while((line=reader.readLine())!=null){

                String[] data=line.split(",");

                if(data.length==3){

                    String storedRole=data[0];
                    String storedUser=data[1];
                    String storedPass=data[2];

                    if(storedRole.equals(role) &&
                       storedUser.equals(username) &&
                       storedPass.equals(password)){

                        reader.close();

                        if(role.equals("Admin")){
                            new LibraryGUI().setVisible(true);
                        }else{
                            new UserGUI().setVisible(true);
                        }

                        dispose();
                        return;
                    }
                }
            }

            reader.close();
            messageLabel.setText("Invalid Login Details");

        }
        catch(IOException e){
            messageLabel.setText("users.txt not found");
        }
    }

    public static void main(String[] args){
        new LoginGUI().setVisible(true);
    }
}