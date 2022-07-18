package com.igw.market.common.interecptor;

import com.alibaba.fastjson.JSON;
import com.igw.base.common.constant.SystemConstant;
import com.igw.market.common.domain.ResultMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static com.igw.base.common.constant.PathConstant.URI_LOGIN_CHECK;
import static com.igw.base.common.constant.PathConstant.URI_LOGOUT;
import static com.igw.base.common.constant.SystemConstant.SESSION_KEY_USER_NAME;


/**
 * 用户认证拦截器。验证用户是否登录。
 *
 * @author aiyongqiang
 */
@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    public static final String PATH_PATTERNS = "/**";

    /**
     * 排除不拦截的路径列表
     */
    public static final String EXCLUDE_PATTERNS =
            "/static/**" + SystemConstant.CHAR_COMMA +
                    URI_LOGIN_CHECK + SystemConstant.CHAR_COMMA +
                    URI_LOGOUT + SystemConstant.CHAR_COMMA +
                    "/config/**" + SystemConstant.CHAR_COMMA +
                    "/open/**" + SystemConstant.CHAR_COMMA +
                    "/loginToSystem" + SystemConstant.CHAR_COMMA +
                    "/user_push/excute/v1" + SystemConstant.CHAR_COMMA +

                    "/home/**" + SystemConstant.CHAR_COMMA +
                    "/docInfo/**" + SystemConstant.CHAR_COMMA +
                    "/file/**" + SystemConstant.CHAR_COMMA +
                    "/search/**" + SystemConstant.CHAR_COMMA +

                    "/createFile/**" + SystemConstant.CHAR_COMMA +
                    "/userFund/**" + SystemConstant.CHAR_COMMA +

                    // swagger2-不拦截的路径列表
                    "/swagger-resources/**" + SystemConstant.CHAR_COMMA +
                    "/webjars/**" + SystemConstant.CHAR_COMMA +
                    "/v2/**" + SystemConstant.CHAR_COMMA +
                    "/swagger-ui.html/**" + SystemConstant.CHAR_COMMA +

                    "/doc.html" + SystemConstant.CHAR_COMMA +
                    "/docs.html" + SystemConstant.CHAR_COMMA +

                    "/almanac/get_almanac_list_by_ide" + SystemConstant.CHAR_COMMA;


    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        //若session中存在用户身份信息，则认证通过。否则，返回登陆界面。
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        Object userIdentify = req.getSession().getAttribute(SESSION_KEY_USER_NAME);
        String path = req.getServletPath();
        if ("/".equals(path)) {
            if (userIdentify == null || "".equals(userIdentify)) {
                resp.sendRedirect("/static/views/page/home/login.html");
            } else {
                resp.sendRedirect("/static/views/page/home/home.html");
            }
            return false;
        } else {
            if (userIdentify == null || "".equals(userIdentify)) {
                PrintWriter writer = resp.getWriter();
                writer.print(JSON.toJSON(new ResultMessage("403", "页面过期，请重新登录。")));
                writer.close();
                return false;
            }
        }
        return super.preHandle(req, resp, handler);
    }

}  