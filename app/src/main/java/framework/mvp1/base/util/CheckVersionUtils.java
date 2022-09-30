package framework.mvp1.base.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.MyApplication;
import com.qbo.lawyerstar.app.net.REQ_Factory;

import java.io.File;

import framework.mvp1.base.bean.BaseBean;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;
import framework.mvp1.views.pop.PopupVersionSelectView;

public class CheckVersionUtils {

    private static CheckVersionUtils checkVersionUtil;
    public static Integer key = new Integer(2);//锁

    private CheckVersionUtils() {
    }

    public static CheckVersionUtils getInstance() {
        //先判断该user变量是否为空，入股为空，进入同步代码块，该步假设为step1
        if (checkVersionUtil == null) {    //step 1     //想象一下，如果不判断，那么每次访问这个方法不管该对象是否已经创建都要进入同步代码块，线程数一多，资源消耗也是非常巨大的。
            synchronized (key) {
                //由于可能多个线程都进入了step1,由于锁定机制，一个线程进入该代码块时，其他线程
                //仍在排队进入该代码块，如果不做判断，当前线程即使创造了实例，下一个线程也不知道，就会继续创建一个实例
                if (checkVersionUtil == null) {
                    checkVersionUtil = new CheckVersionUtils();
                }
            }
        }
        return checkVersionUtil;
    }


    public final String apiToken = "386866f110f4bae7e10f30ee87c8caa7";


    final String ERRO = "-1";
    final String DONOT_UPDATE = "0";
    final String CAN_UPDATE = "1";
    final String HAVETO_UPDATE = "2";
    private PopupVersionSelectView popupVersionSelectView;

    /**
     * 获取服务器版本信息
     */
    public void doVersionCheck(Context mContext, final View parentView, final PopupVersionSelectView.ISelectUpdate iSelectUpdate, boolean todayCheck) {
        if (popupVersionSelectView != null) {
            popupVersionSelectView.dismiss();
            popupVersionSelectView = null;
        }
        popupVersionSelectView = new PopupVersionSelectView((Activity) mContext, iSelectUpdate);
//        new REQ_Factory
//                .POST_CHECK_APPVERSION_REQ("006e89a2918f337efd885e9a1ee672e9")
        BasePresent.doStaticCommRequest(mContext, new REQ_Factory
                .GET_ABOUT_US_INFO_REQ(), false, false, new BasePresent.DoCommRequestInterface<BaseResponse, VersionBean>() {
            @Override
            public void doStart() {

            }

            @Override
            public VersionBean doMap(BaseResponse baseResponse) {
                VersionBean bean = VersionBean.fromJSONAuto(baseResponse.datas, VersionBean.class);
                return bean;
            }

            @Override
            public void onSuccess(VersionBean versionBean) throws Exception {
                if (ToolUtils.isNull(versionBean.android_version_no)) {
                    iSelectUpdate.erro();
                    return;
                }
                if ("0".equals(versionBean.android_update_check)) {//不需要检测更新
                    iSelectUpdate.doNotUpdate();
                    return;
                }
                if ("0".equals(versionBean.android_update) && todayCheck) {//不需要强制更新且判断是否当天不再提醒
                    String nowDate = ToolUtils.timestamp2String(System.currentTimeMillis(), "yyyy-MM-dd");
                    String cacheDate = JnCache.getCache(mContext, PopupVersionSelectView.CHECK_VERSION_KEY);
                    if (nowDate.equals(cacheDate)) {
                        iSelectUpdate.doNotUpdate();
                        return;
                    }
                }
                // 得到当前已安装的app版本信息
                String currentVersionCode = "";
                PackageManager manager = MyApplication.getApp().getPackageManager();
                PackageInfo info = null;
                info = manager.getPackageInfo(MyApplication.getApp().getPackageName(), 0);
                if (info == null) {
                    iSelectUpdate.erro();
                    return;
                } else {
                    currentVersionCode = (info.versionCode + "").trim();
                }
                if (currentVersionCode.equals(versionBean.android_version_no)) {
                    iSelectUpdate.doNotUpdate();
                } else {
//                    popupVersionSelectView.setContentMsg(versionBean.changelog);
//                    popupVersionSelectView.setDownLoadUrl(versionBean.install_url);
                    popupVersionSelectView.setVersionBean(versionBean);
                    popupVersionSelectView.showPop(parentView);
                }
            }

            @Override
            public void onError(Throwable e) {
                iSelectUpdate.erro();
            }
        });
    }

    public void readyDownload(final Context mContext) {
        final AlertDialog.Builder wifiBuilder = new AlertDialog.Builder(
                mContext);
        wifiBuilder.setPositiveButton(
                mContext.getResources().getString(
                        R.string.yes),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which) {
                        downloadApp(mContext);
                    }
                });
