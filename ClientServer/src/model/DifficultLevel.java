
package model;

import controller.HardLevelFXMLController;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.shape.Line;

/**
 *
 * @author dell
 */
public class DifficultLevel {
    //check for win game
       public static int evaluate(Button b[][]) {
       // Checking for Rows for X or O 
        for (int row = 0; row < 3; row++) {
            if (b[row][0].getText().equals(b[row][1].getText()) && b[row][1].getText().equals(b[row][2].getText())) {
                switch (b[row][0].getText()) {
                    case "O":
                        return 10;
                        
                    case "X":
                        return -10;
                }
              
            }
        }

        // Checking for columns for X or O 
        for (int col = 0; col < 3; col++) {
            if (b[0][col].getText().equals(b[1][col].getText()) && b[1][col].getText().equals(b[2][col].getText())) {
                switch (b[0][col].getText()) {
                    case "O":
                        return 10;
                    case "X":
                        return -10;
                }
            }
        }

        // Checking for Diagonals for X or O
        if (b[0][0].getText().equals(b[1][1].getText()) && b[1][1].getText().equals(b[2][2].getText())) {
            switch (b[0][0].getText()) {
                case "O":
                    return 10;
                case "X":
                    return -10;
            }
        }

        if (b[0][2].getText().equals(b[1][1].getText()) && b[1][1].getText().equals(b[2][0].getText())) {
            switch (b[0][2].getText()) {
                case "O":
                    return 10;
                case "X":
                    return -10;
            }
        }
        return 0;
    }

    public static class Move {

        public int row, col;
    }

    static String player = "O", opponent = "X";

    public static Boolean isMoveLeft(Button board[][]) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].getText().equals("")) {
                    return true;
                }
            }
        }
        return false;
    }

    // This is the minimax function. 
    // the possible ways the game can go 
    static int minimax(Button board[][], int depth, Boolean isMax) {
        int score = evaluate(board);
        if (score == 10) {
            return score;
        }
        if (score == -10) {
            return score;
        }
        if (isMoveLeft(board) == false) {
            return 0;
        }

        if (isMax) {
            int best = -1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].getText().equals("")) {
                        board[i][j].setText(player);
                        best = Math.max(best, minimax(board, depth + 1, !isMax));
                        board[i][j].setText("");
                    }
                }
            }
            return best;

        } else {
            int best = 1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].getText().equals("")) {
                        board[i][j].setText(opponent);
                        best = Math.min(best, minimax(board, depth + 1, !isMax));
                        board[i][j].setText("");
                    }
                }
            }
            return best;
        }
    }

    //best move for computer
    public static Move findBestMove(Button board[][]) {
        int bestVal = -1000;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.col = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].getText().equals("")) {
                    board[i][j].setText(player);
                    int moveVal = minimax(board, 0, false);
                    board[i][j].setText("");
                    if (moveVal > bestVal) {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }
     
}
