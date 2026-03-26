import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ReturnBookDAO {

    public static int returnBook(int userId, int bookId) {

        int fine = 0;

        String selectQuery =
                "SELECT due_date FROM issued_books " +
                "WHERE user_id = ? AND book_id = ? AND return_date IS NULL";

        String updateIssue =
                "UPDATE issued_books SET return_date = CURDATE() " +
                "WHERE user_id = ? AND book_id = ? AND return_date IS NULL";

        String updateBook =
                "UPDATE books SET available = true WHERE book_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            // 1. Get due date
            PreparedStatement ps1 = con.prepareStatement(selectQuery);
            ps1.setInt(1, userId);
            ps1.setInt(2, bookId);

            ResultSet rs = ps1.executeQuery();

            if (!rs.next()) {
                return -1; // book not issued
            }

            Date dueDateSql = rs.getDate("due_date");
            LocalDate dueDate = dueDateSql.toLocalDate();
            LocalDate today = LocalDate.now();

            // 2. Calculate fine
            if (today.isAfter(dueDate)) {
                long daysLate = ChronoUnit.DAYS.between(dueDate, today);
                fine = (int) daysLate * 5;
            }

            // 3. Update issued_books
            PreparedStatement ps2 = con.prepareStatement(updateIssue);
            ps2.setInt(1, userId);
            ps2.setInt(2, bookId);
            ps2.executeUpdate();

            // 4. Update books table
            PreparedStatement ps3 = con.prepareStatement(updateBook);
            ps3.setInt(1, bookId);
            ps3.executeUpdate();

            return fine;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
}