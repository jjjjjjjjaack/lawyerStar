package com.qbo.lawyerstar.app.module.home.bean;

import org.json.JSONObject;

import java.util.List;

import framework.mvp1.base.bean.BaseBean;

public class HomeDataBean extends BaseBean {
    private List<BannerBean> banner;
    private List<String> announcement;
    private List<LawyerListBean> lawyer_list;
    private List<ArticleListBean> article_list;

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<String> getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(List<String> announcement) {
        this.announcement = announcement;
    }

    public List<LawyerListBean> getLawyer_list() {
        return lawyer_list;
    }

    public void setLawyer_list(List<LawyerListBean> lawyer_list) {
        this.lawyer_list = lawyer_list;
    }

    public List<ArticleListBean> getArticle_list() {
        return article_list;
    }

    public void setArticle_list(List<ArticleListBean> article_list) {
        this.article_list = article_list;
    }

    public static class BannerBean extends BaseBean {
        /**
         * ext : jpg
         * name : local
         * path : /static/upload/image/20220822/decda5eb577dae2e56a8747945f94028.jpg
         * size :
         * success : true
         * file_name : 微信图片_20220526113512.jpg
         * url : http://192.168.1.143/20220822dgl/PHP/public//static/upload/image/20220822/decda5eb577dae2e56a8747945f94028.jpg
         */

        private String ext;
        private String name;
        private String path;
        private String size;
        private String success;
        private String file_name;
        private String url;

        public String getExt() {
            return ext;
        }

        public void setExt(String ext) {
            this.ext = ext;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class LawyerListBean extends BaseBean {
        /**
         * id : 1000
         * create_time : 2022-08-20 16:53:48
         * update_time : 2022-08-20 16:54:00
         * avatar : http://192.168.1.143/20220822dgl/PHP/public//static/upload/image/20220820/a788b4eccaf37baa11abbda2fcfad97c.jpg
         * real_name : 李好律师
         * province_id : 37
         * city_id : 63
         * area_id : null
         * expertise : ["一般民事","交通事故"]
         * employment_year : 10
         * intro :
         * case_intro :
         * is_tj : 1
         */

        private String id;
        private String create_time;
        private String update_time;
        private String avatar;
        private String real_name;
        private String province_id;
        private String city_id;
        private Object area_id;
        private String employment_year;
        private String intro;
        private String case_intro;
        private String is_tj;
        private List<String> expertise;

        public String expertiseString;

        @Override
        public void fromJSONAuto(com.alibaba.fastjson.JSONObject json) {
            super.fromJSONAuto(json);
            expertiseString = "";
            for (int i = 0; i < expertise.size(); i++) {
                if (i == expertise.size() - 1) {
                    expertiseString += (expertise.get(i));
                } else {
                    if (i % 2 == 1) {
                        expertiseString += (expertise.get(i) + "\n");
                    } else {
                        expertiseString += (expertise.get(i) + ",");
                    }
                }
            }
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getProvince_id() {
            return province_id;
        }

        public void setProvince_id(String province_id) {
            this.province_id = province_id;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public Object getArea_id() {
            return area_id;
        }

        public void setArea_id(Object area_id) {
            this.area_id = area_id;
        }

        public String getEmployment_year() {
            return employment_year;
        }

        public void setEmployment_year(String employment_year) {
            this.employment_year = employment_year;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getCase_intro() {
            return case_intro;
        }

        public void setCase_intro(String case_intro) {
            this.case_intro = case_intro;
        }

        public String getIs_tj() {
            return is_tj;
        }

        public void setIs_tj(String is_tj) {
            this.is_tj = is_tj;
        }

        public List<String> getExpertise() {
            return expertise;
        }

        public void setExpertise(List<String> expertise) {
            this.expertise = expertise;
        }
    }

    public static class ArticleListBean extends BaseBean {
        /**
         * id : 1000
         * create_time : 2022-08-19
         * update_time : 2022-08-22 12:26:50
         * article_clazz : 5
         * title : 房屋买卖合同时效性是多少年?房屋 买卖合同时效过期能起诉吗?
         * image : http://192.168.1.143/20220822dgl/PHP/public//static/upload/image/20220819/2600d63ee39c89cb991e8fab47d99563.jpg
         * is_rec : 1
         * content : <span style="color:#E53333;">商标对于一家企业的产品管理来说非常重要，因为商标是每个公司的象征，每个企业都需要一个品牌商标。</span><br />
         * <br />
         * <strong>然而，许多企业并不选择这种直接注册商标的方式，而是愿意花费数倍的价格购买商标，也就是说，他们通过商标转让来获得自己的商标。</strong><br />
         * reading : 3
         */

        private String id;
        private String create_time;
        private String update_time;
        private String article_clazz;
        private String title;
        private String image;
        private String is_rec;
        private String content;
        private String reading;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getArticle_clazz() {
            return article_clazz;
        }

        public void setArticle_clazz(String article_clazz) {
            this.article_clazz = article_clazz;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getIs_rec() {
            return is_rec;
        }

        public void setIs_rec(String is_rec) {
            this.is_rec = is_rec;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getReading() {
            return reading;
        }

        public void setReading(String reading) {
            this.reading = reading;
        }
    }
}
