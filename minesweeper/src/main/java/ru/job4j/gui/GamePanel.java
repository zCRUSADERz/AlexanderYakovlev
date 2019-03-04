package ru.job4j.gui;

import ru.job4j.NewGame;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

/**
 * GamePanel.
 * Основная игровая панель.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.03.2019
 */
public class GamePanel extends JPanel {
    private final InfoPanel infoPanel;
    private final BoardPanel boardPanel;
    private final Function<JPanel, NewGame> newGameFactory;
    private final Thread worker;

    public GamePanel(final InfoPanel infoPanel, final BoardPanel boardPanel,
                     final Function<JPanel, NewGame> newGameFactory,
                     final Thread worker) {
        super(new BorderLayout());
        this.infoPanel = infoPanel;
        this.boardPanel = boardPanel;
        this.newGameFactory = newGameFactory;
        this.worker = worker;
    }

    public final void init() {
        this.infoPanel.init(this);
        this.boardPanel.init();
        this.add(infoPanel, BorderLayout.NORTH);
        this.add(boardPanel, BorderLayout.CENTER);
        this.newGameFactory.apply(this).start();
        this.worker.start();
    }

    /**
     * Закрывает все процессы связанные с этой игровой панелью.
     */
    public final void destroy() {
        this.worker.interrupt();
    }
}