//        wifiBuilder.setNegativeButton(
//                MyApplication.getInstance2().getContext().getResources().getString(
//                        R.string.no),
//                new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(
//                            DialogInterface dialog,
//                            int which) {
//                        //直接进入
//                    }
//                });
        wifiBuilder.setMessage(MyApplication.getApp().getResources()
                .getString(R.string.wifitip));

        AlertDialog alertDialog = wifiBuilder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
    }

    /**
     * 根据网址用浏览器打开
     */
    public void downloadApp(Context mContext) {
        final ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("安装包下载中");
        dialog.setMax(100);
        dialog.show();
        DownloadUtil downloadUtil = DownloadUtil.get();
        downloadUtil.download(popupVersionSelectView.downLoadUrl, "", "apkdownload", new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                if (mContext == null || ((Activity) mContext).isDestroyed()) {
                    return;
                }
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        T.showShort(mContext, "下载完成,正打开安装程序");
                        installApk(mContext, file.getPath());
//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
////                            Uri uri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileprovider", file);
//                            Uri uri = FileProviderUtil.getUri(mContext, file);
//                            intent.setDataAndType(uri, "application/vnd.android.package-archive");
//                        } else {
//                            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//                        }
////                        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//                        mContext.startActivity(intent);
                    }
                });
            }

            @Override
            public void onDownloading(int progress) {
                dialog.setProgress(progress);
            }

            @Override
            public void onDownloadFailed() {
                if (mContext == null || ((Activity) mContext).isDestroyed()) {
                    return;
                }
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        T.showShort(mContext, "下载失败，尝试用浏览器打开下载");
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        intent.setData(Uri.parse(popupVersionSelectView.downLoadUrl));
                        mContext.startActivity(intent);
                    }
                });
            }
        });
    }


    private void installApk(Context mContext, String path) {
        File file = new File(path);
        if (file.exists()) {
            Intent installApkIntent = new Intent();
            installApkIntent.setAction(Intent.ACTION_VIEW);
            installApkIntent.addCategory(Intent.CATEGORY_DEFAULT);
            installApkIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //适配8.0需要有权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                boolean hasInstallPermission = mContext.getPackageManager().canRequestPackageInstalls();
                if (hasInstallPermission) {
                    //安装应用
                    installApkIntent.setDataAndType(FileProviderUtil.getUri(mContext, file), "application/vnd.android.package-archive");
                    installApkIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    if (mContext.getPackageManager().queryIntentActivities(installApkIntent, 0).size() > 0) {
                        mContext.startActivity(installApkIntent);
                    }
                } else {
                    //跳转至“安装未知应用”权限界面，引导用户开启权限
                    Uri selfPackageUri = Uri.parse("package:" + mContext.getPackageName());
                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, selfPackageUri);
                    ((Activity) mContext).startActivityForResult(intent, 2003);
                }
            } else {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    installApkIntent.setDataAndType(FileProviderUtil.getUri(mContext, file), "application/vnd.android.package-archive");
                    installApkIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                } else {
                    installApkIntent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                }
                if (mContext.getPackageManager().queryIntentActivities(installApkIntent, 0).size() > 0) {
                    mContext.startActivity(installApkIntent);
                }
            }
        }
    }


    public static class VersionBean extends BaseBean {
        /**
         * upload_log : 优化
         * android_logo : https://www.fatianping.com//static/upload/image/20220930/517d501e9aac19f5313272cb350ffff3.png
         * android_name : 法天平
         * download_url : https://www.fatianping.com/
         * android_intro :
         * android_update : 0
         * android_version : v1.0
         * android_version_no : 1.0
         * android_update_check : 1
         * consumer_hotline : 0769-864951852
         */

        public String upload_log;
        public String android_logo;
        public String android_name;
        public String download_url;
        public String android_intro;
        public String android_update;
        public String android_version;
        public String android_version_no;
        public String android_update_check;
        public String consumer_hotline;

        public String getUpload_log() {
            return upload_log;
        }

        public void setUpload_log(String upload_log) {
            this.upload_log = upload_log;
        }

        public String getAndroid_logo() {
            return android_logo;
        }

        public void setAndroid_logo(String android_logo) {
            this.android_logo = android_logo;
        }

        public String getAndroid_name() {
            return android_name;
        }

        public void setAndroid_name(String android_name) {
            this.android_name = android_name;
        }

        public String getDownload_url() {
            return download_url;
        }

        public void setDownload_url(String download_url) {
            this.download_url = download_url;
        }

        public String getAndroid_intro() {
            return android_intro;
        }

        public void setAndroid_intro(String android_intro) {
            this.android_intro = android_intro;
        }

        public String getAndroid_update() {
            return android_update;
        }

        public void setAndroid_update(String android_update) {
            this.android_update = android_update;
        }

        public String getAndroid_version() {
            return android_version;
        }

        public void setAndroid_version(String android_version) {
            this.android_version = android_version;
        }

        public String getAndroid_version_no() {
            return android_version_no;
        }

        public void setAndroid_version_no(String android_version_no) {
            this.android_version_no = android_version_no;
        }

        public String getAndroid_update_check() {
            return android_update_check;
        }

        public void setAndroid_update_check(String android_update_check) {
            this.android_update_check = android_update_check;
        }

        public String getConsumer_hotline() {
            return consumer_hotline;
        }

        public void setConsumer_hotline(String consumer_hotline) {
            this.consumer_hotline = consumer_hotline;
        }

        /**
         * name :
         * version : 1
         * "changelog":"1、修复部分已发现的Bug。\n2、对接资产模块绑定与解绑银行卡。\n3、对接资产模块提现功能。"
         * updated_at : 1645514805
         * versionShort : 1.0
         * build : 1
         * installUrl : https://download.bq04.com/apps/62148f080d81cc74a9abed73/install?download_token=7ac25ba813f2a9ad2104f3ad92d4d83e&source=update
         * install_url : https://download.bq04.com/apps/62148f080d81cc74a9abed73/install?download_token=7ac25ba813f2a9ad2104f3ad92d4d83e&source=update
         * direct_install_url : https://download.bq04.com/apps/62148f080d81cc74a9abed73/install?download_token=7ac25ba813f2a9ad2104f3ad92d4d83e&source=update
         * update_url : https://d.6short.com/f9s3
         * binary : {"fsize":66738502}
         */


    }
}



