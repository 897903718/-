package chanlytech.com.laborsupervision.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by Lyy on 2015/7/14.
 * 加载网络图片工具类
 */
public class Imageload {
    private static ImageLoader mImageLoader;
    private static ImageLoaderConfiguration mImageLoaderConfiguration;//图片配置
    private static DisplayImageOptions mDisplayImageOptions;//显示图片配置，圆角，加载失败

    //显示圆角的图片
    public static void loadImageRound(Context context, int Rounded, String imagepath, final ImageView mImageView) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "laodongshebao/ImagCache");
        mImageLoaderConfiguration = new ImageLoaderConfiguration.Builder(context).threadPoolSize(2)//线程池内加载的数量
                .memoryCacheExtraOptions(480, 800) // maxwidth, max height，即保存的每个缓存文件的最大长宽
////            .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
//              .memoryCacheSize(2 * 1024 * 1024)//缓存内存大小设置
//              .discCacheSize(50 * 1024 * 1024)//缓存大小设置
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//缓存路径MD5加密
                .discCacheFileCount(100)//缓存文件数量
                .discCache(new UnlimitedDiscCache(cacheDir))//缓存路径
                .build();
        mImageLoader = ImageLoader.getInstance();
        if (!mImageLoader.isInited()) {
            ImageLoader.getInstance().init(mImageLoaderConfiguration);
        }

        mDisplayImageOptions = new DisplayImageOptions.Builder()
//               .showImageOnFail(R.mipmap.ic_list_default)
                .bitmapConfig(Bitmap.Config.RGB_565)
//              .showStubImage(R.mipmap.ic_list_default)//加载中的图片
//              .cacheInMemory(true)                               //启用内存缓存
//              .cacheOnDisc(true)//是否缓存到sd卡中，启用缓存
                .displayer(new RoundedBitmapDisplayer(Rounded))//是否圆角显示
//              .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .build();
        mImageLoader.displayImage(imagepath, mImageView, mDisplayImageOptions, new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String s, View view) {
                //开始加载
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                //加载失败
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                //加载完成,按照图片比列来缩放图片
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                //加载取消的时候执行
            }
        });
    }

    //加载不是圆角的图片
    public static void LoadImag(Context context, String imagpath, ImageView mImageView) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "laodongshebao/ImagCache");
        mImageLoaderConfiguration = new ImageLoaderConfiguration.Builder(context).threadPoolSize(2)//线程池内加载的数量
                .memoryCacheExtraOptions(480, 800) // maxwidth, max height，即保存的每个缓存文件的最大长宽
////            .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
//              .memoryCacheSize(2 * 1024 * 1024)//缓存内存大小设置
//              .discCacheSize(50 * 1024 * 1024)//缓存大小设置
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//缓存路径MD5加密
                .discCacheFileCount(100)//缓存文件数量
                .discCache(new UnlimitedDiscCache(cacheDir))//缓存路径
                .build();
        mImageLoader = ImageLoader.getInstance();
        if (!mImageLoader.isInited()) {
            ImageLoader.getInstance().init(mImageLoaderConfiguration);
        }
        mDisplayImageOptions = new DisplayImageOptions.Builder()
//              .showImageOnFail(R.mipmap.ic_list_default)
                .bitmapConfig(Bitmap.Config.RGB_565)
//              .showStubImage(R.mipmap.ic_list_default)//加载中的图片
//              .cacheInMemory(true)                               //启用内存缓存
                .cacheOnDisc(true)//是否缓存到sd卡中，启用缓存
//              .displayer(new RoundedBitmapDisplayer(5))//是否圆角显示
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();

        mImageLoader.displayImage(imagpath, mImageView, mDisplayImageOptions, new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String s, View view) {
                //开始加载
            }
            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                //加载失败
            }
            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                //加载完成,按照图片比列来缩放图片
            }
            @Override
            public void onLoadingCancelled(String s, View view) {
                //加载取消的时候执行
            }
        });
    }


    /**
     * 清除缓存
     */
    public static void cleanCache() {
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.clearDiscCache();
        mImageLoader.clearMemoryCache();
    }
}
