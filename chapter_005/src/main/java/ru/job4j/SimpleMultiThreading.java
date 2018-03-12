package ru.job4j;

public class SimpleMultiThreading {

    public void calc(String line) {
        System.out.println("Calc run");
        new Thread(() -> {
            int counter = 0;
            for (String ignored : line.split(" ")) {
                counter++;
            }
            System.out.println(String.format("Number of words: %d", counter));
        }).start();
        new Thread(() -> {
            int counter = 0;
            for (char ch : line.toCharArray()) {
                if (ch == ' ') {
                    counter++;
                }
            }
            System.out.println(String.format("Number of spaces: %d", counter));
        }).start();
    }
}
