package ru.job4j.gui;

import javax.swing.*;
import java.awt.*;

public class BoardOptions extends JFrame {

    public BoardOptions() {
        super("Параметры");
    }

    public final void init() {
        this.setLayout(new GridLayout(0, 2));
        this.add(new JLabel("Ширина"));
        final JTextField width = new JTextField();
        this.add(width);
        this.add(new JLabel("Высота"));
        final JTextField height = new JTextField();
        this.add(height);
        this.add(new JLabel("Число мин"));
        final JTextField bombs = new JTextField();
        this.add(bombs);
        final JButton ok = new JButton("Ок");
        ok.addActionListener(event -> { });
        final JButton cancel = new JButton("Отмена");
        cancel.addActionListener(event -> dispose());
        this.add(ok);
        this.add(cancel);
        pack();
        this.setVisible(true);
    }
}
