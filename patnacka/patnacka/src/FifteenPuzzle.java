import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class FifteenPuzzle extends JFrame {

    private JButton[][] puzzleButtons;
    private int[][] puzzle;
    private int emptyRow;
    private int emptyCol;


    public FifteenPuzzle() {
        setTitle("15 Puzzle");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        puzzleButtons = new JButton[4][4];
        puzzle = new int[4][4];

        initPuzzle();
        shuffle();

        JPanel puzzlePanel = new JPanel(new GridLayout(4, 4, 5, 5));
        changingColors(Color.PINK);
        setLocationRelativeTo(null);

        for (int i = 0; i < puzzleButtons.length; i++) {
            for (int j = 0; j < puzzleButtons[i].length; j++) {
                puzzlePanel.add(puzzleButtons[i][j]);
                int finalI = i;
                int finalJ = j;
                puzzleButtons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleClick(finalI, finalJ);
                    }
                });
            }
        }

        add(puzzlePanel);

        setVisible(true);
    }

    /**
     * This method is for chanhing the color.
     *
     * @param color is to set the color.
     */
    public void changingColors(Color color) {
        for (int i = 0; i < puzzleButtons.length; i++) {
            for (int j = 0; j < puzzleButtons[i].length; j++) {
                puzzleButtons[i][j].setBackground(color);
            }
        }
    }
    /**
     * This method will give the puzzle the size and font of numbers.
     */
    public void initPuzzle() {
        int count = 1;
        Font font = new Font("Arial", Font.PLAIN, 20);
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                puzzle[i][j] = count++;
            }
        }
        puzzle[3][3] = 0;
        emptyRow = 3;
        emptyCol = 3;

        for (int i = 0; i < puzzleButtons.length; i++) {
            for (int j = 0; j < puzzleButtons[i].length; j++) {
                puzzleButtons[i][j] = new JButton(String.valueOf(puzzle[i][j]));
                puzzleButtons[i][j].setFont(font);

            }
        }
    }


    /**
     * This method is for movement, to move the tiles to the empty space.
     *
     * @param newRow
     * @param newCol
     */
    public void movement(int newRow, int newCol) {
        if (newRow >= 0 && newRow < 4 && newCol >= 0 && newCol < 4) {
            puzzleButtons[emptyRow][emptyCol].setText(puzzleButtons[newRow][newCol].getText());
            puzzleButtons[emptyRow][emptyCol].setVisible(true);
            puzzleButtons[newRow][newCol].setText("");
            puzzleButtons[newRow][newCol].setVisible(false);
            emptyRow = newRow;
            emptyCol = newCol;
        }
    }

    /**
     * This method will shuffle the puzzle.
     */
    public void shuffle() {
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            int randomMove = rand.nextInt(4);
            switch (randomMove) {
                case 0: // Up
                    movement(emptyRow - 1, emptyCol);
                    break;
                case 1: // Down
                    movement(emptyRow + 1, emptyCol);
                    break;
                case 2: // Left
                    movement(emptyRow, emptyCol - 1);
                    break;
                case 3: // Right
                    movement(emptyRow, emptyCol + 1);
                    break;
            }
        }
    }




    /**
     * This method will check if the puzzle is solved.
     *
     * @return True if the puzzle is solved.
     */
    public boolean isSolved() {
        int count = 1;
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                if (puzzle[i][j] != count++ && !(count == 15 && puzzle[i][j] == 0)) {
                    return false;
                }
            }
        }
        return true;

    }

    /**
     * This method will handle button click events and checks for puzzle completion.
     * @param row index of the clicked button.
     * @param col index of the clicked button.
     */
    public void handleClick(int row, int col) {
        if ((row == emptyRow && Math.abs(col - emptyCol) == 1) ||
                (col == emptyCol && Math.abs(row - emptyRow) == 1)) {
            movement(row, col);
        }

        if (isSolved()) {
            JOptionPane.showMessageDialog(FifteenPuzzle.this, "Congratulations! You solved the puzzle.");
        }
    }


}
