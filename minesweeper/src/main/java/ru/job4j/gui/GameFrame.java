package ru.job4j.gui;

import ru.job4j.BoardProperties;

import javax.swing.*;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * GameFrame.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.03.2019
 */
public class GameFrame extends JFrame {
    private final BoardProperties defaultBoardProperties;
    private final BiFunction<GameFrame, BoardProperties, JMenuBar> menuFactory;
    private final Function<BoardProperties, JPanel> panelFactory;


    public GameFrame(
            final BoardProperties defaultBoardProperties,
            final Function<BoardProperties, JPanel> panelFactory,
            final BiFunction<GameFrame, BoardProperties, JMenuBar> menuFactory) {
        super("Minesweeper");
        this.defaultBoardProperties = defaultBoardProperties;
        this.panelFactory = panelFactory;
        this.menuFactory = menuFactory;
    }

    public final void init() {
        this.setJMenuBar(
                this.menuFactory.apply(this, this.defaultBoardProperties)
        );
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setIconImage(
                new ImageIcon(
                        getClass().getResource("/img/icon.png")
                ).getImage()
        );
        this.setLocationRelativeTo(null);
        update(this.defaultBoardProperties);
        this.setVisible(true);
    }

    /**
     * Обновляет игровую доску в соответствии с переданными размерами.
     * @param boardProperties свойства новой игровой доски.
     */
    public void update(final BoardProperties boardProperties) {
        this.getContentPane().removeAll();
        this.add(this.panelFactory.apply(boardProperties));
        this.pack();
    }
}
