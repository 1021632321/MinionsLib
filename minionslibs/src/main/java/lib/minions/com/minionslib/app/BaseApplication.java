package lib.minions.com.minionslib.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.imnjh.imagepicker.PickerConfig;
import com.imnjh.imagepicker.SImagePicker;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lib.minions.com.minionslib.util.glide.GlideManager;

/**
 * Created by chenguozhen on 2017/7/28.
 * 　eMail  1021632321@QQ.com
 */

public class BaseApplication extends Application {

    private static BaseApplication i;
    private Context context = this;

    @Override
    public void onCreate() {
        super.onCreate();
        i = this;
        makeAppFolder();
        initAutoLayout();
        initImagePicker();

    }


    private void initImagePicker() {
        SImagePicker.init(new PickerConfig.Builder()
                .setAppContext(this)
                .setImageLoader(new GlideManager())
                .setToolbaseColor(getResources().getColor(android.R.color.black))
                .build());

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }


    public void initAutoLayout() {
        AutoLayoutConifg.getInstance().useDeviceSize();
    }


    public static BaseApplication getI() {
        return i;
    }

    private void makeAppFolder() {
        if (!ExistSDCard()) {
            return;
        }
        String dir = Constants.APPDIR;
        File file = new File(dir);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        File fileImg = new File(Constants.DIR_IMAGE);
        if (!fileImg.isDirectory()) {
            fileImg.mkdirs();
        }
        File fileFile = new File(Constants.DIR_FILE);
        if (!fileFile.isDirectory()) {
            fileFile.mkdirs();
        }
        File fileCache = new File(Constants.DIR_IMAGE_CACHE);
        if (!fileCache.isDirectory()) {
            fileCache.mkdirs();
        }
    }

    private boolean ExistSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    private List<Activity> activities = new ArrayList<>();

    public void addActivity(Activity activity) {
        if (!activities.contains(activity)) {
            activities.add(activity);
        }
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public void exit() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }

}
