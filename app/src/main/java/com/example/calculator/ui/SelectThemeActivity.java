package com.example.calculator.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.calculator.R;
import com.example.calculator.model.Theme;
import com.example.calculator.model.ThemeRepository;
import com.example.calculator.model.ThemeRepositoryImpl;

import java.util.List;

public class SelectThemeActivity extends AppCompatActivity {

    public static final String EXTRA_THEME = "extra_theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_theme);

        findViewById(R.id.how_to).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://yandex.ru"));
                startActivity(Intent.createChooser(intent, null));
            }
        });

        ThemeRepository themeRepository = ThemeRepositoryImpl.getInstance(this);

        List<Theme> themes = themeRepository.getAll();

        LinearLayout container = findViewById(R.id.container);

        Intent intent = getIntent();

        Theme selectedTheme = (Theme) intent.getSerializableExtra(EXTRA_THEME);

        for (Theme theme : themes) {
            View itemView = getLayoutInflater().inflate(R.layout.item_theme, container, false);

            TextView title = itemView.findViewById(R.id.title);

            title.setText(theme.getTitle());

            CardView cardView = itemView.findViewById(R.id.theme_card);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent data = new Intent();
                    data.putExtra(EXTRA_THEME, theme);

                    setResult(Activity.RESULT_OK, data);
                    //  Toast.makeText(SelectThemeActivity.this, theme.getTitle(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

            ImageView checked = itemView.findViewById(R.id.checked);

            if (theme.equals(selectedTheme)) {
                checked.setVisibility(View.VISIBLE);
            } else {
                checked.setVisibility(View.GONE);
            }
            container.addView(itemView);
        }
    }
}