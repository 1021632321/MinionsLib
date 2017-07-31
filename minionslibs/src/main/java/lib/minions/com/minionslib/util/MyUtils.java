package lib.minions.com.minionslib.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lib.minions.com.minionslib.app.Constants;


/**
 * 七七八八的工具类
 *
 * @author chen
 */
public class MyUtils {

    /**
     * 动态设置ListView的高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null)
            return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 获取本地APP版本号
     *
     * @param context
     * @return
     */
    public static int getLocalVerCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("msg", e.getMessage());
        }
        return verCode;
    }

    /**
     * 获取版本名称
     *
     * @param context
     * @return
     */
    public static String getLocalVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("msg", e.getMessage());
        }
        return verName;
    }

    /**
     * 检查是否存在SDCard
     *
     * @return
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    private static final boolean isShowLog = true;

    /***
     * 打印日志 级别 E .
     *
     * @param message TAG = MyUtils_showlog =
     */
    public static void showLog(String tag, Object message) {
        if (isShowLog) {
            Log.e(tag, tag + " = " + message.toString());
        }
    }

    public static String decode(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }
        StringBuffer retBuf = new StringBuffer();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicodeStr.charAt(i) == '\\') {
                if ((i < maxLoop - 5) && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr.charAt(i + 1) == 'U')))
                    try {
                        retBuf.append((char) Integer.parseInt(unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(unicodeStr.charAt(i));
                    }
                else
                    retBuf.append(unicodeStr.charAt(i));
            } else {
                retBuf.append(unicodeStr.charAt(i));
            }
        }
        return retBuf.toString();
    }

    /**
     * 从游标里获取String类型数据
     */
    public static String getSFromCursor(Cursor cursor, String key) {
        return cursor.getString(cursor.getColumnIndex(key));
    }


    /**
     * 从游标里获取Int类型数据
     */
    public static int getIFromCursor(Cursor cursor, String key) {
        return cursor.getInt(cursor.getColumnIndex(key));
    }

    /**
     * 从ViewHolder中获取View
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T getViewFromVH(View convertView, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            convertView.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = convertView.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    /**
     * 如果该字符串为空 则return掉
     */
    public static boolean isEmptyString(String json) {
        if (TextUtils.isEmpty(json)) {
            return true;
        }

        if (TextUtils.isEmpty(json.trim())) {
            return true;
        }

        if ("null".equals(json.trim())) {
            return true;
        }

        if ("NULL".equals(json.trim())) {
            return true;
        }

        return false;
    }

    // 判断手机格式是否正确
    public static boolean isMobileNum(String mobiles) {
        if (!isEmptyString(mobiles) && mobiles.length() == 11) {
            String subSequence = mobiles.subSequence(0, 1).toString();
            if ("1".equals(subSequence)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    // //判断手机格式是否正确
    // public static boolean isMobileNum(String mobiles) {
    // Pattern p = Pattern
    // .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
    // Matcher m = p.matcher(mobiles);
    //
    // return m.matches();
    // }

    /**
     * 判断email格式是否正确
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    /**
     * 判断网络地址格式是否正确
     */
    public static boolean isURL(String URL) {
        String str = "^((https|http|ftp|rtsp|mms)?://)"
                + "?(([0-9a-zA-Z_!~*'().&=+$%-]+: )?[0-9a-zA-Z_!~*'().&=+$%-]+@)?" // ftp的user@
                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
                + "|" // 允许IP和DOMAIN（域名）
                + "([0-9a-zA-Z_!~*'()-]+\\.)*" // 域名- www.
                + "([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z]\\." // 二级域名
                + "[a-zA-Z]{2,6})" // first level domain- .com or .museum
                + "(:[0-9]{1,4})?" // 端口- :80
                + "((/?)|" + "(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(URL);

        return m.matches();
    }

    /**
     * 从将bitmap装换成file文件
     */
    public static File getImageFileFromBitmap(Bitmap bitmap, String filename) {
        if (bitmap == null) {
            return null;
        }
        File file = new File(Constants.DIR_IMAGE);
        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File(Constants.DIR_IMAGE_CACHE, filename);

        BufferedOutputStream bos = null;
        try {

            bos = new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            return null;
        }
        bitmap.compress(CompressFormat.JPEG, 100, bos);
        try {
            bos.flush();
            bos.close();
        } catch (IOException e) {
            return null;
        }

        return file;

    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 从路径中获取图片文件
     * */
    // @SuppressWarnings("resource")
    // public static File getImageFileFromPath(String path)
    // {
    //
    // if (TextUtils.isEmpty(path))
    // {
    // return null;
    // }
    // File file = new File(path);
    // String name = file.getName();
    //
    // File cachefile = new File(Constans.DIR_IMAGE_CACHE + name);
    // if (cachefile != null && cachefile.exists()) {
    // return cachefile ;
    // }
    // BufferedInputStream in = null;
    // try
    // {
    // in = new BufferedInputStream(new FileInputStream(new File(path)));
    // if (in.available() <= 150) {
    // return file ;
    // }
    // }
    // catch (FileNotFoundException e1)
    // {
    // return null ;
    // } catch (IOException e) {
    // return null ;
    // }
    // BitmapFactory.Options options = new BitmapFactory.Options();
    // options.inJustDecodeBounds = true;
    // BitmapFactory.decodeStream(in, null, options);
    // Bitmap decodeStream = null;
    //
    // double ratio = Math.max(options.outWidth * 1.0d / 1024f,
    // options.outHeight * 1.0d / 1024f);
    //
    // options.inSampleSize = (int) Math.ceil(ratio);
    // options.inJustDecodeBounds = false;
    // decodeStream = BitmapFactory.decodeStream(in, null, options);
    //
    // if (decodeStream == null) {
    // return null ;
    // }
    //
    // int degree = 0;
    // try {
    // ExifInterface exifInterface = new ExifInterface(path);
    // int orientation =
    // exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
    // ExifInterface.ORIENTATION_NORMAL);
    // switch (orientation) {
    // case ExifInterface.ORIENTATION_ROTATE_90:
    // degree = 90;
    // break;
    // case ExifInterface.ORIENTATION_ROTATE_180:
    // degree = 180;
    // break;
    // case ExifInterface.ORIENTATION_ROTATE_270:
    // degree = 270;
    // break;
    // }
    // } catch (IOException e) {
    // return null ;
    // }
    // //修正角度
    // Matrix matrix = new Matrix();
    // matrix.postRotate(degree);
    // Bitmap resizedBitmap = Bitmap.createBitmap(decodeStream, 0, 0,
    // decodeStream.getWidth(), decodeStream.getHeight(), matrix, true);
    // //bitma转换成文件
    // return compressImageFileSize(resizedBitmap, cachefile);
    //
    // }

    /**
     * 将bitmap转换成文件
     */
    public static File getFileFromBitmap(Bitmap bitmap, File file) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
            bitmap.compress(CompressFormat.JPEG, 100, fileOutputStream);
            return file;
        } catch (FileNotFoundException e) {
            Log.e("myUtils", "compressImageFileSize---FileNotFoundException");
            return null;
        }
    }

    /**
     * @param resizedBitmap
     * @param cachefile
     */
    public static File compressImageFileSize(Bitmap resizedBitmap,
                                             File cachefile) {

        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(cachefile);
            resizedBitmap.compress(CompressFormat.JPEG, 20, fileOutputStream);
            resizedBitmap.recycle();
            return cachefile;
        } catch (FileNotFoundException e) {
            Log.e("myUtils", "compressImageFileSize---FileNotFoundException");
            return null;
        }
    }

    /**
     * 获取文件大小
     */
    @SuppressWarnings("resource")
    public static long getFileSize(File file) {
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                return 0;
            }
            try {
                return fis.available();
            } catch (IOException e) {
                return 0;
            }
        } else {
            return 0;
        }
    }

    /**
     * 隐藏输入法
     */
    public static void hideIMM(Context c, View v) {
        InputMethodManager imm = (InputMethodManager) c
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        ;
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    /**
     * 打开输入法
     */
    public static void opendIMM(final EditText v) {
        v.requestFocus();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                InputMethodManager inputManager =

                        (InputMethodManager) v.getContext().getSystemService(
                                Context.INPUT_METHOD_SERVICE);

                inputManager.showSoftInput(v, 0);

            }
        }, 100);
    }

    /**
     * 把dp值转换成px像素
     */
    public static float getPXfromDP(float dp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    /**
     * 判断是不是WIFI
     */
    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }


    /**
     * 文件重命名
     */
    public static boolean renameToNewFile(String file, String toFile) {
        File toBeRenamed = new File(file);
        //检查要重命名的文件是否存在，是否是文件
        if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {
            return false;
        }

        File newFile = new File(toFile);

        //修改文件名
        if (toBeRenamed.renameTo(newFile)) {
            return true;
        }
        return true;
    }

    public static Bitmap getDiskBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }


        return bitmap;
    }

    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            file.delete();
        }
    }


    public static void hideKeyboard(Context context) {
        //  软键盘无得到context
        try {
            InputMethodManager immMain = ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE));
//            immMain.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

            immMain.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
