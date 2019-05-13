package com.haitai.ice.utils.file;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cuihao
 * @create 2016-11-22-9:25
 */

public class WebUtil {
    // 以下为服务器端判断客户端浏览器类型的方法
    public static String getBrowser(HttpServletRequest request) {
        String UserAgent = request.getHeader("USER-AGENT").toLowerCase();
        if (UserAgent != null) {
            if (UserAgent.indexOf("msie") >= 0)
                return "IE";
            if (UserAgent.indexOf("firefox") >= 0)
                return "FF";
            if (UserAgent.indexOf("safari") >= 0)
                return "SF";
        }
        return null;
    }
}
