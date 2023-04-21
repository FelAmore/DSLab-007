import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe extends JFrame implements ActionListener{
//    xScore = player x's score
//    oScore = player o's score
//    moveCounter = counts the num of moves (to determine if the game is draw)
    private int xScore, oScore, moveCounter;
//    to determine current player
    private boolean playerX;
    private JLabel turnLbl, scoreLbl, resultLbl;
    private JButton[][] board;
    private JDialog rsltDialog;

    public TicTacToe() {
        super("Tic-Tac-Toe");
        setSize(CommonConstants.FRAME_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(CommonConstants.BG_COLOR);

//        Initializing Variables
        createRsltDialog();
        board = new JButton[3][3];

//        Player X starts first
        playerX = true;

        addComponent();
    }

    private void addComponent() {
//        Bar Label
        JLabel barLbl = new JLabel();
        barLbl.setOpaque(true);
        barLbl.setBackground(CommonConstants.BAR_COLOR);
        barLbl.setBounds(0, 0, CommonConstants.FRAME_SIZE.width, 25);


//        Turn Label
        turnLbl = new JLabel(CommonConstants.X_LBL);
        turnLbl.setHorizontalAlignment(SwingConstants.CENTER);
        turnLbl.setFont(new Font("Dialog", Font.CENTER_BASELINE, 40));
        turnLbl.setPreferredSize(new Dimension(100, turnLbl.getPreferredSize().height));
        turnLbl.setOpaque(true);
        turnLbl.setBackground(CommonConstants.X_COLOR);
        turnLbl.setForeground(CommonConstants.BOARD_COLOR);
        turnLbl.setBounds(
                (CommonConstants.FRAME_SIZE.width - turnLbl.getPreferredSize().width)/2,
                0,
                turnLbl.getPreferredSize().width,
                turnLbl.getPreferredSize().height);

//      Score Label
        scoreLbl = new JLabel(CommonConstants.SCORE_LBL);
        scoreLbl.setFont(new Font("Dialog", Font.CENTER_BASELINE, 40));
        scoreLbl.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLbl.setOpaque(true);
        scoreLbl.setBackground(CommonConstants.BG_COLOR);
        scoreLbl.setForeground(CommonConstants.TEXT_COLOR);
        scoreLbl.setBounds(
                0,
                turnLbl.getY() + turnLbl.getPreferredSize().height + 25,
                CommonConstants.FRAME_SIZE.width,
                scoreLbl.getPreferredSize().height);

//        Game Board
        GridLayout gridLayout = new GridLayout(3, 3);
        JPanel boardPanel = new JPanel(gridLayout);
        boardPanel.setBackground(CommonConstants.BOARD_COLOR.brighter());
        boardPanel.setBounds(
                10,
                scoreLbl.getY() + scoreLbl.getPreferredSize().height + 35,
                CommonConstants.BOARD_SIZE.width,
                CommonConstants.BOARD_SIZE.height);

//        Creating the Board
        for (int i=0; i<board.length; i++) {
            for (int j=0; j<board[i].length; j++) {
                JButton btn = new JButton();
                btn.setFont(new Font("Dialog", Font.PLAIN, 180));
                btn.setPreferredSize(CommonConstants.BTN_SIZE);
                btn.setBackground(CommonConstants.BOARD_COLOR);
                btn.addActionListener(this);
                btn.setBorder(BorderFactory.createLineBorder(CommonConstants.BG_COLOR));

//                Adding Button
                board[i][j] = btn;
                boardPanel.add(board[i][j]);
            }
        }

//        Reset Button
        JButton resetBtn = new JButton("Reset");
        resetBtn.setFont(new Font("Dialog", Font.CENTER_BASELINE, 24));
        resetBtn.addActionListener(this);
        resetBtn.setBackground(CommonConstants.BOARD_COLOR);
        resetBtn.setBounds(
                (CommonConstants.FRAME_SIZE.width - resetBtn.getPreferredSize().width)/2,
                CommonConstants.FRAME_SIZE.height - 110,
                resetBtn.getPreferredSize().width,
                resetBtn.getPreferredSize().height);

        getContentPane().add(turnLbl);
        getContentPane().add(barLbl);
        getContentPane().add(scoreLbl);
        getContentPane().add(boardPanel);
        getContentPane().add(resetBtn);
    }

    private void createRsltDialog() {
        rsltDialog = new JDialog();
        rsltDialog.getContentPane().setBackground((CommonConstants.BG_COLOR));
        rsltDialog.setResizable(false);
        rsltDialog.setTitle("Result");
        rsltDialog.setSize(CommonConstants.RESULT_DIALOG_SIZE);
        rsltDialog.setLocationRelativeTo(this);
        rsltDialog.setModal(true);
        rsltDialog.setLayout(new GridLayout(2, 1));
        rsltDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                resetGame();
            }
        });

//        Result Label
        resultLbl = new JLabel();
        resultLbl.setFont(new Font("Dialog", Font.CENTER_BASELINE, 18));
        resultLbl.setForeground(CommonConstants.TEXT_COLOR);
        resultLbl.setHorizontalAlignment(SwingConstants.CENTER);

