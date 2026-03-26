import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IssuedBooksDAO {

    public static String getIssuedBooks() {

        StringBuilder result = new StringBuilder();

        String query =
                "SELECT ib.user_id, b.book_id, b.title, ib.issue_date, ib.due_date " +
                "FROM issued_books ib " +
                "JOIN books b ON ib.book_id = b.book_id " +
                "WHERE ib.return_date IS NULL";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                result.append(
                        "User ID: ").append(rs.getInt("user_id"))
                      .append(" | Book ID: ").append(rs.getInt("book_id"))
                      .append(" | Title: ").append(rs.getString("title"))
                      .append(" | Issue Date: ").append(rs.getDate("issue_date"))
                      .append(" | Due Date: ").append(rs.getDate("due_date"))
                      .append("\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result.length() == 0
                ? "No books are currently issued."
                : result.toString();
    }
}