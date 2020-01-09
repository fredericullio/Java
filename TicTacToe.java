import java.util.ArrayList;
import java.util.Scanner;

//The game accepts input in a form of two integers: a column number and a row number.

public class TicTacToe{
    static Scanner input = new Scanner(System.in);
    static char[] marks = new char[] {'X', 'O'};

    public static void main(String[] args) {

        char[][] marksBoard = {
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };
        printBoard(marksBoard);

        char currentPlayer = Math.random() > 0.5 ? 'X' : 'O';

        while (true) {
            placeMarker(marksBoard, currentPlayer);
            if (isWinner(marksBoard, currentPlayer)) {
                System.out.println(currentPlayer + " wins");
                break;
            }
            if (!areEmptySlots(marksBoard)) {
                System.out.println("Draw");
                break;
            }
            currentPlayer = currentPlayer == marks[0] ? marks[1] : marks[0];
        }
    }

    private static void placeMarker(char[][] marks, char currentPlayer) {
        int cols;
        int rows;

        while (true) {
            System.out.println("Enter the coordinates:");

            if (input.hasNextInt()) {
                cols = input.nextInt();
            } else {
                System.out.println("You should enter numbers!");
                continue;
            }
            if (input.hasNextInt()) {
                rows = input.nextInt();
            } else {
                System.out.println("You should enter numbers!");
                continue;
            }
            if (cols>3 || cols<1 || rows>3 || rows<1) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }
            if (marks[rows-1][cols-1]!=' ') {
                System.out.println("This cell is occupied! Choose another one!");
            } else {
                marks[rows-1][cols-1] = currentPlayer;
                printBoard(marks);
                break;
            }
        }
    }

    private static void printBoard(char[][] marks) {
        System.out.println("---------");
        for (int i=marks.length-1; i>=0; i--) {
            System.out.print("| ");
            for (int j=0; j<marks[i].length; j++) {
                System.out.print(marks[i][j] + " ");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("---------");
    }

    private static boolean isWinner(char[][] marks, char ch) {
        ArrayList<String> markList = new ArrayList<>();
        boolean isWinner = false;
        String[][] wins = {
                {"02", "11", "20"},
                {"00", "11", "22"},
                {"00", "01", "02"},
                {"10", "11", "12"},
                {"20", "21", "22"},
                {"00", "10", "20"},
                {"01", "11", "21"},
                {"02", "12", "22"}
        };

        for (int i=0; i<marks.length; i++) {
            for (int j=0; j<marks[i].length; j++){
                if (marks[i][j]==ch) {
                    markList.add(""+i+j);
                }
            }

        }
        for (String[] win : wins) {
            isWinner = true;
            for (String i : win) {
                if (!markList.contains(i)) {
                    isWinner = false;
                    break;
                }
            }
            if (isWinner) {
                break;
            }
        }
        return isWinner;
    }

    private static boolean areEmptySlots(char[][] marks) {
        boolean areEmptySlots = false;
        for (char[] mark : marks) {
            for (char c : mark) {
                if (c == ' ') {
                    areEmptySlots = true;
                    break;
                }
            }
        }
        return areEmptySlots;
    }

}