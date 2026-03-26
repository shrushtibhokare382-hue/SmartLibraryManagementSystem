import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class UserGUI extends JFrame {

    JTextArea area;

    public UserGUI() {
        setTitle("User Dashboard");
        setSize(500,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton showBooks = new JButton("View Available Books");
        JButton logout = new JButton("Logout");

        area = new JTextArea();
        JScrollPane scroll = new JScrollPane(area);

        setLayout(new BorderLayout());
        add(showBooks, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(logout, BorderLayout.SOUTH);

        showBooks.addActionListener(e -> loadBooks());
        logout.addActionListener(e -> {
            new LoginGUI().setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private void loadBooks() {
        area.setText("");

        try {
            BufferedReader reader = new BufferedReader(new FileReader("available.txt"));
            String line;
            while((line = reader.readLine()) != null) {
                area.append(line + "\n");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}