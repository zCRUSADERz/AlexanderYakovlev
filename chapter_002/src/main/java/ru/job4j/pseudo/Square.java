package ru.job4j.pseudo;

public class Square implements Shape {

    public String draw() {
        StringBuilder square = new StringBuilder();
        square.append(String.format(" _______ %n"));
        square.append(String.format("|       |%n"));
        square.append(String.format("|       |%n"));
        square.append(String.format("|       |%n"));
        square.append(String.format("|_______|%n"));
        return square.toString();
    }
}