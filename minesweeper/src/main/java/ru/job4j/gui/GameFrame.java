package ru.job4j.gui;

import javax.swing.*;

/**
 * GameFrame.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.03.2019
 */
public final class GameFrame extends JFrame {
    private final GamePanel panel;

    public GameFrame(final GamePanel panel) {
        super("Minesweeper");
        this.panel = panel;
    }

    public final void init() {
        this.panel.init();
        this.add(panel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setIconImage(
                new ImageIcon(
                        getClass().getResource("/img/icon.png")
                ).getImage()
        );
        this.pack();
        this.setLocationRelativeTo(null);
    }
}
