package com.example.calculator.ui;

import com.example.calculator.model.Calculator;
import com.example.calculator.model.Operator;

import java.text.DecimalFormat;

public class CalculatorPresenter {

    private final DecimalFormat formator = new DecimalFormat("#.##");
    private CalculatorView view;
    private Calculator calculator;
    private double argOne;
    private Double argTwo;
    private Operator selectOperator;
    private Double dotArg;

    public CalculatorPresenter(CalculatorView view, Calculator calculator) {
        this.view = view;
        this.calculator = calculator;
    }

    public void onDigitPressed(int digit) {
        if (!view.getResult().equals("")) {
            argOne = Double.valueOf(view.getResult());
        }

        if (dotArg != null) {
            argOne = argOne * 10 + digit;
            view.showInput(formator.format(dotArg) + "." + formator.format(argOne));
        } else {
            if (argTwo == null) {
                argOne = argOne * 10 + digit;
                showFormatted(argOne);
            } else {
                argTwo = argTwo * 10 + digit;
                showFormatted(argTwo);
            }
        }

    }

    public void onOperatorPressed(Operator operator) {

        if (dotArg != null) {
            argOne = Double.valueOf(view.getInput());
            dotArg = null;
        }

        if (selectOperator != null) {
            if (argTwo == null) {
                argTwo = 0.0;
            }
            argOne = calculator.perform(argOne, argTwo, selectOperator);
            showFormattedResult(argOne);
        }
        argTwo = 0.0;//уже не null идет набор 2 элем
        selectOperator = operator;

    }

    public void onDotPressed() {
        dotArg = argOne;
        argOne = 0.0;
        view.showInput(formator.format(dotArg) + ".");
        //когда нажимается  точка на бираем не *10 а уменьшаем степень(10 в -2, 10 в -1)

    }

    public void onEqualsPressed() {
        if (selectOperator != null) {
            argOne = calculator.perform(argOne, argTwo, selectOperator);
            showFormattedResult(argOne);
            argOne = 0.0;
            argTwo = null;
            selectOperator = null;
            view.showInput("");
        }

    }

    public void onACPressed() {//доделать
        view.showInput("");
        view.showResult("");
        argOne = 0.0;
        argTwo = null;
        selectOperator = null;

    }


    public void onPrecentPressed() {
        if (!view.getResult().equals("")) {
            argOne = Double.valueOf(view.getResult());
        }

        argOne = argOne * 0.01;
        showFormattedResult(argOne);
        argOne = 0.0;
        argTwo = null;
        selectOperator = null;
        view.showInput("");
    }

    public void onPlusMinusPressed() {
        argOne = argOne * (-1);
        showFormatted(argOne);
    }

    private void showFormatted(double value) {
        view.showInput(formator.format(value));
    }

    private void showFormattedResult(double value) {
        view.showResult(formator.format(value));
    }

}
