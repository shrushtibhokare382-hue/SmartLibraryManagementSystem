
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewBooks{

    public static void showAllBooks() {
        String sql = "SELECT * FROM books";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("---- Book List ----");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("book_id") + " | " +
                        rs.getString("title") + " | " +
                        rs.getString("author") + " | Available: " +
                        rs.getBoolean("available")
                );
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // NEW METHOD
    public static void addBook(String title, String author) {
        String sql = "INSERT INTO books (title, author, available) VALUES (?, ?, true)";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, author);

            ps.executeUpdate();
            System.out.println("Book added successfully");

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}