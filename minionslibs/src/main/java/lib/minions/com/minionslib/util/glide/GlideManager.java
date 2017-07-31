package lib.minions.com.minionslib.util.glide;

/**
 * Created by chenguozhen on 2016/11/25.
 * 　eMail  1021632321@QQ.com
 */


import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.imnjh.imagepicker.ImageLoader;

import java.io.File;

import lib.minions.com.minionslib.R;
import lib.minions.com.minionslib.app.BaseApplication;


/**
 * Description:
 * User: chenzheng
 * Date: 2016/8/31 0031
 * Time: 15:43
 *
 *      -keep public class * implements com.bumptech.glide.module.GlideModule
        -keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
        **[] $VALUES;
        public *;
        }
 *
 */
public class GlideManager implements ImageLoader {

    public static final int IMG_ROUND = 8;

    //不用此方法
    @Override
    public void bindImage(ImageView imageView, Uri uri, int width, int height) {
        Glide.with(BaseApplication.getI()).load(uri).placeholder(R.drawable.empty_photo)
                .error(R.drawable.empty_photo).override(width, height).dontAnimate().into(imageView);
    }
    //不用此方法
    @Override
    public void bindImage(ImageView imageView, Uri uri) {
        Glide.with(BaseApplication.getI()).load(uri).placeholder(R.drawable.empty_photo)
                .error(R.drawable.empty_photo).dontAnimate().into(imageView);
    }

    //banner使用
//    @Override
//    public void displayImage(Context context, Object path, ImageView imageView) {
//        loadImage(context,path.toString(),R.mipmap.banner_default,R.mipmap.banner_default,imageView);
//    }

    //不用此方法
    @Override
    public ImageView createImageView(Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }
    //不用此方法
    @Override
    public ImageView createFakeImageView(Context context) {
        return new ImageView(context);
    }

    /**
     *加载图片
     */
    public static void loadImage(Context context, String url, int erroImg, int emptyImg, ImageView iv) {
        //原生 API
        Glide.with(context).load(url).placeholder(emptyImg).error(erroImg).into(iv);
    }

    /**
     *加载图片
     */
    public static void loadImage(Context context, String url,int defaultImg, ImageView iv) {
        //原生 API
        Glide.with(context).load(url).placeholder(defaultImg).error(defaultImg).into(iv);
    }

    /**
     *加载图片
     */
    public static void loadImage(Context context, String url, ImageView iv) {
        //原生 API
        Glide.with(context).load(url).crossFade().error(R.drawable.empty_photo).into(iv);
    }

    /**
     *加载 GIF 图片
     */
    public static void loadGifImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.empty_photo).error(R.drawable.empty_photo).into(iv);
    }

    /**
     *加载圆形图片
     */
    public static void loadCircleImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).placeholder(R.drawable.empty_photo).error(R.drawable.empty_photo).transform(new GlideCircleTransform(context)).into(iv);
    }

    /**
     *加载圆形图片
     */
    public static void loadCircleImage(Context context, String url, int emptyImg,int errorimg,ImageView iv) {
        Glide.with(context).load(url).placeholder(emptyImg).error(errorimg).transform(new GlideCircleTransform(context)).into(iv);
    }

    /**
     *加载圆角图片
     */
    public static void loadRoundCornerImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).placeholder(R.drawable.empty_photo).error(R.drawable.empty_photo).transform(new GlideRoundTransform(context, IMG_ROUND)).into(iv);
    }

    /**
     *加载圆角图片
     */
    public static void loadRoundCornerImage(Context context, String url, ImageView iv,int errorImg) {
        Glide.with(context).load(url).placeholder(errorImg).error(errorImg).transform(new GlideRoundTransform(context, IMG_ROUND)).into(iv);
    }

    /**
     *加载圆角图片
     */
    public static void loadRoundCornerImage(Context context, String url, ImageView iv,int errorImg,int round) {
        Glide.with(context).load(url).placeholder(errorImg).error(errorImg).transform(new GlideRoundTransform(context, round)).into(iv);
    }

    /**
     *加载文件图片
     */
    public static void loadImage(Context context, final File file, final ImageView imageView) {
        Glide.with(context)
                .load(file)
                .into(imageView);


    }


    public static void loadImage(Context context, final int resourceId, final ImageView imageView) {
        Glide.with(context)
                .load(resourceId)
                .into(imageView);
    }
}
