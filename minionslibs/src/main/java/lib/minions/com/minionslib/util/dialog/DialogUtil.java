package lib.minions.com.minionslib.util.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;

/**
 * Created by chenguozhen on 2017/2/15.
 * 　eMail  1021632321@QQ.com
 */

public class DialogUtil {

    /**
     * 加载中 转圈圈的对话框
     */
    public static LoadingDialog creatLoadingDialog(Context context){
        return new LoadingDialog(context);
    }

    /**
     * 加载中 转圈圈的对话框
     */
    public static LoadingDialog creatLoadingDialog(Context context,int themeID){
        return new LoadingDialog(context,themeID);
    }

    /**
     * 标题 内容 加左右按钮的对话框
     */
    public static CenterDialog creatCenterDialog(Context context){
        return new CenterDialog(context);
    }

    /**
     * 标题 内容 加左右按钮的对话框
     */
    public static Dialog creatCenterDialog(Context context,View view){
        return new CenterDialog(context).creatCenterDialog(view);
    }

    /**
     * 标题 内容 加左右按钮的对话框
     */
    public static CenterDialog creatCenterDialog(Context context, int themeId){
        return new CenterDialog(context,themeId);
    }



    /**
     * 拍照选择  图片选择  类型的底部Dialog
     */
    public static ButtomDialog creatbuttomDialog(Context context){
        return new ButtomDialog(context).builder();
    }

    public static Dialog creatButtomDialog(Context context, View view){
        return new ButtomDialog(context).creatButtomDialog(view);
    }

    public static Dialog creatButtomDialog(Context context, View view, Effectstype effectstype){
        return new ButtomDialog(context).creatButtomDialog(view,effectstype);
    }

    public static void creatKickDialog(Context context,String info, Class activity){
        Intent i=new Intent(context,KickDialog.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String name = activity.getName();
        i.putExtra("activity",name);
        i.putExtra("content",info);
        context.startActivity(i);
    }

}
