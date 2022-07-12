package com.example.calculator.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calculator.R;
import com.example.calculator.model.CalculatorImplem;
import com.example.calculator.model.Operator;
import com.example.calculator.model.Theme;
import com.example.calculator.model.ThemeRepository;
import com.example.calculator.model.ThemeRepositoryImpl;

import java.util.HashMap;
import java.util.Map;

public class CalculatorActivity extends AppCompatActivity implements CalculatorView {

    private static final String KEY_INPUT = "input";
    private static final String KEY_RESULT = "result";
    private TextView resultTxt, inputTxt;
    private CalculatorPresenter presenter;
    private ThemeRepository themeRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        themeRepository = ThemeRepositoryImpl.getInstance(this);

        setTheme(themeRepository.getSavedTheme().getThemeRes());

        setContentView(R.layout.activity_calculator);

        resultTxt = findViewById(R.id.result);
        inputTxt = findViewById(R.id.input);

        if (getIntent().hasExtra("msg")) {
            resultTxt.setText(getIntent().getStringExtra("msg"));
        }

        presenter = new CalculatorPresenter(this, new CalculatorImplem());

        Map<Integer, Integer> digits = new HashMap<>();
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

        ActivityResultLauncher<Intent> themeLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent intent = result.getData();

                    Theme selectedTheme = (Theme) intent.getSerializableExtra(SelectThemeActivity.EXTRA_THEME);

                    themeRepository.saveTheme(selectedTheme);
                    recreate();
                }

            }
        });

        findViewById(R.id.select_theme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalculatorActivity.this, SelectThemeActivity.class);
                intent.putExtra(SelectThemeActivity.EXTRA_THEME, themeRepository.getSavedTheme());
                themeLauncher.launch(intent);
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(KEY_INPUT, inputTxt.getText().toString());
        outState.putString(KEY_RESULT, resultTxt.getText().toString());
        super.onSaveInstanceState(outState);

        Log.d("CounterAct", "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        inputTxt.setText(savedInstanceState.getString(KEY_INPUT));
        resultTxt.setText(savedInstanceState.getString(KEY_RESULT));
    }
}
