package lazecoding.project.common.util.security;

import cn.hutool.core.convert.NumberWithFormat;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import lazecoding.project.common.exception.NilParamException;
import lazecoding.project.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 操作类
 *
 * @author lazecoding
 */
public class JWTOperator {

    private static final Logger logger = LoggerFactory.getLogger(JWTOperator.class);

    /**
     * 秘钥
     */
    public static final String SECRET = "scaffold-jwt-secret";

    /**
     * 秘钥
     */
    public static final byte[] SECRET_BYTE = SECRET.getBytes();

    /**
     * 最大有效时长 7 天，单位 秒
     */
    public static int MAX_AGE_DAY = 7;

    /**
     * 最大有效时长 7 天，单位 秒
     */
    public static int MAX_AGE_SECOND = MAX_AGE_DAY * 24 * 60 * 60;

    /**
     * 最大有效时长 7 天，单位 毫秒
     */
    public static long MAX_AGE_MILLISECOND = MAX_AGE_DAY * 24 * 60 * 60 * 60L;

    /**
     * 用户 token 名称
     */
    public static String TOKEN_NAME = "access-token";


    /**
     * 创建用户 token
     **/
    public static String createAccessToken(String uid, String uname) {
        JWTUser jwtUser = new JWTUser(uid, uname);
        return createAccessToken(jwtUser);
    }

    /**
     * 创建用户 token
     **/
    public static String createAccessToken(JWTUser jwtUser) {
        if (jwtUser == null) {
            throw new NilParamException("create access token jwt user is null");
        }
        String uid = jwtUser.getUid();
        String uname = jwtUser.getUname();
        if (!StringUtils.hasText(uid) || !StringUtils.hasText(uname)) {
            throw new NilParamException("create access token param has null");
        }
        Map<String, Object> map = new HashMap<>();
        // 业务属性
        map.put("uid", uid);
        map.put("uname", uname);
        // 基础属性
        DateTime now = DateTime.now();
        DateTime exp = now.offsetNew(DateField.DAY_OF_YEAR, MAX_AGE_DAY);
        // 签发时间
        map.put(JWTPayload.ISSUED_AT, now);
        // 生效时间
        map.put(JWTPayload.NOT_BEFORE, now);
        // 过期时间
        map.put(JWTPayload.EXPIRES_AT, exp);
        // 更新 jwtUser 的过期时间
        jwtUser.setExp(exp.getTime());
        return JWTUtil.createToken(map, SECRET_BYTE);
    }

    /**
     * 校验 token
     */
    public static boolean verify(String token) {
        boolean pass;
        try {
            pass = JWTUtil.verify(token, SECRET_BYTE);
            if (pass) {
                JWTValidator.of(token).validateDate();
            }
        } catch (Exception e) {
            pass = false;
            logger.error("jwt token verify exception", e);
        }
        return pass;
    }

    /**
     * 获取当前用户
     */
    public static JWTUser getJwtUserByToken(String token) {
        if (!StringUtils.hasText(token)) {
            throw new NilParamException("token is nil");
        }
        JWTUser jwtUser = null;
        try {
            JWT jwt = JWTUtil.parseToken(token);
            String uid = (String) jwt.getPayload("uid");
            String uname = (String) jwt.getPayload("uname");
            // 过期时间（秒级时间戳）
            NumberWithFormat expNumber = (NumberWithFormat) jwt.getPayload(JWTPayload.EXPIRES_AT);
            // 过期时间（毫秒级时间戳）
            long exp = expNumber.longValue() * 1000;
            jwtUser = new JWTUser(uid, uname, exp);
        } catch (Exception e) {
            logger.error("get jwt user by token exception", e);
        }
        return jwtUser;
    }

    /**
     * 获取当前用户
     */
    public static JWTUser getCurrentJwtUser() {
        return getJwtUserByToken(getAccessToken());
    }

    /**
     * 获取 access-token
     */
    public static String getAccessToken() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ObjectUtils.isEmpty(requestAttributes)) {
            return null;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        if (ObjectUtils.isEmpty(request)) {
            return null;
        }
        return getAccessToken(request);
    }

    /**
     * 获取 access-token
     */
    public static String getAccessToken(HttpServletRequest request) {
        if (ObjectUtils.isEmpty(request)) {
            return null;
        }
        String accessToken = getCookieValue(request, TOKEN_NAME);
        if (!StringUtils.hasText(accessToken)) {
            accessToken = request.getHeader(TOKEN_NAME);
        }
        return accessToken;
    }

    /**
     * 获取 Cookie 中指定属性的值
     *
     * @param request HttpServletRequest
     * @param name    key
     */
    private static String getCookieValue(HttpServletRequest request, String name) {
        if (ObjectUtils.isEmpty(request)) {
            return null;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 获取登录 Cookie
     *
     * @param accessToken 用户 token
     */
    public static Cookie getLoginCookie(String accessToken) {
        Cookie cookie = new Cookie(TOKEN_NAME, accessToken);
        cookie.setPath("/");
        cookie.setMaxAge(JWTOperator.MAX_AGE_SECOND);
        return cookie;
    }

    /**
     * 获取登出 Cookie
     **/
    public static Cookie getLogoutCookie() {
        Cookie cookie = new Cookie(TOKEN_NAME, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        return cookie;
    }


    public static void main(String[] args) {
        String token = createAccessToken("11", "22");
        System.out.println("token: " + token);
        JWTUser jwtUser = getJwtUserByToken(token);
        System.out.println(JsonUtil.GSON.toJson(jwtUser));
        System.out.println("verify: " + verify(token));
    }

}
