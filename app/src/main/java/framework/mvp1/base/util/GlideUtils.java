package framework.mvp1.base.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qbo.lawyerstar.BuildConfig;
import com.qbo.lawyerstar.R;

public class GlideUtils {

    //设置加载中以及加载失败图片
    public static void loadImageDefult(Context mContext, Object path, ImageView mImageView) {
        if(path!=null&&path.toString().contains("http://192.168.1.143")){
           path = path.toString().replace("http://192.168.1.143", BuildConfig.API_URL);
        }
        Glide.with(mContext).load(path).placeholder(R.mipmap.ic_noimage2).dontAnimate().error(R.mipmap.ic_noimage2)
                .into(mImageView);
    }

    //设置加载中以及加载失败图片
    public static void loadImageUserLogoDefult(Context mContext, String path, ImageView mImageView) {
        if(path!=null&&path.toString().contains("http://192.168.1.143")){
            path = path.toString().replace("http://192.168.1.143",BuildConfig.API_URL);
        }
        Glide.with(mContext).load(path).placeholder(R.mipmap.ic_userlogo_default_1).dontAnimate().error(R.mipmap.ic_userlogo_default_1)
                .into(mImageView);
    }

    //设置加载中以及加载失败图片
    public static void loadImageLawyerLogoDefult(Context mContext, String path, ImageView mImageView) {
        if(path!=null&&path.toString().contains("http://192.168.1.143")){
            path = path.toString().replace("http://192.168.1.143",BuildConfig.API_URL);
        }
        Glide.with(mContext).load(path).placeholder(R.mipmap.ic_userlogo_default_1).dontAnimate().error(R.mipmap.ic_userlogo_default_1)
                .into(mImageView);
    }


    //设置加载中以及加载失败图片
    public static void loadImageRoomDefult(Context mContext, String path, ImageView mImageView) {
//        Glide.with(mContext).load(path).placeholder(R.mipmap.ic_transparent).dontAnimate().error(R.mipmap.ic_transparent)
//                .into(mImageView);
        Glide.with(mContext).load(path).into(mImageView);
    }

}
