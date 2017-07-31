package lib.minions.com.minionslib.util;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import lib.minions.com.minionslib.R;
import lib.minions.com.minionslib.app.BaseApplication;


/**
 * 提示框
 * Change by Cgz on 2017/1/18.
 */
public class ToastUtil {

    private static Toast toast;
    private static int showCount = 1;

    public static void showToast(String str){
        if (null != toast) {
            toast.cancel();
            showCount ++;
        }
        View toastView = getToastView(str);
        toast = new Toast(BaseApplication.getI().getBaseContext());
        toast.setView(toastView);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        toastView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(toast != null && showCount == 1){
                    toast.cancel();
                    toast = null;
                }else{
                    showCount --;
                }
            }
        }, 1500);
    }


    public static View getToastView(String str) {
    TextView view = (TextView) LayoutInflater.from(BaseApplication.getI().getBaseContext()).inflate(R.layout.toast, null);
        view.setText(str);
        return view;
    }

}
