package lib.minions.com.minionslib.app;

import android.os.Environment;

import java.io.File;

/**
 * Created by tian on 2017/5/31.
 */

public class Constants {

    private static final String APP_NAME = "app_name";
    private static final String APP_IMAGE = "images";
    private static final String APP_FILE = "file";
    private static final String APP_IMAGE_CACHE = "cache";

    /**
     * 本应用的文件图片都放到这个路径
     */
    public static final String APPDIR = Environment.getExternalStorageDirectory() +
            File.separator + APP_NAME + File.separator;
    /**
     * 下载图片文件夹
     */
    public static final String DIR_IMAGE = APPDIR + APP_IMAGE + File.separator;
    /**
     * 下载文件文件夹
     */
    public static final String DIR_FILE = APPDIR + APP_FILE + File.separator;
    /**
     * 缓存图片文件夹
     */
    public static final String DIR_IMAGE_CACHE = DIR_IMAGE + APP_IMAGE_CACHE + File.separator;


}
