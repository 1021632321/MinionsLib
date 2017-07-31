package lib.minions.com.minionslib.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Gallery.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;

/**
 * 屏幕相关信息
 * 1:去除标题栏    		setNotTitleBar(final Activity mActivity)
 * 2:全屏				setFullscreen(final Activity mActivity)
 * 3:获取屏幕宽度的像素	getScreenWidth(Context context)
 * 4:获取屏幕高度的像素	getScreenHeight(Context context)
 * 5:把DP单位的尺寸转换为PX单位		dip2Px(Context context, float dpValue)
 * 6:动态设置控件尺寸             setViewSize(int layout, View v,int w,int h)
 * 7:隐藏输入法			hindInputMethod
 *
 * @author T_xin
 */
public class DisplayUtil {


    /**
     * 全屏
     *
     * @param mActivity
     */
    public static void setFullscreen(final Activity mActivity) {
        final Window window = mActivity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        window.requestFeature(Window.FEATURE_NO_TITLE);
    }


    /**
     * 获取屏幕宽度的像素
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度像素
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 把dp单位的尺寸转换为px单位
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2Px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 把px单位的尺寸转换为dp单位
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2Dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 动态设置控件尺寸
     *
     * @param layout 判断父控件是线性布局还是相对布局，1线性布局 2相对布局
     * @param v      控件名称
     * @param w      宽度
     *               w=0  wrap_content;
     *               w=1  match_parent;
     *               w=-1  保持控件原有宽度;
     * @param h      高度
     *               h=0  wrap_content;
     *               h=1  match_parent;
     *               h=-1  保持控件原有高度;
     * @author T_xin
     * 2015年11月24日
     * setViewSize
     */
    public static void setViewSize(int layout, View v, int w, int h) {
        if (layout == 1) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) v.getLayoutParams();
            if (w == 0) {
                lp.width = LayoutParams.WRAP_CONTENT;
            } else if (w == 1) {
                lp.width = LayoutParams.MATCH_PARENT;
            } else if (w == -1) {
                return;
            } else {
                lp.width = w;
            }
            if (h == 0) {
                lp.height = LayoutParams.WRAP_CONTENT;
            } else if (h == 1) {
                lp.width = LayoutParams.MATCH_PARENT;
            } else if (w == -1) {
                return;
            } else {
                lp.height = h;
            }
            v.setLayoutParams(lp);
        } else if (layout == 2) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) v.getLayoutParams();
            if (w == 0) {
                lp.width = LayoutParams.WRAP_CONTENT;
            } else if (w == 1) {
                lp.width = LayoutParams.MATCH_PARENT;
            } else if (w == -1) {
                return;
            } else {
                lp.width = w;
            }
            if (h == 0) {
                lp.height = LayoutParams.WRAP_CONTENT;
            } else if (h == 1) {
                lp.width = LayoutParams.MATCH_PARENT;
            } else if (w == -1) {
                return;
            } else {
                lp.height = h;
            }
            v.setLayoutParams(lp);
        }
    }

    public static int getStatuBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 38;//默认为38，貌似大部分是这样的

        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }


    /**
     * 隐藏输入法
     */
    public static void hindInputMethod(View v, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, int spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * dip转换px
     */
    public static int dip2px(Context context, int dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    static class Platform {
        private static final Platform PLATFORM = findPlatform();

        static Platform get() {
            return PLATFORM;
        }

        private static Platform findPlatform() {
            try {
                Class.forName("android.os.Build");
                if (Build.VERSION.SDK_INT != 0) {
//                    return new Android();
                }
            } catch (ClassNotFoundException ignored) {
            }
            try {
                Class.forName("java.util.Optional");
//                return new Java8();
            } catch (ClassNotFoundException ignored) {
            }
            try {
                Class.forName("org.robovm.apple.foundation.NSObject");
//                return new IOS();
            } catch (ClassNotFoundException ignored) {
            }
            return new Platform();
        }
    }
}
