package framework.mvp1.base.net;

import java.io.File;

/**
 * Created by Administrator on 2017/7/12 0012.
 */

public class BaseMulitRequest {
    /**
     * 媒体类型
     */
    public String contentType = "";
    /**
     * key
     */
    public String key;
    public String theme;// "image 图片 audio 音频 video 视频 files 文件"
    public String path;
    public File file;

    public BaseMulitRequest(String key, File file, String contentType, String theme, String path) {
        this.contentType = contentType;
        this.key = key;
        this.file = file;
        this.theme = theme;
        this.path = path;
    }
}
