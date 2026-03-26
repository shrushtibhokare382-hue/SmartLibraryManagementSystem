import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class IssueBookDAO {

    public static void issueBook(int userId, int bookId) {

        String issueSql =
            "INSERT INTO issued_books (user_id, book_id, issue_date, due_date) " +
            "VALUES (?, ?, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 14 DAY))";

        String updateBookSql =
            "UPDATE books SET available = false WHERE book_id = ?";

        try {
            Connection con = DBConnection.getConnection();

            // 1. Insert into issued_books
            PreparedStatement ps1 = con.prepareStatement(issueSql);
            ps1.setInt(1, userId);
            ps1.setInt(2, bookId);
            ps1.executeUpdate();

            // 2. Update book availability
            PreparedStatement ps2 = con.prepareStatement(updateBookSql);
            ps2.setInt(1, bookId);
            ps2.executeUpdate();

            System.out.println("Book issued successfully");

            con.close();

            FileWriter historyWriter = new FileWriter("history.txt", true);
historyWriter.write(con + "\n");
historyWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}