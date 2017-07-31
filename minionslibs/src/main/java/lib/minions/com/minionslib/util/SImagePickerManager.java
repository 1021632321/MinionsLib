package lib.minions.com.minionslib.util;

import android.app.Activity;
import android.content.Intent;

import com.imnjh.imagepicker.CapturePhotoHelper;
import com.imnjh.imagepicker.FileChooseInterceptor;
import com.imnjh.imagepicker.SImagePicker;
import com.imnjh.imagepicker.activity.CropImageActivity;
import com.imnjh.imagepicker.activity.PhotoPickerActivity;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by chenguozhen on 2017/2/7.
 * 　eMail  1021632321@QQ.com
 * 该工具需要依赖  compile 'com.github.martin90s:ImagePicker:v1.2'
 * 一个简便的图片选择工具
 * 提供 拍照裁剪 多选  多选带筛选条件
 */

public class SImagePickerManager {

    static SImagePickerManager manager;
    private static Activity mContext;
    private CapturePhotoHelper helper;
    private String photoPath;
    private int cutPhotoRequestCode = 10086;
    private boolean isCamera;

    private SImagePickerManager(){
    }

    public static SImagePickerManager getManager(){
        if (manager == null){
            manager = new SImagePickerManager();
        }
        return manager;
    }

    /**
     *单选图片
     *调用摄像头拍照并裁剪成头像
     * @param photoPath 为缓存路径地址  不需要文件名
     */
    public void asCamera(Activity activity, String photoPath, int requestCode){
        cutPhotoRequestCode = requestCode;
        this.photoPath = photoPath;
        mContext = activity;
        helper = new CapturePhotoHelper(activity);
        helper.setPhoto(photoPath + System.currentTimeMillis() +  ".jpg");
        helper.capture();
    }

    /**
     * 单选图片并裁剪
     * 返回值 data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
     * 返回值字段 "EXTRA_RESULT_SELECTION"
     * @param photoPath 为缓存路径地址  不需要文件名
     */
    public void asPicture(Activity activity, String photoPath, int requestCode){
        cutPhotoRequestCode = requestCode;
        SImagePicker
                .from(activity)
                .pickMode(SImagePicker.MODE_AVATAR)
                .showCamera(false)
                .cropFilePath(photoPath + System.currentTimeMillis() + ".jpg")
                .forResult(requestCode);
    }


    /**
     * 多选图片
     * 返回值为 data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
     * 返回值字段为 "EXTRA_RESULT_SELECTION"
     */
    public void selectPhoto(Activity activity, int maxSelect, int requestCode){
        cutPhotoRequestCode = requestCode;
        SImagePicker
                .from(activity)
                .maxCount(maxSelect>0?maxSelect:1)
                .rowCount(3)
                .showCamera(false)
                .pickMode(SImagePicker.MODE_IMAGE)
                .forResult(requestCode);
    }

    /**
     * 多选图片  带筛选条件  筛选条件参考
     * 返回值为 data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
     * 返回值字段为 "EXTRA_RESULT_SELECTION"
     */
    public void selectPhotoLimit(Activity activity, int maxSelect, int requestCode, FileChooseInterceptor interceptor){
        cutPhotoRequestCode = requestCode;
        SImagePicker
                .from(activity)
                .maxCount(maxSelect>0?maxSelect:1)
                .rowCount(3)
                .pickMode(SImagePicker.MODE_IMAGE)
                .fileInterceptor(interceptor)
                .forResult(requestCode);
    }

    /**
     * onActivityResult() 里调用此方法 获取裁剪后的图像 或多选后的图片
     * 如果是拍照  因为有拍照和裁剪两步 所以第一次返回值会为空的字符串，第二次为裁剪后的路径
     * 返回的文件地址就是拍照传入的地址 截图后的 requestCode 是 10086 注意冲突
     */
    public ArrayList<String> onSelectResult(int requestCode, int resultCode, Intent data){
        ArrayList<String> paths = new ArrayList<>();
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == CapturePhotoHelper.CAPTURE_PHOTO_REQUEST_CODE){
                if (helper != null){
                    File photo = helper.getPhoto();
                    if (photo.exists()){
                        String absolutePath = photo.getAbsolutePath();
                        CropImageActivity.startImageCrop(mContext, absolutePath, cutPhotoRequestCode,
                                photoPath + System.currentTimeMillis() +  ".jpg");
                        isCamera = true;
                    }
                }
                return paths;
            }else if (requestCode == cutPhotoRequestCode){
                if (isCamera){
                    //拍照
                    isCamera = false;
                    paths.add(data.getStringExtra(CropImageActivity.RESULT_PATH));
                    return paths;
                }else {
                    //单选、多选
                    return data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
                }
            }
        }
        return paths;
    }

}
