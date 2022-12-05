package framework.mvp1.base.net;


import com.qbo.lawyerstar.BuildConfig;

/****
 * 接口地址
 * @author Administrator
 *TODO 此项配置为PHP服务器的接口地址
 */
public class NET_URL {

    private static final NET_URL u = new NET_URL();
    //	// 获取实体
    public static NET_URL getInstance() {
        return u;
    }
    public final String API = "/20220822dgl/PHP/public/index.php?s=front/";
//    public final String API = "/front/";

    /**
     * @param
     * @return
     * @description
     * @author admin
     * @time 2021/4/1 8:53
     */
    public String getBaseUrl() {
        return BuildConfig.API_URL;
    }

    // 获取接口路径
    public String getUrl(String api) {
        return getBaseUrl() + API + api;
    }

}

