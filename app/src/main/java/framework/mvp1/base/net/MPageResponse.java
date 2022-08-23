package framework.mvp1.base.net;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import framework.mvp1.base.bean.BaseBean;

/**
 * Created by lzj on 2018/5/28.
 */

public abstract class MPageResponse<T extends BaseBean> extends BaseResponse {

    public int total;
    public List<T> rows;

    public abstract Class<T> getBeanType();

    public void fromJSON(String content) {
        super.fromJSON(content);
        try {
            JSONObject json = JSONObject.parseObject(datas);
            this.total = json.getInteger("total");
            this.rows = (List<T>) T.fromJSONListAuto(json.getString("rows"), getBeanType());
        } catch (Exception e) {
            this.total = 0;
            this.rows = new ArrayList<>();
        }
    }

}
