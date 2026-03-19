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

        // 卡片样式
        setRadius(20f);
        setCardElevation(6f);
        setUseCompatPadding(true);

        LayoutInflater.from(context).inflate(R.layout.view_device_switch, this, true);

        tvStatus = findViewById(R.id.tvStatus);
        tvName = findViewById(R.id.tvName);
        tvTemp = findViewById(R.id.tvTemp);
        icon = findViewById(R.id.icon);
        switchBtn = findViewById(R.id.switchBtn);

        // ⭐ 读取自定义属性
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

        // 初始化监听
        setupSwitchListener();

        // 初始化UI
        updateUI();

        //点击整个卡片也能切换
        setOnClickListener(v -> toggle());
    }

    /**
     * Switch监听（单独抽出来，方便重绑）
     */
    private void setupSwitchListener() {
        switchBtn.setOnCheckedChangeListener((buttonView, checked) -> {

            // 防止重复触发
            if (this.isChecked == checked) return;

            this.isChecked = checked;
            updateUI();

            if (onCheckedChangeListener != null) {
                onCheckedChangeListener.onCheckedChanged(checked);
            }
        });
    }

    /**
     * 卡片点击切换
     */
    private void toggle() {
        setChecked(!isChecked);

        if (onCheckedChangeListener != null) {
            onCheckedChangeListener.onCheckedChanged(isChecked);
        }
    }

    /**
     * 更新UI
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
     * 设置开关状态（已防止死循环）
     */
    public void setChecked(boolean checked) {
        if (this.isChecked == checked) return;

        this.isChecked = checked;

        // 移除监听，防止回调
        switchBtn.setOnCheckedChangeListener(null);

        switchBtn.setChecked(checked);

        // 恢复监听
        setupSwitchListener();

        updateUI();
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

    /**
     * 设置监听
     */
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.onCheckedChangeListener = listener;
    }

    /**
     * 手动触发点击（可选）
     */
    public void performToggle() {
        toggle();
    }

    // ================== 接口 ==================

    public interface OnCheckedChangeListener {
        void onCheckedChanged(boolean isChecked);
    }
}