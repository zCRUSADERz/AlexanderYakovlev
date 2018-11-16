package ru.job4j.pseudo;

public class Triangle implements Shape {

    public String draw() {
        StringBuilder triangle = new StringBuilder();
        triangle.append(String.format("    .%n"));
        triangle.append(String.format("   /|%n"));
        triangle.append(String.format("  / |%n"));
        triangle.append(String.format(" /  |%n"));
        triangle.append(String.format("/___|%n"));
        return triangle.toString();
    }
}