package lib.minions.com.minionslib.util.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.effects.BaseEffects;

import lib.minions.com.minionslib.R;
import lib.minions.com.minionslib.util.DisplayUtil;
import lib.minions.com.minionslib.util.MyUtils;


/**
 * Created by chenguozhen on 2017/2/15.
 * 　eMail  1021632321@QQ.com
 */

public class CenterDialog extends Dialog implements View.OnClickListener{

    private String title;
    private int defaultTitleColor;
    private int defaultTtileTextSize;

    private String contentInfo;
    private int defaultContentColor;
    private int defaultContentTextSize;

    private int defaultButtonTextSize = -1;

    private String defaultLeftBtnText;
    private int defaultLeftBtnColor;

    private String defaultRightBtnText;
    private int defaultRightBtnColor;

    private TextView mTitle, mContent,mLeft,mRight;
    private int defaultTypedValue;
    private Effectstype effectstype;
    private int defaultDuration;
    private View view;
    private ICenterDialogBack iCenterDialogBack;
    private Context context;
    private boolean hideLeftBtn;

    public CenterDialog(Context context) {
        super(context, R.style.MyDialogStyle);
        init(context);
    }

    public CenterDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        defaultTypedValue = TypedValue.COMPLEX_UNIT_DIP;
        effectstype = Effectstype.RotateBottom;
        defaultDuration = 300;

        defaultTitleColor = R.color.text_333;
        defaultTtileTextSize = 16;

        defaultContentColor = R.color.text_999;
        defaultContentTextSize = 14;

        defaultLeftBtnText = "否";
        defaultLeftBtnColor = R.color.text_333;

        defaultRightBtnText = "是";
        defaultRightBtnColor = R.color.appColor;

        this.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                if(null != effectstype){
                    BaseEffects animator = effectstype.getAnimator();
                    animator.setDuration(defaultDuration);
                    animator.start(view);
                }
            }
        });

        view = LayoutInflater.from(context).inflate(R.layout.center_dialog, null);
        LinearLayout lin_ss = (LinearLayout) view.findViewById(R.id.lin_ss);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) lin_ss.getLayoutParams();
        int screenWidth = DisplayUtil.getScreenWidth(context);
        /*宽度为屏幕宽度的 0.7 */
        layoutParams.width = screenWidth / 10 * 7;
        lin_ss.setLayoutParams(layoutParams);
        mTitle = (TextView) view.findViewById(R.id.dialog_title);
        mContent = (TextView) view.findViewById(R.id.dialog_content);
        mLeft = (TextView) view.findViewById(R.id.btn_Cancle);
        mRight = (TextView) view.findViewById(R.id.btn_Confirm);
        mLeft.setOnClickListener(this);
        mRight.setOnClickListener(this);
        setContentView(view);

    }

    public Dialog creatCenterDialog(final View view){
        Dialog dialog = new Dialog(context, R.style.MyDialogStyle);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = DisplayUtil.getScreenWidth(context)/10 * 7;
        dialogWindow.setAttributes(lp);
        dialog.setContentView(view );
        dialog.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                if(null != effectstype){
                    BaseEffects animator = effectstype.getAnimator();
                    animator.setDuration(300);
                    animator.start(view);
                }
            }
        });
        return dialog;
    }

    public void show(ICenterDialogBack buttonClick) {
        iCenterDialogBack = buttonClick;
        if(!MyUtils.isEmptyString(title)){
            mTitle.setText(title);
        }
        mTitle.setTextSize(defaultTypedValue,defaultTtileTextSize);
        mContent.setTextSize(defaultTypedValue,defaultContentTextSize);
        if(!MyUtils.isEmptyString(contentInfo)){
            mContent.setText(contentInfo);
            mContent.setVisibility(View.VISIBLE);
        }else{
            mContent.setVisibility(View.GONE);
        }
        mLeft.setText(defaultLeftBtnText);
        mRight.setText(defaultRightBtnText);

        if(defaultButtonTextSize != -1){
            mLeft.setTextSize(defaultTypedValue,defaultButtonTextSize);
            mRight.setTextSize(defaultTypedValue,defaultButtonTextSize);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTitle.setTextColor(context.getColor(defaultTitleColor));
            mContent.setTextColor(context.getColor(defaultContentColor));
            mLeft.setTextColor(context.getColor(defaultLeftBtnColor));
            mRight.setTextColor(context.getColor(defaultRightBtnColor));
        }else{
            mTitle.setTextColor(context.getResources().getColor(defaultTitleColor));
            mContent.setTextColor(context.getResources().getColor(defaultContentColor));
            mLeft.setTextColor(context.getResources().getColor(defaultLeftBtnColor));
            mRight.setTextColor(context.getResources().getColor(defaultRightBtnColor));
        }
        if (hideLeftBtn){
            mLeft.setVisibility(View.GONE);
        }

        super.show();

    }

    public CenterDialog setTitle(String title){
        this.title = title;
        return this;
    }

    public CenterDialog setContentInfo(String contentInfo){
        this.contentInfo = contentInfo;
        return this;
    }

    public CenterDialog setLeftText(String str){
        this.defaultLeftBtnText = str;
        return  this;
    }

    public CenterDialog setRightBtnText(String str){
        this.defaultRightBtnText = str;
        return  this;
    }

    public CenterDialog hideLeftBtn(){
        hideLeftBtn = true;
        return this;
    }

    public CenterDialog setSystemType(){
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        return this;
    }

    public CenterDialog setEffect(Effectstype effect){
        this.effectstype = effect;
        return this;
    }

    public CenterDialog setCanCanceledOnTouchOutSide(boolean cancel){
        this.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public CenterDialog setCanCanceled(boolean cancel){
        this.setCancelable(cancel);
        return this;
    }

    public CenterDialog setView(View view){
        this.view = view;
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.height = ScreenUtil.getScreenHeight(context)/10 * 7;
        dialogWindow.setAttributes(lp);
        setContentView(view);
        return this;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_Cancle) {
            boolean b = iCenterDialogBack.leftClock();
            if(b){
                cancel();
            }
        } else if (i == R.id.btn_Confirm) {
            boolean b = iCenterDialogBack.rightBack();
            if(b){
                cancel();
            }
        }
    }

    public CenterDialog setLeftBtnTextColor(int colorRes){
        this.defaultLeftBtnColor = colorRes;
        return  this;
    }

    public CenterDialog setRightBtnTextColor(int colorRes){
        this.defaultRightBtnColor = colorRes;
        return  this;
    }
}
