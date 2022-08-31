package framework.mvp1.base.bean;

public class SPathBean extends BaseBean {
    /**
     * ext : jpg
     * path : /static/upload/law/20220831/951f9c264c5972a53e215ce105493833.jpg
     * size : 2730960
     * success : true
     * file_name : IMG_20211231_161900.jpg
     * name : local
     * url : http://192.168.1.143/20220822dgl/PHP/public//static/upload/law/20220831/951f9c264c5972a53e215ce105493833.jpg
     */

    public String ext;
    public String path;
    public String size;
    public String success;
    public String file_name;
    public String name;
    public String url;

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
