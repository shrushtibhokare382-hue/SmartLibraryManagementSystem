import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class StatsGraph extends JPanel {

    private int totalBooks;
    private int issuedBooks;

    public StatsGraph(int total, int issued) {
        this.totalBooks = total;
        this.issuedBooks = issued;
    }
    
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        int available = totalBooks - issuedBooks;

        int max = Math.max(totalBooks, Math.max(issuedBooks, available));

        int width = getWidth();
        int height = getHeight();

        int barWidth = 80;

        int x1 = width / 4 - barWidth;
        int x2 = width / 2 - barWidth / 2;
        int x3 = 3 * width / 4 - barWidth;

        int scale = (height - 100) / (max == 0 ? 1 : max);

        int totalHeight = totalBooks * scale;
        int issuedHeight = issuedBooks * scale;
        int availableHeight = available * scale;

        // Total Books
        g.setColor(new Color(52,152,219));
        g.fillRect(x1, height - totalHeight - 50, barWidth, totalHeight);

        // Issued Books
        g.setColor(new Color(231,76,60));
        g.fillRect(x2, height - issuedHeight - 50, barWidth, issuedHeight);

        // Available Books
        g.setColor(new Color(46,204,113));
        g.fillRect(x3, height - availableHeight - 50, barWidth, availableHeight);

        g.setColor(Color.BLACK);

        g.drawString("Total", x1 + 20, height - 20);
        g.drawString("Issued", x2 + 20, height - 20);
        g.drawString("Available", x3 + 10, height - 20);

        g.drawString(String.valueOf(totalBooks), x1 + 25, height - totalHeight - 60);
        g.drawString(String.valueOf(issuedBooks), x2 + 25, height - issuedHeight - 60);
        g.drawString(String.valueOf(available), x3 + 25, height - availableHeight - 60);
    }
}