package homework;

import java.util.Arrays;
import java.util.Scanner;

public class TicTacToe {
    private static final char SIGN_USER = 'X';
    private static final char SIGN_COMPUTER = 'O';
    private static final char SIGN_EMPTY = '.';
    private static final int WIN_COUNT = 5;

    private static Scanner scanner = new Scanner(System.in);

    private static char[][] field;
    private static int fieldSizeX;
    private static int fieldSizeY;
    private static int amountOfFreeCell;

    public static void main(String[] args) {
        TicTacToe ticTacToe = new TicTacToe();
        ticTacToe.play();
    }

    public void play() {
        do {
            System.out.print("Enter size of the field: x = ");
            fieldSizeX = scanner.nextInt();
            System.out.print("y = ");
            fieldSizeY = scanner.nextInt();
            initField();
            printField();
            while (amountOfFreeCell != 0) {
                userTurn();
                amountOfFreeCell--;
                if (amountOfFreeCell == 0) {
                    break;
                }
                printField();
                if (checkWin(SIGN_USER)) {
                    System.out.println("You win!");
                    break;
                }
                computerTurn();
                amountOfFreeCell--;
                printField();
                if (checkWin(SIGN_COMPUTER)) {
                    System.out.println("Computer win!");
                    break;
                }
            }
            if (amountOfFreeCell == 0)
                System.out.println("Standoff!");
            System.out.println("Do you wanna play the game one more time? y/n");
        } while (scanner.next().toLowerCase().equals("y"));
    }

