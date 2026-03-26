import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class LibraryGUI extends JFrame {

    private JTextArea outputArea;
    private JLabel totalBooksLabel;
    private JLabel issuedBooksLabel;

    private boolean darkMode = false;

    Color lightBg = new Color(245,247,250);
    Color darkBg = new Color(30,30,30);

    JPanel mainPanel;

    public LibraryGUI() {

        setTitle("Smart Library Management System");
        setSize(1100,650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(lightBg);
        add(mainPanel);

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(41,128,185));
        header.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));

        JLabel title = new JLabel("📚 Smart Library Dashboard");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI",Font.BOLD,26));

        JPanel rightHeader = new JPanel();
        rightHeader.setOpaque(false);

        JButton darkModeBtn = new JButton("🌙");
        darkModeBtn.setFont(new Font("Segoe UI",Font.BOLD,14));
        darkModeBtn.setFocusPainted(false);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Segoe UI",Font.PLAIN,12));
        logoutBtn.setBackground(new Color(231,76,60));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);

        rightHeader.add(darkModeBtn);
        rightHeader.add(logoutBtn);

        header.add(title,BorderLayout.WEST);
        header.add(rightHeader,BorderLayout.EAST);

        mainPanel.add(header,BorderLayout.NORTH);

        // SIDEBAR
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(7,1,10,10));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20,15,20,15));

        JButton addBookBtn = createButton("Add Book");
        JButton issueBookBtn = createButton("Issue Book");
        JButton viewBooksBtn = createButton("Available Books");
        JButton issuedBtn = createButton("Issued Books");
        JButton searchBtn = createButton("Search Book");
        JButton statsBtn = createButton("Statistics");
        JButton clearBtn = createButton("Clear Output");

        sidebar.add(addBookBtn);
        sidebar.add(issueBookBtn);
        sidebar.add(viewBooksBtn);
        sidebar.add(issuedBtn);
        sidebar.add(searchBtn);
        sidebar.add(statsBtn);
        sidebar.add(clearBtn);

        mainPanel.add(sidebar,BorderLayout.WEST);

        // CENTER
        JPanel center = new JPanel(new BorderLayout());
        center.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        JPanel cards = new JPanel(new GridLayout(1,2,20,20));

        totalBooksLabel = createCard("Total Books",new Color(46,204,113));
        issuedBooksLabel = createCard("Issued Books",new Color(231,76,60));

        cards.add(totalBooksLabel);
        cards.add(issuedBooksLabel);

        center.add(cards,BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setFont(new Font("Consolas",Font.PLAIN,14));
        outputArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("System Output"));

        center.add(scrollPane,BorderLayout.CENTER);

        mainPanel.add(center,BorderLayout.CENTER);

        // BUTTON ACTIONS
        addBookBtn.addActionListener(e -> addBook());
        issueBookBtn.addActionListener(e -> issueBook());
        viewBooksBtn.addActionListener(e -> showBooks("books.txt"));
        issuedBtn.addActionListener(e -> showBooks("issued.txt"));
        searchBtn.addActionListener(e -> searchBook());
        statsBtn.addActionListener(e -> showStatistics());
        clearBtn.addActionListener(e -> outputArea.setText(""));
        logoutBtn.addActionListener(e -> logout());
        darkModeBtn.addActionListener(e -> toggleDarkMode());

        createFiles();
        updateStats();
    }

    private JButton createButton(String text){

        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI",Font.BOLD,14));
        btn.setBackground(new Color(52,73,94));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        btn.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt){
                btn.setBackground(new Color(41,128,185));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt){
                btn.setBackground(new Color(52,73,94));
            }
        });

        return btn;
    }

    private JLabel createCard(String title,Color color){

        JLabel label = new JLabel(title + ": 0",SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(color);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI",Font.BOLD,22));

        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE,2),
                BorderFactory.createEmptyBorder(40,20,40,20)));

        return label;
    }

    private void createFiles(){

        try{

            File books = new File("books.txt");
            File issued = new File("issued.txt");

            if(!books.exists()) books.createNewFile();
            if(!issued.exists()) issued.createNewFile();

        }catch(Exception e){
            outputArea.append("Error creating files\n");
        }
    }

    private void addBook(){

        String book = JOptionPane.showInputDialog(this,"Enter Book Name");

        if(book == null || book.isEmpty()) return;

        try{

            BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt",true));
            writer.write(book);
            writer.newLine();
            writer.close();

            outputArea.append("Book Added: " + book + "\n");

            updateStats();

        }catch(Exception e){
            outputArea.append("Error adding book\n");
        }
    }

    private void issueBook(){

        String book = JOptionPane.showInputDialog(this,"Enter Book Name");
        String student = JOptionPane.showInputDialog(this,"Enter Student Name");

        if(book == null || student == null) return;

        LocalDate issueDate = LocalDate.now();
        LocalDate dueDate = issueDate.plusDays(7);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try{

            BufferedWriter writer = new BufferedWriter(new FileWriter("issued.txt",true));

            writer.write(book + " | " + student + " | " +
                    issueDate.format(formatter) + " | " +
                    dueDate.format(formatter));

            writer.newLine();
            writer.close();

            outputArea.append("Book Issued Successfully\n");

            updateStats();

        }catch(Exception e){
            outputArea.append("Error issuing book\n");
        }
    }

    private void showBooks(String file){

        try{

            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;

            outputArea.append("\n------ " + file + " ------\n");

            while((line = reader.readLine()) != null){
                outputArea.append(line + "\n");
            }

            reader.close();

        }catch(Exception e){
            outputArea.append("No records found\n");
        }
    }

    private void searchBook(){

        String search = JOptionPane.showInputDialog(this,"Enter Book Name");

        try{

            BufferedReader reader = new BufferedReader(new FileReader("books.txt"));

            String line;
            boolean found = false;

            while((line = reader.readLine()) != null){

                if(line.equalsIgnoreCase(search)){
                    found = true;
                    break;
                }
            }

            if(found)
                outputArea.append("Book Found: " + search + "\n");
            else
                outputArea.append("Book Not Found\n");

            reader.close();

        }catch(Exception e){
            outputArea.append("Search error\n");
        }
    }

    private void showStatistics() {

    int total = countLines("books.txt");
    int issued = countLines("issued.txt");

    JFrame graphFrame = new JFrame("Library Statistics");
    graphFrame.setSize(600,400);
    graphFrame.setLocationRelativeTo(this);

    StatsGraph graph = new StatsGraph(total, issued);

    graphFrame.add(graph);

    graphFrame.setVisible(true);
}
    private void updateStats(){

        totalBooksLabel.setText("Total Books: " + countLines("books.txt"));
        issuedBooksLabel.setText("Issued Books: " + countLines("issued.txt"));
    }

    private int countLines(String file){

        int count = 0;

        try{

            BufferedReader reader = new BufferedReader(new FileReader(file));

            while(reader.readLine() != null)
                count++;

            reader.close();

        }catch(Exception e){}

        return count;
    }

    private void toggleDarkMode(){

        darkMode = !darkMode;

        mainPanel.setBackground(darkMode ? darkBg : lightBg);

        outputArea.setBackground(darkMode ? Color.BLACK : Color.WHITE);
        outputArea.setForeground(darkMode ? Color.GREEN : Color.BLACK);
    }

    private void logout(){

        new LoginGUI().setVisible(true);
        dispose();
    }

    public static void main(String[] args){

        new LibraryGUI().setVisible(true);
    }
}