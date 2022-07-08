package com.example.calculator.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calculator.R;
import com.example.calculator.model.CalculatorImplem;
import com.example.calculator.model.Operator;

import java.util.HashMap;
import java.util.Map;

public class CalculatorActivity extends AppCompatActivity implements CalculatorView {

    private TextView resultTxt, inputTxt;

    private CalculatorPresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        resultTxt = findViewById(R.id.result);
        inputTxt = findViewById(R.id.input);

        presenter = new CalculatorPresenter(this, new CalculatorImplem());

        Map <Integer, Integer> digits = new HashMap<>();
        digits.put(R.id.button_0, 0);
        digits.put(R.id.button_1, 1);
        digits.put(R.id.button_2, 2);
        digits.put(R.id.button_3, 3);
        digits.put(R.id.button_4, 4);
        digits.put(R.id.button_5, 5);
        digits.put(R.id.button_6, 6);
        digits.put(R.id.button_7, 7);
        digits.put(R.id.button_8, 8);
        digits.put(R.id.button_9, 9);

        View.OnClickListener digitClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDigitPressed(digits.get(view.getId()));
            }
        };

        findViewById(R.id.button_0).setOnClickListener(digitClickListener);
        findViewById(R.id.button_1).setOnClickListener(digitClickListener);
        findViewById(R.id.button_2).setOnClickListener(digitClickListener);
        findViewById(R.id.button_3).setOnClickListener(digitClickListener);
        findViewById(R.id.button_4).setOnClickListener(digitClickListener);
        findViewById(R.id.button_5).setOnClickListener(digitClickListener);
        findViewById(R.id.button_6).setOnClickListener(digitClickListener);
        findViewById(R.id.button_7).setOnClickListener(digitClickListener);
        findViewById(R.id.button_8).setOnClickListener(digitClickListener);
        findViewById(R.id.button_9).setOnClickListener(digitClickListener);


        Map<Integer, Operator> operator = new HashMap<>();
        operator.put(R.id.button_plus, Operator.ADD);
        operator.put(R.id.button_minus, Operator.SUB);
        operator.put(R.id.button_multiply, Operator.MULT);
        operator.put(R.id.button_divide, Operator.DIV);

        View.OnClickListener operatorClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onOperatorPressed(operator.get(view.getId()));
            }
        };

        findViewById(R.id.button_plus).setOnClickListener(operatorClickListener);
        findViewById(R.id.button_minus).setOnClickListener(operatorClickListener);
        findViewById(R.id.button_divide).setOnClickListener(operatorClickListener);
        findViewById(R.id.button_multiply).setOnClickListener(operatorClickListener);
        findViewById(R.id.button_precent).setOnClickListener(operatorClickListener);

        findViewById(R.id.button_dot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDotPressed();
            }
        });

        findViewById(R.id.button_equals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onEqualsPressed();
            }
        });

        findViewById(R.id.button_AC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onACPressed();
            }
        });

        findViewById(R.id.button_precent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onPrecentPressed();
            }
        });

        findViewById(R.id.button_plus_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onPlusMinusPressed();
            }
        });

    }

    @Override
    public void showResult(String result) {
        resultTxt.setText(result);
    }

    @Override
    public void showInput(String input) {
        inputTxt.setText(input);
    }

    @Override
    public String getResult() {
        return String.valueOf(resultTxt.getText());
    }

    @Override
    public String getInput() {
        return String.valueOf(inputTxt.getText());
    }

}