    private static void userTurn() {
        int x, y;
        do {
            System.out.print("Please enter coordinates of your turn x & y split by whitespace >>> ");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (!isCellValid(y, x) || !isCellEmpty(y, x));
        field[y][x] = SIGN_USER;
    }

    private static void computerTurn() {
        for (int i = 1; i <= WIN_COUNT; i++) {
            if (i == WIN_COUNT) {
                int x, y;
                do {
                    x = (int) (Math.random() * fieldSizeX);
                    y = (int) (Math.random() * fieldSizeY);
                } while (!isCellEmpty(y, x));
                field[y][x] = SIGN_COMPUTER;
            } else {
                if (findEmptyCellByRow(SIGN_COMPUTER, i)) {
                    return;
                } else if (findEmptyCellByCol(SIGN_COMPUTER, i)) {
                    return;
                } else if (findEmptyCellByMainDiagonal(SIGN_COMPUTER, i)) {
                    return;
                } else if (findEmptyCellBySecondaryDiagonal(SIGN_COMPUTER, i)) {
                    return;
                } else if (findEmptyCellByRow(SIGN_USER, i)) {
                    return;
                } else if (findEmptyCellByCol(SIGN_USER, i)) {
                    return;
                } else if (findEmptyCellByMainDiagonal(SIGN_USER, i)) {
                    return;
                } else if (findEmptyCellBySecondaryDiagonal(SIGN_USER, i)) {
                    return;
                }
            }
        }
    }

    public static boolean findEmptyCellByRow(char sign, int amtEmptyCell) {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX - WIN_COUNT + 1; j++) {
                int count = 0;
                int indexEmptyCellX = 0;
                int indexEmptyCellY = 0;
                int countEmptyCell = 0;
                for (int k = j; k < WIN_COUNT + j; k++) {
                    if (field[i][k] == sign) {
                        count++;
                    }
                    if (field[i][k] == SIGN_EMPTY) {
                        indexEmptyCellX = k;
                        indexEmptyCellY = i;
                        countEmptyCell++;
                    }
                }

                if (count == WIN_COUNT - amtEmptyCell && countEmptyCell == amtEmptyCell) {
                    field[indexEmptyCellY][indexEmptyCellX] = SIGN_COMPUTER;
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean findEmptyCellByCol(char sign, int amtEmptyCell) {
        for (int i = 0; i < fieldSizeY - WIN_COUNT + 1; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                int count = 0;
                int indexEmptyCellX = 0;
                int indexEmptyCellY = 0;
                int countEmptyCell = 0;
                for (int k = i; k < WIN_COUNT + i; k++) {
                    if (field[k][j] == sign) {
                        count++;
                    }
                    if (field[k][j] == SIGN_EMPTY) {
                        indexEmptyCellX = j;
                        indexEmptyCellY = k;
                        countEmptyCell++;
                    }
                }

                if (count == WIN_COUNT - amtEmptyCell && countEmptyCell == amtEmptyCell) {
                    field[indexEmptyCellY][indexEmptyCellX] = SIGN_COMPUTER;
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean findEmptyCellByMainDiagonal(char sign, int amtEmptyCell) {
        for (int i = 0; i < fieldSizeY - WIN_COUNT + 1; i++) {
            for (int j = 0; j < fieldSizeX - WIN_COUNT + 1; j++) {
                int count = 0;
                int indexEmptyCellX = 0;
                int indexEmptyCellY = 0;
                int countEmptyCell = 0;
                for (int k1 = i, k2 = j; k1 < WIN_COUNT + i; k1++, k2++) {
                    if (field[k1][k2] == sign) {
                        count++;
                    }
                    if (field[k1][k2] == SIGN_EMPTY) {
                        indexEmptyCellX = k2;
                        indexEmptyCellY = k1;
                        countEmptyCell++;
                    }
                }

                if (count == WIN_COUNT - amtEmptyCell && countEmptyCell == amtEmptyCell) {
                    field[indexEmptyCellY][indexEmptyCellX] = SIGN_COMPUTER;
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean findEmptyCellBySecondaryDiagonal(char sign, int amtEmptyCell) {
        for (int i = 0; i < fieldSizeY - WIN_COUNT + 1; i++) {
            for (int j = fieldSizeX - 1; j >= WIN_COUNT - 1; j--) {
                int count = 0;
                int indexEmptyCellX = 0;
                int indexEmptyCellY = 0;
                int countEmptyCell = 0;
                for (int k1 = i, k2 = j; k1 < WIN_COUNT + i; k1++, k2--) {
                    if (field[k1][k2] == sign) {
                        count++;
                    }
                    if (field[k1][k2] == SIGN_EMPTY) {
                        indexEmptyCellX = k2;
                        indexEmptyCellY = k1;
                        countEmptyCell++;
                    }
                }

                if (count == WIN_COUNT - amtEmptyCell && countEmptyCell == amtEmptyCell) {
                    field[indexEmptyCellY][indexEmptyCellX] = SIGN_COMPUTER;
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkWin(char sign) {
        if (findWinnerByRow(sign)) {
            return true;
        } else if (findWinnerByCol(sign)) {
            return true;
        } else if (findWinnerByMainDiagonal(sign)) {
            return true;
        } else if (findWinnerBySecondaryDiagonal(sign)) {
            return true;
        }
        return false;
    }

    private static boolean findWinnerByRow(char sign) {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX - WIN_COUNT + 1; j++) {
                int count = 0;
                for (int k = j; k < WIN_COUNT + j; k++) {
                    if (field[i][k] == sign) {
                        count++;
                    }
                }
                if (count == WIN_COUNT) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean findWinnerByCol(char sign) {
        for (int i = 0; i < fieldSizeY - WIN_COUNT + 1; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                int count = 0;
                for (int k = i; k < WIN_COUNT + i; k++) {
                    if (field[k][j] == sign) {
                        count++;
                    }
                }
                if (count == WIN_COUNT) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean findWinnerByMainDiagonal(char sign) {
        for (int i = 0; i < fieldSizeY - WIN_COUNT + 1; i++) {
            for (int j = 0; j < fieldSizeX - WIN_COUNT + 1; j++) {
                int count = 0;
                for (int k1 = i, k2 = j; k1 < WIN_COUNT + i; k1++, k2++) {
                    if (field[k1][k2] == sign) {
                        count++;
                    }
                }
                if (count == WIN_COUNT) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean findWinnerBySecondaryDiagonal(char sign) {
        for (int i = 0; i < fieldSizeY - WIN_COUNT + 1; i++) {
            for (int j = fieldSizeX - 1; j >= WIN_COUNT - 1; j--) {
                int count = 0;
                for (int k1 = i, k2 = j; k1 < WIN_COUNT + i; k1++, k2--) {
                    if (field[k1][k2] == sign) {
                        count++;
                    }
                }
                if (count == WIN_COUNT) {
                    return true;
                }
            }
        }
        return false;
    }


    private static void initField() {
        field = new char[fieldSizeY][fieldSizeX];
        amountOfFreeCell = fieldSizeX * fieldSizeY;
        for (int y = 0; y < fieldSizeY; y++) {
            Arrays.fill(field[y], SIGN_EMPTY);
        }
    }

    private static void printField() {
        System.out.print("+");
        for (int i = 0; i < fieldSizeX * 2 + 1; i++) {
            System.out.print(i % 2 == 0 ? "-" : i / 2 + 1);
        }
        System.out.println();
        for (int i = 0; i < fieldSizeY; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < fieldSizeX; j++) {
                System.out.print(field[i][j] + "|");
            }
            System.out.println();
        }
        System.out.println();
    }


    private static boolean isCellValid(int y, int x) {
        return x >= 0 && y >= 0 && x < fieldSizeX && y < fieldSizeY;
    }

    private static boolean isCellEmpty(int y, int x) {
        return field[y][x] == SIGN_EMPTY;
    }
}
