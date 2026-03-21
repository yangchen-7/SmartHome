package com.example.smarthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.smarthome.R;

/**
 * 最终版核心原则：
 * —— UI 更新用 setChecked()，不会触发业务回调
 * —— 用户操作用 setCheckedByUser()，会触发业务回调
 */
public class DeviceSwitchView extends CardView {

    private TextView tvStatus, tvName, tvTemp;
    private ImageView icon;
    private Switch switchBtn;

    private boolean isChecked = false;
    private OnCheckedChangeListener onCheckedChangeListener;

    public DeviceSwitchView(Context context) {
        super(context);
        init(context, null);
    }

    public DeviceSwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DeviceSwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        setRadius(20f);
        setCardElevation(6f);
        setUseCompatPadding(true);

        LayoutInflater.from(context).inflate(R.layout.view_device_switch, this, true);

        tvStatus = findViewById(R.id.tvStatus);
        tvName = findViewById(R.id.tvName);
        tvTemp = findViewById(R.id.tvTemp);
        icon = findViewById(R.id.icon);
        switchBtn = findViewById(R.id.switchBtn);

        // 读取自定义属性
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DeviceSwitchView);

            String name = ta.getString(R.styleable.DeviceSwitchView_deviceName);
            String temp = ta.getString(R.styleable.DeviceSwitchView_deviceTemp);
            int iconRes = ta.getResourceId(R.styleable.DeviceSwitchView_deviceIcon, 0);
            isChecked = ta.getBoolean(R.styleable.DeviceSwitchView_checked, false);

            if (name != null) tvName.setText(name);
            if (temp != null) tvTemp.setText(temp);
            if (iconRes != 0) icon.setImageResource(iconRes);

            ta.recycle();
        }

        // 初始化真正的 Switch 监听（用户操作）
        setupUserSwitchListener();

        updateUI();

        // 点击整个卡片 = 用户行为
        setOnClickListener(v -> setCheckedByUser(!isChecked));
    }

    /**
     * Switch 用户监听
     */
    private void setupUserSwitchListener() {
        switchBtn.setOnCheckedChangeListener((buttonView, checked) -> {

            // 如果状态没变，就不回调
            if (this.isChecked == checked) return;

            // 用户行为
            setCheckedByUser(checked);
        });
    }

    /**
     * 更新UI（只负责显示）
     */
    private void updateUI() {
        if (isChecked) {
            tvStatus.setText("开");
            setCardBackgroundColor(Color.WHITE);
        } else {
            tvStatus.setText("关");
            setCardBackgroundColor(Color.parseColor("#EEEEEE"));
        }
    }

    // ================== 对外API ==================

    /**
     * ★★★★★ UI 更新调用这个，不触发监听器
     */
    public void setChecked(boolean checked) {
        if (this.isChecked == checked) return;

        this.isChecked = checked;

        // 不触发监听器
        switchBtn.setOnCheckedChangeListener(null);
        switchBtn.setChecked(checked);
        switchBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setCheckedByUser(isChecked); // 恢复用户监听
        });

        updateUI();
    }

    /**
     * ★★★★★ 用户操作必须用这个，会触发业务逻辑
     */
    public void setCheckedByUser(boolean checked) {
        if (this.isChecked == checked) return;

        this.isChecked = checked;

        // UI 同步
        switchBtn.setOnCheckedChangeListener(null);
        switchBtn.setChecked(checked);
        switchBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setCheckedByUser(isChecked);
        });

        updateUI();

        // 用户行为 → 通知业务层
        if (onCheckedChangeListener != null) {
            onCheckedChangeListener.onCheckedChanged(checked);
        }
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setName(String name) {
        tvName.setText(name);
    }

    public void setTemp(String temp) {
        tvTemp.setText(temp);
    }

    public void setIcon(int resId) {
        icon.setImageResource(resId);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.onCheckedChangeListener = listener;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(boolean isChecked);
    }
}