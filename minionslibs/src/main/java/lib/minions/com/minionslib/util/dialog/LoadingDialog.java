package lib.minions.com.minionslib.util.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.effects.BaseEffects;

import lib.minions.com.minionslib.R;


/**
 * Created by chenguozhen on 2017/2/15.
 * 　eMail  1021632321@QQ.com
 */

public class LoadingDialog extends Dialog {

    private View view;
    private ImageView img;
    private TextView loadTv;
    private Context context;
    private Effectstype effectstype;
    private int defaultDuration;

    public LoadingDialog(Context context) {

        super(context, R.style.MyDialogStyle);
        init(context);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.loading_dialog,null);
        img = (ImageView) view.findViewById(R.id.img);
        loadTv = (TextView) view.findViewById(R.id.loading_tv);
        effectstype = Effectstype.RotateBottom;
        defaultDuration = 300;
        setContentView(view);

        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                if(null != effectstype){
                    BaseEffects animator = effectstype.getAnimator();
                    animator.setDuration(defaultDuration);
                    animator.start(view);
                }
            }
        });

        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (img != null){
                    img.clearAnimation();
                }
            }
        });
    }

    public LoadingDialog setEffect(Effectstype effect){
        this.effectstype = effect;
        return this;
    }

    public LoadingDialog setCanCanceledOnTouchOutSide(boolean cancel){
        this.setCanceledOnTouchOutside(cancel);
        return this;
    }
    public LoadingDialog setCanCanceled(boolean cancel){
        this.setCancelable(cancel);
        return this;
    }

    @Override
    public void show(){
//        RotateAnimation rotateAnimation = new RotateAnimation(
//            0,
//            355,
//            RotateAnimation.RELATIVE_TO_SELF,
//            0.5F,
//            RotateAnimation.RELATIVE_TO_SELF,
//            0.5F);
//        rotateAnimation.setRepeatMode(Animation.RESTART);
//        rotateAnimation.setRepeatCount(-1);
//        rotateAnimation.setDuration(1000);
//        rotateAnimation.setInterpolator(new LinearInterpolator());
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
        img.startAnimation(animation);
        super.show();
    }

    /**
     * 显示提示文字
     */
    public void show(String text){
        loadTv.setText(text);
        loadTv.setVisibility(View.VISIBLE);
        show();
    }

}
