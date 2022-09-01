package framework.mvp1.base.bean;

/**
 * 登录令牌，只能使用这个类缓存用户登录信息
 */
public class FToken extends BaseBean {
    //登录令牌
    private String token;
    //用户ID
    private String id;
    //
    private String user_name;
    private String mobile;
    private String key;
    private boolean is_rz;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isIs_rz() {
        return is_rz;
    }

    public void setIs_rz(boolean is_rz) {
        this.is_rz = is_rz;
    }
}
