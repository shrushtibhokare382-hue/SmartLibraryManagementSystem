import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FineCalculatorDAO {

    public static int calculateFine(int userId, int bookId) {
        
        String sql =
            "SELECT DATEDIFF(return_date, due_date) AS late_days " +
            "FROM issued_books " +
            "WHERE user_id = ? AND book_id = ? AND return_date IS NOT NULL";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, userId);
            ps.setInt(2, bookId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int lateDays = rs.getInt("late_days");

                if (lateDays > 0) {
                    int fine = lateDays * 5;
                    System.out.println("Late by " + lateDays + " days");
                    System.out.println("Fine amount: ₹" + fine);
                } else {
                    System.out.println("Book returned on time. No fine.");
                }
            } else {
                System.out.println("Return record not found");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}