//        Restart Button
        JButton restartBtn = new JButton("Play Again");
        restartBtn.setBackground(CommonConstants.BOARD_COLOR);
        restartBtn.addActionListener(this);
        restartBtn.setFont(new Font("Dialog", Font.HANGING_BASELINE, 18));

        rsltDialog.add(resultLbl);
        rsltDialog.add(restartBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Reset") || command.equals("Play Again")) {
//            Reset the Game
            resetGame();

//            Reset the score
            if (command.equals("Reset"))
                xScore = oScore = 0;

            if (command.equals("Play Again"))
                rsltDialog.setVisible(false);
        }
        else {
//            Player Move
            JButton btn = (JButton) e.getSource();
            if(btn.getText().equals("")) {
                moveCounter++;

//                Mark the button with X or O onli if it hasn't been selected yet
                if (playerX) {
//                    Player X
                    btn.setText(CommonConstants.X_LBL);
                    btn.setForeground(CommonConstants.X_COLOR);

//                     Update turn label
                    turnLbl.setText(CommonConstants.O_LBL);
                    turnLbl.setBackground(CommonConstants.O_COLOR);

//                     Update turn
                    playerX = false;
                } else {
//                    Player O
                    btn.setText(CommonConstants.O_LBL);
                    btn.setForeground(CommonConstants.O_COLOR);

//                     Update turn label
                    turnLbl.setText(CommonConstants.X_LBL);
                    turnLbl.setBackground(CommonConstants.X_COLOR);

//                     Update turn
                    playerX = true;
                }

//                Checking win conditions
                if (playerX) {
//                    Check if player O won the game
                    checkWinO();
                } else {
//                    Check if player X won the game
                    checkWinX();
                }

//                Check if the game is draw
                checkDraw();

//                Update Score Label
                scoreLbl.setText("X:" + xScore + " | O:" + oScore);
            }

            repaint();
            revalidate();
        }
    }

    private void checkWinX() {
        String result = "X wins the game!";

//        Check the rows
        for (int row=0; row<board.length; row++) {
            if (board[row][0].getText().equals("X") && board[row][1].getText().equals("X") && board[row][2].getText().equals("X")) {
                resultLbl.setText(result);

//                 Display the result dialog
                rsltDialog.setVisible(true);

//                 Update the score
                xScore++;
            }
        }

//        Check the columns
        for (int col=0; col<board.length; col++) {
            if (board[0][col].getText().equals("X") && board[1][col].getText().equals("X") && board[2][col].getText().equals("X")) {
                resultLbl.setText(result);

//                 Display the result dialog
                rsltDialog.setVisible(true);

//                 Update the score
                xScore++;
            }
        }

//        Check the diagonals
        if (board[0][0].getText().equals("X") && board[1][1].getText().equals("X") && board[2][2].getText().equals("X")) {
            resultLbl.setText(result);

//             Display the result dialog
            rsltDialog.setVisible(true);

//             Update the score
            xScore++;
        }

        if (board[2][0].getText().equals("X") && board[1][1].getText().equals("X") && board[0][2].getText().equals("X")) {
            resultLbl.setText(result);

//             Display the result dialog
            rsltDialog.setVisible(true);

//             Update the score
            xScore++;
        }
    }

    private void checkWinO() {
        String result = "O wins the game!";

//         Check the rows
        for (int row=0; row<board.length; row++) {
            if (board[row][0].getText().equals("O") && board[row][1].getText().equals("O") && board[row][2].getText().equals("O")) {
                resultLbl.setText(result);

//                 Display the result dialog
                rsltDialog.setVisible(true);

//                 Update the score
                oScore++;
            }
        }

//        Check the columns
        for (int col=0; col<board.length; col++) {
            if (board[0][col].getText().equals("O") && board[1][col].getText().equals("O") && board[2][col].getText().equals("O")) {
                resultLbl.setText(result);

//                 Display the result dialog
                rsltDialog.setVisible(true);

//                 Update the score
                oScore++;
            }
        }

//        Check the diagonals
        if (board[0][0].getText().equals("O") && board[1][1].getText().equals("O") && board[2][2].getText().equals("O")) {
            resultLbl.setText(result);

//             Display the result dialog
            rsltDialog.setVisible(true);

//             Update the score
            oScore++;
        }

        if (board[2][0].getText().equals("O") && board[1][1].getText().equals("O") && board[0][2].getText().equals("O")) {
            resultLbl.setText(result);

//             Display the result dialog
            rsltDialog.setVisible(true);

//             Update the score
            oScore++;
        }
    }

    private void checkDraw() {
//        if players ran out of moves but no one has won yet
        if (moveCounter >= 9) {
            resultLbl.setText("Draw!!");
            rsltDialog.setVisible(true);
        }
    }

    private void resetGame() {
//        Reset player back to player X
        playerX = true;
        turnLbl.setText(CommonConstants.X_LBL);
        turnLbl.setBackground(CommonConstants.X_COLOR);

//        Reset the score display
        scoreLbl.setText(CommonConstants.SCORE_LBL);

//        Reset the move counter
        moveCounter = 0;

//        Reset the board
        for (int i=0; i<board.length; i++) {
            for (int j=0; j<board[i].length; j++) {
                board[i][j].setText("");
            }
        }
    }
}
