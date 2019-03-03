package ru.job4j.gui;

import javax.swing.*;

/**
 * GameFrame.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.03.2019
 */
public class GameFrame extends JFrame {
    private final GamePanel panel;
    private final JMenuBar menu;

    public GameFrame(final GamePanel panel, final JMenuBar menu) {
        super("Minesweeper");
        this.panel = panel;
        this.menu = menu;
    }

    public final void init() {
        this.panel.init();
        this.setJMenuBar(this.menu);
        this.add(this.panel);
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
