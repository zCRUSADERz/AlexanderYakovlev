package ru.job4j.loop;

/**
 * Board.
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 1.01.2018
 * @version 1.0
 */
public class Board {

    /**
     * Paint chessboard.
     * @param width - width chessboard.
     * @param height - height chessboard.
     * @return - string chessboard.
     */
    public String paint(int width, int height) {
        StringBuilder board = new StringBuilder();
        String ln = System.lineSeparator();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (((i + j) % 2) == 0) {
                    board.append("X");
                } else {
                    board.append(" ");
                }
            }
            board.append(ln);
        }
        return board.toString();
    }
}
