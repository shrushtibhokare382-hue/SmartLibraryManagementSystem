import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookDAO {

        public static void addBook(String title,String author)
        {
        String sql = "INSERT INTO books (title, author, available) VALUES (?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, author);
            ps.setBoolean(3, true); // book available by default

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Book added successfully");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showAvailableBooks() {

    String sql = "SELECT * FROM books WHERE available = true";

    try {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        System.out.println("---- Available Books ----");

        while (rs.next()) {
            System.out.println(
                    "Book ID: " + rs.getInt("book_id") +
                    ", Title: " + rs.getString("title") +
                    ", Author: " + rs.getString("author")
            );
        }

        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static String getAvailableBooksText() {
    
    StringBuilder sb = new StringBuilder();
    String sql = "SELECT * FROM books WHERE available = true";

    try {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        boolean found=false;
        while (rs.next()) {
            found=true;
            sb.append("Book ID: ")
              .append(rs.getInt("book_id"))
              .append(", Title: ")
              .append(rs.getString("title"))
              .append(", Author: ")
              .append(rs.getString("author"))
              .append("\n");
        }

        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    if (sb.length()==0) {
        return "No books available";
    }

    return sb.toString();
}
}