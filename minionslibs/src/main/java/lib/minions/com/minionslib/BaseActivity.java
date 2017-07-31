package lib.minions.com.minionslib;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.autolayout.AutoLinearLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import lib.minions.com.minionslib.app.BaseApplication;
import lib.minions.com.minionslib.util.MyUtils;
import lib.minions.com.minionslib.util.ToastUtil;


public class BaseActivity extends AutoLayoutActivity {
    private static final String TAG = "BaseActivity";
    private TextView baseAcbTitle;
    private ImageView baseAcbBack;
    private ImageView baseAcbSearch;
    private View titleView;

    //
    private FrameLayout layout_Content;
    private View contentView;
    private boolean isForFinish = true;
    private TextView mRightTv;
    private ViewGroup layout;
    private View devider;
    private View positionView;
    private boolean judgeEditView = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 无标题栏
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // 竖屏锁定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.setContentView(R.layout.activity_base);
        // 1. 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }

        positionView = findViewById(R.id.position_view);
        dealStatusBar();
        titleView = findViewById(R.id.titleView);
        layout = (ViewGroup) findViewById(R.id.baseActionBarLayout);
        mRightTv = (TextView) findViewById(R.id.actionber_tv_Right);
        baseAcbTitle = (TextView) findViewById(R.id.actionbar_TItle);
        baseAcbBack = (ImageView) findViewById(R.id.actionber_img_back);
        baseAcbSearch = (ImageView) findViewById(R.id.actionber_img_search);
        layout_Content = (FrameLayout) findViewById(R.id.content);
        devider = findViewById(R.id.devider);
        baseAcbTitle.setVisibility(View.GONE);
        setActionBarBackGroundColor(R.color.white);
        baseAcbTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,54);
        BaseApplication.getI().addActivity(this);
    }

    public void setJudgeEditView(boolean judgeEditView) {
        this.judgeEditView = judgeEditView;
    }

    /**
     * 调整沉浸式菜单的title
     */
    private void dealStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = getStatusBarHeight();
            positionView.setLayoutParams(new AutoLinearLayout.LayoutParams(
                    AutoLinearLayout.LayoutParams.MATCH_PARENT,
                    statusBarHeight));
            positionView.setBackgroundColor(getResources().getColor(R.color.black));

        }
    }

    public void setTitleViewHeight(int height){
        titleView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height));
    }

    public void setStateBarBackBround(int imgres){
        positionView.setBackgroundResource(imgres);
    }

    public void hideStatusBar(){
        positionView.setVisibility(View.GONE);
    }

    public void showStatusBar(){
        positionView.setVisibility(View.VISIBLE);
    }

    private int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    private void initListener() {
        if (null != baseAcbBack && isForFinish) {
            baseAcbBack.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        InputMethodManager immMain = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
                        immMain.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finish();
                }
            });
        }

    }

    protected void showToast(Object c) {
        ToastUtil.showToast(c.toString());
    }

    /**
     * 隐藏顶部分割线
     */
    public void hideDevider(){
        devider.setVisibility(View.GONE);
    }

    /**
     * 设置 自定义标题栏View  左右两边图片不会被覆盖
     */
    public void setActionBarView(View view) {
        if (layout.getChildCount() == 0){
            layout.addView(view);
        }
        layout.setVisibility(View.VISIBLE);
        titleView.setVisibility(View.VISIBLE);
    }

    /***
     * 设置 返回键销毁该界面 默认 true
     */
    public void setBackForFinish(boolean isForFinish) {
        this.isForFinish = isForFinish;
        initListener();
    }

    /***
     * 设置 右边文字
     *
     * @param text
     */
    public TextView setRightText(String text) {
        mRightTv.setVisibility(View.VISIBLE);
        mRightTv.setText(text);
        return mRightTv;
    }

    /***
     * 返回右边控件
     *
     * @return textView
     */
    public TextView getRightTextView() {
        return mRightTv;
    }

    /***
     * 设置 隐藏右边文字
     */
    public void HideRightTv() {
        mRightTv.setVisibility(View.INVISIBLE);
    }


    @Override
    public void setContentView(int resId){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(resId, null);
        contentView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        layout_Content.addView(contentView);
        ButterKnife.bind(this);
    }

    /***
     * 得到加载好的布局
     *
     * @return contentView
     */
    public View getContentView() {
        return contentView;
    }

    /***
     * 得到 左边Img
     *
     * @return imgBack
     */
    public ImageView getLeftImg() {
        return baseAcbBack;
    }

    /***
     * 得到 右边Img
     *
     * @return imgSearch
     */
    public ImageView getRightImg() {
        return baseAcbSearch;
    }

    /***
     * 得到 标题Tv
     *
     * @return title
     */
    public TextView getActionBarTitle() {
        return baseAcbTitle;
    }

    /***
     * 设置 标题
     *
     * @return title+"";
     */
    public void setActionBarTitle(String title) {
        if (null != baseAcbTitle) {
            baseAcbTitle.setText(title + "");
            baseAcbTitle.setVisibility(View.VISIBLE);
        }
        layout.setVisibility(View.GONE);
        titleView.setVisibility(View.VISIBLE);
        devider.setVisibility(View.VISIBLE);
    }

    /***
     * 设置 标题
     *
     * @return getString(titleRes)+"";
     */
    public void setActionBarTitle(int titleRes) {
        layout.setVisibility(View.GONE);
        if (null != baseAcbTitle) {
            baseAcbTitle.setText(getString(titleRes) + "");
            baseAcbTitle.setVisibility(View.VISIBLE);
        }
        titleView.setVisibility(View.VISIBLE);
        devider.setVisibility(View.VISIBLE);
    }

    /***
     * 设置 左返回键图片资源
     *
     * @param resId
     * @return getString(titleRes)+"";
     */
    public void setActionBarLeftImg(int resId) {
        if (null != baseAcbBack) {
            baseAcbBack.setImageResource(resId);
        }
        setBackForFinish(true);
    }

    /***
     * 设置 左返回键图片资源
     *
     * @param resId
     */
    public void setActionBarLeftImg(int resId, boolean b) {
        if (null != baseAcbBack) {
            baseAcbBack.setImageResource(resId);
        }
        setBackForFinish(b);
    }

    /***
     * 设置 左返回键图片资源
     *
     * @param drawable
     */
    public void setActionBarLeftImg(Drawable drawable) {
        if (null != baseAcbBack) {
            baseAcbBack.setImageDrawable(drawable);
        }
        setBackForFinish(true);
    }

    /***
     * 设置 右查询键图片资源
     *
     * @param resId
     */
    public ImageView setActionBarRightImg(int resId) {
        if (null != baseAcbSearch) {
            baseAcbSearch.setVisibility(View.VISIBLE);
            baseAcbSearch.setImageResource(resId);
            return baseAcbSearch;
        }
        return null;
    }

    /***
     * 设置 左返回键图片资源
     *
     * @param drawable
     */
    public void setActionBarRightImg(Drawable drawable) {
        if (null != baseAcbSearch) {
            baseAcbSearch.setVisibility(View.VISIBLE);
            baseAcbSearch.setImageDrawable(drawable);
        }
    }

    /***
     * 隐藏标题栏
     */
    public void hideTitleView() {
        if (null != titleView) {
            titleView.setVisibility(View.GONE);
        }
        hideDevider();
    }

    /***
     * 隐藏标题栏
     */
    public void hideTitleViewAndStatusBar() {
        if (null != titleView) {
            titleView.setVisibility(View.GONE);
        }
        hideDevider();
        hideStatusBar();
    }

    /***
     * 隐藏左边按钮
     */
    public void hideLeftImg() {
        if (null != baseAcbBack) {
            baseAcbBack.setVisibility(View.INVISIBLE);
        }
    }

    /***
     * 隐藏右边按钮
     */
    public void hideRightImg() {
        if (null != baseAcbSearch) {
            baseAcbSearch.setVisibility(View.INVISIBLE);
        }
    }

    /***
     * 设置背景图片
     */
    public void setActionBarBackGroundImg(int id) {
        if (null != titleView) {
            titleView.setBackgroundDrawable(getBaseContext().getResources()
                    .getDrawable(id));
        }
    }

    /***
     * 设置背景颜色
     */
    public void setActionBarBackGroundColor(int color) {
        if (null != titleView) {
            titleView.setBackgroundColor(getResources().getColor(color));
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.alpha_out);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!judgeEditView)return super.dispatchTouchEvent(ev);
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    v.clearFocus();
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getRawX() > left && event.getRawX() < right
                    && event.getRawY() > top && event.getRawY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean isEmptyStr(String string){
        return MyUtils.isEmptyString(string);
    }

    // --------------------权限检测与申请----------------------

    private int REQUEST_CODE_PERMISSION = 0x00099;

    /**
     * 请求权限
     *
     * @param permissions 请求的权限
     * @param requestCode 请求权限的请求码
     */
    public void requestPermission(String[] permissions, int requestCode) {
        this.REQUEST_CODE_PERMISSION = requestCode;
        if (checkPermissions(permissions)) {
            permissionSuccess(REQUEST_CODE_PERMISSION);
        } else {
            List<String> needPermissions = getDeniedPermissions(permissions);
            ActivityCompat.requestPermissions(this, needPermissions.toArray(new String[needPermissions.size()]), REQUEST_CODE_PERMISSION);
        }
    }

    /**
     * 检测所有的权限是否都已授权
     *
     * @param permissions
     * @return
     */
    private boolean checkPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     */
    private List<String> getDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                needRequestPermissionList.add(permission);
            }
        }
        return needRequestPermissionList;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (verifyPermissions(grantResults)) {
                permissionSuccess(REQUEST_CODE_PERMISSION);
            } else {
                permissionFail(REQUEST_CODE_PERMISSION);
                showTipsDialog();
            }
        }
    }

    /**
     * 确认所有的权限是否都已授权
     *
     * @param grantResults
     * @return
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 显示提示对话框
     */
    private void showTipsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示信息")
                .setMessage("当前应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【确定】按钮前往设置中心进行权限授权。")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                }).show();
    }

    /**
     * 启动当前应用设置页面
     */
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    /**
     * 获取权限成功
     *
     * @param requestCode
     */
    public void permissionSuccess(int requestCode) {
        showToast("获取权限成功");
    }

    /**
     * 权限获取失败
     * @param requestCode
     */
    public void permissionFail(int requestCode) {
        showToast("获取权限失败");
    }



}
