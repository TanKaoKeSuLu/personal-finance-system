package com.tankaokesulu.financeapi.config;

import com.tankaokesulu.financeapi.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        // 放行跨域OPTIONS预检请求，解决注册/登录401问题
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String token = request.getHeader("Authorization");
        // 未携带token直接返回401
        if (token == null || token.isEmpty()) {
            response.setStatus(401);
            return false;
        }

        // 去掉Bearer前缀
        token = token.replace("Bearer ", "");

        try {
            // 解析token，将用户ID存入请求域
            Claims claims = JwtUtil.parseToken(token);
            request.setAttribute("userId", claims.get("userId"));
        } catch (Exception e) {
            // token无效、过期均返回401
            response.setStatus(401);
            return false;
        }

        return true;
    }

}