package ru.job4j.chessboard;

/**
 * Chess board.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 7.01.2017
 * @version 1.0
 */
public class Board {
    /**
     * Board.
     */
    final Figure[][] figures = new Figure[8][8];

    /**
     * Move figure.
     * @param source - source cell.
     * @param dest - destination cell.
     * @return - true if figure move on destination cell.
     * @throws ImposibleMoveException - if this way impossible.
     * @throws OccupiedWayException - if there is another figure on the way.
     * @throws FigureNotFoundException - if figure not found.
     */
    public boolean move(Cell source, Cell dest)
            throws ImposibleMoveException, OccupiedWayException, FigureNotFoundException {
        Figure figure = figures[source.x][source.y];
        if (figure == null) {
            throw new FigureNotFoundException(
                    String.format("В ячейке %d:%d нет фигуры.", source.x, source.y)
            );
        }
        Cell[] cells = figure.way(source, dest);
        for (Cell cell : cells) {
            if (figures[cell.x][cell.y] != null) {
                throw new OccupiedWayException(
                        String.format("Ход фигуре преграждает другая в ячейке %d:%d.", cell.x, cell.y)
                );
            }
        }
        figures[source.x][source.y] = null;
        figures[dest.x][dest.y] = figure.copy(dest);
        return true;
    }
}
