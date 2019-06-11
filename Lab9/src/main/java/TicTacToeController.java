import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Scanner;

public class TicTacToeController {
    @FXML
    private Button button0;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button button5;
    @FXML
    private Button button6;
    @FXML
    private Button button7;
    @FXML
    private Button button8;
    @FXML
    private Button button9;
    @FXML
    private Button button10;
    @FXML
    private Button button11;
    @FXML
    private Button button12;
    @FXML
    private Button button13;
    @FXML
    private Button button14;
    @FXML
    private Button button15;
    @FXML
    private RadioButton radioButtonHumanVsHuman;
    @FXML
    private RadioButton radioButtonHumanVsAI;
    @FXML
    private RadioButton radioButtonAIVsAI;
    @FXML
    private RadioButton radioButtonEasy;
    @FXML
    private RadioButton radioButtonHard;
    @FXML
    private Button buttonStartStop;
    @FXML
    private Label labelInfo;

    private Main main;
    private boolean isButtonStartStopPushed = false;
    private ArrayList<Button> arrayListButtons = new ArrayList<Button>();
    private ArrayList<RadioButton> arrayListRadioButtons = new ArrayList<RadioButton>();
    private ArrayList<ImageView> viewX = new ArrayList<>();
    private ArrayList<ImageView> viewO = new ArrayList<>();
    private boolean turn = false;
    private int move = 0;
    private int gameMode = 0;
    private char[] board = {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '};
    private boolean[] hasBeenClicked = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    private EventHandler<MouseEvent> eventHandlerButton = event -> {
        makeNextMove(((Button)event.getSource()).getId());
    };

    public void initialize(){
        for(int i = 0; i < 8; i++) {
            ImageView ivx = new ImageView(new Image(getClass().getResourceAsStream("/icons/iconX.png")));
            ImageView ivo = new ImageView(new Image(getClass().getResourceAsStream("/icons/iconO.png")));
            ivx.setFitWidth(30);
            ivx.setFitHeight(30);
            ivo.setFitWidth(30);
            ivo.setFitHeight(30);
            viewX.add(ivx);
            viewO.add(ivo);
        }

        arrayListButtons.add(button0);
        arrayListButtons.add(button1);
        arrayListButtons.add(button2);
        arrayListButtons.add(button3);
        arrayListButtons.add(button4);
        arrayListButtons.add(button5);
        arrayListButtons.add(button6);
        arrayListButtons.add(button7);
        arrayListButtons.add(button8);
        arrayListButtons.add(button9);
        arrayListButtons.add(button10);
        arrayListButtons.add(button11);
        arrayListButtons.add(button12);
        arrayListButtons.add(button13);
        arrayListButtons.add(button14);
        arrayListButtons.add(button15);

        arrayListRadioButtons.add(radioButtonHumanVsHuman);
        arrayListRadioButtons.add(radioButtonHumanVsAI);
        arrayListRadioButtons.add(radioButtonAIVsAI);
        arrayListRadioButtons.add(radioButtonEasy);
        arrayListRadioButtons.add(radioButtonHard);

        for(Button b : arrayListButtons) {
            b.setDisable(true);
            b.setOnMouseClicked(eventHandlerButton);
        }
    }

    public void actionStartStop(){
        isButtonStartStopPushed = !isButtonStartStopPushed;

        if(isButtonStartStopPushed){
            for(Button b : arrayListButtons)
                b.setGraphic(null);

            for(int i = 0; i < 16; i++) {
                hasBeenClicked[i] = false;
                board[i] = ' ';
            }

            for(Button b : arrayListButtons) b.setDisable(false);
            for(RadioButton rb : arrayListRadioButtons) rb.setDisable(true);
            buttonStartStop.setText("Stop");
            labelInfo.setText("Gracz X");
            if(radioButtonHumanVsHuman.isSelected())
                gameMode = 0;
            else if(radioButtonHumanVsAI.isSelected())
                gameMode = 1;
            else {
                gameMode = 2;
                makeNextMove("");
            }
        }
        else{
            for(RadioButton rb : arrayListRadioButtons) rb.setDisable(false);
            buttonStartStop.setText("Start");
            if(move < 16)
                labelInfo.setText("");
        }

        turn = false;
        move = 0;
    }

    private void makeNextMove(String id){
        int iterator = -1;
        if(turn){
            for (Button b : arrayListButtons) {
                iterator++;
                if (b.getId().equals(id) && !hasBeenClicked[iterator]) {
                    hasBeenClicked[iterator] = true;
                    b.setGraphic(viewO.get(move / 2));
                    board[iterator] = 'O';
                    labelInfo.setText("Gracz X");
                    move++;
                    turn = !turn;
                    break;
                }
            }
            System.out.println(iterator);
        }
        else{
            for (Button b : arrayListButtons) {
                iterator++;
                if (b.getId().equals(id) && !hasBeenClicked[iterator]) {
                    hasBeenClicked[iterator] = true;
                    b.setGraphic(viewX.get(move / 2));
                    board[iterator] = 'X';
                    labelInfo.setText("Gracz O");
                    move++;
                    turn = !turn;
                    break;
                }
            }
        }

        System.out.println();
        for(int j = 0; j < 16; j++){
                if(j % 4 == 0 && j > 0)System.out.println("");
                System.out.print(board[j]+",");
            }
        if(checkIfWon()) {
            for(Button b : arrayListButtons)
                b.setDisable(true);
            return;
        }

        if(gameMode == 1 && move % 2 == 1){
            int nextMove = main.moveAIJS(board, 'O');
            makeNextMove("button" + nextMove);
        }
        else if(gameMode == 2)
            if(move % 2 == 0){
                int nextMove = main.moveAIJS(board, 'X');
                makeNextMove("button" + nextMove);
            }
            else{
                int nextMove = main.moveAIJS(board, 'O');
                makeNextMove("button" + nextMove);
            }

    }

    private boolean checkIfWon(){
        if(move == 16){
            labelInfo.setText("Remis!");
            actionStartStop();
            return true;
        }

        if((board[0] == 'X' && board[1] == 'X' && board[2] == 'X') ||
            (board[1] == 'X' && board[2] == 'X' && board[3] == 'X') ||
            (board[4] == 'X' && board[5] == 'X' && board[6] == 'X') ||
            (board[5] == 'X' && board[6] == 'X' && board[7] == 'X') ||
            (board[8] == 'X' && board[9] == 'X' && board[10] == 'X') ||
            (board[9] == 'X' && board[10] == 'X' && board[11] == 'X') ||
            (board[12] == 'X' && board[13] == 'X' && board[14] == 'X') ||
            (board[13] == 'X' && board[14] == 'X' && board[15] == 'X') ||
            (board[0] == 'X' && board[4] == 'X' && board[8] == 'X') ||
            (board[4] == 'X' && board[8] == 'X' && board[12] == 'X') ||
            (board[1] == 'X' && board[5] == 'X' && board[9] == 'X') ||
            (board[5] == 'X' && board[9] == 'X' && board[13] == 'X') ||
            (board[2] == 'X' && board[6] == 'X' && board[10] == 'X') ||
            (board[6] == 'X' && board[10] == 'X' && board[14] == 'X') ||
            (board[3] == 'X' && board[7] == 'X' && board[11] == 'X') ||
            (board[7] == 'X' && board[11] == 'X' && board[15] == 'X') ||
            (board[1] == 'X' && board[6] == 'X' && board[11] == 'X') ||
            (board[0] == 'X' && board[5] == 'X' && board[10] == 'X') ||
            (board[5] == 'X' && board[10] == 'X' && board[15] == 'X') ||
            (board[2] == 'X' && board[5] == 'X' && board[8] == 'X') ||
            (board[3] == 'X' && board[6] == 'X' && board[9] == 'X') ||
            (board[6] == 'X' && board[9] == 'X' && board[12] == 'X') ||
            (board[7] == 'X' && board[10] == 'X' && board[13] == 'X')) {
            labelInfo.setText("Gracz X wygrał!");

            return true;
        }
        else if((board[0] == 'O' && board[1] == 'O' && board[2] == 'O') ||
            (board[1] == 'O' && board[2] == 'O' && board[3] == 'O') ||
            (board[4] == 'O' && board[5] == 'O' && board[6] == 'O') ||
            (board[5] == 'O' && board[6] == 'O' && board[7] == 'O') ||
            (board[8] == 'O' && board[9] == 'O' && board[10] == 'O') ||
            (board[9] == 'O' && board[10] == 'O' && board[11] == 'O') ||
            (board[12] == 'O' && board[13] == 'O' && board[14] == 'O') ||
            (board[13] == 'O' && board[14] == 'O' && board[15] == 'O') ||
            (board[0] == 'O' && board[4] == 'O' && board[8] == 'O') ||
            (board[4] == 'O' && board[8] == 'O' && board[12] == 'O') ||
            (board[1] == 'O' && board[5] == 'O' && board[9] == 'O') ||
            (board[5] == 'O' && board[9] == 'O' && board[13] == 'O') ||
            (board[2] == 'O' && board[6] == 'O' && board[10] == 'O') ||
            (board[6] == 'O' && board[10] == 'O' && board[14] == 'O') ||
            (board[3] == 'O' && board[7] == 'O' && board[11] == 'O') ||
            (board[7] == 'O' && board[11] == 'O' && board[15] == 'O') ||
            (board[1] == 'O' && board[6] == 'O' && board[11] == 'O') ||
            (board[0] == 'O' && board[5] == 'O' && board[10] == 'O') ||
            (board[5] == 'O' && board[10] == 'O' && board[15] == 'O') ||
            (board[4] == 'O' && board[9] == 'O' && board[14] == 'O') ||
            (board[2] == 'O' && board[5] == 'O' && board[8] == 'O') ||
            (board[3] == 'O' && board[6] == 'O' && board[9] == 'O') ||
            (board[6] == 'O' && board[9] == 'O' && board[12] == 'O') ||
            (board[7] == 'O' && board[10] == 'O' && board[13] == 'O')) {
            labelInfo.setText("Gracz O wygrał!");

            return true;
      }


        return false;
    }

    public void setMain(Main main){
        this.main = main;

//        for(int i = 0; i < 3; i++){
//            board = main.moveAI(board, 'O');
//            board = main.moveAI(board, 'X');
//            System.out.println("");
//            for(int j = 0; j < 16; j++){
//                if(j % 4 == 0 && j > 0)System.out.println("");
//                System.out.print(board[j]+",");
//            }
//        }
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public char[] getBoard() {
        return board;
    }

    public void setBoard(char[] board) {
        this.board = board;
    }
}
