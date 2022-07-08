package com.example.calculator.ui;

public interface CalculatorView {

    void showResult(String result);
    void showInput(String input);
    String getResult();
    String getInput();
}
