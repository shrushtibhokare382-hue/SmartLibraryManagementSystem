import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddBook {

    public static void main(String[] args) {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO books (title, author, available) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, "Java Programming");
            ps.setString(2, "James Gosling");
            ps.setBoolean(3, true); // book is available

            ps.executeUpdate();

            System.out.println("Book added successfully");

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}