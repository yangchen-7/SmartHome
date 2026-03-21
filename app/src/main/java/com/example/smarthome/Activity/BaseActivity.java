package com.example.smarthome.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smarthome.R;

public class BaseActivity extends AppCompatActivity {

    private FrameLayout contentContainer;
    private TextView roomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);

        contentContainer = findViewById(R.id.contentContainer);
        roomName = findViewById(R.id.roomName);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        initStatusBar();
    }

    /** 兼容 layoutId */
    @Override
    public void setContentView(int layoutResID) {
        if (contentContainer != null) {
            LayoutInflater.from(this).inflate(layoutResID, contentContainer, true);
        }
    }

    /** 兼容 ViewBinding */
    @Override
    public void setContentView(View view) {
        if (contentContainer != null) {
            contentContainer.removeAllViews();
            contentContainer.addView(view);
        }
    }

    protected void initStatusBar() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    public void setRoomTitle(String title) {
        if (roomName != null) {
            roomName.setText(title);
        }
    }
}