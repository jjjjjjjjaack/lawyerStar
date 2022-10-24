package framework.mvp1.base.util;

public class WebViewUtil {

    /**
     * 设置富文本适配手机屏幕
     *
     * @param content 富文本内容
     */
    public static String setWebViewContent(String content) {
        return "<html>\n" +
                "    <head>\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "        <style>img{max-width: 100%; width:100%; height:auto;}iframe{max-width: 100%; width:100%; height:auto;}</style>\n" +
                "    </head>\n" +
                "    <body style=\"word-wrap:break-word; font-family:Arial;padding:0px 5px 0px 5px;\">" + (content == null ? "" : content.trim()) + " </body></html>";
    }


    public static String setWebViewContent2(String content) {
        return "<html>"
                + (content == null ? "" : content.trim()) + " </html>";
    }
}
