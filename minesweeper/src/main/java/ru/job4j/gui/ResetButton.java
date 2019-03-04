package ru.job4j.gui;

import ru.job4j.NewGame;

import javax.swing.*;
import java.util.function.Function;

public class ResetButton extends JButton {
    private final Function<JPanel, NewGame> newGameFactory;
    private final Icon icon;

    public ResetButton(final Icon icon,
                       final Function<JPanel, NewGame> newGameFactory) {
        super("", icon);
        this.newGameFactory = newGameFactory;
        this.icon = icon;
    }

    public final void init(final JPanel panel) {
        this.setBorder(BorderFactory.createLoweredBevelBorder());
        final NewGame newGame = this.newGameFactory.apply(panel);
        this.addActionListener(event -> {
            this.setIcon(icon);
            newGame.start();
        });
    }
}
