package ru.job4j.gui;

import ru.job4j.BoardProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * BoardOptions.
 * Окно для ввода собственных свойств(ширина, высота, количество бомб)
 * игровой доски.
 * Получив валидные данные от пользователя отправляет созданный экземпляр
 * BoardProperties по назначению. Окно при этом закрывается.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 03.03.2019
 */
public class BoardOptions extends JFrame {
    private final BoardProperties defaultProperties;
    private final Consumer<BoardProperties> boardPropertiesConsumer;

    public BoardOptions(final BoardProperties defaultProperties,
                        final Consumer<BoardProperties> boardPropertiesConsumer) {
        super("Параметры");
        this.defaultProperties = defaultProperties;
        this.boardPropertiesConsumer = boardPropertiesConsumer;
    }

    public final void init() {
        this.setLayout(new GridLayout(0, 2));
        this.add(new JLabel("Ширина"));
        final JTextField width = new JTextField(this.defaultProperties.width());
        this.add(width);
        this.add(new JLabel("Высота"));
        final JTextField height = new JTextField(this.defaultProperties.height());
        this.add(height);
        this.add(new JLabel("Число мин"));
        final JTextField bombs = new JTextField(this.defaultProperties.bombs());
        this.add(bombs);
        final JButton ok = new JButton("Ок");
        ok.addActionListener(new OptionsListener(
                defaultProperties, width, height, bombs, this, this.boardPropertiesConsumer
        ));
        final JButton cancel = new JButton("Отмена");
        cancel.addActionListener(event -> dispose());
        this.add(ok);
        this.add(cancel);
        pack();
        this.setVisible(true);
    }

    public static class OptionsListener implements ActionListener {
        private final BoardProperties defaultProperties;
        private final JTextField widthField;
        private final JTextField heightField;
        private final JTextField bombsField;
        private final JFrame frame;
        private final Consumer<BoardProperties> boardPropertiesConsumer;

        public OptionsListener(
                final BoardProperties defaultProperties,
                final JTextField widthField, final JTextField heightField,
                final JTextField bombsField, final JFrame frame,
                final Consumer<BoardProperties> boardPropertiesConsumer) {
            this.defaultProperties = defaultProperties;
            this.widthField = widthField;
            this.heightField = heightField;
            this.bombsField = bombsField;
            this.frame = frame;
            this.boardPropertiesConsumer = boardPropertiesConsumer;
        }

        @Override
        public final void actionPerformed(final ActionEvent e) {
            final Predicate<String> digitValidator
                    = Pattern.compile("^\\d+$").asMatchPredicate();
            boolean error = true;
            if (digitValidator.test(this.widthField.getText())
                    && digitValidator.test(this.heightField.getText())
                    && digitValidator.test(this.bombsField.getText())) {
                final int width = Integer.parseInt(this.widthField.getText());
                final int height = Integer.parseInt(this.heightField.getText());
                final int bombs = Integer.parseInt(this.bombsField.getText());
                if (width >= 5 && height >= 1 && bombs >= 0
                        && (width * height) > bombs) {
                    error = false;
                    this.boardPropertiesConsumer.accept(new BoardProperties(
                            width, height, bombs
                    ));
                    this.frame.dispose();
                }
            }
            if (error) {
                this.widthField.setText(Integer.toString(this.defaultProperties.width()));
                this.heightField.setText(Integer.toString(this.defaultProperties.height()));
                this.bombsField.setText(Integer.toString(this.defaultProperties.bombs()));
                showInfo();
            }
        }

        private void showInfo() {
            final StringJoiner builder = new StringJoiner(System.lineSeparator())
                    .add("Условия:")
                    .add("Ширина не менее 5")
                    .add("Высота не менее 1")
                    .add("Количество бомб не менее 0")
                    .add("Количество бомб не должно превышать количество ячеек");
            JOptionPane.showMessageDialog(this.frame, builder.toString());
        }
    }
}
