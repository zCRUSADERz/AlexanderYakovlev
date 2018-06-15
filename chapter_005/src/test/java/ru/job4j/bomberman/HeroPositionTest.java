package ru.job4j.bomberman;

import org.junit.Test;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Местоположение героя на игровой доске. Тестирование.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 15.06.2018
 */
public class HeroPositionTest {
    private final Position start = new Position(0, 0);
    private final GameBoard board = new SimpleGameBoard(
            new ReentrantLock[][] {
                    {new ReentrantLock(), new ReentrantLock()},
                    {new ReentrantLock(), new ReentrantLock()}
            }
            );
    private final SimplePassablePositions passablePositions
            = new SimplePassablePositions(new BoardPositions(2));
    private HeroPosition heroPosition = new HeroPosition(
            start, board, passablePositions
    );

    @Test
    public void whenGetCoordThenReturnCoord() {
        int result = heroPosition.xCoord();
        int expected = start.xCoord();
        assertThat(result, is(expected));
        result = heroPosition.yCoord();
        expected = start.yCoord();
        assertThat(result, is(expected));
    }

    @Test
    public void whenMoveRightInPassableDirectionThenReturnTrue()
            throws InterruptedException {
        heroPosition = new HeroPosition(
                start,
                gameBoardMoveTrue(start, new Position(1, 0)),
                passablePositionsWithCoord(1, 0)
        );
        boolean result = heroPosition.moveInDirection(
                DirectionOfMove.RIGHT, 1, TimeUnit.MILLISECONDS
        );
        boolean expected = true;
        assertThat(result, is(expected));
    }

    @Test
    public void whenMoveSuccessThenPositionChangeOnTarget()
            throws InterruptedException {
        heroPosition = new HeroPosition(
                start,
                gameBoardMoveTrue(start, new Position(1, 0)),
                passablePositionsWithCoord(1, 0)
        );
        heroPosition.moveInDirection(
                DirectionOfMove.RIGHT, 1, TimeUnit.MILLISECONDS
        );
        int result = heroPosition.xCoord();
        int expected = 1;
        assertThat(result, is(expected));
        result = heroPosition.yCoord();
        expected = 0;
        assertThat(result, is(expected));
    }

    @Test
    public void whenBoardMoveReturnFalseThenMoveReturnFalse()
            throws InterruptedException {
        heroPosition = new HeroPosition(
                start,
                gameBoardMoveFalse(),
                passablePositionsWithCoord(1, 0)
        );
        boolean result = heroPosition.moveInDirection(
                DirectionOfMove.RIGHT, 1, TimeUnit.MILLISECONDS
        );
        boolean expected = false;
        assertThat(result, is(expected));
    }

    @Test
    public void whenDirectionImpassableThenMoveReturnFalse()
            throws InterruptedException {
        heroPosition = new HeroPosition(
                new Position(1, 0),
                board,
                passablePositions
        );
        boolean result = heroPosition.moveInDirection(
                DirectionOfMove.DOWN, 1, TimeUnit.MILLISECONDS
        );
        boolean expected = false;
        assertThat(result, is(expected));
    }

    @Test
    public void whenInDirectionOtherHeroThenCheckOtherHeroReturnTrue() {
        HeroPosition otherHero = new HeroPosition(
                new Position(1, 0), board, passablePositions);
        boolean result = heroPosition.checkOtherHeroInDirection(
                DirectionOfMove.RIGHT, otherHero
        );
        boolean expected = true;
        assertThat(result, is(expected));
    }

    @Test
    public void whenDirectionEmptyThenCheckOtherHeroReturnFalse() {
        HeroPosition otherHero = new HeroPosition(
                new Position(0, 1), board, passablePositions);
        boolean result = heroPosition.checkOtherHeroInDirection(
                DirectionOfMove.UP, otherHero
        );
        boolean expected = true;
        assertThat(result, is(expected));
    }

    @Test
    public void whenInitStartPosition() {
        heroPosition = new HeroPosition(
                start, gameBoardWithCheckOccupyCell(start), passablePositions
        );
        heroPosition.initStartPosition();
    }

    private GameBoard gameBoardMoveTrue(Position start, Position finish) {
        return new GameBoard() {
            @Override
            public boolean move(Position current, Position target, long timeout,
                                TimeUnit unit) {
                assertThat(start, is(current));
                assertThat(finish, is(target));
                return true;
            }

            @Override
            public void occupyCell(Position position) {

            }
        };
    }

    private GameBoard gameBoardWithCheckOccupyCell(Position target) {
        return new GameBoard() {
            @Override
            public boolean move(Position current, Position target, long timeout, TimeUnit unit) {
                return false;
            }

            @Override
            public void occupyCell(Position position) {
                assertThat(target, is(position));
            }
        };
    }

    private GameBoard gameBoardMoveFalse() {
        return new GameBoard() {
            @Override
            public boolean move(Position current, Position target, long timeout,
                                TimeUnit unit) {
                return false;
            }

            @Override
            public void occupyCell(Position position) {

            }
        };
    }

    private PassablePositions passablePositionsWithCoord(int xCoord, int yCoord) {
        return new PassablePositions() {
            @Override
            public boolean isPassableCoordinate(int x, int y) {
                assertThat(xCoord, is(x));
                assertThat(yCoord, is(y));
                return true;
            }

            @Override
            public Collection<Position> allPassablePositions() {
                return null;
            }

            @Override
            public Collection<Position> impassablePositions() {
                return null;
            }
        };
    }

    private PassablePositions passablePositionsWithAllImpassableDirections() {
        return new PassablePositions() {
            @Override
            public boolean isPassableCoordinate(int x, int y) {
                return false;
            }

            @Override
            public Collection<Position> allPassablePositions() {
                return null;
            }

            @Override
            public Collection<Position> impassablePositions() {
                return null;
            }
        };
    }
}