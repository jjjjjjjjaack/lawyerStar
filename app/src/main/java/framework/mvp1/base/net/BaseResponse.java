package framework.mvp1.base.net;

import com.alibaba.fastjson.JSONObject;
import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.MyApplication;

import framework.mvp1.base.bean.BaseBean;


/**
 * Created by lzj on 2017/6/3.
 */

public class BaseResponse extends BaseBean {

    public int code;
    public String orgin;
    public String msg;
    public String datas;

    public void fromJSON(String content) {
        this.orgin = content;
        try {
            JSONObject json = JSONObject.parseObject(content);
            this.code = json.getIntValue("code");
            this.datas = json.getString("data");
            this.msg = json.getString("msg");
        } catch (Exception e) {
            e.printStackTrace();
            this.code = NET_CODE.C_M1;// 解析异常
            this.datas = "";// 解析异常
            this.msg = MyApplication.getApp().getString(R.string.BaseRespones_string);
        }
    }

}
