public class TestConnection {

    public static void main(String[] args) {

        // Add book
        BookDAO.addBook("Clean Code", "Robert C. Martin");

        // Issue book
        IssueBookDAO.issueBook(1, 1);

        // Show issued books
        System.out.println(IssuedBooksDAO.getIssuedBooks());

        // Return book
        ReturnBookDAO.returnBook(1, 1);

        // Calculate fine
        int fine = FineCalculatorDAO.calculateFine(1, 1);
        System.out.println("Fine: ₹" + fine);

        // Show available books
        BookDAO.showAvailableBooks();
    }
}