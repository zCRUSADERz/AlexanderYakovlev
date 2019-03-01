package ru.job4j.cells;

import javax.swing.*;
import java.awt.*;

public final class NoBomb implements CellImage {

    @Override
    public final Image image() {
        return new ImageIcon(
                getClass().getResource("/img/nobomb.png")
        ).getImage();
    }
}
