package ru.job4j.coordinates;

import java.util.Objects;

public final class Coordinate {
    private final int x;
    private final int y;

    public Coordinate(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public final int x() {
        return this.x;
    }

    public final int y() {
        return this.y;
    }

    @Override
    public String toString() {
        return String.format("Coordinate{x=%s, y=%s}", this.x, this.y);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(x, y);
    }
}
